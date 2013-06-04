package org.siemac.metamac.common.metadata.core.serviceapi.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.siemac.metamac.core.common.constants.CoreCommonConstants.API_LATEST;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.common.test.constants.ConfigurationMockConstants;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.constants.shared.RegularExpressionConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.utils.TypeExternalArtefactsEnumUtils;

/**
 * Asserts to tests
 */
public class CommonMetadataBaseAsserts extends MetamacAsserts {

    // -----------------------------------------------------------------
    // EXTERNAL ITEMS: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsExternalItem(ExternalItem expected, ExternalItem actual) {

        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }

        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getUri(), actual.getUri());
        assertEquals(expected.getUrn(), actual.getUrn());
        assertEquals(expected.getType(), actual.getType());
        assertEqualsInternationalString(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getManagementAppUrl(), actual.getManagementAppUrl());
    }

    // -----------------------------------------------------------------
    // EXTERNAL ITEMS: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsExternalItem(ExternalItem entity, ExternalItemDto dto, MapperEnum mapperEnum) {
        assertEqualsNullability(entity, dto);
        if (entity == null) {
            return;
        }

        String baseApi = null;
        String baseWebApplication = null;

        if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(dto.getType())) {
            baseWebApplication = ConfigurationMockConstants.COMMON_METADATA_INTERNAL_WEB_APP_URL_BASE;
            baseApi = ConfigurationMockConstants.COMMON_METADATA_EXTERNAL_API_URL_BASE;
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(dto.getType())) {
            baseWebApplication = ConfigurationMockConstants.STATISTICAL_OPERATIONS_INTERNAL_WEB_APP_URL_BASE;
            baseApi = ConfigurationMockConstants.STATISTICAL_OPERATIONS_INTERNAL_API_URL_BASE;
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(dto.getType())) {
            baseWebApplication = ConfigurationMockConstants.SRM_INTERNAL_WEB_APP_URL_BASE;
            baseApi = ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE;
        } else {
            fail("unexpected type of external item");
        }

        assertEqualsExternalItem(entity, dto, baseApi, baseWebApplication, mapperEnum);
    }

    private static void assertEqualsExternalItem(ExternalItem entity, ExternalItemDto dto, String baseApi, String baseWebApplication, MapperEnum mapperEnum) {
        assertEqualsExternalItem(entity, dto);

        assertEqualsNullability(entity.getUri(), dto.getUri());
        if (entity.getUri() != null) {
            if (MapperEnum.DO2DTO.equals(mapperEnum)) {
                assertEquals(baseApi + entity.getUri(), dto.getUri());
            } else if (MapperEnum.DTO2DO.equals(mapperEnum)) {
                String expectedDoUri = dto.getUri().replaceFirst(baseApi, StringUtils.EMPTY);
                expectedDoUri = expectedDoUri.replaceFirst(RegularExpressionConstants.URN_VERSION_REG_EXP, API_LATEST);
                assertEquals(expectedDoUri, entity.getUri());
            } else {
                fail("Mapper unexpected: " + mapperEnum);
            }
        }

        assertEqualsNullability(entity.getManagementAppUrl(), dto.getManagementAppUrl());
        if (entity.getManagementAppUrl() != null) {
            if (MapperEnum.DO2DTO.equals(mapperEnum)) {
                assertEquals(baseWebApplication + entity.getManagementAppUrl(), dto.getManagementAppUrl());
            } else if (MapperEnum.DTO2DO.equals(mapperEnum)) {
                assertEquals(dto.getManagementAppUrl().replaceFirst(baseWebApplication, StringUtils.EMPTY), entity.getManagementAppUrl());
            } else {
                fail("Mapper unexpected: " + mapperEnum);
            }
        }
    }

    private static void assertEqualsExternalItem(ExternalItem entity, ExternalItemDto dto) {
        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getUrn(), dto.getUrn());
        assertEquals(entity.getType(), dto.getType());
        assertEqualsInternationalString(entity.getTitle(), dto.getTitle());
    }

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsInternationalString(InternationalString expected, InternationalString actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
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
    // INTERNATIONAL STRING: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsInternationalString(InternationalString entity, InternationalStringDto dto) {
        assertEqualsNullability(entity, dto);
        if (entity == null) {
            return;
        }

        assertEquals(entity.getTexts().size(), dto.getTexts().size());
        for (LocalisedString localisedStringExpected : entity.getTexts()) {
            assertEquals(localisedStringExpected.getLabel(), dto.getLocalisedLabel(localisedStringExpected.getLocale()));
        }
    }

}
