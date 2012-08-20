package org.siemac.metamac.common_metadata.rest.external.v1_0.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataService;
import org.siemac.metamac.common_metadata.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.common_metadata.rest.external.v1_0.mapper.Do2RestExternalMapperV10;
import org.siemac.metamac.common_metadata.rest.external.v1_0.mapper.RestCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.core.common.aop.LoggingInterceptor;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;
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

    private ServiceContext                      serviceContext = new ServiceContext("restExternal", "restExternal", "restExternal");
    private Logger                              logger         = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public Configuration retrieveConfigurationById(String id) {
        try {
            // Retrieve
            org.siemac.metamac.common.metadata.core.domain.Configuration configurationEntity = retrieveConfigurationEntityAnyStatus(id);

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
            // Find only status 'enabled'
            ConditionalCriteria conditionalCriteriaStatus = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.Configuration.class)
                    .withProperty(ConfigurationProperties.status()).eq(CommonMetadataStatusEnum.ENABLED).buildSingle();

            List<ConditionalCriteria> conditionalCriteria = new ArrayList<ConditionalCriteria>();
            conditionalCriteria.add(conditionalCriteriaStatus);
            conditionalCriteria.addAll(sculptorCriteria.getConditions());

            // Retrieve
            List<org.siemac.metamac.common.metadata.core.domain.Configuration> configurationsEntitiesResult = commonMetadataService.findConfigurationByCondition(serviceContext, conditionalCriteria);

            // Transform
            Configurations configurations = do2RestExternalMapper.toConfigurations(configurationsEntitiesResult);
            return configurations;

        } catch (Exception e) {
            throw manageException(e);
        }
    }

    /**
     * Retrieve configuration by code (id in Api) in any status
     */
    private org.siemac.metamac.common.metadata.core.domain.Configuration retrieveConfigurationEntityAnyStatus(String id) throws MetamacException {

        List<ConditionalCriteria> conditionalCriteria = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.Configuration.class)
                .withProperty(ConfigurationProperties.code()).eq(id).distinctRoot().build();
        List<org.siemac.metamac.common.metadata.core.domain.Configuration> configurationEntities = commonMetadataService.findConfigurationByCondition(serviceContext, conditionalCriteria);

        if (configurationEntities.size() != 1) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.CONFIGURATION_NOT_FOUND, id);
            throw new RestException(exception, Status.NOT_FOUND);
        }
        return configurationEntities.get(0);
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
}