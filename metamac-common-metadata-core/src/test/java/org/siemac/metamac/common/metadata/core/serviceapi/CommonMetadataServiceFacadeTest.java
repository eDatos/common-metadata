package org.siemac.metamac.common.metadata.core.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataAsserts;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataDtoMocks;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/common-metadata/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager="txManager", defaultRollback=true)
@Transactional
public class CommonMetadataServiceFacadeTest extends CommonMetadataBaseTests implements CommonMetadataServiceFacadeTestBase {

    @Autowired
    protected CommonMetadataServiceFacade commonMetadataServiceFacade;

    @Test
    public void testFindConfigurationById() throws MetamacException {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        ConfigurationDto configurationRetrieved = commonMetadataServiceFacade.findConfigurationById(getServiceContextAdministrador(), configurationDto.getId());
        assertNotNull(configurationRetrieved);
        CommonMetadataAsserts.assertEqualsConfigurationDto(configurationDto, configurationRetrieved);
    }
    
    @Test
    public void testFindConfigurationByUrn() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        ConfigurationDto configurationRetrieved = commonMetadataServiceFacade.findConfigurationByUrn(getServiceContextAdministrador(), configurationDto.getUrn());
        assertNotNull(configurationRetrieved);
        CommonMetadataAsserts.assertEqualsConfigurationDto(configurationDto, configurationRetrieved);
        
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
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        assertNotNull(configurationDto);

        List<ConfigurationDto> configurationDtos = commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador());
        assertTrue(!configurationDtos.isEmpty());

    }

    @Test
    public void testCreateConfiguration() throws MetamacException {
        ConfigurationDto expectedConfigurationDto = CommonMetadataDtoMocks.mockEnableConfigurationDto();
        
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), expectedConfigurationDto);
        assertNotNull(configurationDto);
        CommonMetadataAsserts.assertEqualsConfigurationDto(expectedConfigurationDto, configurationDto);
    }

    @Test
    public void testUpdateConfigurationCodeUnmodifiable() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        configurationDto.setCode("Conf-modified");

        try {
            commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDto);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEquals(ServiceExceptionType.METADATA_UNMODIFIABLE.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }
    
    @Test
    public void testUpdateConfiguration() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        
        ExternalItemDto contact = new ExternalItemDto();
        contact.setCode("new-contact-code");
        contact.setUri("new-contact-uri");
        contact.setUrn("new-contact-urn");
        contact.setType(TypeExternalArtefactsEnum.AGENCY);
        contact.setManagementAppUrl("new-management-app-url");
        contact.setTitle(CommonMetadataDtoMocks.mockInternationalStringDto("es", "new-contact-title-es"));
        configurationDto.setContact(contact);
        
        LocalisedStringDto legalActs_ca = new LocalisedStringDto();
        legalActs_ca.setLabel("CATALAN Legal Acts");
        legalActs_ca.setLocale("ca");
        configurationDto.getLegalActs().addText(legalActs_ca);
        
        ConfigurationDto updatedConfigurationDto = commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDto);
        assertNotNull(configurationDto);
        CommonMetadataAsserts.assertEqualsConfigurationDto(configurationDto, updatedConfigurationDto);
    }

    @Test
    public void testUpdateConfigurationOptimisticLockingError() throws Exception {

        Long id = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto()).getId();

        // Retrieve configuration - session 1
        ConfigurationDto configurationDtoSession1 = commonMetadataServiceFacade.findConfigurationById(getServiceContextAdministrador(), id);
        assertEquals(Long.valueOf(0), configurationDtoSession1.getOptimisticLockingVersion());
        configurationDtoSession1.setConfDataTreatment(CommonMetadataDtoMocks.mockInternationalStringDto("es", "newConf1"));

        // Retrieve configuration - session 2
        ConfigurationDto configurationDtoSession2 = commonMetadataServiceFacade.findConfigurationById(getServiceContextAdministrador(), id);
        assertEquals(Long.valueOf(0), configurationDtoSession2.getOptimisticLockingVersion());
        configurationDtoSession2.setConfDataTreatment(CommonMetadataDtoMocks.mockInternationalStringDto("es", "newConf2"));

        // Update configuration - session 1
        ConfigurationDto configurationDtoSession1AfterUpdate = commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDtoSession1);
        assertEquals(Long.valueOf(1), configurationDtoSession1AfterUpdate.getOptimisticLockingVersion());

        // Update configuration - session 2
        try {
            commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDtoSession2);
            fail("Optimistic locking");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEquals(ServiceExceptionType.OPTIMISTIC_LOCKING.getCode(), e.getExceptionItems().get(0).getCode());
            assertNull(e.getExceptionItems().get(0).getMessageParameters());
        }

        // Update configuration - session 1
        configurationDtoSession1AfterUpdate.setConfDataTreatment(CommonMetadataDtoMocks.mockInternationalStringDto("es", "newConf1_secondUpdate"));
        ConfigurationDto configurationDtoSession1AfterUpdate2 = commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDtoSession1AfterUpdate);
        assertEquals(Long.valueOf(2), configurationDtoSession1AfterUpdate2.getOptimisticLockingVersion());

    }

    @Test
    public void testDeleteConfiguration() throws MetamacException {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        assertNotNull(configurationDto);
        int numberConfigurations = commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador()).size();
        commonMetadataServiceFacade.deleteConfiguration(getServiceContextAdministrador(), configurationDto.getId());
        assertTrue(commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador()).size() == (numberConfigurations - 1));
    }

    @Test
    public void testUpdateConfigurationsStatus() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        assertNotNull(configurationDto);

        configurationDto.setStatus(CommonMetadataStatusEnum.DISABLED);

        ConfigurationDto updatedConfigurationDto = commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDto);

        assertNotNull(configurationDto);
        CommonMetadataAsserts.assertEqualsConfigurationDto(configurationDto, updatedConfigurationDto);
    }
}
