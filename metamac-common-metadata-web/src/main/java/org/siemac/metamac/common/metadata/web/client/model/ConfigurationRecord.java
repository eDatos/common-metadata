package org.siemac.metamac.common.metadata.web.client.model;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.web.client.model.ds.ConfigurationDS;
import org.siemac.metamac.web.common.client.resources.GlobalResources;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ConfigurationRecord extends ListGridRecord {

    public ConfigurationRecord() {
    }

    public ConfigurationRecord(String name, CommonMetadataStatusEnum status, String urn, ConfigurationDto configurationDto) {
        setCode(name);
        setStatus(status);
        setUrn(urn);
        setConfigurationDto(configurationDto);
    }

    public void setCode(String value) {
        setAttribute(ConfigurationDS.CODE, value);
    }

    public String getCode() {
        return getAttributeAsString(ConfigurationDS.CODE);
    }

    public void setStatus(CommonMetadataStatusEnum status) {
        setAttribute(ConfigurationDS.STATUS, CommonMetadataStatusEnum.ENABLED.equals(status) ? GlobalResources.RESOURCE.success().getURL() : new String());
    }

    public void setConfigurationDto(ConfigurationDto value) {
        setAttribute(ConfigurationDS.CONFIGURATION_DTO, value);
    }

    public ConfigurationDto getConfigurationDto() {
        return (ConfigurationDto) getAttributeAsObject(ConfigurationDS.CONFIGURATION_DTO);
    }

    public void setUrn(String value) {
        setAttribute(ConfigurationDS.URN, value);
    }

    public String getUrn() {
        return getAttribute(ConfigurationDS.URN);
    }
}
