package org.siemac.metamac.common.metadata.web.client;

import com.google.gwt.i18n.client.Constants;


public interface CommonMetadataWebConstants extends Constants {

	// --------------------------------------------------------------------------------------------
	// 										App Configuration
	// --------------------------------------------------------------------------------------------
	@DefaultStringValue("GOPESTAT")
	String appTitle();
	
	@DefaultStringValue("Gobierno de Canarias")
	String appFooterEntity();
	
	
	// --------------------------------------------------------------------------------------------
	// 										Actions
	// --------------------------------------------------------------------------------------------
	@DefaultStringValue("New")
	String actionNew();
	@DefaultStringValue("Add")
	String actionAdd();
	@DefaultStringValue("Edit")
	String actionEdit();
	@DefaultStringValue("Save")
	String actionSave();
	@DefaultStringValue("Delete")
	String actionDelete();
	@DefaultStringValue("Cancel")
	String actionCancel();
	
	@DefaultStringValue("Confirm Delete")
	String confirmDelete();
	
	@DefaultStringValue("New Family")
	String actionNewFamily();
	@DefaultStringValue("New Operation")
	String actionNewOperation();
	@DefaultStringValue("New Instance")
	String actionNewInstance();
	
	@DefaultStringValue("Add operations to Family")
	String actionAddOperationsToFamily();
	@DefaultStringValue("Add families to Operation")
	String actionAddFamiliesToOperation();

	@DefaultStringValue("Create Family")
	String actionCreateFamily();
	@DefaultStringValue("Create Operation")
	String actionCreateOperation();
	@DefaultStringValue("Create Instace")
	String actionCreateInstance();
	
	@DefaultStringValue("Are you sure you want to delete this Family?")
	String familyDeleteConfirmation();
	@DefaultStringValue("Are you sure you want to delete this Operation?")
	String operationDeleteConfirmation();
	@DefaultStringValue("Are you sure you want to delete this Instance?")
	String instanceDeleteConfirmation();
	
	@DefaultStringValue("Are you sure you want to remove these families from the Operation?")
	String removeFamiliesFromOperationConfirmation();
	@DefaultStringValue("Are you sure you want to remove there operations from the Family?")
	String removeOperationsFromFamilyConfirmation();

	// --------------------------------------------------------------------------------------------
	// 										Default
	// --------------------------------------------------------------------------------------------
	@DefaultStringValue(" ")
	String defautlText();
	
	@DefaultStringValue("Yes")
	String yes();
	
	@DefaultStringValue("No")
	String no();
	
	// --------------------------------------------------------------------------------------------
	// 										BreadCrumbs
	// --------------------------------------------------------------------------------------------
	
	@DefaultStringValue("Home")
	String breadcrumbHome();
	
	@DefaultStringValue("Statistical Families")
	String breadcrumbStatisticalFamilies();
	
	@DefaultStringValue("Statistical Family")
	String breadcrumbStatisticalFamily();
	
	@DefaultStringValue("Statistical Operation")
	String breadcrumbStatisticalOperation();
	
	@DefaultStringValue("Instance")
	String breadcrumbInstance();
	
	@DefaultStringValue("Statistical Operations")
	String breadcrumbStatisticalOperations();
	
	// --------------------------------------------------------------------------------------------
	// 										Families
	// --------------------------------------------------------------------------------------------

	@DefaultStringValue("Statistical Families")
	String statisticalFamilies();
	@DefaultStringValue("Statistical Family")
	String statisticalFamily();
	@DefaultStringValue("Family")
	String family();
	@DefaultStringValue("Families")
	String families();
	@DefaultStringValue("Identifier")
	String familyIdentifier();
	@DefaultStringValue("Title")
	String familyTitle();
	@DefaultStringValue("Description")
	String familyDescription();
	@DefaultStringValue("Status")
	String familyStatus();
	@DefaultStringValue("Language")
	String familyLanguage();
	@DefaultStringValue("Languages")
	String familyLanguages();
	
	
	// --------------------------------------------------------------------------------------------
	// 										Operations
	// --------------------------------------------------------------------------------------------

