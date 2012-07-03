package org.siemac.metamac.common.metadata.web.client.view.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;

import com.gwtplatform.mvp.client.UiHandlers;

public interface ConfigurationUiHandlers extends UiHandlers {

    void populateOrganisations(String organisationSchemeUri);

    void updateConfigurationsStatus(List<Long> configurationIds, CommonMetadataStatusEnum statusEnum);

}
