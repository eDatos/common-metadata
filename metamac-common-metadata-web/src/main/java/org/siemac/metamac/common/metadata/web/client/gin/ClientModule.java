package org.siemac.metamac.common.metadata.web.client.gin;

import org.siemac.metamac.common.metadata.web.client.CommonMetadataPlaceManager;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWebConstants;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWebMessages;
import org.siemac.metamac.common.metadata.web.client.NameTokens;
import org.siemac.metamac.common.metadata.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.common.metadata.web.client.view.MainPageViewImpl;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
	    // Default implementation of standard resources
		//	 |_   bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		//	 |_   bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
		//	 |_   bind(RootPresenter.class).asEagerSingleton();
		//	 |_   bind(PlaceManager.class).to(MyPlaceManager.class).in(Singleton.class);
		//	 |_   bind(GoogleAnalytics.class).to(GoogleAnalyticsImpl.class).in(Singleton.class);		
	    install(new DefaultModule(CommonMetadataPlaceManager.class));
	    
	    // Constants
	    bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.mainPage);
	    		
		// Presenters
	    bindPresenter(MainPagePresenter.class, MainPagePresenter.MainPageView.class, MainPageViewImpl.class, MainPagePresenter.MainPageProxy.class);
	    
		// Interfaces
		bind(CommonMetadataWebConstants.class).in(Singleton.class);
		bind(CommonMetadataWebMessages.class).in(Singleton.class);
	}
	
}
