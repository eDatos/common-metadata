package org.siemac.metamac.common.metadata.web.shared;

import java.util.List;

import org.siemac.metamac.core.common.dto.serviceapi.ExternalItemBtDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class FindAllContacts {

	@Out(1)
	List<ExternalItemBtDto> contacts;
	
}
