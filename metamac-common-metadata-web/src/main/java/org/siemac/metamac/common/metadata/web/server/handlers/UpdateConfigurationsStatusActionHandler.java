package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.server.rest.NoticesRestInternalService;
import org.siemac.metamac.common.metadata.web.shared.UpdateConfigurationsStatusAction;
import org.siemac.metamac.common.metadata.web.shared.UpdateConfigurationsStatusResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateConfigurationsStatusActionHandler extends SecurityActionHandler<UpdateConfigurationsStatusAction, UpdateConfigurationsStatusResult> {

    @Autowired
    private NoticesRestInternalService  noticesRestInternalService;

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    public UpdateConfigurationsStatusActionHandler() {
        super(UpdateConfigurationsStatusAction.class);
    }

    @Override
    public UpdateConfigurationsStatusResult executeSecurityAction(UpdateConfigurationsStatusAction action) throws ActionException {
        List<ConfigurationDto> configurationDtos = new ArrayList<ConfigurationDto>();
        ServiceContext serviceContext = ServiceContextHolder.getCurrentServiceContext();

        try {
            configurationDtos = commonMetadataServiceFacade.updateConfigurationsStatus(serviceContext, action.getConfiguationIds(), action.getStatusEnum());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }

        try {
            noticesRestInternalService.createNotificationForUpdateConfigurationStatus(serviceContext, configurationDtos);
        } catch (MetamacWebException e) {
            return new UpdateConfigurationsStatusResult(configurationDtos, e);
        }

        return new UpdateConfigurationsStatusResult(configurationDtos, null);
    }
}
