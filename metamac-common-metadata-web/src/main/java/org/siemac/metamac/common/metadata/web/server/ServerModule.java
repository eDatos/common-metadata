package org.siemac.metamac.common.metadata.web.server;

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
	
	protected void configureHandlers() {
	
	}
	
}
