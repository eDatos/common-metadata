package org.siemac.metamac.common.metadata.core.mapper;

import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.domain.common.metadata.dto.ConfigurationDto;

public interface Do2DtoMapper {

    public ConfigurationDto configurationDoToDto(Configuration configuration);

}
