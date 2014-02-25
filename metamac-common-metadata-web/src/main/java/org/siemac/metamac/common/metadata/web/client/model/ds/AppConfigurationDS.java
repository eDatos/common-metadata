package org.siemac.metamac.common.metadata.web.client.model.ds;

import org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class AppConfigurationDS extends DataSource {

    public static final String KEY                   = "key";
    public static final String VALUE                 = "value";

    public static final String APP_CONFIGURATION_DTO = "conf-dto";
    public static final String APP_EXTERNAL_ITEM_DTO = "conf-ei-dto";

    public AppConfigurationDS() {
        DataSourceTextField uuid = new DataSourceTextField(KEY, CommonMetadataWeb.getConstants().applicationConfigurationKey());
        uuid.setPrimaryKey(true);
        addField(uuid);
    }
}
