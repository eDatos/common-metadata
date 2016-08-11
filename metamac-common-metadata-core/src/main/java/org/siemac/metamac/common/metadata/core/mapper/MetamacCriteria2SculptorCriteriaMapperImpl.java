package org.siemac.metamac.common.metadata.core.mapper;

import org.siemac.metamac.common.metadata.core.criteria.DataConfigurationCriteriaOrderEnum;
import org.siemac.metamac.common.metadata.core.criteria.DataConfigurationCriteriaPropertyEnum;
import org.siemac.metamac.common.metadata.core.domain.DataConfiguration;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.springframework.stereotype.Component;


@Component
public class MetamacCriteria2SculptorCriteriaMapperImpl implements MetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<DataConfiguration>    dataConfigurationCriteriaMapper    = null;

    public MetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        dataConfigurationCriteriaMapper = new MetamacCriteria2SculptorCriteria<DataConfiguration>(DataConfiguration.class, DataConfigurationCriteriaOrderEnum.class, DataConfigurationCriteriaPropertyEnum.class,
                new DataConfigurationCriteriaCallback());
    }

    @Override
    public MetamacCriteria2SculptorCriteria<DataConfiguration> getDataConfigurationCriteriaMapper() {
        return dataConfigurationCriteriaMapper;
    }

}
