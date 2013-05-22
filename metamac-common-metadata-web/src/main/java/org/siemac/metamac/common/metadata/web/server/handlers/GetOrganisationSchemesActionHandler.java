package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.web.shared.GetOrganisationSchemesAction;
import org.siemac.metamac.common.metadata.web.shared.GetOrganisationSchemesResult;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.serviceapi.MetamacCoreCommonService;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetOrganisationSchemesActionHandler extends SecurityActionHandler<GetOrganisationSchemesAction, GetOrganisationSchemesResult> {

    @Autowired
    private MetamacCoreCommonService metamacCoreCommonService;

    public GetOrganisationSchemesActionHandler() {
        super(GetOrganisationSchemesAction.class);
    }

    @Override
    public GetOrganisationSchemesResult executeSecurityAction(GetOrganisationSchemesAction action) throws ActionException {
        List<ExternalItemDto> organisationSchemes = metamacCoreCommonService.findAllOrganisationSchemes(ServiceContextHolder.getCurrentServiceContext());
        return new GetOrganisationSchemesResult(organisationSchemes);
    }
}
