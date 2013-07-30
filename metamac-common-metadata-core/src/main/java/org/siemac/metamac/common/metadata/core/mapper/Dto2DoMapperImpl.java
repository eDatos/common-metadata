package org.siemac.metamac.common.metadata.core.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionParameters;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataService;
import org.siemac.metamac.common.metadata.core.serviceimpl.utils.CommonMetadataValidationUtils;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.ExternalItemRepository;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.InternationalStringRepository;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.core.common.mapper.BaseDto2DoMapperImpl;
import org.siemac.metamac.core.common.util.OptimisticLockingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Dto2DoMapperImpl extends BaseDto2DoMapperImpl implements Dto2DoMapper {

    @Autowired
    private CommonMetadataService         commonMetadataService;

    @Autowired
    private InternationalStringRepository internationalStringRepository;

    @Autowired
    private ExternalItemRepository        externalItemRepository;

    @Override
    public Configuration configurationDtoToDo(ServiceContext ctx, ConfigurationDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity
        Configuration target = new Configuration();
        if (source.getId() != null) {
            target = commonMetadataService.findConfigurationById(ctx, source.getId());
            OptimisticLockingUtils.checkVersion(target.getVersion(), source.getOptimisticLockingVersion());

            // Metadata unmodifiable
            // It's necessary to check that all the metadata that conforms the URN are unmodifibale once the configuration is published
            List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();
            if (target.isExternallyPublished()) {
                CommonMetadataValidationUtils.checkMetadataUnmodifiable(target.getCode(), source.getCode(), ServiceExceptionParameters.CONFIGURATION_CODE, exceptions);
            }
            ExceptionUtils.throwIfException(exceptions);
        }

        configurationDtoToDo(source, target);
        return target;
    }

    private Configuration configurationDtoToDo(ConfigurationDto source, Configuration target) throws MetamacException {
        if (target == null) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.CONFIGURATION);
        }

        target.setCode(source.getCode());
        target.setUrn(source.getUrn());
        target.setLegalActs(internationalStringToDo(source.getLegalActs(), target.getLegalActs(), ServiceExceptionParameters.CONFIGURATION_LEGAL_ACTS));
        target.setDataSharing(internationalStringToDo(source.getDataSharing(), target.getDataSharing(), ServiceExceptionParameters.CONFIGURATION_DATA_SHARING));
        target.setConfPolicy(internationalStringToDo(source.getConfPolicy(), target.getConfPolicy(), ServiceExceptionParameters.CONFIGURATION_CONF_POLICY));
        target.setConfDataTreatment(internationalStringToDo(source.getConfDataTreatment(), target.getConfDataTreatment(), ServiceExceptionParameters.CONFIGURATION_CONF_DATA_TREATMENT));
        target.setContact(externalItemDtoToDo(source.getContact(), target.getContact(), ServiceExceptionParameters.CONFIGURATION_CONTACT));

        target.setExternallyPublished(source.isExternallyPublished());
        target.setStatus(source.getStatus());

        // Optimistic locking: Update "update date" attribute to force update of the root entity in order to increase attribute "version"
        target.setUpdateDate(new DateTime());

        return target;
    }

    private ExternalItem externalItemDtoToDo(ExternalItemDto source, ExternalItem target, String metadataName) throws MetamacException {
        target = externalItemWithoutUrlDtoToEntity(source, target, metadataName);

        if (target != null) {
            if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(source.getType())) {
                target = commonMetadataExternalItemDtoToDo(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(source.getType())) {
                target = srmExternalItemDtoToDo(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(source.getType())) {
                target = statisticalOperationsExternalItemDtoToDo(source, target);
            } else {
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Type of externalItem not defined for externalItemDtoToEntity: " + source.getType());
            }
        }

        return target;
    }

    private ExternalItem externalItemWithoutUrlDtoToEntity(ExternalItemDto source, ExternalItem target, String metadataName) throws MetamacException {
        if (source == null) {
            if (target != null) {
                // delete previous entity
                externalItemRepository.delete(target);
            }
            return null;
        }

        if (target == null) {
            target = new ExternalItem();
        }
        target.setCode(source.getCode());
        target.setCodeNested(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setType(source.getType());
        target.setTitle(internationalStringToDo(source.getTitle(), target.getTitle(), metadataName));
        return target;
    }

    private ExternalItem commonMetadataExternalItemDtoToDo(ExternalItemDto source, ExternalItem target) throws MetamacException {
        target.setUri(commonMetadataExternalApiUrlDtoToDo(source.getUri()));
        target.setManagementAppUrl(commonMetadataInternalWebAppUrlDtoToDo(source.getManagementAppUrl()));
        return target;
    }

    private ExternalItem srmExternalItemDtoToDo(ExternalItemDto source, ExternalItem target) throws MetamacException {
        target.setUri(srmInternalApiUrlDtoToDo(source.getUri()));
        target.setManagementAppUrl(srmInternalWebAppUrlDtoToDo(source.getManagementAppUrl()));
        return target;
    }

    private ExternalItem statisticalOperationsExternalItemDtoToDo(ExternalItemDto source, ExternalItem target) throws MetamacException {
        target.setUri(statisticalOperationsInternalApiUrlDtoToDo(source.getUri()));
        target.setManagementAppUrl(statisticalOperationsInternalWebAppUrlDtoToDo(source.getManagementAppUrl()));
        return target;
    }

    private InternationalString internationalStringToDo(InternationalStringDto source, InternationalString target, String metadataName) throws MetamacException {
        // Preprocess international string
        internationalStringHtmlToTextPlain(source);

        // Check it is valid
        checkInternationalStringDtoValid(source, metadataName);

        // Transform
        if (source == null) {
            if (target != null) {
                // delete previous entity
                internationalStringRepository.delete(target);
            }
            return null;
        }

        if (CommonMetadataValidationUtils.isEmpty(source)) {
            throw new MetamacException(ServiceExceptionType.METADATA_REQUIRED, metadataName);
        }

        if (target == null) {
            target = new InternationalString();
        }

        Set<LocalisedString> localisedStringEntities = localisedStringDtoToDo(source.getTexts(), target.getTexts(), target);
        target.getTexts().clear();
        target.getTexts().addAll(localisedStringEntities);

        return target;
    }

    /**
     * Transform LocalisedString, reusing existing locales
     * 
     * @throws MetamacException
     */
    private Set<LocalisedString> localisedStringDtoToDo(Set<LocalisedStringDto> sources, Set<LocalisedString> targets, InternationalString internationalStringTarget) throws MetamacException {

        Set<LocalisedString> targetsBefore = targets;
        targets = new HashSet<LocalisedString>();

        for (LocalisedStringDto source : sources) {
            boolean existsBefore = false;
            for (LocalisedString target : targetsBefore) {
                if (source.getLocale().equals(target.getLocale())) {
                    targets.add(localisedStringDtoToDo(source, target, internationalStringTarget));
                    existsBefore = true;
                    break;
                }
            }
            if (!existsBefore) {
                targets.add(localisedStringDtoToDo(source, internationalStringTarget));
            }
        }

        return targets;
    }

    private LocalisedString localisedStringDtoToDo(LocalisedStringDto source, InternationalString internationalStringTarget) {
        LocalisedString target = new LocalisedString();
        localisedStringDtoToDo(source, target, internationalStringTarget);
        return target;
    }

    private LocalisedString localisedStringDtoToDo(LocalisedStringDto source, LocalisedString target, InternationalString internationalStringTarget) {
        target.setLabel(source.getLabel());
        target.setLocale(source.getLocale());
        target.setIsUnmodifiable(source.getIsUnmodifiable());
        target.setInternationalString(internationalStringTarget);
        return target;
    }

}
