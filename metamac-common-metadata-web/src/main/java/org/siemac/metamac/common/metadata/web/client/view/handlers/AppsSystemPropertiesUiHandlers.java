package org.siemac.metamac.common.metadata.web.client.view.handlers;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.presenter.AppsDefaultValuesPresenter.ViewActionHandlers;
import org.siemac.metamac.common.metadata.web.shared.criteria.DataConfigurationWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

public interface AppsSystemPropertiesUiHandlers extends AppsDataConfigurationsUiHandlers {

    void retrieveSystemPropertiesAndSelect(DataConfigurationDto appConfiguration, DataConfigurationWebCriteria criteria);
    
}
