package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContextStore;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.base.serviceapi.CommonMetadataBaseServiceFacade;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListAction;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListResult;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class DeleteConfigurationListActionHandler  extends AbstractActionHandler<DeleteConfigurationListAction, DeleteConfigurationListResult> {

	private static Logger logger = Logger.getLogger(DeleteConfigurationListActionHandler.class.getName());
	
	@Autowired
	private CommonMetadataBaseServiceFacade commonMetadataBaseServiceFacade;
	
	public DeleteConfigurationListActionHandler() {
		super(DeleteConfigurationListAction.class);
	}

	@Override
	public DeleteConfigurationListResult execute(DeleteConfigurationListAction action, ExecutionContext context) throws ActionException {
		List<ConfigurationDto> configurationDtos = action.getConfigurationDtos();
		for (ConfigurationDto c : configurationDtos) {
			try {
				commonMetadataBaseServiceFacade.deleteConfiguration(ServiceContextStore.get(), c);
			} catch (CommonMetadataException e) {
				logger.log(Level.SEVERE, "Error deleting configuration with id = " + c.getId() + ". " + e.getMessage());
				throw new ActionException(e.getLocalizedMessage());
			}
		}
		return new DeleteConfigurationListResult();
	}
	
	@Override
	public void undo(DeleteConfigurationListAction action, DeleteConfigurationListResult result, ExecutionContext context) throws ActionException {
		
	}

}
