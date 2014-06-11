package org.siemac.metamac.common.metadata.web.server.listener;

import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConfigurationConstants;
import org.siemac.metamac.web.common.server.listener.InternalApplicationStartupListener;

public class CommonMetadataApplicationStartupListener extends InternalApplicationStartupListener {

    @Override
    public void checkDatasourceProperties() {
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_DRIVER_NAME);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_URL);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_USERNAME);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_PASSWORD);
        checkRequiredProperty(CommonMetadataConfigurationConstants.DB_DIALECT);
    }

    @Override
    public void checkWebApplicationsProperties() {
        checkRequiredProperty(CommonMetadataConfigurationConstants.WEB_APPLICATION_SRM_INTERNAL_WEB);
    }

    @Override
    public void checkApiProperties() {
        checkRequiredProperty(CommonMetadataConfigurationConstants.ENDPOINT_SRM_INTERNAL_API);
    }

    @Override
    public void checkOtherModuleProperties() {
        checkRequiredProperty(CommonMetadataConfigurationConstants.USER_GUIDE_FILE_NAME);
    }

    @Override
    public String projectName() {
        return "common-metadata-internal";
    }
}
