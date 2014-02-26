package org.siemac.metamac.common.metadata.web.client.widgets.events;

import org.siemac.metamac.common.metadata.web.client.enums.CommonMetadataToolStripButtonEnum;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SelectMainSectionEvent extends GwtEvent<SelectMainSectionEvent.SelectMainSectionEventHandler> {

    public interface SelectMainSectionEventHandler extends EventHandler {

        void onSelectMainSection(SelectMainSectionEvent event);
    }

    private static Type<SelectMainSectionEventHandler> TYPE = new Type<SelectMainSectionEventHandler>();
    private final CommonMetadataToolStripButtonEnum    buttonId;

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SelectMainSectionEventHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, CommonMetadataToolStripButtonEnum buttonId) {
        if (TYPE != null) {
            source.fireEvent(new SelectMainSectionEvent(buttonId));
        }
    }

    public SelectMainSectionEvent(CommonMetadataToolStripButtonEnum buttonId2) {
        this.buttonId = buttonId2;
    }

    public CommonMetadataToolStripButtonEnum getButtonId() {
        return buttonId;
    }

    @Override
    protected void dispatch(SelectMainSectionEventHandler handler) {
        handler.onSelectMainSection(this);
    }

    public static Type<SelectMainSectionEventHandler> getType() {
        return TYPE;
    }
}
