package org.siemac.metamac.common.metadata.rest.internal.v1_0.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.common.metadata.rest.internal.RestInternalConstants;
import org.siemac.metamac.common.metadata.rest.internal.v1_0.domain.Configuration;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.rest.common.v1_0.domain.Error;
import org.siemac.metamac.rest.common.v1_0.domain.ErrorItem;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.utils.RestUtils;
import org.springframework.stereotype.Component;

@Component
public class Do2RestInternalMapperV10Impl implements Do2RestInternalMapperV10 {

    // @Context
    // private MessageContext context; // Always null in this bean (not in Service)...

    @Override
    public Configuration toConfiguration(org.siemac.metamac.common.metadata.core.domain.Configuration source, String apiUrl) {
        if (source == null) {
            return null;
        }
        Configuration target = new Configuration();
        target.setId(source.getCode());
        target.setKind(RestInternalConstants.KIND_CONFIGURATION);
        target.setSelfLink(toConfigurationLink(apiUrl, source));
        target.setLegalActs(toInternationalString(source.getLegalActs()));
        target.setDataSharing(toInternationalString(source.getDataSharing()));
        target.setConfPolicy(toInternationalString(source.getConfPolicy()));
        target.setConfDataTreatment(toInternationalString(source.getConfDataTreatment()));
        target.setContact(toResource(source.getContact()));
        target.setStatus(source.getStatus().getName());
        target.setParent(toConfigurationParent(apiUrl));
        target.getchildren().addAll(toConfigurationChildren(source, apiUrl));
        return target;
    }
    
    // TODO pasar a librería común toError? Si se crea metamac-api-domain sólo con clases de Interfaz
    @Override
    public Error toError(Exception exception) {
        Error error = new Error();
        error.getErrorItems().addAll(toErrorItems(exception));
        return error;
    }

    private Resource toConfigurationParent(String apiUrl) {
        Resource target = new Resource();
        target.setKind(RestInternalConstants.KIND_CONFIGURATIONS);
        target.setSelfLink(toConfigurationsLink(apiUrl));
        return target;
    }

    private List<Resource> toConfigurationChildren(org.siemac.metamac.common.metadata.core.domain.Configuration configuration, String apiUrl) {
        List<Resource> targets = new ArrayList<Resource>();
        // nothing
        return targets;
    }
    
    private Resource toResource(ExternalItem source) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getUrn());
        target.setKind(source.getType().name());
        target.setSelfLink(source.getUri()); // TODO añadir endpoint METAMAC-785
        target.setTitle(toInternationalString(source.getTitle()));
        return target;
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

    // API/configurations/CONFIGURATION_ID
    private String toConfigurationLink(String apiUrl, org.siemac.metamac.common.metadata.core.domain.Configuration configuration) {
        String linkConfigurations = toConfigurationsLink(apiUrl);
        return RestUtils.createLink(linkConfigurations, configuration.getCode());
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
}
