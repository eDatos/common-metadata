package org.siemac.metamac.common.metadata.web.server.rest;

import static org.siemac.metamac.srm.rest.internal.RestInternalConstants.WILDCARD;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.web.server.rest.utils.ExternalItemUtils;
import org.siemac.metamac.common.metadata.web.server.rest.utils.RestQueryUtils;
import org.siemac.metamac.common.metadata.web.server.utils.WebTranslateExceptions;
import org.siemac.metamac.common.metadata.web.shared.constants.WebMessageExceptionsConstants;
import org.siemac.metamac.core.common.lang.shared.LocaleConstants;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Agencies;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AgencySchemes;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.constants.CommonSharedConstants;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemSchemeRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SrmRestInternalFacadeImpl implements SrmRestInternalFacade {

    private static Logger          logger = Logger.getLogger(SrmRestInternalFacadeImpl.class.getName());

    @Autowired
    private RestApiLocator         restApiLocator;

    @Autowired
    private WebTranslateExceptions webTranslateExceptions;

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
        } catch (ServerWebApplicationException e) {
            throwMetamacWebExceptionFromServerWebApplicationException(serviceContext, e);
            return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, "Error finding agency schemes");
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
            Agencies agencies = restApiLocator.getSrmRestInternalFacadeV10().findAgencies(WILDCARD, WILDCARD, WILDCARD, query, orderBy, limit, offset);
            return ExternalItemUtils.getAgenciesAsExternalItemsResult(agencies);
        } catch (ServerWebApplicationException e) {
            throwMetamacWebExceptionFromServerWebApplicationException(serviceContext, e);
            return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, "Error finding categories");
        }
    }

    //
    // EXCEPTION HANDLERS
    //

    private void throwMetamacWebExceptionFromServerWebApplicationException(ServiceContext serviceContext, ServerWebApplicationException e) throws MetamacWebException {

        logger.log(Level.SEVERE, e.getMessage());

        org.siemac.metamac.rest.common.v1_0.domain.Exception exception = e.toErrorObject(WebClient.client(restApiLocator.getSrmRestInternalFacadeV10()),
                org.siemac.metamac.rest.common.v1_0.domain.Exception.class);

        if (exception == null) {

            if (e.getResponse().getStatus() == 404) {
                throwMetamacWebException(serviceContext, WebMessageExceptionsConstants.REST_API_SRM_INVOCATION_ERROR_404);
            } else {
                throwMetamacWebException(serviceContext, WebMessageExceptionsConstants.REST_API_SRM_INVOCATION_ERROR_UNKNOWN);
            }
        }

        throw WebExceptionUtils.createMetamacWebException(exception);
    }

    private void throwMetamacWebException(ServiceContext serviceContext, String exceptionCode) throws MetamacWebException {
        String locale = (String) serviceContext.getProperty(LocaleConstants.locale);
        String exceptionnMessage = webTranslateExceptions.getTranslatedMessage(exceptionCode, locale);

        throw new MetamacWebException(exceptionCode, exceptionnMessage);
    }
}
