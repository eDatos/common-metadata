package org.siemac.metamac.common.metadata.web.client.widgets.external;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.constants.CommonWebConstants;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmItemLinkItemWithSchemeFilterItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmItemSchemeLinkItem;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

public abstract class SearchCodelistLinkItem extends SearchSrmItemSchemeLinkItem {

    public SearchCodelistLinkItem(String name, String title) {
        super(name, title);
    }

    public void setCodelists(ExternalItemsResult result) {
        List<ExternalItemDto> pageResults = result.getExternalItemDtos();
        super.setItemSchemes(pageResults, result.getFirstResult(), result.getTotalResults());
    }

}
