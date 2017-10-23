package org.siemac.metamac.common.metadata.web.shared.dto;

import org.siemac.metamac.common.metadata.core.dto.DataConfigurationDto;
import org.siemac.metamac.core.common.dto.ExternalItemDto;

public class ExternalItemDataConfigurationDto extends DataConfigurationDto {

    private static final long serialVersionUID = 3352756392911005339L;
    private ExternalItemDto   externalItem;

    public ExternalItemDataConfigurationDto() {
    }

    public ExternalItemDataConfigurationDto(DataConfigurationDto dto, ExternalItemDto externalItem) {
        setId(dto.getId());
        setOptimisticLockingVersion(dto.getOptimisticLockingVersion());
        setConfigurationKey(dto.getConfigurationKey());
        setExternallyPublished(dto.isExternallyPublished());
        setConfigurationValue(externalItem != null ? externalItem.getUrn() : null);
        this.externalItem = externalItem;
    }

    public ExternalItemDto getExternalItemDto() {
        return externalItem;
    }
}
