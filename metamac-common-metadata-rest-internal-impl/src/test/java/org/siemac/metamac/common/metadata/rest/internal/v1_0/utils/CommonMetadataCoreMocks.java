package org.siemac.metamac.common.metadata.rest.internal.v1_0.utils;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.service.CommonMetadataRestInternalFacadeV10Test;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;

public class CommonMetadataCoreMocks {

    public static Configuration mockConfiguration1() {
        return mockConfiguration("1", CommonMetadataStatusEnum.ENABLED);
    }

    public static Configuration mockConfiguration2() {
        return mockConfiguration("2", CommonMetadataStatusEnum.ENABLED);
    }
    
    public static Configuration mockConfiguration3() {
        return mockConfiguration("3", CommonMetadataStatusEnum.DISABLED);
    }
    
    public static Configuration mockConfiguration15() {
        return mockConfiguration("15", CommonMetadataStatusEnum.ENABLED);
    }

    public static List<Configuration> mockConfigurationsNoPagedResult(String query) {

        List<Configuration> configurations = new ArrayList<Configuration>();
        if (query == null) {
            configurations.add(mockConfiguration1());
            configurations.add(mockConfiguration2());
            configurations.add(mockConfiguration15());
        } else {
            String querySupported1 = CommonMetadataRestInternalFacadeV10Test.QUERY_CONFIGURATION_ID_LIKE_1;
            if (querySupported1.equals(querySupported1)) {
                configurations.add(mockConfiguration1());
                configurations.add(mockConfiguration15());
            } else {
                fail("Query not supported = " + query);
            }
        }
        return configurations;
    }
    
    private static Configuration mockConfiguration(String subCode, CommonMetadataStatusEnum status) {

        Configuration configuration = new Configuration();
        configuration.setCode("configuration" + subCode);
        configuration.setUrn(GeneratorUrnUtils.generateSiemacCommonMetadataUrn(configuration.getCode()));
        configuration.setDataSharing(mockInternationalString("dataSharing", subCode));
        configuration.setLegalActs(mockInternationalString("legalActs", subCode));
        configuration.setConfPolicy(mockInternationalString("confPolicy", subCode));
        configuration.setConfDataTreatment(mockInternationalString("confDataTreatment", subCode));
        configuration.setStatus(status);
        configuration.setContact(mockExternalItem("contact1", TypeExternalArtefactsEnum.AGENCY));
        return configuration;
    }

    private static ExternalItem mockExternalItem(String code, TypeExternalArtefactsEnum type) {
        String uri = "http://" + code;
        String urn = "urn:" + code;
        return new ExternalItem(code, uri, urn, type, mockInternationalString(code, null), null);
    }

    private static InternationalString mockInternationalString(String metadata, String subCode) {
        String subTitle = subCode != null ? metadata + subCode : metadata;
        return mockInternationalString("es", subTitle + " en Español", "en", subTitle + " in English");
    }
    
    private static InternationalString mockInternationalString(String locale1, String label1, String locale2, String label2) {

        InternationalString internationalString = new InternationalString();

        LocalisedString internationalStringLocale1 = new LocalisedString();
        internationalStringLocale1.setLocale(locale1);
        internationalStringLocale1.setLabel(label1);
        internationalString.addText(internationalStringLocale1);

        LocalisedString internationalStringLocale2 = new LocalisedString();
        internationalStringLocale2.setLocale(locale2);
        internationalStringLocale2.setLabel(label2);
        internationalString.addText(internationalStringLocale2);

        return internationalString;
    }
}