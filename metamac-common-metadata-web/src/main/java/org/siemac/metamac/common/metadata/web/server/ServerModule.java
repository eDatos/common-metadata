package org.siemac.metamac.common.metadata.web.server;

import org.siemac.metamac.common.metadata.web.server.handlers.DeleteConfigurationsActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.GetAppsConfigurationsActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.GetConfigurationActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.GetConfigurationsActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.GetHelpUrlActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.PublishConfigurationExternallyActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.SaveAppConfigurationActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.SaveConfigurationActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.UpdateConfigurationsStatusActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.ValidateTicketActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.external.GetSrmItemSchemesActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.external.GetSrmItemsActionHandler;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.GetAppsConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.GetConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.GetConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.GetHelpUrlAction;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyAction;
import org.siemac.metamac.common.metadata.web.shared.SaveAppConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationAction;
import org.siemac.metamac.common.metadata.web.shared.UpdateConfigurationsStatusAction;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemSchemesAction;
import org.siemac.metamac.common.metadata.web.shared.external.GetSrmItemsAction;
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

    @Override
    protected void configureHandlers() {
        bindHandler(GetConfigurationAction.class, GetConfigurationActionHandler.class);
        bindHandler(GetConfigurationsAction.class, GetConfigurationsActionHandler.class);
        bindHandler(SaveConfigurationAction.class, SaveConfigurationActionHandler.class);
        bindHandler(UpdateConfigurationsStatusAction.class, UpdateConfigurationsStatusActionHandler.class);
        bindHandler(PublishConfigurationExternallyAction.class, PublishConfigurationExternallyActionHandler.class);
        bindHandler(DeleteConfigurationsAction.class, DeleteConfigurationsActionHandler.class);

        bindHandler(GetAppsConfigurationsAction.class, GetAppsConfigurationsActionHandler.class);
        bindHandler(SaveAppConfigurationAction.class, SaveAppConfigurationActionHandler.class);

        bindHandler(ValidateTicketAction.class, ValidateTicketActionHandler.class);
        bindHandler(GetLoginPageUrlAction.class, GetLoginPageUrlActionHandler.class);
        bindHandler(CloseSessionAction.class, CloseSessionActionHandler.class);
        bindHandler(GetNavigationBarUrlAction.class, GetNavigationBarUrlActionHandler.class);

        bindHandler(LoadConfigurationPropertiesAction.class, LoadConfigurationPropertiesActionHandler.class);
        bindHandler(GetHelpUrlAction.class, GetHelpUrlActionHandler.class);

        bindHandler(GetSrmItemsAction.class, GetSrmItemsActionHandler.class);
        bindHandler(GetSrmItemSchemesAction.class, GetSrmItemSchemesActionHandler.class);

        // This action should be removed to use CAS authentication
        bindHandler(MockCASUserAction.class, MockCASUserActionHandler.class);
    }
}
