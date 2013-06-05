package org.siemac.metamac.common.metadata.web.server.handlers;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyAction;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class PublishConfigurationExternallyActionHandler extends SecurityActionHandler<PublishConfigurationExternallyAction, PublishConfigurationExternallyResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    public PublishConfigurationExternallyActionHandler() {
        super(PublishConfigurationExternallyAction.class);
    }

    @Override
    public PublishConfigurationExternallyResult executeSecurityAction(PublishConfigurationExternallyAction action) throws ActionException {
        try {

            // FIXME check that the agency is externally published too!!

            ConfigurationDto configurationDto = commonMetadataServiceFacade.publishExternallyConfiguration(ServiceContextHolder.getCurrentServiceContext(), action.getId());
            return new PublishConfigurationExternallyResult(configurationDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
