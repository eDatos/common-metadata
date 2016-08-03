package org.siemac.metamac.common.metadata.web.shared.external;

import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetSrmItems {

    @In(1)
    SrmItemRestCriteria itemRestCriteria;

    @In(2)
    int                 firstResult;

    @In(3)
    int                 maxResults;

    @Out(1)
    ExternalItemsResult result;
}