	@DefaultStringValue("Statistical Operations")
	String statisticalOperations();
	@DefaultStringValue("Statistical Operation")
	String statisticalOperation();
	@DefaultStringValue("Operation")
	String operation();
	@DefaultStringValue("Operations")
	String operations();
	@DefaultStringValue("Identifier")
	String operationIdentifier();
	@DefaultStringValue("Title")
	String operationTitle();
	@DefaultStringValue("Description")
	String operationDescription();
	@DefaultStringValue("Status")
	String operationStatus();
	@DefaultStringValue("Identifiers")
	String operationIdentifiers();
	@DefaultStringValue("Alternative title")
	String operationAlternativeTitle();
	@DefaultStringValue("Production Descriptors")
	String operationProductionDescriptors();
	@DefaultStringValue("Creator")
	String operationCreator();
	@DefaultStringValue("Contributor")
	String operationContributor();
	@DefaultStringValue("Information Suppliers")
	String operationInformationSuppliers();
	@DefaultStringValue("Activity Start Internal")
	String operationActivityStartInternal();
	@DefaultStringValue("Activity Start")
	String operationActivityStart();
	@DefaultStringValue("Activity End")
	String operationActivityEnd();
	@DefaultStringValue("Current Instance")
	String operationCurrentInstance();
	@DefaultStringValue("Currently Active")
	String operationCurrentlyActive();
	@DefaultStringValue("Families")
	String operationFamilies();
	@DefaultStringValue("Type")
	String operationType();
	@DefaultStringValue("Activity Class")
	String operationActivityClass();
	@DefaultStringValue("Activity Type")
	String operationActivityType();
	@DefaultStringValue("Collection Frequency")
	String operationCollectionFrequency();
	@DefaultStringValue("Data next update")
	String operationDataNextUpdate();
	@DefaultStringValue("Data last updated")
	String operationDataLastUpdated();
	@DefaultStringValue("Update frequency")
	String operationUpdateFrequency();
	@DefaultStringValue("Metadata last update")
	String operationMetadataLastUpdate();
	@DefaultStringValue("Source Data")
	String operationSourceData();
	@DefaultStringValue("Legal Base")
	String operationLegalBase();
	@DefaultStringValue("Contact")
	String operationContact();
	@DefaultStringValue("Officiality Type")
	String operationOfficialityType();
	@DefaultStringValue("Status")
	String operationProcStatus();
	@DefaultStringValue("Content Descriptors")
	String operationContentDescriptors();
	@DefaultStringValue("Detailed description")
	String operationDetailedDescription();
	@DefaultStringValue("Ref Period")
	String operationRefPeriod();
	@DefaultStringValue("Coverage Spatial")
	String operationCoverageSpatial();
	@DefaultStringValue("Coverage Temporal")
	String operationCoverageTemporal();
	@DefaultStringValue("Geographic Unit")
	String operationGeographicUnit();
	@DefaultStringValue("Base Period")
	String operationBasePeriod();
	@DefaultStringValue("Units")
	String operationUnits();
	@DefaultStringValue("Classification System")
	String operationClassificationSystem();
	@DefaultStringValue("Statistical Concepts and Definitions")
	String operationStatisticalConceptsDefinitions();
	@DefaultStringValue("Statistical Unit")
	String operationStatisticalUnit();
	@DefaultStringValue("Statistical Population")
	String operationStatisticalPopulation();
	@DefaultStringValue("Thematic Classification")
	String operationThematicClassification();
	@DefaultStringValue("Subject Area")
	String operationSubjectArea();
	@DefaultStringValue("Subject Code")
	String operationSubjectCode();
	@DefaultStringValue("Subject Areas Secundary")
	String operationSubjectAreasSecundary();
	@DefaultStringValue("Subject Codes Secundary")
	String operationSubjectCodesSecundary();
	@DefaultStringValue("Diffusion and Publication")
	String operationDiffusionAndPublication();
	@DefaultStringValue("Publisher")
	String operationPublisher();
	@DefaultStringValue("Release Calendar")
	String operationReleaseCalendar();
	@DefaultStringValue("Release Calendar Access")
	String operationReleaseCalendarAccess();
	@DefaultStringValue("News Release")
	String operationNewsRelease();
	@DefaultStringValue("Publications")
	String operationPublications();
	@DefaultStringValue("Microdata Access")
	String operationMicrodataAccess();
	@DefaultStringValue("Other Dissemination Formats")
	String operationOtherDisseminationFormats();
	@DefaultStringValue("Activity URL")
	String operationActivityURL();
	@DefaultStringValue("Methodology and Processing")
	String operationMethodologyAndProcessing();
	@DefaultStringValue("Methodological Descriptions")
	String operationMethodologicalDescriptions();
	@DefaultStringValue("Geographical Comparability")
	String operationGeographicalComparability();
	@DefaultStringValue("Over Time Comparability")
	String operationOverTimeComparability();
	@DefaultStringValue("Data Collection")
	String operationDataCollection();
	@DefaultStringValue("Data Validation")
	String operationDataValidation();
	@DefaultStringValue("Data Compilation")
	String operationDataCompilation();
	@DefaultStringValue("Adjustment")
	String operationAdjustment();
	@DefaultStringValue("Confidentiality Policy")
	String operationConfidentialityPolicy();
	@DefaultStringValue("Access Rights")
	String operationAccessRights();
	@DefaultStringValue("Language")
	String operationLanguage();
	@DefaultStringValue("Languages")
	String operationLanguages();
	@DefaultStringValue("Confidentiality and Access")
	String operationConfidentialityAndAccess();
	@DefaultStringValue("Confidentiality Data Treatment")
	String operationConfidentialityDataTreatment();

	
	// --------------------------------------------------------------------------------------------
	// 										Instances
	// --------------------------------------------------------------------------------------------
	
