package org.siemac.metamac.common.metadata.web.shared;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SaveAppConfiguration {

    @In(1)
    DataConfigurationDto propertyToSave;

    @Out(1)
    DataConfigurationDto propertySaved;

}
