package org.siemac.metamac.common_metadata.rest.external.v1_0.service.utils;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.navigation.shared.PlaceRequestParams;
import org.springframework.web.util.UriTemplate;

public class WebApplicationNavigation {

    private final String      ANCHOR                = "#";
    private final String      SEPARATOR             = "/";
    private final String      RESOURCE_ID_PARAMETER = "resourceIdParam";

    private final UriTemplate configurationTemplate;

    public WebApplicationNavigation(String webApplicationPath) {
        configurationTemplate = new UriTemplate(webApplicationPath + SEPARATOR + ANCHOR + NameTokens.configurationListPage + SEPARATOR + NameTokens.configurationPage + ";"
                + PlaceRequestParams.configurationParamId + "=" + "{" + RESOURCE_ID_PARAMETER + "}");
    }

    public String buildConfigurationUrl(Configuration configuration) {
        Map<String, String> parameters = new HashMap<String, String>(1);
        parameters.put(RESOURCE_ID_PARAMETER, configuration.getCode());
        return configurationTemplate.expand(parameters).toString();
    }
}