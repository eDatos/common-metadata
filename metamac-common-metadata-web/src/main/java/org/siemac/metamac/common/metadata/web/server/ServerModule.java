package org.siemac.metamac.common.metadata.web.server;

import org.siemac.metamac.common.metadata.web.server.handlers.DeleteConfigurationListActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.FindAllConfigurationsActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.FindAllOrganisationSchemesActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.GetOrganisationsFromSchemeActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.SaveConfigurationActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.UpdateConfigurationsStatusActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.ValidateTicketActionHandler;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllOrganisationSchemesAction;
import org.siemac.metamac.common.metadata.web.shared.GetOrganisationsFromSchemeAction;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.UpdateConfigurationsStatusAction;
import org.siemac.metamac.web.common.server.handlers.CloseSessionActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetLoginPageUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetNavigationBarUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.LoadConfigurationPropertiesActionHandler;
import org.siemac.metamac.web.common.server.handlers.MockCASUserActionHandler;
import org.siemac.metamac.web.common.shared.CloseSessionAction;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlAction;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlAction;
import org.siemac.metamac.web.common.shared.LoadConfigurationPropertiesAction;
import org.siemac.metamac.web.common.shared.MockCASUserAction;
import org.siemac.metamac.web.common.shared.ValidateTicketAction;
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
        bindHandler(UpdateConfigurationsStatusAction.class, UpdateConfigurationsStatusActionHandler.class);

        bindHandler(ValidateTicketAction.class, ValidateTicketActionHandler.class);
        bindHandler(GetLoginPageUrlAction.class, GetLoginPageUrlActionHandler.class);
        bindHandler(CloseSessionAction.class, CloseSessionActionHandler.class);
        bindHandler(GetNavigationBarUrlAction.class, GetNavigationBarUrlActionHandler.class);

        bindHandler(LoadConfigurationPropertiesAction.class, LoadConfigurationPropertiesActionHandler.class);

        // This action should be removed to use CAS authentication
        bindHandler(MockCASUserAction.class, MockCASUserActionHandler.class);
    }

}
