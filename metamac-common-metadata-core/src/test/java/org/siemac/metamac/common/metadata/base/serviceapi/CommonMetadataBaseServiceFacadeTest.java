package org.siemac.metamac.common.metadata.base.serviceapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
public class CommonMetadataBaseServiceFacadeTest extends AbstractDbUnitJpaTests implements CommonMetadataBaseServiceFacadeTestBase {
    
	@Autowired
    protected CommonMetadataBaseServiceFacade commonMetadataBaseServiceFacade;
	
	private final ServiceContext serviceContext = new ServiceContext("system", "123456", "junit");

    protected ServiceContext getServiceContext() {
        return serviceContext;
    }
    
	@Override
	public void testFindConfigurationById() throws CommonMetadataException {
		ConfigurationDto configurationDto = commonMetadataBaseServiceFacade.saveConfiguration(getServiceContext(), createConfigurationDto());
		ConfigurationDto configurationRetrieved = commonMetadataBaseServiceFacade.findConfigurationById(getServiceContext(), configurationDto.getId());
		assertNotNull(configurationRetrieved);
		assertTrue(configurationDto.getId().equals(configurationRetrieved.getId()));
	}
	
	@Override
	public void testFindAllConfigurations() throws CommonMetadataException {
		testSaveConfiguration();
		List<ConfigurationDto> configurationDtos = commonMetadataBaseServiceFacade.findAllConfigurations(getServiceContext());
		assertTrue(!configurationDtos.isEmpty());
		
	}

	@Override
	public void testSaveConfiguration() throws CommonMetadataException {
		ConfigurationDto configurationDto = commonMetadataBaseServiceFacade.saveConfiguration(getServiceContext(), createConfigurationDto());
		assertNotNull(configurationDto);
	}

	@Override
	public void testDeleteConfiguration() throws CommonMetadataException {
		ConfigurationDto configurationDto = commonMetadataBaseServiceFacade.saveConfiguration(getServiceContext(), createConfigurationDto());
		assertNotNull(configurationDto);
		int numberConfigurations = commonMetadataBaseServiceFacade.findAllConfigurations(getServiceContext()).size();
		commonMetadataBaseServiceFacade.deleteConfiguration(getServiceContext(), configurationDto);
		assertTrue(commonMetadataBaseServiceFacade.findAllConfigurations(getServiceContext()).size() == (numberConfigurations -1));
	}
	
	
	/**************************************************************************
     *  						PRIVATE UTILS 
     **************************************************************************/

	private ConfigurationDto createConfigurationDto() {
		ConfigurationDto configurationDto = new ConfigurationDto();
		configurationDto.setName("configuration-0123456789");
		return configurationDto;
	}
	
}
