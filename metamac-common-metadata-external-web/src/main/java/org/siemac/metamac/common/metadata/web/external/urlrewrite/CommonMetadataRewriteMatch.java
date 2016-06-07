package org.siemac.metamac.common.metadata.web.external.urlrewrite;

import org.siemac.metamac.common_metadata.rest.external.RestExternalConstants;
import org.siemac.metamac.core.common.util.urlrewrite.AbstractRewriteMatch;

class CommonMetadataRewriteMatch extends AbstractRewriteMatch {

    @Override
    protected String getApiPrefix() {
        return "cmetadata";
    }
    @Override
    protected String getLatestApiVersion() {
        return RestExternalConstants.API_VERSION_1_0;
    }
}
