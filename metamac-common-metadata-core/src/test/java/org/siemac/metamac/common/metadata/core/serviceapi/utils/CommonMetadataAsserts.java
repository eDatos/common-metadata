package org.siemac.metamac.common.metadata.core.serviceapi.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;

public class CommonMetadataAsserts extends MetamacAsserts {

    public static void assertEqualsConfiguration(Configuration expected, Configuration actual) {
        assertEquals(expected.getCode(), actual.getCode());
        assertEqualsInternationalString(expected.getConfDataTreatment(), actual.getConfDataTreatment());
        assertEqualsInternationalString(expected.getConfPolicy(), actual.getConfPolicy());
        assertEqualsInternationalString(expected.getDataSharing(), actual.getDataSharing());
        assertEqualsInternationalString(expected.getLegalActs(), actual.getLegalActs());
        assertEqualsInternationalString(expected.getLicense(), actual.getLicense());
        assertEqualsExternalItem(expected.getContact(), actual.getContact());
    }

    public static void assertEqualsConfigurationDto(ConfigurationDto expected, ConfigurationDto actual) {
        assertEquals(expected.getCode(), actual.getCode());
        assertEqualsInternationalStringDto(expected.getConfDataTreatment(), actual.getConfDataTreatment());
        assertEqualsInternationalStringDto(expected.getConfPolicy(), actual.getConfPolicy());
        assertEqualsInternationalStringDto(expected.getDataSharing(), actual.getDataSharing());
        assertEqualsInternationalStringDto(expected.getLegalActs(), actual.getLegalActs());
        assertEqualsInternationalStringDto(expected.getLicense(), actual.getLicense());
        assertEqualsExternalItemDto(expected.getContact(), actual.getContact());
    }

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING
    // -----------------------------------------------------------------

    public static void assertEqualsInternationalString(InternationalString expected, InternationalString actual) {
        if (actual == null && expected == null) {
            return;
        } else if ((actual != null && expected == null) || (actual == null && expected != null)) {
            fail();
        }
        assertEquals(expected.getTexts().size(), actual.getTexts().size());
        for (LocalisedString localisedStringExpected : expected.getTexts()) {
            assertEquals(localisedStringExpected.getLabel(), actual.getLocalisedLabel(localisedStringExpected.getLocale()));
        }
    }

    public static void assertEqualsInternationalString(InternationalString internationalString, String locale1, String label1, String locale2, String label2) {
        int count = 0;
        if (locale1 != null) {
            assertEquals(label1, internationalString.getLocalisedLabel(locale1));
            count++;
        }
        if (locale2 != null) {
            assertEquals(label2, internationalString.getLocalisedLabel(locale2));
            count++;
        }
        assertEquals(count, internationalString.getTexts().size());
    }

    // -----------------------------------------------------------------
    // EXTERNAL ITEMS
    // -----------------------------------------------------------------

    public static void assertEqualsExternalItem(ExternalItem expected, ExternalItem actual) {

        if (actual == null && expected == null) {
            return;
        } else if ((actual != null && expected == null) || (actual == null && expected != null)) {
            fail();
        }

        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getCodeNested(), actual.getCodeNested());
        assertEquals(expected.getUri(), actual.getUri());
        assertEquals(expected.getUrn(), actual.getUrn());
        assertEquals(expected.getUrnProvider(), actual.getUrnProvider());
        assertEquals(expected.getType(), actual.getType());
        assertEqualsInternationalString(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getManagementAppUrl(), actual.getManagementAppUrl());
    }
}
