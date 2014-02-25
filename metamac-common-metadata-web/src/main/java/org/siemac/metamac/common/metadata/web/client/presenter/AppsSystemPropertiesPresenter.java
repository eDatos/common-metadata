package org.siemac.metamac.common.metadata.web.client.presenter;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.LoggedInGatekeeper;
import org.siemac.metamac.common.metadata.web.client.enums.AppsConfigurationsType;
import org.siemac.metamac.common.metadata.web.client.view.handlers.AppsDataConfigurationsUiHandlers;
import org.siemac.metamac.common.metadata.web.shared.GetAppsConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.GetAppsConfigurationsResult;
import org.siemac.metamac.common.metadata.web.shared.SaveAppConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.SaveAppConfigurationResult;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class AppsSystemPropertiesPresenter extends Presenter<AppsSystemPropertiesPresenter.AppsSystemPropertiesView, AppsSystemPropertiesPresenter.AppsSystemPropertiesProxy>
        implements
            AppsDataConfigurationsUiHandlers {

    private DispatchAsync dispatcher;

    @ProxyCodeSplit
    @NameToken(NameTokens.systemPropertiesListPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface AppsSystemPropertiesProxy extends Proxy<AppsSystemPropertiesPresenter>, Place {
    }

    public interface AppsSystemPropertiesView extends View, HasUiHandlers<AppsDataConfigurationsUiHandlers> {

        void setAppConfigurations(List<DataConfigurationDto> properties);

        void selectAppConfiguration(DataConfigurationDto appConfiguration);

    }

    @Inject
    public AppsSystemPropertiesPresenter(EventBus eventBus, AppsSystemPropertiesView view, AppsSystemPropertiesProxy proxy, DispatchAsync dispatcher) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        view.setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, AppsConfigurationsPresenter.TYPE_SetAppsConfigurationsContent, this);
    }

    @Override
    protected void onReset() {
        super.onReset();
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        retrieveSystemPropertiesAndSelect(null);
    }

    private void retrieveSystemPropertiesAndSelect(final DataConfigurationDto appConfiguration) {
        dispatcher.execute(new GetAppsConfigurationsAction(AppsConfigurationsType.SYSTEM), new WaitingAsyncCallbackHandlingError<GetAppsConfigurationsResult>(this) {

            @Override
            public void onWaitSuccess(GetAppsConfigurationsResult result) {
                getView().setAppConfigurations(result.getProperties());
                getView().selectAppConfiguration(appConfiguration);
            }
        });
    }

    @Override
    public void saveDataCondiguration(DataConfigurationDto dataConfigurationDto) {
        dispatcher.execute(new SaveAppConfigurationAction(dataConfigurationDto), new WaitingAsyncCallbackHandlingError<SaveAppConfigurationResult>(this) {

            @Override
            public void onWaitSuccess(SaveAppConfigurationResult result) {
                ShowMessageEvent.fireSuccessMessage(AppsSystemPropertiesPresenter.this, CommonMetadataWeb.getMessages().appConfigurationSaved());
                retrieveSystemPropertiesAndSelect(result.getPropertySaved());
            }
        });
    }
}
