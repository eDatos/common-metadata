package org.siemac.metamac.common.metadata.web.client.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.enums.AppConfPropertyValueType;
import org.siemac.metamac.common.metadata.web.client.model.ds.AppConfigurationDS;
import org.siemac.metamac.common.metadata.web.client.presenter.AppsDefaultValuesPresenter.ViewActionHandlers;
import org.siemac.metamac.common.metadata.web.client.utils.AppDataConfigClientSecurityUtils;
import org.siemac.metamac.common.metadata.web.client.widgets.external.SearchCodeLinkItem;
import org.siemac.metamac.common.metadata.web.client.widgets.external.SearchCodelistLinkItem;
import org.siemac.metamac.common.metadata.web.client.widgets.external.SearchConceptLinkItem;
import org.siemac.metamac.common.metadata.web.shared.dto.ExternalItemDataConfigurationDto;
import org.siemac.metamac.common.metadata.web.shared.utils.AppConfigurationsUtils;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.LinkItem;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class AppPropertyPanel extends VLayout {

    private MainFormLayout         propertyFormLayout;
    private GroupDynamicForm       viewForm;
    private static final String    VALUE_LINK_ID                   = "value-link";
    private static final String    VALUE_EXTERNAL_ITEM_ID          = "value-ei";
    private static final String    VALUE_EXTERNAL_ITEM_CONCEPT_ID  = "value-ei-concept";
    private static final String    VALUE_EXTERNAL_ITEM_CODE_ID     = "value-ei-code";
    private static final String    VALUE_EXTERNAL_ITEM_CODELIST_ID = "value-ei-codelist";

    private SearchConceptLinkItem  searchConceptLinkItem;
    private SearchCodeLinkItem     searchCodeLinkItem;
    private SearchCodelistLinkItem searchCodelistLinkItem;

    private GroupDynamicForm       editForm;
    private DataConfigurationDto   dataConfigurationDto;

    // actions flag
    private boolean                canEdit                         = false;

    public AppPropertyPanel() {
        super();

        propertyFormLayout = createPropertyFormLayout();

        propertyFormLayout.getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (editForm.validate(false)) {
                    saveProperty(buildCurrentProperty());
                }

            }
        });

        propertyFormLayout.hide();

        addMember(propertyFormLayout);
    }
    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
        propertyFormLayout.setCanEdit(canEdit);
    }

    // CREATION

    private MainFormLayout createPropertyFormLayout() {
        MainFormLayout formLayout = new MainFormLayout(canEdit);

        appendPropertyFormViewCanvas(formLayout);
        appendPropertyFormEditionCanvas(formLayout);

        return formLayout;
    }

    public void hideFormLayout() {
        propertyFormLayout.hide();
    }

    private void appendPropertyFormViewCanvas(MainFormLayout formLayout) {
        viewForm = new GroupDynamicForm(CommonMetadataWeb.getConstants().appConfigurationProperty());

        // BASIC
        ViewTextItem key = new ViewTextItem(AppConfigurationDS.KEY, CommonMetadataWeb.getConstants().applicationConfigurationKey());
        ViewTextItem rawValue = new ViewTextItem(AppConfigurationDS.VALUE, CommonMetadataWeb.getConstants().applicationConfigurationValue());
        rawValue.setShowIfCondition(new ShowViewFormItemIfPropertyType(AppConfPropertyValueType.STRING));

        // ADVANCED
        LinkItem linkValue = new LinkItem(VALUE_LINK_ID);
        linkValue.setWidth("*");
        linkValue.setTitle(CommonMetadataWeb.getConstants().applicationConfigurationValue());
        linkValue.setShowIfCondition(new ShowViewFormItemIfPropertyType(AppConfPropertyValueType.LINK));

        ExternalItemLinkItem externalItemValue = new ExternalItemLinkItem(VALUE_EXTERNAL_ITEM_ID, CommonMetadataWeb.getConstants().applicationConfigurationValue());
        externalItemValue.setShowIfCondition(new ShowViewFormItemIfPropertyType(AppConfPropertyValueType.EXTERNAL_ITEM_CODE, AppConfPropertyValueType.EXTERNAL_ITEM_CODELIST,
                AppConfPropertyValueType.EXTERNAL_ITEM_CONCEPT));

        viewForm.setFields(key, rawValue, linkValue, externalItemValue);

        formLayout.addViewCanvas(viewForm);
    }

    private void appendPropertyFormEditionCanvas(MainFormLayout formLayout) {
        editForm = new GroupDynamicForm(CommonMetadataWeb.getConstants().appConfigurationProperty());

        ViewTextItem key = new ViewTextItem(AppConfigurationDS.KEY, CommonMetadataWeb.getConstants().applicationConfigurationKey());

        RequiredTextItem rawValue = new RequiredTextItem(AppConfigurationDS.VALUE, CommonMetadataWeb.getConstants().applicationConfigurationValue());
        rawValue.setShowIfCondition(new ShowEditionFormItemIfPropertyType(AppConfPropertyValueType.STRING));

        searchConceptLinkItem = createSearchConceptExternalItem(VALUE_EXTERNAL_ITEM_CONCEPT_ID, CommonMetadataWeb.getConstants().applicationConfigurationValue());
        searchConceptLinkItem.setRequired(true);
        searchConceptLinkItem.setShowIfCondition(new ShowEditionFormItemIfPropertyType(AppConfPropertyValueType.EXTERNAL_ITEM_CONCEPT));

        searchCodeLinkItem = createSearchCodeExternalItem(VALUE_EXTERNAL_ITEM_CODE_ID, CommonMetadataWeb.getConstants().applicationConfigurationValue());
        searchCodeLinkItem.setRequired(true);
        searchCodeLinkItem.setShowIfCondition(new ShowEditionFormItemIfPropertyType(AppConfPropertyValueType.EXTERNAL_ITEM_CODE));

        searchCodelistLinkItem = createSearchCodelistExternalItem(VALUE_EXTERNAL_ITEM_CODELIST_ID, CommonMetadataWeb.getConstants().applicationConfigurationValue());
        searchCodelistLinkItem.setRequired(true);
        searchCodelistLinkItem.setShowIfCondition(new ShowEditionFormItemIfPropertyType(AppConfPropertyValueType.EXTERNAL_ITEM_CODELIST));

        editForm.setFields(key, rawValue, searchConceptLinkItem, searchCodeLinkItem, searchCodelistLinkItem);

        formLayout.addEditionCanvas(editForm);
    }

    private DataConfigurationDto buildCurrentProperty() {
        dataConfigurationDto.setConfigurationKey(editForm.getValueAsString(AppConfigurationDS.KEY));

        AppConfPropertyValueType type = AppConfigurationsUtils.guessPropertyValueTypeByKey(dataConfigurationDto.getConfigurationKey());
        if (type != null) {
            ExternalItemDto externalItemDto = null;
            switch (type) {
                case EXTERNAL_ITEM_CONCEPT:
                    externalItemDto = editForm.getValueAsExternalItemDto(VALUE_EXTERNAL_ITEM_CONCEPT_ID);
                    break;
                case EXTERNAL_ITEM_CODE:
                    externalItemDto = editForm.getValueAsExternalItemDto(VALUE_EXTERNAL_ITEM_CODE_ID);
                    break;
                case EXTERNAL_ITEM_CODELIST:
                    externalItemDto = editForm.getValueAsExternalItemDto(VALUE_EXTERNAL_ITEM_CODELIST_ID);
                    break;
            }
            dataConfigurationDto.setConfigurationValue(externalItemDto != null ? externalItemDto.getUrn() : null);
        } else {
            dataConfigurationDto.setConfigurationValue(editForm.getValueAsString(AppConfigurationDS.VALUE));
        }
        return dataConfigurationDto;
    }

    // POPULATE

    public void selectDtoForView(DataConfigurationDto dto) {
        this.dataConfigurationDto = dto;
        populateFormLayout(dto);
        propertyFormLayout.setViewMode();
        propertyFormLayout.show();
        if (canEdit) {
            propertyFormLayout.setCanEdit(AppDataConfigClientSecurityUtils.canEditProperty(dto.getConfigurationKey()));
        }
    }

    private void populateFormLayout(DataConfigurationDto dto) {
        AppConfPropertyValueType type = AppConfigurationsUtils.guessPropertyViewValueType(dto.getConfigurationKey(), dto.getConfigurationValue());
        populateViewFormLayout(dto, type);
        populateEditionFormLayout(dto, type);
    }

    private void populateViewFormLayout(DataConfigurationDto dto, AppConfPropertyValueType type) {

        viewForm.setValue(AppConfigurationDS.KEY, dto.getConfigurationKey());
        viewForm.setValue(AppConfigurationDS.VALUE, dto.getConfigurationValue());

        if (AppConfPropertyValueType.LINK.equals(type)) {
            viewForm.setValue(VALUE_LINK_ID, dto.getConfigurationValue());
        } else if (type.isExternalItem()) {
            ExternalItemDto externalItemDto = ((ExternalItemDataConfigurationDto) dto).getExternalItemDto();
            viewForm.setValue(VALUE_EXTERNAL_ITEM_ID, externalItemDto);
        }

        viewForm.markForRedraw();
    }
    private void populateEditionFormLayout(DataConfigurationDto dto, AppConfPropertyValueType type) {
        editForm.clearErrors(true);
        editForm.setValue(AppConfigurationDS.KEY, dto.getConfigurationKey());
        editForm.setValue(AppConfigurationDS.VALUE, dto.getConfigurationValue());

        if (type.isExternalItem()) {
            ExternalItemDto externalItemDto = ((ExternalItemDataConfigurationDto) dto).getExternalItemDto();
            switch (type) {
                case EXTERNAL_ITEM_CONCEPT:
                    editForm.setValue(VALUE_EXTERNAL_ITEM_CONCEPT_ID, externalItemDto);
                    break;
                case EXTERNAL_ITEM_CODE:
                    editForm.setValue(VALUE_EXTERNAL_ITEM_CODE_ID, externalItemDto);
                    break;
                case EXTERNAL_ITEM_CODELIST:
                    editForm.setValue(VALUE_EXTERNAL_ITEM_CODELIST_ID, externalItemDto);
                    break;
            }
        }
        editForm.markForRedraw();
    }

    // Different Search widgets for externalItem

    public void setConcepts(ExternalItemsResult result) {
        searchConceptLinkItem.setConceptsFilter(result);
    }

    public void setConceptSchemes(ExternalItemsResult result) {
        searchConceptLinkItem.setConcepSchemesFilter(result);
    }

    private SearchConceptLinkItem createSearchConceptExternalItem(String name, String title) {
        return new SearchConceptLinkItem(name, title) {

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria itemRestCriteria) {
                retrieveConcepts(itemRestCriteria, firstResult, maxResults);
            }

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria itemSchemeRestCriteria) {
                retrieveConceptSchemes(itemSchemeRestCriteria, firstResult, maxResults);
            }
        };
    }

    // Different Search widgets for externalItem

    public void setCodes(ExternalItemsResult result) {
        searchCodeLinkItem.setCodesFilter(result);
    }

    public void setCodelists(ExternalItemsResult result, ViewActionHandlers viewHandler) {
        switch (viewHandler) {
            case CODELISTS_FILTER:
                searchCodelistLinkItem.setCodelists(result);
                break;
            case CODELISTS_FILTER_FOR_CODES:
                searchCodeLinkItem.setCodelistsFilter(result);
                break;
        }
    }

    private SearchCodeLinkItem createSearchCodeExternalItem(String name, String title) {
        return new SearchCodeLinkItem(name, title) {

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria itemRestCriteria) {
                retrieveCodes(itemRestCriteria, firstResult, maxResults);
            }

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria itemSchemeRestCriteria) {
                retrieveCodelists(itemSchemeRestCriteria, firstResult, maxResults, ViewActionHandlers.CODELISTS_FILTER_FOR_CODES);
            }
        };
    }

    private SearchCodelistLinkItem createSearchCodelistExternalItem(String name, String title) {
        return new SearchCodelistLinkItem(name, title) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria criteria) {
                retrieveCodelists(criteria, firstResult, maxResults, ViewActionHandlers.CODELISTS_FILTER);
            }
        };
    }

    // SHOW IF functions

    private abstract class ShowFormItemIfPropertyType implements FormItemIfFunction {

        protected final List<AppConfPropertyValueType> propertyTypes;

        protected ShowFormItemIfPropertyType(AppConfPropertyValueType... propertyTypes) {
            this.propertyTypes = new ArrayList<AppConfPropertyValueType>(Arrays.asList(propertyTypes));
        }

    }

    private class ShowViewFormItemIfPropertyType extends ShowFormItemIfPropertyType implements FormItemIfFunction {

        public ShowViewFormItemIfPropertyType(AppConfPropertyValueType... propertyTypes) {
            super(propertyTypes);
        }

        @Override
        public boolean execute(FormItem item, Object value, DynamicForm form) {
            String rawValue = form.getValueAsString(AppConfigurationDS.VALUE);
            String key = form.getValueAsString(AppConfigurationDS.KEY);
            return propertyTypes.contains(AppConfigurationsUtils.guessPropertyViewValueType(key, rawValue));
        }
    }

    private class ShowEditionFormItemIfPropertyType extends ShowFormItemIfPropertyType implements FormItemIfFunction {

        public ShowEditionFormItemIfPropertyType(AppConfPropertyValueType... propertyTypes) {
            super(propertyTypes);
        }

        @Override
        public boolean execute(FormItem item, Object value, DynamicForm form) {
            String rawValue = form.getValueAsString(AppConfigurationDS.VALUE);
            String key = form.getValueAsString(AppConfigurationDS.KEY);
            return propertyTypes.contains(AppConfigurationsUtils.guessPropertyEditionValueType(key, rawValue));
        }
    }

    protected abstract void retrieveConceptSchemes(SrmExternalResourceRestCriteria itemSchemeRestCriteria, int firstResult, int maxResults);
    protected abstract void retrieveCodelists(SrmExternalResourceRestCriteria itemSchemeRestCriteria, int firstResult, int maxResults, ViewActionHandlers viewHandler);
    protected abstract void retrieveConcepts(SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults);
    protected abstract void retrieveCodes(SrmItemRestCriteria itemRestCriteria, int firstResult, int maxResults);
    protected abstract void saveProperty(DataConfigurationDto configurationProperty);

}
