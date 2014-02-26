package org.siemac.metamac.common.metadata.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.common.metadata.navigation.shared.NameTokens;
import org.siemac.metamac.common.metadata.navigation.shared.PlaceRequestParams;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.web.common.client.utils.CommonPlaceRequestUtils;

import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class PlaceRequestUtils extends CommonPlaceRequestUtils {

    // ---------------------------------------------------------------------------
    // DATA STRUCTURE DEFINITION
    // ---------------------------------------------------------------------------

    public static String getConfigurationParamFromUrl(PlaceManager placeManager) {
        return getParamFromUrl(placeManager, NameTokens.commonMetadataPage, PlaceRequestParams.commonMetadataParamId);
    }

    public static PlaceRequest buildRelativeConfigurationPlaceRequest(String configurationUrn) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.commonMetadataPage).with(PlaceRequestParams.commonMetadataParamId, UrnUtils.removePrefix(configurationUrn));
        return placeRequest;
    }

    public static List<PlaceRequest> buildAbsoluteConfigurationsPlaceRequest() {
        List<PlaceRequest> placeRequests = new ArrayList<PlaceRequest>();
        PlaceRequest configurationsPlace = new PlaceRequest(NameTokens.commonMetadataListPage);
        placeRequests.add(configurationsPlace);
        return placeRequests;
    }

    public static List<PlaceRequest> buildAbsoluteConfigurationPlaceRequest(String configurationUrn) {
        List<PlaceRequest> placeRequests = buildAbsoluteConfigurationsPlaceRequest();
        PlaceRequest configurationPlace = buildRelativeConfigurationPlaceRequest(configurationUrn);
        placeRequests.add(configurationPlace);
        return placeRequests;
    }

    public static List<PlaceRequest> buildAbsoluteAppsConfigurationsRootPlaceRequest() {
        if (AppDataConfigClientSecurityUtils.canListSystemProperties()) {
            return buildAbsoluteAppsConfSystemPropertiesPlaceRequest();
        } else if (AppDataConfigClientSecurityUtils.canListDefaultValues()) {
            return buildAbsoluteAppsConfDefaultValuesPlaceRequest();
        }
        return null;
    }

    public static List<PlaceRequest> buildAbsoluteAppsConfSystemPropertiesPlaceRequest() {
        List<PlaceRequest> placeRequests = new ArrayList<PlaceRequest>();
        PlaceRequest configurationsPlace = new PlaceRequest(NameTokens.systemPropertiesListPage);;
        placeRequests.add(configurationsPlace);
        return placeRequests;
    }

    public static List<PlaceRequest> buildAbsoluteAppsConfDefaultValuesPlaceRequest() {
        List<PlaceRequest> placeRequests = new ArrayList<PlaceRequest>();
        PlaceRequest configurationsPlace = new PlaceRequest(NameTokens.defaultValuesListPage);;
        placeRequests.add(configurationsPlace);
        return placeRequests;
    }

}
