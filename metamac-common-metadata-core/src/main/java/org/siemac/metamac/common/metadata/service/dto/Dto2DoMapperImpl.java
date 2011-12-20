package org.siemac.metamac.common.metadata.service.dto;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.common.metadata.error.ServiceExceptionType;
import org.siemac.metamac.core.common.dto.serviceapi.InternationalStringDto;
import org.siemac.metamac.core.common.dto.serviceapi.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.InternationalStringRepository;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.springframework.beans.factory.annotation.Autowired;


public class Dto2DoMapperImpl implements Dto2DoMapper {

	@Autowired
	private DozerBeanMapper mapper;
	
	@Autowired
	private InternationalStringRepository internationalStringRepository;

	protected InternationalStringRepository getInternationalStringRepository() {
		return internationalStringRepository;
	}
	
	@Override
	public Configuration configurationDtoToEntity(ConfigurationDto source, ServiceContext ctx) throws CommonMetadataException {
		Configuration target = new Configuration();
		return configurationDtoToEntity(source, target, ctx);
	}
	
	@Override
	public Configuration configurationDtoToEntity(ConfigurationDto source, Configuration target, ServiceContext ctx) throws CommonMetadataException {
		if (source == null) {
			return null;
		}
		if (target == null) {
			throw new CommonMetadataException(ServiceExceptionType.SERVICE_INVALID_PARAMETER_NULL.getErrorCode(), ServiceExceptionType.SERVICE_INVALID_PARAMETER_NULL.getMessageForReasonType());
		}
		Configuration configuration = null;
		configuration = mapper.map(source, Configuration.class);
		
		configuration.setLegalActs(internationalStringToEntity(source.getLegalActs(), target.getLegalActs()));
		configuration.setDataSharing(internationalStringToEntity(source.getDataSharing(), target.getDataSharing()));
		configuration.setConfPolicy(internationalStringToEntity(source.getConfPolicy(), target.getConfPolicy()));
		configuration.setConfDataTreatment(internationalStringToEntity(source.getConfDataTreatment(), target.getConfDataTreatment()));
		
		return configuration;
	}

	private InternationalString internationalStringToEntity(InternationalStringDto source, InternationalString target) {
		if (source == null) {
			// Delete old entity
			if (target != null) {
				getInternationalStringRepository().delete(target);
			}
			
			return null;
		}
		// NAME
		// InternationalStringDTO to InternationalString  
		
		// Avoid the appearance of trash.
		if (target != null) {
			source.setId(target.getId());
			source.setUuid(target.getUuid());
			source.setVersion(target.getVersion());
		}
		
		InternationalString internationalString = mapper.map(source, InternationalString.class);
		
		// LocalisedStringDto to LocalisedString  
		for (LocalisedStringDto item : source.getTexts()) {
			if (StringUtils.isNotBlank(item.getLabel())) {
				internationalString.addText(mapper.map(item, LocalisedString.class));
			}
		}
		
		return internationalString;
	}

}
