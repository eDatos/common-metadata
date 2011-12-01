package org.siemac.metamac.common.metadata.service.dto;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class Do2DtoMapperImpl implements Do2DtoMapper {
	
	@Autowired
	private DozerBeanMapper mapper;
	
	
	/**************************************************************************
	 *  GETTERS
	 **************************************************************************/
	protected DozerBeanMapper getMapper() {
		return mapper;
	}


}
