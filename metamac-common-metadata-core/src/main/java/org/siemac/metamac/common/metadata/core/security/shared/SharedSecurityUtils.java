package org.siemac.metamac.common.metadata.core.security.shared;

import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConstants;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataRoleEnum;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;

public class SharedSecurityUtils {

    /**
     * Checks user has requested role
     */
    public static boolean isUserInRol(MetamacPrincipal metamacPrincipal, CommonMetadataRoleEnum role) {

        if (CommonMetadataRoleEnum.ANY_ROLE_ALLOWED.equals(role)) {
            return isAnyCommonMetadataRole(metamacPrincipal);
        } else {
            return isRoleInAccesses(metamacPrincipal, role);
        }
    }

    /**
     * Checks if user has access with role
     */
    public static Boolean isRoleInAccesses(MetamacPrincipal metamacPrincipal, CommonMetadataRoleEnum role) {
        for (MetamacPrincipalAccess metamacPrincipalAccess : metamacPrincipal.getAccesses()) {
            if (CommonMetadataConstants.APPLICATION_ID.equals(metamacPrincipalAccess.getApplication()) && metamacPrincipalAccess.getRole().equals(role.name())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static Boolean isAnyCommonMetadataRole(MetamacPrincipal metamacPrincipal) {
        return isAdministrador(metamacPrincipal) || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_PLANIFICACION)
                || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.JEFE_NORMALIZACION) || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_APOYO_DIFUSION)
                || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_APOYO_NORMALIZACION) || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_APOYO_PLANIFICACION)
                || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_APOYO_PRODUCCION) || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_DIFUSION)
                || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_NORMALIZACION) || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.LECTOR)
                || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_PRODUCCION);
    }

    public static Boolean isAdministrador(MetamacPrincipal metamacPrincipal) {
        return isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.ADMINISTRADOR);
    }

}
