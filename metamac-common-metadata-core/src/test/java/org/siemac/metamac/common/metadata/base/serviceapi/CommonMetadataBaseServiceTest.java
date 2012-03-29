package org.siemac.metamac.common.metadata.base.serviceapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.siemac.metamac.common.test.MetamacBaseTests;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Spring based transactional test with DbUnit support.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oracle/applicationContext-test.xml"})
public class CommonMetadataBaseServiceTest extends MetamacBaseTests implements CommonMetadataBaseServiceTestBase {

    @Autowired
    protected CommonMetadataBaseService commonMetadataBaseService;

    private final ServiceContext        serviceContext = new ServiceContext("system", "123456", "junit");

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
     * PRIVATE UTILS
     **************************************************************************/

    private Configuration createConfiguration() {
        Configuration configuration = new Configuration();
        // Name
        configuration.setName("configuration-0123456789");
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
        // TODO
        return configuration;
    }

    @Override
    protected String getDataSetFile() {
        return "dbunit/CommonMetadataBaseServiceTest.xml";
    }

    @Override
    protected List<String> getTablesToRemoveContent() {
        List<String> tables = new ArrayList<String>();
        tables.add("TB_CONFIGURATIONS");
        tables.add("TB_EXTERNAL_ITEMS");
        tables.add("TB_LOCALISED_STRINGS");
        tables.add("TB_INTERNATIONAL_STRINGS");
        return tables;
    }

    @Override
    protected List<String> getSequencesToRestart() {
        List<String> sequences = new ArrayList<String>();
        sequences.add("SEQ_EXTERNAL_ITEMS");
        sequences.add("SEQ_L10NSTRS");
        sequences.add("SEQ_I18NSTRS");
        sequences.add("SEQ_CONFIGURATION");
        return sequences;
    }

}
