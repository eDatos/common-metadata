package org.siemac.metamac.common.metadata.web.client.utils;

import static org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataRoleEnum.JEFE_NORMALIZACION;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataRoleEnum;
import org.siemac.metamac.common.metadata.core.security.shared.SharedSecurityUtils;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.sso.client.MetamacPrincipal;

public class ConfigurationClientSecurityUtils extends ClientSecurityUtils {

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

    public static boolean canDeleteConfiguration(ConfigurationDto configurationDto) {
        if (configurationDto.isExternallyPublished()) {
            return false;
        }

        CommonMetadataRoleEnum[] roles = {JEFE_NORMALIZACION};
        if (isRoleAllowed(roles)) {
            return true;
        }
        return false;
    }

    public static boolean canPublishConfigurationExternally() {
        CommonMetadataRoleEnum[] roles = {JEFE_NORMALIZACION};
        if (isRoleAllowed(roles)) {
            return true;
        }
        return false;
    }

}
