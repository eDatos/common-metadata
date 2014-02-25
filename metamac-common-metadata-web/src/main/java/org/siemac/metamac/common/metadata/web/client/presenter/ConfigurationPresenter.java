package org.siemac.metamac.common.metadata.web.client.presenter;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;

import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.LoggedInGatekeeper;
import org.siemac.metamac.common.metadata.web.client.events.UpdateConfigurationsEvent;
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
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemSchemesAction;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemSchemesResult;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemsAction;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemsResult;
import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

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
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class ConfigurationPresenter extends Presenter<ConfigurationPresenter.ConfigurationView, ConfigurationPresenter.ConfigurationProxy> implements ConfigurationUiHandlers {

    private final DispatchAsync dispatcher;
    private final PlaceManager  placeManager;

    @ProxyCodeSplit
    @NameToken(NameTokens.commonMetadataPage)
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

        void setAgencySchemes(ExternalItemsResult result);
        void setAgencies(ExternalItemsResult result);
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
        dispatcher.execute(new GetConfigurationAction(urn), new WaitingAsyncCallbackHandlingError<GetConfigurationResult>(this) {

            @Override
            public void onWaitSuccess(GetConfigurationResult result) {
                getView().setConfiguration(result.getConfiguration());
            }
        });
    }

    @Override
    public void saveConfiguration(ConfigurationDto configurationDto) {
        dispatcher.execute(new SaveConfigurationAction(configurationDto), new WaitingAsyncCallbackHandlingError<SaveConfigurationResult>(this) {

            @Override
            public void onWaitSuccess(SaveConfigurationResult result) {
                ShowMessageEvent.fireSuccessMessage(ConfigurationPresenter.this, CommonMetadataWeb.getMessages().configurationSaved());
                getView().setConfiguration(result.getConfigurationSaved());
            }
        });
    }

    @Override
    public void deleteConfiguration(Long configurationId) {
        dispatcher.execute(new DeleteConfigurationsAction(Arrays.asList(configurationId)), new WaitingAsyncCallbackHandlingError<DeleteConfigurationsResult>(this) {

            @Override
            public void onWaitSuccess(DeleteConfigurationsResult result) {
                ShowMessageEvent.fireSuccessMessage(ConfigurationPresenter.this, CommonMetadataWeb.getMessages().configurationDeleted());
                goToConfigurations();
            }
        });
    }

    @Override
    public void publishExternally(Long id) {
        dispatcher.execute(new PublishConfigurationExternallyAction(id), new WaitingAsyncCallbackHandlingError<PublishConfigurationExternallyResult>(this) {

            @Override
            public void onWaitSuccess(PublishConfigurationExternallyResult result) {
                if (result.getNotificationException() != null) {
                    ShowMessageEvent.fireWarningMessageWithError(ConfigurationPresenter.this, CommonMetadataWeb.getMessages().configurationPublishedExternallyWithNotificationError(),
                            result.getNotificationException());
                } else {
                    ShowMessageEvent.fireSuccessMessage(ConfigurationPresenter.this, CommonMetadataWeb.getMessages().configurationPublishedExternally());
                }

                getView().setConfiguration(result.getConfigurationDto());
                UpdateConfigurationsEvent.fire(ConfigurationPresenter.this);
            }
        });
    }

    //
    // EXTERNAL RESOURCES
    //

    @Override
    public void retrieveAgencySchemes(SrmExternalResourceRestCriteria itemSchemeRestCriteria, int firstResult, int maxResults) {
        itemSchemeRestCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.AGENCY_SCHEME);
        dispatcher.execute(new GetSrmItemSchemesAction(itemSchemeRestCriteria, firstResult, maxResults), new WaitingAsyncCallbackHandlingError<GetSrmItemSchemesResult>(this) {

            @Override
            public void onWaitSuccess(GetSrmItemSchemesResult result) {
                getView().setAgencySchemes(result.getResult());
            }

        });
    }

    @Override
    public void retrieveAgencies(SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) {
        itemWebCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.AGENCY);
        dispatcher.execute(new GetSrmItemsAction(itemWebCriteria, firstResult, maxResults), new WaitingAsyncCallbackHandlingError<GetSrmItemsResult>(this) {

            @Override
            public void onWaitSuccess(GetSrmItemsResult result) {
                getView().setAgencies(result.getResult());
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
