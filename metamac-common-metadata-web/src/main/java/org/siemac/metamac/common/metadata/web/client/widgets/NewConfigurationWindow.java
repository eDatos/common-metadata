package org.siemac.metamac.common.metadata.web.client.widgets;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.model.ds.ConfigurationDS;
import org.siemac.metamac.common.metadata.web.client.widgets.external.SearchAgencyLinkItem;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public abstract class NewConfigurationWindow extends CustomWindow {

    private static final int     FORM_ITEM_CUSTOM_WIDTH = 300;
    private static final String  FIELD_SAVE             = "save-sch";

    private CustomDynamicForm    form;
    private SearchAgencyLinkItem searchContactItem;

    public NewConfigurationWindow(String title) {
        super(title);
        setAutoSize(true);

        RequiredTextItem codeItem = new RequiredTextItem(ConfigurationDS.CODE, CommonMetadataWeb.getConstants().confCode());
        codeItem.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        codeItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        searchContactItem = createAgencyItem(ConfigurationDS.CONTACT, getConstants().confOrganisation());

        CustomTextItem licenseItem = new CustomTextItem(ConfigurationDS.LICENSE, getConstants().confLicense());
        licenseItem.setRequired(true);
        licenseItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        CustomButtonItem createItem = new CustomButtonItem(FIELD_SAVE, CommonMetadataWeb.getConstants().confCreate());
        createItem.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                onSave();
            }
        });

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(codeItem, searchContactItem, licenseItem, createItem);

        addItem(form);
        show();
    }

    public boolean validateForm() {
        return form.validate(false);
    }

    public ConfigurationDto getNewConfigurationDto() {
        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setCode(form.getValueAsString(ConfigurationDS.CODE));
        configurationDto.setContact(form.getValueAsExternalItemDto(ConfigurationDS.CONTACT));
        configurationDto.setLicense(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), form.getValueAsString(ConfigurationDS.LICENSE)));
        return configurationDto;
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES DATA SETTERS
    // ------------------------------------------------------------------------------------------------------------

    public void setAgencySchemes(ExternalItemsResult result) {
        searchContactItem.setAgencySchemesFilter(result);
    }

    public void setAgencies(ExternalItemsResult result) {
        searchContactItem.setAgenciesFilter(result);
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES ITEMS
    // ------------------------------------------------------------------------------------------------------------

    private SearchAgencyLinkItem createAgencyItem(final String name, String title) {
        final SearchAgencyLinkItem item = new SearchAgencyLinkItem(name, title) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria itemSchemeRestCriteria) {
                retrieveAgencySchemes(itemSchemeRestCriteria, firstResult, maxResults);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria itemRestCriteria) {
                retrieveAgencies(itemRestCriteria, firstResult, maxResults);
            }
        };
        item.setRequired(true);
        return item;
    }

    protected abstract void onSave();

    protected abstract void retrieveAgencies(SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults);

    protected abstract void retrieveAgencySchemes(SrmExternalResourceRestCriteria itemSchemeRestCriteria, int firstResult, int maxResults);
}
