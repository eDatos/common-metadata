package org.siemac.metamac.common.metadata.web.shared;

import java.util.List;

import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

@GenDispatch(isSecure = false)
public class DeleteConfigurationList {

	@In(1)
	List<ConfigurationDto> configurationDtos;
	
}
