package org.siemac.metamac.common.metadata.web.server.handlers;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyAction;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class PublishConfigurationExternallyActionHandler extends SecurityActionHandler<PublishConfigurationExternallyAction, PublishConfigurationExternallyResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    @Autowired
    private SrmRestInternalFacade       srmRestInternalFacade;

    public PublishConfigurationExternallyActionHandler() {
        super(PublishConfigurationExternallyAction.class);
    }

    @Override
    public PublishConfigurationExternallyResult executeSecurityAction(PublishConfigurationExternallyAction action) throws ActionException {
        try {
            ConfigurationDto configurationToPublish = commonMetadataServiceFacade.findConfigurationById(ServiceContextHolder.getCurrentServiceContext(), action.getId());
            checkContactIsExternallyPublished(configurationToPublish);

            ConfigurationDto configurationPublished = commonMetadataServiceFacade.publishExternallyConfiguration(ServiceContextHolder.getCurrentServiceContext(), action.getId());
            return new PublishConfigurationExternallyResult(configurationPublished);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    private void checkContactIsExternallyPublished(ConfigurationDto configurationDto) throws ActionException {

        SrmItemRestCriteria criteria = new SrmItemRestCriteria();
        criteria.setUrn(configurationDto.getUrn());

        // FIXME check is externally published

        // TODO WebMessageExceptionsConstants
        // srmRestInternalFacade.findAgencies(itemWebCriteria, firstResult, maxResults)
    }
}
