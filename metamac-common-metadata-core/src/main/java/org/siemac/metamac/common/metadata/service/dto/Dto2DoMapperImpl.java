package org.siemac.metamac.common.metadata.service.dto;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.siemac.metamac.common.metadata.base.serviceapi.CommonMetadataBaseService;
import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.common.metadata.error.ServiceExceptionType;
import org.siemac.metamac.core.common.bt.domain.ExternalItemBt;
import org.siemac.metamac.core.common.dto.serviceapi.ExternalItemBtDto;
import org.siemac.metamac.core.common.dto.serviceapi.InternationalStringDto;
import org.siemac.metamac.core.common.dto.serviceapi.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.InternationalStringRepository;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.core.common.vo.domain.ExternalItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Dto2DoMapperImpl implements Dto2DoMapper {

	@Autowired
	private DozerBeanMapper mapper;
	
	@Autowired
	private InternationalStringRepository internationalStringRepository;

	protected InternationalStringRepository getInternationalStringRepository() {
		return internationalStringRepository;
	}
	
	@Override
	public Configuration configurationDtoToEntity(ConfigurationDto source, ServiceContext ctx) throws MetamacException {
		Configuration target = new Configuration();
		return configurationDtoToEntity(source, target, ctx);
	}
	
	@Override
	public Configuration configurationDtoToEntity(ConfigurationDto source, Configuration target, ServiceContext ctx) throws MetamacException {
		if (source == null) {
			return null;
		}
		if (target == null) {
			throw new MetamacException(ServiceExceptionType.SERVICE_INVALID_PARAMETER_NULL);
		}
		Configuration configuration = null;
		configuration = mapper.map(source, Configuration.class);
		
		configuration.setLegalActs(internationalStringToEntity(source.getLegalActs(), target.getLegalActs(), "CONFIGURATION.LEGAL_ACTS"));
		configuration.setDataSharing(internationalStringToEntity(source.getDataSharing(), target.getDataSharing(), "CONFIGURATION.DATA_SHARING"));
		configuration.setConfPolicy(internationalStringToEntity(source.getConfPolicy(), target.getConfPolicy(), "CONFIGURATION.CONF_POLICY"));
		configuration.setConfDataTreatment(internationalStringToEntity(source.getConfDataTreatment(), target.getConfDataTreatment(), "CONFIGURATION.CONF_DATA_TREATMENT"));
		
		return configuration;
	}

	private InternationalString internationalStringToEntity(InternationalStringDto source, InternationalString target, String metadataName) throws MetamacException {
		if (source == null) {
			// Delete old entity
			if (target != null) {
				getInternationalStringRepository().delete(target);
			}
			return null;
		}
		
		// Avoid the appearance of trash.
		if (target != null) {
			source.setId(target.getId());
			source.setUuid(target.getUuid());
			source.setVersion(target.getVersion());
		}
		
        if (ValidationUtils.isEmpty(source)) {
            throw new MetamacException(ServiceExceptionType.METADATA_REQUIRED, metadataName);
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

	@Override
	public ExternalItem externalItemBtDtoToExternalItem(ExternalItemBtDto externalItemBtDto, ServiceContext ctx, CommonMetadataBaseService commonMetadataBaseService) throws MappingException {
		if (externalItemBtDto == null) {
			return null;
		}
		
		ExternalItem result = new ExternalItem(new ExternalItemBt(externalItemBtDto.getUriInt(), externalItemBtDto.getCodeId(), externalItemBtDto.getType()));
		
		return result;
	}

}
