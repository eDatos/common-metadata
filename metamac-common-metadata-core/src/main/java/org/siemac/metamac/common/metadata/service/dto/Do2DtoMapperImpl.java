package org.siemac.metamac.common.metadata.service.dto;

import org.dozer.DozerBeanMapper;
import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.core.common.dto.serviceapi.InternationalStringDto;
import org.siemac.metamac.core.common.dto.serviceapi.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.springframework.beans.factory.annotation.Autowired;

public class Do2DtoMapperImpl implements Do2DtoMapper {
	
	@Autowired
	private DozerBeanMapper mapper;
	

	protected DozerBeanMapper getMapper() {
		return mapper;
	}

	@Override
	public ConfigurationDto configurationEntityToDto(Configuration configuration) {
		if (configuration == null) {
			return null;
		}
		ConfigurationDto configurationDto = getMapper().map(configuration, ConfigurationDto.class);
		configurationDto.setLegalActs(internationalStringToDto(configuration.getLegalActs()));
		configurationDto.setDataSharing(internationalStringToDto(configuration.getDataSharing()));
		configurationDto.setConfidentialityPolicy(internationalStringToDto(configuration.getConfidentialityPolicy()));
		configurationDto.setConfidentialityDataTreatment(internationalStringToDto(configuration.getConfidentialityDataTreatment()));
		return configurationDto;
	}

	
	/**
	 * {@link InternationalString} to {@link InternationalStringDto}
	 * 
	 * @param name
	 * @return
	 */
	private InternationalStringDto internationalStringToDto(InternationalString internationalString) {
		if (internationalString == null) {
			return null;
		}
		
    	// InternationalString to InternationalString  Dto
    	InternationalStringDto internationalStringDto = getMapper().map(internationalString, InternationalStringDto.class);
    	
    	// LocalisedStringDto to LocalisedString  
    	for (LocalisedString item : internationalString.getTexts()) {
    		internationalStringDto.addText(getMapper().map(item, LocalisedStringDto.class));
    	}
    	
    	return internationalStringDto;
	}

}
