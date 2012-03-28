package org.siemac.metamac.common.metadata.web.client.view;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.model.ConfigurationRecord;
import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationPresenter;
import org.siemac.metamac.common.metadata.web.client.utils.RecordUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationUiHandlers;
import org.siemac.metamac.core.common.dto.serviceapi.ExternalItemBtDto;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextAndUrlItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextAndUrlItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
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

    private static final String             NAME                = "name";
    private static final String             LEGAL_ACTS          = "legal";
    private static final String             DATA_SHARING        = "sharing";
    private static final String             CONF_POLYCY         = "policy";
    private static final String             CONF_DATA_TREATMENT = "data";
    private static final String             ORGANISATION        = "organ";

    private List<ExternalItemBtDto>         organisations;

    private ConfigurationDto                configurationDto;

    private VLayout                         panel;
    private CustomListGrid                  configurationsGrid;
    private InternationalMainFormLayout     mainFormLayout;

    private ToolStripButton                 newToolStripButton;
    private ToolStripButton                 deleteToolStripButton;

    private Label                           confFormTitle;

    private GroupDynamicForm                staticForm;
    private GroupDynamicForm                form;

    // Static View Fields
    private ViewTextItem                    staticName;
    private ViewMultiLanguageTextAndUrlItem staticLegalActs;
    private ViewMultiLanguageTextAndUrlItem staticDataSharing;
    private ViewMultiLanguageTextAndUrlItem staticConfPolicy;
    private ViewMultiLanguageTextAndUrlItem staticConfDataTreatment;

    // Edition Fields
    private RequiredTextItem                name;
    private MultiLanguageTextAndUrlItem     legalActs;
    private MultiLanguageTextAndUrlItem     dataSharing;
    private MultiLanguageTextAndUrlItem     confPolicy;
    private MultiLanguageTextAndUrlItem     confDataTreatment;
    private ExternalSelectItem              organisationItem;

    private VLayout                         selectedConfLayout;

    private DeleteConfirmationWindow        deleteConfirmationWindow;

    @Inject
    public ConfigurationViewImpl() {
        super();

        panel = new VLayout();

        // ToolStrip

        newToolStripButton = new ToolStripButton(CommonMetadataWeb.getConstants().actionNew(), GlobalResources.RESOURCE.newListGrid().getURL());
        newToolStripButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                selectConfiguration(new ConfigurationDto());
            }
        });

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

        ToolStrip toolStrip = new ToolStrip();
        toolStrip.setWidth100();
        toolStrip.addButton(newToolStripButton);
        toolStrip.addSeparator();
        toolStrip.addButton(deleteToolStripButton);

        // ListGrid

        configurationsGrid = new CustomListGrid();
        configurationsGrid.setWidth100();
        configurationsGrid.setHeight(150);
        ListGridField nameField = new ListGridField(ConfigurationRecord.NAME, CommonMetadataWeb.getConstants().configurationName());
        configurationsGrid.setFields(nameField);
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
                        deleteToolStripButton.show();
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

        confFormTitle = new Label();
        confFormTitle.setStyleName("boldSubsectionTitle");
        confFormTitle.setAutoHeight();

        mainFormLayout = new InternationalMainFormLayout();
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
        selectedConfLayout.addMember(confFormTitle);
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
        for (ConfigurationDto configurationDto : configurations) {
            ConfigurationRecord record = RecordUtils.getConfigurationRecord(configurationDto);
            configurationsGrid.addData(record);
        }
    }

    @Override
    public void setOrganisationSchemes(List<ExternalItemBtDto> schemes) {
        organisationItem.setSchemesValueMap(ExternalItemUtils.getExternalItemsHashMap(schemes));
    }

    @Override
    public void setOrganisations(List<ExternalItemBtDto> organisations) {
        this.organisations = organisations;
        organisationItem.setItemsValueMap(ExternalItemUtils.getExternalItemsHashMap(organisations));
    }

    /**
     * Creates and returns the view layout
     * 
     * @return
     */
    private void createViewLayout() {
        staticName = new ViewTextItem(NAME, CommonMetadataWeb.getConstants().confName());
        staticName.setEndRow(true);
        staticLegalActs = new ViewMultiLanguageTextAndUrlItem(LEGAL_ACTS, CommonMetadataWeb.getConstants().confLegalActs());
        staticDataSharing = new ViewMultiLanguageTextAndUrlItem(DATA_SHARING, CommonMetadataWeb.getConstants().confDataSharing());
        staticConfPolicy = new ViewMultiLanguageTextAndUrlItem(CONF_POLYCY, CommonMetadataWeb.getConstants().confPolicy());
        staticConfDataTreatment = new ViewMultiLanguageTextAndUrlItem(CONF_DATA_TREATMENT, CommonMetadataWeb.getConstants().confDataTreatment());
        ViewTextItem staticOrganisation = new ViewTextItem(ORGANISATION, CommonMetadataWeb.getConstants().confOrganisation());

        staticForm = new GroupDynamicForm(CommonMetadataWeb.getConstants().configuration());
        staticForm.setFields(staticName, staticLegalActs, staticDataSharing, staticConfPolicy, staticConfDataTreatment, staticOrganisation);

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
        name = new RequiredTextItem(NAME, CommonMetadataWeb.getConstants().confName());
        name.setEndRow(true);
        legalActs = new MultiLanguageTextAndUrlItem(LEGAL_ACTS, CommonMetadataWeb.getConstants().confLegalActs());
        dataSharing = new MultiLanguageTextAndUrlItem(DATA_SHARING, CommonMetadataWeb.getConstants().confDataSharing());
        confPolicy = new MultiLanguageTextAndUrlItem(CONF_POLYCY, CommonMetadataWeb.getConstants().confPolicy());
        confDataTreatment = new MultiLanguageTextAndUrlItem(CONF_DATA_TREATMENT, CommonMetadataWeb.getConstants().confDataTreatment());
        organisationItem = new ExternalSelectItem(ORGANISATION, CommonMetadataWeb.getConstants().confOrganisation());
        organisationItem.getSchemeItem().addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                if (event.getValue() != null) {
                    getUiHandlers().populateOrganisations(event.getValue().toString());
                }
            }
        });
        form.setFields(name, legalActs, dataSharing, confPolicy, confDataTreatment, organisationItem);

        mainFormLayout.addEditionCanvas(form);

    }

    @Override
    public ConfigurationDto getConfiguration() {
        configurationDto.setName(name.getValueAsString());
        configurationDto.setLegalActs(legalActs.getTextValue());
        configurationDto.setLegalActsUrl(legalActs.getUrlValue());
        configurationDto.setDataSharing(dataSharing.getTextValue());
        configurationDto.setDataSharingUrl(dataSharing.getUrlValue());
        configurationDto.setConfPolicy(confPolicy.getTextValue());
        configurationDto.setConfPolicyUrl(confPolicy.getUrlValue());
        configurationDto.setConfDataTreatment(confDataTreatment.getTextValue());
        configurationDto.setConfDataTreatmentUrl(confDataTreatment.getUrlValue());
        configurationDto.setContact(organisationItem.getSelectedExternalItem(organisations));
        return configurationDto;
    }

    @Override
    public List<ConfigurationDto> getSelectedConfigurations() {
        if (configurationsGrid.getSelectedRecords() != null) {
            List<ConfigurationDto> selectedConfigurations = new ArrayList<ConfigurationDto>();
            ListGridRecord[] records = configurationsGrid.getSelectedRecords();
            for (int i = 0; i < records.length; i++) {
                ConfigurationRecord record = (ConfigurationRecord) records[i];
                selectedConfigurations.add(record.getConfigurationDto());
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
        staticName.setValue(configurationDto.getName());
        staticLegalActs.setValue(configurationDto.getLegalActs(), configurationDto.getLegalActsUrl());
        staticDataSharing.setValue(configurationDto.getDataSharing(), configurationDto.getDataSharingUrl());
        staticConfPolicy.setValue(configurationDto.getConfPolicy(), configurationDto.getConfPolicyUrl());
        staticConfDataTreatment.setValue(configurationDto.getConfDataTreatment(), configurationDto.getConfDataTreatmentUrl());
        staticForm.setValue(ORGANISATION, configurationDto.getContact() == null ? "" : configurationDto.getContact().getCodeId());
        staticForm.redraw();
    }

    private void setConfigurationEditionMode(ConfigurationDto configurationDto) {
        this.configurationDto = configurationDto;
        name.setValue(configurationDto.getName());
        legalActs.setValue(configurationDto.getLegalActs(), configurationDto.getLegalActsUrl());
        dataSharing.setValue(configurationDto.getDataSharing(), configurationDto.getDataSharingUrl());
        confPolicy.setValue(configurationDto.getConfPolicy(), configurationDto.getConfPolicyUrl());
        confDataTreatment.setValue(configurationDto.getConfDataTreatment(), configurationDto.getConfDataTreatmentUrl());
        organisationItem.setValue(configurationDto.getContact() == null ? null : configurationDto.getContact().getCodeId());
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
            // New attribute
            confFormTitle.setContents(new String());
            deleteToolStripButton.hide();
            configurationsGrid.deselectAllRecords();
            setConfigurationEditionMode(configurationSelected);
            mainFormLayout.setEditionMode();
        } else {
            confFormTitle.setContents(configurationSelected.getName());
            deleteToolStripButton.show();
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
    }

    private void setTranslationsShowed(boolean translationsShowed) {
        staticForm.setTranslationsShowed(translationsShowed);
        form.setTranslationsShowed(translationsShowed);
    }

}
