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
import org.siemac.metamac.common.metadata.core.mapper.MetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.common.metadata.core.security.SecurityUtils;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.SculptorCriteria;
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
    
    @Autowired
    private MetamacCriteria2SculptorCriteriaMapper metamacCriteria2SculptorCriteriaMapper;

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
       return do2DtoMapper.configurationDoToDto(configuration);
    }

    @Override
    public ConfigurationDto findConfigurationByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        Configuration configuration = getCommonMetadataService().findConfigurationByUrn(ctx, urn);

        // Transform to Dto
        return do2DtoMapper.configurationDoToDto(configuration);
    }

    @Override
    public List<ConfigurationDto> findAllConfigurations(ServiceContext ctx) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        List<Configuration> configurations = getCommonMetadataService().findAllConfigurations(ctx);

        // Transform to Dto
        return configurationsListDo2Dto(configurations);
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
        return do2DtoMapper.configurationDoToDto(configuration);
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
        return do2DtoMapper.configurationDoToDto(configuration);
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
        return configurationsListDo2Dto(configurations);
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
        return do2DtoMapper.configurationDoToDto(configuration);
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
        return do2DtoMapper.dataConfigurationDoToDto(dataConfiguration);
    }

    @Override
    public DataConfigurationDto findDataConfigurationByConfigurationKey(ServiceContext ctx, String configurationKey) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        DataConfiguration dataConfiguration = getCommonMetadataService().findDataConfigurationByConfigurationKey(ctx, configurationKey);

        // Transform to Dto
        return do2DtoMapper.dataConfigurationDoToDto(dataConfiguration);
    }

    @Override
    @Deprecated
    public List<DataConfigurationDto> findDataConfigurationsOfSystemProperties(ServiceContext ctx) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.JEFE_NORMALIZACION);

        // Service call
        List<DataConfiguration> configurations = getCommonMetadataService().findDataConfigurationsOfSystemProperties(ctx);

        // Transform to Dto
        return dataConfigurationsListDo2Dto(configurations);
    }
    
    @Override
    public List<DataConfigurationDto> findDataConfigurationsOfSystemPropertiesByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.JEFE_NORMALIZACION);
        
        // Transform
        SculptorCriteria sculptorCriteria = metamacCriteria2SculptorCriteriaMapper.getDataConfigurationCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Service call
        List<DataConfiguration> configurations = getCommonMetadataService().findDataConfigurationsOfSystemPropertiesByCondition(ctx, sculptorCriteria.getConditions());

        // Transform to Dto
        return dataConfigurationsListDo2Dto(configurations);
    }
    
    @Override
    @Deprecated
    public List<DataConfigurationDto> findDataConfigurationsOfDefaultValues(ServiceContext ctx) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);

        // Service call
        List<DataConfiguration> configurations = getCommonMetadataService().findDataConfigurationsOfDefaultValues(ctx);

        // Transform to Dto
        return dataConfigurationsListDo2Dto(configurations);
    }

    @Override
    public List<DataConfigurationDto> findDataConfigurationsOfDefaultValuesByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        SecurityUtils.checkServiceOperationAllowed(ctx, CommonMetadataRoleEnum.ANY_ROLE_ALLOWED);
        
        // Transform
        SculptorCriteria sculptorCriteria = metamacCriteria2SculptorCriteriaMapper.getDataConfigurationCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Service call
        List<DataConfiguration> configurations = getCommonMetadataService().findDataConfigurationsOfDefaultValuesByCondition(ctx, sculptorCriteria.getConditions());

        // Transform to Dto
        return dataConfigurationsListDo2Dto(configurations);
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
        return do2DtoMapper.dataConfigurationDoToDto(dataConfiguration);
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
