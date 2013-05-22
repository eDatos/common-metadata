package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.shared.GetConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.GetConfigurationsResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetConfigurationsActionHandler extends SecurityActionHandler<GetConfigurationsAction, GetConfigurationsResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    public GetConfigurationsActionHandler() {
        super(GetConfigurationsAction.class);
    }

    @Override
    public GetConfigurationsResult executeSecurityAction(GetConfigurationsAction action) throws ActionException {
        try {
            List<ConfigurationDto> configurations = commonMetadataServiceFacade.findAllConfigurations(ServiceContextHolder.getCurrentServiceContext());
            return new GetConfigurationsResult(configurations);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
