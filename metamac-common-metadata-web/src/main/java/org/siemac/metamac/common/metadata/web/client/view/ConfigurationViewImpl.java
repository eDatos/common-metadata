package org.siemac.metamac.common.metadata.web.client.view;

import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationPresenter;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationUiHandlers;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class ConfigurationViewImpl extends ViewWithUiHandlers<ConfigurationUiHandlers> implements ConfigurationPresenter.ConfigurationView {

	private VLayout panel;
	
	@Inject
	public ConfigurationViewImpl() {
		super();
		
		panel = new VLayout();
		Label label = new Label("HOLA!");
		panel.addMember(label);
		
	}
	
	@Override
	public Widget asWidget() {
		return panel;
	}
	
}
