package org.siemac.metamac.common.metadata.web.client.view;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;
import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getCoreMessages;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.web.client.model.ConfigurationRecord;
import org.siemac.metamac.common.metadata.web.client.model.ds.ConfigurationDS;
import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationPresenter.ConfigurationView;
import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationsPresenter;
import org.siemac.metamac.common.metadata.web.client.utils.ClientSecurityUtils;
import org.siemac.metamac.common.metadata.web.client.utils.RecordUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationsUiHandlers;
import org.siemac.metamac.common.metadata.web.client.widgets.NewConfigurationWindow;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ConfigurationsViewImpl extends ViewWithUiHandlers<ConfigurationsUiHandlers> implements ConfigurationsPresenter.ConfigurationsView {

    private VLayout                     panel;
    private InternationalMainFormLayout configurationMainFormLayout;

    private CustomListGrid              configurationsListGrid;

    private ToolStripButton             newToolStripButton;
    private ToolStripButton             enableToolStripButton;
    private ToolStripButton             disableToolStripButton;

    @Inject
    public ConfigurationsViewImpl(ConfigurationView configurationView) {
        super();
        this.configurationMainFormLayout = (InternationalMainFormLayout) configurationView.asWidget();
        this.configurationMainFormLayout.setVisibility(Visibility.HIDDEN);

        panel = new VLayout();
        panel.setOverflow(Overflow.SCROLL);

        // ToolStrip

        newToolStripButton = new ToolStripButton(getConstants().actionNew(), GlobalResources.RESOURCE.newListGrid().getURL());
        newToolStripButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final NewConfigurationWindow newConfigurationWindow = new NewConfigurationWindow(getConstants().actionCreate());
                newConfigurationWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        if (newConfigurationWindow.validateForm()) {
                            getUiHandlers().createConfiguration(newConfigurationWindow.getNewConfigurationDto());
                            newConfigurationWindow.markForDestroy();
                        }
                    }
                });
            }
        });
        newToolStripButton.setVisibility(ClientSecurityUtils.canCreateConfiguration() ? Visibility.VISIBLE : Visibility.HIDDEN);

        enableToolStripButton = new ToolStripButton(getConstants().confEnable(), GlobalResources.RESOURCE.success().getURL());
        enableToolStripButton.setVisibility(Visibility.HIDDEN);
        enableToolStripButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().updateConfigurationsStatus(getSelectedConfigurations(), CommonMetadataStatusEnum.ENABLED);
            }
        });

        disableToolStripButton = new ToolStripButton(getConstants().confDisable(), GlobalResources.RESOURCE.disable().getURL());
        disableToolStripButton.setVisibility(Visibility.HIDDEN);
        disableToolStripButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().updateConfigurationsStatus(getSelectedConfigurations(), CommonMetadataStatusEnum.DISABLED);
            }
        });

        ToolStrip toolStrip = new ToolStrip();
        toolStrip.setWidth100();
        toolStrip.addButton(newToolStripButton);
        toolStrip.addSeparator();
        toolStrip.addButton(enableToolStripButton);
        toolStrip.addButton(disableToolStripButton);

        // ListGrid

        configurationsListGrid = new CustomListGrid();
        configurationsListGrid.setWidth100();
        configurationsListGrid.setHeight(250);
        ListGridField codeField = new ListGridField(ConfigurationDS.CODE, getConstants().configurationIdentifier());
        ListGridField status = new ListGridField(ConfigurationDS.STATUS, getCoreMessages().commonMetadataStatusEnumENABLED());
        status.setType(ListGridFieldType.IMAGE);
        status.setWidth("15%");
        ListGridField externallyPublished = new ListGridField(ConfigurationDS.EXTERNALLY_PUBLISHED, getConstants().configurationExternallyPublished());
        externallyPublished.setType(ListGridFieldType.IMAGE);
        externallyPublished.setWidth("15%");
        configurationsListGrid.setFields(codeField, status, externallyPublished);
        // Show configuration details when record clicked
        configurationsListGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                if (configurationsListGrid.getSelectedRecords().length > 0) {
                    // If all selected configurations are ENABLED, show disableConfigurationButton
                    if (areSelectedConfigurationsEnabled()) {
                        showDisableConfigurationButton();
                    }
                    // If all selected configurations are DISABLED, show enableConfigurationButton
                    if (areSelectedConfigurationsDisabled()) {
                        showEnableConfigurationButton();
                    }
                } else {
                    deselectConfiguration();
                }
            }
        });
        configurationsListGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (event.getFieldNum() != 0) { // CheckBox is not clicked
                    String urn = ((ConfigurationRecord) event.getRecord()).getAttribute(ConfigurationDS.URN);
                    getUiHandlers().goToConfiguration(urn);
                }
            }
        });

        VLayout configurationsLayout = new VLayout();
        configurationsLayout.setAutoHeight();
        configurationsLayout.setMargin(15);
        configurationsLayout.addMember(toolStrip);
        configurationsLayout.addMember(configurationsListGrid);

        panel.addMember(configurationsLayout);
        panel.addMember(configurationMainFormLayout);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setConfigurations(List<ConfigurationDto> configurations) {
        configurationsListGrid.removeAllData();
        hideListGridConfigurationButtons();
        for (ConfigurationDto configurationDto : configurations) {
            ConfigurationRecord record = RecordUtils.getConfigurationRecord(configurationDto);
            configurationsListGrid.addData(record);
        }
    }

    private List<Long> getSelectedConfigurations() {
        if (configurationsListGrid.getSelectedRecords() != null) {
            List<Long> selectedConfigurations = new ArrayList<Long>();
            ListGridRecord[] records = configurationsListGrid.getSelectedRecords();
            for (int i = 0; i < records.length; i++) {
                ConfigurationRecord record = (ConfigurationRecord) records[i];
                selectedConfigurations.add(record.getConfigurationDto().getId());
            }
            return selectedConfigurations;
        }
        return null;
    }

    @Override
    public void deselectConfiguration() {
        configurationMainFormLayout.hide();
        hideListGridConfigurationButtons();
        getUiHandlers().goToConfigurations();
    }

    private void showEnableConfigurationButton() {
        if (ClientSecurityUtils.canUpdateConfiguration()) {
            enableToolStripButton.show();
            disableToolStripButton.hide();
        }
    }

    private void showDisableConfigurationButton() {
        if (ClientSecurityUtils.canUpdateConfiguration()) {
            enableToolStripButton.hide();
            disableToolStripButton.show();
        }
    }

    private void hideListGridConfigurationButtons() {
        enableToolStripButton.hide();
        disableToolStripButton.hide();
    }

    private boolean areSelectedConfigurationsEnabled() {
        boolean allConfigurationsEnabled = true;
        for (ListGridRecord record : configurationsListGrid.getSelectedRecords()) {
            ConfigurationRecord configurationRecord = (ConfigurationRecord) record;
            if (CommonMetadataStatusEnum.DISABLED.equals(configurationRecord.getConfigurationDto().getStatus())) {
                allConfigurationsEnabled = false;
            }
        }
        return allConfigurationsEnabled;
    }

    private boolean areSelectedConfigurationsDisabled() {
        boolean allConfigurationsDisabled = true;
        for (ListGridRecord record : configurationsListGrid.getSelectedRecords()) {
            ConfigurationRecord configurationRecord = (ConfigurationRecord) record;
            if (CommonMetadataStatusEnum.ENABLED.equals(configurationRecord.getConfigurationDto().getStatus())) {
                allConfigurationsDisabled = false;
            }
        }
        return allConfigurationsDisabled;
    }
}
