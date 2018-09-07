package org.siemac.metamac.common_metadata.rest.external.v1_0.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.BeforeClass;
import org.junit.Test;
import org.siemac.metamac.common.metadata.core.conf.CommonMetadataConfigurationService;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataService;
import org.siemac.metamac.common_metadata.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.common_metadata.rest.external.v1_0.mockito.FindConfigurationsByIdMatcher;
import org.siemac.metamac.common_metadata.rest.external.v1_0.mockito.FindConfigurationsMatcher;
import org.siemac.metamac.common_metadata.rest.external.v1_0.utils.CommonMetadataCoreMocks;
import org.siemac.metamac.common_metadata.rest.external.v1_0.utils.CommonMetadataRestAsserts;
import org.siemac.metamac.common_metadata.rest.external.v1_0.utils.CommonMetadataRestMocks;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.common.test.MetamacRestBaseTest;
import org.siemac.metamac.rest.common.test.ServerResource;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.CommonMetadataStatus;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ConfigurationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;
import org.siemac.metamac.rest.constants.RestConstants;
import org.siemac.metamac.rest.utils.RestUtils;
import org.springframework.context.ApplicationContext;

public class CommonMetadataRestExternalFacadeV10Test extends MetamacRestBaseTest {

    private static final String            PORT                                 = ServerResource.PORT;
    private static String                  jaxrsServerAddress                   = "http://localhost:" + PORT + "/apis/cmetadata";
    private static String                  baseApi                              = jaxrsServerAddress + "/v1.0";

    private static String                  commonMetadataInternalWebApplication;
    private static String                  srmInternalWebApplication;
    private static String                  srmApiExternalEndpoint;

    // not read property from properties file to check explicity
    private static String                  commonMetadataApiExternalEndpointV10 = "http://data.istac.es/apis/cmetadata/v1.0";

    private static CommonMetadataV1_0      commonMetadataRestExternalFacadeClientXml;

    private static ApplicationContext      applicationContext                   = null;

    private static String                  NOT_EXISTS                           = "NOT_EXISTS";

    public static String                   CONFIGURATION_1                      = "configuration1";
    public static String                   CONFIGURATION_2                      = "configuration2";
    public static String                   CONFIGURATION_3                      = "configuration3";
    public static String                   CONFIGURATION_15                     = "configuration15";
    public static String                   QUERY_CONFIGURATION_ID_LIKE_1        = ConfigurationCriteriaPropertyRestriction.ID + " " + ComparisonOperator.LIKE + " \"1\"";

    private static CommonMetadataRestMocks commonMetadataRestMocks;
    private static CommonMetadataCoreMocks commonMetadataCoreMocks;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        // Start server
        assertTrue("server did not launch correctly", launchServer(ServerResource.class, true));

        // Get application context from Jetty
        applicationContext = ApplicationContextProvider.getApplicationContext();

        // Rest clients
        // xml
        {
            List providers = new ArrayList();
            providers.add(applicationContext.getBean("jaxbProvider", JAXBElementProvider.class));
            commonMetadataRestExternalFacadeClientXml = JAXRSClientFactory.create(jaxrsServerAddress, CommonMetadataV1_0.class, providers, Boolean.TRUE);
        }

        // Configuration
        CommonMetadataConfigurationService configurationService = applicationContext.getBean(CommonMetadataConfigurationService.class);
        commonMetadataInternalWebApplication = configurationService.retrieveCommonMetadataInternalWebApplicationUrlBase();
        srmInternalWebApplication = configurationService.retrieveSrmInternalWebApplicationUrlBase();
        srmApiExternalEndpoint = configurationService.retrieveSrmExternalApiUrlBase();

