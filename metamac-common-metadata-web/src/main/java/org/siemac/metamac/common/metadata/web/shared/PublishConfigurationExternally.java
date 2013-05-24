package org.siemac.metamac.common.metadata.web.shared;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class PublishConfigurationExternally {

    @In(1)
    String           urn;

    @Out(1)
    ConfigurationDto configurationDto;
}
