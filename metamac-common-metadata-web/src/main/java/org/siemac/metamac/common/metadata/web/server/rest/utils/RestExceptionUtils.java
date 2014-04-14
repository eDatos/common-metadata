package org.siemac.metamac.common.metadata.web.server.rest.utils;

import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.error.ServiceExceptionType;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.lang.shared.LocaleConstants;
import org.siemac.metamac.web.common.server.utils.WebTranslateExceptions;
import org.siemac.metamac.web.common.shared.constants.CommonSharedConstants;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestExceptionUtils {

    private static Logger          logger = Logger.getLogger(RestExceptionUtils.class.getName());

    @Autowired
    private WebTranslateExceptions webTranslateExceptions;

    public MetamacWebException manageMetamacRestException(ServiceContext ctx, Exception e, String apiName, Object clientProxy) throws MetamacWebException {
        logger.log(Level.SEVERE, e.getMessage());

        if (e instanceof ServerWebApplicationException) {
            return manageMetamacServerWebApplicationException(ctx, (ServerWebApplicationException) e, apiName, clientProxy);
        } else {
            return new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, e.getMessage());
        }
    }

    private MetamacWebException manageMetamacServerWebApplicationException(ServiceContext ctx, ServerWebApplicationException e, String apiName, Object clientProxy) throws MetamacWebException {
        org.siemac.metamac.rest.common.v1_0.domain.Exception exception = e.toErrorObject(WebClient.client(clientProxy), org.siemac.metamac.rest.common.v1_0.domain.Exception.class);
        MetamacWebException metamacWebException;

        if (exception == null) {
            if (Status.NOT_FOUND.getStatusCode() == e.getResponse().getStatus()) {
                metamacWebException = throwMetamacWebException(ctx, ServiceExceptionType.REST_API_INVOCATION_ERROR_NOT_FOUND_API, apiName);
            } else {
                metamacWebException = throwMetamacWebException(ctx, ServiceExceptionType.REST_API_INVOCATION_ERROR_UNKNOWN_API, e);
            }
        } else {
            if (Status.NOT_FOUND.getStatusCode() == e.getStatus()) {
                metamacWebException = throwMetamacWebException(ctx, ServiceExceptionType.REST_API_INVOCATION_ERROR_NOT_FOUND_RESOURCE, exception.getMessage());
            } else {
                metamacWebException = throwMetamacWebException(ctx, ServiceExceptionType.REST_API_INVOCATION_ERROR_UNKNOWN_RESOURCE, exception.getMessage());
            }
        }

        return metamacWebException;
    }

    private MetamacWebException throwMetamacWebException(ServiceContext ctx, CommonServiceExceptionType errorCode, Serializable... parameter) throws MetamacWebException {
        Locale locale = (Locale) ctx.getProperty(LocaleConstants.locale);
        String exceptionnMessage = webTranslateExceptions.getTranslatedMessage(errorCode, locale, parameter);

        return new MetamacWebException(errorCode.toString(), exceptionnMessage);
    }
}
