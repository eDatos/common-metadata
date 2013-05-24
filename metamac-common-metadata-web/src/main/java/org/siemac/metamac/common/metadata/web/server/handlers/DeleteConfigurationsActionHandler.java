package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationsResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteConfigurationsActionHandler extends SecurityActionHandler<DeleteConfigurationsAction, DeleteConfigurationsResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    public DeleteConfigurationsActionHandler() {
        super(DeleteConfigurationsAction.class);
    }

    @Override
    public DeleteConfigurationsResult executeSecurityAction(DeleteConfigurationsAction action) throws ActionException {
        List<Long> ids = action.getConfigurationIds();
        for (Long id : ids) {
            try {
                commonMetadataServiceFacade.deleteConfiguration(ServiceContextHolder.getCurrentServiceContext(), id);
            } catch (MetamacException e) {
                throw WebExceptionUtils.createMetamacWebException(e);
            }
        }
        return new DeleteConfigurationsResult();
    }
}
