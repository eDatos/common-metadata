package org.siemac.metamac.common.metadata.web.client.view.handlers;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;

import com.gwtplatform.mvp.client.UiHandlers;

public interface AppsDataConfigurationsUiHandlers extends UiHandlers {

    void saveDataCondiguration(DataConfigurationDto dataConfigurationDto);

}
