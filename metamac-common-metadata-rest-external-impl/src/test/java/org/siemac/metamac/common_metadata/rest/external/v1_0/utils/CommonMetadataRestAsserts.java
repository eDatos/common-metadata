package org.siemac.metamac.common_metadata.rest.external.v1_0.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.siemac.metamac.rest.common.test.utils.MetamacRestAsserts;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal;

public class CommonMetadataRestAsserts {

    public static void assertEqualsConfiguration(Configuration expected, Configuration actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUrn(), actual.getUrn());
        assertEquals(expected.getKind(), actual.getKind());
        MetamacRestAsserts.assertEqualsResourceLink(expected.getSelfLink(), actual.getSelfLink());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getLegalActs(), actual.getLegalActs());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDataSharing(), actual.getDataSharing());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getConfPolicy(), actual.getConfPolicy());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getConfDataTreatment(), actual.getConfDataTreatment());
        assertEqualsResource(expected.getContact(), actual.getContact());
        assertEquals(expected.getStatus(), actual.getStatus());
        MetamacRestAsserts.assertEqualsResourceLink(expected.getParentLink(), actual.getParentLink());
        MetamacRestAsserts.assertEqualsChildLinks(expected.getChildLinks(), actual.getChildLinks());
        assertEquals(expected.getManagementAppLink(), actual.getManagementAppLink());
    }

    public static void assertEqualsConfigurations(Configurations expected, Configurations actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        assertEqualsResourcesInternal(expected.getConfigurations(), actual.getConfigurations());
    }

    public static void assertEqualsResourcesInternal(List<ResourceInternal> expecteds, List<ResourceInternal> actuals) {
        MetamacRestAsserts.assertEqualsNullability(expecteds, actuals);
        if (expecteds == null) {
            return;
        }
        assertEquals(expecteds.size(), actuals.size());
        for (ResourceInternal expected : expecteds) {
            boolean existsItem = false;
            for (ResourceInternal actual : actuals) {
                if (expected.getId().equals(actual.getId())) {
                    assertEqualsResource(expected, actual);
                    existsItem = true;
                }
            }
            assertTrue(existsItem);
        }
    }

    public static void assertEqualsResource(ResourceInternal expected, ResourceInternal actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.getManagementAppLink(), actual.getManagementAppLink());
        MetamacRestAsserts.assertEqualsResource(expected, actual);
    }

}
