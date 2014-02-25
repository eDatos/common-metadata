package org.siemac.metamac.common.metadata.web.server.handlers.external;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemSchemesAction;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemSchemesResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.constants.CommonSharedConstants;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetSrmItemSchemesActionHandler extends SecurityActionHandler<GetSrmItemSchemesAction, GetSrmItemSchemesResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetSrmItemSchemesActionHandler() {
        super(GetSrmItemSchemesAction.class);
    }

    @Override
    public GetSrmItemSchemesResult executeSecurityAction(GetSrmItemSchemesAction action) throws ActionException {

        ServiceContext serviceContext = ServiceContextHolder.getCurrentServiceContext();

        ExternalItemsResult result = null;
        switch (action.getItemSchemeRestCriteria().getExternalArtifactType()) {
            case AGENCY_SCHEME:
                result = srmRestInternalFacade.findAgencySchemes(serviceContext, action.getItemSchemeRestCriteria(), action.getFirstResult(), action.getMaxResults());
                break;
            case CONCEPT_SCHEME:
                result = srmRestInternalFacade.findConceptSchemes(serviceContext, action.getItemSchemeRestCriteria(), action.getFirstResult(), action.getMaxResults());
                break;
            case CODELIST:
                result = srmRestInternalFacade.findCodelists(serviceContext, action.getItemSchemeRestCriteria(), action.getFirstResult(), action.getMaxResults());
                break;
            default:
                throw new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, "");
        }
        return new GetSrmItemSchemesResult(result);
    }
}
