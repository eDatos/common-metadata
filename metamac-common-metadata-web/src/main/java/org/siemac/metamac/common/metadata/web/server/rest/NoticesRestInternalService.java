package org.siemac.metamac.common.metadata.web.server.rest;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface NoticesRestInternalService {

    public static final String BEAN_ID = "noticesRestInternalService";

    Notice retrieveNotificationByUrn(ServiceContext ctx, String notificationUrn) throws MetamacWebException;

    void createNotification(ServiceContext ctx, Notice notice) throws MetamacWebException;

}
