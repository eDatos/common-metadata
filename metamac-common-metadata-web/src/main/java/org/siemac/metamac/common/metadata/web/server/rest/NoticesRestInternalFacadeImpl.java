package org.siemac.metamac.common.metadata.web.server.rest;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionParameters;
import org.siemac.metamac.common.metadata.web.server.rest.utils.RestExceptionUtils;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(NoticesRestInternalService.BEAN_ID)
public class NoticesRestInternalFacadeImpl implements NoticesRestInternalService {

    @Autowired
    private RestApiLocator     restApiLocator;

    @Autowired
    private RestExceptionUtils restExceptionUtils;

    // ---------------------------------------------------------------------------------
    // NOTIFICATIONS
    // ---------------------------------------------------------------------------------

    @Override
    public Notice retrieveNotificationByUrn(ServiceContext ctx, String notificationUrn) throws MetamacWebException {
        try {
            return restApiLocator.getNotificationsRestInternalFacadeV10().retrieveNoticeByUrn(notificationUrn);
        } catch (Exception e) {
            throw manageNotificationsInternalRestException(ctx, e);
        }
    }

    @Override
    public void createNotification(ServiceContext ctx, Notice notification) throws MetamacWebException {
        try {
            restApiLocator.getNotificationsRestInternalFacadeV10().createNotice(notification);
        } catch (Exception e) {
            throw manageNotificationsInternalRestException(ctx, e);
        }
    }

    private MetamacWebException manageNotificationsInternalRestException(ServiceContext ctx, Exception e) throws MetamacWebException {
        return restExceptionUtils.manageMetamacRestException(ctx, e, ServiceExceptionParameters.API_STATISTICAL_OPERATIONS_INTERNAL, restApiLocator.getNotificationsRestInternalFacadeV10());
    }
}
