package org.siemac.metamac.common.metadata.web.server.utils;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.common.metadata.core.criteria.DataConfigurationCriteriaOrderEnum;
import org.siemac.metamac.common.metadata.core.criteria.DataConfigurationCriteriaPropertyEnum;
import org.siemac.metamac.common.metadata.web.shared.criteria.DataConfigurationWebCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaConjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaRestriction;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder.OrderTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;

public class MetamacWebCriteriaUtils {

    public static MetamacCriteria build(DataConfigurationWebCriteria dataConfigurationWebCriteria) throws MetamacException {
        MetamacCriteria criteria = new MetamacCriteria();
        
        // Criteria
        MetamacCriteriaConjunctionRestriction restriction = new MetamacCriteriaConjunctionRestriction();
        restriction.getRestrictions().add(buildDataConfigurationCriteriaRestriction(dataConfigurationWebCriteria));
        criteria.setRestriction(restriction);

        // Order
        MetamacCriteriaOrder criteriaOrder = new MetamacCriteriaOrder();
        criteriaOrder.setPropertyName(DataConfigurationCriteriaOrderEnum.CREATED_DATE.name());
        criteriaOrder.setType(OrderTypeEnum.DESC);
        criteria.getOrdersBy().add(criteriaOrder);


        return criteria;
    }

    public static MetamacCriteriaRestriction buildDataConfigurationCriteriaRestriction(DataConfigurationWebCriteria criteria) throws MetamacException {
        MetamacCriteriaConjunctionRestriction conjunctionRestriction = new MetamacCriteriaConjunctionRestriction();
        if (criteria != null) {

            // General criteria
            MetamacCriteriaDisjunctionRestriction searchCriteriaDisjunction = new MetamacCriteriaDisjunctionRestriction();
            if (StringUtils.isNotBlank(criteria.getCriteria())) {
                searchCriteriaDisjunction.getRestrictions()
                        .add(new MetamacCriteriaPropertyRestriction(DataConfigurationCriteriaPropertyEnum.CONF_KEY.name(), criteria.getCriteria(), OperationType.ILIKE));
                searchCriteriaDisjunction.getRestrictions()
                        .add(new MetamacCriteriaPropertyRestriction(DataConfigurationCriteriaPropertyEnum.CONF_VALUE.name(), criteria.getCriteria(), OperationType.ILIKE));
            }
            conjunctionRestriction.getRestrictions().add(searchCriteriaDisjunction);

        }
        return conjunctionRestriction;
    }
}
