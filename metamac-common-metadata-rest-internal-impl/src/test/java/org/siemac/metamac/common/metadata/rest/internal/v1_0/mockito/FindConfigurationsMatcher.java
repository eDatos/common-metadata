package org.siemac.metamac.common.metadata.rest.internal.v1_0.mockito;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.siemac.metamac.common.metadata.core.domain.Configuration;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataStatusEnum;
import org.siemac.metamac.rest.common.test.mockito.ConditionalCriteriasMatcher;

public class FindConfigurationsMatcher extends ConditionalCriteriasMatcher {

    private List<ConditionalCriteria> conditionalCriteriaQueries;
    private List<ConditionalCriteria> conditionalCriteriaOrderBy;

    public FindConfigurationsMatcher() {
    }

    public FindConfigurationsMatcher(List<ConditionalCriteria> conditionalCriteriaQueries, List<ConditionalCriteria> conditionalCriteriaOrderBy) {
        this.conditionalCriteriaQueries = conditionalCriteriaQueries;
        this.conditionalCriteriaOrderBy = conditionalCriteriaOrderBy;
    }

    public boolean matches(Object actual) {
        List<ConditionalCriteria> expected = new ArrayList<ConditionalCriteria>();
        
        // By status
        expected.add(ConditionalCriteriaBuilder.criteriaFor(Configuration.class).withProperty(ConfigurationProperties.status())
                .eq(CommonMetadataStatusEnum.ENABLED).buildSingle());
        
        // orderBy
        if (conditionalCriteriaOrderBy != null) {
            expected.addAll(conditionalCriteriaOrderBy);
        } else {
            // default order
            expected.add(ConditionalCriteriaBuilder.criteriaFor(Configuration.class).orderBy(ConfigurationProperties.id()).ascending().buildSingle());
        }
        // query
        if (conditionalCriteriaQueries != null) {
            expected.addAll(conditionalCriteriaQueries);
        }
        // distinc root
        expected.add(ConditionalCriteriaBuilder.criteriaFor(Configuration.class).distinctRoot().buildSingle());
        
        // Compare
        return super.matches(expected, actual);
    }
}