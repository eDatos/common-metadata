package org.siemac.metamac.common.metadata.web.client.view;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.model.ds.ConfigurationDS;
import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationPresenter;
import org.siemac.metamac.common.metadata.web.client.utils.ClientSecurityUtils;
import org.siemac.metamac.common.metadata.web.client.utils.CommonUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationUiHandlers;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class ConfigurationViewImpl extends ViewWithUiHandlers<ConfigurationUiHandlers> implements ConfigurationPresenter.ConfigurationView {

    private InternationalMainFormLayout mainFormLayout;

    private GroupDynamicForm            form;
    private GroupDynamicForm            editionForm;

    private ConfigurationDto            configurationDto;
    private List<ExternalItemDto>       organisations;

    @Inject
    public ConfigurationViewImpl() {
        super();

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
                if (validateForm()) {
                    getUiHandlers().saveConfiguration(getConfiguration());
                }
            }
        });
        mainFormLayout.getCancelToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // If it is a new dimension, hide mainFormLayout
                if (configurationDto.getId() == null) {
                    // TODO
                    // configurationLayout.hide();
                }
            }
        });

        createViewForm();
        createEditionForm();

    }

    @Override
    public Widget asWidget() {
        return mainFormLayout;
    }

    private void setTranslationsShowed(boolean translationsShowed) {
        form.setTranslationsShowed(translationsShowed);
        editionForm.setTranslationsShowed(translationsShowed);
    }

    public boolean validateForm() {
        return editionForm.validate(false);
    }

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

    private ConfigurationDto getConfiguration() {
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
    public void setConfiguration(ConfigurationDto configurationDto) {
        this.configurationDto = configurationDto;
        mainFormLayout.setTitleLabelContents(configurationDto.getCode());
        mainFormLayout.setViewMode();
        setConfigurationViewMode(configurationDto);
        setConfigurationEditionMode(configurationDto);
        mainFormLayout.show();
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
    public void setOrganisationSchemes(List<ExternalItemDto> schemes) {
        ((ExternalSelectItem) editionForm.getItem(ConfigurationDS.ORGANISATION)).setSchemesValueMap(ExternalItemUtils.getExternalItemsHashMap(schemes));
    }

    @Override
    public void setOrganisations(List<ExternalItemDto> organisations) {
        this.organisations = organisations;
        ((ExternalSelectItem) editionForm.getItem(ConfigurationDS.ORGANISATION)).setItemsValueMap(ExternalItemUtils.getExternalItemsHashMap(organisations));
    }
}
