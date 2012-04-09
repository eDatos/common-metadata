package org.siemac.metamac.common.metadata.core.mapper;

import java.util.HashSet;
import java.util.Set;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataService;
import org.siemac.metamac.core.common.dto.serviceapi.InternationalStringDto;
import org.siemac.metamac.core.common.dto.serviceapi.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.InternationalStringRepository;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.domain.common.metadata.dto.serviceapi.ConfigurationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Dto2DoMapperImpl implements Dto2DoMapper {
    
    @Autowired
    private CommonMetadataService commonMetadataService;
    
    @Autowired
    private InternationalStringRepository internationalStringRepository;
    

    @Override
    public Configuration configurationDtoToDo(ServiceContext ctx, ConfigurationDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity
        Configuration target = new Configuration();
        if (source.getId() != null) {
            target = commonMetadataService.findConfigurationById(ctx, source.getId());
        }

        configurationDtoToDo(source, target);
        return target;
    }
    
    
    private Configuration configurationDtoToDo(ConfigurationDto source, Configuration target) throws MetamacException {
        if (target == null) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, "CONFIGURATION");
        }

        target.setCode(source.getCode());
        target.setLegalActsUrl(source.getLegalActsUrl());
        target.setDataSharingUrl(source.getDataSharingUrl());
        target.setConfPolicyUrl(source.getConfPolicyUrl());
        target.setConfDataTreatmentUrl(source.getConfDataTreatmentUrl());
        target.setLegalActsUrl(source.getLegalActsUrl());
        target.setLegalActs(internationalStringToDo(source.getLegalActs(), target.getLegalActs(), "LEGAL_ACTS"));
        target.setDataSharing(internationalStringToDo(source.getDataSharing(), target.getDataSharing(), "DATA_SHARING"));
        target.setLegalActs(internationalStringToDo(source.getLegalActs(), target.getLegalActs(), "LEGAL_ACTS"));
        target.setConfPolicy(internationalStringToDo(source.getConfPolicy(), target.getConfPolicy(), "CONF_POLICY"));
        target.setConfDataTreatment(internationalStringToDo(source.getConfDataTreatment(), target.getConfDataTreatment(), "CONF_DATA_TREATMENT"));
        
        return target;
    }
    
    
    private InternationalString internationalStringToDo(InternationalStringDto source, InternationalString target, String metadataName) throws MetamacException {
        if (source == null) {
            if (target != null) {
                // delete previous entity
                internationalStringRepository.delete(target);
            }
            return null;
        }

        if (target == null) {
            target = new InternationalString();
        }

        if (ValidationUtils.isEmpty(source)) {
            throw new MetamacException(ServiceExceptionType.METADATA_REQUIRED, metadataName);
        }

        Set<LocalisedString> localisedStringEntities = localisedStringDtoToDo(source.getTexts(), target.getTexts());
        target.getTexts().clear();
        target.getTexts().addAll(localisedStringEntities);

        return target;
    }

    /**
     * Transform LocalisedString, reusing existing locales
     */
    private Set<LocalisedString> localisedStringDtoToDo(Set<LocalisedStringDto> sources, Set<LocalisedString> targets) {

        Set<LocalisedString> targetsBefore = targets;
        targets = new HashSet<LocalisedString>();

        for (LocalisedStringDto source : sources) {
            boolean existsBefore = false;
            for (LocalisedString target : targetsBefore) {
                if (source.getLocale().equals(target.getLocale())) {
                    targets.add(localisedStringDtoToDo(source, target));
                    existsBefore = true;
                    break;
                }
            }
            if (!existsBefore) {
                targets.add(localisedStringDtoToDo(source));
            }
        }
        return targets;
    }

    private LocalisedString localisedStringDtoToDo(LocalisedStringDto source) {
        LocalisedString target = new LocalisedString();
        target.setLabel(source.getLabel());
        target.setLocale(source.getLocale());
        return target;
    }

    private LocalisedString localisedStringDtoToDo(LocalisedStringDto source, LocalisedString target) {
        target.setLabel(source.getLabel());
        target.setLocale(source.getLocale());
        return target;
    }

}
