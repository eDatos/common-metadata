package org.siemac.metamac.common_metadata.rest.internal.exception;

import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;

public class RestServiceExceptionType extends RestCommonServiceExceptionType {

    public static final RestCommonServiceExceptionType CONFIGURATION_NOT_FOUND = create("exception.common_metadata.configuration.not_found");
}