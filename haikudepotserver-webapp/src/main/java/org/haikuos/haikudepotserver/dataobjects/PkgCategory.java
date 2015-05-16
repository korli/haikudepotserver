/*
 * Copyright 2014-2015, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

package org.haikuos.haikudepotserver.dataobjects;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.QueryCacheStrategy;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.haikuos.haikudepotserver.dataobjects.auto._PkgCategory;
import org.haikuos.haikudepotserver.dataobjects.support.Coded;
import org.haikuos.haikudepotserver.support.SingleCollector;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PkgCategory extends _PkgCategory implements Coded {

    public static List<PkgCategory> getAll(ObjectContext context) {
        Preconditions.checkNotNull(context);
        SelectQuery query = new SelectQuery(PkgCategory.class);
        query.setCacheStrategy(QueryCacheStrategy.SHARED_CACHE);
        query.addOrdering(new Ordering(NAME_PROPERTY, SortOrder.ASCENDING));
        return (List<PkgCategory>) context.performQuery(query);
    }

    public static Optional<PkgCategory> getByCode(ObjectContext context, final String code) {
        Preconditions.checkNotNull(context);
        Preconditions.checkState(!Strings.isNullOrEmpty(code));
        return getAll(context).stream().filter(pc -> pc.getCode().equals(code)).collect(SingleCollector.optional());
    }

    /**
     * <p>Given the codes supplied, this method will return all of the categories for
     * those codes.</p>
     */

    public static List<PkgCategory> getByCodes(ObjectContext context, final Collection<String> codes) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(codes);

        if(codes.isEmpty()) {
            return Collections.emptyList();
        }

        return getAll(context).stream().filter(pc -> codes.contains(pc.getCode())).collect(Collectors.toList());
    }

    /**
     * <p>Can be looked up in the localizations to get a title for this category.</p>
     */

    public String getTitleKey() {
        return "pkgCategory." + getCode().toLowerCase() + ".title";
    }

}
