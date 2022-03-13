package org.haiku.haikudepotserver.dataobjects.auto;

import java.util.Map;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.QueryResult;
import org.apache.cayenne.query.MappedExec;

/**
 * This class was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public class _HaikuDepot {

    public static final String PKG_VERSION_LOCALIZATION_RESOLUTION_QUERYNAME = "PkgVersionLocalizationResolution";

    public static final String ALL_PKG_ICONS_QUERYNAME = "AllPkgIcons";

    public static final String ALL_PKG_SCREENSHOTS_QUERYNAME = "AllPkgScreenshots";

    public static final String PKG_NAMES_FOR_REPOSITORY_SOURCE_QUERYNAME = "PkgNamesForRepositorySource";

    public static final String SEARCH_PKG_VERSIONS_QUERYNAME = "SearchPkgVersions";
    public QueryResult<?> performPkgVersionLocalizationResolution(ObjectContext context, Map<String, ?> parameters) {
        MappedExec query = MappedExec.query(PKG_VERSION_LOCALIZATION_RESOLUTION_QUERYNAME).params(parameters);
        return query.execute(context);
    }

    public QueryResult<?> performAllPkgIcons(ObjectContext context, Map<String, ?> parameters) {
        MappedExec query = MappedExec.query(ALL_PKG_ICONS_QUERYNAME).params(parameters);
        return query.execute(context);
    }

    public QueryResult<?> performAllPkgScreenshots(ObjectContext context, Map<String, ?> parameters) {
        MappedExec query = MappedExec.query(ALL_PKG_SCREENSHOTS_QUERYNAME).params(parameters);
        return query.execute(context);
    }

    public QueryResult<?> performPkgNamesForRepositorySource(ObjectContext context, Map<String, ?> parameters) {
        MappedExec query = MappedExec.query(PKG_NAMES_FOR_REPOSITORY_SOURCE_QUERYNAME).params(parameters);
        return query.execute(context);
    }

    public QueryResult<?> performSearchPkgVersions(ObjectContext context, Map<String, ?> parameters) {
        MappedExec query = MappedExec.query(SEARCH_PKG_VERSIONS_QUERYNAME).params(parameters);
        return query.execute(context);
    }

}