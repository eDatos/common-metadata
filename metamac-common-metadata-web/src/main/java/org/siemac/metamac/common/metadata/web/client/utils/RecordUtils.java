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
        ConfigurationRecord record = new ConfigurationRecord();
        record.setCode(configurationDto.getCode());
        record.setStatus(configurationDto.getStatus());
        record.setExternallyPublished(configurationDto.isExternallyPublished());
        record.setUrn(configurationDto.getUrn());
        record.setContact(configurationDto.getContact());
        record.setConfigurationDto(configurationDto);
        return record;
    }
}
