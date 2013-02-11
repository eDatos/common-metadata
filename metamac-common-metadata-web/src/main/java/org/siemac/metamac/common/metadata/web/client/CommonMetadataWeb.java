package org.siemac.metamac.common.metadata.web.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.common.metadata.web.client.gin.CommonMetadataWebGinjector;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.web.common.client.MetamacEntryPoint;
import org.siemac.metamac.web.common.client.events.LoginAuthenticatedEvent;
import org.siemac.metamac.web.common.client.utils.ApplicationEditionLanguages;
import org.siemac.metamac.web.common.client.utils.ApplicationOrganisation;
import org.siemac.metamac.web.common.client.widgets.MetamacNavBar;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlAction;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlResult;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlAction;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlResult;
import org.siemac.metamac.web.common.shared.LoadConfigurationPropertiesAction;
import org.siemac.metamac.web.common.shared.LoadConfigurationPropertiesResult;
import org.siemac.metamac.web.common.shared.MockCASUserAction;
import org.siemac.metamac.web.common.shared.MockCASUserResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CommonMetadataWeb extends MetamacEntryPoint {

    private static Logger                          logger    = Logger.getLogger(CommonMetadataWeb.class.getName());

    private static MetamacPrincipal                principal;
    private static CommonMetadataWebConstants      constants;
    private static CommonMetadataWebMessages       messages;
    private static CommonMetadataWebCoreMessages   coreMessages;

    public static final CommonMetadataWebGinjector ginjector = GWT.create(CommonMetadataWebGinjector.class);

    @Override
    public void onModuleLoad() {
        ginjector.getDispatcher().execute(new GetNavigationBarUrlAction(), new WaitingAsyncCallback<GetNavigationBarUrlResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error loading toolbar");
                loadNonSecuredApplication();
            }

            @Override
            public void onWaitSuccess(GetNavigationBarUrlResult result) {
                // Load scripts for navigation bar
                MetamacNavBar.loadScripts(result.getNavigationBarUrl());
                loadNonSecuredApplication();
            };
        });

    }

    // TODO This method should be removed to use CAS authentication
    // Application id should be the same than the one defined in org.siemac.metamac.common.metadata.core.constants.SECURITY_APPLICATION_ID
    private void loadNonSecuredApplication() {
        ginjector.getDispatcher().execute(new MockCASUserAction("GESTOR_METADATOS_COMUNES"), new WaitingAsyncCallback<MockCASUserResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error mocking CAS user");
            }
            @Override
            public void onWaitSuccess(MockCASUserResult result) {
                CommonMetadataWeb.principal = result.getMetamacPrincipal();

                // Load edition languages
                ginjector.getDispatcher().execute(new LoadConfigurationPropertiesAction(), new WaitingAsyncCallback<LoadConfigurationPropertiesResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        logger.log(Level.SEVERE, "Error loading edition languages");
                        // If an error occurs while loading edition languages, enable SPANISH, ENGLISH and PORTUGUESE by default
                        ApplicationEditionLanguages.setEditionLanguages(new String[]{ApplicationEditionLanguages.SPANISH, ApplicationEditionLanguages.ENGLISH, ApplicationEditionLanguages.PORTUGUESE});
                        loadApplication();
                    }
                    @Override
                    public void onWaitSuccess(LoadConfigurationPropertiesResult result) {
                        ApplicationEditionLanguages.setEditionLanguages(result.getLanguages());
                        ApplicationOrganisation.setCurrentOrganisation(result.getOrganisation());
                        loadApplication();
                    }
                });
            }
        });
    }

    // private void loadSecuredApplication() {
    // String ticketParam = Window.Location.getParameter(TICKET);
    // if (ticketParam != null) {
    // UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
    // urlBuilder.removeParameter(TICKET);
    // urlBuilder.setHash(Window.Location.getHash() + TICKET_HASH + ticketParam);
    // String url = urlBuilder.buildString();
    // Window.Location.replace(url);
    // return;
    // }
    //
    // String hash = Window.Location.getHash();
    //
    // String ticketHash = null;
    // if (hash.contains(TICKET_HASH)) {
    // ticketHash = hash.substring(hash.indexOf(TICKET_HASH) + TICKET_HASH.length(), hash.length());
    // }
    //
    // if (ticketHash == null || ticketHash.length() == 0) {
    // displayLoginView();
    // } else {
    // String serviceUrl = Window.Location.createUrlBuilder().buildString();
    // ginjector.getDispatcher().execute(new ValidateTicketAction(ticketHash, serviceUrl), new WaitingAsyncCallback<ValidateTicketResult>() {
    //
    // @Override
    // public void onWaitFailure(Throwable arg0) {
    // logger.log(Level.SEVERE, "Error validating ticket");
    // }
    // @Override
    // public void onWaitSuccess(ValidateTicketResult result) {
    // CommonMetadataWeb.principal = result.getMetamacPrincipal();
    //
    // String url = Window.Location.createUrlBuilder().setHash("").buildString();
    // Window.Location.assign(url);
    //
    // // Load edition languages
    // ginjector.getDispatcher().execute(new LoadConfigurationPropertiesAction(), new WaitingAsyncCallback<LoadConfigurationPropertiesResult>() {
    //
    // @Override
    // public void onWaitFailure(Throwable caught) {
    // logger.log(Level.SEVERE, "Error loading edition languages");
    // // If an error occurs while loading edition languages, enable SPANISH, ENGLISH and PORTUGUESE by default
    // ApplicationEditionLanguages.setEditionLanguages(new String[]{ApplicationEditionLanguages.SPANISH, ApplicationEditionLanguages.ENGLISH,
    // ApplicationEditionLanguages.PORTUGUESE});
    // loadApplication();
    // }
    // @Override
    // public void onWaitSuccess(LoadConfigurationPropertiesResult result) {
    // ApplicationEditionLanguages.setEditionLanguages(result.getLanguages());
    // ApplicationOrganisation.setCurrentOrganisation(result.getOrganisation());
    // loadApplication();
    // }
    // });
    // }
    // });
    // }
    // }

    private void loadApplication() {
        setUncaughtExceptionHandler();
        LoginAuthenticatedEvent.fire(ginjector.getEventBus(), CommonMetadataWeb.principal);
        // This is required for GWT-Platform proxy's generator.
        DelayedBindRegistry.bind(ginjector);
        ginjector.getPlaceManager().revealCurrentPlace();
    }

    public void displayLoginView() {
        String serviceUrl = Window.Location.createUrlBuilder().buildString();
        ginjector.getDispatcher().execute(new GetLoginPageUrlAction(serviceUrl), new WaitingAsyncCallback<GetLoginPageUrlResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error getting login page URL");
            }
            @Override
            public void onWaitSuccess(GetLoginPageUrlResult result) {
                Window.Location.replace(result.getLoginPageUrl());
            }
        });
    }

    public static MetamacPrincipal getCurrentUser() {
        return CommonMetadataWeb.principal;
    }

    public static CommonMetadataWebConstants getConstants() {
        if (constants == null) {
            constants = (CommonMetadataWebConstants) GWT.create(CommonMetadataWebConstants.class);
        }
        return constants;
    }

    public static CommonMetadataWebMessages getMessages() {
        if (messages == null) {
            messages = (CommonMetadataWebMessages) GWT.create(CommonMetadataWebMessages.class);
        }
        return messages;
    }

    public static CommonMetadataWebCoreMessages getCoreMessages() {
        if (coreMessages == null) {
            coreMessages = (CommonMetadataWebCoreMessages) GWT.create(CommonMetadataWebCoreMessages.class);
        }
        return coreMessages;
    }

    public static CommonMetadataWebGinjector getCommonMetadataWebGinjector() {
        return ginjector;
    }
}
