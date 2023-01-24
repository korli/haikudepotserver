/*
 * Copyright 2018-2023, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

package org.haiku.haikudepotserver.userrating;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ComparisonChain;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.EJBQLQuery;
import org.apache.commons.lang3.StringUtils;
import org.haiku.haikudepotserver.dataobjects.Pkg;
import org.haiku.haikudepotserver.dataobjects.PkgUserRatingAggregate;
import org.haiku.haikudepotserver.dataobjects.PkgVersion;
import org.haiku.haikudepotserver.dataobjects.Repository;
import org.haiku.haikudepotserver.dataobjects.RepositorySource;
import org.haiku.haikudepotserver.dataobjects.User;
import org.haiku.haikudepotserver.dataobjects.UserRating;
import org.haiku.haikudepotserver.support.StoppableConsumer;
import org.haiku.haikudepotserver.support.VersionCoordinates;
import org.haiku.haikudepotserver.support.VersionCoordinatesComparator;
import org.haiku.haikudepotserver.userrating.model.UserRatingSearchSpecification;
import org.haiku.haikudepotserver.userrating.model.UserRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRatingServiceImpl implements UserRatingService {

    protected static Logger LOGGER = LoggerFactory.getLogger(UserRatingServiceImpl.class);

    private final ServerRuntime serverRuntime;
    private final int userRatingDerivationVersionsBack;
    private final int userRatingsDerivationMinRatings;


    public UserRatingServiceImpl(
            ServerRuntime serverRuntime,
            @Value("${userrating.aggregation.pkg.versionsback:2}") int userRatingDerivationVersionsBack,
            @Value("${userrating.aggregation.pkg.minratings:3}") int userRatingsDerivationMinRatings) {
        this.serverRuntime = Preconditions.checkNotNull(serverRuntime);
        this.userRatingDerivationVersionsBack = userRatingDerivationVersionsBack;
        this.userRatingsDerivationMinRatings = userRatingsDerivationMinRatings;
    }

    // -------------------------------------
    // ITERATION

    /**
     * <p>This will be called for each user rating in the system with some constraints.</p>
     * @param c is the callback to invoke.
     * @return the quantity of user ratings processed.
     */

    // TODO; somehow prefetch the user?  prefetch tree?

    @Override
    public int each(
            ObjectContext context,
            UserRatingSearchSpecification search,
            StoppableConsumer<UserRating> c) {

        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(context);

        int count = 0;
        StringBuilder queryExpression = new StringBuilder();
        List<Object> parameters = new ArrayList<>();

        queryExpression.append("SELECT ur FROM UserRating ur");

        if(null!=search) {
            queryExpression.append(" WHERE ");
            queryExpression.append(prepareWhereClause(parameters, context, search));
        }

        queryExpression.append(" ORDER BY ur.createTimestamp DESC, ur.code DESC");

        EJBQLQuery query = new EJBQLQuery(queryExpression.toString());

        for(int i=0;i<parameters.size();i++) {
            query.setParameter(i + 1, parameters.get(i));
        }

        query.setFetchLimit(100);

        // now loop through the user ratings.

        while(true) {

            query.setFetchOffset(count);
            List<UserRating> userRatings = (List<UserRating>) context.performQuery(query);

            if(userRatings.isEmpty()) {
                return count;
            }

            for(UserRating userRating : userRatings) {
                if(!c.accept(userRating)) {
                    return count;
                }
            }

            count += userRatings.size();
        }

    }

    // -------------------------------------
    // SEARCH

    private String prepareWhereClause(
            List<Object> parameterAccumulator,
            ObjectContext context,
            UserRatingSearchSpecification search) {

        Preconditions.checkNotNull(search);
        Preconditions.checkNotNull(context);
        Instant instant = Instant.now();

        List<String> whereExpressions = new ArrayList<>();

        whereExpressions.add("ur.user.active = true");

        if (!search.getIncludeInactive()) {
            whereExpressions.add("ur." + UserRating.ACTIVE.getName() + " = true");
        }

        if (null != search.getRepository()) {
            parameterAccumulator.add(search.getRepository());
            whereExpressions.add(
                    "ur."
                            + UserRating.PKG_VERSION.getName() + "."
                            + PkgVersion.REPOSITORY_SOURCE.getName() + "."
                            + RepositorySource.REPOSITORY.getName() + " = ?" + parameterAccumulator.size());
        }

        if (null != search.getRepositorySource()) {
            parameterAccumulator.add(search.getRepositorySource());
            whereExpressions.add(
                    "ur."
                            + UserRating.PKG_VERSION.getName() + "."
                            + PkgVersion.REPOSITORY_SOURCE.getName() + " = ?" + parameterAccumulator.size());
        }

        if (null != search.getDaysSinceCreated()) {
            parameterAccumulator.add(new java.sql.Timestamp(instant.minus(search.getDaysSinceCreated(), ChronoUnit.DAYS).toEpochMilli()));
            whereExpressions.add("ur." + UserRating.CREATE_TIMESTAMP.getName() + " > ?" + parameterAccumulator.size());
        }

        if (null != search.getPkg() && null == search.getPkgVersion()) {
            parameterAccumulator.add(search.getPkg());
            whereExpressions.add("ur." + UserRating.PKG_VERSION.getName() + "." + PkgVersion.ACTIVE.getName() + " = true");
            whereExpressions.add("ur." + UserRating.PKG_VERSION.getName() + "." + PkgVersion.PKG.getName() + " = ?" + parameterAccumulator.size());
        }

        if (null != search.getArchitecture() && null == search.getPkgVersion()) {
            parameterAccumulator.add(search.getArchitecture());
            whereExpressions.add("ur." + UserRating.PKG_VERSION.getName() + "." + PkgVersion.ARCHITECTURE.getName() + " = ?" + parameterAccumulator.size());
        }

        if (null != search.getPkgVersion()) {
            parameterAccumulator.add(search.getPkgVersion());
            whereExpressions.add("ur." + UserRating.PKG_VERSION.getName() + " = ?" + parameterAccumulator.size());
        }

        if (null != search.getUser()) {
            parameterAccumulator.add(search.getUser());
            whereExpressions.add("ur." + UserRating.USER.getName() + " = ?" + parameterAccumulator.size());
        }

        return String.join(" AND ", whereExpressions);

    }

    @Override
    public List<UserRating> search(ObjectContext context, UserRatingSearchSpecification search) {
        Preconditions.checkNotNull(search);
        Preconditions.checkNotNull(context);

        List<Object> parameters = new ArrayList<>();
        EJBQLQuery query = new EJBQLQuery("SELECT ur FROM "
                + UserRating.class.getSimpleName() + " AS ur WHERE "
                + prepareWhereClause(parameters, context, search)+ " ORDER BY ur." + UserRating.CREATE_TIMESTAMP.getName() + " DESC");
        query.setFetchOffset(search.getOffset());
        query.setFetchLimit(search.getLimit());

        for(int i=0;i<parameters.size();i++) {
            query.setParameter(i+1,parameters.get(i));
        }

        //noinspection unchecked
        return context.performQuery(query);
    }

    @Override
    public long total(ObjectContext context, UserRatingSearchSpecification search) {
        Preconditions.checkNotNull(search);
        Preconditions.checkNotNull(context);

        List<Object> parameters = new ArrayList<>();
        EJBQLQuery ejbQuery = new EJBQLQuery("SELECT COUNT(ur) FROM UserRating AS ur WHERE " + prepareWhereClause(parameters, context, search));

        for (int i = 0; i < parameters.size(); i++) {
            ejbQuery.setParameter(i+1,parameters.get(i));
        }

        @SuppressWarnings("unchecked") List<Number> result = context.performQuery(ejbQuery);

        switch (result.size()) {
            case 1:
                return result.get(0).longValue();

            default:
                throw new IllegalStateException("expected 1 row from count query, but got "+result.size());
        }
    }

    // ------------------------------
    // DERIVATION ALGORITHM

    /**
     * <p>If a user has their active / inactive state swapped, it is possible that it may have some bearing
     * on user ratings because user rating values from inactive users are not included.  The impact may be
     * small, but may also be significant in cases where the package has few ratings anyway.  This method
     * will return a list of package names for those packages that may be accordingly effected by a change
     * in a user's active flag.</p>
     */

    @Override
    public List<String> pkgNamesEffectedByUserActiveStateChange(ObjectContext context, User user) {
        Preconditions.checkNotNull(user);
        EJBQLQuery query = new EJBQLQuery("SELECT DISTINCT ur.pkgVersion.pkg.name FROM UserRating ur WHERE ur.user=?1");
        query.setParameter(1,user);
        return (List<String>) context.performQuery(query);
    }

    private float averageAsFloat(Collection<Short> ratings) {
        Preconditions.checkNotNull(ratings);

        if(ratings.isEmpty()) {
            return 0f;
        }

        int sum = 0;

        for(short rating : ratings) {
            sum += rating;
        }

        sum *= 100;
        sum /= ratings.size();

        return ((float) sum) / 100f;
    }

    private List<String> getUserNicknamesWhoHaveRatedPkgVersions(ObjectContext context, List<PkgVersion> pkgVersions) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(pkgVersions);

        if (pkgVersions.isEmpty()) {
            return Collections.emptyList();
        }

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("SELECT DISTINCT ur.user.nickname FROM UserRating ur WHERE ur.user.active=true AND ur.active=true");
        queryBuilder.append(" AND (");

        for (int i = 0; i < pkgVersions.size(); i++) {
            if (0 != i) {
                queryBuilder.append(" OR ");
            }

            queryBuilder.append("ur.pkgVersion=?" + (i + 1));
        }

        queryBuilder.append(")");

        EJBQLQuery query = new EJBQLQuery(queryBuilder.toString());

        for(int i=0;i<pkgVersions.size();i++) {
            query.setParameter(i + 1, pkgVersions.get(i));
        }

        return context.performQuery(query);
    }

    /**
     * <p>This method will go through all of the relevant packages and will derive their user ratings.</p>
     */

    @Override
    public void updateUserRatingDerivationsForAllPkgs() {
        ObjectContext context = serverRuntime.newContext();

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT p.name FROM ");
        builder.append(Pkg.class.getSimpleName());
        builder.append(" p");
        builder.append(" WHERE p.active=true");
        builder.append(" ORDER BY p.name DESC");

        List<String> pkgNames = context.performQuery(new EJBQLQuery(builder.toString()));

        LOGGER.info("will derive and store user ratings for {} packages", pkgNames.size());

        for (String pkgName : pkgNames) {
            updateUserRatingDerivationsForPkg(pkgName);
        }

        LOGGER.info("did derive and store user ratings for {} packages", pkgNames.size());
    }

    /**
     * <p>This method will update the user rating aggregate across all appropriate
     * repositories.</p>
     */

    @Override
    public void updateUserRatingDerivationsForPkg(String pkgName) {
         Preconditions.checkArgument(!Strings.isNullOrEmpty(pkgName), "the name of the package is required");

        ObjectContext context = serverRuntime.newContext();

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT r.code FROM\n");
        builder.append(Repository.class.getSimpleName());
        builder.append(" r\n");
        builder.append("WHERE EXISTS(SELECT pv FROM\n");
        builder.append(PkgVersion.class.getSimpleName());
        builder.append(" pv WHERE pv.pkg.name = :name\n");
        builder.append(" AND pv.repositorySource.repository = r)\n");
        builder.append("ORDER BY r.code DESC");

        EJBQLQuery query = new EJBQLQuery(builder.toString());
        query.setParameter("name", pkgName);
        List<String> repositoryCodes = (List<String>) context.performQuery(query);

        for(String repositoryCode : repositoryCodes) {
            updateUserRatingDerivation(pkgName, repositoryCode);
        }
    }

    @Override
    public void updateUserRatingDerivationsForUser(String userNickname) {
        Preconditions.checkArgument(StringUtils.isNotBlank(userNickname), "the nickname must be provided");
        ObjectContext context = serverRuntime.newContext();
        User user = User.getByNickname(context, userNickname);
        List<String> pkgNames = pkgNamesEffectedByUserActiveStateChange(context, user);
        LOGGER.info("will update user rating derivations for user [{}] including {} pkgs",
                user, pkgNames.size());
        pkgNames.forEach(this::updateUserRatingDerivationsForPkg);
    }

    /**
     * <p>This method will update the stored user rating aggregate (average) for the package for the
     * specified repository.</p>
     */

    private void updateUserRatingDerivation(String pkgName, String repositoryCode) {
        Preconditions.checkState(!Strings.isNullOrEmpty(pkgName));
        Preconditions.checkState(!Strings.isNullOrEmpty(repositoryCode));

        ObjectContext context = serverRuntime.newContext();
        Pkg pkg = Pkg.tryGetByName(context, pkgName)
                .orElseThrow(() -> new IllegalStateException("user derivation job submitted, but no pkg was found; " + pkgName));
        Repository repository = Repository.tryGetByCode(context, repositoryCode)
                .orElseThrow(() -> new IllegalStateException("user derivation job submitted, but no repository was found; " + repositoryCode));

        long beforeMillis = System.currentTimeMillis();
        Optional<DerivedUserRating> rating = userRatingDerivation(context, pkg, repository);

        LOGGER.info("calculated the user rating for {} in {} in {}ms", pkg, repository, System.currentTimeMillis() - beforeMillis);

        if(rating.isEmpty()) {
            LOGGER.info("unable to establish a user rating for {} in {}", pkg, repository);
        }
        else {
            LOGGER.info(
                    "user rating established for {} in {}; {} (sample {})",
                    pkg,
                    repository,
                    rating.get().getRating(),
                    rating.get().getSampleSize());
        }

        Optional<PkgUserRatingAggregate> pkgUserRatingAggregateOptional = pkg.getPkgUserRatingAggregate(repository);

        if(rating.isPresent()) {

            PkgUserRatingAggregate pkgUserRatingAggregate = pkgUserRatingAggregateOptional.orElseGet(() -> {
                PkgUserRatingAggregate value = context.newObject(PkgUserRatingAggregate.class);
                value.setRepository(repository);
                value.setPkg(pkg);
                return value;
            });

            pkgUserRatingAggregate.setDerivedRating(rating.get().getRating());
            pkgUserRatingAggregate.setDerivedRatingSampleSize(rating.get().getSampleSize());
            pkg.setModifyTimestamp();
        }
        else {

            // if there is no derived user rating then there should also be no database record
            // for the user rating.

            if(pkgUserRatingAggregateOptional.isPresent()) {
                pkg.removeFromPkgUserRatingAggregates(pkgUserRatingAggregateOptional.get());
                context.deleteObject(pkgUserRatingAggregateOptional.get());
                pkg.setModifyTimestamp();
            }
        }

        context.commitChanges();

        LOGGER.info("did update user rating for {} in {}", pkg, repository);
    }

    /**
     * <p>This method will calculate the user rating for the package.  It may not be possible to generate a
     * user rating; in which case, an absent {@link Optional} is returned.</p>
     */

    Optional<DerivedUserRating> userRatingDerivation(ObjectContext context, Pkg pkg, Repository repository) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(pkg);

        // haul all of the pkg versions into memory first.

        List<PkgVersion> pkgVersions = repository.getRepositorySources().stream()
                .flatMap(rs -> PkgVersion.findForPkg(context, pkg, rs, false).stream()) // active only
                .collect(Collectors.toList());

        if(!pkgVersions.isEmpty()) {

            final VersionCoordinatesComparator versionCoordinatesComparator = new VersionCoordinatesComparator();

            // convert the package versions into coordinates and sort those.

            List<VersionCoordinates> versionCoordinates = pkgVersions
                    .stream()
                    .map(pv -> pv.toVersionCoordinates().toVersionCoordinatesWithoutPreReleaseOrRevision())
                    .distinct()
                    .sorted(versionCoordinatesComparator)
                    .toList();

            // work back VERSIONS_BACK from the latest in order to find out where to start fishing out user
            // ratings from.

            final VersionCoordinates oldestVersionCoordinates;

            if (versionCoordinates.size() < userRatingDerivationVersionsBack + 1) {
                oldestVersionCoordinates = versionCoordinates.get(0);
            }
            else {
                oldestVersionCoordinates = versionCoordinates.get(versionCoordinates.size() - (userRatingDerivationVersionsBack +1));
            }

            // now we need to find all of the package versions that are including this one or newer.

            {
                final VersionCoordinatesComparator mainPartsVersionCoordinatesComparator = new VersionCoordinatesComparator(true);
                pkgVersions = pkgVersions
                        .stream()
                        .filter(pv -> mainPartsVersionCoordinatesComparator.compare(pv.toVersionCoordinates(), oldestVersionCoordinates) >= 0)
                        .collect(Collectors.toList());
            }

            // only one user rating should taken from each user; the latest one.  Get a list of all of the
            // people who have rated these versions.

            List<Short> ratings = new ArrayList<>();
            List<String> userNicknames = getUserNicknamesWhoHaveRatedPkgVersions(context, pkgVersions);

            for(String nickname : userNicknames) {
                User user = User.getByNickname(context, nickname);

                List<UserRating> userRatingsForUser = new ArrayList<>(UserRating.getByUserAndPkgVersions(
                        context, user, pkgVersions));

                userRatingsForUser.sort((o1, o2) ->
                        ComparisonChain.start()
                                .compare(
                                        o1.getPkgVersion().toVersionCoordinates(),
                                        o2.getPkgVersion().toVersionCoordinates(),
                                        versionCoordinatesComparator)
                                .compare(o1.getCreateTimestamp(), o2.getCreateTimestamp())
                                .compare(
                                        o1.getPkgVersion().getArchitecture().getCode(),
                                        o2.getPkgVersion().getArchitecture().getCode())
                                .result());

                if(!userRatingsForUser.isEmpty()) {
                    UserRating latestUserRatingForUser = userRatingsForUser.get(userRatingsForUser.size()-1);

                    if(null!=latestUserRatingForUser.getRating()) {
                        ratings.add(latestUserRatingForUser.getRating());
                    }
                }

            }

            // now generate an average from those ratings found.

            if(ratings.size() >= userRatingsDerivationMinRatings) {
                return Optional.of(new DerivedUserRating(
                    averageAsFloat(ratings),
                    ratings.size()));
            }
        }

        return Optional.empty();
    }

    @Override
    public void removeUserRatingAtomically(String userRatingCode) {
        ObjectContext context = serverRuntime.newContext();
        UserRating userRating = UserRating.getByCode(context, userRatingCode);

        context.deleteObject(userRating);
        context.commitChanges();

        LOGGER.info("did delete user rating [{}]", userRatingCode);
    }


    /**
     * <P>This is used as a model class / return value for the derivation of the user rating.</P>
     */

    static class DerivedUserRating {

        private final float rating;
        private final int sampleSize;

        DerivedUserRating(float rating, int sampleSize) {
            this.rating = rating;
            this.sampleSize = sampleSize;
        }

        public float getRating() {
            return rating;
        }

        int getSampleSize() {
            return sampleSize;
        }
    }

}
