package org.siemac.metamac.common.metadata.web.client.presenter;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.common.metadata.web.client.view.handlers.MainPageUiHandlers;
import org.siemac.metamac.common.metadata.web.client.widgets.events.SelectMainSectionEvent;
import org.siemac.metamac.common.metadata.web.client.widgets.events.SelectMainSectionEvent.SelectMainSectionEventHandler;
import org.siemac.metamac.common.metadata.web.client.widgets.presenter.CommonMetadataToolStripPresenterWidget;
import org.siemac.metamac.common.metadata.web.shared.GetHelpUrlAction;
import org.siemac.metamac.common.metadata.web.shared.GetHelpUrlResult;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.HideMessageEvent;
import org.siemac.metamac.web.common.client.events.HideMessageEvent.HideMessageHandler;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent.ShowMessageHandler;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.client.widgets.BreadCrumbsPanel;
import org.siemac.metamac.web.common.client.widgets.MasterHead;
import org.siemac.metamac.web.common.shared.CloseSessionAction;
import org.siemac.metamac.web.common.shared.CloseSessionResult;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.gwtplatform.mvp.client.proxy.SetPlaceTitleHandler;

public class MainPagePresenter extends Presenter<MainPagePresenter.MainPageView, MainPagePresenter.MainPageProxy>
        implements
            MainPageUiHandlers,
            ShowMessageHandler,
            HideMessageHandler,
            SelectMainSectionEventHandler {

    private static Logger                                logger                        = Logger.getLogger(MainPagePresenter.class.getName());

    private final PlaceManager                           placeManager;
    private final DispatchAsync                          dispatcher;

    private static MasterHead                            masterHead;

    private final CommonMetadataToolStripPresenterWidget toolStripPresenterWidget;

    @ContentSlot
    public static final Type<RevealContentHandler<?>>    TYPE_SetCommonMetadataToolBar = new Type<RevealContentHandler<?>>();

    @ProxyStandard
    @NoGatekeeper
    public interface MainPageProxy extends Proxy<MainPagePresenter> {

    }

    public interface MainPageView extends View, HasUiHandlers<MainPageUiHandlers> {

        MasterHead getMasterHead();

        BreadCrumbsPanel getBreadCrumbsPanel();
        void clearBreadcrumbs(int size, PlaceManager placeManager);
        void setBreadcrumb(int index, String title);
        void showMessage(Throwable throwable, String message, MessageTypeEnum type);
        void hideMessages();
        void setTitle(String title);
    }

    /**
     * Use this in leaf presenters, inside their {@link #revealInParent} method.
     * Is used to define a type to use in child presenters when you want to
     * include them inside this page.
     */
    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaContent = new Type<RevealContentHandler<?>>();

    @Inject
    public MainPagePresenter(EventBus eventBus, MainPageView view, MainPageProxy proxy, PlaceManager placeManager, DispatchAsync dispatcher,
            CommonMetadataToolStripPresenterWidget toolStripPresenterWidget) {
        super(eventBus, view, proxy);
        this.toolStripPresenterWidget = toolStripPresenterWidget;
        getView().setUiHandlers(this);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        MainPagePresenter.masterHead = getView().getMasterHead();
    }
    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        setInSlot(TYPE_SetCommonMetadataToolBar, toolStripPresenterWidget);
    }

    @Override
    protected void onReset() {
        super.onReset();
        updateBreadcrumbs();
    }

    private void updateBreadcrumbs() {
        int size = placeManager.getHierarchyDepth();
        getView().clearBreadcrumbs(size, placeManager);
        for (int i = 0; i < size; ++i) {
            final int index = i;
            placeManager.getTitle(i, new SetPlaceTitleHandler() {

                @Override
                public void onSetPlaceTitle(String title) {
                    getView().setBreadcrumb(index, title);
                }
            });
        }
    }

    @Override
    public void onNavigationPaneSectionHeaderClicked(String place) {
        if (place.length() != 0) {
            PlaceRequest placeRequest = new PlaceRequest(place);
            placeManager.revealPlace(placeRequest);
        }
    }

    @Override
    public void onNavigationPaneSectionClicked(String place) {
        if (place.length() != 0) {
            PlaceRequest placeRequest = new PlaceRequest(place);
            placeManager.revealPlace(placeRequest);
        }
    }

    @ProxyEvent
    @Override
    public void onSelectMainSection(SelectMainSectionEvent event) {
        toolStripPresenterWidget.deselectButtons();
        toolStripPresenterWidget.selectButton(event.getButtonId().getValue());
    }

    @ProxyEvent
    @Override
    public void onShowMessage(ShowMessageEvent event) {
        getView().showMessage(event.getThrowable(), event.getMessage(), event.getMessageType());
    }

    @ProxyEvent
    @Override
    public void onHideMessage(HideMessageEvent event) {
        hideMessages();
    }

    private void hideMessages() {
        getView().hideMessages();
    }

    public static MasterHead getMasterHead() {
        return masterHead;
    }

    @Override
    public void closeSession() {
        dispatcher.execute(new CloseSessionAction(), new AsyncCallback<CloseSessionResult>() {

            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error closing session");
                ShowMessageEvent.fireErrorMessage(MainPagePresenter.this, caught);
            }
            @Override
            public void onSuccess(CloseSessionResult result) {
                Window.Location.assign(result.getLogoutPageUrl());
            }
        });
    }

    @Override
    public void openHelpUrl() {
        dispatcher.execute(new GetHelpUrlAction(), new WaitingAsyncCallbackHandlingError<GetHelpUrlResult>(this) {

            @Override
            public void onWaitSuccess(GetHelpUrlResult result) {
                Window.open(result.getHelpUrl(), "_blank", "");
            }
        });
    }
}
