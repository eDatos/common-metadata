package org.siemac.metamac.common.metadata.service.dto;

import org.dozer.DozerBeanMapper;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.base.serviceapi.CommonMetadataBaseService;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.springframework.beans.factory.annotation.Autowired;


public class Dto2DoMapperImpl implements Dto2DoMapper {

	@Autowired
	private DozerBeanMapper mapper;
	
	@Autowired
	private CommonMetadataBaseService commonMetadataBaseService;
	
	
	@Override
	public Configuration configurationDtoToEntity(ConfigurationDto source, ServiceContext ctx) throws CommonMetadataException {
		if (source == null) {
			return null;
		}
		Configuration target = commonMetadataBaseService.findConfigurationById(ctx, source.getId());
		return target;
	}


}
