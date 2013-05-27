package org.siemac.metamac.common.metadata.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class UpdateConfigurationsEvent extends GwtEvent<UpdateConfigurationsEvent.UpdateConfigurationsHandler> {

    public interface UpdateConfigurationsHandler extends EventHandler {

        void onUpdateConfigurations(UpdateConfigurationsEvent event);
    }

    private static Type<UpdateConfigurationsHandler> TYPE = new Type<UpdateConfigurationsHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<UpdateConfigurationsHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source) {
        if (TYPE != null) {
            source.fireEvent(new UpdateConfigurationsEvent());
        }
    }

    @Override
    protected void dispatch(UpdateConfigurationsHandler handler) {
        handler.onUpdateConfigurations(this);
    }

    public UpdateConfigurationsEvent() {
    }

    public static Type<UpdateConfigurationsHandler> getType() {
        return TYPE;
    }
}
