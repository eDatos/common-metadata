package org.siemac.metamac.common.metadata.web.client.model.ds;

import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class ConfigurationDS extends DataSource {

    public static final String CODE                = "code";
    public static final String STATIC_CODE         = "code-view"; // Not mapped in DTO
    public static final String URN                 = "urn";
    public static final String STATUS              = "status";
    public static final String LEGAL_ACTS          = "legal";
    public static final String DATA_SHARING        = "sharing";
    public static final String CONF_POLYCY         = "policy";
    public static final String CONF_DATA_TREATMENT = "data";
    public static final String ORGANISATION        = "organ";

    public static final String CONFIGURATION_DTO   = "conf-dto";

    public ConfigurationDS() {
        DataSourceTextField uuid = new DataSourceTextField(CODE, CommonMetadataWeb.getConstants().confCode());
        uuid.setPrimaryKey(true);
        addField(uuid);
    }
}
