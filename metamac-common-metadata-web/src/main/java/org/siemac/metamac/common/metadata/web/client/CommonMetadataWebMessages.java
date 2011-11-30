package org.siemac.metamac.common.metadata.web.client;

import com.google.gwt.i18n.client.Messages;

public interface CommonMetadataWebMessages extends Messages {

	@DefaultMessage("{0} Statistical Family")
	String titleStatisticalFamily(String familyIdentifier);
	
	@DefaultMessage("{0} Statistical Operation")
	String titleStatisticalOperation(String operationIdentifier);
	
	@DefaultMessage("{0} Instance")
	String titleInstance(String instanceIdentifier);
	
	// --------------------------------------------------------------------------------------------
	// 								Error messages
	// --------------------------------------------------------------------------------------------

	// Lists
	
	@DefaultMessage("Error retrieving value lists")
	String listsErrorRetrievingData();
	
	// Family
	
	@DefaultMessage("Error retrieving families")
	String familiesErrorRetrievingData();
	@DefaultMessage("Error retrieving family")
	String familyErrorRetrievingData();
	@DefaultMessage("Error saving family")
	String familyErrorSave();
	@DefaultMessage("Error deleting Family")
	String familyErrorDelete();
	@DefaultMessage("Error adding families to Operation")
	String familyErrorAddToOperation();
	@DefaultMessage("Error internally publishing Family")
	String familyErrorInternallyPublishing();
	@DefaultMessage("Error externally publishing Family")
	String familyErrorExternallyPublishing();
	@DefaultMessage("Error unpublishing Family")
	String familyErrorUnpublishing();
	
	
	// Operation
	
	@DefaultMessage("Error retrieving operations")
	String operationsErrorRetrievingData();
	@DefaultMessage("Error retrieving operation")
	String operationErrorRetrievingData();
	@DefaultMessage("Error saving operation")
	String operationErrorSave();
	@DefaultMessage("Error deleting Operation")
	String operationErrorDelete();
	@DefaultMessage("Error adding operations to Family")
	String operationErrorAddToFamily();
	@DefaultMessage("Error internally publishing Operation")
	String operationErrorInternallyPublishing();
	@DefaultMessage("Error externally publishing Operation")
	String operationErrorExternallyPublishing();
	@DefaultMessage("Error unpublishing Operation")
	String operationErrorUnpublishing();
	
	// Instance

	@DefaultMessage("Error retrieving instance")
	String instanceErrorRetrievingData();
	@DefaultMessage("Error retrieving instances")
	String instancesErrorRetrievingData();
	@DefaultMessage("Error saving instance")
	String instanceErrorSave();
	@DefaultMessage("Error deleting Instance")
	String instanceErrorDelete();
	@DefaultMessage("Error deleting instances")
	String instancesErrorDelete();
	@DefaultMessage("Error internally publishing Instance")
	String instanceErrorInternallyPublishing();
	@DefaultMessage("Error externally publishing Instance")
	String instanceErrorExternallyPublishing();
	@DefaultMessage("Error unpublishing Instance")
	String instanceErrorUnpublishing();
	
	
	// --------------------------------------------------------------------------------------------
	// 								Info messages
	// --------------------------------------------------------------------------------------------

	// Family
	
	@DefaultMessage("Family saved successfully")
	String familySaved();
	@DefaultMessage("Family deleted successfully")
	String familyDeleted();
	
	// Operation
	
	@DefaultMessage("Operation saved successfully")
	String operationSaved();
	@DefaultMessage("Operation deleted successfully")
	String operationDeleted();
	
	// Instance
	
	@DefaultMessage("Instance saved successfully")
	String instanceSaved();
	@DefaultMessage("Instance deleted successfully")
	String instanceDeleted();
	@DefaultMessage("Instances deleted successfully")
	String instancesDeleted();
	
	@DefaultMessage("Operation added to Family successfully")
	String operationAddedToFamily();
	@DefaultMessage("Operations added to Family successfully")
	String operationsAddedToFamily();
	@DefaultMessage("Family added to Operation successfully")
	String familyAddedToOperation();
	@DefaultMessage("Families added to Operation successfully")
	String familiesAddedToOperation();
	
}