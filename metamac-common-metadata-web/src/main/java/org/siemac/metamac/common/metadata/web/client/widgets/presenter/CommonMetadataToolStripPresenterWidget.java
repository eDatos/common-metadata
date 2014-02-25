package org.siemac.metamac.common.metadata.web.client.widgets.presenter;

import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.web.common.client.widgets.toolstrip.presenter.MetamacToolStripPresenterWidget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class CommonMetadataToolStripPresenterWidget extends MetamacToolStripPresenterWidget<CommonMetadataToolStripPresenterWidget.CommonMetadataToolStripView> {

    public interface CommonMetadataToolStripView extends MetamacToolStripPresenterWidget.MetamacToolStripView {

        HasClickHandlers getGoCommonMetadata();
        HasClickHandlers getGoAppsConfigurations();
    }

    @Inject
    public CommonMetadataToolStripPresenterWidget(EventBus eventBus, CommonMetadataToolStripView toolStripView, PlaceManager placeManager) {
        super(eventBus, toolStripView, placeManager);
    }

    @Override
    protected void onBind() {
        super.onBind();

        registerHandler(getView().getGoCommonMetadata().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getPlaceManager().revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteConfigurationsPlaceRequest());
            }
        }));

        registerHandler(getView().getGoAppsConfigurations().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getPlaceManager().revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteAppsConfigurationsRootPlaceRequest());
            }
        }));
    }
}
