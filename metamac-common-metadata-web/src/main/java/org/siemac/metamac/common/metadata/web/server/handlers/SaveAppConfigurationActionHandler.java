package org.siemac.metamac.common.metadata.web.server.handlers;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.server.utils.AppConfigurationsProcessor;
import org.siemac.metamac.common.metadata.web.shared.SaveAppConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.SaveAppConfigurationResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveAppConfigurationActionHandler extends SecurityActionHandler<SaveAppConfigurationAction, SaveAppConfigurationResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    public SaveAppConfigurationActionHandler() {
        super(SaveAppConfigurationAction.class);
    }

    @Override
    public SaveAppConfigurationResult executeSecurityAction(SaveAppConfigurationAction action) throws ActionException {
        DataConfigurationDto propertyToSave = action.getPropertyToSave();
        try {
            DataConfigurationDto configurationDto = commonMetadataServiceFacade.updateDataConfiguration(ServiceContextHolder.getCurrentServiceContext(), propertyToSave);

            return new SaveAppConfigurationResult(AppConfigurationsProcessor.processExternalItemConfiguration(configurationDto));
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
