package org.siemac.metamac.common.metadata.web.client.presenter;

import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.NameTokens;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationUiHandlers;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsResult;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class ConfigurationPresenter extends Presenter<ConfigurationPresenter.ConfigurationView, ConfigurationPresenter.ConfigurationProxy> implements ConfigurationUiHandlers {

	private final PlaceManager placeManager;
	private final DispatchAsync dispatcher;
	
	@ProxyCodeSplit
	@NameToken(NameTokens.configurationPage)
	public interface ConfigurationProxy extends Proxy<ConfigurationPresenter>, Place {

	}
	
	@TitleFunction
	public static String getTranslatedTitle() {
		return CommonMetadataWeb.getConstants().home();
	}

	public interface ConfigurationView extends View, HasUiHandlers<ConfigurationUiHandlers> {

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
		populateConfigurations();
	}
	
	private void populateConfigurations() {
		dispatcher.execute(new FindAllConfigurationsAction(), new AsyncCallback<FindAllConfigurationsResult>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println();
			}
			@Override
			public void onSuccess(FindAllConfigurationsResult result) {
				System.out.println();
			}
		});
	}
	
}
