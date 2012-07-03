package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.shared.UpdateConfigurationsStatusAction;
import org.siemac.metamac.common.metadata.web.shared.UpdateConfigurationsStatusResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateConfigurationsStatusActionHandler extends SecurityActionHandler<UpdateConfigurationsStatusAction, UpdateConfigurationsStatusResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    public UpdateConfigurationsStatusActionHandler() {
        super(UpdateConfigurationsStatusAction.class);
    }

    @Override
    public UpdateConfigurationsStatusResult executeSecurityAction(UpdateConfigurationsStatusAction action) throws ActionException {
        try {
            List<ConfigurationDto> configurationDtos = commonMetadataServiceFacade.updateConfigurationsStatus(ServiceContextHolder.getCurrentServiceContext(), action.getConfiguationIds(),
                    action.getStatusEnum());
            return new UpdateConfigurationsStatusResult(configurationDtos);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
