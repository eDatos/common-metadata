package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContextStore;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.base.serviceapi.CommonMetadataBaseServiceFacade;
import org.siemac.metamac.common.metadata.web.shared.FindAllOrganisationSchemesAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllOrganisationSchemesResult;
import org.siemac.metamac.core.common.dto.serviceapi.ExternalItemBtDto;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class FindAllOrganisationSchemesActionHandler extends AbstractActionHandler<FindAllOrganisationSchemesAction, FindAllOrganisationSchemesResult> {

	@Autowired
	private CommonMetadataBaseServiceFacade commonMetadataBaseServiceFacade;
	
	public FindAllOrganisationSchemesActionHandler() {
		super(FindAllOrganisationSchemesAction.class);
	}

	@Override
	public FindAllOrganisationSchemesResult execute(FindAllOrganisationSchemesAction action, ExecutionContext context) throws ActionException { 
		try {
			List<ExternalItemBtDto> schemes = commonMetadataBaseServiceFacade.findAllOrganisationSchemes(ServiceContextStore.get());
			return new FindAllOrganisationSchemesResult(schemes);
		} catch (CommonMetadataException e) {
			throw new ActionException(e.getLocalizedMessage());
		}
	}

	@Override
	public void undo(FindAllOrganisationSchemesAction action, FindAllOrganisationSchemesResult result, ExecutionContext context) throws ActionException {
		
	}

}
