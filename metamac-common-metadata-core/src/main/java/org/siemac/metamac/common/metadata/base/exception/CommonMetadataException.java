package org.siemac.metamac.common.metadata.base.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommonMetadataException extends Exception {
	
	private static final long serialVersionUID = -2204282499078445446L;
    
	private List<CommonMetadataExceptionItem> exceptionItems;

	
    public CommonMetadataException(List<CommonMetadataExceptionItem> exceptionItems) {
        super();
        this.exceptionItems = exceptionItems;
    }
    
    public CommonMetadataException(String errorCode, String message, Serializable... messageParameters) {
        super();
        getExceptionItems().add(new CommonMetadataExceptionItem(errorCode, message, messageParameters));
    }
    
    public CommonMetadataException(String errorCode, String message) {
        super();
        getExceptionItems().add(new CommonMetadataExceptionItem(errorCode, message));
    }
    
    
	public List<CommonMetadataExceptionItem> getExceptionItems() {
		if (exceptionItems == null) {
			exceptionItems = new ArrayList<CommonMetadataExceptionItem>();
		} 
		return exceptionItems;
	}
    
}
