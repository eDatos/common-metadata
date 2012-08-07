package org.siemac.metamac.common_metadata.rest.internal.v1_0.mapper;

import javax.ws.rs.core.Response.Status;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.ConfigurationCriteriaPropertyOrder;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.ConfigurationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.common.query.domain.MetamacRestOrder;
import org.siemac.metamac.rest.common.query.domain.MetamacRestQueryPropertyRestriction;
import org.siemac.metamac.rest.common.query.domain.SculptorPropertyCriteria;
import org.siemac.metamac.rest.common.v1_0.domain.Error;
import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria.CriteriaCallback;
import org.springframework.stereotype.Component;

@Component
public class RestCriteria2SculptorCriteriaMapperImpl implements RestCriteria2SculptorCriteriaMapper {

    private RestCriteria2SculptorCriteria<Configuration>    configurationCriteriaMapper    = null;

    public RestCriteria2SculptorCriteriaMapperImpl() {
        configurationCriteriaMapper = new RestCriteria2SculptorCriteria<Configuration>(Configuration.class, ConfigurationCriteriaPropertyOrder.class, ConfigurationCriteriaPropertyRestriction.class, new ConfigurationCriteriaCallback());
    }

    @Override
    public RestCriteria2SculptorCriteria<Configuration> getConfigurationCriteriaMapper() {
        return configurationCriteriaMapper;
    }

    private class ConfigurationCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteria retrieveProperty(MetamacRestQueryPropertyRestriction propertyRestriction) throws RestException {
            ConfigurationCriteriaPropertyRestriction propertyNameCriteria = ConfigurationCriteriaPropertyRestriction.fromValue(propertyRestriction.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return new SculptorPropertyCriteria(ConfigurationProperties.code(), propertyRestriction.getValue());
                case URN:
                    return new SculptorPropertyCriteria(ConfigurationProperties.urn(), propertyRestriction.getValue());
                case CONTACT_URN:
                    return new SculptorPropertyCriteria(ConfigurationProperties.contact().urn(), propertyRestriction.getValue());
                case STATUS:
                    return new SculptorPropertyCriteria(ConfigurationProperties.status(), propertyRestrictionValueToStatusEnum(propertyRestriction.getValue()));
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }
        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrder(MetamacRestOrder order) throws RestException {
            ConfigurationCriteriaPropertyOrder propertyNameCriteria = ConfigurationCriteriaPropertyOrder.fromValue(order.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return ConfigurationProperties.code();
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrderDefault() throws RestException {
            return ConfigurationProperties.id();
        }
    }

    // TODO
    private RestException toRestExceptionParameterIncorrect(String parameter) {
//        Error error = RestExceptionUtils.getError(RestCommonServiceExceptionType.PARAMETER_INCORRECT, parameter);
//        return new RestException(error, Status.INTERNAL_SERVER_ERROR);
        return null;
    }

    private CommonMetadataStatusEnum propertyRestrictionValueToStatusEnum(String value) {
        return value != null ? CommonMetadataStatusEnum.valueOf(value) : null;
    }
}
