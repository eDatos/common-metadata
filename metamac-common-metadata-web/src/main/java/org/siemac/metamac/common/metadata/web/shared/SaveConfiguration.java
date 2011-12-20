package org.siemac.metamac.common.metadata.web.shared;

import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SaveConfiguration {

	@In(1)
	ConfigurationDto configurationDto;
	
	@Out(1)
	ConfigurationDto configurationSaved;
	
}
