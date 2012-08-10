package org.siemac.metamac.common_metadata.rest.internal.v1_0.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.BeforeClass;
import org.junit.Test;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataService;
import org.siemac.metamac.common_metadata.rest.internal.exception.RestServiceExceptionType;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.CommonMetadataStatus;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.ConfigurationCriteriaPropertyRestriction;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.Configurations;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.mockito.FindConfigurationsByIdMatcher;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.mockito.FindConfigurationsMatcher;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.utils.CommonMetadataCoreMocks;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.utils.CommonMetadataRestAsserts;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.utils.CommonMetadataRestMocks;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.common.test.MetamacRestBaseTest;
import org.siemac.metamac.rest.common.test.ServerResource;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.constants.RestConstants;
import org.siemac.metamac.rest.utils.RestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.util.UriUtils;

public class CommonMetadataRestInternalFacadeV10Test extends MetamacRestBaseTest {

    private static final String                        PORT                          = ServerResource.PORT;
    private static String                              jaxrsServerAddress            = "http://localhost:" + PORT + "/internal";
    private static String                              baseApi                       = jaxrsServerAddress + "/v1.0";

    private static CommonMetadataRestInternalFacadeV10 commonMetadataRestInternalFacadeClientXml;

    private static ApplicationContext                  applicationContext            = null;

    private static String                              NOT_EXISTS                    = "NOT_EXISTS";

    public static String                               CONFIGURATION_1               = "configuration1";
    public static String                               CONFIGURATION_2               = "configuration2";
    public static String                               CONFIGURATION_3               = "configuration3";
    public static String                               CONFIGURATION_15              = "configuration15";
    public static String                               QUERY_CONFIGURATION_ID_LIKE_1 = ConfigurationCriteriaPropertyRestriction.ID + " " + ComparisonOperator.LIKE + " \"1\"";

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
            commonMetadataRestInternalFacadeClientXml = JAXRSClientFactory.create(jaxrsServerAddress, CommonMetadataRestInternalFacadeV10.class, providers, Boolean.TRUE);
        }

        // Mockito
        setUpMockito();
    }

    @Test
    public void testWithoutMatchError404() throws Exception {

        String requestUri = baseApi + "/nomatch";

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.NOT_FOUND, new ByteArrayInputStream(new byte[0]));
    }

    @Test
    public void testRetrieveConfigurationByIdXml() throws Exception {

        // Retrieve
        Configuration configuration = getCommonMetadataRestInternalFacadeClientXml().retrieveConfigurationById(CONFIGURATION_1);

        // Validation
        CommonMetadataRestAsserts.assertEqualsConfiguration(CommonMetadataRestMocks.mockConfiguration1(baseApi), configuration);
    }

    @Test
    public void testRetrieveConfigurationDisabledByIdXml() throws Exception {

        // Retrieve
        Configuration configuration = getCommonMetadataRestInternalFacadeClientXml().retrieveConfigurationById(CONFIGURATION_3);

        // Validation
        assertEquals(CommonMetadataStatus.DISABLED, configuration.getStatus());
        CommonMetadataRestAsserts.assertEqualsConfiguration(CommonMetadataRestMocks.mockConfiguration3(baseApi), configuration);
    }

    @Test
    public void testRetrieveConfigurationByIdXmlWithoutJaxbTransformation() throws Exception {

        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1);
        InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveConfigurationByIdXmlWithoutJaxbTransformationWithSufix() throws Exception {

        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1) + ".xml";
        InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveConfigurationByIdXmlWithoutJaxbTransformationWithTypeParameter() throws Exception {

        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1) + "?_type=xml";
        InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveConfigurationByIdErrorNotExistsXml() throws Exception {
        try {
            getCommonMetadataRestInternalFacadeClientXml().retrieveConfigurationById(NOT_EXISTS);
        } catch (ServerWebApplicationException e) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = extractErrorFromException(commonMetadataRestInternalFacadeClientXml, e);

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
        InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.notFound.xml");

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.NOT_FOUND, responseExpected);
    }

    @Test
    public void testRetrieveConfigurationByIdJsonNonAcceptable() throws Exception {

        String requestUri = getRequestUriRetrieveConfigurationById(NOT_EXISTS);

        // Request and validate
        WebClient webClient = WebClient.create(requestUri).accept("application/json");
        Response response = webClient.get();

        assertEquals(Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
    }

    @Test
    public void testFindConfigurationsXml() throws Exception {

        {
            // without query
            String query = null;
            String orderBy = null;
            Configurations configurations = getCommonMetadataRestInternalFacadeClientXml().findConfigurations(query, orderBy);
            CommonMetadataRestAsserts.assertEqualsConfigurations(CommonMetadataRestMocks.mockConfigurations(baseApi, null), configurations);
        }
        {
            // query by id
            String query = QUERY_CONFIGURATION_ID_LIKE_1; // configuration1 and configuration15
            String orderBy = null;
            Configurations configurations = getCommonMetadataRestInternalFacadeClientXml().findConfigurations(query, orderBy);
            CommonMetadataRestAsserts.assertEqualsConfigurations(CommonMetadataRestMocks.mockConfigurations(baseApi, query), configurations);
        }
    }

    @Test
    public void testFindConfigurationsXmlWithoutJaxbTransformation() throws Exception {
        {
            // without query
            String requestUri = getRequestUriFindConfigurations(null);
            InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/findConfigurations.noquery.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
        {
            // query by id
            String query = QUERY_CONFIGURATION_ID_LIKE_1;
            String requestUri = getRequestUriFindConfigurations(query);
            InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/findConfigurations.query1.xml");

            // Request and validate
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    private String encodeParameter(String parameter) throws Exception {
        if (parameter == null) {
            return null;
        }
        parameter = UriUtils.encodePath(parameter, "UTF-8");
        return parameter;
    }

    private String getRequestUriRetrieveConfigurationById(String configurationId) {
        return baseApi + "/configurations/" + configurationId;
    }

    private String getRequestUriFindConfigurations(String query) throws Exception {
        String url = baseApi + "/configurations";
        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_QUERY, encodeParameter(query));
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
            configurationEntities.add(CommonMetadataCoreMocks.mockConfiguration1());
        } else if (CONFIGURATION_2.equals(id)) {
            configurationEntities.add(CommonMetadataCoreMocks.mockConfiguration2());
        } else if (CONFIGURATION_3.equals(id)) {
            configurationEntities.add(CommonMetadataCoreMocks.mockConfiguration3());
        } else if (CONFIGURATION_15.equals(id)) {
            configurationEntities.add(CommonMetadataCoreMocks.mockConfiguration15());
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
                CommonMetadataCoreMocks.mockConfigurationsNoPagedResult(query));
    }

    private CommonMetadataRestInternalFacadeV10 getCommonMetadataRestInternalFacadeClientXml() {
        WebClient.client(commonMetadataRestInternalFacadeClientXml).reset();
        WebClient.client(commonMetadataRestInternalFacadeClientXml).accept(APPLICATION_XML);
        return commonMetadataRestInternalFacadeClientXml;
    }
}
