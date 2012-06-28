package org.siemac.metamac.common.metadata.web.client.model;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ConfigurationRecord extends ListGridRecord {

    public static final String CODE              = "code";
    public static final String CONFIGURATION_DTO = "conf-dto";

    public ConfigurationRecord() {
    }

    public ConfigurationRecord(String name, ConfigurationDto configurationDto) {
        setCode(name);
        setConfigurationDto(configurationDto);
    }

    public void setCode(String value) {
        setAttribute(CODE, value);
    }

    public String getCode() {
        return getAttributeAsString(CODE);
    }

    public void setConfigurationDto(ConfigurationDto value) {
        setAttribute(CONFIGURATION_DTO, value);
    }

    public ConfigurationDto getConfigurationDto() {
        return (ConfigurationDto) getAttributeAsObject(CONFIGURATION_DTO);
    }

}
