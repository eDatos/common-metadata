package org.siemac.metamac.common.metadata.core.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.DataConfiguration;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataRoleEnum;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.core.mapper.Do2DtoMapper;
import org.siemac.metamac.common.metadata.core.mapper.Dto2DoMapper;
import org.siemac.metamac.common.metadata.core.security.SecurityUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
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

    // ------------------------------------------------------------------------------------
    // CONFIGURATIONS (metadata)
    // ------------------------------------------------------------------------------------

    @Override
    public ConfigurationDto findConfigurationById(ServiceContext ctx, Long id) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Configuration configuration = getCommonMetadataService().findConfigurationById(ctx, id);

        // Transform to Dto
        ConfigurationDto configurationDto = do2DtoMapper.configurationDoToDto(configuration);

        return configurationDto;
    }

    @Override
    public ConfigurationDto findConfigurationByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Configuration configuration = getCommonMetadataService().findConfigurationByUrn(ctx, urn);

        // Transform to Dto
        ConfigurationDto configurationDto = do2DtoMapper.configurationDoToDto(configuration);

        return configurationDto;
    }

    @Override
    public List<ConfigurationDto> findAllConfigurations(ServiceContext ctx) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        List<Configuration> configurations = getCommonMetadataService().findAllConfigurations(ctx);

        // Transform to Dto
        List<ConfigurationDto> configurationDtos = configurationsListDo2Dto(configurations);

        return configurationDtos;
    }

    @Override
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

    @Override
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

    @Override
    public void deleteConfiguration(ServiceContext ctx, Long configurationId) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.JEFE_NORMALIZACION);

        // Service call
        getCommonMetadataService().deleteConfiguration(ctx, configurationId);
    }

    @Override
    public List<ConfigurationDto> updateConfigurationsStatus(ServiceContext ctx, List<Long> configurationIds, CommonMetadataStatusEnum status) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.JEFE_NORMALIZACION);

        // Service call
        List<Configuration> configurations = getCommonMetadataService().updateConfigurationsStatus(ctx, configurationIds, status);

        // Transform to Dto
        List<ConfigurationDto> configurationDtos = configurationsListDo2Dto(configurations);

        return configurationDtos;

    }

    @Override
    public ConfigurationDto publishExternallyConfiguration(ServiceContext ctx, Long id) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.JEFE_NORMALIZACION);

        // Service call
        Configuration configuration = getCommonMetadataService().findConfigurationById(ctx, id);
        configuration.setExternallyPublished(true);
        configuration = getCommonMetadataService().updateConfiguration(ctx, configuration);

        // Transform to Dto
        ConfigurationDto configurationDto = do2DtoMapper.configurationDoToDto(configuration);

        return configurationDto;
    }

    // ------------------------------------------------------------------------------------
    // DATA CONFIGURATIONS
    // ------------------------------------------------------------------------------------

    @Override
    public DataConfigurationDto findDataConfigurationById(ServiceContext ctx, Long id) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        DataConfiguration dataConfiguration = getCommonMetadataService().findDataConfigurationById(ctx, id);

        // Transform to Dto
        DataConfigurationDto configurationDto = do2DtoMapper.dataConfigurationDoToDto(dataConfiguration);

        return configurationDto;
    }

    @Override
    public DataConfigurationDto findDataConfigurationByConfigurationKey(ServiceContext ctx, String configurationKey) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        DataConfiguration dataConfiguration = getCommonMetadataService().findDataConfigurationByConfigurationKey(ctx, configurationKey);

        // Transform to Dto
        DataConfigurationDto configurationDto = do2DtoMapper.dataConfigurationDoToDto(dataConfiguration);

        return configurationDto;
    }

    @Override
    public List<DataConfigurationDto> findDataConfigurationsOfSystemProperties(ServiceContext ctx) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.JEFE_NORMALIZACION);

        // Service call
        List<DataConfiguration> configurations = getCommonMetadataService().findDataConfigurationsOfSystemProperties(ctx);

        // Transform to Dto
        List<DataConfigurationDto> configurationDtos = dataConfigurationsListDo2Dto(configurations);

        return configurationDtos;
    }

    @Override
    public List<DataConfigurationDto> findDataConfigurationsOfDefaultValues(ServiceContext ctx) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        List<DataConfiguration> configurations = getCommonMetadataService().findDataConfigurationsOfDefaultValues(ctx);

        // Transform to Dto
        List<DataConfigurationDto> configurationDtos = dataConfigurationsListDo2Dto(configurations);

        return configurationDtos;
    }

    @Override
    public DataConfigurationDto updateDataConfiguration(ServiceContext ctx, DataConfigurationDto dataConfigurationDto) throws MetamacException {
        // Security
        if (dataConfigurationDto.isSystemProperty()) {
            SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ADMINISTRADOR);
        } else {
            SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.JEFE_NORMALIZACION);
        }

        // Transform to Entity
        DataConfiguration dataConfiguration = dto2DoMapper.dataConfigurationDtoToDo(ctx, dataConfigurationDto);

        // Service call
        dataConfiguration = getCommonMetadataService().updateDataConfiguration(ctx, dataConfiguration);

        // Transform to Dto
        dataConfigurationDto = do2DtoMapper.dataConfigurationDoToDto(dataConfiguration);

        // Return
        return dataConfigurationDto;
    }

    // --------------------------------------------------------------------------------
    // TRANSFORM LISTS
    // --------------------------------------------------------------------------------

    private List<ConfigurationDto> configurationsListDo2Dto(List<Configuration> configurations) throws MetamacException {
        List<ConfigurationDto> configurationsDto = new ArrayList<ConfigurationDto>();
        for (Configuration configuration : configurations) {
            configurationsDto.add(do2DtoMapper.configurationDoToDto(configuration));
        }
        return configurationsDto;
    }

    private List<DataConfigurationDto> dataConfigurationsListDo2Dto(List<DataConfiguration> dataConfigurations) throws MetamacException {
        List<DataConfigurationDto> dataConfigurationsDto = new ArrayList<DataConfigurationDto>();
        for (DataConfiguration dataConfiguration : dataConfigurations) {
            dataConfigurationsDto.add(do2DtoMapper.dataConfigurationDoToDto(dataConfiguration));
        }
        return dataConfigurationsDto;
    }
}
