package org.siemac.metamac.common_metadata.rest.internal.v1_0.mapper;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response.Status;

import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common_metadata.rest.internal.RestInternalConstants;
import org.siemac.metamac.common_metadata.rest.internal.exception.RestServiceExceptionType;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.CommonMetadataStatus;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.common_metadata.rest.internal.v1_0.domain.Configurations;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.rest.common.v1_0.domain.Children;
import org.siemac.metamac.rest.common.v1_0.domain.Error;
import org.siemac.metamac.rest.common.v1_0.domain.ErrorParameters;
import org.siemac.metamac.rest.common.v1_0.domain.Errors;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.constants.RestEndpointsConstants;
import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.utils.RestUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Do2RestInternalMapperV10Impl implements Do2RestInternalMapperV10 {

    // @Context
    // private MessageContext context; // Always null in this bean (not in Service)...

    @Autowired
    private ConfigurationService configurationService;

    private String               srmApiEndpoint;

    @PostConstruct
    public void init() throws Exception {

        // Srm Api
        srmApiEndpoint = configurationService.getProperty(RestEndpointsConstants.SRM_INTERNAL_API);
        if (srmApiEndpoint == null) {
            throw new BeanCreationException("Property not found: " + RestEndpointsConstants.SRM_INTERNAL_API);
        }
    }
    
    @Override
    public Configuration toConfiguration(org.siemac.metamac.common.metadata.core.domain.Configuration source, String apiUrl) {
        if (source == null) {
            return null;
        }
        Configuration target = new Configuration();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(RestInternalConstants.KIND_CONFIGURATION);
        target.setSelfLink(toConfigurationLink(apiUrl, source));
        target.setLegalActs(toInternationalString(source.getLegalActs()));
        target.setDataSharing(toInternationalString(source.getDataSharing()));
        target.setConfPolicy(toInternationalString(source.getConfPolicy()));
        target.setConfDataTreatment(toInternationalString(source.getConfDataTreatment()));
        target.setContact(toResourceExternalItemSrm(source.getContact()));
        target.setStatus(toCommonMetadataStatusEnum(source.getStatus()));
        target.setParent(toConfigurationParent(apiUrl));
        target.setChildren(toConfigurationChildren(source, apiUrl));
        return target;
    }

    @Override
    public Configurations toConfigurations(List<org.siemac.metamac.common.metadata.core.domain.Configuration> sources, String apiUrl) {

        Configurations targets = new Configurations();
        targets.setKind(RestInternalConstants.KIND_CONFIGURATIONS);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (org.siemac.metamac.common.metadata.core.domain.Configuration source : sources) {
                Resource target = toResource(source, apiUrl);
                targets.getConfigurations().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    @Override
    public org.siemac.metamac.rest.common.v1_0.domain.Exception toException(Exception source) {
        org.siemac.metamac.rest.common.v1_0.domain.Exception target = null;
        if (source instanceof MetamacException) {
            MetamacException metamacException = (MetamacException) source;
            if (metamacException.getPrincipalException() != null) {
                target = new org.siemac.metamac.rest.common.v1_0.domain.Exception();
                Error principalError = toError(metamacException.getPrincipalException());
                target.setCode(principalError.getCode());
                target.setMessage(principalError.getMessage());
                target.setParameters(principalError.getParameters());
            } else {
                target = RestExceptionUtils.getException(RestCommonServiceExceptionType.UNKNOWN);
            }
            if (metamacException.getExceptionItems() != null && metamacException.getExceptionItems().size() != 0) {
                target.setErrors(new Errors());
                for (MetamacExceptionItem metamacExceptionItem : metamacException.getExceptionItems()) {
                    Error error = toError(metamacExceptionItem);
                    target.getErrors().getErrors().add(error);
                }
            }
        } else {
            target = RestExceptionUtils.getException(RestCommonServiceExceptionType.UNKNOWN);
        }
        return target;
    }

    private Error toError(MetamacExceptionItem metamacExceptionItem) {
        Error error = new Error();
        error.setCode(metamacExceptionItem.getCode());
        error.setMessage(metamacExceptionItem.getMessage());
        error.setParameters(new ErrorParameters());
        if (metamacExceptionItem.getMessageParameters() != null) {
            for (int i = 0; i < metamacExceptionItem.getMessageParameters().length; i++) {
                Serializable messageParameter = metamacExceptionItem.getMessageParameters()[i];
                String parameter = null;
                if (messageParameter instanceof String) {
                    parameter = messageParameter.toString();
                } else if (messageParameter instanceof String[]) {
                    parameter = Arrays.deepToString((String[]) messageParameter);
                } else {
                    parameter = messageParameter.toString();
                }
                error.getParameters().getParameters().add(parameter);
            }
        }
        return error;
    }

    private Resource toResource(org.siemac.metamac.common.metadata.core.domain.Configuration source, String apiUrl) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(RestInternalConstants.KIND_CONFIGURATION);
        target.setSelfLink(toConfigurationLink(apiUrl, source));
        // configuration has not title
        return target;
    }

    private Resource toResourceExternalItemSrm(ExternalItem source) {
        if (source == null) {
            return null;
        }
        return toResourceExternalItem(source, srmApiEndpoint);
    }

    private Resource toResourceExternalItem(ExternalItem source, String apiExternalItem) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(source.getType().name());
        target.setSelfLink(RestUtils.createLink(apiExternalItem, source.getUri()));
        target.setTitle(toInternationalString(source.getTitle()));
        return target;
    }

    private ResourceLink toConfigurationParent(String apiUrl) {
        ResourceLink target = new ResourceLink();
        target.setKind(RestInternalConstants.KIND_CONFIGURATIONS);
        target.setSelfLink(toConfigurationsLink(apiUrl));
        return target;
    }

    private Children toConfigurationChildren(org.siemac.metamac.common.metadata.core.domain.Configuration configuration, String apiUrl) {
        return null;
    }

    private InternationalString toInternationalString(org.siemac.metamac.core.common.ent.domain.InternationalString sources) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (org.siemac.metamac.core.common.ent.domain.LocalisedString source : sources.getTexts()) {
            LocalisedString target = new LocalisedString();
            target.setValue(source.getLabel());
            target.setLang(source.getLocale());
            targets.getTexts().add(target);
        }
        return targets;
    }

    // API/configurations
    private String toConfigurationsLink(String apiUrl) {
        return RestUtils.createLink(apiUrl, RestInternalConstants.LINK_SUBPATH_CONFIGURATIONS);
    }

    // API/configurations/configuration
    private String toConfigurationLink(String apiUrl, org.siemac.metamac.common.metadata.core.domain.Configuration configuration) {
        String linkConfigurations = toConfigurationsLink(apiUrl);
        return RestUtils.createLink(linkConfigurations, configuration.getCode());
    }

    private CommonMetadataStatus toCommonMetadataStatusEnum(CommonMetadataStatusEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case DISABLED:
                return CommonMetadataStatus.DISABLED;
            case ENABLED:
                return CommonMetadataStatus.ENABLED;
            default:
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }
}
