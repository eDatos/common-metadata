package org.siemac.metamac.common.metadata.web.server.rest.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.rest.api.utils.RestCriteriaUtils;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodelistCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptSchemeCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationSchemeCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

public class RestQueryUtils extends RestCriteriaUtils {

    //
    // AGENCY SCHEME
    //

    public static String buildAgencySchemeQuery(SrmExternalResourceRestCriteria srmItemSchemeRestCriteria) {
        return buildItemSchemeQuery(srmItemSchemeRestCriteria, OrganisationSchemeCriteriaPropertyRestriction.ID, OrganisationSchemeCriteriaPropertyRestriction.NAME,
                OrganisationSchemeCriteriaPropertyRestriction.URN, OrganisationSchemeCriteriaPropertyRestriction.LATEST);
    }

    //
    // CODELIST
    //

    public static String buildCodelistQuery(SrmExternalResourceRestCriteria srmItemSchemeRestCriteria) {
        return buildItemSchemeQuery(srmItemSchemeRestCriteria, CodelistCriteriaPropertyRestriction.ID, CodelistCriteriaPropertyRestriction.NAME, CodelistCriteriaPropertyRestriction.URN,
                CodelistCriteriaPropertyRestriction.LATEST);
    }

    //
    // CONCEPT SCHCME
    //

    public static String buildConceptSchemeQuery(SrmExternalResourceRestCriteria srmItemSchemeRestCriteria) {
        return buildItemSchemeQuery(srmItemSchemeRestCriteria, ConceptSchemeCriteriaPropertyRestriction.ID, ConceptSchemeCriteriaPropertyRestriction.NAME,
                ConceptSchemeCriteriaPropertyRestriction.URN, ConceptSchemeCriteriaPropertyRestriction.LATEST);
    }

    //
    // AGENCY
    //

    public static String buildAgencyQuery(SrmItemRestCriteria itemRestCriteria) {
        return buildItemQuery(itemRestCriteria, OrganisationCriteriaPropertyRestriction.ID, OrganisationCriteriaPropertyRestriction.NAME, OrganisationCriteriaPropertyRestriction.URN,
                OrganisationCriteriaPropertyRestriction.ORGANISATION_SCHEME_URN, OrganisationCriteriaPropertyRestriction.ORGANISATION_SCHEME_PROC_STATUS,
                OrganisationCriteriaPropertyRestriction.ORGANISATION_SCHEME_LATEST);
    }

    //
    // CONCEPT
    //

    public static String buildConceptQuery(SrmItemRestCriteria itemRestCriteria) {
        return buildItemQuery(itemRestCriteria, ConceptCriteriaPropertyRestriction.ID, ConceptCriteriaPropertyRestriction.NAME, ConceptCriteriaPropertyRestriction.URN,
                ConceptCriteriaPropertyRestriction.CONCEPT_SCHEME_URN, ConceptCriteriaPropertyRestriction.CONCEPT_SCHEME_PROC_STATUS, ConceptCriteriaPropertyRestriction.CONCEPT_SCHEME_LATEST);
    }

    //
    // CODE
    //

    public static String buildCodeQuery(SrmItemRestCriteria itemRestCriteria) {
        return buildItemQuery(itemRestCriteria, CodeCriteriaPropertyRestriction.ID, CodeCriteriaPropertyRestriction.NAME, CodeCriteriaPropertyRestriction.URN,
                CodeCriteriaPropertyRestriction.CODELIST_URN, CodeCriteriaPropertyRestriction.CODELIST_PROC_STATUS, CodeCriteriaPropertyRestriction.CODELIST_LATEST);
    }

