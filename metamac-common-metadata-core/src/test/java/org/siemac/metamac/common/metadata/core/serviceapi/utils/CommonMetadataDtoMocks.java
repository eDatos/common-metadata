package org.siemac.metamac.common.metadata.core.serviceapi.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;

/**
 * Mocks
 */
public class CommonMetadataDtoMocks extends MetamacMocks {

    private static final String CODE_01 = "mock01";
    private static final String URN_01  = "lorem:ipsum:externalItem:mock01:01";

    // --------------------------------------------------------------------------------
    // CONFIGURATIONS (metadata)
    // --------------------------------------------------------------------------------

    public static ConfigurationDto mockEnableConfigurationDto() {
        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setCode("configuration-" + RandomStringUtils.randomAlphabetic(5));

        // Legal Acts
        InternationalStringDto legalActs = new InternationalStringDto();
        LocalisedStringDto legalActs_es = new LocalisedStringDto();
        legalActs_es.setLabel("ESPAÑOL Legal Acts");
        legalActs_es.setLocale("es");
        LocalisedStringDto legalActs_en = new LocalisedStringDto();
        legalActs_en.setLabel("ENGLISH Legal Acts");
        legalActs_en.setLocale("en");
        legalActs.addText(legalActs_es);
        legalActs.addText(legalActs_en);
        configurationDto.setLegalActs(legalActs);

        // Data Sharing
        InternationalStringDto dataSharing = new InternationalStringDto();
        LocalisedStringDto dataSharing_es = new LocalisedStringDto();
        dataSharing_es.setLabel("ESPAÑOL Data Sharing");
        dataSharing_es.setLocale("es");
        LocalisedStringDto dataSharing_en = new LocalisedStringDto();
        dataSharing_en.setLabel("ENGLISH Data Sharing");
        dataSharing_en.setLocale("en");
        dataSharing.addText(dataSharing_es);
        dataSharing.addText(dataSharing_en);
        configurationDto.setDataSharing(dataSharing);

        // Confidentiality Policy
        InternationalStringDto confidentialityPolicy = new InternationalStringDto();
        LocalisedStringDto confidentialityPolicy_es = new LocalisedStringDto();
        confidentialityPolicy_es.setLabel("ESPAÑOL Confidentiality Policy");
        confidentialityPolicy_es.setLocale("es");
        LocalisedStringDto confidentialityPolicy_en = new LocalisedStringDto();
        confidentialityPolicy_en.setLabel("ENGLISH Confidentiality Policy");
        confidentialityPolicy_en.setLocale("en");
        confidentialityPolicy.addText(confidentialityPolicy_es);
        confidentialityPolicy.addText(confidentialityPolicy_en);
        configurationDto.setConfPolicy(confidentialityPolicy);

        // Confidentiality Data Treatment
        InternationalStringDto confidentialityDataTreatment = new InternationalStringDto();
        LocalisedStringDto confidentialityDataTreatment_es = new LocalisedStringDto();
        confidentialityDataTreatment_es.setLabel("ESPAÑOL Confidentiality Data Treatment");
        confidentialityDataTreatment_es.setLocale("es");
        LocalisedStringDto confidentialityDataTreatment_en = new LocalisedStringDto();
        confidentialityDataTreatment_en.setLabel("ENGLISH Confidentiality Data Treatment");
        confidentialityDataTreatment_en.setLocale("en");
        confidentialityDataTreatment.addText(confidentialityDataTreatment_es);
        confidentialityDataTreatment.addText(confidentialityDataTreatment_en);
        configurationDto.setConfDataTreatment(confidentialityDataTreatment);

        // License
        InternationalStringDto license = new InternationalStringDto();
        LocalisedStringDto license_es = new LocalisedStringDto();
        license_es.setLabel("ESPAÑOL Licencia");
        license_es.setLocale("es");
        LocalisedStringDto license_en = new LocalisedStringDto();
        license_en.setLabel("ENGLISH License");
        license_en.setLocale("en");
        license.addText(license_es);
        license.addText(license_en);
        configurationDto.setLicense(license);

        // Contact
        configurationDto.setContact(mockExternalItemDtoComplete(CODE_01, URN_01, TypeExternalArtefactsEnum.AGENCY));

        // Status
        configurationDto.setStatus(CommonMetadataStatusEnum.ENABLED);

        return configurationDto;
    }

}
