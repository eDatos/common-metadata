package org.siemac.metamac.common_metadata.rest.external.v1_0.mapper;

import java.util.List;

import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Properties;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Property;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal;

public interface Do2RestExternalMapperV10 {

    // Configurations
    Configuration toConfiguration(org.siemac.metamac.common.metadata.core.domain.Configuration source);

    Configurations toConfigurations(List<org.siemac.metamac.common.metadata.core.domain.Configuration> sources);

    ResourceInternal toResource(org.siemac.metamac.common.metadata.core.domain.Configuration source);
    
    Properties toProperties(List<org.siemac.metamac.common.metadata.core.domain.DataConfiguration> sources);
    
    Property toProperty(org.siemac.metamac.common.metadata.core.domain.DataConfiguration source);
}
