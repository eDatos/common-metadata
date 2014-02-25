package org.siemac.metamac.common.metadata.web.client.view.handlers;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

public interface ConfigurationUiHandlers extends BaseUiHandlers {

    void saveConfiguration(ConfigurationDto configurationDto);
    void deleteConfiguration(Long configurationId);
    void publishExternally(Long id);

    void retrieveAgencySchemes(SrmExternalResourceRestCriteria srmItemSchemeRestCriteria, int firstResult, int maxResults);

    void retrieveAgencies(SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults);
}
