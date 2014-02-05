package org.siemac.metamac.common.metadata.web.server.rest;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionParameters;
import org.siemac.metamac.common.metadata.web.server.rest.utils.RestExceptionUtils;
import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(NotificationsRestInternalService.BEAN_ID)
public class NotificationsRestInternalFacadeImpl implements NotificationsRestInternalService {

    @Autowired
    private RestApiLocator     restApiLocator;

    @Autowired
    private RestExceptionUtils restExceptionUtils;

    // ---------------------------------------------------------------------------------
    // NOTIFICATIONS
    // ---------------------------------------------------------------------------------

    @Override
    public Notification retrieveNotificationByUrn(ServiceContext ctx, String notificationUrn) throws MetamacWebException {
        try {
            return restApiLocator.getNotificationsRestInternalFacadeV10().retrieveResourceByUrn(notificationUrn);
        } catch (Exception e) {
            throw manageNotificationsInternalRestException(ctx, e);
        }
    }

    @Override
    public void createNotification(ServiceContext ctx, Notification notification) throws MetamacWebException {
        try {
            restApiLocator.getNotificationsRestInternalFacadeV10().createNotification(notification);
        } catch (Exception e) {
            throw manageNotificationsInternalRestException(ctx, e);
        }
    }

    private MetamacWebException manageNotificationsInternalRestException(ServiceContext ctx, Exception e) throws MetamacWebException {
        return restExceptionUtils.manageMetamacRestException(ctx, e, ServiceExceptionParameters.API_STATISTICAL_OPERATIONS_INTERNAL, restApiLocator.getNotificationsRestInternalFacadeV10());
    }
}
