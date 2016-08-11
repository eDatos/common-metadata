package org.siemac.metamac.common.metadata.core.serviceimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationRepository;
import org.siemac.metamac.common.metadata.core.domain.DataConfiguration;
import org.siemac.metamac.common.metadata.core.domain.DataConfigurationRepository;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.common.metadata.core.serviceimpl.utils.InvocationValidator;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of CommonMetadataService.
 */
@Service("commonMetadataService")
public class CommonMetadataServiceImpl extends CommonMetadataServiceImplBase {

    private static final Logger         LOG = LoggerFactory.getLogger(CommonMetadataServiceImpl.class);

    @Autowired
    private ConfigurationRepository     configurationRepository;

    @Autowired
    private DataConfigurationRepository dataConfigurationRepository;

    public CommonMetadataServiceImpl() {
    }

    // ------------------------------------------------------------------------------------
    // CONFIGURATIONS (metadata)
    // ------------------------------------------------------------------------------------

    @Override
    public Configuration findConfigurationById(ServiceContext ctx, Long id) throws MetamacException {
        InvocationValidator.checkFindConfigurationById(id);

        // Service operation
        try {
            return configurationRepository.findById(id);
        } catch (Exception e) {
            LOG.error("findConfigurationById: id not found", e);
            throw new MetamacException(ServiceExceptionType.CONFIGURATION_NOT_FOUND, id);
        }
    }

