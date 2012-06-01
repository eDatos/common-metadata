package org.siemac.metamac.common.metadata.web.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.common.metadata.web.client.gin.CommonMetadataWebGinjector;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.web.common.client.MetamacEntryPoint;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlAction;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlResult;
import org.siemac.metamac.web.common.shared.ValidateTicketAction;
import org.siemac.metamac.web.common.shared.ValidateTicketResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
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

    interface GlobalResources extends ClientBundle {

        @NotStrict
        @Source("resources/CommonMetadataWebStyles.css")
        CssResource css();
    }

    public void onModuleLoad() {
        String ticketParam = Window.Location.getParameter(TICKET);
        if (ticketParam != null) {
            UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
            urlBuilder.removeParameter(TICKET);
            urlBuilder.setHash(Window.Location.getHash() + TICKET_HASH + ticketParam);
            String url = urlBuilder.buildString();
            Window.Location.replace(url);
            return;
        }

        String hash = Window.Location.getHash();

        String ticketHash = null;
        if (hash.contains(TICKET_HASH)) {
            ticketHash = hash.substring(hash.indexOf(TICKET_HASH) + TICKET_HASH.length(), hash.length());
        }

        if (ticketHash == null || ticketHash.length() == 0) {
            displayLoginView();
        } else {
            String serviceUrl = Window.Location.createUrlBuilder().buildString();
            ginjector.getDispatcher().execute(new ValidateTicketAction(ticketHash, serviceUrl), new WaitingAsyncCallback<ValidateTicketResult>() {

                @Override
                public void onWaitFailure(Throwable arg0) {
                    logger.log(Level.SEVERE, "Error validating ticket");
                }
                @Override
                public void onWaitSuccess(ValidateTicketResult result) {
                    CommonMetadataWeb.principal = result.getMetamacPrincipal();

                    String url = Window.Location.createUrlBuilder().setHash("").buildString();
                    Window.Location.assign(url);

                    // This is required for GWT-Platform proxy's generator.
                    DelayedBindRegistry.bind(ginjector);
                    ginjector.getPlaceManager().revealCurrentPlace();

                    // Inject global styles
                    GWT.<GlobalResources> create(GlobalResources.class).css().ensureInjected();
                }
            });
        }
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