    private static String buildItemQuery(SrmItemRestCriteria itemRestCriteria, Enum idField, Enum nameField, Enum urnField, Enum schemeUrnField, Enum schemeProcStatusField, Enum itemSchemeLatestField) {
        StringBuilder queryBuilder = new StringBuilder();

        String criteria = itemRestCriteria.getCriteria();
        if (StringUtils.isNotBlank(criteria)) {
            addSimpleRestCriteria(queryBuilder, criteria, idField, nameField, urnField);
        }
        // Filter by agency scheme
        if (StringUtils.isNotBlank(itemRestCriteria.getItemSchemeUrn())) {
            String schemeCondition = fieldComparison(schemeUrnField, ComparisonOperator.EQ, itemRestCriteria.getItemSchemeUrn());
            appendConditionToQuery(queryBuilder, schemeCondition);
        }

        // Filter by URN
        if (StringUtils.isNotBlank(itemRestCriteria.getUrn())) {
            String schemeCondition = fieldComparison(urnField, ComparisonOperator.EQ, itemRestCriteria.getUrn());
            appendConditionToQuery(queryBuilder, schemeCondition);
        }

        // Filter by URNS
        if (itemRestCriteria.getUrns() != null && !itemRestCriteria.getUrns().isEmpty()) {
            String urnsCondition = fieldComparison(urnField, ComparisonOperator.IN, transformListIntoQuotedCommaSeparatedString(itemRestCriteria.getUrns()));
            appendConditionToQuery(queryBuilder, urnsCondition);
        }

        // Only find externally published agencies
        if (BooleanUtils.isTrue(itemRestCriteria.isItemSchemeExternallyPublished())) {
            String schemeCondition = fieldComparison(schemeProcStatusField, ComparisonOperator.EQ, ProcStatus.EXTERNALLY_PUBLISHED);
            appendConditionToQuery(queryBuilder, schemeCondition);
        }

        // Only find externally published agencies
        if (BooleanUtils.isTrue(itemRestCriteria.isItemSchemeLastVersion())) {
            String lastVersionCondition = fieldComparison(itemSchemeLatestField, ComparisonOperator.EQ, itemRestCriteria.isItemSchemeLastVersion());
            appendConditionToQuery(queryBuilder, lastVersionCondition);
        }

        return queryBuilder.toString();
    }

    private static String buildItemSchemeQuery(SrmExternalResourceRestCriteria itemSchemeRestCriteria, Enum idField, Enum nameField, Enum urnField, Enum lastVersionField) {
        StringBuilder queryBuilder = new StringBuilder();

        String criteria = itemSchemeRestCriteria.getCriteria();
        if (StringUtils.isNotBlank(criteria)) {
            addSimpleRestCriteria(queryBuilder, criteria, idField, nameField, urnField);
        }

        // Filter by URN
        if (StringUtils.isNotBlank(itemSchemeRestCriteria.getUrn())) {
            String schemeCondition = fieldComparison(urnField, ComparisonOperator.EQ, itemSchemeRestCriteria.getUrn());
            appendConditionToQuery(queryBuilder, schemeCondition);
        }

        // Filter by URNS
        if (itemSchemeRestCriteria.getUrns() != null && !itemSchemeRestCriteria.getUrns().isEmpty()) {
            String urnsCondition = fieldComparison(urnField, ComparisonOperator.IN, transformListIntoQuotedCommaSeparatedString(itemSchemeRestCriteria.getUrns()));
            appendConditionToQuery(queryBuilder, urnsCondition);
        }

        // Filter by only last version
        if (BooleanUtils.isTrue(itemSchemeRestCriteria.isOnlyLastVersion())) {
            String lastVersionCondition = fieldComparison(lastVersionField, ComparisonOperator.EQ, itemSchemeRestCriteria.isOnlyLastVersion());
            appendConditionToQuery(queryBuilder, lastVersionCondition);
        }

        return queryBuilder.toString();
    }

    @SuppressWarnings("rawtypes")
    private static void addSimpleRestCriteria(StringBuilder queryBuilder, String criteria, Enum... fields) {
        if (StringUtils.isNotBlank(criteria)) {
            StringBuilder conditionBuilder = new StringBuilder();

            List<String> conditions = new ArrayList<String>();
            for (Enum field : fields) {
                conditions.add(fieldComparison(field, ComparisonOperator.ILIKE, criteria));
            }

            conditionBuilder.append("(");
            for (int i = 0; i < conditions.size(); i++) {
                if (i > 0) {
                    conditionBuilder.append(" ").append(LogicalOperator.OR).append(" ");
                }
                conditionBuilder.append(conditions.get(i));
            }
            conditionBuilder.append(")");
            appendConditionToQuery(queryBuilder, conditionBuilder.toString());
        }
    }
}
