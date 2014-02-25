package org.siemac.metamac.common.metadata.web.client.view.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

public interface ConfigurationsUiHandlers extends BaseUiHandlers {

    void goToConfigurations();
    void goToConfiguration(String urn);
    void createConfiguration(ConfigurationDto configurationDto);
    void updateConfigurationsStatus(List<Long> configurationIds, CommonMetadataStatusEnum statusEnum);
    void deleteConfigurations(List<Long> ids);

    void retrieveAgencySchemes(SrmExternalResourceRestCriteria srmItemSchemeRestCriteria, int firstResult, int maxResults);
    void retrieveAgencies(SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults);
}
