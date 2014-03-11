package org.siemac.metamac.common.metadata.core.conf;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;

public interface CommonMetadataConfigurationService extends ConfigurationService {

    public String retrieveUserGuideFileName() throws MetamacException;
}
