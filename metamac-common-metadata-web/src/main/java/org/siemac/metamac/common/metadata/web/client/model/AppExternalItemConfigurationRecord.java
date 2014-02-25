package org.siemac.metamac.common.metadata.web.client.model;

import org.siemac.metamac.common.metadata.web.client.model.ds.AppConfigurationDS;
import org.siemac.metamac.core.common.dto.ExternalItemDto;

public class AppExternalItemConfigurationRecord extends AppConfigurationRecord {

    public AppExternalItemConfigurationRecord() {

    }

    public void setExternalItemDto(ExternalItemDto dto) {
        setAttribute(AppConfigurationDS.APP_EXTERNAL_ITEM_DTO, dto);
    }

    public ExternalItemDto getExternalItemDto() {
        return (ExternalItemDto) (getAttributeAsObject(AppConfigurationDS.APP_EXTERNAL_ITEM_DTO));
    }
}
