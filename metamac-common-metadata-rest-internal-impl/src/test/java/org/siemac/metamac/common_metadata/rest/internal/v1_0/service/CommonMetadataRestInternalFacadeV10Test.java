package org.siemac.metamac.common_metadata.rest.internal.v1_0.service;

import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.ConfigurationCriteriaPropertyRestriction;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.service.CommonMetadataRestInternalFacadeV10;
import org.siemac.metamac.rest.common.test.MetamacRestBaseTest;
import org.siemac.metamac.rest.common.test.ServerResource;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.springframework.context.ApplicationContext;

public class CommonMetadataRestInternalFacadeV10Test extends MetamacRestBaseTest {

    private static final String                PORT                          = ServerResource.PORT;
    private static String                      baseApi                       = "http://localhost:" + PORT + "/internal/v1.0";

    private static CommonMetadataRestInternalFacadeV10 commonMetadataRestInternalFacadeClientXml;
    private static CommonMetadataRestInternalFacadeV10 commonMetadataRestInternalFacadeClientJson;

    private static ApplicationContext          applicationContext            = null;

    private static String                      NOT_EXISTS                    = "NOT_EXISTS";

    public static String                       CONFIGURATION_1               = "configuration1";
    public static String                       CONFIGURATION_2               = "configuration2";
    public static String                       CONFIGURATION_3               = "configuration3";
    public static String                       CONFIGURATION_15               = "configuration15";
    public static String                       QUERY_CONFIGURATION_ID_LIKE_1 = ConfigurationCriteriaPropertyRestriction.ID + " " + ComparisonOperator.LIKE + " \"1\"";

