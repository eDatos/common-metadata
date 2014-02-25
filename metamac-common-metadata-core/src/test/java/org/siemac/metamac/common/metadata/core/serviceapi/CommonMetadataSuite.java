package org.siemac.metamac.common.metadata.core.serviceapi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.siemac.metamac.common.metadata.core.error.CommonMetadataCheckTranslationsTest;
import org.siemac.metamac.common.metadata.core.mapper.ExternalItemsDo2DtoMapperTest;
import org.siemac.metamac.common.metadata.core.mapper.ExternalItemsDto2DoMapperTest;
import org.siemac.metamac.common.metadata.core.mapper.InternationalStringsDo2DtoMapperTest;
import org.siemac.metamac.common.metadata.core.mapper.InternationalStringsDto2DoMapperTest;

/**
 * Spring based transactional test with DbUnit support.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({CommonMetadataCheckTranslationsTest.class,
                        CommonMetadataServiceFacadeTest.class, 
                        CommonMetadataServiceTest.class, 
                        SecurityCommonMetadataServiceFacadeTest.class,
                        ExternalItemsDo2DtoMapperTest.class,
                        ExternalItemsDto2DoMapperTest.class,
                        InternationalStringsDo2DtoMapperTest.class,
                        InternationalStringsDto2DoMapperTest.class})
public class CommonMetadataSuite {
}
