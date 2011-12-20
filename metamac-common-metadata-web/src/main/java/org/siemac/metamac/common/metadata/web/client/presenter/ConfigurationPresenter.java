package org.siemac.metamac.common.metadata.web.client.presenter;

import java.util.List;

import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.NameTokens;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationUiHandlers;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListAction;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListResult;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsResult;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationResult;

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
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class ConfigurationPresenter extends Presenter<ConfigurationPresenter.ConfigurationView, ConfigurationPresenter.ConfigurationProxy> implements ConfigurationUiHandlers {

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
		void setConfigurations(List<ConfigurationDto> configurations);
		void setConfiguration(ConfigurationDto configurationDto);
		ConfigurationDto getConfiguration();
		List<ConfigurationDto> getSelectedConfigurations();
		boolean validate();
		HasClickHandlers getSave();
		HasClickHandlers getDelete();
		void onConfigurationSaved(ConfigurationDto configurationDto);
	}
	
	@Inject
	public ConfigurationPresenter(EventBus eventBus, ConfigurationView configurationView, ConfigurationProxy configurationProxy, DispatchAsync dispatcher) {
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
		populateConfigurations();
	}
	
	@Override
	protected void onBind() {
		registerHandler(getView().getSave().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (getView().validate()) {
					saveConfiguration(getView().getConfiguration());
				}
			}
		}));
		
		registerHandler(getView().getDelete().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deleteConfigurations(getView().getSelectedConfigurations());
			}
		}));
	}
	
	private void populateConfigurations() {
		dispatcher.execute(new FindAllConfigurationsAction(), new AsyncCallback<FindAllConfigurationsResult>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO
				System.err.println("ERROR!");
			}
			@Override
			public void onSuccess(FindAllConfigurationsResult result) {
				getView().setConfigurations(result.getConfigurations());
			}
		});
	}
	
	private void saveConfiguration(ConfigurationDto configurationDto) {
		dispatcher.execute(new SaveConfigurationAction(configurationDto), new AsyncCallback<SaveConfigurationResult>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO
				System.err.println("ERROR!");
			}
			@Override
			public void onSuccess(SaveConfigurationResult result) {
				getView().onConfigurationSaved(result.getConfigurationSaved());
			}
		});
	}
	
	private void deleteConfigurations(List<ConfigurationDto> configurationDtos) {
		dispatcher.execute(new DeleteConfigurationListAction(configurationDtos), new AsyncCallback<DeleteConfigurationListResult>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO
				System.err.println("ERROR!");
			}
			@Override
			public void onSuccess(DeleteConfigurationListResult result) {
				populateConfigurations();
			}
		});
	}

}
