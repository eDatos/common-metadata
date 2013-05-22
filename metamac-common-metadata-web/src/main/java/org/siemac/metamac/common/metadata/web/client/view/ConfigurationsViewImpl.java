package org.siemac.metamac.common.metadata.web.client.view;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.model.ConfigurationRecord;
import org.siemac.metamac.common.metadata.web.client.model.ds.ConfigurationDS;
import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationsPresenter;
import org.siemac.metamac.common.metadata.web.client.utils.ClientSecurityUtils;
import org.siemac.metamac.common.metadata.web.client.utils.CommonUtils;
import org.siemac.metamac.common.metadata.web.client.utils.RecordUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationsUiHandlers;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultilanguageRichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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

    private List<ExternalItemDto>       organisations;

    private ConfigurationDto            configurationDto;

    private CustomListGrid              configurationsListGrid;
    private InternationalMainFormLayout mainFormLayout;

    private ToolStripButton             newToolStripButton;
    private ToolStripButton             deleteToolStripButton;
    private ToolStripButton             enableToolStripButton;
    private ToolStripButton             disableToolStripButton;

    private GroupDynamicForm            form;
    private GroupDynamicForm            editionForm;

    private VLayout                     selectedConfLayout;

    private DeleteConfirmationWindow    deleteConfirmationWindow;

    @Inject
    public ConfigurationsViewImpl() {
        super();

        panel = new VLayout();
        panel.setOverflow(Overflow.SCROLL);

        // ToolStrip

        newToolStripButton = new ToolStripButton(CommonMetadataWeb.getConstants().actionNew(), GlobalResources.RESOURCE.newListGrid().getURL());
        newToolStripButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                selectConfiguration(new ConfigurationDto());
            }
        });
        newToolStripButton.setVisibility(ClientSecurityUtils.canCreateConfiguration() ? Visibility.VISIBLE : Visibility.HIDDEN);

        deleteConfirmationWindow = new DeleteConfirmationWindow(CommonMetadataWeb.getConstants().confDeleteConfirmationTitle(), CommonMetadataWeb.getConstants().confDeleteConfirmation());
        deleteConfirmationWindow.setVisibility(Visibility.HIDDEN);
        deleteConfirmationWindow.getYesButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().deleteConfigurations(getSelectedConfigurations());
            }
        });

        deleteToolStripButton = new ToolStripButton(CommonMetadataWeb.getConstants().actionDelete(), GlobalResources.RESOURCE.deleteListGrid().getURL());
        deleteToolStripButton.setVisibility(Visibility.HIDDEN);
        deleteToolStripButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                deleteConfirmationWindow.show();
            }
        });

        enableToolStripButton = new ToolStripButton(CommonMetadataWeb.getConstants().confEnable(), GlobalResources.RESOURCE.success().getURL());
        enableToolStripButton.setVisibility(Visibility.HIDDEN);
        enableToolStripButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().updateConfigurationsStatus(getSelectedConfigurations(), CommonMetadataStatusEnum.ENABLED);
            }
        });

        disableToolStripButton = new ToolStripButton(CommonMetadataWeb.getConstants().confDisable(), GlobalResources.RESOURCE.disable().getURL());
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
        toolStrip.addButton(deleteToolStripButton);
        toolStrip.addSeparator();
        toolStrip.addButton(enableToolStripButton);
        toolStrip.addButton(disableToolStripButton);

        // ListGrid

        configurationsListGrid = new CustomListGrid();
        configurationsListGrid.setWidth100();
        configurationsListGrid.setHeight(150);
        ListGridField codeField = new ListGridField(ConfigurationDS.CODE, CommonMetadataWeb.getConstants().configurationIdentifier());
        ListGridField status = new ListGridField(ConfigurationDS.STATUS, CommonMetadataWeb.getCoreMessages().commonMetadataStatusEnumENABLED());
        status.setType(ListGridFieldType.IMAGE);
        status.setWidth("20%");
        configurationsListGrid.setFields(codeField, status);
        // Show configuration details when record clicked
        configurationsListGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                if (configurationsListGrid.getSelectedRecords() != null && configurationsListGrid.getSelectedRecords().length == 1) {
                    ConfigurationRecord record = (ConfigurationRecord) configurationsListGrid.getSelectedRecord();
                    ConfigurationDto configurationDtoDto = record.getConfigurationDto();
                    selectConfiguration(configurationDtoDto);
                } else {
                    // No record selected
                    deselectAttribute();
                    if (configurationsListGrid.getSelectedRecords().length > 1) {
                        // Delete more than one configuration with one click
                        showDeleteConfigurationButton();
                        // If all selected configurations are ENABLED, show disableConfigurationButton
                        if (areSelectedConfigurationsEnabled()) {
                            showDisableConfigurationButton();
                        }
                        // If all selected configurations are DISABLED, show enableConfigurationButton
                        if (areSelectedConfigurationsDisabled()) {
                            showEnableConfigurationButton();
                        }
                    }
                }
            }
        });
        configurationsListGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (event.getFieldNum() != 0) { // CheckBox is not clicked
                    configurationsListGrid.deselectAllRecords();
                    configurationsListGrid.selectRecord(event.getRecord());
                }
            }
        });

        VLayout gridLayout = new VLayout();
        gridLayout.setAutoHeight();
        gridLayout.setMargin(10);
        gridLayout.addMember(toolStrip);
        gridLayout.addMember(configurationsListGrid);

        // ··················
        // Configuration Form
        // ··················

        // Title

        mainFormLayout = new InternationalMainFormLayout(ClientSecurityUtils.canUpdateConfiguration());
        mainFormLayout.getTranslateToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                setTranslationsShowed(mainFormLayout.getTranslateToolStripButton().isSelected());
            }
        });
        mainFormLayout.getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (validate()) {
                    getUiHandlers().saveConfiguration(getConfiguration());
                }
            }
        });
        mainFormLayout.getCancelToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // If it is a new dimension, hide mainFormLayout
                if (configurationDto.getId() == null) {
                    selectedConfLayout.hide();
                }
            }
        });

        createViewForm();
        createEditionForm();

        selectedConfLayout = new VLayout(10);
        selectedConfLayout.addMember(mainFormLayout);
        selectedConfLayout.setVisibility(Visibility.HIDDEN);

        panel.addMember(gridLayout);
        panel.addMember(selectedConfLayout);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setConfigurations(List<ConfigurationDto> configurations) {
        configurationsListGrid.removeAllData();
        hideStatusConfigurationButtons();
        for (ConfigurationDto configurationDto : configurations) {
            ConfigurationRecord record = RecordUtils.getConfigurationRecord(configurationDto);
            configurationsListGrid.addData(record);
        }
    }

    @Override
    public void setOrganisationSchemes(List<ExternalItemDto> schemes) {
        ((ExternalSelectItem) editionForm.getItem(ConfigurationDS.ORGANISATION)).setSchemesValueMap(ExternalItemUtils.getExternalItemsHashMap(schemes));
    }

    @Override
    public void setOrganisations(List<ExternalItemDto> organisations) {
        this.organisations = organisations;
        ((ExternalSelectItem) editionForm.getItem(ConfigurationDS.ORGANISATION)).setItemsValueMap(ExternalItemUtils.getExternalItemsHashMap(organisations));
    }

    /**
     * Creates and returns the view layout
     * 
     * @return
     */
    private void createViewForm() {
        ViewTextItem staticCode = new ViewTextItem(ConfigurationDS.CODE, CommonMetadataWeb.getConstants().confCode());
        ViewTextItem staticOrganisation = new ViewTextItem(ConfigurationDS.ORGANISATION, CommonMetadataWeb.getConstants().confOrganisation());
        ViewTextItem status = new ViewTextItem(ConfigurationDS.STATUS, CommonMetadataWeb.getConstants().confStatus());
        ViewMultiLanguageTextItem staticLegalActs = new ViewMultiLanguageTextItem(ConfigurationDS.LEGAL_ACTS, CommonMetadataWeb.getConstants().confLegalActs());
        ViewMultiLanguageTextItem staticDataSharing = new ViewMultiLanguageTextItem(ConfigurationDS.DATA_SHARING, CommonMetadataWeb.getConstants().confDataSharing());
        ViewMultiLanguageTextItem staticConfPolicy = new ViewMultiLanguageTextItem(ConfigurationDS.CONF_POLYCY, CommonMetadataWeb.getConstants().confPolicy());
        ViewMultiLanguageTextItem staticConfDataTreatment = new ViewMultiLanguageTextItem(ConfigurationDS.CONF_DATA_TREATMENT, CommonMetadataWeb.getConstants().confDataTreatment());

        form = new GroupDynamicForm(CommonMetadataWeb.getConstants().configuration());
        form.setFields(staticCode, staticOrganisation, status, staticLegalActs, staticDataSharing, staticConfPolicy, staticConfDataTreatment);

        mainFormLayout.addViewCanvas(form);
    }

    /**
     * Creates and returns the edition layout
     * 
     * @return
     */
    private void createEditionForm() {

        // ·········
        // ToolStrip
        // ·········

        ToolStrip formToolStrip = new ToolStrip();
        formToolStrip.setWidth100();

        // ····
        // Form
        // ····

        editionForm = new GroupDynamicForm(CommonMetadataWeb.getConstants().configuration());

        RequiredTextItem code = new RequiredTextItem(ConfigurationDS.CODE, CommonMetadataWeb.getConstants().confCode());
        code.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        code.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                // Code cannot be edited
                return configurationDto.getId() == null;
            }
        });

        ViewTextItem staticCode = new ViewTextItem(ConfigurationDS.STATIC_CODE, CommonMetadataWeb.getConstants().confCode());
        staticCode.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                // Code cannot be edited
                return configurationDto.getId() != null;
            }
        });

        ExternalSelectItem organisationItem = new ExternalSelectItem(ConfigurationDS.ORGANISATION, CommonMetadataWeb.getConstants().confOrganisation());
        organisationItem.getSchemeItem().addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                if (event.getValue() != null) {
                    getUiHandlers().populateOrganisations(event.getValue().toString());
                }
            }
        });

        CustomSelectItem status = new CustomSelectItem(ConfigurationDS.STATUS, CommonMetadataWeb.getConstants().confStatus());
        status.setRequired(true);
        status.setValueMap(CommonUtils.getCommonMetadataStatusEnumHashMap());

        MultilanguageRichTextEditorItem legalActs = new MultilanguageRichTextEditorItem(ConfigurationDS.LEGAL_ACTS, CommonMetadataWeb.getConstants().confLegalActs());
        MultilanguageRichTextEditorItem dataSharing = new MultilanguageRichTextEditorItem(ConfigurationDS.DATA_SHARING, CommonMetadataWeb.getConstants().confDataSharing());
        MultilanguageRichTextEditorItem confPolicy = new MultilanguageRichTextEditorItem(ConfigurationDS.CONF_POLYCY, CommonMetadataWeb.getConstants().confPolicy());
        MultilanguageRichTextEditorItem confDataTreatment = new MultilanguageRichTextEditorItem(ConfigurationDS.CONF_DATA_TREATMENT, CommonMetadataWeb.getConstants().confDataTreatment());

        editionForm.setFields(staticCode, code, organisationItem, status, legalActs, dataSharing, confPolicy, confDataTreatment);

        mainFormLayout.addEditionCanvas(editionForm);
    }

    @Override
    public ConfigurationDto getConfiguration() {
        configurationDto.setCode(editionForm.getValueAsString(ConfigurationDS.CODE));
        configurationDto.setContact(((ExternalSelectItem) editionForm.getItem(ConfigurationDS.ORGANISATION)).getSelectedExternalItem(organisations));
        configurationDto.setStatus(editionForm.getValueAsString(ConfigurationDS.STATUS) != null ? CommonMetadataStatusEnum.valueOf(editionForm.getValueAsString(ConfigurationDS.STATUS)) : null);
        configurationDto.setLegalActs((InternationalStringDto) editionForm.getValue(ConfigurationDS.LEGAL_ACTS));
        configurationDto.setDataSharing((InternationalStringDto) editionForm.getValue(ConfigurationDS.DATA_SHARING));
        configurationDto.setConfPolicy((InternationalStringDto) editionForm.getValue(ConfigurationDS.CONF_POLYCY));
        configurationDto.setConfDataTreatment((InternationalStringDto) editionForm.getValue(ConfigurationDS.CONF_DATA_TREATMENT));
        return configurationDto;
    }

    @Override
    public List<Long> getSelectedConfigurations() {
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
    public void setConfiguration(ConfigurationDto configurationDto) {
        setConfigurationViewMode(configurationDto);
        setConfigurationEditionMode(configurationDto);
    }

    private void setConfigurationViewMode(ConfigurationDto configurationDto) {
        this.configurationDto = configurationDto;
        form.setValue(ConfigurationDS.CODE, configurationDto.getCode());
        form.setValue(ConfigurationDS.ORGANISATION, configurationDto.getContact() == null ? new String() : configurationDto.getContact().getUrn());
        form.setValue(
                ConfigurationDS.STATUS,
                configurationDto.getStatus() == null ? new String() : CommonMetadataWeb.getCoreMessages().getString(
                        CommonMetadataWeb.getCoreMessages().commonMetadataStatusEnum() + configurationDto.getStatus().toString()));
        form.setValue(ConfigurationDS.LEGAL_ACTS, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getLegalActs()));
        form.setValue(ConfigurationDS.DATA_SHARING, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getDataSharing()));
        form.setValue(ConfigurationDS.CONF_POLYCY, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getConfPolicy()));
        form.setValue(ConfigurationDS.CONF_DATA_TREATMENT, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getConfDataTreatment()));
        form.redraw();
    }

    private void setConfigurationEditionMode(ConfigurationDto configurationDto) {
        this.configurationDto = configurationDto;
        editionForm.setValue(ConfigurationDS.STATIC_CODE, configurationDto.getCode());
        editionForm.setValue(ConfigurationDS.CODE, configurationDto.getCode());
        editionForm.setValue(ConfigurationDS.ORGANISATION, configurationDto.getContact() == null ? null : configurationDto.getContact().getUrn());
        editionForm.setValue(ConfigurationDS.STATUS, configurationDto.getStatus() != null ? configurationDto.getStatus().toString() : new String());
        editionForm.setValue(ConfigurationDS.LEGAL_ACTS, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getLegalActs()));
        editionForm.setValue(ConfigurationDS.DATA_SHARING, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getDataSharing()));
        editionForm.setValue(ConfigurationDS.CONF_POLYCY, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getConfPolicy()));
        editionForm.setValue(ConfigurationDS.CONF_DATA_TREATMENT, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getConfDataTreatment()));
    }

    @Override
    public boolean validate() {
        return editionForm.validate(false);
    }

    @Override
    public HasClickHandlers getDelete() {
        return deleteConfirmationWindow.getYesButton();
    }

    @Override
    public void onConfigurationSaved(ConfigurationDto configurationDto) {
        this.configurationDto = configurationDto;
        configurationsListGrid.removeSelectedData();
        ConfigurationRecord record = RecordUtils.getConfigurationRecord(configurationDto);
        configurationsListGrid.addData(record);
        configurationsListGrid.selectRecord(record);
        mainFormLayout.setViewMode();
    }

    private void selectConfiguration(ConfigurationDto configurationSelected) {
        if (configurationSelected.getId() == null) {
            // New configuration
            mainFormLayout.setTitleLabelContents(new String());
            deleteToolStripButton.hide();
            hideStatusConfigurationButtons();
            configurationsListGrid.deselectAllRecords();
            setConfigurationEditionMode(configurationSelected);
            mainFormLayout.setEditionMode();
        } else {
            mainFormLayout.setTitleLabelContents(configurationSelected.getCode());
            showDeleteConfigurationButton();
            if (CommonMetadataStatusEnum.ENABLED.equals(configurationSelected.getStatus())) {
                showDisableConfigurationButton();
            } else if (CommonMetadataStatusEnum.DISABLED.equals(configurationSelected.getStatus())) {
                showEnableConfigurationButton();
            }
            setConfiguration(configurationSelected);
            mainFormLayout.setViewMode();
        }

        // Clear errors
        editionForm.clearErrors(true);

        selectedConfLayout.show();
        selectedConfLayout.redraw();
    }

    private void deselectAttribute() {
        selectedConfLayout.hide();
        deleteToolStripButton.hide();
        hideStatusConfigurationButtons();
    }

    private void setTranslationsShowed(boolean translationsShowed) {
        form.setTranslationsShowed(translationsShowed);
        editionForm.setTranslationsShowed(translationsShowed);
    }

    private void showDeleteConfigurationButton() {
        if (ClientSecurityUtils.canDeleteConfiguration()) {
            deleteToolStripButton.show();
        }
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

    private void hideStatusConfigurationButtons() {
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
