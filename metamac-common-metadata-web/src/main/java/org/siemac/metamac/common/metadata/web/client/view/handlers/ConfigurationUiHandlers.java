package org.siemac.metamac.common.metadata.web.client.view.handlers;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.web.common.client.view.handlers.SrmExternalResourcesUiHandlers;

public interface ConfigurationUiHandlers extends SrmExternalResourcesUiHandlers {

    void saveConfiguration(ConfigurationDto configurationDto);
    void deleteConfiguration(Long configurationId);
    void publishExternally(Long id);
}
