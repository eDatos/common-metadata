package org.siemac.metamac.common.metadata.web.client.presenter;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.LoggedInGatekeeper;
import org.siemac.metamac.common.metadata.web.client.utils.ErrorUtils;
import org.siemac.metamac.common.metadata.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationUiHandlers;
import org.siemac.metamac.common.metadata.web.shared.GetConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.GetConfigurationResult;
import org.siemac.metamac.common.metadata.web.shared.GetOrganisationSchemesAction;
import org.siemac.metamac.common.metadata.web.shared.GetOrganisationSchemesResult;
import org.siemac.metamac.common.metadata.web.shared.GetOrganisationsFromSchemeAction;
import org.siemac.metamac.common.metadata.web.shared.GetOrganisationsFromSchemeResult;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationResult;
import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class ConfigurationPresenter extends Presenter<ConfigurationPresenter.ConfigurationView, ConfigurationPresenter.ConfigurationProxy> implements ConfigurationUiHandlers {

    private final DispatchAsync dispatcher;
    private final PlaceManager  placeManager;

    @ProxyCodeSplit
    @NameToken(NameTokens.configurationPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface ConfigurationProxy extends Proxy<ConfigurationPresenter>, Place {

    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().configuration();
    }

    public interface ConfigurationView extends View, HasUiHandlers<ConfigurationUiHandlers> {

        void setConfiguration(ConfigurationDto configurationDto);
        void setOrganisationSchemes(List<ExternalItemDto> schemes);
        void setOrganisations(List<ExternalItemDto> organisations);
    }

    @Inject
    public ConfigurationPresenter(EventBus eventBus, ConfigurationView configurationView, ConfigurationProxy configurationProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, configurationView, configurationProxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, ConfigurationsPresenter.TYPE_SetContextAreaContentConfiguration, this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        super.prepareFromRequest(request);
        String identifier = PlaceRequestUtils.getConfigurationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(identifier)) {
            String urn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_COMMONMETADATA_PREFIX, identifier);
            retrieveConfiguration(urn);
        } else {
            CommonMetadataWeb.showErrorPage();
        }

        populateOrganisationSchemes(); // TODO remove this call when the application were integrated with SRM
    }

    private void retrieveConfiguration(String urn) {
        dispatcher.execute(new GetConfigurationAction(urn), new WaitingAsyncCallback<GetConfigurationResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorRetrievingConfiguration()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetConfigurationResult result) {
                getView().setConfiguration(result.getConfiguration());
            }
        });
    }

    @Override
    public void saveConfiguration(ConfigurationDto configurationDto) {
        dispatcher.execute(new SaveConfigurationAction(configurationDto), new WaitingAsyncCallback<SaveConfigurationResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorSavingConfiguration()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(SaveConfigurationResult result) {
                ShowMessageEvent.fire(ConfigurationPresenter.this, ErrorUtils.getMessageList(CommonMetadataWeb.getMessages().configurationSaved()), MessageTypeEnum.SUCCESS);
                getView().setConfiguration(result.getConfigurationSaved());
            }
        });
    }

    @Override
    public void populateOrganisations(String organisationSchemeUri) {
        dispatcher.execute(new GetOrganisationsFromSchemeAction(organisationSchemeUri), new WaitingAsyncCallback<GetOrganisationsFromSchemeResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorRetrievingOrganisations()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetOrganisationsFromSchemeResult result) {
                getView().setOrganisations(result.getOrganisations());
            }
        });
    }

    private void populateOrganisationSchemes() {
        dispatcher.execute(new GetOrganisationSchemesAction(), new WaitingAsyncCallback<GetOrganisationSchemesResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorRetrievingOrganisations()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetOrganisationSchemesResult result) {
                getView().setOrganisationSchemes(result.getOrganisationSchemes());
            }
        });
    }
}
