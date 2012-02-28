package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.base.serviceapi.CommonMetadataBaseServiceFacade;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.server.ServiceContextHelper;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
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
			List<ConfigurationDto> configurations = commonMetadataBaseServiceFacade.findAllConfigurations(ServiceContextHelper.getServiceContext());
			return new FindAllConfigurationsResult(configurations);
		} catch (MetamacException e) {
		    throw new MetamacWebException(WebExceptionUtils.getMetamacWebExceptionItem(e.getExceptionItems()));
		}
	}

	@Override
	public void undo(FindAllConfigurationsAction action, FindAllConfigurationsResult result, ExecutionContext context) throws ActionException {
		
	}
	
}
