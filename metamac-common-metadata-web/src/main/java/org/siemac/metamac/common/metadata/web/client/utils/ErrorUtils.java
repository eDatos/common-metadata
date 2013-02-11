package org.siemac.metamac.common.metadata.web.client.utils;

import java.util.List;

import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;
import org.siemac.metamac.web.common.client.utils.CommonErrorUtils;

public class ErrorUtils extends CommonErrorUtils {

    public static List<String> getErrorMessages(Throwable caught, String alternativeMessage) {
        return getErrorMessages(CommonMetadataWeb.getCoreMessages(), caught, alternativeMessage);
    }
}
