package org.siemac.metamac.common.metadata.web.server.rest;

import static org.siemac.metamac.rest.api.constants.RestApiConstants.WILDCARD_ALL;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionParameters;
import org.siemac.metamac.common.metadata.web.server.rest.utils.ExternalItemUtils;
import org.siemac.metamac.common.metadata.web.server.rest.utils.RestExceptionUtils;
import org.siemac.metamac.common.metadata.web.server.rest.utils.RestQueryUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Agencies;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AgencySchemes;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemSchemeRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SrmRestInternalFacadeImpl implements SrmRestInternalFacade {

    @Autowired
    private RestApiLocator     restApiLocator;

    @Autowired
    private RestExceptionUtils restExceptionUtils;

    //
    // AGENCY SCHEMES
    //

    @Override
    public ExternalItemsResult findAgencySchemes(ServiceContext serviceContext, SrmItemSchemeRestCriteria criteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildAgencySchemeQuery(criteria);

        try {
            AgencySchemes agencySchemes = restApiLocator.getSrmRestInternalFacadeV10().findAgencySchemes(query, orderBy, limit, offset);
            return ExternalItemUtils.getAgencySchemesAsExternalItemsResult(agencySchemes);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    //
    // AGENCIES
    //

    @Override
    public ExternalItemsResult findAgencies(ServiceContext serviceContext, SrmItemRestCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException {

        String limit = String.valueOf(maxResults);
        String offset = String.valueOf(firstResult);
        String orderBy = null;
        String query = RestQueryUtils.buildAgencyQuery(itemWebCriteria);

        try {
            Agencies agencies = restApiLocator.getSrmRestInternalFacadeV10().findAgencies(WILDCARD_ALL, WILDCARD_ALL, WILDCARD_ALL, query, orderBy, limit, offset);
            return ExternalItemUtils.getAgenciesAsExternalItemsResult(agencies);
        } catch (Exception e) {
            throw manageSrmInternalRestException(serviceContext, e);
        }
    }

    private MetamacWebException manageSrmInternalRestException(ServiceContext ctx, Exception e) throws MetamacWebException {
        return restExceptionUtils.manageMetamacRestException(ctx, e, ServiceExceptionParameters.API_SRM_INTERNAL, restApiLocator.getSrmRestInternalFacadeV10());
    }
}
