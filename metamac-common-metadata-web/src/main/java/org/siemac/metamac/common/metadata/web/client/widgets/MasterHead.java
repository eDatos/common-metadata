package org.siemac.metamac.common.metadata.web.client.widgets;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

public class MasterHead extends HLayout {

	private static final int MASTHEAD_HEIGHT = 60;
	private static final int IMAGE_SIZE_WIDTH = 255;
	private static final int IMAGE_SIZE_HEIGHT = 67;

	//	private static final String WEST_WIDTH = "100%";
	private static final String EAST_WIDTH = "100%";

	private Label titleLabel;
	
	public MasterHead() {
		super();

		// Head layout container
		this.setStyleName("titleHeaderPanel");
		this.setHeight(MASTHEAD_HEIGHT);
		this.setBackgroundColor("white");

		// Logo image
//		Img logo = new Img(GlobalResources.RESOURCE.istacLogo().getURL(), IMAGE_SIZE_WIDTH, IMAGE_SIZE_HEIGHT);
//		logo.setStyleName("metamac-head-Logo");

		// Name label
		titleLabel = new Label();
		titleLabel.setStyleName("titleHeaderPanel");
//		titleLabel.setContents(GopestatWeb.getConstants().appTitle());
		titleLabel.setWidth(600);

		// West layout container
		HLayout westLayout = new HLayout();
		westLayout.setHeight(MASTHEAD_HEIGHT);
//		westLayout.addMember(logo);

		// East layout container
		HLayout eastLayout = new HLayout();
		eastLayout.setAlign(Alignment.CENTER);
		eastLayout.setHeight(MASTHEAD_HEIGHT);
		eastLayout.setWidth(EAST_WIDTH);
		eastLayout.addMember(titleLabel);

		// add the West and East layout containers to the MasterHead layout container
		this.addMember(westLayout);
		this.addMember(eastLayout);
	}
	
	public void setTitleLabel(String title)  {
		titleLabel.setContents(title);
	}
	
}
