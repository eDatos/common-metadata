package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.web.shared.FindAllOrganisationSchemesAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllOrganisationSchemesResult;
import org.siemac.metamac.core.common.dto.ExternalItemBtDto;
import org.siemac.metamac.core.common.serviceapi.MetamacCoreCommonService;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class FindAllOrganisationSchemesActionHandler extends SecurityActionHandler<FindAllOrganisationSchemesAction, FindAllOrganisationSchemesResult> {

    @Autowired
    private MetamacCoreCommonService metamacCoreCommonService;

    public FindAllOrganisationSchemesActionHandler() {
        super(FindAllOrganisationSchemesAction.class);
    }

    @Override
    public FindAllOrganisationSchemesResult executeSecurityAction(FindAllOrganisationSchemesAction action) throws ActionException {
        List<ExternalItemBtDto> organisationSchemes = metamacCoreCommonService.findAllOrganisationSchemes(ServiceContextHolder.getCurrentServiceContext());
        return new FindAllOrganisationSchemesResult(organisationSchemes);
    }

    @Override
    public void undo(FindAllOrganisationSchemesAction action, FindAllOrganisationSchemesResult result, ExecutionContext context) throws ActionException {

    }

}
