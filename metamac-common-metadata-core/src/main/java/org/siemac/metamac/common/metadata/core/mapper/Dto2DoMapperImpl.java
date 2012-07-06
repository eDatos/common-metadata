package org.siemac.metamac.common.metadata.core.mapper;

import java.util.HashSet;
import java.util.Set;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionParameters;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataService;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.ExternalItemRepository;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.InternationalStringRepository;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.core.common.util.OptimisticLockingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Dto2DoMapperImpl implements Dto2DoMapper {

    @Autowired
    private CommonMetadataService         commonMetadataService;

    @Autowired
    private InternationalStringRepository internationalStringRepository;
    
    @Autowired
    private ExternalItemRepository externalItemRepository;

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
        }

        configurationDtoToDo(source, target);
        return target;
    }

    private Configuration configurationDtoToDo(ConfigurationDto source, Configuration target) throws MetamacException {
        if (target == null) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.CONFIGURATION);
        }

        target.setCode(source.getCode());
        target.setLegalActs(internationalStringToDo(source.getLegalActs(), target.getLegalActs(), ServiceExceptionParameters.CONFIGURATION_LEGAL_ACTS));
        target.setDataSharing(internationalStringToDo(source.getDataSharing(), target.getDataSharing(), ServiceExceptionParameters.CONFIGURATION_DATA_SHARING));
        target.setConfPolicy(internationalStringToDo(source.getConfPolicy(), target.getConfPolicy(), ServiceExceptionParameters.CONFIGURATION_CONF_POLICY));
        target.setConfDataTreatment(internationalStringToDo(source.getConfDataTreatment(), target.getConfDataTreatment(), ServiceExceptionParameters.CONFIGURATION_CONF_DATA_TREATMENT));
        target.setContact(externalItemDtoToDo(source.getContact(), target.getContact(), ServiceExceptionParameters.CONFIGURATION_CONTACT));
        
        target.setStatus(source.getStatus());

        // Optimistic locking: Update "update date" attribute to force update of the root entity in order to increase attribute "version"
        target.setUpdateDate(new DateTime());

        return target;
    }
    
    private ExternalItem externalItemDtoToDo(ExternalItemDto source, ExternalItem target, String metadataName) throws MetamacException {
        if (source == null) {
            if (target != null) {
                // delete previous entity
                externalItemRepository.delete(target);
            }
            return null;
        }
        
        if (target == null) {
            target = new ExternalItem(source.getUri(), source.getUrn(), source.getType(), internationalStringToDo(source.getTitle(), null, metadataName), source.getManagementAppUrl());
        } else {
            target.setUri(source.getUri());
            target.setUrn(source.getUrn());
            target.setType(source.getType());
            target.setManagementAppUrl(source.getManagementAppUrl());
            target.setTitle(internationalStringToDo(source.getTitle(), target.getTitle(), metadataName));
        }
        
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
