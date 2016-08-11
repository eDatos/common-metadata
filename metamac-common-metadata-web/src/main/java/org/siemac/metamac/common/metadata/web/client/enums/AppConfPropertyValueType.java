package org.siemac.metamac.common.metadata.web.client.enums;

public enum AppConfPropertyValueType {
    STRING, LINK, EXTERNAL_ITEM_CONCEPT, EXTERNAL_ITEM_CODE, EXTERNAL_ITEM_CODELIST;

    public boolean isExternalItem() {
        return EXTERNAL_ITEM_CODE.equals(this) || EXTERNAL_ITEM_CODELIST.equals(this) || EXTERNAL_ITEM_CONCEPT.equals(this);
    }
}