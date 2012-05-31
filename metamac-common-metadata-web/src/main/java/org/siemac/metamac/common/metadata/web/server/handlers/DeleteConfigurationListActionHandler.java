package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.server.ServiceContextHolder;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListAction;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteConfigurationListActionHandler extends AbstractActionHandler<DeleteConfigurationListAction, DeleteConfigurationListResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    public DeleteConfigurationListActionHandler() {
        super(DeleteConfigurationListAction.class);
    }

    @Override
    public DeleteConfigurationListResult execute(DeleteConfigurationListAction action, ExecutionContext context) throws ActionException {
        List<Long> ids = action.getConfigurationIds();
        for (Long id : ids) {
            try {
                commonMetadataServiceFacade.deleteConfiguration(ServiceContextHolder.getCurrentServiceContext(), id);
            } catch (MetamacException e) {
                throw WebExceptionUtils.createMetamacWebException(e);
            }
        }
        return new DeleteConfigurationListResult();
    }

    @Override
    public void undo(DeleteConfigurationListAction action, DeleteConfigurationListResult result, ExecutionContext context) throws ActionException {

    }

}
