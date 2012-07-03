package org.siemac.metamac.common.metadata.web.server.handlers;

import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.UpdateConfigurationsStatusAction;
import org.siemac.metamac.common.metadata.web.shared.UpdateConfigurationsStatusResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateConfigurationsStatusActionHandler extends SecurityActionHandler<UpdateConfigurationsStatusAction, UpdateConfigurationsStatusResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    public UpdateConfigurationsStatusActionHandler() {
        super(UpdateConfigurationsStatusAction.class);
    }

    @Override
    public UpdateConfigurationsStatusResult executeSecurityAction(UpdateConfigurationsStatusAction action) throws ActionException {
        // TODO Auto-generated method stub
        return null;
    }

}
