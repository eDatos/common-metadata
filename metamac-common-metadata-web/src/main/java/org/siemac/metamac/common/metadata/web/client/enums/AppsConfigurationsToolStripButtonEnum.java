package org.siemac.metamac.common.metadata.web.client.enums;

import com.smartgwt.client.types.ValueEnum;

public enum AppsConfigurationsToolStripButtonEnum implements ValueEnum {

    SYSTEM_PROPERTIES("system_properties_button"), DEFAULT_VALUES("default_values_button");

    private String value;

    AppsConfigurationsToolStripButtonEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
