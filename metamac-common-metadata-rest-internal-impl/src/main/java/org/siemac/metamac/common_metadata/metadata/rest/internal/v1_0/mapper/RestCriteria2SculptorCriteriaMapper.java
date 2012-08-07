package org.siemac.metamac.common.metadata.rest.internal.v1_0.mapper;

import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria;

public interface RestCriteria2SculptorCriteriaMapper {

    public RestCriteria2SculptorCriteria<Configuration> getConfigurationCriteriaMapper();
}
