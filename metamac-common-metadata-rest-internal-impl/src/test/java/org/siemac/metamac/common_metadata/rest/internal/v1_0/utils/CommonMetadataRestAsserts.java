package org.siemac.metamac.common_metadata.rest.internal.v1_0.utils;

import static org.junit.Assert.assertEquals;

import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.Configurations;
import org.siemac.metamac.rest.common.test.utils.MetamacRestAsserts;

public class CommonMetadataRestAsserts {

    public static void assertEqualsConfiguration(Configuration expected, Configuration actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUrn(), actual.getUrn());
        assertEquals(expected.getKind(), actual.getKind());
        assertEquals(expected.getSelfLink(), actual.getSelfLink());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getLegalActs(), actual.getLegalActs());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDataSharing(), actual.getDataSharing());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getConfPolicy(), actual.getConfPolicy());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getConfDataTreatment(), actual.getConfDataTreatment());
        MetamacRestAsserts.assertEqualsResource(expected.getContact(), actual.getContact());
        assertEquals(expected.getStatus(), actual.getStatus());
        MetamacRestAsserts.assertEqualsResourceLink(expected.getParent(), actual.getParent());
        MetamacRestAsserts.assertEqualsChildren(expected.getChildren(), actual.getChildren());
    }
    
    public static void assertEqualsConfigurations(Configurations expected, Configurations actual) {
        MetamacRestAsserts.assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        MetamacRestAsserts.assertEqualsListBase(expected, actual);
        MetamacRestAsserts.assertEqualsResources(expected.getConfigurations(), actual.getConfigurations());
    }
}
