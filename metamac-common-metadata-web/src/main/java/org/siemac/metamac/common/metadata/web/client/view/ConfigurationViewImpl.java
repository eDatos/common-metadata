package org.siemac.metamac.common.metadata.web.client.view;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;
import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getCoreMessages;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.model.ds.ConfigurationDS;
import org.siemac.metamac.common.metadata.web.client.presenter.ConfigurationPresenter;
import org.siemac.metamac.common.metadata.web.client.utils.ConfigurationClientSecurityUtils;
import org.siemac.metamac.common.metadata.web.client.view.handlers.ConfigurationUiHandlers;
import org.siemac.metamac.common.metadata.web.client.widgets.ConfigurationMainFormLayout;
import org.siemac.metamac.common.metadata.web.client.widgets.external.SearchAgencyLinkItem;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.web.common.client.utils.BooleanWebUtils;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageRichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
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

        mainFormLayout = new ConfigurationMainFormLayout(ConfigurationClientSecurityUtils.canUpdateConfiguration());

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

        // ??????????????????
        // ToolStrip
        // ??????????????????

        ToolStrip formToolStrip = new ToolStrip();
        formToolStrip.setWidth100();

        // ????????
        // Form
        // ????????

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

        SearchAgencyLinkItem agencyItem = createAgencyItem(ConfigurationDS.CONTACT, getConstants().confOrganisation());

        ViewTextItem status = new ViewTextItem(ConfigurationDS.STATUS, getConstants().confStatus());

        ViewTextItem externallyPublished = new ViewTextItem(ConfigurationDS.EXTERNALLY_PUBLISHED, getConstants().configurationExternallyPublished());

        MultiLanguageRichTextEditorItem license = new MultiLanguageRichTextEditorItem(ConfigurationDS.LICENSE, getConstants().confLicense());
        license.setRequired(true);
        MultiLanguageRichTextEditorItem legalActs = new MultiLanguageRichTextEditorItem(ConfigurationDS.LEGAL_ACTS, getConstants().confLegalActs());
        MultiLanguageRichTextEditorItem dataSharing = new MultiLanguageRichTextEditorItem(ConfigurationDS.DATA_SHARING, getConstants().confDataSharing());
        MultiLanguageRichTextEditorItem confPolicy = new MultiLanguageRichTextEditorItem(ConfigurationDS.CONF_POLYCY, getConstants().confPolicy());
        MultiLanguageRichTextEditorItem confDataTreatment = new MultiLanguageRichTextEditorItem(ConfigurationDS.CONF_DATA_TREATMENT, getConstants().confDataTreatment());

        editionForm.setFields(staticCode, code, agencyItem, status, externallyPublished, license, legalActs, dataSharing, confPolicy, confDataTreatment);

        mainFormLayout.addEditionCanvas(editionForm);
    }

    private ConfigurationDto getConfiguration() {
        configurationDto.setCode(editionForm.getValueAsString(ConfigurationDS.CODE));

        configurationDto.setContact(editionForm.getValueAsExternalItemDto(ConfigurationDS.CONTACT));
        configurationDto.setLicense(editionForm.getValueAsInternationalStringDto(ConfigurationDS.LICENSE));
        configurationDto.setLegalActs(editionForm.getValueAsInternationalStringDto(ConfigurationDS.LEGAL_ACTS));
        configurationDto.setDataSharing(editionForm.getValueAsInternationalStringDto(ConfigurationDS.DATA_SHARING));
        configurationDto.setConfPolicy(editionForm.getValueAsInternationalStringDto(ConfigurationDS.CONF_POLYCY));
        configurationDto.setConfDataTreatment(editionForm.getValueAsInternationalStringDto(ConfigurationDS.CONF_DATA_TREATMENT));
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
        form.setValue(ConfigurationDS.CONTACT, configurationDto.getContact());
        form.setValue(ConfigurationDS.STATUS,
                configurationDto.getStatus() == null ? new String() : getCoreMessages().getString(getCoreMessages().commonMetadataStatusEnum() + configurationDto.getStatus().toString()));
        form.setValue(ConfigurationDS.EXTERNALLY_PUBLISHED, BooleanWebUtils.getBooleanLabel(configurationDto.isExternallyPublished()));
        form.setValue(ConfigurationDS.LICENSE, configurationDto.getLicense());
        form.setValue(ConfigurationDS.LEGAL_ACTS, configurationDto.getLegalActs());
        form.setValue(ConfigurationDS.DATA_SHARING, configurationDto.getDataSharing());
        form.setValue(ConfigurationDS.CONF_POLYCY, configurationDto.getConfPolicy());
        form.setValue(ConfigurationDS.CONF_DATA_TREATMENT, configurationDto.getConfDataTreatment());
        form.redraw();
    }

    private void setConfigurationEditionMode(ConfigurationDto configurationDto) {
        this.configurationDto = configurationDto;
        editionForm.setValue(ConfigurationDS.STATIC_CODE, configurationDto.getCode());
        editionForm.setValue(ConfigurationDS.CODE, configurationDto.getCode());
        editionForm.setValue(ConfigurationDS.CONTACT, configurationDto.getContact());
        editionForm.setValue(ConfigurationDS.STATUS,
                configurationDto.getStatus() == null ? StringUtils.EMPTY : getCoreMessages().getString(getCoreMessages().commonMetadataStatusEnum() + configurationDto.getStatus().toString()));
        editionForm.setValue(ConfigurationDS.EXTERNALLY_PUBLISHED, BooleanWebUtils.getBooleanLabel(configurationDto.isExternallyPublished()));
        editionForm.setValue(ConfigurationDS.LICENSE, configurationDto.getLicense());
        editionForm.setValue(ConfigurationDS.LEGAL_ACTS, configurationDto.getLegalActs());
        editionForm.setValue(ConfigurationDS.DATA_SHARING, configurationDto.getDataSharing());
        editionForm.setValue(ConfigurationDS.CONF_POLYCY, configurationDto.getConfPolicy());
        editionForm.setValue(ConfigurationDS.CONF_DATA_TREATMENT, configurationDto.getConfDataTreatment());
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES DATA SETTERS
    // ------------------------------------------------------------------------------------------------------------

    @Override
    public void setAgencySchemes(ExternalItemsResult result) {
        ((SearchAgencyLinkItem) editionForm.getItem(ConfigurationDS.CONTACT)).setAgencySchemesFilter(result);
    }

    @Override
    public void setAgencies(ExternalItemsResult result) {
        ((SearchAgencyLinkItem) editionForm.getItem(ConfigurationDS.CONTACT)).setAgenciesFilter(result);
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES ITEMS
    // ------------------------------------------------------------------------------------------------------------

    private SearchAgencyLinkItem createAgencyItem(final String name, String title) {
        SearchAgencyLinkItem item = new SearchAgencyLinkItem(name, title) {

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria itemRestCriteria) {
                getUiHandlers().retrieveAgencies(itemRestCriteria, firstResult, maxResults);
            }

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria itemSchemeRestCriteria) {
                getUiHandlers().retrieveAgencySchemes(itemSchemeRestCriteria, firstResult, maxResults);
            }
        };

        item.setRequired(true);
        return item;
    }
}
