package org.siemac.metamac.common_metadata.rest.internal.v1_0.mapper;

import java.math.BigInteger;
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
import org.siemac.metamac.rest.common.v1_0.domain.Children;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.constants.RestEndpointsConstants;
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

    private String               commonMetadataApiInternalEndpointV10;
    private String               srmApiExternalEndpoint;

    @PostConstruct
    public void init() throws Exception {

        // Statistical operations Internal Api
        String commonMetadataApiInternalEndpoint = configurationService.getProperty(RestEndpointsConstants.COMMON_METADATA_INTERNAL_API);
        if (commonMetadataApiInternalEndpoint == null) {
            throw new BeanCreationException("Property not found: " + RestEndpointsConstants.COMMON_METADATA_INTERNAL_API);
        }
        commonMetadataApiInternalEndpointV10 = RestUtils.createLink(commonMetadataApiInternalEndpoint, RestInternalConstants.API_VERSION_1_0);

        // Srm External Api
        srmApiExternalEndpoint = configurationService.getProperty(RestEndpointsConstants.SRM_EXTERNAL_API);
        if (srmApiExternalEndpoint == null) {
            throw new BeanCreationException("Property not found: " + RestEndpointsConstants.SRM_EXTERNAL_API);
        }
    }

    @Override
    public Configuration toConfiguration(org.siemac.metamac.common.metadata.core.domain.Configuration source) {
        if (source == null) {
            return null;
        }
        Configuration target = new Configuration();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(RestInternalConstants.KIND_CONFIGURATION);
        target.setSelfLink(toConfigurationLink(source));
        target.setLegalActs(toInternationalString(source.getLegalActs()));
        target.setDataSharing(toInternationalString(source.getDataSharing()));
        target.setConfPolicy(toInternationalString(source.getConfPolicy()));
        target.setConfDataTreatment(toInternationalString(source.getConfDataTreatment()));
        target.setContact(toResourceExternalItemSrm(source.getContact()));
        target.setStatus(toCommonMetadataStatusEnum(source.getStatus()));
        target.setParent(toConfigurationParent());
        target.setChildren(toConfigurationChildren(source));
        return target;
    }

    @Override
    public Configurations toConfigurations(List<org.siemac.metamac.common.metadata.core.domain.Configuration> sources) {

        Configurations targets = new Configurations();
        targets.setKind(RestInternalConstants.KIND_CONFIGURATIONS);

        if (sources == null) {
            targets.setTotal(BigInteger.ZERO);
        } else {
            for (org.siemac.metamac.common.metadata.core.domain.Configuration source : sources) {
                Resource target = toResource(source);
                targets.getConfigurations().add(target);
            }
            targets.setTotal(BigInteger.valueOf(sources.size()));
        }

        return targets;
    }

    private Resource toResource(org.siemac.metamac.common.metadata.core.domain.Configuration source) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(RestInternalConstants.KIND_CONFIGURATION);
        target.setSelfLink(toConfigurationLink(source));
        // configuration has not title
        return target;
    }

    private Resource toResourceExternalItemSrm(ExternalItem source) {
        if (source == null) {
            return null;
        }
        return toResourceExternalItem(source, srmApiExternalEndpoint);
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

    private ResourceLink toConfigurationParent() {
        ResourceLink target = new ResourceLink();
        target.setKind(RestInternalConstants.KIND_CONFIGURATIONS);
        target.setSelfLink(toConfigurationsLink());
        return target;
    }

    private Children toConfigurationChildren(org.siemac.metamac.common.metadata.core.domain.Configuration configuration) {
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
    private String toConfigurationsLink() {
        return RestUtils.createLink(commonMetadataApiInternalEndpointV10, RestInternalConstants.LINK_SUBPATH_CONFIGURATIONS);
    }

    // API/configurations/configuration
    private String toConfigurationLink(org.siemac.metamac.common.metadata.core.domain.Configuration configuration) {
        String linkConfigurations = toConfigurationsLink();
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
