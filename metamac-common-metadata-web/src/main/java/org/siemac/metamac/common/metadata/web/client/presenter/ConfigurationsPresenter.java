package org.siemac.metamac.common.metadata.web.client.presenter;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.LoggedInGatekeeper;
import org.siemac.metamac.common.metadata.web.client.utils.ErrorUtils;
import org.siemac.metamac.common.metadata.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationsUiHandlers;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListAction;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListResult;
import org.siemac.metamac.common.metadata.web.shared.GetConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.GetConfigurationsResult;
import org.siemac.metamac.common.metadata.web.shared.UpdateConfigurationsStatusAction;
import org.siemac.metamac.common.metadata.web.shared.UpdateConfigurationsStatusResult;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class ConfigurationsPresenter extends Presenter<ConfigurationsPresenter.ConfigurationsView, ConfigurationsPresenter.ConfigurationsProxy> implements ConfigurationsUiHandlers {

    private final DispatchAsync dispatcher;
    private final PlaceManager  placeManager;

    @ProxyCodeSplit
    @NameToken(NameTokens.configurationListPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface ConfigurationsProxy extends Proxy<ConfigurationsPresenter>, Place {

    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaContentConfiguration = new Type<RevealContentHandler<?>>();

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().configurations();
    }

    public interface ConfigurationsView extends View, HasUiHandlers<ConfigurationsUiHandlers> {

        void setConfigurations(List<ConfigurationDto> configurations);
    }

    @Inject
    public ConfigurationsPresenter(EventBus eventBus, ConfigurationsView configurationView, ConfigurationsProxy configurationProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, configurationView, configurationProxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, MainPagePresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        MainPagePresenter.getMasterHead().setTitleLabel(CommonMetadataWeb.getConstants().configurationMetamac());
        retrieveConfigurations();
    }

    private void retrieveConfigurations() {
        dispatcher.execute(new GetConfigurationsAction(), new WaitingAsyncCallback<GetConfigurationsResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorRetrievingConfigurations()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetConfigurationsResult result) {
                getView().setConfigurations(result.getConfigurations());
            }
        });
    }

    @Override
    public void deleteConfigurations(List<Long> configurationIds) {
        dispatcher.execute(new DeleteConfigurationListAction(configurationIds), new WaitingAsyncCallback<DeleteConfigurationListResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorDeletingConfigurations()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(DeleteConfigurationListResult result) {
                retrieveConfigurations();
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getMessageList(CommonMetadataWeb.getMessages().configurationDeleted()), MessageTypeEnum.SUCCESS);
            }
        });
    }

    @Override
    public void updateConfigurationsStatus(List<Long> configurationIds, CommonMetadataStatusEnum statusEnum) {
        dispatcher.execute(new UpdateConfigurationsStatusAction(configurationIds, statusEnum), new WaitingAsyncCallback<UpdateConfigurationsStatusResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorUpdatingConfigurationStatus()), MessageTypeEnum.ERROR);
                retrieveConfigurations();
            }

            @Override
            public void onWaitSuccess(UpdateConfigurationsStatusResult result) {
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getMessageList(CommonMetadataWeb.getMessages().configurationStatusUpdated()), MessageTypeEnum.SUCCESS);
                retrieveConfigurations();
            }
        });
    }

    //
    // NAVIGATION
    //

    @Override
    public void goToConfigurations() {
        placeManager.revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteConfigurationsPlaceRequest());
    }

    @Override
    public void goToConfiguration(String urn) {
        if (!StringUtils.isBlank(urn)) {
            placeManager.revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteConfigurationPlaceRequest(urn));
        }
    }
}
