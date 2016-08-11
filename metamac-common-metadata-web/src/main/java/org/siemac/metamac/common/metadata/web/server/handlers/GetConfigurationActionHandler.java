package org.siemac.metamac.common.metadata.web.server.handlers;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.shared.GetConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.GetConfigurationResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetConfigurationActionHandler extends SecurityActionHandler<GetConfigurationAction, GetConfigurationResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    public GetConfigurationActionHandler() {
        super(GetConfigurationAction.class);
    }

    @Override
    public GetConfigurationResult executeSecurityAction(GetConfigurationAction action) throws ActionException {
        try {
            ConfigurationDto configuration = commonMetadataServiceFacade.findConfigurationByUrn(ServiceContextHolder.getCurrentServiceContext(), action.getUrn());
            return new GetConfigurationResult(configuration);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
