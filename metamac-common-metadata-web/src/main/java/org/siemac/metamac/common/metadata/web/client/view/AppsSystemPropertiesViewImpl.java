package org.siemac.metamac.common.metadata.web.client.view;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.model.AppConfigurationRecord;
import org.siemac.metamac.common.metadata.web.client.presenter.AppsSystemPropertiesPresenter;
import org.siemac.metamac.common.metadata.web.client.utils.AppDataConfigClientSecurityUtils;
import org.siemac.metamac.common.metadata.web.client.utils.RecordUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.AppsDataConfigurationsUiHandlers;
import org.siemac.metamac.common.metadata.web.client.widgets.AppPropertyNoExternalItemPanel;
import org.siemac.metamac.common.metadata.web.client.widgets.AppPropertyPanel;
import org.siemac.metamac.common.metadata.web.client.widgets.AppsConfigurationsListGrid;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class AppsSystemPropertiesViewImpl extends ViewWithUiHandlers<AppsDataConfigurationsUiHandlers> implements AppsSystemPropertiesPresenter.AppsSystemPropertiesView {

    private VLayout                    panel;
    private AppsConfigurationsListGrid listGrid;
    private AppPropertyPanel           detailPanel;

    public AppsSystemPropertiesViewImpl() {

        listGrid = new AppsConfigurationsListGrid();

        listGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                AppConfigurationRecord record = (AppConfigurationRecord) event.getRecord();
                detailPanel.selectDtoForView(record.getDto());
            }
        });

        detailPanel = createDetailPanel();

        panel = new VLayout();
        panel.addMember(listGrid);
        panel.addMember(detailPanel);
    }

    @Override
    public void selectAppConfiguration(DataConfigurationDto appConfiguration) {
        if (appConfiguration != null) {
            detailPanel.selectDtoForView(appConfiguration);
        } else {
            detailPanel.hideFormLayout();
        }
    }

    private AppPropertyPanel createDetailPanel() {
        AppPropertyNoExternalItemPanel propertyPanel = new AppPropertyNoExternalItemPanel() {

            @Override
            protected void saveProperty(DataConfigurationDto configurationProperty) {
                getUiHandlers().saveDataCondiguration(configurationProperty);
            }
        };
        propertyPanel.setCanEdit(AppDataConfigClientSecurityUtils.canEditSystemProperties());
        return propertyPanel;
    }

    @Override
    public void setAppConfigurations(List<DataConfigurationDto> properties) {
        listGrid.setData(RecordUtils.getAppConfigurationRecords(properties));
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

}
