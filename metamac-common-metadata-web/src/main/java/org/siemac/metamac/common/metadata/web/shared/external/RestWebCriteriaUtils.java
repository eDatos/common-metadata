package org.siemac.metamac.common.metadata.web.shared.external;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.web.common.shared.criteria.ExternalResourceWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemWebCriteria;

public class RestWebCriteriaUtils {

    public static ExternalResourceWebCriteria buildItemSchemeWebCriteria(TypeExternalArtefactsEnum... types) {
        return buildItemSchemeWebCriteria(types, null);
    }

    public static ExternalResourceWebCriteria buildItemSchemeWebCriteria(TypeExternalArtefactsEnum[] types, String criteria) {
        ExternalResourceWebCriteria externalResourceWebCriteria = new ExternalResourceWebCriteria();
        externalResourceWebCriteria.setType(types[0]); // only a type will be passed in this application
        externalResourceWebCriteria.setCriteria(criteria);
        return externalResourceWebCriteria;
    }

    public static SrmItemWebCriteria buildItemWebCriteria(TypeExternalArtefactsEnum[] types, String criteria, String itemSchemeUrn) {
        SrmItemWebCriteria itemWebCriteria = new SrmItemWebCriteria();
        itemWebCriteria.setType(types[0]); // only a type will be passed in this application
        itemWebCriteria.setItemSchemUrn(itemSchemeUrn);
        itemWebCriteria.setCriteria(criteria);
        return itemWebCriteria;
    }
}
