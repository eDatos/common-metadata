package org.siemac.metamac.common.metadata.rest.internal.v1_0.utils;

import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.rest.internal.RestInternalConstants;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.test.utils.MetamacRestMocks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;

public class CommonMetadataRestMocks {

    public static Configuration mockConfiguration1(String baseApi) {
        return mockConfiguration(baseApi, "1", CommonMetadataStatusEnum.ENABLED);
    }

    public static Configuration mockConfiguration2(String baseApi) {
        return mockConfiguration(baseApi, "2", CommonMetadataStatusEnum.ENABLED);
    }

    private static Configuration mockConfiguration(String baseApi, String subCode, CommonMetadataStatusEnum status) {

        Configuration configuration = new Configuration();
        configuration.setId("configuration" + subCode);
        configuration.setKind(RestInternalConstants.KIND_CONFIGURATION);
        configuration.setSelfLink(baseApi + "/configurations/configuration" + subCode);
        configuration.setLegalActs(mockInternationalString("LegalActs", subCode));
        configuration.setDataSharing(mockInternationalString("DataSharing", subCode));
        configuration.setLegalActs(mockInternationalString("LegalActs", subCode));
        configuration.setConfPolicy(mockInternationalString("ConfPolicy", subCode));
        configuration.setConfDataTreatment(mockInternationalString("ConfDataTreatment", subCode));
        configuration.setContact(MetamacRestMocks.mockResource("contact" + subCode, TypeExternalArtefactsEnum.AGENCY.name(), "/contacts/contact1", Boolean.TRUE, "contact"));
        configuration.setStatus(status.name());
        configuration.setParent(MetamacRestMocks.mockResource(null, RestInternalConstants.KIND_CONFIGURATIONS, baseApi + "/configurations", Boolean.FALSE, null));
        // no children
        return configuration;
    }

    private static InternationalString mockInternationalString(String metadata, String subCode) {
        String subTitle = metadata + " " + subCode;
        return MetamacRestMocks.mockInternationalString("es", subTitle + " en español", "en", subTitle + " in English");
    }
}