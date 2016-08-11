package org.siemac.metamac.common.metadata.web.client.model;

import org.siemac.metamac.common.metadata.core.dto.ConfigurationDto;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.common.metadata.web.client.model.ds.ConfigurationDS;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.NavigableListGridRecord;

public class ConfigurationRecord extends NavigableListGridRecord {

    public ConfigurationRecord() {
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

    public void setExternallyPublished(boolean externallyPublished) {
        setAttribute(ConfigurationDS.EXTERNALLY_PUBLISHED, externallyPublished ? GlobalResources.RESOURCE.success().getURL() : new String());
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

    public void setContact(ExternalItemDto externalItemDto) {
        setExternalItem(ConfigurationDS.CONTACT, externalItemDto);
    }
}
