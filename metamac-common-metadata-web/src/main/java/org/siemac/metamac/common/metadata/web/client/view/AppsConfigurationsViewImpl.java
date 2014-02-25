package org.siemac.metamac.common.metadata.web.client.view;

import org.siemac.metamac.common.metadata.web.client.presenter.AppsConfigurationsPresenter;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

public class AppsConfigurationsViewImpl extends ViewImpl implements AppsConfigurationsPresenter.AppsConfigurationsView {

    private VLayout rootPanel;
    private VLayout toolBarPanel;
    private VLayout contentPanel;

    public AppsConfigurationsViewImpl() {

        toolBarPanel = new VLayout();
        toolBarPanel.setAutoHeight();
        contentPanel = new VLayout();
        contentPanel.setHeight100();

        rootPanel = new VLayout();
        rootPanel.addMember(toolBarPanel);
        rootPanel.addMember(contentPanel);
    }
    @Override
    public Widget asWidget() {
        return rootPanel;
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == AppsConfigurationsPresenter.TYPE_SetAppsConfigurationsToolbarSlot) {
            toolBarPanel.setMembers((Canvas) content);
        } else if (slot == AppsConfigurationsPresenter.TYPE_SetAppsConfigurationsContent) {
            contentPanel.setMembers((Canvas) content);
        } else {
            super.setInSlot(slot, content);
        }
    }
}
