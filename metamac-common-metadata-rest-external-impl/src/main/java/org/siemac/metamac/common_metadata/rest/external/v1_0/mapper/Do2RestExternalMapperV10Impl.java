package org.siemac.metamac.common_metadata.rest.external.v1_0.mapper;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response.Status;

import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common_metadata.rest.external.RestExternalConstants;
import org.siemac.metamac.common_metadata.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.CommonMetadataStatus;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;
import org.siemac.metamac.rest.constants.RestEndpointsConstants;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.utils.RestUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Do2RestExternalMapperV10Impl implements Do2RestExternalMapperV10 {

    @Autowired
    private ConfigurationService configurationService;

    private String               commonMetadataApiExternalEndpointV10;
    private String               srmApiExternalEndpoint;

    @PostConstruct
    public void init() throws Exception {

        // Common metadata External Api
        String commonMetadataApiExternalEndpoint = configurationService.getProperty(RestEndpointsConstants.COMMON_METADATA_EXTERNAL_API);
        if (commonMetadataApiExternalEndpoint == null) {
            throw new BeanCreationException("Property not found: " + RestEndpointsConstants.COMMON_METADATA_EXTERNAL_API);
        }
        commonMetadataApiExternalEndpointV10 = RestUtils.createLink(commonMetadataApiExternalEndpoint, RestExternalConstants.API_VERSION_1_0);

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
        target.setKind(RestExternalConstants.KIND_CONFIGURATION);
        target.setSelfLink(toConfigurationSelfLink(source));
        target.setLegalActs(toInternationalString(source.getLegalActs()));
        target.setDataSharing(toInternationalString(source.getDataSharing()));
        target.setConfPolicy(toInternationalString(source.getConfPolicy()));
        target.setConfDataTreatment(toInternationalString(source.getConfDataTreatment()));
        target.setContact(toResourceExternalItemSrm(source.getContact()));
        target.setStatus(toCommonMetadataStatusEnum(source.getStatus()));
        target.setParentLink(toConfigurationParent());
        target.setChildLinks(toConfigurationChildLinks(source));
        return target;
    }

    @Override
    public Configurations toConfigurations(List<org.siemac.metamac.common.metadata.core.domain.Configuration> sources) {

        Configurations targets = new Configurations();
        targets.setKind(RestExternalConstants.KIND_CONFIGURATIONS);

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
        target.setKind(RestExternalConstants.KIND_CONFIGURATION);
        target.setSelfLink(toConfigurationSelfLink(source));
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
        String kind = source.getType().getValue();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(kind);
        target.setSelfLink(toResourceLink(kind, RestUtils.createLink(apiExternalItem, source.getUri())));
        target.setTitle(toInternationalString(source.getTitle()));
        return target;
    }

    private ResourceLink toConfigurationSelfLink(org.siemac.metamac.common.metadata.core.domain.Configuration configuration) {
        return toResourceLink(RestExternalConstants.KIND_CONFIGURATION, toConfigurationLink(configuration));
    }

    private ResourceLink toConfigurationParent() {
        ResourceLink target = new ResourceLink();
        target.setKind(RestExternalConstants.KIND_CONFIGURATIONS);
        target.setHref(toConfigurationsLink());
        return target;
    }

    private ChildLinks toConfigurationChildLinks(org.siemac.metamac.common.metadata.core.domain.Configuration configuration) {
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
        return RestUtils.createLink(commonMetadataApiExternalEndpointV10, RestExternalConstants.LINK_SUBPATH_CONFIGURATIONS);
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

    private ResourceLink toResourceLink(String kind, String href) {
        ResourceLink target = new ResourceLink();
        target.setKind(kind);
        target.setHref(href);
        return target;
    }
}