package org.siemac.metamac.common.metadata.web.shared;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class UpdateConfigurationsStatus {

    @In(1)
    List<Long>               configuationIds;

    @In(2)
    CommonMetadataStatusEnum statusEnum;

    @Out(1)
    List<ConfigurationDto>   configurationDtos;

    @Out(2)
    MetamacWebException      notificationException;

}
