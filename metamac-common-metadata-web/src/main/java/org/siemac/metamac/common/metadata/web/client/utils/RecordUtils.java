package org.siemac.metamac.common.metadata.web.client.utils;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.model.ConfigurationRecord;

public class RecordUtils {

    /**
     * Returns {@link ConfigurationRecord} from {@link ConfigurationDto}
     * 
     * @param configurationDto
     * @return
     */
    public static ConfigurationRecord getConfigurationRecord(ConfigurationDto configurationDto) {
        ConfigurationRecord record = new ConfigurationRecord(configurationDto.getCode(), configurationDto.getStatus(), configurationDto);
        return record;
    }

}
