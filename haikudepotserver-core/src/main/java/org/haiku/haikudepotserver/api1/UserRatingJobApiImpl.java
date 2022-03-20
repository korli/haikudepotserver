/*
 * Copyright 2018-2021, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

package org.haiku.haikudepotserver.api1;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.haiku.haikudepotserver.api1.model.userrating.job.QueueUserRatingSpreadsheetJobRequest;
import org.haiku.haikudepotserver.api1.model.userrating.job.QueueUserRatingSpreadsheetJobResult;
import org.haiku.haikudepotserver.dataobjects.Pkg;
import org.haiku.haikudepotserver.dataobjects.User;
import org.haiku.haikudepotserver.job.model.JobService;
import org.haiku.haikudepotserver.job.model.JobSnapshot;
import org.haiku.haikudepotserver.security.PermissionEvaluator;
import org.haiku.haikudepotserver.security.model.Permission;
import org.haiku.haikudepotserver.userrating.model.UserRatingSpreadsheetJobSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("userRatingJobApiImplV1")
@AutoJsonRpcServiceImpl
public class UserRatingJobApiImpl extends AbstractApiImpl implements UserRatingJobApi {

    protected static Logger LOGGER = LoggerFactory.getLogger(UserRatingJobApiImpl.class);

    private final ServerRuntime serverRuntime;
    private final PermissionEvaluator permissionEvaluator;
    private final JobService jobService;

    public UserRatingJobApiImpl(
            ServerRuntime serverRuntime,
            PermissionEvaluator permissionEvaluator,
            JobService jobService) {
        this.serverRuntime = Preconditions.checkNotNull(serverRuntime);
        this.permissionEvaluator = Preconditions.checkNotNull(permissionEvaluator);
        this.jobService = Preconditions.checkNotNull(jobService);
    }

    @Override
    public QueueUserRatingSpreadsheetJobResult queueUserRatingSpreadsheetJob(QueueUserRatingSpreadsheetJobRequest request) {
        Preconditions.checkArgument(null!=request);
        Preconditions.checkArgument(Strings.isNullOrEmpty(request.pkgName) || Strings.isNullOrEmpty(request.userNickname),"the user nickname or pkg name can be supplied, but not both");

        final ObjectContext context = serverRuntime.newContext();

        User user = obtainAuthenticatedUser(context);
        UserRatingSpreadsheetJobSpecification spec = new UserRatingSpreadsheetJobSpecification();

        if(!Strings.isNullOrEmpty(request.repositoryCode)) {
            spec.setRepositoryCode(getRepository(context, request.repositoryCode).getCode());
        }

        if(!Strings.isNullOrEmpty(request.userNickname)) {
            Optional<User> requestUserOptional = User.tryGetByNickname(context, request.userNickname);

            if(requestUserOptional.isEmpty()) {
                throw new AccessDeniedException("attempt to produce user rating report for user ["
                        + request.userNickname + "], but that user does not exist -- not allowed");
            }

            if(!permissionEvaluator.hasPermission(
                    SecurityContextHolder.getContext().getAuthentication(),
                    requestUserOptional.get(),
                    Permission.BULK_USERRATINGSPREADSHEETREPORT_USER)) {
                throw new AccessDeniedException(
                        "attempt to access a user rating report for user ["
                                + request.userNickname + "], but this was disallowed");
            }

            spec.setUserNickname(request.userNickname);
        }
        else {
            if (!Strings.isNullOrEmpty(request.pkgName)) {
                Optional<Pkg> requestPkgOptional = Pkg.tryGetByName(context, request.pkgName);

                if (requestPkgOptional.isEmpty()) {
                    throw new AccessDeniedException(
                            "attempt to produce user rating report for pkg ["
                                    + request.pkgName + "], but that pkg does not exist -- not allowed");
                }

                if (!permissionEvaluator.hasPermission(
                        SecurityContextHolder.getContext().getAuthentication(),
                        requestPkgOptional.get(),
                        Permission.BULK_USERRATINGSPREADSHEETREPORT_PKG)) {
                    throw new AccessDeniedException(
                            "attempt to access a user rating report for pkg ["
                                    + request.pkgName + "], but this was disallowed");
                }

                spec.setPkgName(request.pkgName);
            }
            else {
                if (!permissionEvaluator.hasPermission(
                        SecurityContextHolder.getContext().getAuthentication(),
                        null,
                        Permission.BULK_USERRATINGSPREADSHEETREPORT_ALL)) {
                    throw new AccessDeniedException("attempt to access a user rating report, but was unauthorized");
                }
            }
        }

        spec.setOwnerUserNickname(user.getNickname());

        return new QueueUserRatingSpreadsheetJobResult(
                jobService.submit(spec, JobSnapshot.COALESCE_STATUSES_QUEUED_STARTED));

    }


}
