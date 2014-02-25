package org.siemac.metamac.common.metadata.web.client.widgets.view;

import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.enums.CommonMetadataToolStripButtonEnum;
import org.siemac.metamac.common.metadata.web.client.widgets.presenter.CommonMetadataToolStripPresenterWidget.CommonMetadataToolStripView;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.toolstrip.view.MetamacToolStripViewImpl;

import com.google.inject.Inject;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class CommonMetadataToolStripViewImpl extends MetamacToolStripViewImpl implements CommonMetadataToolStripView {

    private CustomToolStripButton commonMetadataButton;
    private CustomToolStripButton appsConfigurationsButton;

    @Inject
    public CommonMetadataToolStripViewImpl() {
        super();

        commonMetadataButton = new CustomToolStripButton(CommonMetadataWeb.getConstants().commonMetadata());
        commonMetadataButton.setID(CommonMetadataToolStripButtonEnum.COMMON_METADATA.getValue());

        appsConfigurationsButton = new CustomToolStripButton(CommonMetadataWeb.getConstants().appsConfigurations());
        appsConfigurationsButton.setID(CommonMetadataToolStripButtonEnum.APPS_CONFIGURATIONS.getValue());

        toolStrip.addButton(commonMetadataButton);
        toolStrip.addButton(appsConfigurationsButton);
    }

    @Override
    public HasClickHandlers getGoCommonMetadata() {
        return commonMetadataButton;
    }

    @Override
    public HasClickHandlers getGoAppsConfigurations() {
        return appsConfigurationsButton;
    }

}
