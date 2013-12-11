package org.siemac.metamac.common.metadata.core.error;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    // Extended Error Codes
    public static final CommonServiceExceptionType CONFIGURATION_NOT_FOUND                     = create("exception.common_metadata.configuration.not_found");
    public static final CommonServiceExceptionType CONFIGURATION_NOT_FOUND_URN                 = create("exception.common_metadata.configuration.not_found_urn");
    public static final CommonServiceExceptionType CONFIGURATION_ALREADY_EXIST_CODE_DUPLICATED = create("exception.common_metadata.configuration.already_exists.code_duplicated");

    public static final CommonServiceExceptionType DATA_CONFIGURATION_NOT_FOUND                = create("exception.common_metadata.data_configuration.not_found");
    public static final CommonServiceExceptionType DATA_CONFIGURATION_NOT_FOUND_CONFIGURATION_KEY   = create("exception.common_metadata.data_configuration.not_found_configuration_key");
}
