package org.siemac.metamac.common.metadata.web.server.rest;

import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.Response;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.conf.CommonMetadataConfigurationService;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.mapper.Dto2DoMapper;
import org.siemac.metamac.common.metadata.core.notices.ServiceNoticeAction;
import org.siemac.metamac.common.metadata.core.notices.ServiceNoticeMessage;
import org.siemac.metamac.common_metadata.rest.external.v1_0.mapper.Do2RestExternalMapperV10;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.core.common.util.ServiceContextUtils;
import org.siemac.metamac.rest.notices.v1_0.domain.Message;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacApplicationsEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacRolesEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.MessageBuilder;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.NoticeBuilder;
import org.siemac.metamac.web.common.server.rest.utils.RestExceptionUtils;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(NoticesRestInternalService.BEAN_ID)
public class NoticesRestInternalFacadeImpl implements NoticesRestInternalService {

    @Autowired
    private RestApiLocator             restApiLocator;

    @Autowired
    private RestExceptionUtils         restExceptionUtils;

    @Autowired
    CommonMetadataConfigurationService configurationService;

    @Autowired
    Dto2DoMapper                       dto2DoMapper;

    @Autowired
    Do2RestExternalMapperV10           do2RestExternalMapperV10;

    // ---------------------------------------------------------------------------------
    // NOTIFICATIONS
    // ---------------------------------------------------------------------------------

    @Override
    public void createNotificationForPublishExternallyConfiguration(ServiceContext ctx, ConfigurationDto configurationDto) throws MetamacWebException {
        ResourceInternal resource = configurationDtoToResourceInternal(ctx, configurationDto);
        ResourceInternal[] noticeResource = {resource};
        MetamacApplicationsEnum[] applications = {MetamacApplicationsEnum.GESTOR_RECURSOS_ESTADISTICOS};
        MetamacRolesEnum[] roles = {MetamacRolesEnum.TECNICO_APOYO_PRODUCCION, MetamacRolesEnum.TECNICO_APOYO_PRODUCCION, MetamacRolesEnum.JEFE_PRODUCCION};

        createNotification(ctx, ServiceNoticeAction.CONFIGURATION_PUBLISH_EXTERNALLY, ServiceNoticeMessage.CONFIGURATION_PUBLISH_EXTERNALLY_OK, noticeResource, applications, roles);
    }

    @Override
    public void createNotificationForUpdateConfigurationStatus(ServiceContext ctx, List<ConfigurationDto> configurationDtos) throws MetamacWebException {
        ResourceInternal[] noticeResources = configurationDtoListToResourceInternalList(ctx, configurationDtos);
        MetamacApplicationsEnum[] applications = {MetamacApplicationsEnum.GESTOR_RECURSOS_ESTADISTICOS};
        MetamacRolesEnum[] roles = {MetamacRolesEnum.TECNICO_APOYO_PRODUCCION, MetamacRolesEnum.TECNICO_APOYO_PRODUCCION, MetamacRolesEnum.JEFE_PRODUCCION};

        createNotification(ctx, ServiceNoticeAction.CONFIGURATION_UPDATE_STATUS, ServiceNoticeMessage.CONFIGURATION_UPDATE_STATUS_OK, noticeResources, applications, roles);
    }

    private void createNotification(ServiceContext ctx, String actionCode, String messageCode, ResourceInternal[] resources, MetamacApplicationsEnum[] applications, MetamacRolesEnum[] roles)
            throws MetamacWebException {
        Locale locale = ServiceContextUtils.getLocale(ctx);
        String subject = LocaleUtil.getMessageForCode(actionCode, locale);
        String localisedMessage = LocaleUtil.getMessageForCode(messageCode, locale);
        String sendingApp = MetamacApplicationsEnum.GESTOR_METADATOS_COMUNES.getName();

        // @formatter:off
            Message message = MessageBuilder.message()
                                            .withText(localisedMessage)
                                            .withResources(resources)
                                            .build();
            
            Notice notification = NoticeBuilder.notification()
                                                .withMessages(message)
                                                .withSendingApplication(sendingApp)
                                                .withSendingUser(ctx.getUserId())
                                                .withSubject(subject)
                                                .withApplications(applications)
                                                .withRoles(roles)
                                                .build();
            // @formatter:on

        Response response = restApiLocator.getNoticesRestInternalFacadeV10().createNotice(notification);
        restExceptionUtils.checkSendNotificationRestResponseAndThrowErrorIfApplicable(ctx, response);
    }

    private ResourceInternal[] configurationDtoListToResourceInternalList(ServiceContext ctx, List<ConfigurationDto> configurationDtos) throws MetamacWebException {
        ResourceInternal[] resources = new ResourceInternal[configurationDtos.size()];

        for (int i = 0; i < configurationDtos.size(); i++) {
            resources[i] = configurationDtoToResourceInternal(ctx, configurationDtos.get(i));
        }

        return resources;
    }

    private ResourceInternal configurationDtoToResourceInternal(ServiceContext ctx, ConfigurationDto configurationDto) throws MetamacWebException {
        Configuration configuration;
        try {
            configuration = dto2DoMapper.configurationDtoToDo(ctx, configurationDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
        org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal commonMetadataResource = do2RestExternalMapperV10.toResource(configuration);
        org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal noticeResource = toNoticeResourceInternal(commonMetadataResource);
        return noticeResource;
    }

    private ResourceInternal toNoticeResourceInternal(org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal commonMetadataResourceInternal) {
        ResourceInternal resource = new ResourceInternal();

        if (commonMetadataResourceInternal != null) {
            resource.setId(commonMetadataResourceInternal.getId());
            resource.setKind(commonMetadataResourceInternal.getKind());
            resource.setManagementAppLink(commonMetadataResourceInternal.getManagementAppLink());
            resource.setName(commonMetadataResourceInternal.getName());
            resource.setNestedId(commonMetadataResourceInternal.getNestedId());
            resource.setSelfLink(commonMetadataResourceInternal.getSelfLink());
            resource.setUrn(commonMetadataResourceInternal.getUrn());
            resource.setUrnProvider(null);
        }
        return resource;
    }

}
