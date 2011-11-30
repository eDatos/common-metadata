package org.siemac.metamac.common.metadata.web.client.view;

import org.siemac.metamac.common.metadata.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.common.metadata.web.client.view.handlers.MainPageUiHandlers;
import org.siemac.metamac.common.metadata.web.client.widgets.MasterHead;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class MainPageViewImpl extends ViewWithUiHandlers<MainPageUiHandlers> implements MainPagePresenter.MainPageView {

	private static final int NORTH_HEIGHT = 85;
	private static final String DEFAULT_MARGIN = "0px";

	private final MasterHead masterHead;
	
	private VLayout panel;
	private HLayout northLayout;
	private HLayout southLayout;
	private HLayout footerLayout;
	

	@Inject
	public MainPageViewImpl(MasterHead masterHead) {
		this.masterHead = masterHead;

		// get rid of scroll bars, and clear out the window's built-in margin,
		// because we want to take advantage of the entire client area
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);

		// Initialize the main layout container
		panel = new VLayout();
		panel.setWidth100();
		panel.setHeight100();
		panel.setAlign(Alignment.CENTER);
		panel.setCanDrag(false);

		// Initialize the North layout container
		northLayout = new HLayout();
		northLayout.setHeight(NORTH_HEIGHT);

		// Nested layout container
		VLayout vLayout = new VLayout();
		vLayout.addMember(this.masterHead);
		
		// Nested layout container to the North layout container
		northLayout.addMember(vLayout);
		northLayout.setHeight(65);

		// Initialize the South layout container
		southLayout = new HLayout();
		southLayout.setHeight100();
		
		footerLayout = new HLayout();
		footerLayout.setBorder("1px solid #A7ABB4");

		// Add the North and South layout containers to the main layout
		// container
		panel.addMember(northLayout);
		panel.addMember(southLayout);
		panel.addMember(footerLayout);
	}

	@Override
	public Widget asWidget() {
		return panel;
	}
	

	/****************************************************
	 * Code for nested presenters.
	 ***************************************************/

	/*
	 * GWTP will call setInSlot when a child presenter asks to be added under this view
	 */
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == MainPagePresenter.TYPE_SetContextAreaContent) {
			if (content != null) {
				southLayout.setMembers((VLayout) content);
			}
		} else {
			// To support inheritance in your views it is good practice to call super.setInSlot when you can't handle the call. 
			// Who knows, maybe the parent class knows what to do with this slot. 
			super.setInSlot(slot, content);
		}
	}

	@Override
	public void removeFromSlot(Object slot, Widget content) {
		super.removeFromSlot(slot, content);
	}

	@Override
	public MasterHead getMasterHead() {
		return masterHead;
	}
	
	/****************************************************
	 * End code for nested presenters.
	 ***************************************************/

}
