package org.siemac.metamac.common.metadata.web.client.presenter;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;

import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.web.client.LoggedInGatekeeper;
import org.siemac.metamac.common.metadata.web.client.enums.AppsConfigurationsToolStripButtonEnum;
import org.siemac.metamac.common.metadata.web.client.widgets.presenter.AppsConfigurationsToolStripPresenterWidget;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class AppsConfigurationsPresenter extends Presenter<AppsConfigurationsPresenter.AppsConfigurationsView, AppsConfigurationsPresenter.AppsConfigurationsProxy> {

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

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().appsConfigurations();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        setInSlot(TYPE_SetAppsConfigurationsToolbarSlot, presenterWidget);
        presenterWidget.selectButton(AppsConfigurationsToolStripButtonEnum.SYSTEM_PROPERTIES.name());
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, MainPagePresenter.TYPE_SetContextAreaContent, this);
    }

}
