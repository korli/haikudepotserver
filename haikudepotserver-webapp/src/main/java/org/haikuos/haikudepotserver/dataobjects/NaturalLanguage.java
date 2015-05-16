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
import org.haikuos.haikudepotserver.dataobjects.auto._NaturalLanguage;
import org.haikuos.haikudepotserver.support.SingleCollector;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <P>This describes a spoken or "natural" language that can be used for localization of the application.  These are
 * referenced by <a href="https://en.wikipedia.org/wiki/ISO_639-1">ISO 639-1</a> values such as "en", "de" etc...</P>
 */

public class NaturalLanguage extends _NaturalLanguage {

    public final static String CODE_ENGLISH = "en";
    public final static String CODE_GERMAN = "de";
    public final static String CODE_SPANISH = "es";
    public final static String CODE_FRENCH = "fr";
    public final static String CODE_RUSSIAN = "ru";

    public static List<NaturalLanguage> getAll(ObjectContext context) {
        Preconditions.checkNotNull(context);
        SelectQuery query = new SelectQuery(NaturalLanguage.class);
        query.setCacheStrategy(QueryCacheStrategy.SHARED_CACHE);
        query.addOrdering(new Ordering(NAME_PROPERTY, SortOrder.ASCENDING));
        return (List<NaturalLanguage>) context.performQuery(query);
    }

    public static List<NaturalLanguage> getAllPopular(ObjectContext context) {
        Preconditions.checkNotNull(context);
        return getAll(context).stream().filter(nl -> nl.getIsPopular()).collect(Collectors.toList());
    }

    public static List<NaturalLanguage> getAllExceptEnglish(ObjectContext context) {
        Preconditions.checkNotNull(context);
        return getAll(context).stream().filter(nl -> !nl.getCode().equals(NaturalLanguage.CODE_ENGLISH)).collect(Collectors.toList());
    }

    public static Optional<NaturalLanguage> getByCode(ObjectContext context, final String code) {
        Preconditions.checkNotNull(context);
        Preconditions.checkState(!Strings.isNullOrEmpty(code));
        return getAll(context).stream().filter(nl -> nl.getCode().equals(code)).collect(SingleCollector.optional());
    }

    public static NaturalLanguage getEnglish(ObjectContext context) {
        return getByCode(context, CODE_ENGLISH).get();
    }

    /**
     * <p>This method will return all of the natural language codes in the system.</p>
     */

    public static List<String> getAllCodes(ObjectContext context) {
        Preconditions.checkNotNull(context);
        return getAll(context).stream().map(NaturalLanguage::getCode).collect(Collectors.toList());
    }

    public boolean isEnglish() {
        return CODE_ENGLISH.equals(getCode());
    }

    /**
     * <p>Can be used to lookup the title of this language in the localization strings.</p>
     */

    public String getTitleKey() {
        return String.format("naturalLanguage.%s",getCode().toLowerCase());
    }

    public Locale toLocale() {
        return Locale.forLanguageTag(getCode());
    }

    @Override
    public String toString() {
        return "nat-lang;"+getCode();
    }

}
