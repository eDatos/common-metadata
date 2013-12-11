package org.siemac.metamac.common.metadata.core.serviceimpl.utils;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.DataConfiguration;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionParameters;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;

public class InvocationValidator {

    // ------------------------------------------------------------------------------------
    // CONFIGURATIONS (metadata)
    // ------------------------------------------------------------------------------------
    public static void checkFindConfigurationById(Long id, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        CommonMetadataValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkFindConfigurationByUrn(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        CommonMetadataValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);

        ExceptionUtils.throwIfException(exceptions);

    }

    public static void checkFindAllConfigurations(List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        // nothing to validate

        ExceptionUtils.throwIfException(exceptions);

    }

    public static void checkCreateConfiguration(Configuration configuration, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        checkConfiguration(configuration, exceptions);
        CommonMetadataValidationUtils.checkMetadataEmpty(configuration.getId(), ServiceExceptionParameters.CONFIGURATION_ID, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkUpdateConfiguration(Configuration configuration, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        checkConfiguration(configuration, exceptions);
        CommonMetadataValidationUtils.checkMetadataRequired(configuration.getId(), ServiceExceptionParameters.CONFIGURATION_ID, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkDeleteConfiguration(Long id, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        CommonMetadataValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkFindConfigurationByCondition(List<ConditionalCriteria> conditions, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        // nothing to validate

        ExceptionUtils.throwIfException(exceptions);

    }

    public static void checkUpdateConfigurationsStatus(List<Long> configurationIds, CommonMetadataStatusEnum status, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        CommonMetadataValidationUtils.checkParameterRequired(configurationIds, ServiceExceptionParameters.CONFIGURATION_IDS, exceptions);
        CommonMetadataValidationUtils.checkParameterRequired(status, ServiceExceptionParameters.STATUS, exceptions);

        ExceptionUtils.throwIfException(exceptions);

    }

    // ------------------------------------------------------------------------------------
    // DATA CONFIGURATIONS
    // ------------------------------------------------------------------------------------

    public static void checkFindDataConfigurationById(Long id, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        CommonMetadataValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkFindDataConfigurationByConfigurationKey(String configurationKey, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        CommonMetadataValidationUtils.checkParameterRequired(configurationKey, ServiceExceptionParameters.DATA_CONFIGURATION_KEY, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkUpdateDataConfiguration(DataConfiguration dataConfiguration, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        checkDataConfiguration(dataConfiguration, exceptions);
        CommonMetadataValidationUtils.checkMetadataRequired(dataConfiguration.getId(), ServiceExceptionParameters.DATA_CONFIGURATION_ID, exceptions);

        ExceptionUtils.throwIfException(exceptions);

    }

    public static void checkFindDataConfigurationsOfSystemProperties(List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        // nothing

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkFindDataConfigurationsOfDefaultValues(List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        // nothing

        ExceptionUtils.throwIfException(exceptions);
    }

    // ------------------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------------------
    private static void checkConfiguration(Configuration configuration, List<MetamacExceptionItem> exceptions) {
        CommonMetadataValidationUtils.checkParameterRequired(configuration, ServiceExceptionParameters.CONFIGURATION, exceptions);

        CommonMetadataValidationUtils.checkMetadataRequired(configuration.getCode(), ServiceExceptionParameters.CONFIGURATION_CODE, exceptions);
        CommonMetadataValidationUtils.checkSemanticIdentifierAsMetamacID(configuration.getCode(), ServiceExceptionParameters.CONFIGURATION_CODE, exceptions);
        CommonMetadataValidationUtils.checkMetadataRequired(configuration.getUrn(), ServiceExceptionParameters.CONFIGURATION_URN, exceptions);

        CommonMetadataValidationUtils.checkMetadataRequired(configuration.getStatus(), ServiceExceptionParameters.CONFIGURATION_STATUS, exceptions);

        CommonMetadataValidationUtils.checkMetadataOptionalIsValid(configuration.getLegalActs(), ServiceExceptionParameters.CONFIGURATION_LEGAL_ACTS, exceptions);
        CommonMetadataValidationUtils.checkMetadataOptionalIsValid(configuration.getDataSharing(), ServiceExceptionParameters.CONFIGURATION_DATA_SHARING, exceptions);
        CommonMetadataValidationUtils.checkMetadataOptionalIsValid(configuration.getConfPolicy(), ServiceExceptionParameters.CONFIGURATION_CONF_POLICY, exceptions);
        CommonMetadataValidationUtils.checkMetadataOptionalIsValid(configuration.getConfDataTreatment(), ServiceExceptionParameters.CONFIGURATION_CONF_DATA_TREATMENT, exceptions);
        CommonMetadataValidationUtils.checkMetadataRequired(configuration.getLicense(), ServiceExceptionParameters.CONFIGURATION_LICENSE, exceptions);

        CommonMetadataValidationUtils.checkMetadataRequired(configuration.getContact(), ServiceExceptionParameters.CONFIGURATION_CONTACT, exceptions);
    }

    private static void checkDataConfiguration(DataConfiguration dataConfiguration, List<MetamacExceptionItem> exceptions) {
        CommonMetadataValidationUtils.checkParameterRequired(dataConfiguration, ServiceExceptionParameters.DATA_CONFIGURATION, exceptions);

        CommonMetadataValidationUtils.checkMetadataRequired(dataConfiguration.getConfigurationKey(), ServiceExceptionParameters.DATA_CONFIGURATION_KEY, exceptions);
        CommonMetadataValidationUtils.checkMetadataOptionalIsValid(dataConfiguration.getConfigurationValue(), ServiceExceptionParameters.DATA_CONFIGURATION_VALUE, exceptions);
    }

}
