package org.siemac.metamac.common.metadata.core.criteria;

public enum DataConfigurationCriteriaPropertyEnum {

    CONF_KEY, CONF_VALUE;

    public String value() {
        return name();
    }
    public static DataConfigurationCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}
