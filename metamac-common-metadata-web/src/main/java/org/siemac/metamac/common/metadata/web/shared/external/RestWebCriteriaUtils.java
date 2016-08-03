package org.siemac.metamac.common.metadata.web.shared.external;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.web.common.shared.criteria.ExternalResourceWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

public class RestWebCriteriaUtils {

    public static ExternalResourceWebCriteria buildItemSchemeWebCriteria(TypeExternalArtefactsEnum... types) {
        return buildItemSchemeWebCriteria(new SrmExternalResourceRestCriteria(), types);
    }

    public static SrmExternalResourceRestCriteria buildItemSchemeWebCriteria(SrmExternalResourceRestCriteria itemSchemeRestCriteria, TypeExternalArtefactsEnum[] types) {
        itemSchemeRestCriteria.setExternalArtifactType(types[0]); // only a type will be passed in this application
        return itemSchemeRestCriteria;
    }

    public static SrmItemRestCriteria buildItemWebCriteria(SrmItemRestCriteria itemRestCriteria, TypeExternalArtefactsEnum[] types) {
        itemRestCriteria.setExternalArtifactType(types[0]); // only a type will be passed in this application
        return itemRestCriteria;
    }
}
