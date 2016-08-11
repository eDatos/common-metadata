package org.siemac.metamac.common.metadata.core.mapper;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.common.metadata.core.criteria.DataConfigurationCriteriaOrderEnum;
import org.siemac.metamac.common.metadata.core.criteria.DataConfigurationCriteriaPropertyEnum;
import org.siemac.metamac.common.metadata.core.domain.DataConfiguration;
import org.siemac.metamac.common.metadata.core.domain.DataConfigurationProperties;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;


class DataConfigurationCriteriaCallback implements CriteriaCallback {

    DataConfigurationCriteriaCallback() {
    }

    @Override
    public SculptorPropertyCriteriaBase retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
        DataConfigurationCriteriaPropertyEnum propertyEnum = DataConfigurationCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
        switch (propertyEnum) {
            case CONF_KEY:
                return new SculptorPropertyCriteria(DataConfigurationProperties.configurationKey(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
            case CONF_VALUE:
                return new SculptorPropertyCriteria(DataConfigurationProperties.configurationValue(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
            default:
                throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Property<DataConfiguration> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
        DataConfigurationCriteriaOrderEnum criteriaOrderEnum = DataConfigurationCriteriaOrderEnum.fromValue(order.getPropertyName());
        switch (criteriaOrderEnum) {
            case CREATED_DATE:
                return CriteriaUtils.getDatetimeLeafPropertyEmbedded(DataConfigurationProperties.createdDate(), DataConfiguration.class);
            default:
                throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Property<DataConfiguration> retrievePropertyOrderDefault() throws MetamacException {
        return CriteriaUtils.getDatetimeLeafPropertyEmbedded(DataConfigurationProperties.createdDate(), DataConfiguration.class);
    }
}