package org.siemac.metamac.common.metadata.core.mapper;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataService;
import org.siemac.metamac.domain.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.core.common.dto.serviceapi.ExternalItemBtDto;
import org.siemac.metamac.core.common.vo.domain.ExternalItem;

public interface Do2DtoMapper {

    public ConfigurationDto configurationEntityToDto(Configuration configuration);

    public ExternalItemBtDto externalItemToExternalItemBtDto(ExternalItem externalItem, ServiceContext ctx, CommonMetadataService commonMetadataService);

}
