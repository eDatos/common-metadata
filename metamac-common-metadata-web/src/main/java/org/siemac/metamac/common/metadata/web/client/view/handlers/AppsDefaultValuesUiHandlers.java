package org.siemac.metamac.common.metadata.web.client.view.handlers;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.presenter.AppsDefaultValuesPresenter.ViewActionHandlers;
import org.siemac.metamac.common.metadata.web.shared.criteria.DataConfigurationWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

public interface AppsDefaultValuesUiHandlers extends AppsDataConfigurationsUiHandlers {

    void retrieveConceptSchemes(SrmExternalResourceRestCriteria srmItemSchemeRestCriteria, int firstResult, int maxResults);
    void retrieveCodelists(SrmExternalResourceRestCriteria srmItemSchemeRestCriteria, int firstResult, int maxResults, final ViewActionHandlers viewHandler);
    void retrieveConcepts(SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults);
    void retrieveCodes(SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults);
    
    void retrieveDefaultValuesPropertiesAndSelect(DataConfigurationDto appConfiguration, DataConfigurationWebCriteria criteria);    

}
