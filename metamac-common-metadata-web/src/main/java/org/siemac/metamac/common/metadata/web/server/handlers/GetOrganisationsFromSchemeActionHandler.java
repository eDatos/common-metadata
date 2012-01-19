package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContextStore;
import org.siemac.metamac.common.metadata.web.shared.GetOrganisationsFromSchemeAction;
import org.siemac.metamac.common.metadata.web.shared.GetOrganisationsFromSchemeResult;
import org.siemac.metamac.core.common.dto.serviceapi.ExternalItemBtDto;
import org.siemac.metamac.core.common.serviceapi.MetamacCoreCommonService;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetOrganisationsFromSchemeActionHandler extends AbstractActionHandler<GetOrganisationsFromSchemeAction, GetOrganisationsFromSchemeResult> {

	@Autowired
	private MetamacCoreCommonService metamacCoreCommonService;
	
	public GetOrganisationsFromSchemeActionHandler() {
		super(GetOrganisationsFromSchemeAction.class);
	}

	@Override
	public GetOrganisationsFromSchemeResult execute(GetOrganisationsFromSchemeAction action, ExecutionContext context) throws ActionException {
		List<ExternalItemBtDto> organisations =  metamacCoreCommonService.retrieveOrganisationScheme(ServiceContextStore.get(), action.getOrganisationSchemeUri());
		return new GetOrganisationsFromSchemeResult(organisations);
	}

	@Override
	public void undo(GetOrganisationsFromSchemeAction action, GetOrganisationsFromSchemeResult result, ExecutionContext context) throws ActionException {
		
	}

}
