package org.siemac.metamac.common.metadata.core.mapper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.springframework.stereotype.Component;

@Component
public class Do2DtoMapperImpl implements Do2DtoMapper {

    @Override
    public ConfigurationDto configurationDoToDto(Configuration source) {
        ConfigurationDto target = new ConfigurationDto();

        target.setId(source.getId());
        target.setUuid(source.getUuid());
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
        
        target.setStatus(source.getStatus());

        if (source.getContact() != null) {
            target.setContact(externalItemToDto(source.getContact()));
        }
        
        target.setOptimisticLockingVersion(source.getVersion());

        return target;
    }

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

    private ExternalItemDto externalItemToDto(ExternalItem source) {
        if (source == null) {
            return null;
        }

        ExternalItemDto target = new ExternalItemDto(source.getCode(), source.getUri(), source.getUrn(), source.getType(), internationalStringToDto(source.getTitle()), source.getManagementAppUrl()); 

        return target;
    }

    private Date dateDoToDto(DateTime source) {
        if (source == null) {
            return null;
        }
        return source.toDate();
    }

}
