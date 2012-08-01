package org.siemac.metamac.common.metadata.rest.internal.v1_0.mapper;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.rest.internal.RestInternalConstants;
import org.siemac.metamac.common.metadata.rest.internal.exception.RestServiceExceptionType;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.CommonMetadataStatus;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.rest.common.v1_0.domain.Error;
import org.siemac.metamac.rest.common.v1_0.domain.ErrorItem;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common.v1_0.domain.ResourcesNoPagedResult;
import org.siemac.metamac.rest.constants.RestEndpointsConstants;
import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.utils.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Do2RestInternalMapperV10Impl implements Do2RestInternalMapperV10 {

    // @Context
    // private MessageContext context; // Always null in this bean (not in Service)...

    @Autowired
    private ConfigurationService configurationService;

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
        target.getChildren().addAll(toConfigurationChildren(source, apiUrl));
        return target;
    }

    @Override
    public ResourcesNoPagedResult toConfigurationsNoPagedResult(List<org.siemac.metamac.common.metadata.core.domain.Configuration> sources, String apiUrl) {

        ResourcesNoPagedResult targets = new ResourcesNoPagedResult();
        targets.setKind(RestInternalConstants.KIND_CONFIGURATIONS);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (org.siemac.metamac.common.metadata.core.domain.Configuration source : sources) {
                Resource target = toResource(source, apiUrl);
                targets.getItems().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    // TODO pasar a librería común toError? Si se crea metamac-api-domain sólo con clases de Interfaz
    @Override
    public Error toError(Exception exception) {
        Error error = new Error();
        error.getErrorItems().addAll(toErrorItems(exception));
        return error;
    }

    private List<ErrorItem> toErrorItems(Exception exception) {

        List<ErrorItem> errorItems = new ArrayList<ErrorItem>();
        if (exception instanceof MetamacException) {
            MetamacException metamacException = (MetamacException) exception;
            for (MetamacExceptionItem metamacExceptionItem : metamacException.getExceptionItems()) {
                ErrorItem errorItem = new ErrorItem();
                errorItem.setCode(metamacExceptionItem.getCode());
                errorItem.setMessage(metamacExceptionItem.getMessage());
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
                        errorItem.getParameters().add(parameter);
                    }
                }
                errorItems.add(errorItem);
            }
        } else {
            ErrorItem errorItem = RestExceptionUtils.getErrorItem(RestCommonServiceExceptionType.UNKNOWN);
            errorItems.add(errorItem);
        }
        return errorItems;
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
        // no title
        return target;
    }

    private Resource toResourceExternalItemSrm(ExternalItem source) {
        String apiExternalItem = getSrmEndpointInternalApi();
        return toResourceExternalItem(source, apiExternalItem);
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

    private List<ResourceLink> toConfigurationChildren(org.siemac.metamac.common.metadata.core.domain.Configuration configuration, String apiUrl) {
        List<ResourceLink> targets = new ArrayList<ResourceLink>();
        // nothing

        return targets;
    }

    private InternationalString toInternationalString(org.siemac.metamac.core.common.ent.domain.InternationalString sources) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (org.siemac.metamac.core.common.ent.domain.LocalisedString source : sources.getTexts()) {
            LocalisedString target = new LocalisedString();
            target.setLabel(source.getLabel());
            target.setLocale(source.getLocale());
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
                Error error = RestExceptionUtils.getError(RestServiceExceptionType.UNKNOWN);
                throw new RestException(error, Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    private String getSrmEndpointInternalApi() {
        String property = configurationService.getProperty(RestEndpointsConstants.SRM_INTERNAL_API);
        if (StringUtils.isBlank(property)) {
            Error error = RestExceptionUtils.getError(RestServiceExceptionType.UNKNOWN, "Property not found: " + RestEndpointsConstants.SRM_INTERNAL_API);
            throw new RestException(error, Status.INTERNAL_SERVER_ERROR);
        }
        return property;
    }
}
