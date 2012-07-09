package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class FindAllConfigurationsActionHandler extends SecurityActionHandler<FindAllConfigurationsAction, FindAllConfigurationsResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    public FindAllConfigurationsActionHandler() {
        super(FindAllConfigurationsAction.class);
    }

    @Override
    public FindAllConfigurationsResult executeSecurityAction(FindAllConfigurationsAction action) throws ActionException {
        try {
            List<ConfigurationDto> configurations = commonMetadataServiceFacade.findAllConfigurations(ServiceContextHolder.getCurrentServiceContext());
            return new FindAllConfigurationsResult(configurations);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public void undo(FindAllConfigurationsAction action, FindAllConfigurationsResult result, ExecutionContext context) throws ActionException {

    }

}
