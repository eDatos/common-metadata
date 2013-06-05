package org.siemac.metamac.common.metadata.web.server.handlers.external;

import org.siemac.metamac.common.metadata.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.common.metadata.web.shared.external.GetExternalResourcesAction;
import org.siemac.metamac.common.metadata.web.shared.external.GetExternalResourcesResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.constants.CommonSharedConstants;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetExternalResourcesActionHandler extends SecurityActionHandler<GetExternalResourcesAction, GetExternalResourcesResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetExternalResourcesActionHandler() {
        super(GetExternalResourcesAction.class);
    }

    @Override
    public GetExternalResourcesResult executeSecurityAction(GetExternalResourcesAction action) throws ActionException {
        ExternalItemsResult result = null;
        switch (action.getExternalResourceWebCriteria().getType()) {
            case AGENCY_SCHEME:
                result = srmRestInternalFacade.findAgencySchemes(action.getExternalResourceWebCriteria(), action.getFirstResult(), action.getMaxResults());
                break;
            case AGENCY:
                result = srmRestInternalFacade.findAgencies((SrmItemRestCriteria) action.getExternalResourceWebCriteria(), action.getFirstResult(), action.getMaxResults());
                break;
            default:
                throw new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, ""); // TODO
        }
        return new GetExternalResourcesResult(result);
    }
}
