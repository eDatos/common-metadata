package org.siemac.metamac.common.metadata.base.serviceapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import org.junit.Test;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.core.common.dto.serviceapi.InternationalStringDto;
import org.siemac.metamac.core.common.dto.serviceapi.LocalisedStringDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
public class CommonMetadataBaseServiceFacadeTest extends AbstractDbUnitJpaTests implements CommonMetadataBaseServiceFacadeTestBase {
    
	@Autowired
    protected CommonMetadataBaseServiceFacade commonMetadataBaseServiceFacade;
	
	private final ServiceContext serviceContext = new ServiceContext("system", "123456", "junit");

    protected ServiceContext getServiceContext() {
        return serviceContext;
    }
    
    @Test
	public void testFindConfigurationById() throws CommonMetadataException {
		ConfigurationDto configurationDto = commonMetadataBaseServiceFacade.saveConfiguration(getServiceContext(), createConfigurationDto());
		ConfigurationDto configurationRetrieved = commonMetadataBaseServiceFacade.findConfigurationById(getServiceContext(), configurationDto.getId());
		assertNotNull(configurationRetrieved);
		assertTrue(configurationDto.getId().equals(configurationRetrieved.getId()));
	}
	
    @Test
	public void testFindAllConfigurations() throws CommonMetadataException {
		testSaveConfiguration();
		List<ConfigurationDto> configurationDtos = commonMetadataBaseServiceFacade.findAllConfigurations(getServiceContext());
		assertTrue(!configurationDtos.isEmpty());
		
	}

    @Test
	public void testSaveConfiguration() throws CommonMetadataException {
		ConfigurationDto configurationDto = commonMetadataBaseServiceFacade.saveConfiguration(getServiceContext(), createConfigurationDto());
		assertNotNull(configurationDto);
	}

    @Test
	public void testDeleteConfiguration() throws CommonMetadataException {
		ConfigurationDto configurationDto = commonMetadataBaseServiceFacade.saveConfiguration(getServiceContext(), createConfigurationDto());
		assertNotNull(configurationDto);
		int numberConfigurations = commonMetadataBaseServiceFacade.findAllConfigurations(getServiceContext()).size();
		commonMetadataBaseServiceFacade.deleteConfiguration(getServiceContext(), configurationDto);
		assertTrue(commonMetadataBaseServiceFacade.findAllConfigurations(getServiceContext()).size() == (numberConfigurations -1));
	}
    
	@Override
	public void testFindAllOrganisationSchemes() throws Exception {
		
	}
	
	
	/**************************************************************************
     *  						PRIVATE UTILS 
     **************************************************************************/

	private ConfigurationDto createConfigurationDto() {
		ConfigurationDto configurationDto = new ConfigurationDto();
		configurationDto.setName("configuration-0123456789");
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
