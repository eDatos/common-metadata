package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.server.utils.AppConfigurationsProcessor;
import org.siemac.metamac.common.metadata.web.server.utils.MetamacWebCriteriaUtils;
import org.siemac.metamac.common.metadata.web.shared.GetAppsConfigurationsAction;
import org.siemac.metamac.common.metadata.web.shared.GetAppsConfigurationsResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetAppsConfigurationsActionHandler extends SecurityActionHandler<GetAppsConfigurationsAction, GetAppsConfigurationsResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    public GetAppsConfigurationsActionHandler() {
        super(GetAppsConfigurationsAction.class);
    }

    @Override
    public GetAppsConfigurationsResult executeSecurityAction(GetAppsConfigurationsAction action) throws ActionException {
        List<DataConfigurationDto> configurations = null;
        try {
            MetamacCriteria criteria = MetamacWebCriteriaUtils.build(action.getCriteria());            
            switch (action.getType()) {
                case SYSTEM:
                    configurations = commonMetadataServiceFacade.findDataConfigurationsOfSystemPropertiesByCondition(ServiceContextHolder.getCurrentServiceContext(), criteria);
                    break;
                case DEFAULT_VALUES:
                    configurations = commonMetadataServiceFacade.findDataConfigurationsOfDefaultValuesByCondition(ServiceContextHolder.getCurrentServiceContext(), criteria);
                    break;
                default:
                    throw new ActionException("Unsupported configuration type " + action.getType());
            }
            return new GetAppsConfigurationsResult(AppConfigurationsProcessor.processExternalItemsConfigurations(configurations));
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
