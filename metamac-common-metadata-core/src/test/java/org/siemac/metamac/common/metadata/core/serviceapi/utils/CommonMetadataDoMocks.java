package org.siemac.metamac.common.metadata.core.serviceapi.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.DataConfiguration;
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
    // DATA CONFIGURATIONS
    // -----------------------------------------------------------------

    public static DataConfiguration mockDataConfigurationOfSystemProperty() {
        DataConfiguration dataConfiguration = mockDataConfiguration();
        dataConfiguration.setSystemProperty(Boolean.TRUE);
        return dataConfiguration;
    }

    public static DataConfiguration mockDataConfigurationOfDefaultValue() {
        DataConfiguration dataConfiguration = mockDataConfiguration();
        dataConfiguration.setSystemProperty(Boolean.FALSE);
        return dataConfiguration;
    }

    private static DataConfiguration mockDataConfiguration() {
        DataConfiguration dataConfiguration = new DataConfiguration();
        dataConfiguration.setConfigurationKey("data_configuration-" + RandomStringUtils.randomAlphabetic(5));
        dataConfiguration.setConfigurationValue(RandomStringUtils.randomAlphabetic(5));
        return dataConfiguration;
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

    /**
     * Mock an InternationalString with two locales
     */
    public static InternationalString mockInternationalString(String locale01, String label01, String locale02, String label02) {
        InternationalString target = new InternationalString();
        LocalisedString localisedString01 = new LocalisedString();
        localisedString01.setLocale(locale01);
        localisedString01.setLabel(label01);
        target.addText(localisedString01);

        LocalisedString localisedString02 = new LocalisedString();
        localisedString02.setLocale(locale02);
        localisedString02.setLabel(label02);
        target.addText(localisedString02);
        return target;
    }

    // -----------------------------------------------------------------
    // EXTERNAL ITEM
    // -----------------------------------------------------------------

    public static ExternalItem mockStatisticalOperationItem() {
        String code = mockCode();
        return mockStatisticalOperationItem(code);
    }

    public static ExternalItem mockStatisticalOperationItem(String code) {
        return mockStatisticalOperationAppExternalItem(code, mockStatisticalOperationUrn(code), TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
    }

    public static ExternalItem mockStatisticalOperationInstanceItem() {
        String code = mockCode();
        return mockStatisticalOperationAppExternalItem(code, mockStatisticalOperationInstanceUrn(code), TypeExternalArtefactsEnum.STATISTICAL_OPERATION_INSTANCE);
    }

    public static ExternalItem mockAgencyExternalItem() {
        String code = mockCode();
        ExternalItem target = mockSrmAppExternalItem(code, mockAgencyUrn(code), TypeExternalArtefactsEnum.AGENCY);
        target.setCodeNested(code + "nested");
        return target;
    }

    public static ExternalItem mockOrganizationUnitExternalItem() {
        String code = mockCode();
        return mockSrmAppExternalItem(code, mockOrganizationUnitUrn(code), TypeExternalArtefactsEnum.ORGANISATION_UNIT);
    }

    public static ExternalItem mockConceptExternalItem() {
        String code = mockCode();
        return mockSrmAppExternalItem(code, mockConceptUrn(code), TypeExternalArtefactsEnum.CONCEPT);
    }

    public static ExternalItem mockConceptSchemeExternalItem() {
        String code = mockCode();
        return mockSrmAppExternalItem(code, mockConceptSchemeUrn(code), TypeExternalArtefactsEnum.CONCEPT_SCHEME);
    }

    public static ExternalItem mockCodeListSchemeExternalItem() {
        String code = mockCode();
        return mockSrmAppExternalItem(code, mockCodeListUrn(code), TypeExternalArtefactsEnum.CODELIST);
    }

    public static ExternalItem mockCodeExternalItem() {
        String code = mockCode();
        return mockSrmAppExternalItem(code, mockCodeUrn(code), TypeExternalArtefactsEnum.CODE);
    }

    public static ExternalItem mockDsdExternalItem() {
        String code = mockCode();
        return mockSrmAppExternalItem(code, mockDsdUrn(code), TypeExternalArtefactsEnum.DATASTRUCTURE);
    }

    public static ExternalItem mockDimensionExternalItem() {
        String code = mockCode();
        return mockSrmAppExternalItem(code, mockDimensionUrn(code), TypeExternalArtefactsEnum.DIMENSION);
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

    private static ExternalItem mockStatisticalOperationAppExternalItem(String code, String urn, TypeExternalArtefactsEnum type) {
        return mockExternalItemCommon(code, urn, type);
    }

    private static ExternalItem mockSrmAppExternalItem(String code, String urn, TypeExternalArtefactsEnum type) {
        return mockExternalItemCommon(code, urn, type);
    }
}
