package org.siemac.metamac.common.metadata.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.common.metadata.error.ServiceExceptionType;
import org.siemac.metamac.core.common.dto.serviceapi.InternationalStringDto;
import org.siemac.metamac.core.common.dto.serviceapi.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.springframework.beans.factory.annotation.Autowired;


public class Dto2DoMapperImpl implements Dto2DoMapper {

	@Autowired
	private DozerBeanMapper mapper;
	
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
			return null;
		}
		
		if (target == null) {
			target =  new InternationalString();	
		}
		
		target.getTexts().clear();
		target.getTexts().addAll(localisedStringDtoToDo(source.getTexts()));
		return target;
	}
	

	private List<LocalisedString> localisedStringDtoToDo(Set<LocalisedStringDto> sources) {
		List<LocalisedString> targets = new ArrayList<LocalisedString>();
		for (LocalisedStringDto source : sources) {
			LocalisedString target = new LocalisedString();
    		target.setLabel(source.getLabel());
    		target.setLocale(source.getLocale());
			targets.add(target);
		}
		return targets;
	}

}
