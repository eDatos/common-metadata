package org.siemac.metamac.common.metadata.web.external;

import javax.servlet.ServletContextEvent;

import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConfigurationConstants;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.listener.ApplicationStartupListener;
import org.siemac.metamac.core.common.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationStartup extends ApplicationStartupListener {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStartup.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        try {
            WebUtils.setAppsBaseUrl(configurationService.retrieveAppsExternalWebApplicationUrlBase());
            WebUtils.setApiBaseURL(configurationService.retrieveCommonMetadataExternalApiUrlBase());

            WebUtils.setApiStyleHeaderUrl(configurationService.retrieveApiStyleHeaderUrl());
            WebUtils.setApiStyleCssUrl(configurationService.retrieveApiStyleCssUrl());
            WebUtils.setApiStyleFooterUrl(configurationService.retrieveApiStyleFooterUrl());
        } catch (MetamacException e) {
            log.error("Error retrieving the organisation from the configuration", e);
        }
    }

    @Override
    public String projectName() {
        return "common-metadata";
    }

    @Override
    public void checkApplicationProperties() throws MetamacException {
        // Datasource
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_DRIVER_NAME);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_URL);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_USERNAME);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_PASSWORD);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_DIALECT);

        // Api
        checkRequiredProperty(CommonMetadataConfigurationConstants.ENDPOINT_COMMON_METADATA_EXTERNAL_API);
        checkRequiredProperty(CommonMetadataConfigurationConstants.ENDPOINT_SRM_EXTERNAL_API);
        checkRequiredProperty(CommonMetadataConfigurationConstants.WEB_APPLICATION_COMMON_METADATA_INTERNAL_WEB);
        checkRequiredProperty(CommonMetadataConfigurationConstants.WEB_APPLICATION_COMMON_METADATA_INTERNAL_WEB);
    }
}
