package org.siemac.metamac.common.metadata.web.client.gin;

import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataPlaceManager;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWebConstants;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWebMessages;
import org.siemac.metamac.common.metadata.web.client.LoggedInGatekeeper;
import org.siemac.metamac.common.metadata.web.client.RoleLoggedInGatekeeper;
import org.siemac.metamac.common.metadata.web.client.presenter.AppsConfigurationsPresenter;
import org.siemac.metamac.common.metadata.web.client.presenter.AppsDefaultValuesPresenter;
import org.siemac.metamac.common.metadata.web.client.presenter.AppsSystemPropertiesPresenter;
import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationPresenter;
import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationsPresenter;
import org.siemac.metamac.common.metadata.web.client.presenter.ErrorPagePresenter;
import org.siemac.metamac.common.metadata.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.common.metadata.web.client.presenter.UnauthorizedPagePresenter;
import org.siemac.metamac.common.metadata.web.client.view.AppsConfigurationsViewImpl;
import org.siemac.metamac.common.metadata.web.client.view.AppsDefaultValuesViewImpl;
import org.siemac.metamac.common.metadata.web.client.view.AppsSystemPropertiesViewImpl;
import org.siemac.metamac.common.metadata.web.client.view.ConfigurationViewImpl;
import org.siemac.metamac.common.metadata.web.client.view.ConfigurationsViewImpl;
import org.siemac.metamac.common.metadata.web.client.view.ErrorPageViewImpl;
import org.siemac.metamac.common.metadata.web.client.view.MainPageViewImpl;
import org.siemac.metamac.common.metadata.web.client.view.UnauthorizedPageViewImpl;
import org.siemac.metamac.common.metadata.web.client.widgets.presenter.AppsConfigurationsToolStripPresenterWidget;
import org.siemac.metamac.common.metadata.web.client.widgets.presenter.CommonMetadataToolStripPresenterWidget;
import org.siemac.metamac.common.metadata.web.client.widgets.view.AppsConfigurationsToolStripViewImpl;
import org.siemac.metamac.common.metadata.web.client.widgets.view.CommonMetadataToolStripViewImpl;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class ClientModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        // Default implementation of standard resources
        // |_ bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        // |_ bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
        // |_ bind(RootPresenter.class).asEagerSingleton();
        // |_ bind(PlaceManager.class).to(MyPlaceManager.class).in(Singleton.class);
        // |_ bind(GoogleAnalytics.class).to(GoogleAnalyticsImpl.class).in(Singleton.class);
        install(new DefaultModule(CommonMetadataPlaceManager.class));

        // Gate keeper
        bind(LoggedInGatekeeper.class).in(Singleton.class);
        bind(RoleLoggedInGatekeeper.class).in(Singleton.class);

        // Constants
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.commonMetadataListPage);

        // Presenters
        bindPresenter(MainPagePresenter.class, MainPagePresenter.MainPageView.class, MainPageViewImpl.class, MainPagePresenter.MainPageProxy.class);
        bindPresenter(ConfigurationsPresenter.class, ConfigurationsPresenter.ConfigurationsView.class, ConfigurationsViewImpl.class, ConfigurationsPresenter.ConfigurationsProxy.class);
        bindPresenter(ConfigurationPresenter.class, ConfigurationPresenter.ConfigurationView.class, ConfigurationViewImpl.class, ConfigurationPresenter.ConfigurationProxy.class);
        bindPresenter(AppsConfigurationsPresenter.class, AppsConfigurationsPresenter.AppsConfigurationsView.class, AppsConfigurationsViewImpl.class,
                AppsConfigurationsPresenter.AppsConfigurationsProxy.class);
        bindPresenter(AppsSystemPropertiesPresenter.class, AppsSystemPropertiesPresenter.AppsSystemPropertiesView.class, AppsSystemPropertiesViewImpl.class,
                AppsSystemPropertiesPresenter.AppsSystemPropertiesProxy.class);
        bindPresenter(AppsDefaultValuesPresenter.class, AppsDefaultValuesPresenter.AppsDefaultValuesView.class, AppsDefaultValuesViewImpl.class,
                AppsDefaultValuesPresenter.AppsDefaultValuesProxy.class);

        // Error pages
        bindPresenter(ErrorPagePresenter.class, ErrorPagePresenter.ErrorPageView.class, ErrorPageViewImpl.class, ErrorPagePresenter.ErrorPageProxy.class);
        bindPresenter(UnauthorizedPagePresenter.class, UnauthorizedPagePresenter.UnauthorizedPageView.class, UnauthorizedPageViewImpl.class, UnauthorizedPagePresenter.UnauthorizedPageProxy.class);

        // Presenter widgets
        bindSingletonPresenterWidget(CommonMetadataToolStripPresenterWidget.class, CommonMetadataToolStripPresenterWidget.CommonMetadataToolStripView.class, CommonMetadataToolStripViewImpl.class);
        bindSingletonPresenterWidget(AppsConfigurationsToolStripPresenterWidget.class, AppsConfigurationsToolStripPresenterWidget.AppsConfigurationsToolStripView.class,
                AppsConfigurationsToolStripViewImpl.class);

        // Interfaces
        bind(CommonMetadataWebConstants.class).in(Singleton.class);
        bind(CommonMetadataWebMessages.class).in(Singleton.class);
    }
}
