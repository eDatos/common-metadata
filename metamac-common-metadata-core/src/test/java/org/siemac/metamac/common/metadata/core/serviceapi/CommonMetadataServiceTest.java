package org.siemac.metamac.common.metadata.core.serviceapi;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionParameters;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.common.metadata.core.serviceapi.utils.CommonMetadataAsserts;
import org.siemac.metamac.common.test.utils.DirtyDatabase;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
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
    protected CommonMetadataService    commonMetadataService;

    @Autowired
    private PlatformTransactionManager transactionManager = null;

    @Test
    @Transactional
    public void testFindConfigurationById() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createEnableConfiguration());
        assertNotNull(configuration);
        Configuration configurationRetrieved = commonMetadataService.findConfigurationById(getServiceContextAdministrador(), configuration.getId());
        assertNotNull(configurationRetrieved);
        CommonMetadataAsserts.assertEqualsConfiguration(configuration, configurationRetrieved);
    }

    @Test
    @Transactional
    public void testFindConfigurationByUrn() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createEnableConfiguration());
        assertNotNull(configuration);
        Configuration configurationRetrieved = commonMetadataService.findConfigurationByUrn(getServiceContextAdministrador(), configuration.getUrn());
        assertNotNull(configurationRetrieved);
        CommonMetadataAsserts.assertEqualsConfiguration(configuration, configurationRetrieved);
    }

    @Test
    @Transactional
    public void testFindConfigurationByUrnNotExists() throws Exception {
        String urn = "not_exists";

        try {
            commonMetadataService.findConfigurationByUrn(getServiceContextAdministrador(), urn);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.CONFIGURATION_NOT_FOUND.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Test
    @Transactional
    public void testFindAllConfigurations() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createEnableConfiguration());
        assertNotNull(configuration);

        List<Configuration> configurations = commonMetadataService.findAllConfigurations(getServiceContextAdministrador());
        assertTrue(!configurations.isEmpty());
    }

    @Test
    @Transactional
    public void testCreateConfiguration() throws Exception {
        Configuration configurationExpected = createEnableConfiguration();

        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), configurationExpected);
        assertNotNull(configuration);
        assertEquals("urn:siemac:org.siemac.metamac.infomodel.commonmetadata.CommonMetadata=" + configuration.getCode(), configuration.getUrn());
        CommonMetadataAsserts.assertEqualsConfiguration(configurationExpected, configuration);
    }

    @Test
    @Transactional
    public void testCreateConfigurationWithIncorrectCode() throws Exception {
        Configuration configuration = createEnableConfiguration();
        configuration.setCode("1ISTAC");

        try {
            commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.METADATA_INCORRECT.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Test
    @Transactional
    public void testCreateConfigurationRequiredStatus() throws Exception {
        Configuration configuration = createEnableConfiguration();
        configuration.setStatus(null);

        try {
            commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.METADATA_REQUIRED.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }

    @Test
    @Transactional
    public void testCreateConfigurationBase() throws Exception {
        Configuration configurationExpected = createConfigurationBase();

        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), configurationExpected);
        assertNotNull(configuration);
        CommonMetadataAsserts.assertEqualsConfiguration(configurationExpected, configuration);
    }

    @Test
    @Transactional
    public void testCreateConfigurationCodeDuplicated() throws Exception {
        // Create first configuration
        {
            Configuration configuration = createEnableConfiguration();
            configuration.setCode("ISTAC");
            configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
            assertNotNull(configuration);
        }

        // Create second configuration
        {
            Configuration configuration = createEnableConfiguration();
            configuration.setCode("ISTAC");

            try {
                commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
            } catch (MetamacException e) {
                assertEquals(ServiceExceptionType.CONFIGURATION_ALREADY_EXIST_CODE_DUPLICATED.getCode(), e.getExceptionItems().get(0).getCode());
            }
        }

    }

    @Test
    @Transactional
    public void testDeleteConfiguration() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createEnableConfiguration());
        assertNotNull(configuration);

        List<Configuration> configurations = commonMetadataService.findAllConfigurations(getServiceContextAdministrador());
        commonMetadataService.deleteConfiguration(getServiceContextAdministrador(), configuration.getId());

        assertTrue(commonMetadataService.findAllConfigurations(getServiceContextAdministrador()).size() < configurations.size());
    }

    @Test
    @Transactional
    public void testFindConfigurationByCondition() throws Exception {
        String code = "conf-ISTAC";

        Configuration configuration = createEnableConfiguration();
        configuration.setCode(code);
        configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
        assertNotNull(configuration);

        List<ConditionalCriteria> condition = criteriaFor(Configuration.class).withProperty(org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties.code())
                .ignoreCaseLike("%" + code + "%").build();
        List<Configuration> configurations = commonMetadataService.findConfigurationByCondition(getServiceContextAdministrador(), condition);
        assertEquals(1, configurations.size());

    }

    @Test
    @Transactional
    public void testUpdateConfiguration() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createEnableConfiguration());
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
    public void testUpdateConfigurationCodeUnmodifiable() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createEnableConfiguration());
        assertNotNull(configuration);

        configuration.setCode("Conf-modified");

        try {
            commonMetadataService.updateConfiguration(getServiceContextAdministrador(), configuration);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.METADATA_UNMODIFIABLE.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(ServiceExceptionParameters.CONFIGURATION_CODE, e.getExceptionItems().get(0).getMessageParameters()[0]);
        }

    }

    @Test
    @DirtyDatabase
    public void testUpdateConfigurationStatusRequired() throws Exception {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);

        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createEnableConfiguration());
        assertNotNull(configuration);

        transactionManager.commit(status);

        configuration.setStatus(null);
        try {
            commonMetadataService.updateConfiguration(getServiceContextAdministrador(), configuration);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.METADATA_REQUIRED.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(ServiceExceptionParameters.CONFIGURATION_STATUS, e.getExceptionItems().get(0).getMessageParameters()[0]);
        }
    }

    @Test
    @Transactional
    public void testUpdateConfigurationStatus() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createEnableConfiguration());
        assertNotNull(configuration);

        configuration.setCode("Conf-modified");
        configuration.setStatus(CommonMetadataStatusEnum.DISABLED);

        Configuration updatedConfiguration = commonMetadataService.updateConfiguration(getServiceContextAdministrador(), configuration);

        assertNotNull(configuration);
        CommonMetadataAsserts.assertEqualsConfiguration(configuration, updatedConfiguration);

    }

    @Test
    @Transactional
    public void testUpdateConfigurationsStatus() throws Exception {

        // Create configurations
        Long configuration01 = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createEnableConfiguration()).getId();
        Long configuration02 = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createEnableConfiguration()).getId();

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

        try {
            commonMetadataService.updateConfigurationsStatus(getServiceContextAdministrador(), null, CommonMetadataStatusEnum.DISABLED);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.PARAMETER_REQUIRED.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(ServiceExceptionParameters.CONFIGURATION_IDS, e.getExceptionItems().get(0).getMessageParameters()[0]);
        }
    }

    @Test
    @Transactional
    public void testUpdateConfigurationsStatusStatusRequired() throws Exception {

        // Create configurations
        Long configuration01 = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createEnableConfiguration()).getId();
        Long configuration02 = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createEnableConfiguration()).getId();

        // Update status
        List<Long> configurationIds = new ArrayList<Long>();
        configurationIds.add(configuration01);
        configurationIds.add(configuration02);

        try {
            commonMetadataService.updateConfigurationsStatus(getServiceContextAdministrador(), configurationIds, null);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.PARAMETER_REQUIRED.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(ServiceExceptionParameters.STATUS, e.getExceptionItems().get(0).getMessageParameters()[0]);
        }
    }

    // ------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // ------------------------------------------------------------------------------------

    private Configuration createEnableConfiguration() {
        Configuration configuration = new Configuration();

        // Code
        configuration.setCode("configuration-" + RandomStringUtils.randomAlphabetic(5));

        // Legal Acts
        InternationalString legalActs = new InternationalString();
        LocalisedString legalActs_es = new LocalisedString();
        legalActs_es.setLabel("ESPAÑOL Legal Acts");
        legalActs_es.setLocale("es");
        LocalisedString legalActs_en = new LocalisedString();
        legalActs_en.setLabel("ENGLISH Legal Acts");
        legalActs_en.setLocale("en");
        legalActs.addText(legalActs_es);
        legalActs.addText(legalActs_en);
        configuration.setLegalActs(legalActs);

        // Data Sharing
        InternationalString dataSharing = new InternationalString();
        LocalisedString dataSharing_es = new LocalisedString();
        dataSharing_es.setLabel("ESPAÑOL Data Sharing");
        dataSharing_es.setLocale("es");
        LocalisedString dataSharing_en = new LocalisedString();
        dataSharing_en.setLabel("ENGLISH Data Sharing");
        dataSharing_en.setLocale("en");
        dataSharing.addText(dataSharing_es);
        dataSharing.addText(dataSharing_en);
        configuration.setDataSharing(dataSharing);

        // Confidentiality Policy
        InternationalString confidentialityPolicy = new InternationalString();
        LocalisedString confidentialityPolicy_es = new LocalisedString();
        confidentialityPolicy_es.setLabel("ESPAÑOL Confidentiality Policy");
        confidentialityPolicy_es.setLocale("es");
        LocalisedString confidentialityPolicy_en = new LocalisedString();
        confidentialityPolicy_en.setLabel("ENGLISH Confidentiality Policy");
        confidentialityPolicy_en.setLocale("en");
        confidentialityPolicy.addText(confidentialityPolicy_es);
        confidentialityPolicy.addText(confidentialityPolicy_en);
        configuration.setConfPolicy(confidentialityPolicy);

        // Confidentiality Data Treatment
        InternationalString confidentialityDataTreatment = new InternationalString();
        LocalisedString confidentialityDataTreatment_es = new LocalisedString();
        confidentialityDataTreatment_es.setLabel("ESPAÑOL Confidentiality Policy");
        confidentialityDataTreatment_es.setLocale("es");
        LocalisedString confidentialityDataTreatment_en = new LocalisedString();
        confidentialityDataTreatment_en.setLabel("ENGLISH Confidentiality Policy");
        confidentialityDataTreatment_en.setLocale("en");
        confidentialityDataTreatment.addText(confidentialityDataTreatment_es);
        confidentialityDataTreatment.addText(confidentialityDataTreatment_en);
        configuration.setConfDataTreatment(confidentialityDataTreatment);

        // Contact
        configuration.setContact(new ExternalItem("CONTACT-CODE", "CONTACT-URI", "CONTACT-URN", TypeExternalArtefactsEnum.AGENCY, null, "CONTACT-MANAGEMENT_APP_URL"));

        // Status
        configuration.setStatus(CommonMetadataStatusEnum.ENABLED);

        return configuration;
    }

    private Configuration createConfigurationBase() {
        Configuration configuration = new Configuration();

        // Code
        configuration.setCode("configuration-0123456789");

        // Status
        configuration.setStatus(CommonMetadataStatusEnum.ENABLED);

        return configuration;
    }
}
