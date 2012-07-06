package org.siemac.metamac.common.metadata.rest.internal.v1_0.utils;

import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;

public class CommonMetadataCoreMocks {

    public static Configuration mockConfiguration1() {
        return mockConfiguration("1", CommonMetadataStatusEnum.ENABLED);
    }

    public static Configuration mockConfiguration2() {
        return mockConfiguration("2", CommonMetadataStatusEnum.DISABLED);
    }

    private static Configuration mockConfiguration(String subCode, CommonMetadataStatusEnum status) {

        Configuration configuration = new Configuration();
        configuration.setCode("configuration" + subCode);
        configuration.setLegalActs(mockInternationalString("LegalActs", subCode));
        configuration.setDataSharing(mockInternationalString("DataSharing", subCode));
        configuration.setLegalActs(mockInternationalString("LegalActs", subCode));
        configuration.setConfPolicy(mockInternationalString("ConfPolicy", subCode));
        configuration.setConfDataTreatment(mockInternationalString("ConfDataTreatment", subCode));
        configuration.setStatus(status);
        configuration.setContact(mockContact("1", TypeExternalArtefactsEnum.AGENCY, "/contacts/contact1"));
        return configuration;
    }

    private static ExternalItem mockContact(String subCode, TypeExternalArtefactsEnum type, String uri) {
        InternationalString title = mockInternationalString("es", "Título contact contact" + subCode, "en", "Title contact contact" + subCode);
        return new ExternalItem(uri, "contact" + subCode, type, title, null);
    }

    private static InternationalString mockInternationalString(String metadata, String subCode) {
        String subTitle = metadata + " " + subCode;
        return mockInternationalString("es", subTitle + " en español", "en", subTitle + " in English");
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