package org.siemac.metamac.common.metadata.web.client.utils;

import static org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataRoleEnum.JEFE_NORMALIZACION;
import static org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataRoleEnum.ADMINISTRADOR;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataRoleEnum;
import org.siemac.metamac.common.metadata.web.server.utils.AppConfigurationsProcessor;
import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;

public class AppDataConfigClientSecurityUtils extends ClientSecurityUtils {

    private static final Set<String> uneditableProperties = new HashSet<String>(Arrays.asList(ConfigurationConstants.METAMAC_ORGANISATION, ConfigurationConstants.METAMAC_ORGANISATION_URN));

    public static boolean canListSystemProperties() {
        CommonMetadataRoleEnum[] roles = {ADMINISTRADOR, JEFE_NORMALIZACION};
        if (isRoleAllowed(roles)) {
            return true;
        }
        return false;
    }

    public static boolean canEditSystemProperties() {
        CommonMetadataRoleEnum[] roles = {ADMINISTRADOR};
        if (isRoleAllowed(roles)) {
            return true;
        }
        return false;
    }

    public static boolean canEditProperty(String propertyKey) {
        return propertyKey != null && !uneditableProperties.contains(propertyKey);
    }

    public static boolean canListDefaultValues() {
        return true;
    }

    public static boolean canEditDefaultValues() {
        CommonMetadataRoleEnum[] roles = {ADMINISTRADOR, JEFE_NORMALIZACION};
        if (isRoleAllowed(roles)) {
            return true;
        }
        return false;
    }
}
