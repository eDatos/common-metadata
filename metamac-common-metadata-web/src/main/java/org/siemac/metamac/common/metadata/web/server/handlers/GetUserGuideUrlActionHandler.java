package org.siemac.metamac.common.metadata.web.server.handlers;

import org.siemac.metamac.common.metadata.core.conf.CommonMetadataConfigurationService;
import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConfigurationConstants;
import org.siemac.metamac.common.metadata.web.client.constants.CommonMetadataWebConstants;
import org.siemac.metamac.common.metadata.web.shared.GetUserGuideUrlAction;
import org.siemac.metamac.common.metadata.web.shared.GetUserGuideUrlResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetUserGuideUrlActionHandler extends SecurityActionHandler<GetUserGuideUrlAction, GetUserGuideUrlResult> {

    @Autowired
    private final CommonMetadataConfigurationService configurationService = null;

    public GetUserGuideUrlActionHandler() {
        super(GetUserGuideUrlAction.class);
    }

    @Override
    public GetUserGuideUrlResult executeSecurityAction(GetUserGuideUrlAction action) throws ActionException {
        String dataUrl = configurationService.getConfig().getString(CommonMetadataWebConstants.ENVIRONMENT_DATA_URL);
        String userGuideFileName = configurationService.getConfig().getString(CommonMetadataConfigurationConstants.USER_GUIDE_FILE_NAME);
        return new GetUserGuideUrlResult(dataUrl + "/common-metadata/common-metadata-web/docs/" + userGuideFileName);
    }
}
