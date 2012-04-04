package org.siemac.metamac.common.metadata.core.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationRepository;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of CommonMetadataService.
 */
@Service("commonMetadataService")
public class CommonMetadataServiceImpl extends CommonMetadataServiceImplBase {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public CommonMetadataServiceImpl() {
    }

    public Configuration findConfigurationById(ServiceContext ctx, Long id) throws MetamacException {
        try {
            return configurationRepository.findById(id);
        } catch (Exception e) {
            throw new MetamacException(ServiceExceptionType.SERVICE_CONFIGURATION_NOT_FOUND);
        }

    }

    public List<Configuration> findAllConfigurations(ServiceContext ctx) throws MetamacException {
        return configurationRepository.findAll();
    }

    public Configuration saveConfiguration(ServiceContext ctx, Configuration configuration) throws MetamacException {
        return configurationRepository.save(configuration);
    }

    public void deleteConfiguration(ServiceContext ctx, Configuration configuration) throws MetamacException {
        configurationRepository.delete(configuration);
    }

}
