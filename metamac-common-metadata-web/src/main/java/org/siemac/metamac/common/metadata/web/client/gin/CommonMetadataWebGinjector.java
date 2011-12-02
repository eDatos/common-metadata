package org.siemac.metamac.common.metadata.web.client.gin;

import org.siemac.metamac.common.metadata.web.client.CommonMetadataWebConstants;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWebMessages;
import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationPresenter;
import org.siemac.metamac.common.metadata.web.client.presenter.MainPagePresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

@GinModules({DispatchAsyncModule.class, ClientModule.class})
public interface CommonMetadataWebGinjector extends Ginjector {
	
	PlaceManager getPlaceManager();
	EventBus getEventBus();
		
	Provider<MainPagePresenter> getMainPagePresenter();
	AsyncProvider<ConfigurationPresenter> getConfigurationPresenter();

	// Interfaces
	public CommonMetadataWebConstants getCommonMetadataWebConstants();
	public CommonMetadataWebMessages getCommonMetadataWebMessages();

}
