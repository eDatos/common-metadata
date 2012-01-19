package org.siemac.metamac.common.metadata.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.gwtplatform.dispatch.shared.ServiceException;

public class ErrorUtils {

	/**
	 * Returns error message
	 * 
	 * @param caught
	 * @param message
	 */
	public static String getErrorMessage(Throwable caught, String message) {
		if (caught instanceof ServiceException) {
			return message;
		} else {
			return caught.getMessage();
		}
	}
	
	/**
	 * 
	 * @param messages
	 * @return
	 */
	public static List<String> getMessageList(String ...messages) {
		List<String> messageList = new ArrayList<String>();
		for (String message : messages) {
			messageList.add(message);
		}
		return messageList;
	}

}
