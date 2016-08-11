package org.siemac.metamac.common.metadata.core.criteria;

public enum DataConfigurationCriteriaOrderEnum {

    CREATED_DATE;

    public String value() {
        return name();
    }

    public static DataConfigurationCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }
}
