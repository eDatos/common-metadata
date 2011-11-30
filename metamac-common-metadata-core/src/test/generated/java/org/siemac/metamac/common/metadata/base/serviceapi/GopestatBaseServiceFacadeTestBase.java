package org.siemac.metamac.gopestat.base.serviceapi;


/**
 * Definition of test methods to implement.
 */
public interface GopestatBaseServiceFacadeTestBase {
    public void testFindAllCommonMetadataConfigurations() throws Exception;

    public void testFindCommonMetadataById() throws Exception;

    public void testCreateFamily() throws Exception;

    public void testUpdateFamily() throws Exception;

    public void testDeleteFamily() throws Exception;

    public void testFindAllFamilies() throws Exception;

    public void testFindFamilyByCondition() throws Exception;

    public void testFindFamilyById() throws Exception;

    public void testPublishInternallyFamily() throws Exception;

    public void testPublishExternallyFamily() throws Exception;

    public void testFindOperationsForFamily() throws Exception;

    public void testAddOperationForFamily() throws Exception;

    public void testRemoveOperationForFamily() throws Exception;

    public void testCreateOperation() throws Exception;

    public void testUpdateOperation() throws Exception;

    public void testDeleteOperation() throws Exception;

    public void testFindAllOperations() throws Exception;

    public void testFindOperationByCondition() throws Exception;

    public void testFindOperationById() throws Exception;

    public void testPublishInternallyOperation() throws Exception;

    public void testPublishExternallyOperation() throws Exception;

    public void testFindFamiliesForOperation() throws Exception;

    public void testFindInstancesForOperation() throws Exception;

    public void testAddFamilyForOperation() throws Exception;

    public void testRemoveFamilyForOperation() throws Exception;

    public void testCreateInstance() throws Exception;

    public void testUpdateInstance() throws Exception;

    public void testDeleteInstance() throws Exception;

    public void testFindAllInstances() throws Exception;

    public void testFindInstanceByCondition() throws Exception;

    public void testFindInstanceById() throws Exception;

    public void testFindInstanceBaseById() throws Exception;

    public void testPublishInternallyInstance() throws Exception;

    public void testPublishExternallyInstance() throws Exception;

    public void testFindOperationForInstance() throws Exception;
}
