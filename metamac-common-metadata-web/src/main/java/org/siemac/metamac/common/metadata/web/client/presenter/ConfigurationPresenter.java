package org.siemac.metamac.common.metadata.web.client.presenter;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;
import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getMessages;

import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.LoggedInGatekeeper;
import org.siemac.metamac.common.metadata.web.client.events.UpdateConfigurationsEvent;
import org.siemac.metamac.common.metadata.web.client.utils.ErrorUtils;
import org.siemac.metamac.common.metadata.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationUiHandlers;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationsResult;
import org.siemac.metamac.common.metadata.web.shared.GetConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.GetConfigurationResult;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyAction;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyResult;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationResult;
import org.siemac.metamac.common.metadata.web.shared.external.GetExternalResourcesAction;
import org.siemac.metamac.common.metadata.web.shared.external.GetExternalResourcesResult;
import org.siemac.metamac.common.metadata.web.shared.external.RestWebCriteriaUtils;
import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;
import org.siemac.metamac.web.common.shared.criteria.ExternalResourceWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemWebCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

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

        // External resources

        void setItemSchemes(String formItemName, ExternalItemsResult result);
        void setItems(String formItemName, ExternalItemsResult result);
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
    public void deleteConfiguration(Long configurationId) {
        dispatcher.execute(new DeleteConfigurationsAction(Arrays.asList(configurationId)), new WaitingAsyncCallback<DeleteConfigurationsResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorDeletingConfigurations()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(DeleteConfigurationsResult result) {
                ShowMessageEvent.fire(ConfigurationPresenter.this, ErrorUtils.getMessageList(CommonMetadataWeb.getMessages().configurationDeleted()), MessageTypeEnum.SUCCESS);
                goToConfigurations();
            }
        });
    }

    @Override
    public void publishExternally(Long id) {
        dispatcher.execute(new PublishConfigurationExternallyAction(id), new WaitingAsyncCallback<PublishConfigurationExternallyResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationPresenter.this, ErrorUtils.getErrorMessages(caught, CommonMetadataWeb.getMessages().errorPublishingConfigurationExternally()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(PublishConfigurationExternallyResult result) {
                ShowMessageEvent.fire(ConfigurationPresenter.this, ErrorUtils.getMessageList(CommonMetadataWeb.getMessages().configurationPublishedExternally()), MessageTypeEnum.SUCCESS);
                getView().setConfiguration(result.getConfigurationDto());
                UpdateConfigurationsEvent.fire(ConfigurationPresenter.this);
            }
        });
    }

    //
    // EXTERNAL RESOURCES
    //

    @Override
    public void retrieveItemSchemes(final String formItemName, TypeExternalArtefactsEnum[] types, int firstResult, int maxResults, String criteria) {
        ExternalResourceWebCriteria externalResourceWebCriteria = RestWebCriteriaUtils.buildItemSchemeWebCriteria(types, criteria);
        retrieveItemSchemes(formItemName, externalResourceWebCriteria, firstResult, maxResults);
    }

    @Override
    public void retrieveItemSchemes(final String formItemName, ExternalResourceWebCriteria externalResourceWebCriteria, int firstResult, int maxResults) {
        dispatcher.execute(new GetExternalResourcesAction(externalResourceWebCriteria, firstResult, maxResults), new WaitingAsyncCallback<GetExternalResourcesResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().errorRetrievingExternalStructuralResources()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetExternalResourcesResult result) {
                getView().setItemSchemes(formItemName, result.getExternalItemsResult());
            }
        });
    }

    @Override
    public void retrieveItems(final String formItemName, TypeExternalArtefactsEnum[] types, int firstResult, int maxResults, String criteria, String itemSchemeUrn) {
        SrmItemWebCriteria itemWebCriteria = RestWebCriteriaUtils.buildItemWebCriteria(types, criteria, itemSchemeUrn);
        retrieveItems(formItemName, itemWebCriteria, firstResult, maxResults);
    }

    @Override
    public void retrieveItems(final String formItemName, SrmItemWebCriteria itemWebCriteria, int firstResult, int maxResults) {
        dispatcher.execute(new GetExternalResourcesAction(itemWebCriteria, firstResult, maxResults), new WaitingAsyncCallback<GetExternalResourcesResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(ConfigurationPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().errorRetrievingExternalStructuralResources()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetExternalResourcesResult result) {
                getView().setItems(formItemName, result.getExternalItemsResult());
            }
        });
    }

    //
    // NAVIGATION
    //

    private void goToConfigurations() {
        placeManager.revealRelativePlace(-1);
    }

    @Override
    public void goTo(List<PlaceRequest> location) {
        if (location != null && !location.isEmpty()) {
            placeManager.revealPlaceHierarchy(location);
        }
    }
}
