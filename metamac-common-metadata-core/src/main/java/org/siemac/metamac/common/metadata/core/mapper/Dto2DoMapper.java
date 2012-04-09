package org.siemac.metamac.common.metadata.core.mapper;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.domain.common.metadata.dto.serviceapi.ConfigurationDto;

public interface Dto2DoMapper {

    Configuration configurationDtoToDo(ServiceContext ctx, ConfigurationDto source) throws MetamacException;

}
