package org.siemac.metamac.common.metadata.web.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConstants;
import org.siemac.metamac.domain.common.metadata.enume.domain.CommonMetadataRoleEnum;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.web.common.client.events.LoginAuthenticatedEvent;
import org.siemac.metamac.web.common.client.events.LoginAuthenticatedEvent.LoginAuthenticatedEventHandler;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

public class LoggedInGatekeeper implements Gatekeeper {

    private static Logger logger = Logger.getLogger(LoggedInGatekeeper.class.getName());

    @Inject
    public LoggedInGatekeeper(EventBus eventBus) {
        eventBus.addHandler(LoginAuthenticatedEvent.getType(), new LoginAuthenticatedEventHandler() {

            @Override
            public void onLogin(LoginAuthenticatedEvent event) {
                logger.log(Level.INFO, event.getMetamacPrincipal() + " " + " credentials have been authenticated.");
            }
        });
    }

    @Override
    public boolean canReveal() {
        return hasAnyAllowedRole(CommonMetadataWeb.getCurrentUser());
    }

    private boolean hasAnyAllowedRole(MetamacPrincipal metamacPrincipal) {
        for (MetamacPrincipalAccess access : metamacPrincipal.getAccesses()) {
            if (CommonMetadataConstants.SECURITY_APPLICATION_ID.equals(access.getApplication()) && isRoleAllowed(access.getRole())) {
                return true;
            }
        }
        return false;
    }

    private boolean isRoleAllowed(String role) {
        for (CommonMetadataRoleEnum roleEnum : CommonMetadataRoleEnum.values()) {
            if (roleEnum.toString().equals(role)) {
                return true;
            }
        }
        return false;
    }

}
