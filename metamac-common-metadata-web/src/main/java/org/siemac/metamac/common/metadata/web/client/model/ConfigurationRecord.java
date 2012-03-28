package org.siemac.metamac.common.metadata.web.client.model;

import org.siemac.metamac.common.metadata.dto.serviceapi.ConfigurationDto;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ConfigurationRecord extends ListGridRecord {

    public static final String NAME              = "name";
    public static final String CONFIGURATION_DTO = "conf-dto";

    public ConfigurationRecord() {
    }

    public ConfigurationRecord(String name, ConfigurationDto configurationDto) {
        setName(name);
        setConfigurationDto(configurationDto);
    }

    public void setName(String value) {
        setAttribute(NAME, value);
    }

    public String getName() {
        return getAttributeAsString(NAME);
    }

    public void setConfigurationDto(ConfigurationDto value) {
        setAttribute(CONFIGURATION_DTO, value);
    }

    public ConfigurationDto getConfigurationDto() {
        return (ConfigurationDto) getAttributeAsObject(CONFIGURATION_DTO);
    }

}
