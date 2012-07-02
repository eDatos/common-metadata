package org.siemac.metamac.common.metadata.web.client.utils;

import java.util.LinkedHashMap;

import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;

public class CommonUtils {

    public static LinkedHashMap<String, String> getCommonMetadataStatusEnumHashMap() {
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
        for (CommonMetadataStatusEnum type : CommonMetadataStatusEnum.values()) {
            valueMap.put(type.toString(), CommonMetadataWeb.getCoreMessages().getString(CommonMetadataWeb.getCoreMessages().commonMetadataStatusEnum() + type.getName()));
        }
        return valueMap;
    }

}
