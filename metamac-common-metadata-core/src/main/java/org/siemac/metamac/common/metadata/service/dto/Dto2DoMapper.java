package org.siemac.metamac.common.metadata.service.dto;

import org.dozer.MappingException;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.siemac.metamac.common.metadata.base.serviceapi.CommonMetadataBaseService;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.core.common.dto.serviceapi.ExternalItemBtDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.vo.domain.ExternalItem;

public interface Dto2DoMapper {
	
	public Configuration configurationDtoToEntity(ConfigurationDto source, ServiceContext ctx) throws MetamacException;
	public Configuration configurationDtoToEntity(ConfigurationDto source, Configuration target, ServiceContext ctx) throws MetamacException;
	
	public ExternalItem externalItemBtDtoToExternalItem(ExternalItemBtDto externalItemBtDto, ServiceContext ctx, CommonMetadataBaseService commonMetadataBaseService) throws MappingException;
	
}
