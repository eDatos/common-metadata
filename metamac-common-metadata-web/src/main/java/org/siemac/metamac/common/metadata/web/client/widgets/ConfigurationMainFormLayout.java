package org.siemac.metamac.common.metadata.web.client.widgets;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.resources.GlobalResources;
import org.siemac.metamac.common.metadata.web.client.utils.ClientSecurityUtils;
import org.siemac.metamac.web.common.client.widgets.MainFormLayoutButton;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public class ConfigurationMainFormLayout extends InternationalMainFormLayout {

    protected MainFormLayoutButton publishExternally;

    public ConfigurationMainFormLayout(boolean canEdit) {
        super(canEdit);

        publishExternally = new MainFormLayoutButton(getConstants().lifeCyclePublishExternally(), GlobalResources.RESOURCE.publishExternally().getURL());

        toolStrip.addButton(publishExternally);
    }

    public void setConfiguration(ConfigurationDto configurationDto) {
        deleteToolStringButton.hide();
        publishExternally.hide();

        boolean canDelete = !configurationDto.isExternallyPublished() && ClientSecurityUtils.canDeleteConfiguration();
        boolean canPublishExternally = !configurationDto.isExternallyPublished() && ClientSecurityUtils.canPublishConfigurationExternally();

        if (canDelete) {
            deleteToolStringButton.show();
        }
        if (canPublishExternally) {
            publishExternally.show();
        }
    }

    public HasClickHandlers getPublishExternally() {
        return publishExternally;
    }
}
