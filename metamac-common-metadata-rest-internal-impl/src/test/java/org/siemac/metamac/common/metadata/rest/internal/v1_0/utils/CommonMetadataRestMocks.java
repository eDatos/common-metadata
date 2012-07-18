package org.siemac.metamac.common.metadata.rest.internal.v1_0.utils;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.siemac.metamac.common.metadata.rest.internal.RestInternalConstants;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.CommonMetadataStatus;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.service.CommonMetadataRestInternalFacadeV10Test;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.test.utils.MetamacRestMocks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourcesNoPagedResult;

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

    public static ResourcesNoPagedResult mockConfigurationsNoPagedResult(String baseApi, String query) {
        ResourcesNoPagedResult configurationsResult = new ResourcesNoPagedResult();
        configurationsResult.setKind(RestInternalConstants.KIND_CONFIGURATIONS);
        
        if (query == null) {
            // all enabled
            configurationsResult.setTotal(BigInteger.valueOf(3));
            configurationsResult.getItems().add(mockConfiguration1Resource(baseApi));
            configurationsResult.getItems().add(mockConfiguration2Resource(baseApi));
            configurationsResult.getItems().add(mockConfiguration15Resource(baseApi));
        } else {
            String querySupported1 = CommonMetadataRestInternalFacadeV10Test.QUERY_CONFIGURATION_ID_LIKE_1;
            if (querySupported1.equals(querySupported1)) {
                configurationsResult.setTotal(BigInteger.valueOf(2));
                configurationsResult.getItems().add(mockConfiguration1Resource(baseApi));
                configurationsResult.getItems().add(mockConfiguration15Resource(baseApi));
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
        configuration.setKind(RestInternalConstants.KIND_CONFIGURATION);
        configuration.setSelfLink(baseApi + "/configurations/configuration" + subCode);
        configuration.setLegalActs(mockInternationalString("legalActs", subCode));
        configuration.setDataSharing(mockInternationalString("dataSharing", subCode));
        configuration.setConfPolicy(mockInternationalString("confPolicy", subCode));
        configuration.setConfDataTreatment(mockInternationalString("confDataTreatment", subCode));
        configuration.setContact(mockResourceFromExternalItem("contact1", TypeExternalArtefactsEnum.AGENCY));
        configuration.setStatus(status);
        configuration.setParent(MetamacRestMocks.mockResourceLink(RestInternalConstants.KIND_CONFIGURATIONS, baseApi + "/configurations"));
        // no children
        return configuration;
    }

    private static Resource mockConfiguration1Resource(String baseApi) {
        return mockConfigurationResource("1", baseApi);
    }

    private static Resource mockConfiguration2Resource(String baseApi) {
        return mockConfigurationResource("2", baseApi);
    }
    
    private static Resource mockConfiguration3Resource(String baseApi) {
        return mockConfigurationResource("3", baseApi);
    }
    
    private static Resource mockConfiguration15Resource(String baseApi) {
        return mockConfigurationResource("15", baseApi);
    }
    
    private static Resource mockConfigurationResource(String subId, String baseApi) {
        String configurationId = "configuration" + subId;
        Resource resource = MetamacRestMocks.mockResource(configurationId, "urn:siemac:org.siemac.metamac.infomodel.commonmetadata.CommonMetadata=" + configurationId, RestInternalConstants.KIND_CONFIGURATION,
                baseApi + "/configurations/" + configurationId);
        resource.setTitle(null); // no title
        return resource;
    }

    private static InternationalString mockInternationalString(String metadata, String subsubTitle) {
        String subTitle = subsubTitle != null ? metadata + subsubTitle : metadata;
        return MetamacRestMocks.mockInternationalString("es", subTitle + " en Español", "en", subTitle + " in English");
    }

    private static Resource mockResourceFromExternalItem(String id, TypeExternalArtefactsEnum kind) {
        String urn = "urn:" + id;
        String selfLink = "http://" + id;
        return MetamacRestMocks.mockResource(id, urn, kind.name(), selfLink);
    }
}