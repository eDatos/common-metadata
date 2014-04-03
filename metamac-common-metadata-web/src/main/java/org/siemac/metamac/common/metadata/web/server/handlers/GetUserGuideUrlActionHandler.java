package org.siemac.metamac.common.metadata.web.server.handlers;

import org.siemac.metamac.common.metadata.core.conf.CommonMetadataConfigurationService;
import org.siemac.metamac.common.metadata.web.shared.GetUserGuideUrlAction;
import org.siemac.metamac.common.metadata.web.shared.GetUserGuideUrlResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
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
        try {
            String userGuideFileName = configurationService.retrieveUserGuideFileName();
            return new GetUserGuideUrlResult(userGuideFileName);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
