package org.siemac.metamac.common.metadata.web.client.widgets.presenter;

import org.siemac.metamac.common.metadata.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.web.common.client.widgets.toolstrip.presenter.MetamacToolStripPresenterWidget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class AppsConfigurationsToolStripPresenterWidget extends MetamacToolStripPresenterWidget<AppsConfigurationsToolStripPresenterWidget.AppsConfigurationsToolStripView> {

    public interface AppsConfigurationsToolStripView extends MetamacToolStripPresenterWidget.MetamacToolStripView {

        HasClickHandlers getGoSystemProperties();
        HasClickHandlers getGoDefaultValues();
    }

    @Inject
    public AppsConfigurationsToolStripPresenterWidget(EventBus eventBus, AppsConfigurationsToolStripView toolStripView, PlaceManager placeManager) {
        super(eventBus, toolStripView, placeManager);
    }

    @Override
    protected void onBind() {
        super.onBind();

        registerHandler(getView().getGoSystemProperties().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getPlaceManager().revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteAppsConfSystemPropertiesPlaceRequest());
            }
        }));

        registerHandler(getView().getGoDefaultValues().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getPlaceManager().revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteAppsConfDefaultValuesPlaceRequest());
            }
        }));
    }
}
