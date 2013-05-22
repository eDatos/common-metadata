package org.siemac.metamac.common.metadata.web.client.presenter;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.LoggedInGatekeeper;
import org.siemac.metamac.common.metadata.web.client.utils.ErrorUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationsUiHandlers;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListAction;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListResult;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsResult;
import org.siemac.metamac.common.metadata.web.shared.FindAllOrganisationSchemesAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllOrganisationSchemesResult;
import org.siemac.metamac.common.metadata.web.shared.GetOrganisationsFromSchemeAction;
import org.siemac.metamac.common.metadata.web.shared.GetOrganisationsFromSchemeResult;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationResult;
import org.siemac.metamac.common.metadata.web.shared.UpdateConfigurationsStatusAction;
import org.siemac.metamac.common.metadata.web.shared.UpdateConfigurationsStatusResult;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
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
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class ConfigurationsPresenter extends Presenter<ConfigurationsPresenter.ConfigurationsView, ConfigurationsPresenter.ConfigurationsProxy> implements ConfigurationsUiHandlers {

    private final DispatchAsync dispatcher;

    @ProxyCodeSplit
    @NameToken(NameTokens.configurationListPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface ConfigurationsProxy extends Proxy<ConfigurationsPresenter>, Place {

    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().configurations();
    }

    public interface ConfigurationsView extends View, HasUiHandlers<ConfigurationsUiHandlers> {

        void setConfigurations(List<ConfigurationDto> configurations);
        void setConfiguration(ConfigurationDto configurationDto);
        void setOrganisationSchemes(List<ExternalItemDto> schemes);
        void setOrganisations(List<ExternalItemDto> organisations);
        ConfigurationDto getConfiguration();
        List<Long> getSelectedConfigurations();
        boolean validate();
        HasClickHandlers getDelete();
        void onConfigurationSaved(ConfigurationDto configurationDto);
    }

    @Inject
    public ConfigurationsPresenter(EventBus eventBus, ConfigurationsView configurationView, ConfigurationsProxy configurationProxy, DispatchAsync dispatcher) {
        super(eventBus, configurationView, configurationProxy);
        this.dispatcher = dispatcher;
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
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        populateOrganisationSchemes();
        populateConfigurations();
    }

    private void populateConfigurations() {
        dispatcher.execute(new FindAllConfigurationsAction(), new WaitingAsyncCallback<FindAllConfigurationsResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorRetrievingConfigurations()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(FindAllConfigurationsResult result) {
                getView().setConfigurations(result.getConfigurations());
            }
        });
    }

    @Override
    public void saveConfiguration(ConfigurationDto configurationDto) {
        dispatcher.execute(new SaveConfigurationAction(configurationDto), new WaitingAsyncCallback<SaveConfigurationResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorSavingConfiguration()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(SaveConfigurationResult result) {
                getView().onConfigurationSaved(result.getConfigurationSaved());
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getMessageList(CommonMetadataWeb.getMessages().configurationSaved()), MessageTypeEnum.SUCCESS);
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
                populateConfigurations();
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getMessageList(CommonMetadataWeb.getMessages().configurationDeleted()), MessageTypeEnum.SUCCESS);
            }
        });
    }

    private void populateOrganisationSchemes() {
        dispatcher.execute(new FindAllOrganisationSchemesAction(), new WaitingAsyncCallback<FindAllOrganisationSchemesResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorRetrievingOrganisations()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(FindAllOrganisationSchemesResult result) {
                getView().setOrganisationSchemes(result.getOrganisationSchemes());
            }
        });
    }

    @Override
    public void populateOrganisations(String organisationSchemeUri) {
        dispatcher.execute(new GetOrganisationsFromSchemeAction(organisationSchemeUri), new WaitingAsyncCallback<GetOrganisationsFromSchemeResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorRetrievingOrganisations()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetOrganisationsFromSchemeResult result) {
                getView().setOrganisations(result.getOrganisations());
            }
        });
    }

    @Override
    public void updateConfigurationsStatus(List<Long> configurationIds, CommonMetadataStatusEnum statusEnum) {
        dispatcher.execute(new UpdateConfigurationsStatusAction(configurationIds, statusEnum), new WaitingAsyncCallback<UpdateConfigurationsStatusResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorUpdatingConfigurationStatus()), MessageTypeEnum.ERROR);
                populateConfigurations();
            }

            @Override
            public void onWaitSuccess(UpdateConfigurationsStatusResult result) {
                ShowMessageEvent.fire(ConfigurationsPresenter.this, ErrorUtils.getMessageList(CommonMetadataWeb.getMessages().configurationStatusUpdated()), MessageTypeEnum.SUCCESS);
                populateConfigurations();
            }
        });
    }
}