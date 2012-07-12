package org.siemac.metamac.common.metadata.web.client.view;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.model.ConfigurationRecord;
import org.siemac.metamac.common.metadata.web.client.model.ds.ConfigurationDS;
import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationPresenter;
import org.siemac.metamac.common.metadata.web.client.utils.ClientSecurityUtils;
import org.siemac.metamac.common.metadata.web.client.utils.CommonUtils;
import org.siemac.metamac.common.metadata.web.client.utils.RecordUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationUiHandlers;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
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

public class ConfigurationViewImpl extends ViewWithUiHandlers<ConfigurationUiHandlers> implements ConfigurationPresenter.ConfigurationView {

    private List<ExternalItemDto>           organisations;

    private ConfigurationDto                configurationDto;

    private VLayout                         panel;
    private CustomListGrid                  configurationsGrid;
    private InternationalMainFormLayout     mainFormLayout;

    private ToolStripButton                 newToolStripButton;
    private ToolStripButton                 deleteToolStripButton;
    private ToolStripButton                 enableToolStripButton;
    private ToolStripButton                 disableToolStripButton;

    private GroupDynamicForm                staticForm;
    private GroupDynamicForm                form;

    // Static View Fields
    private ViewTextItem                    staticCode;
    private ViewMultiLanguageTextItem       staticLegalActs;
    private ViewMultiLanguageTextItem       staticDataSharing;
    private ViewMultiLanguageTextItem       staticConfPolicy;
    private ViewMultiLanguageTextItem       staticConfDataTreatment;

    // Edition Fields
    private RequiredTextItem                code;
    private MultilanguageRichTextEditorItem legalActs;
    private MultilanguageRichTextEditorItem dataSharing;
    private MultilanguageRichTextEditorItem confPolicy;
    private MultilanguageRichTextEditorItem confDataTreatment;
    private ExternalSelectItem              organisationItem;

    private VLayout                         selectedConfLayout;

    private DeleteConfirmationWindow        deleteConfirmationWindow;

    @Inject
    public ConfigurationViewImpl() {
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

        configurationsGrid = new CustomListGrid();
        configurationsGrid.setWidth100();
        configurationsGrid.setHeight(150);
        ListGridField codeField = new ListGridField(ConfigurationDS.CODE, CommonMetadataWeb.getConstants().configurationIdentifier());
        ListGridField status = new ListGridField(ConfigurationDS.STATUS, CommonMetadataWeb.getCoreMessages().commonMetadataStatusEnumENABLED());
        status.setType(ListGridFieldType.IMAGE);
        status.setWidth("20%");
        configurationsGrid.setFields(codeField, status);
        // Show configuration details when record clicked
        configurationsGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                if (configurationsGrid.getSelectedRecords() != null && configurationsGrid.getSelectedRecords().length == 1) {
                    ConfigurationRecord record = (ConfigurationRecord) configurationsGrid.getSelectedRecord();
                    ConfigurationDto configurationDtoDto = record.getConfigurationDto();
                    selectConfiguration(configurationDtoDto);
                } else {
                    // No record selected
                    deselectAttribute();
                    if (configurationsGrid.getSelectedRecords().length > 1) {
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
        configurationsGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (event.getFieldNum() != 0) { // CheckBox is not clicked
                    configurationsGrid.deselectAllRecords();
                    configurationsGrid.selectRecord(event.getRecord());
                }
            }
        });

