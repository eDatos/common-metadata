package org.siemac.metamac.common.metadata.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.navigation.shared.PlaceRequestParams;
import org.siemac.metamac.core.common.util.shared.UrnUtils;

import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class PlaceRequestUtils {

    // ---------------------------------------------------------------------------
    // DATA STRUCTURE DEFINITION
    // ---------------------------------------------------------------------------

    public static String getConfigurationParamFromUrl(PlaceManager placeManager) {
        return getParamFromUrl(placeManager, NameTokens.configurationPage, PlaceRequestParams.configurationParamId);
    }

    public static PlaceRequest buildRelativeConfigurationPlaceRequest(String configurationUrn) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.configurationPage).with(PlaceRequestParams.configurationParamId, UrnUtils.removePrefix(configurationUrn));
        return placeRequest;
    }

    public static List<PlaceRequest> buildAbsoluteConfigurationsPlaceRequest() {
        List<PlaceRequest> placeRequests = new ArrayList<PlaceRequest>();
        PlaceRequest configurationsPlace = new PlaceRequest(NameTokens.configurationListPage);
        placeRequests.add(configurationsPlace);
        return placeRequests;
    }

    public static List<PlaceRequest> buildAbsoluteConfigurationPlaceRequest(String configurationUrn) {
        List<PlaceRequest> placeRequests = buildAbsoluteConfigurationsPlaceRequest();
        PlaceRequest configurationPlace = buildRelativeConfigurationPlaceRequest(configurationUrn);
        placeRequests.add(configurationPlace);
        return placeRequests;
    }

    // ---------------------------------------------------------------------------
    // PRIVATE METHODS
    // ---------------------------------------------------------------------------

    private static String getParamFromUrl(PlaceManager placeManager, String nameToken, String paramName) {
        for (PlaceRequest request : placeManager.getCurrentPlaceHierarchy()) {
            if (nameToken.equals(request.getNameToken())) {
                return request.getParameter(paramName, null);
            }
        }
        return null;
    }
}
