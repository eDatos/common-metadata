package org.siemac.metamac.common.metadata.web.client.widgets.events;

import org.siemac.metamac.common.metadata.web.client.enums.AppsConfigurationsToolStripButtonEnum;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SelectAppConfigurationSectionEvent extends GwtEvent<SelectAppConfigurationSectionEvent.SelectAppConfigurationSectionEventHandler> {

    public interface SelectAppConfigurationSectionEventHandler extends EventHandler {

        void onSelectAppConfigurationSection(SelectAppConfigurationSectionEvent event);
    }

    private static Type<SelectAppConfigurationSectionEventHandler> TYPE = new Type<SelectAppConfigurationSectionEventHandler>();
    private final AppsConfigurationsToolStripButtonEnum            buttonId;

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SelectAppConfigurationSectionEventHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, AppsConfigurationsToolStripButtonEnum buttonId) {
        if (TYPE != null) {
            source.fireEvent(new SelectAppConfigurationSectionEvent(buttonId));
        }
    }

    public SelectAppConfigurationSectionEvent(AppsConfigurationsToolStripButtonEnum buttonId2) {
        this.buttonId = buttonId2;
    }

    public AppsConfigurationsToolStripButtonEnum getButtonId() {
        return buttonId;
    }

    @Override
    protected void dispatch(SelectAppConfigurationSectionEventHandler handler) {
        handler.onSelectAppConfigurationSection(this);
    }

    public static Type<SelectAppConfigurationSectionEventHandler> getType() {
        return TYPE;
    }
}
