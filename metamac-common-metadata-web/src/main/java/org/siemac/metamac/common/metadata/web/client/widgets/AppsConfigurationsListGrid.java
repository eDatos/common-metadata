package org.siemac.metamac.common.metadata.web.client.widgets;

import java.util.List;

import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.common.metadata.web.client.enums.AppConfPropertyValueType;
import org.siemac.metamac.common.metadata.web.client.model.AppConfigurationRecord;
import org.siemac.metamac.common.metadata.web.client.model.AppExternalItemConfigurationRecord;
import org.siemac.metamac.common.metadata.web.client.model.ds.AppConfigurationDS;
import org.siemac.metamac.common.metadata.web.shared.utils.AppConfigurationsUtils;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.BaseNavigableListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AppsConfigurationsListGrid extends BaseNavigableListGrid<ExternalItemDto> {

    public AppsConfigurationsListGrid() {
        setSelectionType(SelectionStyle.NONE);

        ListGridField code = new ListGridField(AppConfigurationDS.KEY, CommonMetadataWeb.getConstants().applicationConfigurationKey());
        CustomListGridField link = new CustomListGridField(AppConfigurationDS.VALUE, CommonMetadataWeb.getConstants().applicationConfigurationValue());
        link.setCellFormatter(new CellFormatter() {

            @Override
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                AppConfigurationRecord confRecord = (AppConfigurationRecord) record;
                AppConfPropertyValueType type = AppConfigurationsUtils.guessPropertyViewValueType(confRecord.getKey(), confRecord.getValue());
                switch (type) {
                    case LINK:
                        return "<a href=\"" + value + "\">" + value + "</a>";
                    case EXTERNAL_ITEM_CODE:
                    case EXTERNAL_ITEM_CONCEPT:
                    case EXTERNAL_ITEM_CODELIST:
                        AppExternalItemConfigurationRecord externalItemConfRecord = (AppExternalItemConfigurationRecord) confRecord;
                        ExternalItemDto externalItemDto = externalItemConfRecord.getExternalItemDto();
                        if (externalItemDto != null) {
                            return "<a href=\"" + externalItemDto.getManagementAppUrl() + "\">" + InternationalStringUtils.getLocalisedString(externalItemDto.getTitle()) + "</a>";
                        }
                        return null;
                }
                return value != null ? value.toString() : null;
            }
        });
        setFields(code, link);
    }
    @Override
    protected List<PlaceRequest> buildLocation(ExternalItemDto relatedResourceDto) {
        throw new UnsupportedOperationException();
    }
}