	@DefaultStringValue("Instances")
	String instances();
	@DefaultStringValue("Instance")
	String instance();
	@DefaultStringValue("Identifier")
	String instanceIdentifier();
	@DefaultStringValue("Title")
	String instanceTitle();
	@DefaultStringValue("Description")
	String instanceDescription();
	@DefaultStringValue("Status")
	String instanceStatus();
	@DefaultStringValue("Contributor")
	String instanceContributor();
	@DefaultStringValue("Information Suppliers")
	String instanceInformationSuppliers();
	@DefaultStringValue("Successor")
	String instanceSuccessor();
	@DefaultStringValue("Predecessor")
	String instancePredecessor();
	@DefaultStringValue("Collection Frequency")
	String instanceCollectionFrequency();
	@DefaultStringValue("Data next update")
	String instanceDataNextUpdate();
	@DefaultStringValue("Data Last Updated")
	String instanceDataLastUpdated();
	@DefaultStringValue("Update Frequency")
	String instanceUpdateFrequency();
	@DefaultStringValue("Legal Base")
	String instanceLegalBase();
	@DefaultStringValue("Instance Start Internal")
	String instanceStartInternal();
	@DefaultStringValue("Collection Date")
	String instanceCollectionDate();
	@DefaultStringValue("Status")
	String instanceProcStatus();
	@DefaultStringValue("Detailed Description")
	String instanceDetailedDescription();
	@DefaultStringValue("Coverage Spatial")
	String instanceCoverageSpatial();
	@DefaultStringValue("Coverage Temporal")
	String instanceCoverageTemporal();
	@DefaultStringValue("Geographic Unit")
	String instanceGeographicUnit();
	@DefaultStringValue("Base Period")
	String instanceBasePeriod();
	@DefaultStringValue("Units")
	String instanceUnits();
	@DefaultStringValue("Multiplier")
	String instanceMultiplier();
	@DefaultStringValue("Classification System")
	String instanceClassificationSystem();
	@DefaultStringValue("Statistical concepts definitions")
	String instanceStatisticaConceptsDefinitions();
	@DefaultStringValue("Statistical Unit")
	String instanceStatisticaUnit();
	@DefaultStringValue("Statistical Population")
	String instanceStatisticalPopulation();
	@DefaultStringValue("News Release")
	String instanceNewsRelease();
	@DefaultStringValue("Publications")
	String instancePublications();
	@DefaultStringValue("URL")
	String instanceURL();
	@DefaultStringValue("Micro Data Access")
	String instanceMicroDataAccess();
	@DefaultStringValue("Other Dissemination Formats")
	String instanceOtherDisseminationFormats();
	@DefaultStringValue("Methodological Descriptions")
	String instanceMethodologicalDescriptions();
	@DefaultStringValue("Comparability Geographical")
	String instanceComparabilityGeographical();
	@DefaultStringValue("Comparability Over Time")
	String instanceComparabilityOverTime();
	@DefaultStringValue("Data Collection")
	String instanceDataCollection();
	@DefaultStringValue("Data Validation")
	String instanceDataValidation();
	@DefaultStringValue("Data Compilation")
	String instanceDataCompilation();
	@DefaultStringValue("Adjustment")
	String instanceAdjustment();
	@DefaultStringValue("Identifiers")
	String instanceIdentifiers();
	@DefaultStringValue("Production Descriptors")
	String instanceProductionDescriptors();
	@DefaultStringValue("Content Descriptors")
	String instanceContentDescriptors();
	@DefaultStringValue("Diffusion and Publication")
	String instanceDiffusionAndPublication();
	@DefaultStringValue("Methodology and Processing")
	String instanceMethodologyAndProcessing();
	@DefaultStringValue("Language")
	String instanceLanguage();
	@DefaultStringValue("Languages")
	String instanceLanguages();
	
	
	// Common
	
	@DefaultStringValue("Publish internally")
	String publishInternally();
	@DefaultStringValue("Publish externally")
	String publishExternally();
	@DefaultStringValue("Unpublish externally")
	String unpublishExternally();
	
}
