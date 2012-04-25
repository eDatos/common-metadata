package org.siemac.metamac.common.metadata.core.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
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
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), createConfigurationDto());
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
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), createConfigurationDto());
        assertNotNull(configurationDto);
        
        List<ConfigurationDto> configurationDtos = commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador());
        assertTrue(!configurationDtos.isEmpty());

    }

    @Test
    public void testCreateConfiguration() throws MetamacException {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), createConfigurationDto());
        assertNotNull(configurationDto);
    }
    
    @Test
    public void testUpdateConfiguration() throws Exception {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), createConfigurationDto());
        assertNotNull(configurationDto);
        
        configurationDto.setCode("Conf-modified");
        configurationDto = commonMetadataServiceFacade.updateConfiguration(getServiceContextAdministrador(), configurationDto);
        assertNotNull(configurationDto);
    }

    @Test
    public void testDeleteConfiguration() throws MetamacException {
        ConfigurationDto configurationDto = commonMetadataServiceFacade.createConfiguration(getServiceContextAdministrador(), createConfigurationDto());
        assertNotNull(configurationDto);
        int numberConfigurations = commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador()).size();
        commonMetadataServiceFacade.deleteConfiguration(getServiceContextAdministrador(), configurationDto.getId());
        assertTrue(commonMetadataServiceFacade.findAllConfigurations(getServiceContextAdministrador()).size() == (numberConfigurations - 1));
    }


    // ------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // ------------------------------------------------------------------------------------

    private ConfigurationDto createConfigurationDto() {
        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setCode("configuration-0123456789");
        // Legal Acts
        InternationalStringDto legalActs = new InternationalStringDto();
        LocalisedStringDto legalActs_es = new LocalisedStringDto();
        legalActs_es.setLabel("ESPAÑOL Legal Acts");
        legalActs_es.setLocale("es");
        LocalisedStringDto legalActs_en = new LocalisedStringDto();
        legalActs_en.setLabel("ENGLISH Legal Acts");
        legalActs_en.setLocale("en");
        legalActs.addText(legalActs_es);
        legalActs.addText(legalActs_en);
        configurationDto.setLegalActs(legalActs);
        // Data Sharing
        InternationalStringDto dataSharing = new InternationalStringDto();
        LocalisedStringDto dataSharing_es = new LocalisedStringDto();
        dataSharing_es.setLabel("ESPAÑOL Data Sharing");
        dataSharing_es.setLocale("es");
        LocalisedStringDto dataSharing_en = new LocalisedStringDto();
        dataSharing_en.setLabel("ENGLISH Data Sharing");
        dataSharing_en.setLocale("en");
        dataSharing.addText(dataSharing_es);
        dataSharing.addText(dataSharing_en);
        configurationDto.setDataSharing(dataSharing);
        // Confidentiality Policy
        InternationalStringDto confidentialityPolicy = new InternationalStringDto();
        LocalisedStringDto confidentialityPolicy_es = new LocalisedStringDto();
        confidentialityPolicy_es.setLabel("ESPAÑOL Confidentiality Policy");
        confidentialityPolicy_es.setLocale("es");
        LocalisedStringDto confidentialityPolicy_en = new LocalisedStringDto();
        confidentialityPolicy_en.setLabel("ENGLISH Confidentiality Policy");
        confidentialityPolicy_en.setLocale("en");
        confidentialityPolicy.addText(confidentialityPolicy_es);
        confidentialityPolicy.addText(confidentialityPolicy_en);
        configurationDto.setConfPolicy(confidentialityPolicy);
        // Confidentiality Data Treatment
        InternationalStringDto confidentialityDataTreatment = new InternationalStringDto();
        LocalisedStringDto confidentialityDataTreatment_es = new LocalisedStringDto();
        confidentialityDataTreatment_es.setLabel("ESPAÑOL Confidentiality Data Treatment");
        confidentialityDataTreatment_es.setLocale("es");
        LocalisedStringDto confidentialityDataTreatment_en = new LocalisedStringDto();
        confidentialityDataTreatment_en.setLabel("ENGLISH Confidentiality Data Treatment");
        confidentialityDataTreatment_en.setLocale("en");
        confidentialityDataTreatment.addText(confidentialityDataTreatment_es);
        confidentialityDataTreatment.addText(confidentialityDataTreatment_en);
        configurationDto.setConfDataTreatment(confidentialityDataTreatment);
        // Legal Acts URL
        configurationDto.setLegalActsUrl("http://legalActs.com");
        // Data Sharing URL
        configurationDto.setDataSharingUrl("http://dataSharing.com");
        // Confidentiality Policy URL
        configurationDto.setConfPolicyUrl("http://confidentialityPolicy.com");
        // Confidentiality Data Treatment URL
        configurationDto.setConfDataTreatmentUrl("http://confidentialityDataTreatment.com");
        // TODO
        return configurationDto;
    }

}
