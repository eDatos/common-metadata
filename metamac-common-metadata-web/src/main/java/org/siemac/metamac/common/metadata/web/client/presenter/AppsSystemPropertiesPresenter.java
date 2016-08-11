package org.siemac.metamac.common.metadata.web.client.presenter;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.RoleLoggedInGatekeeper;
import org.siemac.metamac.common.metadata.web.client.enums.AppsConfigurationsToolStripButtonEnum;
import org.siemac.metamac.common.metadata.web.client.enums.AppsConfigurationsType;
import org.siemac.metamac.common.metadata.web.client.view.handlers.AppsSystemPropertiesUiHandlers;
import org.siemac.metamac.common.metadata.web.client.widgets.events.SelectAppConfigurationSectionEvent;
import org.siemac.metamac.common.metadata.web.shared.GetAppsConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.GetAppsConfigurationsResult;
import org.siemac.metamac.common.metadata.web.shared.SaveAppConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.SaveAppConfigurationResult;
import org.siemac.metamac.common.metadata.web.shared.criteria.DataConfigurationWebCriteria;
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
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class AppsSystemPropertiesPresenter extends Presenter<AppsSystemPropertiesPresenter.AppsSystemPropertiesView, AppsSystemPropertiesPresenter.AppsSystemPropertiesProxy>
        implements
            AppsSystemPropertiesUiHandlers {

    private final DispatchAsync dispatcher;

    @ProxyCodeSplit
    @NameToken(NameTokens.SYSTEM_PROPERTIES_LIST_PAGE)
    @UseGatekeeper(RoleLoggedInGatekeeper.class)
    public interface AppsSystemPropertiesProxy extends Proxy<AppsSystemPropertiesPresenter>, Place {
    }

    public interface AppsSystemPropertiesView extends View, HasUiHandlers<AppsSystemPropertiesUiHandlers> {

        void setAppConfigurations(List<DataConfigurationDto> properties);

        void selectAppConfiguration(DataConfigurationDto appConfiguration);

        DataConfigurationWebCriteria getDataConfigurationWebCriteria();

    }

    @Inject
    public AppsSystemPropertiesPresenter(EventBus eventBus, AppsSystemPropertiesView view, AppsSystemPropertiesProxy proxy, DispatchAsync dispatcher) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        view.setUiHandlers(this);
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().systemProperties();
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, AppsConfigurationsPresenter.TYPE_SetAppsConfigurationsContent, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        SelectAppConfigurationSectionEvent.fire(this, AppsConfigurationsToolStripButtonEnum.SYSTEM_PROPERTIES);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        retrieveSystemPropertiesAndSelect(null, getView().getDataConfigurationWebCriteria());
    }

    @Override
    public void retrieveSystemPropertiesAndSelect(final DataConfigurationDto appConfiguration, DataConfigurationWebCriteria criteria) {
        dispatcher.execute(new GetAppsConfigurationsAction(AppsConfigurationsType.SYSTEM, criteria), new WaitingAsyncCallbackHandlingError<GetAppsConfigurationsResult>(this) {

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
                retrieveSystemPropertiesAndSelect(result.getPropertySaved(), getView().getDataConfigurationWebCriteria());
            }
        });
    }

}
