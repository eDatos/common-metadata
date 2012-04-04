package org.siemac.metamac.common.metadata.core.error;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    public static final CommonServiceExceptionType SERVICE_INVALID_PARAMETER_COLLECTION_EMPTY    = create("exception.service.invalid.parameter.collection_empty");
    public static final CommonServiceExceptionType SERVICE_INVALID_PARAMETER_UNEXPECTED          = create("exception.service.invalid.parameter.unexpected");

    public static final CommonServiceExceptionType SERVICE_INVALID_PARAMETER_NULL                = create("exception.service.invalid.parameter.null");
    public static final CommonServiceExceptionType SERVICE_INVALID_NOT_FOUND                     = create("exception.service.invalid.parameter.not_found");
    public static final CommonServiceExceptionType SERVICE_INVALID_PROC_STATUS                   = create("exception.service.invalid.procStatus");

    public static final CommonServiceExceptionType SERVICE_SEARCH_NOT_FOUND                      = create("exception.service.search.not_found");
    public static final CommonServiceExceptionType SERVICE_VALIDATION_CONSTRAINT_ENUMERATED      = create("exception.service.validation.constraint.enumerated");
    public static final CommonServiceExceptionType SERVICE_VALIDATION_CONSTRAINT_CARDINALITY_MAX = create("exception.service.validation.constraint.cardinality_max");
    public static final CommonServiceExceptionType SERVICE_VALIDATION_COLLECTION_EMPTY           = create("exception.service.validation.collection_empty");
    public static final CommonServiceExceptionType SERVICE_VALIDATION_METADATA_REQUIRED          = create("exception.service.validation.metadata.required");

    public static final CommonServiceExceptionType SERVICE_CONFIGURATION_NOT_FOUND               = create("exception.service.configuration.not.found");

}
