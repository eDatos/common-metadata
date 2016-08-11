package org.siemac.metamac.common.metadata.web.server.handlers.external;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemsAction;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemsResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.constants.CommonSharedConstants;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetSrmItemsActionHandler extends SecurityActionHandler<GetSrmItemsAction, GetSrmItemsResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetSrmItemsActionHandler() {
        super(GetSrmItemsAction.class);
    }

    @Override
    public GetSrmItemsResult executeSecurityAction(GetSrmItemsAction action) throws ActionException {

        ServiceContext serviceContext = ServiceContextHolder.getCurrentServiceContext();

        ExternalItemsResult result = null;
        switch (action.getItemRestCriteria().getExternalArtifactType()) {
            case CONCEPT:
                result = srmRestInternalFacade.findConcepts(serviceContext, action.getItemRestCriteria(), action.getFirstResult(), action.getMaxResults());
                break;
            case AGENCY:
                result = srmRestInternalFacade.findAgencies(serviceContext, action.getItemRestCriteria(), action.getFirstResult(), action.getMaxResults());
                break;
            case CODE:
                result = srmRestInternalFacade.findCodes(serviceContext, action.getItemRestCriteria(), action.getFirstResult(), action.getMaxResults());
                break;
            default:
                throw new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, "");
        }
        return new GetSrmItemsResult(result);
    }
}
