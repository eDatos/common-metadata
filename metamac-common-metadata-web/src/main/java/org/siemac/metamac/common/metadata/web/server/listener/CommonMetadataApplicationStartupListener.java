package org.siemac.metamac.common.metadata.web.server.listener;

import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConfigurationConstants;
import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;
import org.siemac.metamac.web.common.server.listener.ApplicationStartupListener;

public class CommonMetadataApplicationStartupListener extends ApplicationStartupListener {

    @Override
    public void checkConfiguration() {

        // SECURITY

        checkRequiredProperty(ConfigurationConstants.SECURITY_CAS_SERVER_URL_PREFIX);
        checkRequiredProperty(ConfigurationConstants.SECURITY_CAS_SERVICE_LOGIN_URL);
        checkRequiredProperty(ConfigurationConstants.SECURITY_CAS_SERVICE_LOGOUT_URL);
        checkRequiredProperty(ConfigurationConstants.SECURITY_TOLERANCE);

        // DATASOURCE

        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_DRIVER_NAME);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_URL);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_USERNAME);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_PASSWORD);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_DIALECT);

        // OTHER CONFIGURATION PROPERTIES

        // Common properties

        checkRequiredProperty(ConfigurationConstants.METAMAC_EDITION_LANGUAGES);
        checkRequiredProperty(ConfigurationConstants.METAMAC_NAVBAR_URL);

        // API

        checkRequiredProperty(ConfigurationConstants.ENDPOINT_SRM_INTERNAL_API);

        // SRM properties

        checkRequiredProperty(CommonMetadataConfigurationConstants.USER_GUIDE_FILE_NAME);
    }
}
