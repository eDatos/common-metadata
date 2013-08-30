package org.siemac.metamac.common.metadata.web.server.listener;

import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConfigurationConstants;
import org.siemac.metamac.web.common.server.listener.ApplicationStartupListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonMetadataApplicationStartupListener extends ApplicationStartupListener {

    private static final Log     LOG = LogFactory.getLog(CommonMetadataApplicationStartupListener.class);
    
    @Override
    public void checkConfiguration() {

        LOG.info("****************************************************************");
        LOG.info("[metamac-common-metadata-web] Checking application configuration");
        LOG.info("****************************************************************");
        
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

        // WEB APPLICATIONS

        checkRequiredProperty(CommonMetadataConfigurationConstants.WEB_APPLICATION_SRM_INTERNAL_WEB);

        // API

        checkRequiredProperty(CommonMetadataConfigurationConstants.ENDPOINT_SRM_INTERNAL_API);

        // COMMON METADATA properties

        checkRequiredProperty(CommonMetadataConfigurationConstants.USER_GUIDE_FILE_NAME);
        
        LOG.info("****************************************************************");
        LOG.info("[metamac-common-metadata-web] Application configuration checked");
        LOG.info("****************************************************************");
    }
}
