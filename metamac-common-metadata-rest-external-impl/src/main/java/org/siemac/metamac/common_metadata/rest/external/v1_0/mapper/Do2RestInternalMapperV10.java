package org.siemac.metamac.common_metadata.rest.internal.v1_0.mapper;

import java.util.List;

import org.siemac.metamac.rest.common_metadata_internal.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata_internal.v1_0.domain.Configurations;

public interface Do2RestInternalMapperV10 {

    // Configurations
    public Configuration toConfiguration(org.siemac.metamac.common.metadata.core.domain.Configuration source);
    public Configurations toConfigurations(List<org.siemac.metamac.common.metadata.core.domain.Configuration> sources);
}
