package org.siemac.metamac.common_metadata.rest.external.v1_0.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties;
import org.siemac.metamac.common.metadata.core.domain.DataConfigurationProperties;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataService;
import org.siemac.metamac.common_metadata.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.common_metadata.rest.external.v1_0.mapper.Do2RestExternalMapperV10;
import org.siemac.metamac.common_metadata.rest.external.v1_0.mapper.RestCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Properties;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Property;
import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.search.criteria.SculptorCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("commonMetadataRestExternalFacadeV10")
public class CommonMetadataRestExternalFacadeV10Impl implements CommonMetadataV1_0 {

    @Autowired
    private CommonMetadataService               commonMetadataService;

    @Autowired
    private Do2RestExternalMapperV10            do2RestExternalMapper;

    @Autowired
    private RestCriteria2SculptorCriteriaMapper restCriteria2SculptorCriteriaMapper;

    private final ServiceContext                serviceContext = new ServiceContext("restExternal", "restExternal", "restExternal");
    private final Logger                        logger         = LoggerFactory.getLogger(CommonMetadataRestExternalFacadeV10Impl.class);

    @Override
    public Configuration retrieveConfigurationById(String id) {
        try {
            // Retrieve
            List<org.siemac.metamac.common.metadata.core.domain.Configuration> configurationEntities = findConfigurationsPublishedCore(id, null);
            if (configurationEntities.size() != 1) {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.CONFIGURATION_NOT_FOUND, id);
                throw new RestException(exception, Status.NOT_FOUND);
            }
            org.siemac.metamac.common.metadata.core.domain.Configuration configurationEntity = configurationEntities.get(0);

            // Transform
            Configuration configuration = do2RestExternalMapper.toConfiguration(configurationEntity);
            return configuration;

        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Configurations findConfigurations(String query, String orderBy) {
        try {
            // Retrieve configurations by criteria
            SculptorCriteria sculptorCriteria = restCriteria2SculptorCriteriaMapper.getConfigurationCriteriaMapper().restCriteriaToSculptorCriteria(query, orderBy, null, null);

            // Find
            List<org.siemac.metamac.common.metadata.core.domain.Configuration> configurationsEntitiesResult = findConfigurationsPublishedCore(null, sculptorCriteria.getConditions());

            // Transform
            Configurations configurations = do2RestExternalMapper.toConfigurations(configurationsEntitiesResult);
            return configurations;

        } catch (Exception e) {
            throw manageException(e);
        }
    }

    private List<org.siemac.metamac.common.metadata.core.domain.Configuration> findConfigurationsPublishedCore(String id, List<ConditionalCriteria> conditionalCriteriaQuery) throws MetamacException {

        List<ConditionalCriteria> conditionalCriteria = new ArrayList<ConditionalCriteria>();
        if (CollectionUtils.isNotEmpty(conditionalCriteriaQuery)) {
            conditionalCriteria.addAll(conditionalCriteriaQuery);
        } else {
            // init
            conditionalCriteria.addAll(ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.Configuration.class).distinctRoot().build());
        }
        if (id != null) {
            conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.Configuration.class).withProperty(ConfigurationProperties.code()).eq(id)
                    .buildSingle());
        }
        // Find only published
        conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.Configuration.class).withProperty(ConfigurationProperties.externallyPublished())
                .eq(Boolean.TRUE).buildSingle());

        // Find
        return commonMetadataService.findConfigurationByCondition(serviceContext, conditionalCriteria);
    }

    /**
     * Throws response error, logging exception
     */
    private RestException manageException(Exception e) {
        logger.error("Error", e);
        if (e instanceof RestException) {
            return (RestException) e;
        } else {
            // do not show information details about exception to user
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestCommonServiceExceptionType.UNKNOWN);
            return new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Properties findProperties(String query, String orderBy) {
        try {
            // Retrieve configurations by criteria
            SculptorCriteria sculptorCriteria = restCriteria2SculptorCriteriaMapper.getPropertyCriteriaMapper().restCriteriaToSculptorCriteria(query, orderBy, null, null);

            // Find
            List<org.siemac.metamac.common.metadata.core.domain.DataConfiguration> propertiesEntitiesResult = findPropertiesPublishedCore(null, sculptorCriteria.getConditions());

            // Transform
            Properties properties = do2RestExternalMapper.toProperties(propertiesEntitiesResult);
            return properties;

        } catch (Exception e) {
            throw manageException(e);
        }
    }
    
    private List<org.siemac.metamac.common.metadata.core.domain.DataConfiguration> findPropertiesPublishedCore(String key, List<ConditionalCriteria> conditionalCriteriaQuery) throws MetamacException {

        List<ConditionalCriteria> conditionalCriteria = new ArrayList<ConditionalCriteria>();
        if (CollectionUtils.isNotEmpty(conditionalCriteriaQuery)) {
            conditionalCriteria.addAll(conditionalCriteriaQuery);
        } else {
            // init
            conditionalCriteria.addAll(ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.DataConfiguration.class).distinctRoot().build());
        }
        if (key != null) {
            conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.DataConfiguration.class)
                    .withProperty(DataConfigurationProperties.configurationKey()).eq(key).buildSingle());
        }
        // Find only published
        conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.DataConfiguration.class).withProperty(DataConfigurationProperties.externallyPublished())
                .eq(Boolean.TRUE).buildSingle());

        // Find
        return commonMetadataService.findDataConfigurationsByCondition(serviceContext, conditionalCriteria);
    }

    @Override
    public Property retrievePropertyByKey(String key) {
        // TODO Auto-generated method stub
        return null;
    }
}