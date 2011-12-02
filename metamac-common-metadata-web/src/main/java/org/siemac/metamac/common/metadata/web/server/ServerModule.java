package org.siemac.metamac.common.metadata.web.server;

import org.siemac.metamac.common.metadata.web.server.handlers.FindAllConfigurationsActionHandler;
import org.siemac.metamac.common.metadata.web.shared.FindAllConfigurationsAction;
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
	
	
	protected void configureHandlers() {
		bindHandler(FindAllConfigurationsAction.class, FindAllConfigurationsActionHandler.class);
	}
	
}
