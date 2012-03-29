package org.siemac.metamac.core.common.dto.serviceapi;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;

/**
 * External codeList items
 */
public class ExternalItemBtDto extends ExternalItemBtDtoBase {
    private static final long serialVersionUID = 1L;

    public ExternalItemBtDto() {
    }

    public ExternalItemBtDto(String uriInt, String codeId,
        TypeExternalArtefactsEnum type) {
        super(uriInt, codeId, type);
    }
}
