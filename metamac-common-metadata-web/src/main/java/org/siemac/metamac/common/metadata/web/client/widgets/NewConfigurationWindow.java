package org.siemac.metamac.common.metadata.web.client.widgets;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.model.ds.ConfigurationDS;
import org.siemac.metamac.common.metadata.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;

import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class NewConfigurationWindow extends CustomWindow {

    private static final int    FORM_ITEM_CUSTOM_WIDTH = 300;
    private static final String FIELD_SAVE             = "save-sch";

    private CustomDynamicForm   form;

    public NewConfigurationWindow(String title) {
        super(title);
        setAutoSize(true);

        RequiredTextItem codeItem = new RequiredTextItem(ConfigurationDS.CODE, CommonMetadataWeb.getConstants().confCode());
        codeItem.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        codeItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        RequiredSelectItem statusItem = new RequiredSelectItem(ConfigurationDS.STATUS, CommonMetadataWeb.getConstants().confStatus());
        statusItem.setValueMap(CommonUtils.getCommonMetadataStatusEnumHashMap());
        statusItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        CustomButtonItem createItem = new CustomButtonItem(FIELD_SAVE, CommonMetadataWeb.getConstants().confCreate());

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(codeItem, statusItem, createItem);

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
        return configurationDto;
    }
}
