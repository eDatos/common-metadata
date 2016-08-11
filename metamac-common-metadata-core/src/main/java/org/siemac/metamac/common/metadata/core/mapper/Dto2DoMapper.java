package org.siemac.metamac.common.metadata.core.mapper;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.DataConfiguration;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDto2DoMapper;

public interface Dto2DoMapper extends BaseDto2DoMapper {

    Configuration configurationDtoToDo(ServiceContext ctx, ConfigurationDto source) throws MetamacException;

    DataConfiguration dataConfigurationDtoToDo(ServiceContext ctx, DataConfigurationDto source) throws MetamacException;

}
