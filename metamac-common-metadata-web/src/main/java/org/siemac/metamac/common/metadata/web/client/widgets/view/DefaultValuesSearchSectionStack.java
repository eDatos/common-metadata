package org.siemac.metamac.common.metadata.web.client.widgets.view;

import org.siemac.metamac.common.metadata.web.client.view.handlers.AppsDefaultValuesUiHandlers;

public class DefaultValuesSearchSectionStack extends CommonMetadataSearchSectionStack {
    private AppsDefaultValuesUiHandlers handlers;

    protected void retrieveResources() {
        getUiHandlers().retrieveDefaultValuesPropertiesAndSelect(null, getDataConfigurationWebCriteria());
    }

    private AppsDefaultValuesUiHandlers getUiHandlers() {
        return handlers;
    }

    public void setUiHandlers(AppsDefaultValuesUiHandlers handlers) {
        this.handlers = handlers;
    }
}
