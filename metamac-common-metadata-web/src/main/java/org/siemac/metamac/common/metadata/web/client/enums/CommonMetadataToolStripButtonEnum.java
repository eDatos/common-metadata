package org.siemac.metamac.common.metadata.web.client.enums;

import com.smartgwt.client.types.ValueEnum;

public enum CommonMetadataToolStripButtonEnum implements ValueEnum {

    COMMON_METADATA("common_metadata_button"), APPS_CONFIGURATIONS("apps_configurations_button");

    private String value;

    CommonMetadataToolStripButtonEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
