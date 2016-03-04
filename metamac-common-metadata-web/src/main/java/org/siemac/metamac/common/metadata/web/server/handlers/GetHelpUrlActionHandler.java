package org.siemac.metamac.common.metadata.web.server.handlers;

import org.siemac.metamac.common.metadata.core.conf.CommonMetadataConfigurationService;
import org.siemac.metamac.common.metadata.web.shared.GetHelpUrlAction;
import org.siemac.metamac.common.metadata.web.shared.GetHelpUrlResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetHelpUrlActionHandler extends SecurityActionHandler<GetHelpUrlAction, GetHelpUrlResult> {

    @Autowired
    private final CommonMetadataConfigurationService configurationService = null;

    public GetHelpUrlActionHandler() {
        super(GetHelpUrlAction.class);
    }

    @Override
    public GetHelpUrlResult executeSecurityAction(GetHelpUrlAction action) throws ActionException {
        try {
            String helpUrl = configurationService.retrieveHelpUrl();
            return new GetHelpUrlResult(helpUrl);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
