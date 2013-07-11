package org.siemac.metamac.common_metadata.rest.external.v1_0.mapper;

import javax.ws.rs.core.Response.Status;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common_metadata.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.rest.common.query.domain.MetamacRestOrder;
import org.siemac.metamac.rest.common.query.domain.MetamacRestQueryPropertyRestriction;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ConfigurationCriteriaPropertyOrder;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ConfigurationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.search.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.rest.search.criteria.utils.CriteriaUtils;
import org.siemac.metamac.rest.search.criteria.utils.CriteriaUtils.PropertyValueRestToPropertyValueEntityInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RestCriteria2SculptorCriteriaMapperImpl implements RestCriteria2SculptorCriteriaMapper {

    private PropertyValueRestToPropertyValueEntityInterface propertyValueRestToPropertyValueEntity = null;
    private RestCriteria2SculptorCriteria<Configuration>    configurationCriteriaMapper            = null;
    private final Logger                                    logger                                 = LoggerFactory.getLogger(RestCriteria2SculptorCriteriaMapperImpl.class);

    private enum PropertyTypeEnum {
        STRING, DATE, BOOLEAN, STATUS
    }

    public RestCriteria2SculptorCriteriaMapperImpl() {
        propertyValueRestToPropertyValueEntity = new PropertyValueRestToPropertyValueEntity();
        configurationCriteriaMapper = new RestCriteria2SculptorCriteria<Configuration>(Configuration.class, ConfigurationCriteriaPropertyOrder.class, ConfigurationCriteriaPropertyRestriction.class,
                new ConfigurationCriteriaCallback());
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
                    return buildSculptorPropertyCriteria(ConfigurationProperties.code(), PropertyTypeEnum.STRING, propertyRestriction);
                case URN:
                    return buildSculptorPropertyCriteria(ConfigurationProperties.urn(), PropertyTypeEnum.STRING, propertyRestriction);
                case CONTACT_URN:
                    return buildSculptorPropertyCriteria(ConfigurationProperties.contact().urn(), PropertyTypeEnum.STRING, propertyRestriction);
                case STATUS:
                    return buildSculptorPropertyCriteria(ConfigurationProperties.status(), PropertyTypeEnum.STATUS, propertyRestriction);
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

    @SuppressWarnings("rawtypes")
    private SculptorPropertyCriteria buildSculptorPropertyCriteria(Property propertyEntity, PropertyTypeEnum propertyEntityType, MetamacRestQueryPropertyRestriction restPropertyRestriction) {
        return CriteriaUtils.buildSculptorPropertyCriteria(propertyEntity, propertyEntityType.name(), restPropertyRestriction, propertyValueRestToPropertyValueEntity);
    }

    private class PropertyValueRestToPropertyValueEntity implements PropertyValueRestToPropertyValueEntityInterface {

        @Override
        public Object restValueToEntityValue(String propertyName, String value, String propertyType) {
            if (value == null) {
                return null;
            }

            try {
                PropertyTypeEnum propertyTypeEnum = PropertyTypeEnum.valueOf(propertyType);
                switch (propertyTypeEnum) {
                    case STRING:
                        return value;
                    case DATE:
                        return CoreCommonUtil.transformISODateTimeLexicalRepresentationToDateTime(value).toDate();
                    case BOOLEAN:
                        return Boolean.valueOf(value);
                    case STATUS:
                        return CommonMetadataStatusEnum.valueOf(value);
                    default:
                        throw toRestExceptionParameterIncorrect(propertyName);
                }
            } catch (Exception e) {
                logger.error("Error parsing Rest query", e);
                if (e instanceof RestException) {
                    throw (RestException) e;
                } else {
                    throw toRestExceptionParameterIncorrect(propertyName);
                }
            }
        }
    }

    private RestException toRestExceptionParameterIncorrect(String parameter) {
        org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.PARAMETER_INCORRECT, parameter);
        return new RestException(exception, Status.INTERNAL_SERVER_ERROR);
    }
}
