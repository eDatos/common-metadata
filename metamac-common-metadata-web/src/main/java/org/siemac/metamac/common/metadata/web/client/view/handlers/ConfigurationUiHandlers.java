package org.siemac.metamac.common.metadata.web.client.view.handlers;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;

import com.gwtplatform.mvp.client.UiHandlers;

public interface ConfigurationUiHandlers extends UiHandlers {

    void saveConfiguration(ConfigurationDto configurationDto);
    void populateOrganisations(String organisationSchemeUri);
}