    // TODO
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @BeforeClass
//    public static void setUpBeforeClass() throws Exception {
//
//        // Start server
//        assertTrue("server did not launch correctly", launchServer(ServerResource.class, true));
//
//        // Get application context from Jetty
//        applicationContext = ApplicationContextProvider.getApplicationContext();
//
//        // Rest clients
//        // xml
//        {
//            List providers = new ArrayList();
//            providers.add(new org.apache.cxf.jaxrs.provider.JAXBElementProvider());
//            commonMetadataRestInternalFacadeClientXml = JAXRSClientFactory.create(baseApi, CommonMetadataRestInternalFacadeV10.class, providers, Boolean.TRUE);
//        }
//        // json
//        {
//            List providers = new ArrayList();
//            providers.add(new org.codehaus.jackson.jaxrs.JacksonJsonProvider());
//            commonMetadataRestInternalFacadeClientJson = JAXRSClientFactory.create(baseApi, CommonMetadataRestInternalFacadeV10.class, providers, Boolean.TRUE);
//        }
//        // Mockito
//        setUpMockito();
//    }
//
//    @Test
//    public void testWithoutMatchError404() throws Exception {
//
//        String requestUri = baseApi + "/nomatch";
//
//        // Request and validate
//        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.NOT_FOUND, new ByteArrayInputStream(new byte[0]));
//    }
//
//    @Test
//    public void testRetrieveConfigurationByIdXml() throws Exception {
//
//        // Retrieve
//        Configuration configuration = getCommonMetadataRestInternalFacadeClientXml().retrieveConfigurationById(CONFIGURATION_1);
//
//        // Validation
//        CommonMetadataRestAsserts.assertEqualsConfiguration(CommonMetadataRestMocks.mockConfiguration1(baseApi), configuration);
//    }
//    
//    @Test
//    public void testRetrieveConfigurationDisabledByIdXml() throws Exception {
//
//        // Retrieve
//        Configuration configuration = getCommonMetadataRestInternalFacadeClientXml().retrieveConfigurationById(CONFIGURATION_3);
//
//        // Validation
//        assertEquals(CommonMetadataStatus.DISABLED, configuration.getStatus());
//        CommonMetadataRestAsserts.assertEqualsConfiguration(CommonMetadataRestMocks.mockConfiguration3(baseApi), configuration);
//    }
//
//    @Test
//    public void testRetrieveConfigurationByIdJson() throws Exception {
//
//        // Retrieve
//        Configuration configuration = getCommonMetadataRestInternalFacadeClientJson().retrieveConfigurationById(CONFIGURATION_1);
//
//        // Validation
//        CommonMetadataRestAsserts.assertEqualsConfiguration(CommonMetadataRestMocks.mockConfiguration1(baseApi), configuration);
//    }
//
//    @Test
//    public void testRetrieveConfigurationByIdXmlWithoutJaxbTransformation() throws Exception {
//
//        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1);
//        InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.xml");
//
//        // Request and validate
//        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
//    }
//    
//    @Test
//    public void testRetrieveConfigurationByIdXmlWithoutJaxbTransformationWithSufix() throws Exception {
//
//        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1) + ".xml";
//        InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.xml");
//
//        // Request and validate
//        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
//    }
//
//    @Test
//    public void testRetrieveConfigurationByIdXmlWithoutJaxbTransformationWithTypeParameter() throws Exception {
//
//        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1) + "?_type=xml";
//        InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.xml");
//
//        // Request and validate
//        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
//    }
//
//    @Test
//    public void testRetrieveConfigurationByIdJsonWithoutJaxbTransformation() throws Exception {
//
//        String requestUri = getRequestUriRetrieveConfigurationById(CONFIGURATION_1);
//        InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.id1.json");
//
//        // Request and validate
//        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.OK, responseExpected);
//    }
//
//    @Test
//    public void testRetrieveConfigurationByIdErrorNotExistsXml() throws Exception {
//        try {
//            getCommonMetadataRestInternalFacadeClientXml().retrieveConfigurationById(NOT_EXISTS);
//        } catch (ServerWebApplicationException e) {
//            org.siemac.metamac.rest.common.v1_0.domain.Error error = extractErrorFromException(commonMetadataRestInternalFacadeClientXml, e);
//
//            assertEquals(1, error.getErrorItems().size());
//            assertEquals(RestServiceExceptionType.CONFIGURATION_NOT_FOUND.getCode(), error.getErrorItems().get(0).getCode());
//            assertEquals("Configuration not found with id {0}", error.getErrorItems().get(0).getMessage());
//            assertEquals(1, error.getErrorItems().get(0).getParameters().size());
//            assertEquals(NOT_EXISTS, error.getErrorItems().get(0).getParameters().get(0));
//        } catch (Exception e) {
//            fail("Incorrect exception");
//        }
//    }
//
//    @Test
//    public void testRetrieveConfigurationByIdErrorNotExistsXmlWithoutJaxbTransformation() throws Exception {
//        String requestUri = getRequestUriRetrieveConfigurationById(NOT_EXISTS);
//        InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.notFound.xml");
//
//        // Request and validate
//        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.NOT_FOUND, responseExpected);
//    }
//
//    @Test
//    public void testRetrieveConfigurationByIdErrorNotExistsJson() throws Exception {
//
//        try {
//            getCommonMetadataRestInternalFacadeClientJson().retrieveConfigurationById(NOT_EXISTS);
//        } catch (Exception e) {
//            // note: do not work 'extractErrorFromException'
//            InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.notFound.json");
//            InputStream responseActual = (InputStream) ((ServerWebApplicationException) e).getResponse().getEntity();
//            MetamacRestAsserts.assertEqualsResponse(responseExpected, responseActual);
//        }
//    }
//
//    @Test
//    public void testRetrieveConfigurationByIdErrorNotExistsJsonWithoutJaxbTransformation() throws Exception {
//        String requestUri = getRequestUriRetrieveConfigurationById(NOT_EXISTS);
//        InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationById.notFound.json");
//
//        // Request and validate
//        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.NOT_FOUND, responseExpected);
//    }
//
//    @Test
//    public void testFindConfigurationsXml() throws Exception {
//
//        {
//            // without query
//            String query = null;
//            String orderBy = null;
//            ResourcesNoPagedResult noPagedResult = getCommonMetadataRestInternalFacadeClientXml().findConfigurations(query, orderBy);
//            MetamacRestAsserts.assertEqualsResourcesNoPagedResult(CommonMetadataRestMocks.mockConfigurationsNoPagedResult(baseApi, null), noPagedResult);
//        }
//        {
//            // query by id
//            String query = QUERY_CONFIGURATION_ID_LIKE_1; // configuration1 and configuration15
//            String orderBy = null;
//            ResourcesNoPagedResult noPagedResult = getCommonMetadataRestInternalFacadeClientXml().findConfigurations(query, orderBy);
//            MetamacRestAsserts.assertEqualsResourcesNoPagedResult(CommonMetadataRestMocks.mockConfigurationsNoPagedResult(baseApi, query), noPagedResult);
//        }
//    }
//
//    @Test
//    public void testFindConfigurationsXmlWithoutJaxbTransformation() throws Exception {
//        {
//            // without query
//            String requestUri = getRequestUriFindConfigurations(null);
//            InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/findConfigurations.noquery.xml");
//
//            // Request and validate
//            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
//        }
//        {
//            // query by id
//            String query = QUERY_CONFIGURATION_ID_LIKE_1;
//            String requestUri = getRequestUriFindConfigurations(query);
//            InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/findConfigurations.query1.xml");
//
//            // Request and validate
//            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
//        }
//    }
//
//    @Test
//    public void testFindConfigurationsJsonWithoutJaxbTransformation() throws Exception {
//        {
//            // without query
//            String requestUri = getRequestUriFindConfigurations(null);
//            InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/findConfigurations.noquery.json");
//
//            // Request and validate
//            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.OK, responseExpected);
//        }
//        {
//            // query by id
//            String query = QUERY_CONFIGURATION_ID_LIKE_1;
//            String requestUri = getRequestUriFindConfigurations(query);
//            InputStream responseExpected = CommonMetadataRestInternalFacadeV10Test.class.getResourceAsStream("/responses/findConfigurations.query1.json");
//
//            // Request and validate
//            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.OK, responseExpected);
//        }
//    }
//
//    private String encodeParameter(String parameter) throws Exception {
//        if (parameter == null) {
//            return null;
//        }
//        parameter = UriUtils.encodePath(parameter, "UTF-8");
//        return parameter;
//    }
//
//    private String getRequestUriRetrieveConfigurationById(String configurationId) {
//        return baseApi + "/configurations/" + configurationId;
//    }
//
//    private String getRequestUriFindConfigurations(String query) throws Exception {
//        String url = baseApi + "/configurations";
//        url = RestUtils.createLinkWithQueryParam(url, RestConstants.PARAMETER_QUERY, encodeParameter(query));
//        return url;
//    }
//
//    private static void setUpMockito() throws MetamacException {
//        // MOCKS
//        CommonMetadataService commonMetadataService = applicationContext.getBean(CommonMetadataService.class);
//
//        // Retrieve configuration
//        mockitoFindConfigurationById(commonMetadataService, CONFIGURATION_1);
//        mockitoFindConfigurationById(commonMetadataService, CONFIGURATION_2);
//        mockitoFindConfigurationById(commonMetadataService, CONFIGURATION_3);
//        mockitoFindConfigurationById(commonMetadataService, CONFIGURATION_15);
//
//        // Find configurations
//        mockitoFindConfigurationByCondition(commonMetadataService, null);
//        mockitoFindConfigurationByCondition(commonMetadataService, QUERY_CONFIGURATION_ID_LIKE_1);
//    }
//
//    private static void mockitoFindConfigurationById(CommonMetadataService commonMetadataService, String id) throws MetamacException {
//        List<org.siemac.metamac.common.metadata.core.domain.Configuration> configurationEntities = new ArrayList<org.siemac.metamac.common.metadata.core.domain.Configuration>();
//        if (CONFIGURATION_1.equals(id)) {
//            configurationEntities.add(CommonMetadataCoreMocks.mockConfiguration1());
//        } else if (CONFIGURATION_2.equals(id)) {
//            configurationEntities.add(CommonMetadataCoreMocks.mockConfiguration2());
//        } else if (CONFIGURATION_3.equals(id)) {
//            configurationEntities.add(CommonMetadataCoreMocks.mockConfiguration3());
//        } else if (CONFIGURATION_15.equals(id)) {
//            configurationEntities.add(CommonMetadataCoreMocks.mockConfiguration15());
//        } else if (NOT_EXISTS.equals(id)) {
//            // no exits
//        }
//
//        // Mock
//        when(commonMetadataService.findConfigurationByCondition(any(ServiceContext.class), argThat(new FindConfigurationsByIdMatcher(id)))).thenReturn(configurationEntities);
//    }
//
//    private static void mockitoFindConfigurationByCondition(CommonMetadataService commonMetadataService, String query) throws MetamacException {
//        List<ConditionalCriteria> conditionalCriterias = null;
//        String querySupported1 = QUERY_CONFIGURATION_ID_LIKE_1;
//
//        if (querySupported1.equals(query)) {
//            conditionalCriterias = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.Configuration.class).withProperty(ConfigurationProperties.code()).like("%1%")
//                    .build();
//        } else {
//            conditionalCriterias = new ArrayList<ConditionalCriteria>();
//        }
//        when(commonMetadataService.findConfigurationByCondition(any(ServiceContext.class), argThat(new FindConfigurationsMatcher(conditionalCriterias, null)))).thenReturn(
//                CommonMetadataCoreMocks.mockConfigurationsNoPagedResult(query));
//    }
//
//    private CommonMetadataRestInternalFacadeV10 getCommonMetadataRestInternalFacadeClientXml() {
//        WebClient.client(commonMetadataRestInternalFacadeClientXml).reset();
//        WebClient.client(commonMetadataRestInternalFacadeClientXml).accept(APPLICATION_XML);
//        return commonMetadataRestInternalFacadeClientXml;
//    }
//
//    private CommonMetadataRestInternalFacadeV10 getCommonMetadataRestInternalFacadeClientJson() {
//        WebClient.client(commonMetadataRestInternalFacadeClientJson).reset();
//        WebClient.client(commonMetadataRestInternalFacadeClientJson).accept(APPLICATION_JSON);
//        return commonMetadataRestInternalFacadeClientJson;
//    }
}
