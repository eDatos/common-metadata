package org.siemac.metamac.common.metadata.web.client.model;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.model.ds.AppConfigurationDS;
import org.siemac.metamac.web.common.client.widgets.NavigableListGridRecord;

public class AppConfigurationRecord extends NavigableListGridRecord {

    public AppConfigurationRecord() {

    }

    public void setKey(String value) {
        setAttribute(AppConfigurationDS.KEY, value);
    }

    public String getKey() {
        return getAttributeAsString(AppConfigurationDS.KEY);
    }

    public void setValue(String value) {
        setAttribute(AppConfigurationDS.VALUE, value);
    }

    public String getValue() {
        return getAttributeAsString(AppConfigurationDS.VALUE);
    }

    public void setDto(DataConfigurationDto value) {
        setAttribute(AppConfigurationDS.APP_CONFIGURATION_DTO, value);
    }

    public DataConfigurationDto getDto() {
        return (DataConfigurationDto) getAttributeAsObject(AppConfigurationDS.APP_CONFIGURATION_DTO);
    }

}
