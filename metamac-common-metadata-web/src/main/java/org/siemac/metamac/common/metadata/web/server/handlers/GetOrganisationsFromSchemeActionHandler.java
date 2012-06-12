package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.web.shared.GetOrganisationsFromSchemeAction;
import org.siemac.metamac.common.metadata.web.shared.GetOrganisationsFromSchemeResult;
import org.siemac.metamac.core.common.dto.ExternalItemBtDto;
import org.siemac.metamac.core.common.serviceapi.MetamacCoreCommonService;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetOrganisationsFromSchemeActionHandler extends SecurityActionHandler<GetOrganisationsFromSchemeAction, GetOrganisationsFromSchemeResult> {

    @Autowired
    private MetamacCoreCommonService metamacCoreCommonService;

    public GetOrganisationsFromSchemeActionHandler() {
        super(GetOrganisationsFromSchemeAction.class);
    }

    @Override
    public GetOrganisationsFromSchemeResult executeSecurityAction(GetOrganisationsFromSchemeAction action) throws ActionException {
        List<ExternalItemBtDto> organisations = metamacCoreCommonService.retrieveOrganisationScheme(ServiceContextHolder.getCurrentServiceContext(), action.getOrganisationSchemeUri());
        return new GetOrganisationsFromSchemeResult(organisations);
    }

    @Override
    public void undo(GetOrganisationsFromSchemeAction action, GetOrganisationsFromSchemeResult result, ExecutionContext context) throws ActionException {

    }

}
