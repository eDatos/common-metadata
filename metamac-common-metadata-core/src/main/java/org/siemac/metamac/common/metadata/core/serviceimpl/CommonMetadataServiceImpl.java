package org.siemac.metamac.common.metadata.core.serviceimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationRepository;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.common.metadata.core.serviceimpl.utils.InvocationValidator;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of CommonMetadataService.
 */
@Service("commonMetadataService")
public class CommonMetadataServiceImpl extends CommonMetadataServiceImplBase {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public CommonMetadataServiceImpl() {
    }

    // ------------------------------------------------------------------------------------
    // CONFIGURATIONS
    // ------------------------------------------------------------------------------------

    @Override
    public Configuration findConfigurationById(ServiceContext ctx, Long id) throws MetamacException {
        InvocationValidator.checkFindConfigurationById(id, null);

        // Service operation
        try {
            return configurationRepository.findById(id);
        } catch (Exception e) {
            throw new MetamacException(ServiceExceptionType.CONFIGURATION_NOT_FOUND, id);
        }
    }

    @Override
    public Configuration findConfigurationByUrn(ServiceContext ctx, String urn) throws MetamacException {
        InvocationValidator.checkFindConfigurationByUrn(urn, null);

        // Prepare criteria
        List<ConditionalCriteria> conditions = criteriaFor(Configuration.class).withProperty(org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties.urn()).eq(urn).distinctRoot().build();

        // Find
        List<Configuration> result = findConfigurationByCondition(ctx, conditions);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.CONFIGURATION_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one configuration with urn " + urn);
        }

        // Return unique result
        return result.get(0);
    }

    @Override
    public List<Configuration> findAllConfigurations(ServiceContext ctx) throws MetamacException {
        InvocationValidator.checkFindAllConfigurations(null);
        return configurationRepository.findAll();
    }

    @Override
    public List<Configuration> findConfigurationByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions) throws MetamacException {
        // Validations
        InvocationValidator.checkFindConfigurationByCondition(conditions, null);

        // Initializations
        initCriteriaConditions(conditions, Configuration.class);

        // Repository operation
        return configurationRepository.findByCondition(conditions);
    }

    @Override
    public Configuration createConfiguration(ServiceContext ctx, Configuration configuration) throws MetamacException {
        // Fill metadata
        configuration.setUrn(GeneratorUrnUtils.generateSiemacCommonMetadataUrn(configuration.getCode()));

        // Validations
        InvocationValidator.checkCreateConfiguration(configuration, null);
        validateConfigurationCodeUnique(ctx, configuration.getCode(), null);

        // Repository operation
        return configurationRepository.save(configuration);
    }

    @Override
    public Configuration updateConfiguration(ServiceContext ctx, Configuration configuration) throws MetamacException {
        // Validations
        InvocationValidator.checkUpdateConfiguration(configuration, null);

        // Repository operation
        return configurationRepository.save(configuration);
    }

    @Override
    public void deleteConfiguration(ServiceContext ctx, Long configurationId) throws MetamacException {
        InvocationValidator.checkDeleteConfiguration(configurationId, null);

        // Repository operation
        Configuration configuration = findConfigurationById(ctx, configurationId);
        configurationRepository.delete(configuration);
    }

    @Override
    public List<Configuration> updateConfigurationsStatus(ServiceContext ctx, List<Long> configurationIds, CommonMetadataStatusEnum status) throws MetamacException {
        // Validations
        InvocationValidator.checkUpdateConfigurationsStatus(configurationIds, status, null);

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
    // VALIDATORS
    // ----------------------------------------------------------------------

    private void validateConfigurationCodeUnique(ServiceContext ctx, String code, Long actualId) throws MetamacException {
        List<ConditionalCriteria> condition = criteriaFor(Configuration.class).withProperty(org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties.code()).ignoreCaseEq(code).build();

        List<Configuration> configurations = findConfigurationByCondition(ctx, condition);

        if (configurations != null) {
            if (actualId == null) {
                if (configurations.size() == 1) {
                    throw new MetamacException(ServiceExceptionType.CONFIGURATION_ALREADY_EXIST_CODE_DUPLICATED, code);
                } else if (configurations.size() > 1) {
                    throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one configuration with code " + code);
                }
            } else {
                if (configurations.size() == 2) {
                    throw new MetamacException(ServiceExceptionType.CONFIGURATION_ALREADY_EXIST_CODE_DUPLICATED, code);
                } else if (configurations.size() > 2) {
                    throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one configuration with code " + code);
                }
            }
        }

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
