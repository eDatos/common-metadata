package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContextStore;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.base.serviceapi.CommonMetadataBaseServiceFacade;
import org.siemac.metamac.common.metadata.web.shared.FindAllContactsAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllContactsResult;
import org.siemac.metamac.core.common.dto.serviceapi.ExternalItemBtDto;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class FindAllContactsActionHandler extends AbstractActionHandler<FindAllContactsAction, FindAllContactsResult> {

	@Autowired
	private CommonMetadataBaseServiceFacade commonMetadataBaseServiceFacade;
	
	public FindAllContactsActionHandler() {
		super(FindAllContactsAction.class);
	}

	@Override
	public FindAllContactsResult execute(FindAllContactsAction action, ExecutionContext context) throws ActionException { 
		try {
			List<ExternalItemBtDto> configurations = commonMetadataBaseServiceFacade.findAllContacts(ServiceContextStore.get());
			return new FindAllContactsResult(configurations);
		} catch (CommonMetadataException e) {
			throw new ActionException(e.getLocalizedMessage());
		}
	}

	@Override
	public void undo(FindAllContactsAction action, FindAllContactsResult result, ExecutionContext context) throws ActionException {
		
	}

	
}
