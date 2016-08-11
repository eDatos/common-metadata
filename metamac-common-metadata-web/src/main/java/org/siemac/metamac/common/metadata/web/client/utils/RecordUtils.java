package org.siemac.metamac.common.metadata.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.common.metadata.web.client.model.AppConfigurationRecord;
import org.siemac.metamac.common.metadata.web.client.model.AppExternalItemConfigurationRecord;
import org.siemac.metamac.common.metadata.web.client.model.ConfigurationRecord;
import org.siemac.metamac.common.metadata.web.shared.dto.ExternalItemDataConfigurationDto;

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

    public static AppConfigurationRecord getAppConfigurationRecord(DataConfigurationDto configurationDto) {
        AppConfigurationRecord record = null;
        if (configurationDto instanceof ExternalItemDataConfigurationDto) {
            AppExternalItemConfigurationRecord eiRecord = new AppExternalItemConfigurationRecord();
            eiRecord.setExternalItemDto(((ExternalItemDataConfigurationDto) configurationDto).getExternalItemDto());
            record = eiRecord;
        } else {
            record = new AppConfigurationRecord();
        }
        record.setKey(configurationDto.getConfigurationKey());
        record.setValue(configurationDto.getConfigurationValue());
        record.setValue(configurationDto.getConfigurationValue());
        record.setDto(configurationDto);
        return record;
    }
    public static AppConfigurationRecord[] getAppConfigurationRecords(List<DataConfigurationDto> dtos) {
        AppConfigurationRecord[] records = new AppConfigurationRecord[dtos.size()];
        int i = 0;
        for (DataConfigurationDto dto : dtos) {
            records[i++] = getAppConfigurationRecord(dto);
        }
        return records;
    }
}
