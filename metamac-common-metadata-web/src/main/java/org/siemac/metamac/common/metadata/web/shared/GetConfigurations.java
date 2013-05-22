package org.siemac.metamac.common.metadata.web.shared;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetConfigurations {

    @Out(1)
    List<ConfigurationDto> configurations;
}
