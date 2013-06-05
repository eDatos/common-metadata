package org.siemac.metamac.common.metadata.web.server.rest.utils;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationSchemeCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationSchemeType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationType;
import org.siemac.metamac.web.common.shared.criteria.ExternalResourceWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemWebCriteria;

public class RestQueryUtils {

    //
    // AGENCY SCHEME
    //

    public static String buildAgencySchemeQuery(ExternalResourceWebCriteria externalResourceWebCriteria) {
        StringBuilder queryBuilder = new StringBuilder();
        String criteria = externalResourceWebCriteria.getCriteria();
        if (StringUtils.isNotBlank(criteria)) {
            queryBuilder.append("(");
            queryBuilder.append(OrganisationSchemeCriteriaPropertyRestriction.ID).append(" ").append(ComparisonOperator.ILIKE.name()).append(" \"").append(criteria).append("\"");
            queryBuilder.append(" ").append(LogicalOperator.OR.name()).append(" ");
            queryBuilder.append(OrganisationSchemeCriteriaPropertyRestriction.NAME).append(" ").append(ComparisonOperator.ILIKE.name()).append(" \"").append(criteria).append("\"");
            queryBuilder.append(" ").append(LogicalOperator.OR.name()).append(" ");
            queryBuilder.append(OrganisationSchemeCriteriaPropertyRestriction.URN).append(" ").append(ComparisonOperator.ILIKE.name()).append(" \"").append(criteria).append("\"");
            queryBuilder.append(")");
        }
        if (StringUtils.isNotBlank(queryBuilder.toString())) {
            queryBuilder.append(" ").append(LogicalOperator.AND.name()).append(" ");
        }
        queryBuilder.append("(");
        queryBuilder.append(OrganisationSchemeCriteriaPropertyRestriction.TYPE).append(" ").append(ComparisonOperator.EQ.name()).append(" \"").append(OrganisationSchemeType.AGENCY_SCHEME.name())
                .append("\"");
        queryBuilder.append(")");
        return queryBuilder.toString();
    }

    //
    // AGENCY
    //

    public static String buildAgencyQuery(SrmItemWebCriteria itemWebCriteria) {
        StringBuilder queryBuilder = new StringBuilder();
        String criteria = itemWebCriteria.getCriteria();
        if (StringUtils.isNotBlank(criteria)) {
            queryBuilder.append("(");
            queryBuilder.append(OrganisationCriteriaPropertyRestriction.ID).append(" ").append(ComparisonOperator.ILIKE.name()).append(" \"").append(criteria).append("\"");
            queryBuilder.append(" ").append(LogicalOperator.OR.name()).append(" ");
            queryBuilder.append(OrganisationCriteriaPropertyRestriction.NAME).append(" ").append(ComparisonOperator.ILIKE.name()).append(" \"").append(criteria).append("\"");
            queryBuilder.append(" ").append(LogicalOperator.OR.name()).append(" ");
            queryBuilder.append(OrganisationCriteriaPropertyRestriction.URN).append(" ").append(ComparisonOperator.ILIKE.name()).append(" \"").append(criteria).append("\"");
            queryBuilder.append(")");
        }
        if (StringUtils.isNotBlank(itemWebCriteria.getItemSchemUrn())) {
            if (StringUtils.isNotBlank(queryBuilder.toString())) {
                queryBuilder.append(" ").append(LogicalOperator.AND.name()).append(" ");
            }
            queryBuilder.append("(");
            queryBuilder.append(OrganisationCriteriaPropertyRestriction.ORGANISATION_SCHEME_URN).append(" ").append(ComparisonOperator.EQ.name()).append(" \"")
                    .append(itemWebCriteria.getItemSchemUrn()).append("\"");
            queryBuilder.append(")");
        }
        if (StringUtils.isNotBlank(queryBuilder.toString())) {
            queryBuilder.append(" ").append(LogicalOperator.AND.name()).append(" ");
        }
        queryBuilder.append("(");
        queryBuilder.append(OrganisationCriteriaPropertyRestriction.TYPE).append(" ").append(ComparisonOperator.EQ.name()).append(" \"").append(OrganisationType.AGENCY.name()).append("\"");
        queryBuilder.append(")");
        return queryBuilder.toString();
    }
}
