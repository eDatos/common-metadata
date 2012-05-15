package org.siemac.metamac.common.metadata.web.shared;

import org.siemac.metamac.domain.common.metadata.dto.ConfigurationDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SaveConfiguration {

    @In(1)
    ConfigurationDto configurationToSave;

    @Out(1)
    ConfigurationDto configurationSaved;

}
