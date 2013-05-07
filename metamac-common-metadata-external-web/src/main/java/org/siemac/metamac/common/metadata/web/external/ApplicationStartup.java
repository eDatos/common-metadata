package org.siemac.metamac.common.metadata.web.external;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConfigurationConstants;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;

public class ApplicationStartup implements ServletContextListener {

    private static final Log     LOG = LogFactory.getLog(ApplicationStartup.class);

    private ConfigurationService configurationService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            configurationService = ApplicationContextProvider.getApplicationContext().getBean(ConfigurationService.class);
            checkConfiguration();
        } catch (Exception e) {
            // Abort startup application
            throw new RuntimeException(e);
        }
    }

    private void checkConfiguration() {
        LOG.info("**********************************************************");
        LOG.info("Checking application configuration");
        LOG.info("**********************************************************");

        // Datasource
        configurationService.checkRequiredProperty(CommonMetadataConfigurationConstants.DB_DRIVER_NAME);
        configurationService.checkRequiredProperty(CommonMetadataConfigurationConstants.DB_URL);
        configurationService.checkRequiredProperty(CommonMetadataConfigurationConstants.DB_USERNAME);
        configurationService.checkRequiredProperty(CommonMetadataConfigurationConstants.DB_PASSWORD);
        configurationService.checkRequiredProperty(CommonMetadataConfigurationConstants.DB_DIALECT);

        // Api
        configurationService.checkRequiredProperty(CommonMetadataConfigurationConstants.ENDPOINT_COMMON_METADATA_EXTERNAL_API);
        configurationService.checkRequiredProperty(CommonMetadataConfigurationConstants.ENDPOINT_SRM_EXTERNAL_API);
        configurationService.checkRequiredProperty(CommonMetadataConfigurationConstants.WEB_APPLICATION_COMMON_METADATA_INTERNAL_WEB);
        configurationService.checkRequiredProperty(CommonMetadataConfigurationConstants.WEB_APPLICATION_COMMON_METADATA_INTERNAL_WEB);

        LOG.info("**********************************************************");
        LOG.info("Application configuration checked");
        LOG.info("**********************************************************");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
