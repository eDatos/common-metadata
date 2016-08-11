package org.siemac.metamac.common.metadata.core.mapper;

import org.siemac.metamac.common.metadata.core.domain.DataConfiguration;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;

public interface MetamacCriteria2SculptorCriteriaMapper {

    public MetamacCriteria2SculptorCriteria<DataConfiguration> getDataConfigurationCriteriaMapper();
}
