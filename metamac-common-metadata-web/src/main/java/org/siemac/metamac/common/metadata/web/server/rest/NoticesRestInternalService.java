package org.siemac.metamac.common.metadata.web.server.rest;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface NoticesRestInternalService {

    public static final String BEAN_ID = "noticesRestInternalService";

    void createNotificationForPublishExternallyConfiguration(ServiceContext serviceContext, ConfigurationDto configurationDto) throws MetamacWebException;

    void createNotificationForUpdateConfigurationStatus(ServiceContext ctx, List<ConfigurationDto> configurationDtos) throws MetamacWebException;
}
