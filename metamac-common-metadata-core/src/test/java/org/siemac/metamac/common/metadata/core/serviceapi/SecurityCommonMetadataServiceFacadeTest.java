package org.siemac.metamac.common.metadata.core.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataDtoMocks;
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
public class SecurityCommonMetadataServiceFacadeTest extends CommonMetadataBaseTests implements CommonMetadataServiceFacadeTestBase {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    // --------------------------------------------------------------------------------
    // CONFIGURATIONS (metadata)
    // --------------------------------------------------------------------------------

    @Override
    @Test
    public void testFindConfigurationById() throws Exception {
        Long configurationId = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto()).getId();

        commonMetadataServiceFacade.findConfigurationById(getServiceContextAdministrador(), configurationId);
        commonMetadataServiceFacade.findConfigurationById(getServiceContextJefeNormalizacion(), configurationId);
        commonMetadataServiceFacade.findConfigurationById(getServiceContextTecnicoNormalizacion(), configurationId);
        commonMetadataServiceFacade.findConfigurationById(getServiceContextTecnicoApoyoNormalizacion(), configurationId);
        commonMetadataServiceFacade.findConfigurationById(getServiceContextTecnicoPlanificacion(), configurationId);
        commonMetadataServiceFacade.findConfigurationById(getServiceContextTecnicoApoyoPlanificacion(), configurationId);
        commonMetadataServiceFacade.findConfigurationById(getServiceContextTecnicoProduccion(), configurationId);
        commonMetadataServiceFacade.findConfigurationById(getServiceContextTecnicoApoyoProduccion(), configurationId);
        commonMetadataServiceFacade.findConfigurationById(getServiceContextTecnicoDifusion(), configurationId);
        commonMetadataServiceFacade.findConfigurationById(getServiceContextTecnicoApoyoDifusion(), configurationId);
    }

    @Override
    @Test
    public void testFindConfigurationByUrn() throws Exception {
        String configurationUrn = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto()).getUrn();

        commonMetadataServiceFacade.findConfigurationByUrn(getServiceContextAdministrador(), configurationUrn);
        commonMetadataServiceFacade.findConfigurationByUrn(getServiceContextJefeNormalizacion(), configurationUrn);
        commonMetadataServiceFacade.findConfigurationByUrn(getServiceContextTecnicoNormalizacion(), configurationUrn);
        commonMetadataServiceFacade.findConfigurationByUrn(getServiceContextTecnicoApoyoNormalizacion(), configurationUrn);
        commonMetadataServiceFacade.findConfigurationByUrn(getServiceContextTecnicoPlanificacion(), configurationUrn);
        commonMetadataServiceFacade.findConfigurationByUrn(getServiceContextTecnicoApoyoPlanificacion(), configurationUrn);
        commonMetadataServiceFacade.findConfigurationByUrn(getServiceContextTecnicoProduccion(), configurationUrn);
        commonMetadataServiceFacade.findConfigurationByUrn(getServiceContextTecnicoApoyoProduccion(), configurationUrn);
        commonMetadataServiceFacade.findConfigurationByUrn(getServiceContextTecnicoDifusion(), configurationUrn);
        commonMetadataServiceFacade.findConfigurationByUrn(getServiceContextTecnicoApoyoDifusion(), configurationUrn);

    }

    @Override
    @Test
    public void testFindAllConfigurations() throws Exception {
        commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador());
        commonMetadataServiceFacade.findAllConfigurations(getServiceContextJefeNormalizacion());
        commonMetadataServiceFacade.findAllConfigurations(getServiceContextTecnicoNormalizacion());
        commonMetadataServiceFacade.findAllConfigurations(getServiceContextTecnicoApoyoNormalizacion());
        commonMetadataServiceFacade.findAllConfigurations(getServiceContextTecnicoPlanificacion());
        commonMetadataServiceFacade.findAllConfigurations(getServiceContextTecnicoApoyoPlanificacion());
        commonMetadataServiceFacade.findAllConfigurations(getServiceContextTecnicoProduccion());
        commonMetadataServiceFacade.findAllConfigurations(getServiceContextTecnicoApoyoProduccion());
        commonMetadataServiceFacade.findAllConfigurations(getServiceContextTecnicoDifusion());
        commonMetadataServiceFacade.findAllConfigurations(getServiceContextTecnicoApoyoDifusion());
    }

    @Override
    @Test
    public void testCreateConfiguration() throws Exception {
        commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        commonMetadataServiceFacade.createConfiguration(getServiceContextJefeNormalizacion(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
        try {
            commonMetadataServiceFacade.createConfiguration(getServiceContextTecnicoNormalizacion(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.createConfiguration(getServiceContextTecnicoApoyoNormalizacion(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.createConfiguration(getServiceContextTecnicoPlanificacion(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.createConfiguration(getServiceContextTecnicoApoyoPlanificacion(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.createConfiguration(getServiceContextTecnicoProduccion(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.createConfiguration(getServiceContextTecnicoApoyoProduccion(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.createConfiguration(getServiceContextTecnicoDifusion(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.createConfiguration(getServiceContextTecnicoApoyoDifusion(), CommonMetadataDtoMocks.mockEnableConfigurationDto());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }

    }

    @Override
    @Test
    public void testUpdateConfiguration() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto());

        configurationDto = commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDto);
        configurationDto = commonMetadataServiceFacade.updateConfiguration(getServiceContextJefeNormalizacion(), configurationDto);
        try {
            commonMetadataServiceFacade.updateConfiguration(getServiceContextTecnicoNormalizacion(), configurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfiguration(getServiceContextTecnicoApoyoNormalizacion(), configurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfiguration(getServiceContextTecnicoPlanificacion(), configurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfiguration(getServiceContextTecnicoApoyoPlanificacion(), configurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfiguration(getServiceContextTecnicoProduccion(), configurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfiguration(getServiceContextTecnicoApoyoProduccion(), configurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfiguration(getServiceContextTecnicoDifusion(), configurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfiguration(getServiceContextTecnicoApoyoDifusion(), configurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Override
    @Test
    public void testDeleteConfiguration() throws Exception {

        // ADMINISTRADOR
        {
            Long configurationId = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto()).getId();
            commonMetadataServiceFacade.deleteConfiguration(getServiceContextAdministrador(), configurationId);
        }

        // JEFE_NORMALIZACION
        {
            Long configurationId = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto()).getId();
            commonMetadataServiceFacade.deleteConfiguration(getServiceContextJefeNormalizacion(), configurationId);
        }

        // Roles that can't delete
        {
            Long configurationId = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto()).getId();

            try {
                commonMetadataServiceFacade.deleteConfiguration(getServiceContextTecnicoNormalizacion(), configurationId);
                fail("security exception - operation not allowed");
            } catch (MetamacException e) {
                assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
            }
            try {
                commonMetadataServiceFacade.deleteConfiguration(getServiceContextTecnicoApoyoNormalizacion(), configurationId);
                fail("security exception - operation not allowed");
            } catch (MetamacException e) {
                assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
            }
            try {
                commonMetadataServiceFacade.deleteConfiguration(getServiceContextTecnicoPlanificacion(), configurationId);
                fail("security exception - operation not allowed");
            } catch (MetamacException e) {
                assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
            }
            try {
                commonMetadataServiceFacade.deleteConfiguration(getServiceContextTecnicoApoyoPlanificacion(), configurationId);
                fail("security exception - operation not allowed");
            } catch (MetamacException e) {
                assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
            }
            try {
                commonMetadataServiceFacade.deleteConfiguration(getServiceContextTecnicoProduccion(), configurationId);
                fail("security exception - operation not allowed");
            } catch (MetamacException e) {
                assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
            }
            try {
                commonMetadataServiceFacade.deleteConfiguration(getServiceContextTecnicoApoyoProduccion(), configurationId);
                fail("security exception - operation not allowed");
            } catch (MetamacException e) {
                assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
            }
            try {
                commonMetadataServiceFacade.deleteConfiguration(getServiceContextTecnicoDifusion(), configurationId);
                fail("security exception - operation not allowed");
            } catch (MetamacException e) {
                assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
            }
            try {
                commonMetadataServiceFacade.deleteConfiguration(getServiceContextTecnicoApoyoDifusion(), configurationId);
                fail("security exception - operation not allowed");
            } catch (MetamacException e) {
                assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
            }
        }

    }

    @Override
    @Test
    public void testUpdateConfigurationsStatus() throws Exception {
        Long configuration01 = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto()).getId();
        Long configuration02 = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto()).getId();

        // Update status
        List<Long> configurationIds = new ArrayList<Long>();
        configurationIds.add(configuration01);
        configurationIds.add(configuration02);

        commonMetadataServiceFacade.updateConfigurationsStatus(getServiceContextAdministrador(), configurationIds, CommonMetadataStatusEnum.DISABLED);
        commonMetadataServiceFacade.updateConfigurationsStatus(getServiceContextJefeNormalizacion(), configurationIds, CommonMetadataStatusEnum.DISABLED);

        try {
            commonMetadataServiceFacade.updateConfigurationsStatus(getServiceContextTecnicoNormalizacion(), configurationIds, CommonMetadataStatusEnum.DISABLED);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfigurationsStatus(getServiceContextTecnicoApoyoNormalizacion(), configurationIds, CommonMetadataStatusEnum.DISABLED);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfigurationsStatus(getServiceContextTecnicoPlanificacion(), configurationIds, CommonMetadataStatusEnum.DISABLED);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfigurationsStatus(getServiceContextTecnicoApoyoPlanificacion(), configurationIds, CommonMetadataStatusEnum.DISABLED);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfigurationsStatus(getServiceContextTecnicoProduccion(), configurationIds, CommonMetadataStatusEnum.DISABLED);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfigurationsStatus(getServiceContextTecnicoApoyoProduccion(), configurationIds, CommonMetadataStatusEnum.DISABLED);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfigurationsStatus(getServiceContextTecnicoDifusion(), configurationIds, CommonMetadataStatusEnum.DISABLED);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateConfigurationsStatus(getServiceContextTecnicoApoyoDifusion(), configurationIds, CommonMetadataStatusEnum.DISABLED);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Override
    public void testPublishExternallyConfiguration() throws Exception {
        Long configurationId = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto()).getId();

        commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextAdministrador(), configurationId);
        commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextJefeNormalizacion(), configurationId);

        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoNormalizacion(), configurationId);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoApoyoNormalizacion(), configurationId);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoPlanificacion(), configurationId);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoApoyoPlanificacion(), configurationId);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoProduccion(), configurationId);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoApoyoProduccion(), configurationId);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoDifusion(), configurationId);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoApoyoDifusion(), configurationId);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    // --------------------------------------------------------------------------------
    // DATA CONFIGURATIONS
    // --------------------------------------------------------------------------------

    @Override
    @Test
    public void testFindDataConfigurationById() throws Exception {
        Long dataConfigurationId = DATA_CONFIGURATION_01_ID;
        commonMetadataServiceFacade.findDataConfigurationById(getServiceContextAdministrador(), dataConfigurationId);
        commonMetadataServiceFacade.findDataConfigurationById(getServiceContextJefeNormalizacion(), dataConfigurationId);
        commonMetadataServiceFacade.findDataConfigurationById(getServiceContextTecnicoNormalizacion(), dataConfigurationId);
        commonMetadataServiceFacade.findDataConfigurationById(getServiceContextTecnicoApoyoNormalizacion(), dataConfigurationId);
        commonMetadataServiceFacade.findDataConfigurationById(getServiceContextTecnicoPlanificacion(), dataConfigurationId);
        commonMetadataServiceFacade.findDataConfigurationById(getServiceContextTecnicoApoyoPlanificacion(), dataConfigurationId);
        commonMetadataServiceFacade.findDataConfigurationById(getServiceContextTecnicoProduccion(), dataConfigurationId);
        commonMetadataServiceFacade.findDataConfigurationById(getServiceContextTecnicoApoyoProduccion(), dataConfigurationId);
        commonMetadataServiceFacade.findDataConfigurationById(getServiceContextTecnicoDifusion(), dataConfigurationId);
        commonMetadataServiceFacade.findDataConfigurationById(getServiceContextTecnicoApoyoDifusion(), dataConfigurationId);
    }

    @Override
    @Test
    public void testFindDataConfigurationByConfigurationKey() throws Exception {
        String dataConfigurationKey = DATA_CONFIGURATION_01_KEY;
        commonMetadataServiceFacade.findDataConfigurationByConfigurationKey(getServiceContextAdministrador(), dataConfigurationKey);
        commonMetadataServiceFacade.findDataConfigurationByConfigurationKey(getServiceContextJefeNormalizacion(), dataConfigurationKey);
        commonMetadataServiceFacade.findDataConfigurationByConfigurationKey(getServiceContextTecnicoNormalizacion(), dataConfigurationKey);
        commonMetadataServiceFacade.findDataConfigurationByConfigurationKey(getServiceContextTecnicoApoyoNormalizacion(), dataConfigurationKey);
        commonMetadataServiceFacade.findDataConfigurationByConfigurationKey(getServiceContextTecnicoPlanificacion(), dataConfigurationKey);
        commonMetadataServiceFacade.findDataConfigurationByConfigurationKey(getServiceContextTecnicoApoyoPlanificacion(), dataConfigurationKey);
        commonMetadataServiceFacade.findDataConfigurationByConfigurationKey(getServiceContextTecnicoProduccion(), dataConfigurationKey);
        commonMetadataServiceFacade.findDataConfigurationByConfigurationKey(getServiceContextTecnicoApoyoProduccion(), dataConfigurationKey);
        commonMetadataServiceFacade.findDataConfigurationByConfigurationKey(getServiceContextTecnicoDifusion(), dataConfigurationKey);
        commonMetadataServiceFacade.findDataConfigurationByConfigurationKey(getServiceContextTecnicoApoyoDifusion(), dataConfigurationKey);
    }

    @Override
    @Test
    public void testFindDataConfigurationsOfSystemProperties() throws Exception {
        commonMetadataServiceFacade.findDataConfigurationsOfSystemProperties(getServiceContextAdministrador());
        commonMetadataServiceFacade.findDataConfigurationsOfSystemProperties(getServiceContextJefeNormalizacion());
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemProperties(getServiceContextTecnicoNormalizacion());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemProperties(getServiceContextTecnicoApoyoNormalizacion());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemProperties(getServiceContextTecnicoPlanificacion());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemProperties(getServiceContextTecnicoApoyoPlanificacion());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemProperties(getServiceContextTecnicoProduccion());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemProperties(getServiceContextTecnicoApoyoProduccion());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemProperties(getServiceContextTecnicoDifusion());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemProperties(getServiceContextTecnicoApoyoDifusion());
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }


    @Override
    public void testFindDataConfigurationsOfSystemPropertiesByCondition() throws Exception {
        commonMetadataServiceFacade.findDataConfigurationsOfSystemPropertiesByCondition(getServiceContextAdministrador(), null);
        commonMetadataServiceFacade.findDataConfigurationsOfSystemPropertiesByCondition(getServiceContextJefeNormalizacion(), null);
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemPropertiesByCondition(getServiceContextTecnicoNormalizacion(), null);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemPropertiesByCondition(getServiceContextTecnicoApoyoNormalizacion(), null);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemPropertiesByCondition(getServiceContextTecnicoPlanificacion(), null);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemPropertiesByCondition(getServiceContextTecnicoApoyoPlanificacion(), null);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemPropertiesByCondition(getServiceContextTecnicoProduccion(), null);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemPropertiesByCondition(getServiceContextTecnicoApoyoProduccion(), null);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemPropertiesByCondition(getServiceContextTecnicoDifusion(), null);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.findDataConfigurationsOfSystemPropertiesByCondition(getServiceContextTecnicoApoyoDifusion(), null);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Override
    @Test
    public void testFindDataConfigurationsOfDefaultValues() throws Exception {
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValues(getServiceContextAdministrador());
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValues(getServiceContextJefeNormalizacion());
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValues(getServiceContextTecnicoNormalizacion());
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValues(getServiceContextTecnicoApoyoNormalizacion());
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValues(getServiceContextTecnicoPlanificacion());
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValues(getServiceContextTecnicoApoyoPlanificacion());
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValues(getServiceContextTecnicoProduccion());
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValues(getServiceContextTecnicoApoyoProduccion());
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValues(getServiceContextTecnicoDifusion());
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValues(getServiceContextTecnicoApoyoDifusion());
    }
    
    @Override
    @Test
    public void testFindDataConfigurationsOfDefaultValuesByCondition() throws Exception {
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValuesByCondition(getServiceContextAdministrador(), null);
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValuesByCondition(getServiceContextJefeNormalizacion(), null);
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValuesByCondition(getServiceContextTecnicoNormalizacion(), null);
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValuesByCondition(getServiceContextTecnicoApoyoNormalizacion(), null);
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValuesByCondition(getServiceContextTecnicoPlanificacion(), null);
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValuesByCondition(getServiceContextTecnicoApoyoPlanificacion(), null);
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValuesByCondition(getServiceContextTecnicoProduccion(), null);
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValuesByCondition(getServiceContextTecnicoApoyoProduccion(), null);
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValuesByCondition(getServiceContextTecnicoDifusion(), null);
        commonMetadataServiceFacade.findDataConfigurationsOfDefaultValuesByCondition(getServiceContextTecnicoApoyoDifusion(), null);
    }

    @Override
    @Test
    public void testUpdateDataConfiguration() throws Exception {
        // See:
        // - testUpdateDataConfigurationOfSystemProperty
        // - testUpdateDataConfigurationOfDefaultValue
    }

    @Test
    public void testUpdateDataConfigurationOfSystemProperty() throws Exception {
        DataConfigurationDto dataConfigurationDto = commonMetadataServiceFacade.findDataConfigurationsOfSystemProperties(getServiceContextAdministrador()).get(0);

        dataConfigurationDto = commonMetadataServiceFacade.updateDataConfiguration(getServiceContextAdministrador(), dataConfigurationDto);
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextJefeNormalizacion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoNormalizacion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoApoyoNormalizacion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoPlanificacion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoApoyoPlanificacion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoProduccion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoApoyoProduccion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoDifusion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoApoyoDifusion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Test
    public void testUpdateDataConfigurationOfDefaultValue() throws Exception {
        DataConfigurationDto dataConfigurationDto = commonMetadataServiceFacade.findDataConfigurationsOfDefaultValues(getServiceContextAdministrador()).get(0);

        dataConfigurationDto = commonMetadataServiceFacade.updateDataConfiguration(getServiceContextAdministrador(), dataConfigurationDto);
        dataConfigurationDto = commonMetadataServiceFacade.updateDataConfiguration(getServiceContextJefeNormalizacion(), dataConfigurationDto);
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoNormalizacion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoApoyoNormalizacion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoPlanificacion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoApoyoPlanificacion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoProduccion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoApoyoProduccion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoDifusion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.updateDataConfiguration(getServiceContextTecnicoApoyoDifusion(), dataConfigurationDto);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Override
    public void testFindDataConfigurationsByCondition() throws Exception {
        commonMetadataServiceFacade.findDataConfigurationsByCondition(getServiceContextAdministrador(), null);
        commonMetadataServiceFacade.findDataConfigurationsByCondition(getServiceContextJefeNormalizacion(), null);
        commonMetadataServiceFacade.findDataConfigurationsByCondition(getServiceContextTecnicoNormalizacion(), null);
        commonMetadataServiceFacade.findDataConfigurationsByCondition(getServiceContextTecnicoApoyoNormalizacion(), null);
        commonMetadataServiceFacade.findDataConfigurationsByCondition(getServiceContextTecnicoPlanificacion(), null);
        commonMetadataServiceFacade.findDataConfigurationsByCondition(getServiceContextTecnicoApoyoPlanificacion(), null);
        commonMetadataServiceFacade.findDataConfigurationsByCondition(getServiceContextTecnicoProduccion(), null);
        commonMetadataServiceFacade.findDataConfigurationsByCondition(getServiceContextTecnicoApoyoProduccion(), null);
        commonMetadataServiceFacade.findDataConfigurationsByCondition(getServiceContextTecnicoDifusion(), null);
        commonMetadataServiceFacade.findDataConfigurationsByCondition(getServiceContextTecnicoApoyoDifusion(), null);
    }

}
