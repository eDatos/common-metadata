package org.siemac.metamac.common.metadata.web.server.handlers;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveConfigurationActionHandler extends SecurityActionHandler<SaveConfigurationAction, SaveConfigurationResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    public SaveConfigurationActionHandler() {
        super(SaveConfigurationAction.class);
    }

    @Override
    public SaveConfigurationResult executeSecurityAction(SaveConfigurationAction action) throws ActionException {
        ConfigurationDto configurationToSave = action.getConfigurationToSave();
        try {
            ConfigurationDto configurationDto = null;
            if (configurationToSave.getId() == null) {
                configurationDto = commonMetadataServiceFacade.createConfiguration(ServiceContextHolder.getCurrentServiceContext(), configurationToSave);
            } else {
                configurationDto = commonMetadataServiceFacade.updateConfiguration(ServiceContextHolder.getCurrentServiceContext(), configurationToSave);
            }
            return new SaveConfigurationResult(configurationDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
