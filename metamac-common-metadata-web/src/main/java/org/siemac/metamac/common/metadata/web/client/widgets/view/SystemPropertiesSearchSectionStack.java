package org.siemac.metamac.common.metadata.web.client.widgets.view;

import org.siemac.metamac.common.metadata.web.client.view.handlers.AppsSystemPropertiesUiHandlers;

public class SystemPropertiesSearchSectionStack extends CommonMetadataSearchSectionStack {
    private AppsSystemPropertiesUiHandlers handlers;

    protected void retrieveResources() {
        getUiHandlers().retrieveSystemPropertiesAndSelect(null, getDataConfigurationWebCriteria());
    }

    private AppsSystemPropertiesUiHandlers getUiHandlers() {
        return handlers;
    }

    public void setUiHandlers(AppsSystemPropertiesUiHandlers handlers) {
        this.handlers = handlers;
    }
}
