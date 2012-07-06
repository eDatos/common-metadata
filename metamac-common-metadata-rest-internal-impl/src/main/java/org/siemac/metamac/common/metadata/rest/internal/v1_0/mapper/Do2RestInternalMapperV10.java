package org.siemac.metamac.common.metadata.rest.internal.v1_0.mapper;

import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common.v1_0.domain.Error;

public interface Do2RestInternalMapperV10 {

    // Configurations
    public Configuration toConfiguration(org.siemac.metamac.common.metadata.core.domain.Configuration source, String apiUrl);
//    public ResourcesPagedResult toConfigurationsNoPagedResult(PagedResult<org.siemac.metamac.common.metadata.core.domain.Configuration> sources, Integer limit, String apiUrl);

    // Other
    public Error toError(Exception exception);
}
