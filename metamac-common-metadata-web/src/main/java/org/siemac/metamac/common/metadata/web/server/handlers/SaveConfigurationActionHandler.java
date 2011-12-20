package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContextStore;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.base.serviceapi.CommonMetadataBaseServiceFacade;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationResult;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

@GenDispatch(isSecure = false)
public class SaveConfigurationActionHandler extends AbstractActionHandler<SaveConfigurationAction, SaveConfigurationResult> {

	private static Logger logger = Logger.getLogger(SaveConfigurationActionHandler.class.getName());
	
	@Autowired
	private CommonMetadataBaseServiceFacade commonMetadataBaseServiceFacade;
	
	public SaveConfigurationActionHandler() {
		super(SaveConfigurationAction.class);
	}

	@Override
	public SaveConfigurationResult execute(SaveConfigurationAction action, ExecutionContext context) throws ActionException {
		try {
			ConfigurationDto configurationDto = commonMetadataBaseServiceFacade.saveConfiguration(ServiceContextStore.get(), action.getConfigurationDto());
			return new SaveConfigurationResult(configurationDto);
		} catch (CommonMetadataException e) {
			logger.log(Level.SEVERE, "Error saving Configuration with id = " + action.getConfigurationDto().getId() + ". " + e.getMessage());
			throw new ActionException(e.getLocalizedMessage());
		}
	}

	@Override
	public void undo(SaveConfigurationAction action, SaveConfigurationResult result, ExecutionContext context) throws ActionException {
		
	}
	
}
