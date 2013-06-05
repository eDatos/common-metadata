package org.siemac.metamac.common.metadata.web.server.rest;

import org.siemac.metamac.web.common.shared.criteria.ExternalResourceWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemWebCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface SrmRestInternalFacade {

    ExternalItemsResult findAgencySchemes(ExternalResourceWebCriteria criteria, int firstResult, int maxResults) throws MetamacWebException;
    ExternalItemsResult findAgencies(SrmItemWebCriteria itemWebCriteria, int firstResult, int maxResults) throws MetamacWebException;
}
