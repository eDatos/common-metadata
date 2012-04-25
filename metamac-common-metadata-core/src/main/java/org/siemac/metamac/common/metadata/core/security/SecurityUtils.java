package org.siemac.metamac.common.metadata.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConstants;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.domain.common.metadata.enume.domain.CommonMetadataRoleEnum;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;

public class SecurityUtils {

    /**
     * Checks user can execute any operation, if has any role of requested roles
     */
    public static void checkServiceOperationAllowed(ServiceContext ctx, CommonMetadataRoleEnum... roles) throws MetamacException {

        MetamacPrincipal metamacPrincipal = getMetamacPrincipal(ctx);

        // Administration has total control
        if (isAdministrador(metamacPrincipal)) {
            return;
        }
        // Checks user has any role of requested
        if (roles != null) {
            for (int i = 0; i < roles.length; i++) {
                CommonMetadataRoleEnum role = roles[i];
                if (isUserInRol(metamacPrincipal, role)) {
                    return;
                }
            }
        }
        throw new MetamacException(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED, metamacPrincipal.getUserId());
    }

    /**
     * Checks user has requested rol
     */
    private static boolean isUserInRol(MetamacPrincipal metamacPrincipal, CommonMetadataRoleEnum role) throws MetamacException {

        if (CommonMetadataRoleEnum.ANY_ROLE_ALLOWED.equals(role)) {
            return isAnyCommonMetadataRole(metamacPrincipal);
        } else {
            return isRoleInAccesses(metamacPrincipal, role);
        }
    }

    /**
     * Retrieves MetamacPrincipal in ServiceContext
     */
    private static MetamacPrincipal getMetamacPrincipal(ServiceContext ctx) throws MetamacException {
        Object principalProperty = ctx.getProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE);
        if (principalProperty == null) {
            throw new MetamacException(ServiceExceptionType.SECURITY_PRINCIPAL_NOT_FOUND);
        }
        MetamacPrincipal metamacPrincipal = (MetamacPrincipal) principalProperty;
        if (!metamacPrincipal.getUserId().equals(ctx.getUserId())) {
            throw new MetamacException(ServiceExceptionType.SECURITY_PRINCIPAL_NOT_FOUND);
        }
        return metamacPrincipal;
    }

    /**
     * Checks if user has access with role
     */
    private static Boolean isRoleInAccesses(MetamacPrincipal metamacPrincipal, CommonMetadataRoleEnum role) {
        for (MetamacPrincipalAccess metamacPrincipalAccess : metamacPrincipal.getAccesses()) {
            if (CommonMetadataConstants.SECURITY_APPLICATION_ID.equals(metamacPrincipalAccess.getApplication()) && metamacPrincipalAccess.getRole().equals(role.name())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private static Boolean isAnyCommonMetadataRole(MetamacPrincipal metamacPrincipal) {
        return isAdministrador(metamacPrincipal) || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_PLANIFICACION)
                || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.JEFE_NORMALIZACION) || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_APOYO_DIFUSION)
                || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_APOYO_NORMALIZACION) || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_APOYO_PLANIFICACION)
                || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_APOYO_PRODUCCION) || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_DIFUSION)
                || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_NORMALIZACION) || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_PLANIFICACION)
                || isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.TECNICO_PRODUCCION);
    }

    private static Boolean isAdministrador(MetamacPrincipal metamacPrincipal) {
        return isRoleInAccesses(metamacPrincipal, CommonMetadataRoleEnum.ADMINISTRADOR);
    }

}