package org.siemac.metamac.common.metadata.web.client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConstants;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataRoleEnum;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.web.common.client.events.LoginAuthenticatedEvent;
import org.siemac.metamac.web.common.client.events.LoginAuthenticatedEvent.LoginAuthenticatedEventHandler;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

public class RoleLoggedInGatekeeper implements Gatekeeper {

    private static final Set<String> rolesAllowed = new HashSet<String>(Arrays.asList(CommonMetadataRoleEnum.ADMINISTRADOR.name(), CommonMetadataRoleEnum.JEFE_NORMALIZACION.name()));

    private static Logger            logger       = Logger.getLogger(LoggedInGatekeeper.class.getName());

    @Inject
    public RoleLoggedInGatekeeper(EventBus eventBus) {
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
            if (CommonMetadataConstants.APPLICATION_ID.equals(access.getApplication()) && isRoleAllowed(access.getRole())) {
                return true;
            }
        }
        return false;
    }

    protected boolean isRoleAllowed(String role) {
        if (rolesAllowed.contains(role)) {
            return true;
        }
        return false;
    }
}
