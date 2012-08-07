package org.siemac.metamac.common_metadata.rest.internal.v1_0.mapper;

import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.Configuration;

public interface Do2RestInternalMapperV10 {

    // Configurations
    public Configuration toConfiguration(org.siemac.metamac.common.metadata.core.domain.Configuration source, String apiUrl);
    // TODO
//    public ResourcesNoPagedResult toConfigurationsNoPagedResult(List<org.siemac.metamac.common.metadata.core.domain.Configuration> sources, String apiUrl);

    // Other
//    public Error toError(Exception exception);
}
