package org.siemac.metamac.common_metadata.rest.external.v1_0.utils;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common_metadata.rest.external.v1_0.service.CommonMetadataRestExternalFacadeV10Test;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;

public class CommonMetadataCoreMocks {

    public CommonMetadataCoreMocks() {
    }

    public Configuration mockConfiguration1() {
        return mockConfiguration("1", CommonMetadataStatusEnum.ENABLED);
    }

    public Configuration mockConfiguration2() {
        return mockConfiguration("2", CommonMetadataStatusEnum.ENABLED);
    }

    public Configuration mockConfiguration3() {
        return mockConfiguration("3", CommonMetadataStatusEnum.DISABLED);
    }

    public Configuration mockConfiguration15() {
        return mockConfiguration("15", CommonMetadataStatusEnum.ENABLED);
    }

    public List<Configuration> mockConfigurationsNoPagedResult(String query) {

        List<Configuration> configurations = new ArrayList<Configuration>();
        if (query == null) {
            configurations.add(mockConfiguration1());
            configurations.add(mockConfiguration2());
            configurations.add(mockConfiguration15());
        } else {
            String querySupported1 = CommonMetadataRestExternalFacadeV10Test.QUERY_CONFIGURATION_ID_LIKE_1;
            if (querySupported1.equals(querySupported1)) {
                configurations.add(mockConfiguration1());
                configurations.add(mockConfiguration15());
            } else {
                fail("Query not supported = " + query);
            }
        }
        return configurations;
    }

    private Configuration mockConfiguration(String subCode, CommonMetadataStatusEnum status) {

        Configuration configuration = new Configuration();
        configuration.setCode("configuration" + subCode);
        configuration.setUrn(GeneratorUrnUtils.generateSiemacCommonMetadataUrn(configuration.getCode()));
        configuration.setDataSharing(mockInternationalString("dataSharing", subCode));
        configuration.setLegalActs(mockInternationalString("legalActs", subCode));
        configuration.setConfPolicy(mockInternationalString("confPolicy", subCode));
        configuration.setConfDataTreatment(mockInternationalString("confDataTreatment", subCode));
        configuration.setStatus(status);
        configuration.setContact(mockExternalItemOrganisationSrm("contact1"));
        return configuration;
    }

    private ExternalItem mockExternalItemOrganisationSrm(String code) {
        String uri = "v1.0/agencyschemes/SDMX/AGENCIES/1.0/agencies/" + code;
        String urn = "urn:sdmx:org.sdmx.infomodel.base.Agency=SDMX:AGENCIES(1.0)." + code;
        String managementUrlPart = "#structuralResources/organisationSchemes/organisationScheme;type=AGENCY_SCHEME;id=SDMX:AGENCIES(1.0)/organisation;id=" + code;
        return new ExternalItem(code, uri, urn, TypeExternalArtefactsEnum.AGENCY, mockInternationalString(code, null), managementUrlPart);
    }

    private InternationalString mockInternationalString(String metadata, String subCode) {
        String subTitle = subCode != null ? metadata + subCode : metadata;
        return mockInternationalString("es", subTitle + " en Español", "en", subTitle + " in English");
    }

    private InternationalString mockInternationalString(String locale1, String label1, String locale2, String label2) {

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