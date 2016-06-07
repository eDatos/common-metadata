package org.siemac.metamac.common.metadata.web.external.urlrewrite;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.siemac.metamac.common_metadata.rest.external.RestExternalConstants.API_VERSION_1_0;
import static org.siemac.metamac.core.common.constants.CoreCommonConstants.API_LATEST;
import static org.siemac.metamac.core.common.constants.CoreCommonConstants.URL_SEPARATOR;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.tuckey.web.filters.urlrewrite.extend.RewriteMatch;

class CustomRewriteMatch extends RewriteMatch {

    private static final String  API_PREFIX           = "cmetadata";

    private static Pattern       urlPattern           = Pattern.compile(".*/apis/" + API_PREFIX + "/(v\\d+\\.\\d+|" + API_LATEST + ")(/(.*)?)?");

    private ConfigurationService configurationService = null;

    @Override
    public boolean execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        String queryString = ((HttpServletRequest) request).getQueryString();
        Matcher matcher = urlPattern.matcher(requestURI);
        if (matcher.matches() && matcher.groupCount() > 2) {
            if (API_LATEST.equals(matcher.group(1)) || (matcher.group(2) == null && requestURI.endsWith(matcher.group(1)) && isBlank(queryString))) {
                String location = getApiBaseUrl() + URL_SEPARATOR + API_VERSION_1_0 + (matcher.group(2) == null ? URL_SEPARATOR : matcher.group(2))
                        + (isBlank(queryString) ? EMPTY : "?" + queryString);
                response.sendRedirect(location);
            }
        }
        return false;
    }

    private String getApiBaseUrl() throws ServletException {
        try {
            return getConfigurationService().retrieveCommonMetadataExternalApiUrlBase();
        } catch (MetamacException e) {
            throw new ServletException("Error retrieving configuration property of the external API URL base", e);
        }
    }

    private ConfigurationService getConfigurationService() {
        if (configurationService == null) {
            configurationService = ApplicationContextProvider.getApplicationContext().getBean(ConfigurationService.class);
        }
        return configurationService;

    }
}
