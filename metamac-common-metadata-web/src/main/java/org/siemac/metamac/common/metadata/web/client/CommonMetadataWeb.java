package org.siemac.metamac.common.metadata.web.client;

import org.siemac.metamac.common.metadata.web.client.gin.CommonMetadataWebGinjector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.gwtplatform.mvp.client.DelayedBindRegistry;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CommonMetadataWeb implements EntryPoint {
	
	interface GlobalResources extends ClientBundle {
		@NotStrict
		@Source("GopestatWebStyles.css")
		CssResource css();
	}

	
	private static CommonMetadataWebConstants constants;
	private static CommonMetadataWebMessages messages;
	
	public static final CommonMetadataWebGinjector ginjector = GWT.create(CommonMetadataWebGinjector.class);
	
    public void onModuleLoad() {
        // This is required for GWT-Platform proxy's generator.
        DelayedBindRegistry.bind(ginjector);
        ginjector.getPlaceManager().revealCurrentPlace();
        
        // Inject global styles
        GWT.<GlobalResources>create(GlobalResources.class).css().ensureInjected();
    }


    public static CommonMetadataWebConstants getConstants() {
    	if (constants == null) {
    		constants = (CommonMetadataWebConstants) GWT.create(CommonMetadataWebConstants.class);
    	}
    	return constants;
    }
    
    public static CommonMetadataWebMessages getMessages() {
    	if (messages == null) {
    		messages = (CommonMetadataWebMessages) GWT.create(CommonMetadataWebMessages.class);
    	}
    	return messages;
    }

    
    public static CommonMetadataWebGinjector getCommonMetadataWebGinjector() {
      return ginjector;
    }
    
}
