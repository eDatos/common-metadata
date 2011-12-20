package org.siemac.metamac.common.metadata.web.server;

import org.siemac.metamac.common.metadata.web.server.handlers.DeleteConfigurationListActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.FindAllConfigurationsActionHandler;
import org.siemac.metamac.common.metadata.web.server.handlers.SaveConfigurationActionHandler;
import org.siemac.metamac.common.metadata.web.shared.DeleteConfigurationListAction;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.SaveConfigurationAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.gwtplatform.dispatch.server.actionvalidator.ActionValidator;
import com.gwtplatform.dispatch.server.spring.HandlerModule;
import com.gwtplatform.dispatch.server.spring.actionvalidator.DefaultActionValidator;
import com.gwtplatform.dispatch.server.spring.configuration.DefaultModule;

/**
 * Module which binds the handlers and configurations.
 */
@Configuration
@Import(DefaultModule.class)
public class ServerModule extends HandlerModule {

	public ServerModule() {
	}

	@Bean
	public ActionValidator getDefaultActionValidator() {
		return new DefaultActionValidator();
	}
	
	@Bean
	public FindAllConfigurationsActionHandler getFindAllConfigurationsActionHandler() {
		return new FindAllConfigurationsActionHandler();
	}
	
	@Bean
	public SaveConfigurationActionHandler getSaveConfigurationActionHandler() {
		return new SaveConfigurationActionHandler();
	}
	
	@Bean
	public DeleteConfigurationListActionHandler getConfigurationListActionHandler() {
		return new DeleteConfigurationListActionHandler();
	}
	
	
	protected void configureHandlers() {
		bindHandler(FindAllConfigurationsAction.class, FindAllConfigurationsActionHandler.class);
		bindHandler(SaveConfigurationAction.class, SaveConfigurationActionHandler.class);
		bindHandler(DeleteConfigurationListAction.class, DeleteConfigurationListActionHandler.class);
	}
	
}
