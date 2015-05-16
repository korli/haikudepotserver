/*
 * Copyright 2013-2015, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

package org.haikuos.haikudepotserver.dataobjects;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.QueryCacheStrategy;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.haikuos.haikudepotserver.dataobjects.auto._Architecture;
import org.haikuos.haikudepotserver.support.SingleCollector;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Architecture extends _Architecture {

    public final static String CODE_SOURCE = "source";
    public final static String CODE_ANY = "any";
    public final static String CODE_X86 = "x86";

    public static List<Architecture> getAll(ObjectContext context) {
        SelectQuery query = new SelectQuery(Architecture.class);
        query.addOrdering(Architecture.CODE_PROPERTY, SortOrder.ASCENDING);
        query.setCacheStrategy(QueryCacheStrategy.SHARED_CACHE);
        return (List<Architecture>) context.performQuery(query);
    }

    public static Optional<Architecture> getByCode(ObjectContext context, final String code) {
        Preconditions.checkNotNull(context);
        Preconditions.checkState(!Strings.isNullOrEmpty(code));
        return getAll(context).stream().filter(a -> a.getCode().equals(code)).collect(SingleCollector.optional());
    }

    /**
     * <p>This method will return all of the architectures except for those that are identified by
     * the supplied list of codes.</p>
     */

    public static List<Architecture> getAllExceptByCode(ObjectContext context, final Collection<String> codes) {
        Preconditions.checkArgument(null != context);
        assert null!=context;
        Preconditions.checkArgument(null != codes);
        assert null!=codes;
        return getAll(context).stream().filter(a -> !codes.contains(a.getCode())).collect(Collectors.toList());
    }

    public UriComponentsBuilder appendPathSegments(UriComponentsBuilder builder) {
        return builder.pathSegment(getCode());
    }

    @Override
    public String toString() {
        return "arch;"+getCode();
    }

}
