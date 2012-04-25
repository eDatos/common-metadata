package org.siemac.metamac.common.metadata.core.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataDtoMocks;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.domain.common.metadata.dto.ConfigurationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oracle/applicationContext-test.xml"})
public class CommonMetadataServiceFacadeTest extends CommonMetadataBaseTests implements CommonMetadataServiceFacadeTestBase {

    @Autowired
    protected CommonMetadataServiceFacade commonMetadataServiceFacade;

    @Test
    public void testFindConfigurationById() throws MetamacException {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockConfigurationDto());
        ConfigurationDto configurationRetrieved = commonMetadataServiceFacade.findConfigurationById(getServiceContextAdministrador(), configurationDto.getId());
        assertNotNull(configurationRetrieved);
        assertTrue(configurationDto.getId().equals(configurationRetrieved.getId()));
    }
    
    @Test
    public void testFindConfigurationByIdNotFound() throws MetamacException {
        try {
            commonMetadataServiceFacade.findConfigurationById(getServiceContextAdministrador(), Long.valueOf(-1));
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.CONFIGURATION_NOT_FOUND.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Test
    public void testFindAllConfigurations() throws MetamacException {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockConfigurationDto());
        assertNotNull(configurationDto);
        
        List<ConfigurationDto> configurationDtos = commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador());
        assertTrue(!configurationDtos.isEmpty());

    }

    @Test
    public void testCreateConfiguration() throws MetamacException {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockConfigurationDto());
        assertNotNull(configurationDto);
    }
    
    @Test
    public void testUpdateConfiguration() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockConfigurationDto());
        assertNotNull(configurationDto);
        
        configurationDto.setCode("Conf-modified");
        configurationDto = commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDto);
        assertNotNull(configurationDto);
    }

    @Test
    public void testDeleteConfiguration() throws MetamacException {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockConfigurationDto());
        assertNotNull(configurationDto);
        int numberConfigurations = commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador()).size();
        commonMetadataServiceFacade.deleteConfiguration(getServiceContextAdministrador(), configurationDto.getId());
        assertTrue(commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador()).size() == (numberConfigurations - 1));
    }

}
