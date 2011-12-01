package org.siemac.metamac.common.metadata.base.exception;

import java.io.Serializable;

public class CommonMetadataExceptionItem {
	private String errorCode;
	private String message;
	private Serializable[] messageParameters = null;
	

	public CommonMetadataExceptionItem (String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public CommonMetadataExceptionItem (String errorCode, String message,  Serializable... messageParameters) {
		this.errorCode = errorCode;
		this.message = message;
		this.messageParameters = messageParameters;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Serializable[] getMessageParameters() {
		return messageParameters;
	}
	
	public void setMessageParameters(Serializable[] messageParameters) {
		this.messageParameters = messageParameters;
	}
}
