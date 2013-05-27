package org.siemac.metamac.common.metadata.web.client.widgets;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.resources.GlobalResources;
import org.siemac.metamac.common.metadata.web.client.utils.ClientSecurityUtils;
import org.siemac.metamac.web.common.client.widgets.MainFormLayoutButton;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class ConfigurationMainFormLayout extends InternationalMainFormLayout {

    private ConfigurationDto       configurationDto;
    protected MainFormLayoutButton publishExternally;

    public ConfigurationMainFormLayout(boolean canEdit) {
        super(canEdit);

        publishExternally = new MainFormLayoutButton(getConstants().lifeCyclePublishExternally(), GlobalResources.RESOURCE.publishExternally().getURL());
        toolStrip.addButton(publishExternally);

        cancelToolStripButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                updateButtonsVisibility();
            }
        });
    }

    public void setConfiguration(ConfigurationDto configurationDto) {
        this.configurationDto = configurationDto;
        updateButtonsVisibility();
    }

    private void updateButtonsVisibility() {

        boolean isExternallyPublished = configurationDto.isExternallyPublished();

        deleteToolStringButton.hide();
        publishExternally.hide();

        boolean canDelete = ClientSecurityUtils.canDeleteConfiguration(configurationDto);
        boolean canPublishExternally = !isExternallyPublished && ClientSecurityUtils.canPublishConfigurationExternally();

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
