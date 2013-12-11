package org.siemac.metamac.common.metadata.core.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity representing Configuration.
 * <p>
 * This class is responsible for the domain object related business logic for Configuration. Properties and associations are implemented in the generated base class
 * {@link org.siemac.metamac.common.metadata.core.domain.ConfigurationBase}.
 */
@Entity
@Table(name = "TB_CONFIGURATIONS", uniqueConstraints = {@UniqueConstraint(columnNames = {"CODE"})})
public class Configuration extends ConfigurationBase {

    private static final long serialVersionUID = 1L;

    public Configuration() {
    }
}
