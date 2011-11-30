package org.siemac.metamac.gopestat.base.serviceapi;


/**
 * Definition of test methods to implement.
 */
public interface GopestatBaseServiceTestBase {
    public void testFindCommonMetadataById() throws Exception;

    public void testFindAllCommonMetadataConfigurations() throws Exception;

    public void testFindFamilyById() throws Exception;

    public void testSaveFamily() throws Exception;

    public void testDeleteFamily() throws Exception;

    public void testFindAllFamilies() throws Exception;

    public void testFindFamilyByCondition() throws Exception;

    public void testFindOperationById() throws Exception;

    public void testSaveOperation() throws Exception;

    public void testDeleteOperation() throws Exception;

    public void testFindAllOperations() throws Exception;

    public void testFindOperationByCondition() throws Exception;

    public void testFindInstanceById() throws Exception;

    public void testSaveInstance() throws Exception;

    public void testDeleteInstance() throws Exception;

    public void testFindAllInstances() throws Exception;

    public void testFindInstanceByCondition() throws Exception;
}
