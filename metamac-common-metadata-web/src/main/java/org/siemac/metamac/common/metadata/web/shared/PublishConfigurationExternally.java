package org.siemac.metamac.common.metadata.web.shared;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class PublishConfigurationExternally {

    @In(1)
    Long                id;

    @Out(1)
    ConfigurationDto    configurationDto;

    @Out(2)
    MetamacWebException notificationException;
}
