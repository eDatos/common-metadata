package org.siemac.metamac.common_metadata.rest.external.v1_0.mockito;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties;
import org.siemac.metamac.rest.common.test.mockito.ConditionalCriteriasMatcher;

public class FindConfigurationsByIdMatcher extends ConditionalCriteriasMatcher {

    private String configurationId;

    public FindConfigurationsByIdMatcher(String configurationId) {
        this.configurationId = configurationId;
    }
    public boolean matches(Object actual) {
        List<ConditionalCriteria> expected = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.Configuration.class).withProperty(ConfigurationProperties.code())
                .eq(configurationId).distinctRoot().build();
        return super.matches(expected, actual);
    }
}