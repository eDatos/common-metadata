package org.siemac.metamac.common.metadata.service.utils;

import org.apache.commons.lang.RandomStringUtils;


public class CommonMetadataToolsServer {
	
	public static String generateUri() {
		StringBuilder aux = new StringBuilder("http://www.gobiernodecanarias.org/istac");
		aux.append("/").append(RandomStringUtils.randomAlphanumeric(5));
		aux.append("/").append(RandomStringUtils.randomAlphanumeric(5));
		aux.append("/").append(RandomStringUtils.randomAlphanumeric(5));
		aux.append("/").append(RandomStringUtils.randomAlphanumeric(5));
				
		return aux.toString();
	}
	
	public static String generateUrn(Object element) {		
		// Example: urn:istac:sdmx:0000-0000-9E59-0000-O-0000-0000-2
		StringBuilder aux = new StringBuilder("urn:istac:sdmx");
		aux.append(":").append(element.getClass().getSimpleName());
		aux.append(":").append(RandomStringUtils.randomAlphanumeric(5));
		aux.append(":").append(RandomStringUtils.randomAlphanumeric(5));
		aux.append(":").append(RandomStringUtils.randomAlphanumeric(5));
		aux.append(":").append(RandomStringUtils.randomAlphanumeric(5));
				
		return aux.toString();
	}
	
	
	/**
	 * Used to check whether to delete orphan entities manually.
	 * @param idOld
	 * @param idNew
	 * @return
	 */
	public static boolean removeOld(Long idOld, Long idNew) {
		// Algorithm
		//		if (idOld == null) {
		//			;
		//		}
		//		else {
		//			if (idNew == null) {
		//				return true;
		//			}
		//			else {
		//				if (idNew == idOld) {
		//					;
		//				}
		//				else {
		//					return true;
		//				}
		//			}
		//		}		
		if (idOld != null) {
			if (idNew == null) {
				return true;
			}
			else {
				if (idNew.compareTo(idOld) != 0) {
					return true;
				}
			}
		} 
		return false;
	}
	

}
