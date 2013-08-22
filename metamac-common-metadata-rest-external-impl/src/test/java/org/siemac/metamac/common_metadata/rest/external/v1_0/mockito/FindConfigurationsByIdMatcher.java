package org.siemac.metamac.common_metadata.rest.external.v1_0.mockito;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties;
import org.siemac.metamac.rest.common.test.mockito.ConditionalCriteriasMatcher;

public class FindConfigurationsByIdMatcher extends ConditionalCriteriasMatcher {

    private final String configurationId;

    public FindConfigurationsByIdMatcher(String configurationId) {
        this.configurationId = configurationId;
    }
    @Override
    public boolean matches(Object actual) {
        List<ConditionalCriteria> expected = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.Configuration.class).distinctRoot()
                .withProperty(ConfigurationProperties.code()).eq(configurationId).withProperty(ConfigurationProperties.externallyPublished()).eq(Boolean.TRUE).build();
        return super.matches(expected, actual);
    }
}