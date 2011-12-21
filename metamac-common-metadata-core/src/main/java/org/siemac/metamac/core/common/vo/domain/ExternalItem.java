package org.siemac.metamac.core.common.vo.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.siemac.metamac.core.common.bt.domain.ExternalItemBt;

/**
 * Dto for ExternalItem
 */
@Entity
@Table(name = "TBL_EXTERNAL_ITEM")
public class ExternalItem extends ExternalItemBase {
    private static final long serialVersionUID = 1L;

    protected ExternalItem() {
    }

    public ExternalItem(ExternalItemBt ext) {
        super(ext);
    }
    
    @Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals
			(this, obj,new String[] {"id", "uuid"});
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode
				(this,new String[] {"id", "uuid"});
	}
	
}
