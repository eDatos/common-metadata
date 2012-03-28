package org.siemac.metamac.core.common.bt.domain;

import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;

/**
 * External codeList items
 */
@Embeddable
public class ExternalItemBt extends ExternalItemBtBase {

    private static final long serialVersionUID = 1L;

    protected ExternalItemBt() {
    }

    public ExternalItemBt(String uri, String codeId, TypeExternalArtefactsEnum type) {
        super(uri, codeId, type);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
