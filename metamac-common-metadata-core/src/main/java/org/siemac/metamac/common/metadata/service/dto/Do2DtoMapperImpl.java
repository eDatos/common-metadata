package org.siemac.metamac.common.metadata.service.dto;

import org.dozer.DozerBeanMapper;
import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
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
		
		return configurationDto;
	}

}
