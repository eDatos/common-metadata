package org.siemac.metamac.common.metadata.core.mapper;

import org.dozer.MappingException;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataService;
import org.siemac.metamac.core.common.dto.serviceapi.ExternalItemBtDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.vo.domain.ExternalItem;
import org.siemac.metamac.domain.common.metadata.dto.serviceapi.ConfigurationDto;

public interface Dto2DoMapper {

    public Configuration configurationDtoToEntity(ConfigurationDto source, ServiceContext ctx) throws MetamacException;
    public Configuration configurationDtoToEntity(ConfigurationDto source, Configuration target, ServiceContext ctx) throws MetamacException;

    public ExternalItem externalItemBtDtoToExternalItem(ExternalItemBtDto externalItemBtDto, ServiceContext ctx, CommonMetadataService commonMetadataService) throws MappingException;

}
