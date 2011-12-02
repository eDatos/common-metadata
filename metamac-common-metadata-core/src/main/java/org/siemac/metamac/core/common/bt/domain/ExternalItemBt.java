package org.siemac.metamac.core.common.bt.domain;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;

import javax.persistence.Embeddable;

/**
 * External codeList items
 */
@Embeddable
public class ExternalItemBt extends ExternalItemBtBase {
    private static final long serialVersionUID = 1L;

    protected ExternalItemBt() {
    }

    public ExternalItemBt(String uriInt, String codeId,
        TypeExternalArtefactsEnum type) {
        super(uriInt, codeId, type);
    }
}
