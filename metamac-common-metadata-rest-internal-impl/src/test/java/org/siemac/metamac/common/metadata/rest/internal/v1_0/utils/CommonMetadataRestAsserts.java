package org.siemac.metamac.common.metadata.rest.internal.v1_0.utils;

import static org.junit.Assert.assertEquals;

import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common.test.utils.MetamacRestAsserts;

public class CommonMetadataRestAsserts {

    public static void assertEqualsConfiguration(Configuration expected, Configuration actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getKind(), actual.getKind());
        assertEquals(expected.getSelfLink(), actual.getSelfLink());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getLegalActs(), actual.getLegalActs());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getDataSharing(), actual.getDataSharing());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getConfPolicy(), actual.getConfPolicy());
        MetamacRestAsserts.assertEqualsInternationalString(expected.getConfDataTreatment(), actual.getConfDataTreatment());
        MetamacRestAsserts.assertEqualsResource(expected.getContact(), actual.getContact());
        assertEquals(expected.getStatus(), actual.getStatus());
        MetamacRestAsserts.assertEqualsResource(expected.getParent(), actual.getParent());
        MetamacRestAsserts.assertEqualsResources(expected.getchildren(), actual.getchildren());
    }
}
