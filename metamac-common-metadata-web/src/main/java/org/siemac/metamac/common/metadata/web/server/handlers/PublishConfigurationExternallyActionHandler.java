package org.siemac.metamac.common.metadata.web.server.handlers;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.serviceapi.CommonMetadataServiceFacade;
import org.siemac.metamac.common.metadata.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.common.metadata.web.server.utils.WebTranslateExceptions;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyAction;
import org.siemac.metamac.common.metadata.web.shared.PublishConfigurationExternallyResult;
import org.siemac.metamac.common.metadata.web.shared.constants.WebMessageExceptionsConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.lang.shared.LocaleConstants;
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

    @Autowired
    private WebTranslateExceptions      webTranslateExceptions;

    public PublishConfigurationExternallyActionHandler() {
        super(PublishConfigurationExternallyAction.class);
    }

    @Override
    public PublishConfigurationExternallyResult executeSecurityAction(PublishConfigurationExternallyAction action) throws ActionException {
        try {
            ServiceContext serviceContext = ServiceContextHolder.getCurrentServiceContext();

            ConfigurationDto configurationToPublish = commonMetadataServiceFacade.findConfigurationById(serviceContext, action.getId());
            checkContactIsExternallyPublished(serviceContext, configurationToPublish);

            ConfigurationDto configurationPublished = commonMetadataServiceFacade.publishExternallyConfiguration(serviceContext, action.getId());
            return new PublishConfigurationExternallyResult(configurationPublished);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    private void checkContactIsExternallyPublished(ServiceContext serviceContext, ConfigurationDto configurationDto) throws ActionException {
        ExternalItemDto contact = configurationDto.getContact();
        if (contact != null) {

            String contactUrn = getContactUrn(serviceContext, contact);

            SrmItemRestCriteria criteria = new SrmItemRestCriteria();
            criteria.setUrn(contactUrn);
            criteria.setIsItemSchemeExternallyPublished(Boolean.TRUE);

            ExternalItemsResult result = srmRestInternalFacade.findAgencies(serviceContext, criteria, 0, 1);
            if (result.getExternalItemDtos().isEmpty()) {

                throwMetamacWebException(serviceContext, WebMessageExceptionsConstants.CONFIGURATION_ERROR_CONTACT_NOT_EXTERNALLY_PUBLISHED);
            }
        }
    }

    private String getContactUrn(ServiceContext serviceContext, ExternalItemDto contact) throws MetamacWebException {
        String urn = contact.getUrn();
        String urnInternal = contact.getUrnInternal();

        if (StringUtils.isBlank(urn) && StringUtils.isBlank(urnInternal)) {
            throwMetamacWebException(serviceContext, WebMessageExceptionsConstants.CONFIGURATION_ERROR_CONTACT_NOT_FOUND);
        }

        if (StringUtils.isNotBlank(urn)) {
            return urn;
        } else {
            return urnInternal;
        }
    }

    private void throwMetamacWebException(ServiceContext serviceContext, String exceptionCode) throws MetamacWebException {
        Locale locale = (Locale) serviceContext.getProperty(LocaleConstants.locale);
        String exceptionnMessage = webTranslateExceptions.getTranslatedMessage(exceptionCode, locale);

        throw new MetamacWebException(exceptionCode, exceptionnMessage);
    }
}
