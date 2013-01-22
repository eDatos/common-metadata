package org.siemac.metamac.common.metadata.core.serviceimpl.utils;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionParameters;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;

public class InvocationValidator {

    // ------------------------------------------------------------------------------------
    // CONFIGURATIONS
    // ------------------------------------------------------------------------------------
    public static void checkFindConfigurationById(Long id, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        ValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }
    
    public static void checkFindConfigurationByUrn(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        ValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);

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
        ValidationUtils.checkMetadataEmpty(configuration.getId(), ServiceExceptionParameters.CONFIGURATION_ID, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkUpdateConfiguration(Configuration configuration, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        checkConfiguration(configuration, exceptions);
        ValidationUtils.checkMetadataRequired(configuration.getId(), ServiceExceptionParameters.CONFIGURATION_ID, exceptions);
        ValidationUtils.checkMetadataRequired(configuration.getUuid(), ServiceExceptionParameters.CONFIGURATION_UUID, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkDeleteConfiguration(Long id, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        ValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkFindConfigurationByCondition(List<ConditionalCriteria> conditions, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        // nothing to validate

        ExceptionUtils.throwIfException(exceptions);

    }
    
    public static void checkUpdateConfigurationsStatus(List<Long> configurationIds, CommonMetadataStatusEnum status, List<MetamacExceptionItem> exceptions)  throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        ValidationUtils.checkParameterRequired(configurationIds, ServiceExceptionParameters.CONFIGURATION_IDS, exceptions);
        ValidationUtils.checkParameterRequired(status, ServiceExceptionParameters.STATUS, exceptions);

        ExceptionUtils.throwIfException(exceptions);
        
    }

    // ------------------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------------------
    private static void checkConfiguration(Configuration configuration, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(configuration, ServiceExceptionParameters.CONFIGURATION, exceptions);
        ValidationUtils.checkMetadataRequired(configuration.getCode(), ServiceExceptionParameters.CONFIGURATION_CODE, exceptions);
        ValidationUtils.checkMetadataRequired(configuration.getUrn(), ServiceExceptionParameters.CONFIGURATION_URN, exceptions);
        ValidationUtils.checkMetadataRequired(configuration.getStatus(), ServiceExceptionParameters.CONFIGURATION_STATUS, exceptions);
        ValidationUtils.checkSemanticIdentifierAsMetamacID(configuration.getCode(), ServiceExceptionParameters.CONFIGURATION_CODE, exceptions);
    }


}
