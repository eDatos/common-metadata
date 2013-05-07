package org.siemac.metamac.common_metadata.rest.external.v1_0.utils;

import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.siemac.metamac.common_metadata.rest.external.RestExternalConstants;
import org.siemac.metamac.common_metadata.rest.external.v1_0.service.CommonMetadataRestExternalFacadeV10Test;
import org.siemac.metamac.rest.common.test.utils.MetamacRestMocks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.CommonMetadataStatus;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;

public class CommonMetadataRestMocks {

    public static Configuration mockConfiguration1(String baseApi) {
        return mockConfiguration(baseApi, "1", CommonMetadataStatus.ENABLED);
    }

    public static Configuration mockConfiguration2(String baseApi) {
        return mockConfiguration(baseApi, "2", CommonMetadataStatus.ENABLED);
    }

    public static Configuration mockConfiguration3(String baseApi) {
        return mockConfiguration(baseApi, "3", CommonMetadataStatus.DISABLED);
    }

    public static Configuration mockConfiguration15(String baseApi) {
        return mockConfiguration(baseApi, "15", CommonMetadataStatus.ENABLED);
    }

    public static Configurations mockConfigurations(String baseApi, String baseWebApplication, String query) {
        Configurations configurationsResult = new Configurations();
        configurationsResult.setKind(RestExternalConstants.KIND_CONFIGURATIONS);

        if (query == null) {
            // all enabled
            configurationsResult.setTotal(BigInteger.valueOf(3));
            configurationsResult.getConfigurations().add(mockConfiguration1Resource(baseApi, baseWebApplication));
            configurationsResult.getConfigurations().add(mockConfiguration2Resource(baseApi, baseWebApplication));
            configurationsResult.getConfigurations().add(mockConfiguration15Resource(baseApi, baseWebApplication));
        } else {
            String querySupported1 = CommonMetadataRestExternalFacadeV10Test.QUERY_CONFIGURATION_ID_LIKE_1;
            if (querySupported1.equals(querySupported1)) {
                configurationsResult.setTotal(BigInteger.valueOf(2));
                configurationsResult.getConfigurations().add(mockConfiguration1Resource(baseApi, baseWebApplication));
                configurationsResult.getConfigurations().add(mockConfiguration15Resource(baseApi, baseWebApplication));
            } else {
                fail("Query not supported = " + query);
            }
        }
        return configurationsResult;
    }

    private static Configuration mockConfiguration(String baseApi, String subCode, CommonMetadataStatus status) {

        Configuration configuration = new Configuration();
        configuration.setId("configuration" + subCode);
        configuration.setUrn("urn:siemac:org.siemac.metamac.infomodel.commonmetadata.CommonMetadata=" + configuration.getId());
        configuration.setKind(RestExternalConstants.KIND_CONFIGURATION);
        configuration.setSelfLink(MetamacRestMocks.mockResourceLink(RestExternalConstants.KIND_CONFIGURATION, baseApi + "/configurations/configuration" + subCode));
        configuration.setLegalActs(mockInternationalString("legalActs", subCode));
        configuration.setDataSharing(mockInternationalString("dataSharing", subCode));
        configuration.setConfPolicy(mockInternationalString("confPolicy", subCode));
        configuration.setConfDataTreatment(mockInternationalString("confDataTreatment", subCode));
        configuration.setContact(mockOrganisationResourceFromExternalItemSrm("contact1", "contacts", "structuralResources#agency"));
        configuration.setStatus(status);
        configuration.setParentLink(MetamacRestMocks.mockResourceLink(RestExternalConstants.KIND_CONFIGURATIONS, baseApi + "/configurations"));
        // no children
        return configuration;
    }

    private static ResourceInternal mockConfiguration1Resource(String baseApi, String baseWebApplication) {
        return mockConfigurationResource("1", baseApi, baseWebApplication);
    }

    private static ResourceInternal mockConfiguration2Resource(String baseApi, String baseWebApplication) {
        return mockConfigurationResource("2", baseApi, baseWebApplication);
    }

    private static ResourceInternal mockConfiguration15Resource(String baseApi, String baseWebApplication) {
        return mockConfigurationResource("15", baseApi, baseWebApplication);
    }

    private static ResourceInternal mockConfigurationResource(String subId, String baseApi, String baseWebApplication) {
        String configurationId = "configuration" + subId;
        ResourceInternal resource = new ResourceInternal();
        resource.setId(configurationId);
        resource.setUrn("urn:siemac:org.siemac.metamac.infomodel.commonmetadata.CommonMetadata=" + configurationId);
        resource.setKind(RestExternalConstants.KIND_CONFIGURATION);
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(RestExternalConstants.KIND_CONFIGURATION, baseApi + "/configurations/" + configurationId));
        resource.setManagementAppLink(baseWebApplication + "/#configurations/configuration;id=" + configurationId);
        resource.setTitle(null); // no title
        return resource;
    }

    private static InternationalString mockInternationalString(String metadata, String subsubTitle) {
        String subTitle = subsubTitle != null ? metadata + subsubTitle : metadata;
        return MetamacRestMocks.mockInternationalString("es", subTitle + " en Español", "en", subTitle + " in English");
    }

    private static ResourceInternal mockOrganisationResourceFromExternalItemSrm(String id, String apiSubpath, String kind) {
        ResourceInternal resource = new ResourceInternal();
        resource.setId(id);
        resource.setUrn("urn:sdmx:org.sdmx.infomodel.base.Agency=SDMX:AGENCIES(1.0)." + id);
        resource.setKind(kind);
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(kind, "http://data.istac.es/apis/srm/v1.0" + "/" + apiSubpath + "/" + id));
        resource.setManagementAppLink("http://localhost:8080/metamac-srm-web/#structuralResources/organisationSchemes/organisationScheme;type=AGENCY_SCHEME;id=SDMX:AGENCIES(1.0)/organisation;id="
                + id);
        resource.setTitle(MetamacRestMocks.mockInternationalString("es", id + " en Español", "en", id + " in English"));
        return resource;
    }
}