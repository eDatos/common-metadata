package org.siemac.metamac.common.metadata.web.client.view;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.model.ConfigurationRecord;
import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationPresenter;
import org.siemac.metamac.common.metadata.web.client.utils.RecordUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationUiHandlers;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.InternationalTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.fields.TextItem;
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

	private static final String NAME = "name";
	private static final String LEGAL_ACTS = "legal";
	private static final String LEGAL_ACTS_URL = "legal-url";
	private static final String DATA_SHARING = "sharing";
	private static final String DATA_SHARING_URL = "sharing-url";
	private static final String CONF_POLYCY = "policy";
	private static final String CONF_POLYCY_URL = "policy-url";
	private static final String CONF_DATA_TREATMENT = "data";
	private static final String CONF_DATA_TREATMENT_URL = "data-url";
	private static final String CONTACT = "contact";
	
	
	private ConfigurationDto configurationDto;
	
	private VLayout panel;
	private CustomListGrid configurationsGrid;
	
	private ToolStripButton newToolStripButton;
	private ToolStripButton editToolStripButton;
	private ToolStripButton saveToolStripButton;
	private ToolStripButton cancelToolStripButton;
	private ToolStripButton deleteToolStripButton;
	
	private Label confFormTitle;
	
	private VLayout viewLayout;
	private VLayout editionLayout;
	
	private GroupDynamicForm staticForm;
	private GroupDynamicForm form;
	
	// Static View Fields
	private ViewTextItem staticName;
	private InternationalTextItem staticLegalActs;
	private ViewTextItem staticLegalActsUrl;
	private InternationalTextItem staticDataSharing;
	private ViewTextItem staticDataSharingUrl;
	private InternationalTextItem staticConfPolicy;
	private ViewTextItem staticConfPolicyUrl;
	private InternationalTextItem staticConfDataTreatment;
	private ViewTextItem staticConfDataTreatmentUrl;
	
	// Edition Fields
	private RequiredTextItem name;
	private InternationalTextItem legalActs;
	private TextItem legalActsUrl;
	private InternationalTextItem dataSharing;
	private TextItem dataSharingUrl;
	private InternationalTextItem confPolicy;
	private TextItem confPolicyUrl;
	private InternationalTextItem confDataTreatment;
	private TextItem confDataTreatmentUrl;

	private VLayout selectedConfLayout;
	
	private DeleteConfirmationWindow deleteConfirmationWindow;
	
	
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
		
		editToolStripButton = new ToolStripButton(CommonMetadataWeb.getConstants().actionEdit(), GlobalResources.RESOURCE.editListGrid().getURL());
		editToolStripButton.setVisibility(Visibility.HIDDEN);
		editToolStripButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setEditionMode();
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
        toolStrip.addButton(editToolStripButton);
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
						// Delete more than one dimension with one click
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
		confFormTitle.setStyleName("subsectionTitle");
		confFormTitle.setAutoHeight();
		
		
		viewLayout = getViewLayout();
		editionLayout = getEditionLayout();
		
		selectedConfLayout = new VLayout(10);
		selectedConfLayout.setMargin(15);
		selectedConfLayout.addMember(confFormTitle);
		selectedConfLayout.addMember(viewLayout);
		selectedConfLayout.addMember(editionLayout);
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
	
	/**
	 * Creates and returns the view layout
	 * 
	 * @return
	 */
	private VLayout getViewLayout() {
		staticName = new ViewTextItem(NAME, CommonMetadataWeb.getConstants().confName());
		staticName.setEndRow(true);
		staticLegalActs = new InternationalTextItem(LEGAL_ACTS, CommonMetadataWeb.getConstants().confLegalActs(), true, false);
		staticLegalActsUrl = new ViewTextItem(LEGAL_ACTS_URL, CommonMetadataWeb.getConstants().confLegalActsUrl());
		staticDataSharing = new InternationalTextItem(DATA_SHARING, CommonMetadataWeb.getConstants().confDataSharing(), true, false);
		staticDataSharingUrl = new ViewTextItem(DATA_SHARING_URL, CommonMetadataWeb.getConstants().confDataSharingUrl());
		staticConfPolicy = new InternationalTextItem(CONF_POLYCY, CommonMetadataWeb.getConstants().confPolicy(), true, false);
		staticConfPolicyUrl = new ViewTextItem(CONF_POLYCY_URL, CommonMetadataWeb.getConstants().confPolicyUrl());
		staticConfDataTreatment = new InternationalTextItem(CONF_DATA_TREATMENT, CommonMetadataWeb.getConstants().confDataTreatment(), true, false);
		staticConfDataTreatmentUrl = new ViewTextItem(CONF_DATA_TREATMENT_URL, CommonMetadataWeb.getConstants().confDataTreatmentUrl());
		
		staticForm = new GroupDynamicForm(CommonMetadataWeb.getConstants().configuration());
		staticForm.setFields(staticName, staticLegalActs, staticLegalActsUrl, staticDataSharing, staticDataSharingUrl, staticConfPolicy, staticConfPolicyUrl, staticConfDataTreatment, staticConfDataTreatmentUrl);
		
		VLayout viewLayout = new VLayout(15);
		viewLayout.setAutoHeight();
		viewLayout.addMember(staticForm);
		
		return viewLayout;
	}
	
	
	/**
	 * Creates and returns the edition layout
	 * 
	 * @return
	 */
	private VLayout getEditionLayout() {
		
		// ·········
		// ToolStrip
		// ·········
		
		saveToolStripButton = new ToolStripButton(CommonMetadataWeb.getConstants().actionSave(), GlobalResources.RESOURCE.saveListGrid().getURL());
		
		cancelToolStripButton = new ToolStripButton(CommonMetadataWeb.getConstants().actionCancel(), GlobalResources.RESOURCE.cancelListGrid().getURL());
		cancelToolStripButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (configurationDto.getId() == null) {
					// Editing new configuration
					editionLayout.hide();
				} else {
					// Editing existing configuration
					setViewMode();
				}
			}
		});
		
		ToolStrip formToolStrip = new ToolStrip();
		formToolStrip.setWidth100();
		formToolStrip.addButton(saveToolStripButton);
		formToolStrip.addButton(cancelToolStripButton);
		
		// ····
		// Form
		// ····
		
		form = new GroupDynamicForm(CommonMetadataWeb.getConstants().configuration());
		name = new RequiredTextItem(NAME, CommonMetadataWeb.getConstants().confName());
		name.setEndRow(true);
		legalActs = new InternationalTextItem(LEGAL_ACTS, CommonMetadataWeb.getConstants().confLegalActs(), false, false);
		legalActsUrl = new TextItem(LEGAL_ACTS_URL, CommonMetadataWeb.getConstants().confLegalActsUrl());
		dataSharing = new InternationalTextItem(DATA_SHARING, CommonMetadataWeb.getConstants().confDataSharing(), false, false);
		dataSharingUrl = new TextItem(DATA_SHARING_URL, CommonMetadataWeb.getConstants().confDataSharingUrl());
		confPolicy = new InternationalTextItem(CONF_POLYCY, CommonMetadataWeb.getConstants().confPolicy(), false, false);
		confPolicyUrl = new TextItem(CONF_POLYCY_URL, CommonMetadataWeb.getConstants().confPolicyUrl());
		confDataTreatment = new InternationalTextItem(CONF_DATA_TREATMENT, CommonMetadataWeb.getConstants().confDataTreatment(), false, false);
		confDataTreatmentUrl = new TextItem(CONF_DATA_TREATMENT, CommonMetadataWeb.getConstants().confDataTreatmentUrl());
		form.setFields(name, legalActs, legalActsUrl, dataSharing, dataSharingUrl, confPolicy, confPolicyUrl, confDataTreatment, confDataTreatmentUrl);

		VLayout formLayout = new VLayout(15);
		formLayout.setMargin(10);
		formLayout.addMember(form);
		
		VLayout editionLayout = new VLayout();
		editionLayout.setVisibility(Visibility.HIDDEN);
		editionLayout.setBorder("1px solid #d9d9d9");
		editionLayout.setAutoHeight();
		editionLayout.addMember(formToolStrip);
		editionLayout.addMember(formLayout);
		
		return editionLayout;
	}

	@Override
	public ConfigurationDto getConfiguration() {
		configurationDto.setName(name.getValueAsString());
		configurationDto.setLegalActs(legalActs.getValue(configurationDto.getLegalActs()));
		configurationDto.setLegalActsUrl(legalActsUrl.getValueAsString());
		configurationDto.setDataSharing(dataSharing.getValue(configurationDto.getDataSharing()));
		configurationDto.setDataSharingUrl(dataSharingUrl.getValueAsString());
		configurationDto.setConfPolicy(confPolicy.getValue(configurationDto.getConfPolicy()));
		configurationDto.setConfPolicyUrl(confPolicyUrl.getValueAsString());
		return configurationDto;
	}

	@Override
	public List<ConfigurationDto> getSelectedConfiguration() {
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
		staticLegalActs.setValue(configurationDto.getLegalActs());
		staticLegalActsUrl.setValue(configurationDto.getLegalActsUrl());
		staticDataSharing.setValue(configurationDto.getDataSharing());
		staticDataSharingUrl.setValue(configurationDto.getDataSharingUrl());
		staticConfPolicy.setValue(configurationDto.getConfPolicy());
		staticConfPolicyUrl.setValue(configurationDto.getConfPolicyUrl());
		staticConfDataTreatment.setValue(configurationDto.getConfDataTreatment());
		staticConfDataTreatmentUrl.setValue(configurationDto.getConfDataTreatmentUrl());
	}
	
	private void setConfigurationEditionMode(ConfigurationDto configurationDto) {
		this.configurationDto = configurationDto;
		
		name.setValue(configurationDto.getName());
		legalActs.setValue(configurationDto.getLegalActs());
		legalActsUrl.setValue(configurationDto.getLegalActsUrl());
		dataSharing.setValue(configurationDto.getDataSharing());
		dataSharingUrl.setValue(configurationDto.getDataSharingUrl());
		confPolicy.setValue(configurationDto.getConfPolicy());
		confPolicyUrl.setValue(configurationDto.getConfPolicyUrl());
		confDataTreatment.setValue(configurationDto.getConfDataTreatment());
		confDataTreatmentUrl.setValue(configurationDto.getConfDataTreatmentUrl());
	}
	
	@Override
	public boolean validate() {
		return form.validate(false);
	}

	@Override
	public HasClickHandlers getSave() {
		return saveToolStripButton;
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
	}
	
	private void selectConfiguration(ConfigurationDto configurationSelected) {
		if (configurationSelected.getId() == null) {
			// New attribute
			confFormTitle.setContents(new String());
			deleteToolStripButton.hide();
			editToolStripButton.hide();
			configurationsGrid.deselectAllRecords();
			setEditionMode();
			setConfigurationEditionMode(configurationSelected);
		} else {
			confFormTitle.setContents(configurationSelected.getName());
			deleteToolStripButton.show();
			editToolStripButton.show();
			setViewMode();
			setConfiguration(configurationSelected);
		}
		
		// Clear errors
		form.clearErrors(true);
		
		selectedConfLayout.show();
		selectedConfLayout.redraw();
	}
	
	private void deselectAttribute() {
		selectedConfLayout.hide();
		deleteToolStripButton.hide();
		editToolStripButton.hide();
	}
	
	/**
	 * Set edition mode
	 */
	private void setEditionMode() {
		viewLayout.hide();
		editionLayout.show();
		
		editToolStripButton.hide();
		deleteToolStripButton.hide();
	}
	
	/**
	 * Set view mode
	 */
	private void setViewMode() {
		viewLayout.show();
		editionLayout.hide();
		
		editToolStripButton.show();
		deleteToolStripButton.show();
	}

}
