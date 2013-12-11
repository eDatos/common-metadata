package org.siemac.metamac.common.metadata.core.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionParameters;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataAsserts;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataDtoMocks;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/common-metadata/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CommonMetadataServiceFacadeTest extends CommonMetadataBaseTests implements CommonMetadataServiceFacadeTestBase {

    @Autowired
    protected CommonMetadataServiceFacade commonMetadataServiceFacade;

    // --------------------------------------------------------------------------------
    // CONFIGURATIONS (metadata)
    // --------------------------------------------------------------------------------

    @Override
    @Test
    public void testFindConfigurationById() throws MetamacException {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        ConfigurationDto configurationRetrieved = commonMetadataServiceFacade.findConfigurationById(getServiceContextAdministrador(), configurationDto.getId());
        assertNotNull(configurationRetrieved);
        CommonMetadataAsserts.assertEqualsConfigurationDto(configurationDto, configurationRetrieved);
    }

    @Override
    @Test
    public void testFindConfigurationByUrn() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        ConfigurationDto configurationRetrieved = commonMetadataServiceFacade.findConfigurationByUrn(getServiceContextAdministrador(), configurationDto.getUrn());
        assertNotNull(configurationRetrieved);
        CommonMetadataAsserts.assertEqualsConfigurationDto(configurationDto, configurationRetrieved);

    }

    @Test
    public void testFindConfigurationByIdNotFound() throws MetamacException {
        Long code = NOT_EXISTS_ID;

        expectedMetamacException(new MetamacException(ServiceExceptionType.CONFIGURATION_NOT_FOUND, code));
        commonMetadataServiceFacade.findConfigurationById(getServiceContextAdministrador(), NOT_EXISTS_ID);
    }

    @Override
    @Test
    public void testFindAllConfigurations() throws MetamacException {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        assertNotNull(configurationDto);

        List<ConfigurationDto> configurationDtos = commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador());
        assertTrue(!configurationDtos.isEmpty());

    }

    @Override
    @Test
    public void testCreateConfiguration() throws MetamacException {
        ConfigurationDto expectedConfigurationDto = CommonMetadataDtoMocks.mockEnableConfigurationDto();

        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), expectedConfigurationDto);
        assertNotNull(configurationDto);
        CommonMetadataAsserts.assertEqualsConfigurationDto(expectedConfigurationDto, configurationDto);
    }

    @Test
    public void testUpdateConfigurationCodeModifiableBeforePublishExternallyBesideChangeIsExternallyPublished() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());

        configurationDto.setCode("Conf-modified");
        configurationDto.setExternallyPublished(true);
        configurationDto = commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDto);

        assertEquals("Conf-modified", configurationDto.getCode());
    }

    @Test
    public void testUpdateConfigurationCodeModifiableBeforePublishExternally() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());

        configurationDto.setCode("Conf-modified");
        commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDto);

        assertEquals("Conf-modified", configurationDto.getCode());

    }

    @Test
    public void testUpdateConfigurationCodeUnmodifiableAfterPublishExternally() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        configurationDto = commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextAdministrador(), configurationDto.getId());

        try {
            configurationDto.setCode("Conf-modified");
            commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDto);
            fail("code unmodifiable");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEquals(ServiceExceptionType.METADATA_UNMODIFIABLE.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Override
    @Test
    public void testUpdateConfiguration() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());

        configurationDto.setContact(MetamacMocks.mockExternalItemDtoComplete("new-contact-code", "new-contact-urn", TypeExternalArtefactsEnum.AGENCY));

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

    @Override
    @Test
    public void testDeleteConfiguration() throws MetamacException {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        assertNotNull(configurationDto);
        int numberConfigurations = commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador()).size();
        commonMetadataServiceFacade.deleteConfiguration(getServiceContextAdministrador(), configurationDto.getId());
        assertTrue(commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador()).size() == (numberConfigurations - 1));
    }

    @Override
    @Test
    public void testUpdateConfigurationsStatus() throws Exception {
        // Create configurations
        ConfigurationDto configurationDto01 = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        ConfigurationDto configurationDto02 = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        ConfigurationDto configurationDto03 = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        assertNotNull(configurationDto01);
        assertNotNull(configurationDto02);
        assertNotNull(configurationDto03);

        // Create List to be actualize
        List<Long> configurationIds = new ArrayList<Long>();
        configurationIds.add(configurationDto01.getId());
        configurationIds.add(configurationDto02.getId());
        configurationIds.add(configurationDto03.getId());

        commonMetadataServiceFacade.updateConfigurationsStatus(getServiceContextAdministrador(), configurationIds, CommonMetadataStatusEnum.DISABLED);

        assertEquals(CommonMetadataStatusEnum.DISABLED, commonMetadataServiceFacade.findConfigurationById(getServiceContextAdministrador(), configurationDto01.getId()).getStatus());
        assertEquals(CommonMetadataStatusEnum.DISABLED, commonMetadataServiceFacade.findConfigurationById(getServiceContextAdministrador(), configurationDto02.getId()).getStatus());
        assertEquals(CommonMetadataStatusEnum.DISABLED, commonMetadataServiceFacade.findConfigurationById(getServiceContextAdministrador(), configurationDto03.getId()).getStatus());
    }

    @Override
    @Test
    public void testPublishExternallyConfiguration() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        assertNotNull(configurationDto);

        ConfigurationDto updatedConfigurationDto = commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextAdministrador(), configurationDto.getId());

        assertNotNull(configurationDto);
        assertEquals(true, updatedConfigurationDto.isExternallyPublished());
    }

    @Test
    public void testPublishExternallyConfigurationDisabled() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        assertNotNull(configurationDto);

        configurationDto.setStatus(CommonMetadataStatusEnum.DISABLED);

        ConfigurationDto updatedConfigurationDto = commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDto);
        updatedConfigurationDto = commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextAdministrador(), updatedConfigurationDto.getId());

        assertNotNull(configurationDto);
        assertEquals(true, updatedConfigurationDto.isExternallyPublished());
    }

    @Test
    public void testPublishExternallyConfigurationEnabled() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        assertNotNull(configurationDto);

        configurationDto.setStatus(CommonMetadataStatusEnum.ENABLED);

        ConfigurationDto updatedConfigurationDto = commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDto);
        updatedConfigurationDto = commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextAdministrador(), updatedConfigurationDto.getId());

        assertNotNull(configurationDto);
        assertEquals(true, updatedConfigurationDto.isExternallyPublished());
    }

    // --------------------------------------------------------------------------------
    // DATA CONFIGURATIONS
    // --------------------------------------------------------------------------------

    @Override
    @Test
    public void testFindDataConfigurationById() throws Exception {
        DataConfigurationDto dataConfiguration = commonMetadataServiceFacade.findDataConfigurationById(getServiceContextAdministrador(), DATA_CONFIGURATION_01_ID);
        assertNotNull(dataConfiguration);
    }

    @Test
    public void testFindDataConfigurationByIdNotFound() throws MetamacException {
        expectedMetamacException(new MetamacException(ServiceExceptionType.DATA_CONFIGURATION_NOT_FOUND, NOT_EXISTS_ID));
        commonMetadataServiceFacade.findDataConfigurationById(getServiceContextAdministrador(), NOT_EXISTS_ID);
    }

    @Override
    @Test
    public void testFindDataConfigurationByConfigurationKey() throws Exception {
        DataConfigurationDto dataConfiguration = commonMetadataServiceFacade.findDataConfigurationByConfigurationKey(getServiceContextAdministrador(), DATA_CONFIGURATION_01_KEY);
        assertNotNull(dataConfiguration);
    }

    @Test
    public void testFindDataConfigurationByConfigurationKeyNotFound() throws MetamacException {
        expectedMetamacException(new MetamacException(ServiceExceptionType.DATA_CONFIGURATION_NOT_FOUND_CONFIGURATION_KEY, NOT_EXISTS_CODE));
        commonMetadataServiceFacade.findDataConfigurationByConfigurationKey(getServiceContextAdministrador(), NOT_EXISTS_CODE);
    }

    @Override
    @Test
    public void testFindDataConfigurationsOfSystemProperties() throws Exception {
        int sytemProperties = commonMetadataServiceFacade.findDataConfigurationsOfSystemProperties(getServiceContextAdministrador()).size();
        assertEquals(NUMBER_DATA_CONFIGURATIONS_SYSTEM_PROPERTIES, sytemProperties);
    }

    @Override
    @Test
    public void testFindDataConfigurationsOfDefaultValues() throws Exception {
        int defaultValues = commonMetadataServiceFacade.findDataConfigurationsOfDefaultValues(getServiceContextAdministrador()).size();
        assertEquals(NUMBER_DATA_CONFIGURATIONS_DEFAULT_VALUES, defaultValues);
    }

    @Override
    @Test
    public void testUpdateDataConfiguration() throws Exception {
        DataConfigurationDto dataConfigurationDtoBefore = commonMetadataServiceFacade.findDataConfigurationById(getServiceContextAdministrador(), DATA_CONFIGURATION_01_ID);
        dataConfigurationDtoBefore.setConfigurationValue("MODIFIED-VALUE");
        DataConfigurationDto dataConfigurationDtoAfter = commonMetadataServiceFacade.updateDataConfiguration(getServiceContextAdministrador(), dataConfigurationDtoBefore);
        assertEquals(dataConfigurationDtoBefore.getConfigurationValue(), dataConfigurationDtoAfter.getConfigurationValue());
    }

    @Test
    public void testUpdateDataConfigurationErrorDataConfigurationKeyUnmodifiable() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_UNMODIFIABLE, ServiceExceptionParameters.DATA_CONFIGURATION_KEY));

        DataConfigurationDto dataConfigurationDto = commonMetadataServiceFacade.findDataConfigurationById(getServiceContextAdministrador(), DATA_CONFIGURATION_01_ID);
        dataConfigurationDto.setConfigurationKey("MODIFIED-KEY");
        commonMetadataServiceFacade.updateDataConfiguration(getServiceContextAdministrador(), dataConfigurationDto);
    }

    @Test
    public void testUpdateDataConfigurationErrorDataIsSystemPropertyUnmodifiable() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_UNMODIFIABLE, ServiceExceptionParameters.DATA_CONFIGURATION_IS_SYSTEM_PROPERTY));

        DataConfigurationDto dataConfigurationDto = commonMetadataServiceFacade.findDataConfigurationById(getServiceContextAdministrador(), DATA_CONFIGURATION_01_ID);
        dataConfigurationDto.setSystemProperty(!dataConfigurationDto.isSystemProperty());
        commonMetadataServiceFacade.updateDataConfiguration(getServiceContextAdministrador(), dataConfigurationDto);
    }

    @Test
    public void testUpdateDataConfigurationOptimisticLockingError() throws Exception {
        Long id = DATA_CONFIGURATION_01_ID;

        // Retrieve dataConfiguration - session 1
        DataConfigurationDto dataConfigurationDtoSession1 = commonMetadataServiceFacade.findDataConfigurationById(getServiceContextAdministrador(), id);
        assertEquals(Long.valueOf(1), dataConfigurationDtoSession1.getOptimisticLockingVersion());
        dataConfigurationDtoSession1.setConfigurationValue("new-value-1");

        // Retrieve dataConfiguration - session 2
        DataConfigurationDto dataConfigurationDtoSession2 = commonMetadataServiceFacade.findDataConfigurationById(getServiceContextAdministrador(), id);
        assertEquals(Long.valueOf(1), dataConfigurationDtoSession2.getOptimisticLockingVersion());
        dataConfigurationDtoSession2.setConfigurationValue("new-value-2");

        // Update dataConfiguration - session 1
        DataConfigurationDto dataConfigurationDtoSession1AfterUpdate = commonMetadataServiceFacade.updateDataConfiguration(getServiceContextAdministrador(), dataConfigurationDtoSession1);
        assertEquals(Long.valueOf(2), dataConfigurationDtoSession1AfterUpdate.getOptimisticLockingVersion());

        // Update dataConfiguration - session 2
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextAdministrador(), dataConfigurationDtoSession2);
            fail("Optimistic locking");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEquals(ServiceExceptionType.OPTIMISTIC_LOCKING.getCode(), e.getExceptionItems().get(0).getCode());
            assertNull(e.getExceptionItems().get(0).getMessageParameters());
        }

        // Update dataConfiguration - session 1
        dataConfigurationDtoSession1AfterUpdate.setConfigurationValue("new-value-second-update");
        DataConfigurationDto dataConfigurationDtoSession1AfterUpdate2 = commonMetadataServiceFacade.updateDataConfiguration(getServiceContextAdministrador(), dataConfigurationDtoSession1AfterUpdate);
        assertEquals(Long.valueOf(3), dataConfigurationDtoSession1AfterUpdate2.getOptimisticLockingVersion());
    }
}
