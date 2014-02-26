package org.siemac.metamac.common.metadata.web.client.widgets.view;

import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.enums.AppsConfigurationsToolStripButtonEnum;
import org.siemac.metamac.common.metadata.web.client.utils.AppDataConfigClientSecurityUtils;
import org.siemac.metamac.common.metadata.web.client.widgets.presenter.AppsConfigurationsToolStripPresenterWidget.AppsConfigurationsToolStripView;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.toolstrip.view.MetamacToolStripViewImpl;

import com.google.inject.Inject;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class AppsConfigurationsToolStripViewImpl extends MetamacToolStripViewImpl implements AppsConfigurationsToolStripView {

    private CustomToolStripButton systemPropertiesButton;
    private CustomToolStripButton defaultValuesButton;

    @Inject
    public AppsConfigurationsToolStripViewImpl() {
        super();

        systemPropertiesButton = new CustomToolStripButton(CommonMetadataWeb.getConstants().systemProperties());
        systemPropertiesButton.setID(AppsConfigurationsToolStripButtonEnum.SYSTEM_PROPERTIES.getValue());
        systemPropertiesButton.setVisible(AppDataConfigClientSecurityUtils.canListSystemProperties());

        defaultValuesButton = new CustomToolStripButton(CommonMetadataWeb.getConstants().defaultValues());
        defaultValuesButton.setID(AppsConfigurationsToolStripButtonEnum.DEFAULT_VALUES.getValue());
        defaultValuesButton.setVisible(AppDataConfigClientSecurityUtils.canListDefaultValues());

        toolStrip.addButton(systemPropertiesButton);
        toolStrip.addButton(defaultValuesButton);
    }

    @Override
    public HasClickHandlers getGoSystemProperties() {
        return systemPropertiesButton;
    }

    @Override
    public HasClickHandlers getGoDefaultValues() {
        return defaultValuesButton;
    }

}
