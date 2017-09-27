package org.siemac.metamac.common.metadata.core.mapper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.DataConfiguration;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDo2DtoMapperImpl;
import org.springframework.stereotype.Component;

@Component
public class Do2DtoMapperImpl extends BaseDo2DtoMapperImpl implements Do2DtoMapper {

    // ------------------------------------------------------------------------------------
    // CONFIGURATION (metadata)
    // ------------------------------------------------------------------------------------

    @Override
    public ConfigurationDto configurationDoToDto(Configuration source) throws MetamacException {
        ConfigurationDto target = new ConfigurationDto();

        target.setId(source.getId());
        target.setVersion(source.getVersion());

        target.setCreatedDate(dateDoToDto(source.getCreatedDate()));
        target.setCreatedBy(source.getCreatedBy());
        target.setLastUpdated(dateDoToDto(source.getLastUpdated()));
        target.setLastUpdatedBy(source.getLastUpdatedBy());

        target.setCode(source.getCode());
        target.setUrn(source.getUrn());

        target.setLegalActs(internationalStringToDto(source.getLegalActs()));
        target.setDataSharing(internationalStringToDto(source.getDataSharing()));
        target.setConfPolicy(internationalStringToDto(source.getConfPolicy()));
        target.setConfDataTreatment(internationalStringToDto(source.getConfDataTreatment()));
        target.setLicense(internationalStringToDto(source.getLicense()));

        target.setExternallyPublished(source.isExternallyPublished());
        target.setStatus(source.getStatus());

        if (source.getContact() != null) {
            target.setContact(externalItemToDto(source.getContact()));
        }

        target.setOptimisticLockingVersion(source.getVersion());

        return target;
    }

    // ------------------------------------------------------------------------------------
    // DATA CONFIGURATION
    // ------------------------------------------------------------------------------------

    @Override
    public DataConfigurationDto dataConfigurationDoToDto(DataConfiguration source) throws MetamacException {
        DataConfigurationDto target = new DataConfigurationDto();

        target.setId(source.getId());
        target.setVersion(source.getVersion());

        target.setCreatedDate(dateDoToDto(source.getCreatedDate()));
        target.setCreatedBy(source.getCreatedBy());
        target.setLastUpdated(dateDoToDto(source.getLastUpdated()));
        target.setLastUpdatedBy(source.getLastUpdatedBy());

        target.setConfigurationKey(source.getConfigurationKey());
        target.setConfigurationValue(source.getConfigurationValue());
        target.setSystemProperty(source.isSystemProperty());
        target.setExternallyPublished(source.isExternallyPublished());

        target.setOptimisticLockingVersion(source.getVersion());

        return target;
    }

    // ------------------------------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // ------------------------------------------------------------------------------------

    private InternationalStringDto internationalStringToDto(InternationalString source) {
        if (source == null) {
            return null;
        }
        InternationalStringDto target = new InternationalStringDto();
        target.getTexts().addAll(localisedStringDoToDto(source.getTexts()));
        return target;
    }

    private Set<LocalisedStringDto> localisedStringDoToDto(Set<LocalisedString> sources) {
        Set<LocalisedStringDto> targets = new HashSet<LocalisedStringDto>();
        for (LocalisedString source : sources) {
            LocalisedStringDto target = new LocalisedStringDto();
            target.setLabel(source.getLabel());
            target.setLocale(source.getLocale());
            target.setIsUnmodifiable(source.getIsUnmodifiable());
            targets.add(target);
        }
        return targets;
    }

    // ------------------------------------------------------------------------------------
    // EXTERNAL ITEMS
    // ------------------------------------------------------------------------------------

    private ExternalItemDto externalItemToDto(ExternalItem source) throws MetamacException {
        ExternalItemDto target = externalItemWithoutUrlsToDto(source);
        if (target != null) {
            if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(source.getType())) {
                target = commonMetadataExternalItemDoToDto(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(source.getType())) {
                target = srmExternalItemDoToDto(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(source.getType())) {
                target = statisticalOperationsExternalItemDoToDto(source, target);
            } else {
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Type of externalItem not defined for externalItemDoToDto: " + source.getType());
            }
        }

        return target;
    }

    private ExternalItemDto externalItemWithoutUrlsToDto(ExternalItem source) {
        if (source == null) {
            return null;
        }
        ExternalItemDto target = new ExternalItemDto();
        target.setId(source.getId());
        target.setCode(source.getCode());
        target.setCodeNested(source.getCodeNested());
        target.setUri(source.getUri());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setType(source.getType());
        target.setManagementAppUrl(source.getManagementAppUrl());
        target.setTitle(internationalStringToDto(source.getTitle()));
        return target;
    }

    private ExternalItemDto commonMetadataExternalItemDoToDto(ExternalItem source, ExternalItemDto target) throws MetamacException {
        target.setUri(commonMetadataExternalApiUrlDoToDto(source.getUri()));
        target.setManagementAppUrl(commonMetadataInternalWebAppUrlDoToDto(source.getManagementAppUrl()));
        return target;
    }

    private ExternalItemDto srmExternalItemDoToDto(ExternalItem source, ExternalItemDto target) throws MetamacException {
        target.setUri(srmInternalApiUrlDoToDto(source.getUri()));
        target.setManagementAppUrl(srmInternalWebAppUrlDoToDto(source.getManagementAppUrl()));
        return target;
    }

    private ExternalItemDto statisticalOperationsExternalItemDoToDto(ExternalItem source, ExternalItemDto target) throws MetamacException {
        target.setUri(statisticalOperationsInternalApiUrlDoToDto(source.getUri()));
        target.setManagementAppUrl(statisticalOperationsInternalWebAppUrlDoToDto(source.getManagementAppUrl()));
        return target;
    }

    private Date dateDoToDto(DateTime source) {
        if (source == null) {
            return null;
        }
        return source.toDate();
    }

}
