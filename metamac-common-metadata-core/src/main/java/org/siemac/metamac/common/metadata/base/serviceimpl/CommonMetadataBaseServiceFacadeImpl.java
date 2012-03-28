package org.siemac.metamac.common.metadata.base.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.siemac.metamac.common.metadata.base.serviceapi.CommonMetadataBaseService;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.common.metadata.service.dto.Do2DtoMapper;
import org.siemac.metamac.common.metadata.service.dto.Dto2DoMapper;
import org.siemac.metamac.core.common.dto.serviceapi.ExternalItemBtDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.sw.ServicesResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of CommonMetadataBaseServiceFacade.
 */
@Service("commonMetadataBaseServiceFacade")
public class CommonMetadataBaseServiceFacadeImpl extends CommonMetadataBaseServiceFacadeImplBase {

    @Autowired
    private Do2DtoMapper              do2DtoMapper;

    @Autowired
    private Dto2DoMapper              dto2DoMapper;

    @Autowired
    private CommonMetadataBaseService commonMetadataBaseService;

    protected Do2DtoMapper getDo2DtoMapper() {
        return do2DtoMapper;
    }

    protected Dto2DoMapper getDto2DoMapper() {
        return dto2DoMapper;
    }

    protected CommonMetadataBaseService getCommonMetadataBaseService() {
        return commonMetadataBaseService;
    }

    public CommonMetadataBaseServiceFacadeImpl() {
    }

    public ConfigurationDto findConfigurationById(ServiceContext ctx, Long id) throws MetamacException {
        return getDo2DtoMapper().configurationEntityToDto(getCommonMetadataBaseService().findConfigurationById(ctx, id));
    }

    public List<ConfigurationDto> findAllConfigurations(ServiceContext ctx) throws MetamacException {
        List<Configuration> configurations = getCommonMetadataBaseService().findAllConfigurations(ctx);
        List<ConfigurationDto> configurationDtos = new ArrayList<ConfigurationDto>();
        for (Configuration configuration : configurations) {
            configurationDtos.add(getDo2DtoMapper().configurationEntityToDto(configuration));
        }
        return configurationDtos;
    }

    public ConfigurationDto saveConfiguration(ServiceContext ctx, ConfigurationDto configurationDto) throws MetamacException {
        Configuration configuration = getDto2DoMapper().configurationDtoToEntity(configurationDto, ctx);
        configuration = getCommonMetadataBaseService().saveConfiguration(ctx, configuration);
        configurationDto = getDo2DtoMapper().configurationEntityToDto(configuration);
        return configurationDto;
    }

    public void deleteConfiguration(ServiceContext ctx, ConfigurationDto configurationDto) throws MetamacException {
        Configuration configuration = getDto2DoMapper().configurationDtoToEntity(configurationDto, ctx);
        getCommonMetadataBaseService().deleteConfiguration(ctx, configuration);
    }

    @Override
    public List<ExternalItemBtDto> findAllOrganisationSchemes(ServiceContext ctx) throws MetamacException {
        return ServicesResolver.findAllOrganisationSchemes();
    }

}
