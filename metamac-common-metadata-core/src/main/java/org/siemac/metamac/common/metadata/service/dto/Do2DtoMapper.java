package org.siemac.metamac.common.metadata.service.dto;

import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;

public interface Do2DtoMapper {

	public ConfigurationDto configurationEntityToDto(Configuration configuration) throws CommonMetadataException;
	
}
