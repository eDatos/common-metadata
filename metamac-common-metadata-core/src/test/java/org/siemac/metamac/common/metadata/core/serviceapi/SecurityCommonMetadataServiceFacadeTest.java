package org.siemac.metamac.common.metadata.core.serviceapi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
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
    protected CommonMetadataServiceFacade commonMetadataServiceFacade;

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
        String configurationUrn = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), CommonMetadataDtoMocks.mockEnableConfigurationDto()).getUrn();

        commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextAdministrador(), configurationUrn);
        commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextJefeNormalizacion(), configurationUrn);

        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoNormalizacion(), configurationUrn);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoApoyoNormalizacion(), configurationUrn);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoPlanificacion(), configurationUrn);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoApoyoPlanificacion(), configurationUrn);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoProduccion(), configurationUrn);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoApoyoProduccion(), configurationUrn);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoDifusion(), configurationUrn);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        try {
            commonMetadataServiceFacade.publishExternallyConfiguration(getServiceContextTecnicoApoyoDifusion(), configurationUrn);
            fail("security exception - operation not allowed");
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED.getCode(), e.getExceptionItems().get(0).getCode());
        }
        
    }

}
