package org.siemac.metamac.common.metadata.web.server.rest;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface NotificationsRestInternalService {

    public static final String BEAN_ID = "notificationsRestInternalService";

    public Notification retrieveNotificationByUrn(ServiceContext ctx, String notificationUrn) throws MetamacWebException;

    public void createNotification(ServiceContext ctx, Notification notification) throws MetamacWebException;

}
