package org.siemac.metamac.common.metadata.web.server.rest;

import javax.annotation.PostConstruct;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.notifications.rest.internal.v1_0.service.NotificationsV1_0;
import org.siemac.metamac.srm.rest.internal.v1_0.service.SrmRestInternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestApiLocator {

    @Autowired
    private ConfigurationService     configurationService;

    private SrmRestInternalFacadeV10 srmRestInternalFacadeV10           = null;
    private NotificationsV1_0        notificationsRestInternalFacadeV10 = null;

    @PostConstruct
    public void initService() throws Exception {
        String srmBaseApi = configurationService.retrieveSrmInternalApiUrlBase();
        srmRestInternalFacadeV10 = JAXRSClientFactory.create(srmBaseApi, SrmRestInternalFacadeV10.class, null, true);

        String notificationsBaseApi = configurationService.retrieveNotificationsInternalApiUrlBase();
        notificationsRestInternalFacadeV10 = JAXRSClientFactory.create(notificationsBaseApi, NotificationsV1_0.class, null, true);
    }

    public SrmRestInternalFacadeV10 getSrmRestInternalFacadeV10() {
        // reset thread context
        WebClient.client(srmRestInternalFacadeV10).reset();
        WebClient.client(srmRestInternalFacadeV10).accept("application/xml");

        return srmRestInternalFacadeV10;
    }

    public NotificationsV1_0 getNotificationsRestInternalFacadeV10() {
        // reset thread context
        WebClient.client(notificationsRestInternalFacadeV10).reset();
        WebClient.client(notificationsRestInternalFacadeV10).accept("application/xml");

        return notificationsRestInternalFacadeV10;
    }
}
