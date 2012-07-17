package org.siemac.metamac.common.metadata.rest.internal.v1_0.mapper;

import java.util.List;

import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common.v1_0.domain.Error;
import org.siemac.metamac.rest.common.v1_0.domain.ResourcesNoPagedResult;

public interface Do2RestInternalMapperV10 {

    // Configurations
    public Configuration toConfiguration(org.siemac.metamac.common.metadata.core.domain.Configuration source, String apiUrl);
    public ResourcesNoPagedResult toConfigurationsNoPagedResult(List<org.siemac.metamac.common.metadata.core.domain.Configuration> sources, String apiUrl);

    // Other
    public Error toError(Exception exception);
}
