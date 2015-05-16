/*
 * Copyright 2013-2014, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

package org.haikuos.haikudepotserver.api1;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.haikuos.haikudepotserver.api1.model.miscellaneous.*;
import org.haikuos.haikudepotserver.api1.support.ObjectNotFoundException;
import org.haikuos.haikudepotserver.dataobjects.*;
import org.haikuos.haikudepotserver.feed.FeedOrchestrationService;
import org.haikuos.haikudepotserver.feed.model.FeedSpecification;
import org.haikuos.haikudepotserver.naturallanguage.NaturalLanguageOrchestrationService;
import org.haikuos.haikudepotserver.support.RuntimeInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MiscellaneousApiImpl extends AbstractApiImpl implements MiscellaneousApi {

    protected static Logger LOGGER = LoggerFactory.getLogger(PkgApiImpl.class);

    public final static String RESOURCE_MESSAGES = "/messages%s.properties";
    public final static String RESOURCE_MESSAGES_NATURALLANGUAGE = "/naturallanguagemessages.properties";

    @Resource
    private ServerRuntime serverRuntime;

    @Resource
    private RuntimeInformationService runtimeInformationService;

    @Resource
    private FeedOrchestrationService feedOrchestrationService;

    @Resource
    private MessageSource messageSource;

    @Resource
    private NaturalLanguageOrchestrationService naturalLanguageOrchestrationService;

    @Value("${deployment.isproduction:false}")
    private Boolean isProduction;

    @Override
    public GetAllPkgCategoriesResult getAllPkgCategories(GetAllPkgCategoriesRequest getAllPkgCategoriesRequest) {
        Preconditions.checkNotNull(getAllPkgCategoriesRequest);
        final ObjectContext context = serverRuntime.getContext();

        final Optional<NaturalLanguage> naturalLanguageOptional =
                Strings.isNullOrEmpty(getAllPkgCategoriesRequest.naturalLanguageCode)
                ? Optional.<NaturalLanguage>empty()
                        : NaturalLanguage.getByCode(context, getAllPkgCategoriesRequest.naturalLanguageCode);

        return new GetAllPkgCategoriesResult(
                PkgCategory.getAll(context).stream().map(pc -> {
                    if(naturalLanguageOptional.isPresent()) {
                        return new GetAllPkgCategoriesResult.PkgCategory(
                                pc.getCode(),
                                messageSource.getMessage(
                                        pc.getTitleKey(),
                                        null, // params
                                        naturalLanguageOptional.get().toLocale()));
                    }
                    else {
                        return new GetAllPkgCategoriesResult.PkgCategory(
                                pc.getCode(),
                                pc.getName());
                    }
                }).collect(Collectors.toList())
        );
    }

    @Override
    public GetAllNaturalLanguagesResult getAllNaturalLanguages(GetAllNaturalLanguagesRequest getAllNaturalLanguagesRequest) {
        Preconditions.checkNotNull(getAllNaturalLanguagesRequest);
        final ObjectContext context = serverRuntime.getContext();

        final Optional<NaturalLanguage> naturalLanguageOptional =
                Strings.isNullOrEmpty(getAllNaturalLanguagesRequest.naturalLanguageCode)
                        ? Optional.<NaturalLanguage>empty()
                        : NaturalLanguage.getByCode(context, getAllNaturalLanguagesRequest.naturalLanguageCode);

        return new GetAllNaturalLanguagesResult(
                NaturalLanguage.getAll(context).stream().map(nl -> {
                            if(naturalLanguageOptional.isPresent()) {
                                return new GetAllNaturalLanguagesResult.NaturalLanguage(
                                        nl.getCode(),
                                        messageSource.getMessage(
                                                nl.getTitleKey(),
                                                null, // params
                                                naturalLanguageOptional.get().toLocale()),
                                        nl.getIsPopular(),
                                        naturalLanguageOrchestrationService.hasData(nl.getCode()),
                                        naturalLanguageOrchestrationService.hasLocalizationMessages(nl.getCode()));
                            }
                            else {
                                return new GetAllNaturalLanguagesResult.NaturalLanguage(
                                        nl.getCode(),
                                        nl.getName(),
                                        nl.getIsPopular(),
                                        naturalLanguageOrchestrationService.hasData(nl.getCode()),
                                        naturalLanguageOrchestrationService.hasLocalizationMessages(nl.getCode()));
                            }
                        }
                ).collect(Collectors.toList())
        );

    }

    @Override
    public RaiseExceptionResult raiseException(RaiseExceptionRequest raiseExceptionRequest) {

        final ObjectContext context = serverRuntime.getContext();
        Optional<User> authUserOptional = tryObtainAuthenticatedUser(context);

        if(authUserOptional.isPresent() && authUserOptional.get().getIsRoot()) {
            throw new RuntimeException("test exception");
        }

        LOGGER.warn("attempt to raise a test exception without being authenticated as root");

        return new RaiseExceptionResult();
    }

    @Override
    public GetRuntimeInformationResult getRuntimeInformation(GetRuntimeInformationRequest getRuntimeInformationRequest) {

        final ObjectContext context = serverRuntime.getContext();
        Optional<User> authUserOptional = tryObtainAuthenticatedUser(context);

        GetRuntimeInformationResult result = new GetRuntimeInformationResult();
        result.projectVersion = runtimeInformationService.getProjectVersion();
        result.getBulkPkgRequestLimit = PkgApi.GETBULKPKG_LIMIT;
        result.isProduction = isProduction;

        if(authUserOptional.isPresent() && authUserOptional.get().getIsRoot()) {
            result.javaVersion = runtimeInformationService.getJavaVersion();
            result.startTimestamp = runtimeInformationService.getStartTimestamp();
        }

        return result;
    }

    @Override
    public GetAllArchitecturesResult getAllArchitectures(GetAllArchitecturesRequest getAllArchitecturesRequest) {
        Preconditions.checkNotNull(getAllArchitecturesRequest);
        GetAllArchitecturesResult result = new GetAllArchitecturesResult();
        result.architectures =
                Architecture.getAll(serverRuntime.getContext())
                        .stream()
                        .filter(a -> !a.getCode().equals(Architecture.CODE_SOURCE) && !a.getCode().equals(Architecture.CODE_ANY))
                        .map(a -> new GetAllArchitecturesResult.Architecture(a.getCode()))
                        .collect(Collectors.toList());

        return result;
    }

    private void appendFromBase(
            Map<String,String> result,
            NaturalLanguage naturalLanguage,
            String baseFormat) {

        String resourcePath = baseFormat;

        if(null!=naturalLanguage) {
            resourcePath = String.format(
                    baseFormat,
                    "_" + naturalLanguage.getCode());
        }

        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {

            if(null==inputStream) {
                LOGGER.debug("attempt to access localization messages; {} -- not found", resourcePath);
            }
            else {

                try (InputStreamReader reader = new InputStreamReader(inputStream, Charsets.UTF_8)) {

                    Properties properties = new Properties();
                    properties.load(reader);

                    for (String propertyName : properties.stringPropertyNames()) {
                        result.put(propertyName, properties.get(propertyName).toString());
                    }
                }
            }
        }
        catch(IOException ioe) {
            throw new RuntimeException("unable to assemble the messages to send for api1 from; "+resourcePath,ioe);
        }
    }

    @Override
    public GetAllMessagesResult getAllMessages(GetAllMessagesRequest getAllMessagesRequest) throws ObjectNotFoundException {
        Preconditions.checkNotNull(getAllMessagesRequest);
        Preconditions.checkNotNull(getAllMessagesRequest.naturalLanguageCode);

        ObjectContext context = serverRuntime.getContext();

        Optional<NaturalLanguage> naturalLanguageOptional = NaturalLanguage.getByCode(context, getAllMessagesRequest.naturalLanguageCode);

        if(!naturalLanguageOptional.isPresent()) {
            throw new ObjectNotFoundException(NaturalLanguage.class.getSimpleName(), getAllMessagesRequest.naturalLanguageCode);
        }

        GetAllMessagesResult getAllMessagesResult = new GetAllMessagesResult();
        getAllMessagesResult.messages = new HashMap<>();
        appendFromBase(getAllMessagesResult.messages, naturalLanguageOptional.get(), RESOURCE_MESSAGES);
        appendFromBase(getAllMessagesResult.messages, null, RESOURCE_MESSAGES_NATURALLANGUAGE);
        return getAllMessagesResult;
    }

    @Override
    public GetAllUserRatingStabilitiesResult getAllUserRatingStabilities(GetAllUserRatingStabilitiesRequest getAllUserRatingStabilitiesRequest) {
        Preconditions.checkNotNull(getAllUserRatingStabilitiesRequest);
        final ObjectContext context = serverRuntime.getContext();

        final Optional<NaturalLanguage> naturalLanguageOptional =
                Strings.isNullOrEmpty(getAllUserRatingStabilitiesRequest.naturalLanguageCode)
                        ? Optional.<NaturalLanguage>empty()
                        : NaturalLanguage.getByCode(context, getAllUserRatingStabilitiesRequest.naturalLanguageCode);

        return new GetAllUserRatingStabilitiesResult(
                UserRatingStability.getAll(context)
                        .stream()
                        .map(urs -> {
                            if(naturalLanguageOptional.isPresent()) {
                                return new GetAllUserRatingStabilitiesResult.UserRatingStability(
                                        urs.getCode(),
                                        messageSource.getMessage(
                                                urs.getTitleKey(),
                                                null, // params
                                                naturalLanguageOptional.get().toLocale()));
                            }

                            return new GetAllUserRatingStabilitiesResult.UserRatingStability(
                                    urs.getCode(),
                                    urs.getName());
                        })
                        .collect(Collectors.toList())
        );
    }

    @Override
    public GetAllProminencesResult getAllProminences(GetAllProminencesRequest request) {
        Preconditions.checkNotNull(request);
        final ObjectContext context = serverRuntime.getContext();

        return new GetAllProminencesResult(
                Prominence.getAll(context)
                .stream()
                .map(p -> new GetAllProminencesResult.Prominence(p.getOrdering(),p.getName()))
                .collect(Collectors.toList())
        );
    }

    @Override
    public GenerateFeedUrlResult generateFeedUrl(final GenerateFeedUrlRequest request) throws ObjectNotFoundException {
        Preconditions.checkNotNull(request);

        final ObjectContext context = serverRuntime.getContext();
        FeedSpecification specification = new FeedSpecification();
        specification.setLimit(request.limit);

        if(null!=request.supplierTypes) {
            specification.setSupplierTypes(
                    request.supplierTypes
                    .stream()
                    .map(st -> FeedSpecification.SupplierType.valueOf(st.name()))
                    .collect(Collectors.toList())
            );
        }

        if(null!=request.naturalLanguageCode) {
            specification.setNaturalLanguageCode(getNaturalLanguage(context, request.naturalLanguageCode).getCode());
        }

        if(null!=request.pkgNames) {
            List<String> checkedPkgNames = new ArrayList<>();

            for (String pkgName : request.pkgNames) {
                Optional<Pkg> pkgOptional = Pkg.getByName(context, pkgName);

                if (!pkgOptional.isPresent()) {
                    throw new ObjectNotFoundException(Pkg.class.getSimpleName(), pkgName);
                }

                checkedPkgNames.add(pkgOptional.get().getName());
            }

            specification.setPkgNames(checkedPkgNames);
        }

        GenerateFeedUrlResult result = new GenerateFeedUrlResult();
        result.url = feedOrchestrationService.generateUrl(specification);
        return result;
    }

}
