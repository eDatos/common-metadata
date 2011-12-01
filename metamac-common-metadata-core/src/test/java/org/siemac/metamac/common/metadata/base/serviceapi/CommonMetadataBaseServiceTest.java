package org.siemac.metamac.common.metadata.base.serviceapi;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import org.junit.Test;
import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
public class CommonMetadataBaseServiceTest extends AbstractDbUnitJpaTests implements CommonMetadataBaseServiceTestBase {
    
	@Autowired
    protected CommonMetadataBaseService commonMetadataBaseService;
	
	private final ServiceContext serviceContext = new ServiceContext("system", "123456", "junit");

    protected ServiceContext getServiceContext() {
        return serviceContext;
    }
    

    @Test
    public void testFindConfigurationById() throws Exception {
       Configuration configuration = commonMetadataBaseService.saveConfiguration(getServiceContext(), createConfiguration());
       assertNotNull(configuration);
       Configuration configurationRetrieved = commonMetadataBaseService.findConfigurationById(getServiceContext(), configuration.getId());
       assertNotNull(configurationRetrieved);
       assertTrue(configuration.equals(configurationRetrieved));
    }

    @Test
    public void testFindAllConfigurations() throws Exception {
    	testSaveConfiguration();
    	List<Configuration> configurations = commonMetadataBaseService.findAllConfigurations(getServiceContext());
    	assertTrue(!configurations.isEmpty());
    }

    @Test
    public void testSaveConfiguration() throws Exception {
    	Configuration configuration = commonMetadataBaseService.saveConfiguration(getServiceContext(), createConfiguration());
    	assertNotNull(configuration);
    }

    @Test
    public void testDeleteConfiguration() throws Exception {
    	testSaveConfiguration();
    	List<Configuration> configurations = commonMetadataBaseService.findAllConfigurations(getServiceContext());
    	Configuration configuration = configurations.get(configurations.size() - 1);
    	commonMetadataBaseService.deleteConfiguration(getServiceContext(), configuration);
    	assertTrue(commonMetadataBaseService.findAllConfigurations(getServiceContext()).size() < configurations.size());
    }
    
    
	/**************************************************************************
     *  						PRIVATE UTILS 
     **************************************************************************/

    private Configuration createConfiguration() {
    	Configuration configuration = new Configuration();
    	configuration.setName("configuration-0123456789");
    	return configuration;
    }
    
}