        // Mockito
        commonMetadataRestMocks = new CommonMetadataRestMocks(commonMetadataApiExternalEndpointV10, commonMetadataInternalWebApplication, srmApiExternalEndpoint, srmInternalWebApplication);
        commonMetadataCoreMocks = new CommonMetadataCoreMocks();
        setUpMockito();
    }

    @Test
    public void testWithoutMatchError404() throws Exception {

        String requestUri = baseApi + "/nomatch";

        WebClient webClient = WebClient.create(requestUri).accept(APPLICATION_XML);
        Response response = webClient.get();

        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
        InputStream responseActual = (InputStream) response.getEntity();
        assertTrue(StringUtils.isBlank(IOUtils.toString(responseActual)));
    }

    @Test
    public void testRetrieveConfigurationByIdXml() throws Exception {

        // Retrieve
        Configuration configuration = getCommonMetadataRestExternalFacadeClientXml().retrieveConfigurationById(CONFIGURATION_1);

        // Validation
        CommonMetadataRestAsserts.assertEqualsConfiguration(commonMetadataRestMocks.mockConfiguration1(), configuration);
    }

    @Test
    public void testRetrieveConfigurationDisabledByIdXml() throws Exception {

        // Retrieve
        Configuration configuration = getCommonMetadataRestExternalFacadeClientXml().retrieveConfigurationById(CONFIGURATION_3);

        // Validation
        assertEquals(CommonMetadataStatus.DISABLED, configuration.getStatus());
        CommonMetadataRestAsserts.assertEqualsConfiguration(commonMetadataRestMocks.mockConfiguration3(), configuration);
    }

    @Test
    public void testRetrieveConfigurationByIdXmlWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1);
        InputStream responseExpected = CommonMetadataRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveConfigurationByIdXmlWithoutJaxbTransformationWithSufix() throws Exception {

        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1) + ".xml";
        InputStream responseExpected = CommonMetadataRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveConfigurationByIdXmlWithoutJaxbTransformationWithTypeParameter() throws Exception {

        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1) + "?_type=xml";
        InputStream responseExpected = CommonMetadataRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveConfigurationByIdErrorNotExistsXml() throws Exception {
        try {
            getCommonMetadataRestExternalFacadeClientXml().retrieveConfigurationById(NOT_EXISTS);
        } catch (ServerWebApplicationException e) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = extractErrorFromException(commonMetadataRestExternalFacadeClientXml, e);

            assertEquals(RestServiceExceptionType.CONFIGURATION_NOT_FOUND.getCode(), exception.getCode());
            assertEquals("Configuration not found with id " + NOT_EXISTS, exception.getMessage());
            assertEquals(1, exception.getParameters().getParameters().size());
            assertEquals(NOT_EXISTS, exception.getParameters().getParameters().get(0));
            assertNull(exception.getErrors());
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    @Test
    public void testRetrieveConfigurationByIdErrorNotExistsXmlWithoutJaxbTransformation() throws Exception {
        String requestUri = getRequestUriRetrieveConfigurationById(NOT_EXISTS);
        InputStream responseExpected = CommonMetadataRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.notFound.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.NOT_FOUND, responseExpected);
    }
    
    @Test
    public void testRetrieveConfigurationByIdJsonWithoutJaxbTransformation() throws Exception {
        
        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1);
        InputStream responseExpected = CommonMetadataRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.json");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.OK, responseExpected);
    }
    
    @Test
    public void testRetrieveConfigurationByIdJsonWithoutJaxbTransformationWithJsonSufix() throws Exception {
        
        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1) + ".json";
        InputStream responseExpected = CommonMetadataRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.json");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.OK, responseExpected);
    }
    
    @Test
    public void testRetrieveConfigurationByIdJsonWithoutJaxbTransformationWithTypeParameter() throws Exception {
        
        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1) + "?_type=json";
        InputStream responseExpected = CommonMetadataRestExternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.json");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveConfigurationByIdJsonOk() throws Exception {

        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1);

        // Request and validate
        WebClient webClient = WebClient.create(requestUri).accept("application/json");
        Response response = webClient.get();

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testFindConfigurationsXml() throws Exception {

        {
            // without query
            String query = null;
            String orderBy = null;
            Configurations configurations = getCommonMetadataRestExternalFacadeClientXml().findConfigurations(query, orderBy);
            CommonMetadataRestAsserts.assertEqualsConfigurations(commonMetadataRestMocks.mockConfigurations(null), configurations);
        }
        {
            // query by id
            String query = QUERY_CONFIGURATION_ID_LIKE_1; // configuration1 and configuration15
            String orderBy = null;
            Configurations configurations = getCommonMetadataRestExternalFacadeClientXml().findConfigurations(query, orderBy);
            CommonMetadataRestAsserts.assertEqualsConfigurations(commonMetadataRestMocks.mockConfigurations(query), configurations);
        }
    }

    @Test
    public void testFindConfigurationsXmlWithoutJaxbTransformation() throws Exception {
        {
            // without query
            String requestUri = getRequestUriFindConfigurations(null);
            InputStream responseExpected = CommonMetadataRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findConfigurations.noquery.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
        {
            // query by id
            String query = QUERY_CONFIGURATION_ID_LIKE_1;
            String requestUri = getRequestUriFindConfigurations(query);
            InputStream responseExpected = CommonMetadataRestExternalFacadeV10Test.class.getResourceAsStream("/responses/findConfigurations.query1.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    private String getRequestUriRetrieveConfigurationById(String configurationId) {
        return baseApi + "/configurations/" + configurationId;
    }

    private String getRequestUriFindConfigurations(String query) throws Exception {
        String url = baseApi + "/configurations";
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_QUERY, RestUtils.encodeParameter(query));
        return url;
    }

    private static void setUpMockito() throws MetamacException {
        // MOCKS
        CommonMetadataService commonMetadataService = applicationContext.getBean(CommonMetadataService.class);

        // Retrieve configuration
        mockitoFindConfigurationById(commonMetadataService, CONFIGURATION_1);
        mockitoFindConfigurationById(commonMetadataService, CONFIGURATION_2);
        mockitoFindConfigurationById(commonMetadataService, CONFIGURATION_3);
        mockitoFindConfigurationById(commonMetadataService, CONFIGURATION_15);

        // Find configurations
        mockitoFindConfigurationByCondition(commonMetadataService, null);
        mockitoFindConfigurationByCondition(commonMetadataService, QUERY_CONFIGURATION_ID_LIKE_1);
    }

    private static void mockitoFindConfigurationById(CommonMetadataService commonMetadataService, String id) throws MetamacException {
        List<org.siemac.metamac.common.metadata.core.domain.Configuration> configurationEntities = new ArrayList<org.siemac.metamac.common.metadata.core.domain.Configuration>();
        if (CONFIGURATION_1.equals(id)) {
            configurationEntities.add(commonMetadataCoreMocks.mockConfiguration1());
        } else if (CONFIGURATION_2.equals(id)) {
            configurationEntities.add(commonMetadataCoreMocks.mockConfiguration2());
        } else if (CONFIGURATION_3.equals(id)) {
            configurationEntities.add(commonMetadataCoreMocks.mockConfiguration3());
        } else if (CONFIGURATION_15.equals(id)) {
            configurationEntities.add(commonMetadataCoreMocks.mockConfiguration15());
        } else if (NOT_EXISTS.equals(id)) {
            // no exits
        }

        // Mock
        when(commonMetadataService.findConfigurationByCondition(any(ServiceContext.class), argThat(new FindConfigurationsByIdMatcher(id)))).thenReturn(configurationEntities);
    }

    private static void mockitoFindConfigurationByCondition(CommonMetadataService commonMetadataService, String query) throws MetamacException {
        List<ConditionalCriteria> conditionalCriterias = null;
        String querySupported1 = QUERY_CONFIGURATION_ID_LIKE_1;

        if (querySupported1.equals(query)) {
            conditionalCriterias = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.Configuration.class).withProperty(ConfigurationProperties.code()).like("%1%")
                    .build();
        } else {
            conditionalCriterias = new ArrayList<ConditionalCriteria>();
        }
        when(commonMetadataService.findConfigurationByCondition(any(ServiceContext.class), argThat(new FindConfigurationsMatcher(conditionalCriterias, null)))).thenReturn(
                commonMetadataCoreMocks.mockConfigurationsNoPagedResult(query));
    }

    private CommonMetadataV1_0 getCommonMetadataRestExternalFacadeClientXml() {
        WebClient.client(commonMetadataRestExternalFacadeClientXml).reset();
        WebClient.client(commonMetadataRestExternalFacadeClientXml).accept(APPLICATION_XML);
        return commonMetadataRestExternalFacadeClientXml;
    }
}
