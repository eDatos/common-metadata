package org.siemac.metamac.common.metadata.web.server;

import org.siemac.metamac.common.metadata.web.server.handlers.DeleteConfigurationListActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.FindAllConfigurationsActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.FindAllOrganisationSchemesActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.GetOrganisationsFromSchemeActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.SaveConfigurationActionHandler;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllOrganisationSchemesAction;
import org.siemac.metamac.common.metadata.web.shared.GetOrganisationsFromSchemeAction;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationAction;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.spring.HandlerModule;

/**
 * Module which binds the handlers and configurations.
 */
@Component
public class ServerModule extends HandlerModule {

    public ServerModule() {
    }

    protected void configureHandlers() {
        bindHandler(FindAllConfigurationsAction.class, FindAllConfigurationsActionHandler.class);
        bindHandler(SaveConfigurationAction.class, SaveConfigurationActionHandler.class);
        bindHandler(DeleteConfigurationListAction.class, DeleteConfigurationListActionHandler.class);
        bindHandler(FindAllOrganisationSchemesAction.class, FindAllOrganisationSchemesActionHandler.class);
        bindHandler(GetOrganisationsFromSchemeAction.class, GetOrganisationsFromSchemeActionHandler.class);
    }

}
