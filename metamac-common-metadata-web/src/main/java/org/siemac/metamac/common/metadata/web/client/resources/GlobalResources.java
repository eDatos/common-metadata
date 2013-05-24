package org.siemac.metamac.common.metadata.web.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface GlobalResources extends ClientBundle {

    public static final GlobalResources RESOURCE = GWT.create(GlobalResources.class);

    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("images/publish_externally.png")
    ImageResource publishExternally();
}
