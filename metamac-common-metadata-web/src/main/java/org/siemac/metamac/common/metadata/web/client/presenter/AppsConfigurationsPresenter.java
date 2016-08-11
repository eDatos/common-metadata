package org.siemac.metamac.common.metadata.web.client.presenter;

import org.siemac.metamac.common.metadata.web.client.LoggedInGatekeeper;
import org.siemac.metamac.common.metadata.web.client.enums.CommonMetadataToolStripButtonEnum;
import org.siemac.metamac.common.metadata.web.client.widgets.events.SelectAppConfigurationSectionEvent;
import org.siemac.metamac.common.metadata.web.client.widgets.events.SelectMainSectionEvent;
import org.siemac.metamac.common.metadata.web.client.widgets.events.SelectAppConfigurationSectionEvent.SelectAppConfigurationSectionEventHandler;
import org.siemac.metamac.common.metadata.web.client.widgets.presenter.AppsConfigurationsToolStripPresenterWidget;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class AppsConfigurationsPresenter extends Presenter<AppsConfigurationsPresenter.AppsConfigurationsView, AppsConfigurationsPresenter.AppsConfigurationsProxy>
        implements
            SelectAppConfigurationSectionEventHandler {

    @ContentSlot
    public static final Type<RevealContentHandler<?>>        TYPE_SetAppsConfigurationsToolbarSlot = new Type<RevealContentHandler<?>>();

    @ContentSlot
    public static final Type<RevealContentHandler<?>>        TYPE_SetAppsConfigurationsContent     = new Type<RevealContentHandler<?>>();

    private final AppsConfigurationsToolStripPresenterWidget presenterWidget;

    @ProxyCodeSplit
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface AppsConfigurationsProxy extends Proxy<AppsConfigurationsPresenter> {
    }

    public interface AppsConfigurationsView extends View {

    }

    @Inject
    public AppsConfigurationsPresenter(EventBus eventBus, AppsConfigurationsView view, AppsConfigurationsProxy proxy, AppsConfigurationsToolStripPresenterWidget presenterWidget) {
        super(eventBus, view, proxy);
        this.presenterWidget = presenterWidget;
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        SelectMainSectionEvent.fire(this, CommonMetadataToolStripButtonEnum.APPS_CONFIGURATIONS);

        setInSlot(TYPE_SetAppsConfigurationsToolbarSlot, presenterWidget);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, MainPagePresenter.TYPE_SetContextAreaContent, this);
    }

    @ProxyEvent
    @Override
    public void onSelectAppConfigurationSection(SelectAppConfigurationSectionEvent event) {
        presenterWidget.deselectButtons();
        presenterWidget.selectButton(event.getButtonId().getValue());
    }

}
