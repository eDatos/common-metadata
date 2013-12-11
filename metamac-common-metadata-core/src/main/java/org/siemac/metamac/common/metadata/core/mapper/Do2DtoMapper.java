package org.siemac.metamac.common.metadata.core.mapper;

import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.DataConfiguration;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDo2DtoMapper;

public interface Do2DtoMapper extends BaseDo2DtoMapper {

    public ConfigurationDto configurationDoToDto(Configuration configuration) throws MetamacException;

    public DataConfigurationDto dataConfigurationDoToDto(DataConfiguration dataConfiguration) throws MetamacException;

}
