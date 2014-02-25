package org.siemac.metamac.common.metadata.web.shared;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.enums.AppsConfigurationsType;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetAppsConfigurations {

    @In(1)
    private AppsConfigurationsType     type;

    @Out(1)
    private List<DataConfigurationDto> properties;
}
