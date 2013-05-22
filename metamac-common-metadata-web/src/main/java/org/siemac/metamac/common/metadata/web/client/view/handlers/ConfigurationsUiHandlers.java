package org.siemac.metamac.common.metadata.web.client.view.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;

import com.gwtplatform.mvp.client.UiHandlers;

public interface ConfigurationsUiHandlers extends UiHandlers {

    void goToConfiguration(String urn);
    void deleteConfigurations(List<Long> configurationIds);
    void updateConfigurationsStatus(List<Long> configurationIds, CommonMetadataStatusEnum statusEnum);
}
