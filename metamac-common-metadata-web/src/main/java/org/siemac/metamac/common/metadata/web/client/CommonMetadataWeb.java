package org.siemac.metamac.common.metadata.web.client;

import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConstants;
import org.siemac.metamac.common.metadata.web.client.gin.CommonMetadataWebGinjector;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.web.common.client.MetamacSecurityEntryPoint;
import org.siemac.metamac.web.common.client.gin.MetamacWebGinjector;

import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CommonMetadataWeb extends MetamacSecurityEntryPoint {

    private static final Boolean                   SECURITY_ENABLED = true;

    private static MetamacPrincipal                principal;
    private static CommonMetadataWebConstants      constants;
    private static CommonMetadataWebMessages       messages;
    private static CommonMetadataWebCoreMessages   coreMessages;

    public static final CommonMetadataWebGinjector ginjector        = GWT.create(CommonMetadataWebGinjector.class);

    @Override
    public void onModuleLoad() {
        setUncaughtExceptionHandler();

        prepareApplication(SECURITY_ENABLED);
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

    public static void showErrorPage() {
        ginjector.getPlaceManager().revealErrorPlace(null);
    }

    // Security Entry point

    @Override
    protected String getApplicationTitle() {
        return getConstants().appTitle();
    }

    @Override
    protected MetamacPrincipal getPrincipal() {
        return CommonMetadataWeb.principal;
    }

    @Override
    protected void setPrincipal(MetamacPrincipal principal) {
        CommonMetadataWeb.principal = principal;
    }

    @Override
    protected String getSecurityApplicationId() {
        return CommonMetadataConstants.SECURITY_APPLICATION_ID;
    }

    @Override
    protected MetamacWebGinjector getWebGinjector() {
        return ginjector;
    }
}
