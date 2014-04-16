package org.siemac.metamac.common.metadata.web.server.handlers;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.server.rest.NoticesRestInternalService;
import org.siemac.metamac.common.metadata.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyAction;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyResult;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class PublishConfigurationExternallyActionHandler extends SecurityActionHandler<PublishConfigurationExternallyAction, PublishConfigurationExternallyResult> {

    @Autowired
    private CommonMetadataServiceFacade commonMetadataServiceFacade;

    @Autowired
    private NoticesRestInternalService  noticesRestInternalService;

    @Autowired
    private SrmRestInternalFacade       srmRestInternalFacade;

    public PublishConfigurationExternallyActionHandler() {
        super(PublishConfigurationExternallyAction.class);
    }

    @Override
    public PublishConfigurationExternallyResult executeSecurityAction(PublishConfigurationExternallyAction action) throws ActionException {
        ConfigurationDto configurationPublished = null;
        ServiceContext serviceContext = ServiceContextHolder.getCurrentServiceContext();

        try {

            ConfigurationDto configurationToPublish = commonMetadataServiceFacade.findConfigurationById(serviceContext, action.getId());
            checkContactIsExternallyPublished(serviceContext, configurationToPublish);

            configurationPublished = commonMetadataServiceFacade.publishExternallyConfiguration(serviceContext, action.getId());

        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }

        try {
            noticesRestInternalService.createNotificationForPublishExternallyConfiguration(serviceContext, configurationPublished);
        } catch (MetamacWebException e) {
            return new PublishConfigurationExternallyResult(configurationPublished, e);
        }

        return new PublishConfigurationExternallyResult(configurationPublished, null);
    }

    private void checkContactIsExternallyPublished(ServiceContext serviceContext, ConfigurationDto configurationDto) throws ActionException {
        ExternalItemDto contact = configurationDto.getContact();
        if (contact != null) {

            String contactUrn = getContactUrn(serviceContext, contact);

            SrmItemRestCriteria criteria = new SrmItemRestCriteria();
            criteria.setUrn(contactUrn);
            criteria.setItemSchemeExternallyPublished(Boolean.TRUE);

            ExternalItemsResult result = srmRestInternalFacade.findAgencies(serviceContext, criteria, 0, 1);
            if (result.getExternalItemDtos().isEmpty()) {

                WebExceptionUtils.throwMetamacWebException(serviceContext, ServiceExceptionType.CONFIGURATION_ERROR_CONTACT_NOT_EXTERNALLY_PUBLISHED);
            }
        }
    }

    private String getContactUrn(ServiceContext serviceContext, ExternalItemDto contact) throws MetamacWebException {
        String urnProvider = contact.getUrnProvider();
        String urn = contact.getUrn();

        if (StringUtils.isBlank(urnProvider) && StringUtils.isBlank(urn)) {
            WebExceptionUtils.throwMetamacWebException(serviceContext, ServiceExceptionType.CONFIGURATION_ERROR_CONTACT_NOT_FOUND);
        }

        if (StringUtils.isNotBlank(urn)) {
            return urn;
        } else {
            return urnProvider;
        }
    }
}
