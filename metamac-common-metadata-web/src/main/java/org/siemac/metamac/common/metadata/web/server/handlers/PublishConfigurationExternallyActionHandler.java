package org.siemac.metamac.common.metadata.web.server.handlers;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyAction;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyResult;
import org.siemac.metamac.common.metadata.web.shared.constants.WebMessageExceptionsConstants;
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
        ExternalItemDto contact = configurationDto.getContact();
        if (contact != null) {

            String contactUrn = getContactUrn(contact);

            SrmItemRestCriteria criteria = new SrmItemRestCriteria();
            criteria.setUrn(contactUrn);
            criteria.setIsItemSchemeExternallyPublished(Boolean.TRUE);

            ExternalItemsResult result = srmRestInternalFacade.findAgencies(criteria, 0, 1);
            if (result.getExternalItemDtos().isEmpty()) {
                throw new MetamacWebException(WebMessageExceptionsConstants.CONFIGURATION_ERROR_CONTACT_NOT_EXTERNALLY_PUBLISHED,
                        "Configuration cannot be published because the related organisation is not externally published");
            }
        }
    }

    private String getContactUrn(ExternalItemDto contact) throws MetamacWebException {
        String urn = contact.getUrn();
        String urnInternal = contact.getUrnInternal();
        if (StringUtils.isNotBlank(urn)) {
            return urn;
        } else if (StringUtils.isNotBlank(urnInternal)) {
            return urnInternal;
        }
        throw new MetamacWebException(WebMessageExceptionsConstants.CONFIGURATION_ERROR_CONTACT_NOT_FOUND, "Configuration contact cannot be found");
    }
}
