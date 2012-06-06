package org.siemac.metamac.common.metadata.web.client.utils;

import static org.siemac.metamac.domain.common.metadata.enume.domain.CommonMetadataRoleEnum.JEFE_NORMALIZACION;

import org.siemac.metamac.common.metadata.core.security.shared.SharedSecurityUtils;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.domain.common.metadata.enume.domain.CommonMetadataRoleEnum;
import org.siemac.metamac.sso.client.MetamacPrincipal;

public class ClientSecurityUtils {

    public static boolean canCreateConfiguration() {
        CommonMetadataRoleEnum[] roles = {JEFE_NORMALIZACION};
        if (isRoleAllowed(roles)) {
            return true;
        }
        return false;
    }

    public static boolean canUpdateConfiguration() {
        CommonMetadataRoleEnum[] roles = {JEFE_NORMALIZACION};
        if (isRoleAllowed(roles)) {
            return true;
        }
        return false;
    }

    public static boolean canDeleteConfiguration() {
        CommonMetadataRoleEnum[] roles = {JEFE_NORMALIZACION};
        if (isRoleAllowed(roles)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if logged user has one of the allowed roles
     * 
     * @param roles
     * @return
     */
    private static boolean isRoleAllowed(CommonMetadataRoleEnum... roles) {
        MetamacPrincipal userPrincipal = CommonMetadataWeb.getCurrentUser();
        // Administration has total control
        if (SharedSecurityUtils.isAdministrador(userPrincipal)) {
            return true;
        }
        // Checks user has any role of requested
        if (roles != null) {
            for (int i = 0; i < roles.length; i++) {
                CommonMetadataRoleEnum role = roles[i];
                if (SharedSecurityUtils.isUserInRol(userPrincipal, role)) {
                    return true;
                }
            }
        }
        return false;
    }

}
