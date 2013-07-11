package org.siemac.metamac.common_metadata.rest.external.v1_0.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.siemac.metamac.common_metadata.rest.external.RestExternalConstants;
import org.siemac.metamac.common_metadata.rest.external.v1_0.service.CommonMetadataRestExternalFacadeV10Test;
import org.siemac.metamac.rest.common.test.utils.MetamacRestMocks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.CommonMetadataStatus;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal;

public class CommonMetadataRestMocks {

    private final String commonMetadataExternalApiBaseV10;
    private final String commonMetadataInternalWebApplicationBase;
    private final String srmApiExternalEndpoint;
    private final String srmInternalWebApplicationBase;

    public CommonMetadataRestMocks(String commonMetadataExternalApiBaseV10, String commonMetadataInternalWebApplicationBase, String srmApiExternalEndpoint, String srmInternalWebApplicationBase) {
        assertNotNull(commonMetadataExternalApiBaseV10);
        assertNotNull(commonMetadataInternalWebApplicationBase);
        assertNotNull(srmApiExternalEndpoint);
        assertNotNull(srmInternalWebApplicationBase);

        this.commonMetadataExternalApiBaseV10 = commonMetadataExternalApiBaseV10;
        this.commonMetadataInternalWebApplicationBase = commonMetadataInternalWebApplicationBase;
        this.srmApiExternalEndpoint = srmApiExternalEndpoint;
        this.srmInternalWebApplicationBase = srmInternalWebApplicationBase;
    }

    public Configuration mockConfiguration1() {
        return mockConfiguration("1", CommonMetadataStatus.ENABLED);
    }

    public Configuration mockConfiguration2() {
        return mockConfiguration("2", CommonMetadataStatus.ENABLED);
    }

    public Configuration mockConfiguration3() {
        return mockConfiguration("3", CommonMetadataStatus.DISABLED);
    }

    public Configuration mockConfiguration15() {
        return mockConfiguration("15", CommonMetadataStatus.ENABLED);
    }

    public Configurations mockConfigurations(String query) {
        Configurations configurationsResult = new Configurations();
        configurationsResult.setKind(RestExternalConstants.KIND_CONFIGURATIONS);

        if (query == null) {
            // all enabled
            configurationsResult.setTotal(BigInteger.valueOf(3));
            configurationsResult.getConfigurations().add(mockConfiguration1Resource());
            configurationsResult.getConfigurations().add(mockConfiguration2Resource());
            configurationsResult.getConfigurations().add(mockConfiguration15Resource());
        } else {
            String querySupported1 = CommonMetadataRestExternalFacadeV10Test.QUERY_CONFIGURATION_ID_LIKE_1;
            if (querySupported1.equals(querySupported1)) {
                configurationsResult.setTotal(BigInteger.valueOf(2));
                configurationsResult.getConfigurations().add(mockConfiguration1Resource());
                configurationsResult.getConfigurations().add(mockConfiguration15Resource());
            } else {
                fail("Query not supported = " + query);
            }
        }
        return configurationsResult;
    }

    private Configuration mockConfiguration(String subCode, CommonMetadataStatus status) {
        Configuration configuration = new Configuration();
        configuration.setId("configuration" + subCode);
        configuration.setUrn("urn:siemac:org.siemac.metamac.infomodel.commonmetadata.CommonMetadata=" + configuration.getId());
        configuration.setKind(RestExternalConstants.KIND_CONFIGURATION);
        configuration.setSelfLink(MetamacRestMocks.mockResourceLink(RestExternalConstants.KIND_CONFIGURATION, commonMetadataExternalApiBaseV10 + "/configurations/configuration" + subCode));
        configuration.setLegalActs(mockInternationalString("legalActs", subCode));
        configuration.setDataSharing(mockInternationalString("dataSharing", subCode));
        configuration.setConfPolicy(mockInternationalString("confPolicy", subCode));
        configuration.setConfDataTreatment(mockInternationalString("confDataTreatment", subCode));
        configuration.setContact(mockOrganisationResourceFromExternalItemSrm("contact1"));
        configuration.setStatus(status);
        configuration.setParentLink(MetamacRestMocks.mockResourceLink(RestExternalConstants.KIND_CONFIGURATIONS, commonMetadataExternalApiBaseV10 + "/configurations"));
        configuration.setManagementAppLink(commonMetadataInternalWebApplicationBase + "/#configurations/configuration;id=" + configuration.getId());
        // no children
        return configuration;
    }

    private ResourceInternal mockConfiguration1Resource() {
        return mockConfigurationResource("1");
    }

    private ResourceInternal mockConfiguration2Resource() {
        return mockConfigurationResource("2");
    }

    private ResourceInternal mockConfiguration15Resource() {
        return mockConfigurationResource("15");
    }

    private ResourceInternal mockConfigurationResource(String subId) {
        String configurationId = "configuration" + subId;
        ResourceInternal resource = new ResourceInternal();
        resource.setId(configurationId);
        resource.setUrn("urn:siemac:org.siemac.metamac.infomodel.commonmetadata.CommonMetadata=" + configurationId);
        resource.setKind(RestExternalConstants.KIND_CONFIGURATION);
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(RestExternalConstants.KIND_CONFIGURATION, commonMetadataExternalApiBaseV10 + "/configurations/" + configurationId));
        resource.setManagementAppLink(commonMetadataInternalWebApplicationBase + "/#configurations/configuration;id=" + configurationId);
        resource.setName(null); // no title
        return resource;
    }

    private InternationalString mockInternationalString(String metadata, String subsubTitle) {
        String subTitle = subsubTitle != null ? metadata + subsubTitle : metadata;
        return MetamacRestMocks.mockInternationalString("es", subTitle + " en Español", "en", subTitle + " in English");
    }

    private ResourceInternal mockOrganisationResourceFromExternalItemSrm(String id) {
        ResourceInternal resource = new ResourceInternal();
        resource.setId(id);
        resource.setNestedId(id + "Nested");
        resource.setUrn("urn:sdmx:org.sdmx.infomodel.base.Agency=SDMX:AGENCIES(1.0)." + id);
        resource.setKind("structuralResources#agency");
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(resource.getKind(), srmApiExternalEndpoint + "/v1.0/agencyschemes/SDMX/AGENCIES/1.0/agencies/" + id));
        resource.setManagementAppLink(srmInternalWebApplicationBase + "/#structuralResources/organisationSchemes/organisationScheme;type=AGENCY_SCHEME;id=SDMX:AGENCIES(1.0)/organisation;id=" + id);
        resource.setName(MetamacRestMocks.mockInternationalString("es", id + " en Español", "en", id + " in English"));
        return resource;
    }
}