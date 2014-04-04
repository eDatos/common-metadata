package org.siemac.metamac.common.metadata.web.client.presenter;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.LoggedInGatekeeper;
import org.siemac.metamac.common.metadata.web.client.enums.AppsConfigurationsToolStripButtonEnum;
import org.siemac.metamac.common.metadata.web.client.enums.AppsConfigurationsType;
import org.siemac.metamac.common.metadata.web.client.view.handlers.AppsDefaultValuesUiHandlers;
import org.siemac.metamac.common.metadata.web.client.widgets.events.SelectAppConfigurationSectionEvent;
import org.siemac.metamac.common.metadata.web.shared.GetAppsConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.GetAppsConfigurationsResult;
import org.siemac.metamac.common.metadata.web.shared.SaveAppConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.SaveAppConfigurationResult;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemSchemesAction;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemSchemesResult;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemsAction;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemsResult;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
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
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class AppsDefaultValuesPresenter extends Presenter<AppsDefaultValuesPresenter.AppsDefaultValuesView, AppsDefaultValuesPresenter.AppsDefaultValuesProxy> implements AppsDefaultValuesUiHandlers {

    private final DispatchAsync dispatcher;

    @ProxyCodeSplit
    @NameToken(NameTokens.DEFAULT_VALUES_LIST_PAGE)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface AppsDefaultValuesProxy extends Proxy<AppsDefaultValuesPresenter>, Place {
    }

    public interface AppsDefaultValuesView extends View, HasUiHandlers<AppsDefaultValuesUiHandlers> {

        void setAppConfigurations(List<DataConfigurationDto> properties);

        void setConcepts(ExternalItemsResult externalItemsResult);

        void setConceptSchemes(ExternalItemsResult externalItemsResult);

        void setCodes(ExternalItemsResult result);

        void setCodelists(ExternalItemsResult result, ViewActionHandlers viewHandler);

        void selectAppConfiguration(DataConfigurationDto appConfiguration);
    }

    public enum ViewActionHandlers {
        CODELISTS_FILTER_FOR_CODES, CODELISTS_FILTER,
    }

    @Inject
    public AppsDefaultValuesPresenter(EventBus eventBus, AppsDefaultValuesView view, AppsDefaultValuesProxy proxy, DispatchAsync dispatcher) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().defaultValues();
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, AppsConfigurationsPresenter.TYPE_SetAppsConfigurationsContent, this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        retrieveDefaultValuesPropertiesAndSelect(null);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        SelectAppConfigurationSectionEvent.fire(this, AppsConfigurationsToolStripButtonEnum.DEFAULT_VALUES);
    }

    private void retrieveDefaultValuesPropertiesAndSelect(final DataConfigurationDto appConfiguration) {
        dispatcher.execute(new GetAppsConfigurationsAction(AppsConfigurationsType.DEFAULT_VALUES), new WaitingAsyncCallbackHandlingError<GetAppsConfigurationsResult>(this) {

            @Override
            public void onWaitSuccess(GetAppsConfigurationsResult result) {
                getView().setAppConfigurations(result.getProperties());
                getView().selectAppConfiguration(appConfiguration);
            }
        });
    }

    // UI HANDLERS
    @Override
    public void retrieveConcepts(SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) {
        itemWebCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.CONCEPT);
        dispatcher.execute(new GetSrmItemsAction(itemWebCriteria, firstResult, maxResults), new WaitingAsyncCallbackHandlingError<GetSrmItemsResult>(this) {

            @Override
            public void onWaitSuccess(GetSrmItemsResult result) {
                getView().setConcepts(result.getResult());
            }
        });
    }

    @Override
    public void retrieveConceptSchemes(SrmExternalResourceRestCriteria srmItemSchemeRestCriteria, int firstResult, int maxResults) {
        srmItemSchemeRestCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.CONCEPT_SCHEME);
        dispatcher.execute(new GetSrmItemSchemesAction(srmItemSchemeRestCriteria, firstResult, maxResults), new WaitingAsyncCallbackHandlingError<GetSrmItemSchemesResult>(this) {

            @Override
            public void onWaitSuccess(GetSrmItemSchemesResult result) {
                getView().setConceptSchemes(result.getResult());
            }
        });
    }

    @Override
    public void retrieveCodelists(SrmExternalResourceRestCriteria srmItemSchemeRestCriteria, int firstResult, int maxResults, final ViewActionHandlers viewHandler) {
        srmItemSchemeRestCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.CODELIST);
        dispatcher.execute(new GetSrmItemSchemesAction(srmItemSchemeRestCriteria, firstResult, maxResults), new WaitingAsyncCallbackHandlingError<GetSrmItemSchemesResult>(this) {

            @Override
            public void onWaitSuccess(GetSrmItemSchemesResult result) {
                getView().setCodelists(result.getResult(), viewHandler);
            }
        });
    }

    @Override
    public void retrieveCodes(SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) {
        itemWebCriteria.setExternalArtifactType(TypeExternalArtefactsEnum.CODE);
        dispatcher.execute(new GetSrmItemsAction(itemWebCriteria, firstResult, maxResults), new WaitingAsyncCallbackHandlingError<GetSrmItemsResult>(this) {

            @Override
            public void onWaitSuccess(GetSrmItemsResult result) {
                getView().setCodes(result.getResult());
            }
        });
    }

    @Override
    public void saveDataCondiguration(DataConfigurationDto dataConfigurationDto) {
        dispatcher.execute(new SaveAppConfigurationAction(dataConfigurationDto), new WaitingAsyncCallbackHandlingError<SaveAppConfigurationResult>(this) {

            @Override
            public void onWaitSuccess(SaveAppConfigurationResult result) {
                ShowMessageEvent.fireSuccessMessage(AppsDefaultValuesPresenter.this, CommonMetadataWeb.getMessages().appConfigurationSaved());
                retrieveDefaultValuesPropertiesAndSelect(result.getPropertySaved());
            }
        });

    }
}
