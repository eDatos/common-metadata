package org.siemac.metamac.common.metadata.core.serviceapi;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
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
@ContextConfiguration(locations = {"classpath:oracle/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class CommonMetadataServiceTest extends CommonMetadataBaseTests implements CommonMetadataServiceTestBase {

    @Autowired
    protected CommonMetadataService commonMetadataService;

    @Test
    public void testFindConfigurationById() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createConfiguration());
        assertNotNull(configuration);
        Configuration configurationRetrieved = commonMetadataService.findConfigurationById(getServiceContextAdministrador(), configuration.getId());
        assertNotNull(configurationRetrieved);
        assertTrue(configuration.equals(configurationRetrieved));
    }

    @Test
    public void testFindAllConfigurations() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createConfiguration());
        assertNotNull(configuration);

        List<Configuration> configurations = commonMetadataService.findAllConfigurations(getServiceContextAdministrador());
        assertTrue(!configurations.isEmpty());
    }

    @Test
    public void testCreateConfiguration() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createConfiguration());
        assertNotNull(configuration);
    }
    
    @Test
    public void testCreateConfigurationWithIncorrectCode() throws Exception {
        Configuration configuration = createConfiguration();
        configuration.setCode("1ISTAC");
        
        try {
            commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
        } catch (MetamacException e) {
            assertEquals(ServiceExceptionType.METADATA_INCORRECT.getCode(), e.getExceptionItems().get(0).getCode());
        }
    }
    
    @Test
    public void testCreateConfigurationBase() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createConfigurationBase());
        assertNotNull(configuration);
    }
    
    @Test
    public void testCreateConfigurationWithIncorrectUrls() throws Exception {
        try {
            commonMetadataService.createConfiguration(getServiceContextAdministrador(), createConfigurationWithIncorrectUrls());
        } catch (MetamacException e) {
            assertEquals(4, e.getExceptionItems().size());
            assertEquals(ServiceExceptionType.METADATA_INVALID_URL.getCode(), e.getExceptionItems().get(0).getCode());
            assertEquals(ServiceExceptionType.METADATA_INVALID_URL.getCode(), e.getExceptionItems().get(1).getCode());
            assertEquals(ServiceExceptionType.METADATA_INVALID_URL.getCode(), e.getExceptionItems().get(2).getCode());
            assertEquals(ServiceExceptionType.METADATA_INVALID_URL.getCode(), e.getExceptionItems().get(3).getCode());
        }
    }

    @Test
    public void testCreateConfigurationCodeDuplicated() throws Exception {
        // Create first configuration
        {
            Configuration configuration = createConfiguration();
            configuration.setCode("ISTAC");
            configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
            assertNotNull(configuration);
        }

        // Create second configuration
        {
            Configuration configuration = createConfiguration();
            configuration.setCode("ISTAC");
            
            try {
                commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
            } catch (MetamacException e) {
                assertEquals(ServiceExceptionType.CONFIGURATION_ALREADY_EXIST_CODE_DUPLICATED.getCode(), e.getExceptionItems().get(0).getCode());
            }
        }

    }

    @Test
    public void testDeleteConfiguration() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createConfiguration());
        assertNotNull(configuration);

        List<Configuration> configurations = commonMetadataService.findAllConfigurations(getServiceContextAdministrador());
        commonMetadataService.deleteConfiguration(getServiceContextAdministrador(), configuration.getId());
        assertTrue(commonMetadataService.findAllConfigurations(getServiceContextAdministrador()).size() < configurations.size());
    }

    @Test
    public void testFindConfigurationByCondition() throws Exception {
        String code = "conf-ISTAC";

        Configuration configuration = createConfiguration();
        configuration.setCode(code);
        configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), configuration);
        assertNotNull(configuration);

        List<ConditionalCriteria> condition = criteriaFor(Configuration.class).withProperty(org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties.code())
                .ignoreCaseLike("%" + code + "%").build();
        List<Configuration> configurations = commonMetadataService.findConfigurationByCondition(getServiceContextAdministrador(), condition);
        assertEquals(1, configurations.size());

    }

    @Test
    public void testUpdateConfiguration() throws Exception {
        Configuration configuration = commonMetadataService.createConfiguration(getServiceContextAdministrador(), createConfiguration());
        assertNotNull(configuration);

        configuration.setCode("Conf-modified");
        configuration = commonMetadataService.updateConfiguration(getServiceContextAdministrador(), configuration);
        assertNotNull(configuration);

    }

    // ------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // ------------------------------------------------------------------------------------

    private Configuration createConfiguration() {
        Configuration configuration = new Configuration();
        // Code
        configuration.setCode("configuration-0123456789");
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
        // Legal Acts URL
        configuration.setLegalActsUrl("http://legalActs.com");
        // Data Sharing URL
        configuration.setDataSharingUrl("http://dataSharing.com");
        // Confidentiality Policy URL
        configuration.setConfPolicyUrl("http://confidentialityPolicy.com");
        // Confidentiality Data Treatment URL
        configuration.setConfDataTreatmentUrl("http://confidentialityDataTreatment.com");
        // Contact
        // TODO: It's an externalItem from organisation schemes
        return configuration;
    }
    
    
    private Configuration createConfigurationBase() {
        Configuration configuration = new Configuration();
        // Code
        configuration.setCode("configuration-0123456789");
        
        return configuration;
    }
    
    private Configuration createConfigurationWithIncorrectUrls() {
        Configuration configuration = new Configuration();
        // Code
        configuration.setCode("configuration-0123456789");
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
        // Legal Acts URL
        configuration.setLegalActsUrl("httpa://legalActs.com");
        // Data Sharing URL
        configuration.setDataSharingUrl("incorrectUrl");
        // Confidentiality Policy URL
        configuration.setConfPolicyUrl("ahttp://confidentialityPolicy.com");
        // Confidentiality Data Treatment URL
        configuration.setConfDataTreatmentUrl("http:a//confidentialityDataTreatment.com");
        // Contact
        // TODO: It's an externalItem from organisation schemes
        return configuration;
    }

}
