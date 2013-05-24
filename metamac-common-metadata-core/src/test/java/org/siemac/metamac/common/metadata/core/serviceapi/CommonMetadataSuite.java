package org.siemac.metamac.common.metadata.core.serviceapi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Spring based transactional test with DbUnit support.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({CommonMetadataServiceFacadeTest.class, 
                        CommonMetadataServiceTest.class, 
                        SecurityCommonMetadataServiceFacadeTest.class})
public class CommonMetadataSuite {
}
