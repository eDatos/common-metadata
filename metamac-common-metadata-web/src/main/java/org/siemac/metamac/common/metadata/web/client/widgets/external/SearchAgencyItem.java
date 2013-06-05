package org.siemac.metamac.common.metadata.web.client.widgets.external;

import static org.siemac.metamac.common.metadata.web.client.CommonMetadataWeb.getConstants;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmItemItem;

public class SearchAgencyItem extends SearchSrmItemItem {

    public SearchAgencyItem(String name, String title) {
        super(name, title, TypeExternalArtefactsEnum.AGENCY_SCHEME, TypeExternalArtefactsEnum.AGENCY, getConstants().searchAgencies(), getConstants().filterAgencyScheme(), getConstants()
                .selectedAgencyScheme(), getConstants().selectionAgency());
    }
}