        VLayout gridLayout = new VLayout();
        gridLayout.setAutoHeight();
        gridLayout.setMargin(10);
        gridLayout.addMember(toolStrip);
        gridLayout.addMember(configurationsGrid);

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
        mainFormLayout.getCancelToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // If it is a new dimension, hide mainFormLayout
                if (configurationDto.getId() == null) {
                    selectedConfLayout.hide();
                }
            }
        });

        createViewLayout();
        createEditionLayout();

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
        configurationsGrid.removeAllData();
        hideStatusConfigurationButtons();
        for (ConfigurationDto configurationDto : configurations) {
            ConfigurationRecord record = RecordUtils.getConfigurationRecord(configurationDto);
            configurationsGrid.addData(record);
        }
    }

    @Override
    public void setOrganisationSchemes(List<ExternalItemDto> schemes) {
        organisationItem.setSchemesValueMap(ExternalItemUtils.getExternalItemsHashMap(schemes));
    }

    @Override
    public void setOrganisations(List<ExternalItemDto> organisations) {
        this.organisations = organisations;
        organisationItem.setItemsValueMap(ExternalItemUtils.getExternalItemsHashMap(organisations));
    }

    /**
     * Creates and returns the view layout
     * 
     * @return
     */
    private void createViewLayout() {
        staticCode = new ViewTextItem(ConfigurationDS.CODE, CommonMetadataWeb.getConstants().confCode());
        ViewTextItem staticOrganisation = new ViewTextItem(ConfigurationDS.ORGANISATION, CommonMetadataWeb.getConstants().confOrganisation());
        ViewTextItem status = new ViewTextItem(ConfigurationDS.STATUS, CommonMetadataWeb.getConstants().confStatus());
        staticLegalActs = new ViewMultiLanguageTextItem(ConfigurationDS.LEGAL_ACTS, CommonMetadataWeb.getConstants().confLegalActs());
        staticDataSharing = new ViewMultiLanguageTextItem(ConfigurationDS.DATA_SHARING, CommonMetadataWeb.getConstants().confDataSharing());
        staticConfPolicy = new ViewMultiLanguageTextItem(ConfigurationDS.CONF_POLYCY, CommonMetadataWeb.getConstants().confPolicy());
        staticConfDataTreatment = new ViewMultiLanguageTextItem(ConfigurationDS.CONF_DATA_TREATMENT, CommonMetadataWeb.getConstants().confDataTreatment());

        staticForm = new GroupDynamicForm(CommonMetadataWeb.getConstants().configuration());
        staticForm.setFields(staticCode, staticOrganisation, status, staticLegalActs, staticDataSharing, staticConfPolicy, staticConfDataTreatment);

        mainFormLayout.addViewCanvas(staticForm);
    }

    /**
     * Creates and returns the edition layout
     * 
     * @return
     */
    private void createEditionLayout() {

        // ·········
        // ToolStrip
        // ·········

        ToolStrip formToolStrip = new ToolStrip();
        formToolStrip.setWidth100();

        // ····
        // Form
        // ····

        form = new GroupDynamicForm(CommonMetadataWeb.getConstants().configuration());

        code = new RequiredTextItem(ConfigurationDS.CODE, CommonMetadataWeb.getConstants().confCode());
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

        organisationItem = new ExternalSelectItem(ConfigurationDS.ORGANISATION, CommonMetadataWeb.getConstants().confOrganisation());
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

        legalActs = new MultilanguageRichTextEditorItem(ConfigurationDS.LEGAL_ACTS, CommonMetadataWeb.getConstants().confLegalActs());
        dataSharing = new MultilanguageRichTextEditorItem(ConfigurationDS.DATA_SHARING, CommonMetadataWeb.getConstants().confDataSharing());
        confPolicy = new MultilanguageRichTextEditorItem(ConfigurationDS.CONF_POLYCY, CommonMetadataWeb.getConstants().confPolicy());
        confDataTreatment = new MultilanguageRichTextEditorItem(ConfigurationDS.CONF_DATA_TREATMENT, CommonMetadataWeb.getConstants().confDataTreatment());

        form.setFields(staticCode, code, organisationItem, status, legalActs, dataSharing, confPolicy, confDataTreatment);

        mainFormLayout.addEditionCanvas(form);
    }

    @Override
    public ConfigurationDto getConfiguration() {
        configurationDto.setCode(code.getValueAsString());
        configurationDto.setContact(organisationItem.getSelectedExternalItem(organisations));
        configurationDto.setStatus(form.getValueAsString(ConfigurationDS.STATUS) != null ? CommonMetadataStatusEnum.valueOf(form.getValueAsString(ConfigurationDS.STATUS)) : null);
        configurationDto.setLegalActs(legalActs.getValue());
        configurationDto.setDataSharing(dataSharing.getValue());
        configurationDto.setConfPolicy(confPolicy.getValue());
        configurationDto.setConfDataTreatment(confDataTreatment.getValue());
        return configurationDto;
    }

    @Override
    public List<Long> getSelectedConfigurations() {
        if (configurationsGrid.getSelectedRecords() != null) {
            List<Long> selectedConfigurations = new ArrayList<Long>();
            ListGridRecord[] records = configurationsGrid.getSelectedRecords();
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
        staticCode.setValue(configurationDto.getCode());
        staticForm.setValue(ConfigurationDS.ORGANISATION, configurationDto.getContact() == null ? new String() : configurationDto.getContact().getUrn());
        staticForm.setValue(
                ConfigurationDS.STATUS,
                configurationDto.getStatus() == null ? new String() : CommonMetadataWeb.getCoreMessages().getString(
                        CommonMetadataWeb.getCoreMessages().commonMetadataStatusEnum() + configurationDto.getStatus().toString()));
        staticLegalActs.setValue(configurationDto.getLegalActs());
        staticDataSharing.setValue(configurationDto.getDataSharing());
        staticConfPolicy.setValue(configurationDto.getConfPolicy());
        staticConfDataTreatment.setValue(configurationDto.getConfDataTreatment());
        staticForm.redraw();
    }

    private void setConfigurationEditionMode(ConfigurationDto configurationDto) {
        this.configurationDto = configurationDto;
        form.setValue(ConfigurationDS.STATIC_CODE, configurationDto.getCode());
        code.setValue(configurationDto.getCode());
        organisationItem.setValue(configurationDto.getContact() == null ? null : configurationDto.getContact().getUrn());
        form.setValue(ConfigurationDS.STATUS, configurationDto.getStatus() != null ? configurationDto.getStatus().toString() : new String());
        form.setValue(ConfigurationDS.LEGAL_ACTS, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getLegalActs()));
        form.setValue(ConfigurationDS.DATA_SHARING, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getDataSharing()));
        form.setValue(ConfigurationDS.CONF_POLYCY, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getConfPolicy()));
        form.setValue(ConfigurationDS.CONF_DATA_TREATMENT, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getConfDataTreatment()));
    }

    @Override
    public boolean validate() {
        return form.validate(false);
    }

    @Override
    public HasClickHandlers getSave() {
        return mainFormLayout.getSave();
    }

    @Override
    public HasClickHandlers getDelete() {
        return deleteConfirmationWindow.getYesButton();
    }

    @Override
    public void onConfigurationSaved(ConfigurationDto configurationDto) {
        this.configurationDto = configurationDto;
        configurationsGrid.removeSelectedData();
        ConfigurationRecord record = RecordUtils.getConfigurationRecord(configurationDto);
        configurationsGrid.addData(record);
        configurationsGrid.selectRecord(record);
        mainFormLayout.setViewMode();
    }

    private void selectConfiguration(ConfigurationDto configurationSelected) {
        if (configurationSelected.getId() == null) {
            // New configuration
            mainFormLayout.setTitleLabelContents(new String());
            deleteToolStripButton.hide();
            hideStatusConfigurationButtons();
            configurationsGrid.deselectAllRecords();
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
        form.clearErrors(true);

        selectedConfLayout.show();
        selectedConfLayout.redraw();
    }

    private void deselectAttribute() {
        selectedConfLayout.hide();
        deleteToolStripButton.hide();
        hideStatusConfigurationButtons();
    }

    private void setTranslationsShowed(boolean translationsShowed) {
        staticForm.setTranslationsShowed(translationsShowed);
        form.setTranslationsShowed(translationsShowed);
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
        for (ListGridRecord record : configurationsGrid.getSelectedRecords()) {
            ConfigurationRecord configurationRecord = (ConfigurationRecord) record;
            if (CommonMetadataStatusEnum.DISABLED.equals(configurationRecord.getConfigurationDto().getStatus())) {
                allConfigurationsEnabled = false;
            }
        }
        return allConfigurationsEnabled;
    }

    private boolean areSelectedConfigurationsDisabled() {
        boolean allConfigurationsDisabled = true;
        for (ListGridRecord record : configurationsGrid.getSelectedRecords()) {
            ConfigurationRecord configurationRecord = (ConfigurationRecord) record;
            if (CommonMetadataStatusEnum.ENABLED.equals(configurationRecord.getConfigurationDto().getStatus())) {
                allConfigurationsDisabled = false;
            }
        }
        return allConfigurationsDisabled;
    }

}
