package org.siemac.metamac.common.metadata.web.shared;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

@GenDispatch(isSecure = false)
public class DeleteConfigurations {

    @In(1)
    List<Long> configurationIds;
}
