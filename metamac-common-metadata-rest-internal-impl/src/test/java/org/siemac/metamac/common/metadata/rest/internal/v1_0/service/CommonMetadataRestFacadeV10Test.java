package org.siemac.metamac.common.metadata.rest.internal.v1_0.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataService;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.mockito.FindConfigurationsByCodeMatcher;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.utils.CommonMetadataCoreMocks;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.utils.CommonMetadataRestAsserts;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.utils.CommonMetadataRestMocks;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.common.test.MetamacRestBaseTest;
import org.siemac.metamac.rest.common.test.ServerResource;
import org.siemac.metamac.rest.common.test.utils.MetamacRestAsserts;
import org.springframework.context.ApplicationContext;

public class CommonMetadataRestFacadeV10Test extends MetamacRestBaseTest {

    private static final String                PORT                = ServerResource.PORT;
    private static String                      baseApi             = "http://localhost:" + PORT + "/internal/v1.0";

    private static CommonMetadataRestFacadeV10 commonMetadataRestFacadeClientXml;
    private static CommonMetadataRestFacadeV10 commonMetadataRestFacadeClientJson;

    private static ApplicationContext          applicationContext  = null;

    private static String                      NOT_EXISTS          = "NOT_EXISTS";

    // Configurations
    public static String                       CONFIGURATION_CODE1 = "configuration1";
    public static String                       CONFIGURATION_CODE2 = "configuration2";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        // Start server
        assertTrue("server did not launch correctly", launchServer(ServerResource.class, true));

        // Get application context from Jetty
        applicationContext = ApplicationContextProvider.getApplicationContext();

        // Rest clients
        // xml
        commonMetadataRestFacadeClientXml = JAXRSClientFactory.create(baseApi, CommonMetadataRestFacadeV10.class);
        WebClient.client(commonMetadataRestFacadeClientXml).accept(APPLICATION_XML);
        // json
        commonMetadataRestFacadeClientJson = JAXRSClientFactory.create(baseApi, CommonMetadataRestFacadeV10.class);
        WebClient.client(commonMetadataRestFacadeClientJson).accept(APPLICATION_JSON);

    }

    @Before
    public void setUp() throws Exception {
        setUpMockito();
    }

    @Test
    public void testWithoutMatchError404() throws Exception {

        String requestUri = baseApi + "/nomatch";

        // Request and validate
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.NOT_FOUND, new ByteArrayInputStream(new byte[0]));
    }

    @Test
    public void testRetrieveConfigurationByCodeXml() throws Exception {

        // Retrieve
        Configuration configuration = commonMetadataRestFacadeClientXml.retrieveConfigurationByCode(CONFIGURATION_CODE1);

        // Validation
        CommonMetadataRestAsserts.assertEqualsConfiguration(CommonMetadataRestMocks.mockConfiguration1(baseApi), configuration);
    }

    @Ignore
    @Test
    public void testRetrieveConfigurationByCodeJson() throws Exception {
        // NOTE: Throws exception. We dont support calls with jaxb transformation when media type is Json. @see METAMAC-675
        // Configuration configuration = commonMetadataRestFacadeClientJson.retrieveConfigurationByCode(CONFIGURATION_CODE1);
        // CommonMetadataRestAsserts.assertEqualsConfiguration(CommonMetadataRestMocks.mockConfiguration1(baseApi), configuration);
    }

    // TODO pendientes tests!
//    @Test
//    public void testRetrieveConfigurationByCodeXmlWithoutJaxbTransformation() throws Exception {
//
//        String requestUri = getRequestUriRetrieveConfigurationByCode(CONFIGURATION_CODE1);
//        InputStream responseExpected = CommonMetadataRestFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationByCode.code1.xml");
//
//        // Request and validate
//        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
//    }
//
//    @Test
//    public void testRetrieveConfigurationByCodeJsonWithoutJaxbTransformation() throws Exception {
//
//        String requestUri = getRequestUriRetrieveConfigurationByCode(CONFIGURATION_CODE1);
//        InputStream responseExpected = CommonMetadataRestFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationByCode.code1.json");
//
//        // Request and validate
//        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.OK, responseExpected);
//    }
//
//    @Test
//    public void testRetrieveConfigurationByCodeErrorNotExistsXml() throws Exception {
//        try {
//            commonMetadataRestFacadeClientXml.retrieveConfigurationByCode(NOT_EXISTS);
//        } catch (Exception e) {
//            InputStream responseExpected = CommonMetadataRestFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationByCode.notFound.xml");
//            InputStream responseActual = (InputStream) ((ServerWebApplicationException) e).getResponse().getEntity();
//            MetamacRestAsserts.assertEqualsResponse(responseExpected, responseActual);
//        }
//    }
//
//    @Test
//    public void testRetrieveConfigurationByCodeErrorNotExistsJson() throws Exception {
//
//        try {
//            commonMetadataRestFacadeClientJson.retrieveConfigurationByCode(NOT_EXISTS);
//        } catch (Exception e) {
//            InputStream responseExpected = CommonMetadataRestFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationByCode.notFound.json");
//            InputStream responseActual = (InputStream) ((ServerWebApplicationException) e).getResponse().getEntity();
//            MetamacRestAsserts.assertEqualsResponse(responseExpected, responseActual);
//        }
//    }
//
//    @Test
//    public void testRetrieveConfigurationByCodeErrorNotExistsXmlWithoutJaxbTransformation() throws Exception {
//        String requestUri = getRequestUriRetrieveConfigurationByCode(NOT_EXISTS);
//        InputStream responseExpected = CommonMetadataRestFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationByCode.notFound.xml");
//
//        // Request and validate
//        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.NOT_FOUND, responseExpected);
//    }
//
//    @Test
//    public void testRetrieveConfigurationByCodeErrorNotExistsJsonWithoutJaxbTransformation() throws Exception {
//        String requestUri = getRequestUriRetrieveConfigurationByCode(NOT_EXISTS);
//        InputStream responseExpected = CommonMetadataRestFacadeV10Test.class.getResourceAsStream("/responses/retrieveConfigurationByCode.notFound.json");
//
//        // Request and validate
//        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.NOT_FOUND, responseExpected);
//    }

    private String getRequestUriRetrieveConfigurationByCode(String code) {
        return baseApi + "/configurations/" + code;
    }

    private void setUpMockito() throws MetamacException {
        // MOCKS
        CommonMetadataService commonMetadata = applicationContext.getBean(CommonMetadataService.class);
        // Retrieve configurations
        mockitoFindConfigurationByCode(commonMetadata, CONFIGURATION_CODE1);
        mockitoFindConfigurationByCode(commonMetadata, CONFIGURATION_CODE2);
    }

    private void mockitoFindConfigurationByCode(CommonMetadataService commonMetadata, String code) throws MetamacException {
        List<org.siemac.metamac.common.metadata.core.domain.Configuration> configurationEntities = new ArrayList<org.siemac.metamac.common.metadata.core.domain.Configuration>();
        if (CONFIGURATION_CODE1.equals(code)) {
            configurationEntities.add(CommonMetadataCoreMocks.mockConfiguration1());
        } else if (CONFIGURATION_CODE2.equals(code)) {
            configurationEntities.add(CommonMetadataCoreMocks.mockConfiguration2());
        }
        // Mock
        when(commonMetadata.findConfigurationByCondition(any(ServiceContext.class), argThat(new FindConfigurationsByCodeMatcher(code)))).thenReturn(configurationEntities);
    }
}
