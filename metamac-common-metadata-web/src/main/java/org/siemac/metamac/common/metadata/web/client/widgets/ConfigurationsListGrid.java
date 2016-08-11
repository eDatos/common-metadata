package org.siemac.metamac.common.metadata.web.client.widgets;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.utils.ListGridUtils;
import org.siemac.metamac.web.common.client.widgets.BaseNavigableListGrid;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class ConfigurationsListGrid extends BaseNavigableListGrid<ExternalItemDto> {

    public ConfigurationsListGrid() {
        ListGridUtils.setCheckBoxSelectionType(this);
    }

    @Override
    protected List<PlaceRequest> buildLocation(ExternalItemDto relatedResourceDto) {
        throw new UnsupportedOperationException();
    }
}
