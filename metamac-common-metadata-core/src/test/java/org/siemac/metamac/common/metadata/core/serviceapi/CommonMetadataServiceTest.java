package org.siemac.metamac.common.metadata.core.serviceapi;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.DataConfiguration;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionParameters;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataAsserts;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataDoMocks;
import org.siemac.metamac.common.test.utils.DirtyDatabase;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Spring based transactional test with DbUnit support.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/common-metadata/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CommonMetadataServiceTest extends CommonMetadataBaseTests implements CommonMetadataServiceTestBase {

    @Autowired
    protected CommonMetadataService          commonMetadataService;

    @Autowired
    private final PlatformTransactionManager transactionManager = null;

    // --------------------------------------------------------------------------------
    // CONFIGURATIONS (metadata)
    // --------------------------------------------------------------------------------

    @Override
    @Test
    @Transactional
    public void testFindConfigurationById() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration());
        assertNotNull(configuration);
        Configuration configurationRetrieved = commonMetadataService.findConfigurationById(getServiceContextAdministrador(), configuration.getId());
        assertNotNull(configurationRetrieved);
        CommonMetadataAsserts.assertEqualsConfiguration(configuration, configurationRetrieved);
    }

    @Override
    @Test
    @Transactional
    public void testFindConfigurationByUrn() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration());
        assertNotNull(configuration);
        Configuration configurationRetrieved = commonMetadataService.findConfigurationByUrn(getServiceContextAdministrador(), configuration.getUrn());
        assertNotNull(configurationRetrieved);
        CommonMetadataAsserts.assertEqualsConfiguration(configuration, configurationRetrieved);
    }

    @Test
    @Transactional
    public void testFindConfigurationByUrnNotExists() throws Exception {
        String urn = "not_exists";

        expectedMetamacException(new MetamacException(ServiceExceptionType.CONFIGURATION_NOT_FOUND_URN, urn));
        commonMetadataService.findConfigurationByUrn(getServiceContextAdministrador(), urn);
    }

    @Override
    @Test
    @Transactional
    public void testFindAllConfigurations() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration());
        assertNotNull(configuration);

        List<Configuration> configurations = commonMetadataService.findAllConfigurations(getServiceContextAdministrador());
        assertTrue(!configurations.isEmpty());
    }

    @Override
    @Test
    @Transactional
    public void testCreateConfiguration() throws Exception {
        Configuration configurationExpected = CommonMetadataDoMocks.createEnableConfiguration();

        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), configurationExpected);
        assertNotNull(configuration);
        assertEquals("urn:siemac:org.siemac.metamac.infomodel.commonmetadata.CommonMetadata=" + configuration.getCode(), configuration.getUrn());
        CommonMetadataAsserts.assertEqualsConfiguration(configurationExpected, configuration);
        assertEquals(false, configuration.isExternallyPublished());
    }

    @Test
    @Transactional
    public void testCreateConfigurationWithIncorrectCode() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.CONFIGURATION_CODE));

        Configuration configuration = CommonMetadataDoMocks.createEnableConfiguration();
        configuration.setCode("ISTAC@1");

        commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
    }

    @Test
    @Transactional
    public void testCreateConfigurationInitializeStatus() throws Exception {
        Configuration configuration = CommonMetadataDoMocks.createEnableConfiguration();
        configuration.setStatus(null);

        configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
        assertEquals(CommonMetadataStatusEnum.ENABLED, configuration.getStatus());
    }

    @Test
    @Transactional
    public void testCreateConfigurationBase() throws Exception {
        Configuration configurationExpected = CommonMetadataDoMocks.createConfigurationBase();

        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), configurationExpected);
        assertNotNull(configuration);
        CommonMetadataAsserts.assertEqualsConfiguration(configurationExpected, configuration);
    }

    @Test
    @Transactional
    public void testCreateConfigurationErrorContactRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.CONFIGURATION_CONTACT));

        Configuration configuration = CommonMetadataDoMocks.createEnableConfiguration();
        configuration.setContact(null);
        commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
    }

    @Test
    @Transactional
    public void testCreateConfigurationErrorLicenseRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.CONFIGURATION_LICENSE));

        Configuration configuration = CommonMetadataDoMocks.createEnableConfiguration();
        configuration.setLicense(null);
        commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
    }

    @Test
    @Transactional
    public void testCreateConfigurationCodeDuplicated() throws Exception {
        String code = "ISTAC";
        expectedMetamacException(new MetamacException(ServiceExceptionType.CONFIGURATION_ALREADY_EXIST_CODE_DUPLICATED, code));

        // Create first configuration
        {
            Configuration configuration = CommonMetadataDoMocks.createEnableConfiguration();
            configuration.setCode(code);
            configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
            assertNotNull(configuration);
        }

        // Create second configuration
        {
            Configuration configuration = CommonMetadataDoMocks.createEnableConfiguration();
            configuration.setCode(code);
            commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
        }

    }

    @Override
    @Test
    @Transactional
    public void testDeleteConfiguration() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration());
        assertNotNull(configuration);

        List<Configuration> configurations = commonMetadataService.findAllConfigurations(getServiceContextAdministrador());
        commonMetadataService.deleteConfiguration(getServiceContextAdministrador(), configuration.getId());

        assertTrue(commonMetadataService.findAllConfigurations(getServiceContextAdministrador()).size() < configurations.size());
    }

    @Override
    @Test
    @Transactional
    public void testFindConfigurationByCondition() throws Exception {
        String code = "conf-ISTAC";

        Configuration configuration = CommonMetadataDoMocks.createEnableConfiguration();
        configuration.setCode(code);
        configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
        assertNotNull(configuration);

        List<ConditionalCriteria> condition = criteriaFor(Configuration.class).withProperty(org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties.code())
                .ignoreCaseLike("%" + code + "%").build();
        List<Configuration> configurations = commonMetadataService.findConfigurationByCondition(getServiceContextAdministrador(), condition);
        assertEquals(1, configurations.size());

    }

    @Override
    @Test
    @Transactional
    public void testUpdateConfiguration() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration());
        assertNotNull(configuration);

        // Legal Acts
        InternationalString legalActs = new InternationalString();
        LocalisedString legalActs_es = new LocalisedString();
        legalActs_es.setLabel("new es Legal Acts");
        legalActs_es.setLocale("es");
        legalActs.addText(legalActs_es);
        configuration.setLegalActs(legalActs);

        Configuration updatedConfiguration = commonMetadataService.updateConfiguration(getServiceContextAdministrador(), configuration);
        assertNotNull(configuration);
        CommonMetadataAsserts.assertEqualsConfiguration(configuration, updatedConfiguration);

    }

    @Test
    @Transactional
    public void testUpdateConfigurationErrorContactRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.CONFIGURATION_CONTACT));

        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);

        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration());
        assertNotNull(configuration);

        transactionManager.commit(status);

        configuration.setContact(null);
        commonMetadataService.updateConfiguration(getServiceContextAdministrador(), configuration);
    }

    @Test
    @Transactional
    public void testUpdateConfigurationErrorLicenseRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.CONFIGURATION_LICENSE));

        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);

        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration());
        assertNotNull(configuration);

        transactionManager.commit(status);

        configuration.setLicense(null);
        commonMetadataService.updateConfiguration(getServiceContextAdministrador(), configuration);
    }

    @Test
    @DirtyDatabase
    public void testUpdateConfigurationStatusRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.CONFIGURATION_STATUS));

        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);

        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration());
        assertNotNull(configuration);

        transactionManager.commit(status);

        configuration.setStatus(null);
        commonMetadataService.updateConfiguration(getServiceContextAdministrador(), configuration);
    }

    @Test
    @Transactional
    public void testUpdateConfigurationStatus() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration());
        assertNotNull(configuration);

        configuration.setCode("Conf-modified");
        configuration.setStatus(CommonMetadataStatusEnum.DISABLED);

        Configuration updatedConfiguration = commonMetadataService.updateConfiguration(getServiceContextAdministrador(), configuration);

        assertNotNull(configuration);
        CommonMetadataAsserts.assertEqualsConfiguration(configuration, updatedConfiguration);

    }

    @Override
    @Test
    @Transactional
    public void testUpdateConfigurationsStatus() throws Exception {

        // Create configurations
        Long configuration01 = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration()).getId();
        Long configuration02 = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration()).getId();

        // Update status
        List<Long> configurationIds = new ArrayList<Long>();
        configurationIds.add(configuration01);
        configurationIds.add(configuration02);

        List<Configuration> updatedConfigurations = commonMetadataService.updateConfigurationsStatus(getServiceContextAdministrador(), configurationIds, CommonMetadataStatusEnum.DISABLED);

        // Check status
        for (Configuration configuration : updatedConfigurations) {
            assertEquals(CommonMetadataStatusEnum.DISABLED, configuration.getStatus());
        }
    }

    @Test
    @Transactional
    public void testUpdateConfigurationsStatusConfigurationIdsRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.CONFIGURATION_IDS));
        commonMetadataService.updateConfigurationsStatus(getServiceContextAdministrador(), null, CommonMetadataStatusEnum.DISABLED);
    }

    @Test
    @Transactional
    public void testUpdateConfigurationsStatusStatusRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.STATUS));

        // Create configurations
        Long configuration01 = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration()).getId();
        Long configuration02 = commonMetadataService.createConfiguration(getServiceContextAdministrador(), CommonMetadataDoMocks.createEnableConfiguration()).getId();

        // Update status
        List<Long> configurationIds = new ArrayList<Long>();
        configurationIds.add(configuration01);
        configurationIds.add(configuration02);

        commonMetadataService.updateConfigurationsStatus(getServiceContextAdministrador(), configurationIds, null);
    }

    // --------------------------------------------------------------------------------
    // DATA CONFIGURATION
    // --------------------------------------------------------------------------------

    @Override
    @Test
    @Transactional
    public void testFindDataConfigurationByConfigurationKey() throws Exception {
        DataConfiguration dataConfiguration = commonMetadataService.findDataConfigurationByConfigurationKey(getServiceContextAdministrador(), DATA_CONFIGURATION_01_KEY);
        assertNotNull(dataConfiguration);
    }

    @Override
    @Test
    @Transactional
    public void testUpdateDataConfiguration() throws Exception {
        DataConfiguration dataConfiguration = commonMetadataService.findDataConfigurationById(getServiceContextAdministrador(), DATA_CONFIGURATION_01_ID);
        dataConfiguration.setConfigurationValue("new-value");
        DataConfiguration dataConfigurationAfter = commonMetadataService.updateDataConfiguration(getServiceContextAdministrador(), dataConfiguration);
        assertEquals(dataConfiguration.getConfigurationValue(), dataConfigurationAfter.getConfigurationValue());
    }

    @Override
    @Test
    @Transactional
    public void testFindDataConfigurationsOfSystemProperties() throws Exception {
        int systemProperties = commonMetadataService.findDataConfigurationsOfSystemProperties(getServiceContextAdministrador()).size();
        assertEquals(NUMBER_DATA_CONFIGURATIONS_SYSTEM_PROPERTIES, systemProperties);
    }

    @Override
    @Test
    @Transactional
    public void testFindDataConfigurationsOfDefaultValues() throws Exception {
        int defaultValues = commonMetadataService.findDataConfigurationsOfDefaultValues(getServiceContextAdministrador()).size();
        assertEquals(NUMBER_DATA_CONFIGURATIONS_DEFAULT_VALUES, defaultValues);
    }

    @Override
    @Test
    @Transactional
    public void testFindDataConfigurationById() throws Exception {
        DataConfiguration dataConfiguration = commonMetadataService.findDataConfigurationById(getServiceContextAdministrador(), DATA_CONFIGURATION_01_ID);
        assertNotNull(dataConfiguration);
    }
}
