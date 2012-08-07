package org.siemac.metamac.common_metadata.rest.internal.v1_0.service;

import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataService;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.service.CommonMetadataRestInternalFacadeV10;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.mapper.Do2RestInternalMapperV10;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.mapper.RestCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.core.common.aop.LoggingInterceptor;
import org.siemac.metamac.rest.utils.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("commonMetadataRestInternalFacadeV10")
public class CommonMetadataRestInternalFacadeV10Impl implements CommonMetadataRestInternalFacadeV10 {

    @Autowired
    private CommonMetadataService               commonMetadataService;

    @Autowired
    private Do2RestInternalMapperV10            do2RestInternalMapper;

    @Autowired
    private RestCriteria2SculptorCriteriaMapper restCriteria2SculptorCriteriaMapper;

    // @Context // must inject with setter, because with @Context is not injected in web server
    private MessageContext                      context;

    private ServiceContext                      serviceContextRestInternal = new ServiceContext("restInternal", "restInternal", "restInternal");
    private Logger                              logger                     = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Context
    public void setMessageContext(MessageContext context) {
        this.context = context;
    }

    // TODO
    @Override
    public Configuration retrieveConfigurationById(String id) {
//        try {
//            // Retrieve
//            org.siemac.metamac.common.metadata.core.domain.Configuration configurationEntity = retrieveConfigurationEntityAnyStatus(id);
//
//            // Transform
//            Configuration configuration = do2RestInternalMapper.toConfiguration(configurationEntity, getApiUrl());
//            return configuration;
//
//        } catch (MetamacException e) {
//            throw manageException(e);
//        }
        return null;
    }

    // TODO
//    @Override
//    public ResourcesNoPagedResult findConfigurations(String query, String orderBy) {
//        try {
//            // Retrieve configurations by criteria
//            SculptorCriteria sculptorCriteria = restCriteria2SculptorCriteriaMapper.getConfigurationCriteriaMapper().restCriteriaToSculptorCriteria(query, orderBy, null, null);
//            // Find only status 'enabled'
//            ConditionalCriteria conditionalCriteriaStatus = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.Configuration.class)
//                    .withProperty(ConfigurationProperties.status()).eq(CommonMetadataStatusEnum.ENABLED).buildSingle();
//
//            List<ConditionalCriteria> conditionalCriteria = new ArrayList<ConditionalCriteria>();
//            conditionalCriteria.add(conditionalCriteriaStatus);
//            conditionalCriteria.addAll(sculptorCriteria.getConditions());
//
//            // Retrieve
//            List<org.siemac.metamac.common.metadata.core.domain.Configuration> configurationsEntitiesResult = commonMetadataService.findConfigurationByCondition(serviceContextRestInternal,
//                    conditionalCriteria);
//
//            // Transform
//            ResourcesNoPagedResult configurationsNoPagedResult = do2RestInternalMapper.toConfigurationsNoPagedResult(configurationsEntitiesResult, getApiUrl());
//            return configurationsNoPagedResult;
//
//        } catch (MetamacException e) {
//            throw manageException(e);
//        }
//    }

    // TODO
//    /**
//     * Retrieve configuration by code (id in Api) in any status
//     */
//    private org.siemac.metamac.common.metadata.core.domain.Configuration retrieveConfigurationEntityAnyStatus(String code) throws MetamacException {
//
//        List<ConditionalCriteria> conditionalCriteria = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.Configuration.class)
//                .withProperty(ConfigurationProperties.code()).eq(code).distinctRoot().build();
//        List<org.siemac.metamac.common.metadata.core.domain.Configuration> configurationEntities = commonMetadataService.findConfigurationByCondition(serviceContextRestInternal, conditionalCriteria);
//
//        if (configurationEntities.size() != 1) {
//            Error error = RestExceptionUtils.getError(RestServiceExceptionType.CONFIGURATION_NOT_FOUND, code);
//            throw new RestException(error, Status.NOT_FOUND);
//        }
//        return configurationEntities.get(0);
//    }

    /**
     * Get Base API url
     */
    private String getApiUrl() {
        return RestUtils.getApiUrl(context);
    }

    // TODO
//    /**
//     * Throws response error, logging exception
//     */
//    private RestException manageException(MetamacException e) {
//        logger.error("Error", e);
//        Error error = do2RestInternalMapper.toError(e);
//        return new RestException(error, Status.INTERNAL_SERVER_ERROR);
//    }
}