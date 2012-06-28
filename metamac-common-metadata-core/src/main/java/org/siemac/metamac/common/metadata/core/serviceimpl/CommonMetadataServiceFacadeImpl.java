package org.siemac.metamac.common.metadata.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.mapper.Do2DtoMapper;
import org.siemac.metamac.common.metadata.core.mapper.Dto2DoMapper;
import org.siemac.metamac.common.metadata.core.security.SecurityUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.domain.common.metadata.enume.domain.CommonMetadataRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of CommonMetadataServiceFacade.
 */
@Service("commonMetadataServiceFacade")
public class CommonMetadataServiceFacadeImpl extends CommonMetadataServiceFacadeImplBase {

    @Autowired
    private Do2DtoMapper do2DtoMapper;

    @Autowired
    private Dto2DoMapper dto2DoMapper;

    public CommonMetadataServiceFacadeImpl() {
    }

    public ConfigurationDto findConfigurationById(ServiceContext ctx, Long id) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Configuration configuration = getCommonMetadataService().findConfigurationById(ctx, id);

        // Transform to Dto
        ConfigurationDto configurationDto = do2DtoMapper.configurationDoToDto(configuration);

        return configurationDto;
    }

    public List<ConfigurationDto> findAllConfigurations(ServiceContext ctx) throws MetamacException {

        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        List<Configuration> configurations = getCommonMetadataService().findAllConfigurations(ctx);

        // Transform to Dto
        List<ConfigurationDto> configurationDtos = configurationsListDo2Dto(configurations);

        return configurationDtos;
    }

    public ConfigurationDto createConfiguration(ServiceContext ctx, ConfigurationDto configurationDto) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.JEFE_NORMALIZACION);

        // Transform to Entity
        Configuration configuration = dto2DoMapper.configurationDtoToDo(ctx, configurationDto);

        // Service call
        configuration = getCommonMetadataService().createConfiguration(ctx, configuration);

        // Transform to Dto
        configurationDto = do2DtoMapper.configurationDoToDto(configuration);

        // Return
        return configurationDto;
    }

    public ConfigurationDto updateConfiguration(ServiceContext ctx, ConfigurationDto configurationDto) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.JEFE_NORMALIZACION);

        // Transform to Entity
        Configuration configuration = dto2DoMapper.configurationDtoToDo(ctx, configurationDto);

        // Service call
        configuration = getCommonMetadataService().updateConfiguration(ctx, configuration);

        // Transform to Dto
        configurationDto = do2DtoMapper.configurationDoToDto(configuration);

        // Return
        return configurationDto;
    }

    public void deleteConfiguration(ServiceContext ctx, Long configurationId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.JEFE_NORMALIZACION);
        
        // Service call
        getCommonMetadataService().deleteConfiguration(ctx, configurationId);
    }

    // --------------------------------------------------------------------------------
    // TRANSFORM LISTS
    // --------------------------------------------------------------------------------

    private List<ConfigurationDto> configurationsListDo2Dto(List<Configuration> configurations) {
        List<ConfigurationDto> configurationsDto = new ArrayList<ConfigurationDto>();
        for (Configuration configuration : configurations) {
            configurationsDto.add(do2DtoMapper.configurationDoToDto(configuration));
        }
        return configurationsDto;
    }

}
