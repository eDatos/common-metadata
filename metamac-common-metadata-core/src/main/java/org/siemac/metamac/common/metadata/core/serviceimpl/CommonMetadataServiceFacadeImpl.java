package org.siemac.metamac.common.metadata.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.mapper.Do2DtoMapper;
import org.siemac.metamac.common.metadata.core.mapper.Dto2DoMapper;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.domain.common.metadata.dto.serviceapi.ConfigurationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of CommonMetadataServiceFacade.
 */
@Service("commonMetadataServiceFacade")
public class CommonMetadataServiceFacadeImpl extends CommonMetadataServiceFacadeImplBase {

    @Autowired
    private Do2DtoMapper              do2DtoMapper;

    @Autowired
    private Dto2DoMapper              dto2DoMapper;

    @Autowired
    private CommonMetadataService commonMetadataService;

    protected Do2DtoMapper getDo2DtoMapper() {
        return do2DtoMapper;
    }

    protected Dto2DoMapper getDto2DoMapper() {
        return dto2DoMapper;
    }

    protected CommonMetadataService getCommonMetadataService() {
        return commonMetadataService;
    }

    public CommonMetadataServiceFacadeImpl() {
    }

    public ConfigurationDto findConfigurationById(ServiceContext ctx, Long id) throws MetamacException {
        return getDo2DtoMapper().configurationEntityToDto(getCommonMetadataService().findConfigurationById(ctx, id));
    }

    public List<ConfigurationDto> findAllConfigurations(ServiceContext ctx) throws MetamacException {
        List<Configuration> configurations = getCommonMetadataService().findAllConfigurations(ctx);
        List<ConfigurationDto> configurationDtos = new ArrayList<ConfigurationDto>();
        for (Configuration configuration : configurations) {
            configurationDtos.add(getDo2DtoMapper().configurationEntityToDto(configuration));
        }
        return configurationDtos;
    }

    public ConfigurationDto saveConfiguration(ServiceContext ctx, ConfigurationDto configurationDto) throws MetamacException {
        Configuration configuration = getDto2DoMapper().configurationDtoToEntity(configurationDto, ctx);
        configuration = getCommonMetadataService().saveConfiguration(ctx, configuration);
        configurationDto = getDo2DtoMapper().configurationEntityToDto(configuration);
        return configurationDto;
    }

    public void deleteConfiguration(ServiceContext ctx, ConfigurationDto configurationDto) throws MetamacException {
        Configuration configuration = getDto2DoMapper().configurationDtoToEntity(configurationDto, ctx);
        getCommonMetadataService().deleteConfiguration(ctx, configuration);
    }

}
