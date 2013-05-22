package org.siemac.metamac.common.metadata.web.client.view.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;

import com.gwtplatform.mvp.client.UiHandlers;

public interface ConfigurationsUiHandlers extends UiHandlers {

    void saveConfiguration(ConfigurationDto configurationDto);
    void deleteConfigurations(List<Long> configurationIds);
    void populateOrganisations(String organisationSchemeUri);
    void updateConfigurationsStatus(List<Long> configurationIds, CommonMetadataStatusEnum statusEnum);
}
