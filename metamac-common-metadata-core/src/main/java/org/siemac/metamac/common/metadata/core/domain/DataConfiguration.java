package org.siemac.metamac.common.metadata.core.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity representing DataConfiguration.
 * <p>
 * This class is responsible for the domain object related business logic for DataConfiguration. Properties and associations are implemented in the generated base class
 * {@link org.siemac.metamac.common.metadata.core.domain.DataConfigurationBase}.
 */
@Entity
@Table(name = "TB_DATA_CONFIGURATIONS", uniqueConstraints = {@UniqueConstraint(columnNames = {"CONF_KEY"})})
public class DataConfiguration extends DataConfigurationBase {

    private static final long serialVersionUID = 1L;

    public DataConfiguration() {
    }
}
