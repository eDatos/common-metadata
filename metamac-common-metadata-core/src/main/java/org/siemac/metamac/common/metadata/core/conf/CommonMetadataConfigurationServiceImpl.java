package org.siemac.metamac.common.metadata.core.conf;

import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConfigurationConstants;
import org.siemac.metamac.core.common.conf.ConfigurationServiceImpl;
import org.siemac.metamac.core.common.exception.MetamacException;

public class CommonMetadataConfigurationServiceImpl extends ConfigurationServiceImpl implements CommonMetadataConfigurationService {

    @Override
    public String retrieveUserGuideFileName() throws MetamacException {
        return retrieveProperty(CommonMetadataConfigurationConstants.USER_GUIDE_FILE_NAME, Boolean.TRUE);
    }

    @Override
    public String retrieveDocsPath() throws MetamacException {
        return retrieveProperty(CommonMetadataConfigurationConstants.DOCS_PATH, Boolean.TRUE);
    }

}
