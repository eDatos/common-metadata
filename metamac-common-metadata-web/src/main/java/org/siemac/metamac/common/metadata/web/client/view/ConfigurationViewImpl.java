package org.siemac.metamac.common.metadata.web.client.view;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;
import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getCoreMessages;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.web.client.model.ds.ConfigurationDS;
import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationPresenter;
import org.siemac.metamac.common.metadata.web.client.utils.ClientSecurityUtils;
import org.siemac.metamac.common.metadata.web.client.utils.CommonUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationUiHandlers;
import org.siemac.metamac.common.metadata.web.client.widgets.ConfigurationMainFormLayout;
import org.siemac.metamac.common.metadata.web.client.widgets.external.SearchAgencyItem;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultilanguageRichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmItemItem;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class ConfigurationViewImpl extends ViewWithUiHandlers<ConfigurationUiHandlers> implements ConfigurationPresenter.ConfigurationView {

    private ConfigurationMainFormLayout mainFormLayout;

    private GroupDynamicForm            form;
    private GroupDynamicForm            editionForm;

    private ConfigurationDto            configurationDto;

    @Inject
    public ConfigurationViewImpl() {
        super();

        mainFormLayout = new ConfigurationMainFormLayout(ClientSecurityUtils.canUpdateConfiguration());

        bindMainFormLayoutEvents();

        createViewForm();
        createEditionForm();
    }

    private void bindMainFormLayoutEvents() {
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

        mainFormLayout.getDeleteConfirmationWindow().getYesButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().deleteConfiguration(configurationDto.getId());
            }
        });

        mainFormLayout.getPublishExternally().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().publishExternally(configurationDto.getId());
            }
        });
    }

    @Override
    public Widget asWidget() {
        return mainFormLayout;
    }

    @Override
    public void setUiHandlers(ConfigurationUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);

        // Set uiHandlers in formItems

        ((SearchSrmItemItem) editionForm.getItem(ConfigurationDS.CONTACT)).setUiHandlers(uiHandlers);
    }

    private void setTranslationsShowed(boolean translationsShowed) {
        form.setTranslationsShowed(translationsShowed);
        editionForm.setTranslationsShowed(translationsShowed);
    }

    public boolean validateForm() {
        return editionForm.validate(false);
    }

    private void createViewForm() {

        form = new GroupDynamicForm(getConstants().configuration());

        ViewTextItem staticCode = new ViewTextItem(ConfigurationDS.CODE, getConstants().confCode());
        ExternalItemLinkItem staticOrganisation = new ExternalItemLinkItem(ConfigurationDS.CONTACT, getConstants().confOrganisation());
        ViewTextItem status = new ViewTextItem(ConfigurationDS.STATUS, getConstants().confStatus());
        ViewTextItem externallyPublished = new ViewTextItem(ConfigurationDS.EXTERNALLY_PUBLISHED, getConstants().configurationExternallyPublished());
        ViewMultiLanguageTextItem license = new ViewMultiLanguageTextItem(ConfigurationDS.LICENSE, getConstants().confLicense());
        ViewMultiLanguageTextItem staticLegalActs = new ViewMultiLanguageTextItem(ConfigurationDS.LEGAL_ACTS, getConstants().confLegalActs());
        ViewMultiLanguageTextItem staticDataSharing = new ViewMultiLanguageTextItem(ConfigurationDS.DATA_SHARING, getConstants().confDataSharing());
        ViewMultiLanguageTextItem staticConfPolicy = new ViewMultiLanguageTextItem(ConfigurationDS.CONF_POLYCY, getConstants().confPolicy());
        ViewMultiLanguageTextItem staticConfDataTreatment = new ViewMultiLanguageTextItem(ConfigurationDS.CONF_DATA_TREATMENT, getConstants().confDataTreatment());

        form.setFields(staticCode, staticOrganisation, status, externallyPublished, license, staticLegalActs, staticDataSharing, staticConfPolicy, staticConfDataTreatment);

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

        editionForm = new GroupDynamicForm(getConstants().configuration());

        RequiredTextItem code = new RequiredTextItem(ConfigurationDS.CODE, getConstants().confCode());
        code.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        code.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                // Code cannot be edited
                return !configurationDto.isExternallyPublished();
            }
        });

        ViewTextItem staticCode = new ViewTextItem(ConfigurationDS.STATIC_CODE, getConstants().confCode());
        staticCode.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                // Code cannot be edited
                return configurationDto.isExternallyPublished();
            }
        });

        SearchExternalItemLinkItem agencyItem = createAgencyItem(ConfigurationDS.CONTACT, getConstants().confOrganisation());

        CustomSelectItem status = new CustomSelectItem(ConfigurationDS.STATUS, getConstants().confStatus());
        status.setRequired(true);
        status.setValueMap(CommonUtils.getCommonMetadataStatusEnumHashMap());

        ViewTextItem externallyPublished = new ViewTextItem(ConfigurationDS.EXTERNALLY_PUBLISHED, getConstants().configurationExternallyPublished());

        MultilanguageRichTextEditorItem license = new MultilanguageRichTextEditorItem(ConfigurationDS.LICENSE, getConstants().confLicense());
        license.setRequired(true);
        MultilanguageRichTextEditorItem legalActs = new MultilanguageRichTextEditorItem(ConfigurationDS.LEGAL_ACTS, getConstants().confLegalActs());
        MultilanguageRichTextEditorItem dataSharing = new MultilanguageRichTextEditorItem(ConfigurationDS.DATA_SHARING, getConstants().confDataSharing());
        MultilanguageRichTextEditorItem confPolicy = new MultilanguageRichTextEditorItem(ConfigurationDS.CONF_POLYCY, getConstants().confPolicy());
        MultilanguageRichTextEditorItem confDataTreatment = new MultilanguageRichTextEditorItem(ConfigurationDS.CONF_DATA_TREATMENT, getConstants().confDataTreatment());

        editionForm.setFields(staticCode, code, agencyItem, status, externallyPublished, license, legalActs, dataSharing, confPolicy, confDataTreatment);

        mainFormLayout.addEditionCanvas(editionForm);
    }

    private ConfigurationDto getConfiguration() {
        configurationDto.setCode(editionForm.getValueAsString(ConfigurationDS.CODE));
        configurationDto.setContact(((ExternalItemLinkItem) editionForm.getItem(ConfigurationDS.CONTACT)).getExternalItemDto());
        configurationDto.setStatus(editionForm.getValueAsString(ConfigurationDS.STATUS) != null ? CommonMetadataStatusEnum.valueOf(editionForm.getValueAsString(ConfigurationDS.STATUS)) : null);
        configurationDto.setLicense((InternationalStringDto) editionForm.getValue(ConfigurationDS.LICENSE));
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
        mainFormLayout.setConfiguration(configurationDto);

        setConfigurationViewMode(configurationDto);
        setConfigurationEditionMode(configurationDto);

        mainFormLayout.show();
    }

    private void setConfigurationViewMode(ConfigurationDto configurationDto) {
        this.configurationDto = configurationDto;
        form.setValue(ConfigurationDS.CODE, configurationDto.getCode());

        ((ExternalItemLinkItem) form.getItem(ConfigurationDS.CONTACT)).setExternalItem(configurationDto.getContact());

        form.setValue(ConfigurationDS.STATUS,
                configurationDto.getStatus() == null ? new String() : getCoreMessages().getString(getCoreMessages().commonMetadataStatusEnum() + configurationDto.getStatus().toString()));
        form.setValue(ConfigurationDS.EXTERNALLY_PUBLISHED, CommonWebUtils.getBooleanValueAsString(Boolean.valueOf(configurationDto.isExternallyPublished())));
        form.setValue(ConfigurationDS.LICENSE, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getLicense()));
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

        ((ExternalItemLinkItem) editionForm.getItem(ConfigurationDS.CONTACT)).setExternalItem(configurationDto.getContact());

        editionForm.setValue(ConfigurationDS.STATUS, configurationDto.getStatus() != null ? configurationDto.getStatus().toString() : new String());
        editionForm.setValue(ConfigurationDS.EXTERNALLY_PUBLISHED, CommonWebUtils.getBooleanValueAsString(Boolean.valueOf(configurationDto.isExternallyPublished())));
        editionForm.setValue(ConfigurationDS.LICENSE, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getLicense()));
        editionForm.setValue(ConfigurationDS.LEGAL_ACTS, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getLegalActs()));
        editionForm.setValue(ConfigurationDS.DATA_SHARING, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getDataSharing()));
        editionForm.setValue(ConfigurationDS.CONF_POLYCY, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getConfPolicy()));
        editionForm.setValue(ConfigurationDS.CONF_DATA_TREATMENT, org.siemac.metamac.web.common.client.utils.RecordUtils.getInternationalStringRecord(configurationDto.getConfDataTreatment()));
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES DATA SETTERS
    // ------------------------------------------------------------------------------------------------------------

    @Override
    public void setItemSchemes(String formItemName, ExternalItemsResult result) {
        if (StringUtils.equals(ConfigurationDS.CONTACT, formItemName)) {
            ((SearchSrmItemItem) editionForm.getItem(formItemName)).setItemSchemes(result);
        }
    }

    @Override
    public void setItems(String formItemName, ExternalItemsResult result) {
        if (StringUtils.equals(ConfigurationDS.CONTACT, formItemName)) {
            ((SearchSrmItemItem) editionForm.getItem(formItemName)).setItems(result);
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES ITEMS
    // ------------------------------------------------------------------------------------------------------------

    private SearchSrmItemItem createAgencyItem(final String name, String title) {
        final SearchSrmItemItem item = new SearchAgencyItem(name, title);
        item.setIsLastVersionItemVisible(false);
        item.setRequired(true);
        com.smartgwt.client.widgets.form.fields.events.ClickHandler clickHandler = new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                ExternalItemDto agency = item.getSelectedItem();
                item.markSearchWindowForDestroy();
                ((SearchSrmItemItem) editionForm.getItem(name)).setExternalItem(agency);
                editionForm.validate(false);
            }
        };
        item.setSaveClickHandler(clickHandler);
        return item;
    }
}
