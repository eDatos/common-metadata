package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.common.metadata.base.serviceapi.CommonMetadataBaseServiceFacade;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.server.ServiceContextHelper;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
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
			ConfigurationDto configurationDto = commonMetadataBaseServiceFacade.saveConfiguration(ServiceContextHelper.getServiceContext(), action.getConfigurationDto());
			return new SaveConfigurationResult(configurationDto);
		} catch (MetamacException e) {
			logger.log(Level.SEVERE, "Error saving Configuration with id = " + action.getConfigurationDto().getId() + ". " + e.getMessage());
			throw new MetamacWebException(WebExceptionUtils.getMetamacWebExceptionItem(e.getExceptionItems()));
		}
	}

	@Override
	public void undo(SaveConfigurationAction action, SaveConfigurationResult result, ExecutionContext context) throws ActionException {
		
	}
	
}
