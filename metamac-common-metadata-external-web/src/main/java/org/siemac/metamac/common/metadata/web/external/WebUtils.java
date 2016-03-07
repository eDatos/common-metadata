package org.siemac.metamac.common.metadata.web.external;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.constants.shared.WebFaviconConstants;
import org.siemac.metamac.core.common.enume.shared.ApplicationOrganisationEnum;

public class WebUtils {

    protected static String organisation = null;

    public static String getBaseURL(HttpServletRequest request) throws MalformedURLException {
        URL url = new URL(request.getRequestURL().toString());
        return url.getAuthority() + url.getPath().replace("/docs/api/swagger.jsp", StringUtils.EMPTY);
    }

    public static void setOrganisation(String organisation) {
        WebUtils.organisation = organisation;
    }

    public static String getFavicon() {
        if (ApplicationOrganisationEnum.ISTAC.getName().equals(organisation)) {
            return WebFaviconConstants.ISTAC;
        } else if (ApplicationOrganisationEnum.DREM.getName().equals(organisation)) {
            return WebFaviconConstants.DREM;
        }
        return StringUtils.EMPTY;
    }
}
