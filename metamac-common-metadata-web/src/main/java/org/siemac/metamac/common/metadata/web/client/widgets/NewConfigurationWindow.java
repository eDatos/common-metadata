package org.siemac.metamac.common.metadata.web.client.widgets;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.model.ds.ConfigurationDS;
import org.siemac.metamac.common.metadata.web.client.utils.CommonUtils;
import org.siemac.metamac.common.metadata.web.client.widgets.external.SearchAgencyItem;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.view.handlers.SrmExternalResourcesUiHandlers;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmItemItem;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class NewConfigurationWindow extends CustomWindow {

    private static final int    FORM_ITEM_CUSTOM_WIDTH = 300;
    private static final String FIELD_SAVE             = "save-sch";

    private CustomDynamicForm   form;

    public NewConfigurationWindow(String title, SrmExternalResourcesUiHandlers uiHandlers) {
        super(title);
        setAutoSize(true);

        RequiredTextItem codeItem = new RequiredTextItem(ConfigurationDS.CODE, CommonMetadataWeb.getConstants().confCode());
        codeItem.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        codeItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        RequiredSelectItem statusItem = new RequiredSelectItem(ConfigurationDS.STATUS, CommonMetadataWeb.getConstants().confStatus());
        statusItem.setValueMap(CommonUtils.getCommonMetadataStatusEnumHashMap());
        statusItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        SearchExternalItemLinkItem agencyItem = createAgencyItem(ConfigurationDS.CONTACT, getConstants().confOrganisation(), uiHandlers);
        agencyItem.setExternalItem(null);

        CustomTextItem licenseItem = new CustomTextItem(ConfigurationDS.LICENSE, getConstants().confLicense());
        licenseItem.setRequired(true);
        licenseItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        CustomButtonItem createItem = new CustomButtonItem(FIELD_SAVE, CommonMetadataWeb.getConstants().confCreate());

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(codeItem, statusItem, agencyItem, licenseItem, createItem);

        addItem(form);
        show();
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

    public boolean validateForm() {
        return form.validate(false);
    }

    public ConfigurationDto getNewConfigurationDto() {
        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setCode(form.getValueAsString(ConfigurationDS.CODE));
        configurationDto.setStatus(form.getValueAsString(ConfigurationDS.STATUS) != null ? CommonMetadataStatusEnum.valueOf(form.getValueAsString(ConfigurationDS.STATUS)) : null);
        configurationDto.setContact(((SearchSrmItemItem) form.getItem(ConfigurationDS.CONTACT)).getExternalItemDto());
        configurationDto.setLicense(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), form.getValueAsString(ConfigurationDS.LICENSE)));
        return configurationDto;
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES DATA SETTERS
    // ------------------------------------------------------------------------------------------------------------

    public void setItemSchemes(String formItemName, ExternalItemsResult result) {
        if (StringUtils.equals(ConfigurationDS.CONTACT, formItemName)) {
            ((SearchSrmItemItem) form.getItem(formItemName)).setItemSchemes(result);
        }
    }

    public void setItems(String formItemName, ExternalItemsResult result) {
        if (StringUtils.equals(ConfigurationDS.CONTACT, formItemName)) {
            ((SearchSrmItemItem) form.getItem(formItemName)).setItems(result);
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // EXTERNAL RESOURCES ITEMS
    // ------------------------------------------------------------------------------------------------------------

    private SearchSrmItemItem createAgencyItem(final String name, String title, SrmExternalResourcesUiHandlers uiHandlers) {
        final SearchSrmItemItem item = new SearchAgencyItem(name, title);
        item.setUiHandlers(uiHandlers);
        item.setRequired(true);
        com.smartgwt.client.widgets.form.fields.events.ClickHandler clickHandler = new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                ExternalItemDto agency = item.getSelectedItem();
                item.markSearchWindowForDestroy();
                ((SearchSrmItemItem) form.getItem(name)).setExternalItem(agency);
                form.validate(false);
            }
        };
        item.setSaveClickHandler(clickHandler);
        return item;
    }
}
