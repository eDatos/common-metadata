package org.siemac.metamac.common.metadata.service.dto;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.base.serviceapi.CommonMetadataBaseService;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.core.common.dto.serviceapi.ExternalItemBtDto;
import org.siemac.metamac.core.common.vo.domain.ExternalItem;

public interface Do2DtoMapper {

	public ConfigurationDto configurationEntityToDto(Configuration configuration) throws CommonMetadataException;
	
	public ExternalItemBtDto externalItemToExternalItemBtDto(ExternalItem externalItem, ServiceContext ctx, CommonMetadataBaseService commonMetadataBaseService);
	
}
