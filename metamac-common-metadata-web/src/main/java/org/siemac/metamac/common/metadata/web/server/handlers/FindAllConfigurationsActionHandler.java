package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContextStore;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.base.serviceapi.CommonMetadataBaseServiceFacade;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsResult;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;


public class FindAllConfigurationsActionHandler extends AbstractActionHandler<FindAllConfigurationsAction, FindAllConfigurationsResult> {

	@Autowired
	private CommonMetadataBaseServiceFacade commonMetadataBaseServiceFacade;
	
	public FindAllConfigurationsActionHandler() {
		super(FindAllConfigurationsAction.class);
	}

	@Override
	public FindAllConfigurationsResult execute(FindAllConfigurationsAction action, ExecutionContext context) throws ActionException {
		try {
			List<ConfigurationDto> configurations = commonMetadataBaseServiceFacade.findAllConfigurations(ServiceContextStore.get());
			return new FindAllConfigurationsResult(configurations);
		} catch (CommonMetadataException e) {
			throw new ActionException(e.getLocalizedMessage());
		}
	}

	@Override
	public void undo(FindAllConfigurationsAction action, FindAllConfigurationsResult result, ExecutionContext context) throws ActionException {
		
	}
	
}
