package org.siemac.metamac.common.metadata.base.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.base.domain.Configuration;
import org.siemac.metamac.common.metadata.base.domain.ConfigurationRepository;
import org.siemac.metamac.common.metadata.base.exception.CommonMetadataException;
import org.siemac.metamac.common.metadata.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of CommonMetadataBaseService.
 */
@Service("commonMetadataBaseService")
public class CommonMetadataBaseServiceImpl extends CommonMetadataBaseServiceImplBase {
   
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	public CommonMetadataBaseServiceImpl() {
    }

    public Configuration findConfigurationById(ServiceContext ctx, Long id) throws CommonMetadataException {
		try {
			return configurationRepository.findById(id);
		} catch (Exception e) {
			throw new CommonMetadataException(ServiceExceptionType.SERVICE_CONFIGURATION_NOT_FOUND.getErrorCode(), ServiceExceptionType.SERVICE_CONFIGURATION_NOT_FOUND.getMessageForReasonType());
		}

    }

    public List<Configuration> findAllConfigurations(ServiceContext ctx) throws CommonMetadataException {
    	return configurationRepository.findAll();
    }

    public Configuration saveConfiguration(ServiceContext ctx, Configuration configuration) throws CommonMetadataException {
    	return configurationRepository.save(configuration);
    }

    public void deleteConfiguration(ServiceContext ctx, Configuration configuration) throws CommonMetadataException {
    	configurationRepository.delete(configuration);
    }

}
