package org.siemac.metamac.common.metadata.web.client.widgets.external;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.constants.CommonWebConstants;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmItemLinkItemWithSchemeFilterItem;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

public abstract class SearchCodeLinkItem extends SearchSrmItemLinkItemWithSchemeFilterItem {

    public SearchCodeLinkItem(String name, String title) {
        super(name, title, CommonWebConstants.FORM_LIST_MAX_RESULTS);
    }

    public void setCodelistsFilter(ExternalItemsResult result) {
        List<ExternalItemDto> pageResults = result.getExternalItemDtos();
        super.setFilterResources(pageResults, result.getFirstResult(), pageResults.size(), result.getTotalResults());
    }

    public void setCodesFilter(ExternalItemsResult result) {
        List<ExternalItemDto> pageResults = result.getExternalItemDtos();
        super.setResources(pageResults, result.getFirstResult(), pageResults.size(), result.getTotalResults());
    }
}
