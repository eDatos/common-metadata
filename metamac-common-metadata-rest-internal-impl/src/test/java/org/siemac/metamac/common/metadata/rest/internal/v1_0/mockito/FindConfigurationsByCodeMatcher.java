package org.siemac.metamac.common.metadata.rest.internal.v1_0.mockito;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.siemac.metamac.common.metadata.core.domain.ConfigurationProperties;
import org.siemac.metamac.rest.common.test.mockito.ConditionalCriteriasMatcher;

public class FindConfigurationsByCodeMatcher extends ConditionalCriteriasMatcher {

    private String configurationCode;

    public FindConfigurationsByCodeMatcher(String configurationCode) {
        this.configurationCode = configurationCode;
    }
    public boolean matches(Object actual) {
        List<ConditionalCriteria> expected = ConditionalCriteriaBuilder.criteriaFor(org.siemac.metamac.common.metadata.core.domain.Configuration.class)
                .withProperty(ConfigurationProperties.code()).eq(configurationCode).distinctRoot().build();
        return super.matches(expected, actual);
    }
}