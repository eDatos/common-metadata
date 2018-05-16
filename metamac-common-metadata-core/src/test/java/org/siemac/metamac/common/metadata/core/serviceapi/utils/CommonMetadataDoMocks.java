package org.siemac.metamac.common.metadata.core.serviceapi.utils;

import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.constants.CoreCommonConstants;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;

/**
 * Mocks
 */
public class CommonMetadataDoMocks extends MetamacMocks {

    // -----------------------------------------------------------------
    // CONFIGURATIONS (metadata)
    // -----------------------------------------------------------------

    public static Configuration createEnableConfiguration() {
        Configuration configuration = createConfigurationBase();
        configuration.setStatus(CommonMetadataStatusEnum.ENABLED);
        return configuration;
    }

    public static Configuration createConfigurationBase() {
        Configuration configuration = new Configuration();
        configuration.setCode(CommonMetadataDoMocks.mockCode());
        configuration.setStatus(CommonMetadataStatusEnum.DISABLED);
        configuration.setContact(mockAgencyExternalItem());
        configuration.setLegalActs(mockInternationalString());
        configuration.setDataSharing(mockInternationalString());
        configuration.setConfPolicy(mockInternationalString());
        configuration.setConfDataTreatment(mockInternationalString());
        configuration.setLicense(mockInternationalString());
        return configuration;
    }

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING
    // -----------------------------------------------------------------

    public static InternationalString mockInternationalString() {
        InternationalString internationalString = new InternationalString();
        LocalisedString es = new LocalisedString();
        es.setLabel(mockString(10) + " en Espanol");
        es.setLocale("es");
        es.setVersion(Long.valueOf(0));
        LocalisedString en = new LocalisedString();
        en.setLabel(mockString(10) + " in English");
        en.setLocale("en");
        en.setVersion(Long.valueOf(0));
        internationalString.addText(es);
        internationalString.addText(en);
        internationalString.setVersion(Long.valueOf(0));
        return internationalString;
    }

    /**
     * Mock an InternationalString with one locale
     */
    public static InternationalString mockInternationalString(String locale, String label) {
        InternationalString target = new InternationalString();
        LocalisedString localisedString = new LocalisedString();
        localisedString.setLocale(locale);
        localisedString.setLabel(label);
        target.addText(localisedString);
        return target;
    }


    // -----------------------------------------------------------------
    // EXTERNAL ITEM
    // -----------------------------------------------------------------

    public static ExternalItem mockAgencyExternalItem() {
        String code = mockCode();
        ExternalItem target = mockSrmAppExternalItem(code, mockAgencyUrn(code), TypeExternalArtefactsEnum.AGENCY);
        target.setCodeNested(code + "nested");
        return target;
    }


    // -----------------------------------------------------------------
    // PRIVATE
    // -----------------------------------------------------------------

    private static ExternalItem mockExternalItemCommon(String code, String urn, TypeExternalArtefactsEnum type) {
        ExternalItem item = new ExternalItem();
        item.setCode(code);
        item.setCodeNested(code + "nested");
        item.setUri(CoreCommonConstants.API_LATEST_WITH_SLASHES + code);
        item.setUrn(urn);
        item.setUrnProvider(urn + "provider");
        item.setType(type);
        item.setTitle(mockInternationalString());
        item.setManagementAppUrl(CoreCommonConstants.URL_SEPARATOR + code);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    private static ExternalItem mockSrmAppExternalItem(String code, String urn, TypeExternalArtefactsEnum type) {
        return mockExternalItemCommon(code, urn, type);
    }
}
