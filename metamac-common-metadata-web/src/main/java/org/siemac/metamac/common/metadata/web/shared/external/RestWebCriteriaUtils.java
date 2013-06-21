package org.siemac.metamac.common.metadata.web.shared.external;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.web.common.shared.criteria.ExternalResourceWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemSchemeRestCriteria;

public class RestWebCriteriaUtils {

    public static ExternalResourceWebCriteria buildItemSchemeWebCriteria(TypeExternalArtefactsEnum... types) {
        return buildItemSchemeWebCriteria(new SrmItemSchemeRestCriteria(), types);
    }

    public static SrmItemSchemeRestCriteria buildItemSchemeWebCriteria(SrmItemSchemeRestCriteria itemSchemeRestCriteria, TypeExternalArtefactsEnum[] types) {
        itemSchemeRestCriteria.setType(types[0]); // only a type will be passed in this application
        return itemSchemeRestCriteria;
    }

    public static SrmItemRestCriteria buildItemWebCriteria(SrmItemRestCriteria itemRestCriteria, TypeExternalArtefactsEnum[] types) {
        itemRestCriteria.setType(types[0]); // only a type will be passed in this application
        return itemRestCriteria;
    }
}
