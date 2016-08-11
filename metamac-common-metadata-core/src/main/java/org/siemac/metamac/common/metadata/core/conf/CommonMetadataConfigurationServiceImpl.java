package org.siemac.metamac.common.metadata.core.conf;

import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConfigurationConstants;
import org.siemac.metamac.core.common.conf.ConfigurationServiceImpl;
import org.siemac.metamac.core.common.exception.MetamacException;

public class CommonMetadataConfigurationServiceImpl extends ConfigurationServiceImpl implements CommonMetadataConfigurationService {

    @Override
    public String retrieveHelpUrl() throws MetamacException {
        return retrieveProperty(CommonMetadataConfigurationConstants.HELP_URL);
    }

    @Override
    public String retrieveDocsPath() throws MetamacException {
        return retrieveProperty(CommonMetadataConfigurationConstants.DOCS_PATH);
    }
}