    @Override
    public Configuration findConfigurationByUrn(ServiceContext ctx, String urn) throws MetamacException {
        InvocationValidator.checkFindConfigurationByUrn(urn);

        // Prepare criteria
        List<ConditionalCriteria> conditions = criteriaFor(Configuration.class).withProperty(org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties.urn()).eq(urn).distinctRoot()
                .build();

        // Find
        List<Configuration> result = findConfigurationByCondition(ctx, conditions);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.CONFIGURATION_NOT_FOUND_URN, urn);
        } else if (result.size() > 1) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one configuration with urn " + urn);
        }

        // Return unique result
        return result.get(0);
    }

    @Override
    public List<Configuration> findAllConfigurations(ServiceContext ctx) throws MetamacException {
        InvocationValidator.checkFindAllConfigurations();
        return configurationRepository.findAll();
    }

    @Override
    public List<Configuration> findConfigurationByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions) throws MetamacException {
        // Validations
        InvocationValidator.checkFindConfigurationByCondition(conditions);

        // Initializations
        initCriteriaConditions(conditions, Configuration.class);

        // Repository operation
        return configurationRepository.findByCondition(conditions);
    }

    @Override
    public Configuration createConfiguration(ServiceContext ctx, Configuration configuration) throws MetamacException {
        // Fill metadata
        configuration.setUrn(GeneratorUrnUtils.generateSiemacCommonMetadataUrn(configuration.getCode()));
        configuration.setStatus(CommonMetadataStatusEnum.ENABLED);
        configuration.setExternallyPublished(false);

        // Validations
        InvocationValidator.checkCreateConfiguration(configuration);
        validateConfigurationCodeUnique(ctx, configuration.getCode(), null);

        // Repository operation
        return configurationRepository.save(configuration);
    }

    @Override
    public Configuration updateConfiguration(ServiceContext ctx, Configuration configuration) throws MetamacException {
        // Validations
        InvocationValidator.checkUpdateConfiguration(configuration);

        // Repository operation
        return configurationRepository.save(configuration);
    }

    @Override
    public void deleteConfiguration(ServiceContext ctx, Long configurationId) throws MetamacException {
        InvocationValidator.checkDeleteConfiguration(configurationId);

        // Repository operation
        Configuration configuration = findConfigurationById(ctx, configurationId);
        configurationRepository.delete(configuration);
    }

    @Override
    public List<Configuration> updateConfigurationsStatus(ServiceContext ctx, List<Long> configurationIds, CommonMetadataStatusEnum status) throws MetamacException {
        // Validations
        InvocationValidator.checkUpdateConfigurationsStatus(configurationIds, status);

        // Update status
        List<Configuration> updatedCondifurations = new ArrayList<Configuration>();

        for (Long id : configurationIds) {
            Configuration configuration = findConfigurationById(ctx, id);
            configuration.setStatus(status);
            configuration = updateConfiguration(ctx, configuration);
            updatedCondifurations.add(configuration);
        }

        // Return
        return updatedCondifurations;
    }

    // ----------------------------------------------------------------------
    // DATA CONFIGURATIONS
    // ----------------------------------------------------------------------

    @Override
    public DataConfiguration findDataConfigurationById(ServiceContext ctx, Long id) throws MetamacException {
        InvocationValidator.checkFindDataConfigurationById(id);

        try {
            return dataConfigurationRepository.findById(id);
        } catch (Exception e) {
            LOG.error("findDataConfigurationById: id not found", e);
            throw new MetamacException(ServiceExceptionType.DATA_CONFIGURATION_NOT_FOUND, id);
        }
    }

    @Override
    public DataConfiguration findDataConfigurationByConfigurationKey(ServiceContext ctx, String dataConfigurationKey) throws MetamacException {
        InvocationValidator.checkFindDataConfigurationByConfigurationKey(dataConfigurationKey);

        // Prepare criteria
        List<ConditionalCriteria> conditions = criteriaFor(DataConfiguration.class).withProperty(org.siemac.metamac.common.metadata.core.domain.DataConfigurationProperties.configurationKey())
                .eq(dataConfigurationKey).distinctRoot().build();

        // Find
        List<DataConfiguration> result = dataConfigurationRepository.findByCondition(conditions);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.DATA_CONFIGURATION_NOT_FOUND_CONFIGURATION_KEY, dataConfigurationKey);
        } else if (result.size() > 1) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one configuration with configuration key " + dataConfigurationKey);
        }

        // Return unique result
        return result.get(0);
    }

    @Override
    public DataConfiguration updateDataConfiguration(ServiceContext ctx, DataConfiguration dataConfiguration) throws MetamacException {
        // Validations
        InvocationValidator.checkUpdateDataConfiguration(dataConfiguration);

        // Repository operation
        return dataConfigurationRepository.save(dataConfiguration);
    }

    @Override
    public List<DataConfiguration> findDataConfigurationsOfSystemProperties(ServiceContext ctx) throws MetamacException {
        return findDataConfigurationsOfSystemPropertiesByCondition(ctx, null);
    }

    @Override
    public List<DataConfiguration> findDataConfigurationsOfSystemPropertiesByCondition(ServiceContext ctx, List<ConditionalCriteria> condition) throws MetamacException {
        InvocationValidator.checkFindDataConfigurationsOfSystemProperties();

        // Prepare criteria
        List<ConditionalCriteria> conditions = criteriaFor(DataConfiguration.class).withProperty(org.siemac.metamac.common.metadata.core.domain.DataConfigurationProperties.systemProperty())
                .eq(Boolean.TRUE).distinctRoot().build();
        
        if (condition != null) {
            conditions.addAll(condition);
        }

        // Find
        List<DataConfiguration> result = dataConfigurationRepository.findByCondition(conditions);

        return result;
    }

    @Override
    public List<DataConfiguration> findDataConfigurationsOfDefaultValues(ServiceContext ctx) throws MetamacException {
        return findDataConfigurationsOfDefaultValuesByCondition(ctx, null);
    }

    @Override
    public List<DataConfiguration> findDataConfigurationsOfDefaultValuesByCondition(ServiceContext ctx, List<ConditionalCriteria> condition) throws MetamacException {
        InvocationValidator.checkFindDataConfigurationsOfDefaultValues();

        // Prepare criteria
        List<ConditionalCriteria> conditions = criteriaFor(DataConfiguration.class).withProperty(org.siemac.metamac.common.metadata.core.domain.DataConfigurationProperties.systemProperty())
                .eq(Boolean.FALSE).distinctRoot().build();
        
        // Merge conditions
        if (condition != null) {
            conditions.addAll(condition);
        }

        // Find
        List<DataConfiguration> result = dataConfigurationRepository.findByCondition(conditions);

        return result;
    }
    
    // ----------------------------------------------------------------------
    // VALIDATORS
    // ----------------------------------------------------------------------

    private void validateConfigurationCodeUnique(ServiceContext ctx, String code, Long actualId) throws MetamacException {
        List<ConditionalCriteria> condition = criteriaFor(Configuration.class).withProperty(org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties.code()).ignoreCaseEq(code).build();
        List<Configuration> configurations = findConfigurationByCondition(ctx, condition);
        if (configurations != null) {
            throwErrorForDuplicatedConfigurationCode(code, actualId, configurations);
        }
    }

    private void throwErrorForDuplicatedConfigurationCode(String code, Long actualId, List<Configuration> configurations) throws MetamacException {
        if (actualId == null) {
            if (configurations.size() == 1) {
                throwErrorConfigurationAlreadyExistsCodeDuplicated(code);
            } else if (configurations.size() > 1) {
                throwErrorUnknownMoreThanOneCofigurationWhithSameCodeInDatabase(code);
            }
        } else {
            if (configurations.size() == 2) {
                throwErrorConfigurationAlreadyExistsCodeDuplicated(code);
            } else if (configurations.size() > 2) {
                throwErrorUnknownMoreThanOneCofigurationWhithSameCodeInDatabase(code);
            }
        }
    }

    private void throwErrorUnknownMoreThanOneCofigurationWhithSameCodeInDatabase(String code) throws MetamacException {
        throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one configuration with code " + code);
    }

    private void throwErrorConfigurationAlreadyExistsCodeDuplicated(String code) throws MetamacException {
        throw new MetamacException(ServiceExceptionType.CONFIGURATION_ALREADY_EXIST_CODE_DUPLICATED, code);
    }

    // ----------------------------------------------------------------------
    // UTILS
    // ----------------------------------------------------------------------
    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<ConditionalCriteria> initCriteriaConditions(List<ConditionalCriteria> conditions, Class entityClass) {
        List<ConditionalCriteria> conditionsEntity = ConditionalCriteriaBuilder.criteriaFor(entityClass).build();
        if (conditions != null) {
            conditionsEntity.addAll(conditions);
        }
        return conditionsEntity;
    }

}
