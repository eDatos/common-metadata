package org.siemac.metamac.common.metadata.web.server.listener;

import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConfigurationConstants;
import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;
import org.siemac.metamac.web.common.server.listener.ApplicationStartupListener;

public class CommonMetadataApplicationStartupListener extends ApplicationStartupListener {

    @Override
    public void checkConfiguration() {

        // SECURITY

        checkSecurityProperties();

        // DATASOURCE

        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_DRIVER_NAME);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_URL);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_USERNAME);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_PASSWORD);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_DIALECT);

        // OTHER CONFIGURATION PROPERTIES

        // Common properties

        checkEditionLanguagesProperty();
        checkNavBarUrlProperty();
        checkOrganisationProperty();

        // API

        checkRequiredProperty(ConfigurationConstants.ENDPOINT_SRM_INTERNAL_API);

        // SRM properties

        checkRequiredProperty(CommonMetadataConfigurationConstants.USER_GUIDE_FILE_NAME);
    }
}
