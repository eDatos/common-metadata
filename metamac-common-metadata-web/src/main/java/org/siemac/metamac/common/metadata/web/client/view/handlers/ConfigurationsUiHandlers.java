package org.siemac.metamac.common.metadata.web.client.view.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.web.common.client.view.handlers.SrmExternalResourcesUiHandlers;

public interface ConfigurationsUiHandlers extends SrmExternalResourcesUiHandlers {

    void goToConfigurations();
    void goToConfiguration(String urn);
    void createConfiguration(ConfigurationDto configurationDto);
    void updateConfigurationsStatus(List<Long> configurationIds, CommonMetadataStatusEnum statusEnum);
    void deleteConfigurations(List<Long> ids);
}
