package org.siemac.metamac.common.metadata.core.serviceapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConstants;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataRoleEnum;
import org.siemac.metamac.common.test.dbunit.MetamacDBUnitBaseTests;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.springframework.beans.factory.annotation.Value;

public abstract class CommonMetadataBaseTests extends MetamacDBUnitBaseTests {

    protected static Long   NOT_EXISTS_ID                                               = Long.valueOf(-1);
    protected static String NOT_EXISTS_CODE                                             = "NOT_EXISTS";

    protected static Long   DATA_CONFIGURATION_01_ID                                    = Long.valueOf(1);
    protected static String DATA_CONFIGURATION_01_KEY                                   = "metamac.common_metadata.db.url";
    protected static int    NUMBER_DATA_CONFIGURATIONS_SYSTEM_PROPERTIES                = 38;
    protected static int    NUMBER_DATA_CONFIGURATIONS_DEFAULT_VALUES                   = 5;
    protected static int    NUMBER_DATA_CONFIGURATIONS_SYSTEM_PROPERTIES_WITH_CODE_SDMX = 4;
    protected static int    NUMBER_DATA_CONFIGURATIONS_DEFAULT_VALUES_WITH_CODE_SDMX    = 4;

    @Value("${metamac.common_metadata.db.provider}")
    private String          databaseProvider;

    // --------------------------------------------------------------------------------------------------------------
    // SERVICE CONTEXT
    // --------------------------------------------------------------------------------------------------------------

    protected ServiceContext getServiceContextAdministrador() {
        ServiceContext serviceContext = super.mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.ADMINISTRADOR);
        return serviceContext;
    }

    protected ServiceContext getServiceContextJefeNormalizacion() {
        ServiceContext serviceContext = super.mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.JEFE_NORMALIZACION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoNormalizacion() {
        ServiceContext serviceContext = super.mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_NORMALIZACION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoApoyoNormalizacion() {
        ServiceContext serviceContext = super.mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_APOYO_NORMALIZACION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoPlanificacion() {
        ServiceContext serviceContext = super.mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_PLANIFICACION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoApoyoPlanificacion() {
        ServiceContext serviceContext = super.mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_APOYO_PLANIFICACION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoProduccion() {
        ServiceContext serviceContext = super.mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_PRODUCCION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoApoyoProduccion() {
        ServiceContext serviceContext = super.mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_APOYO_PRODUCCION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoDifusion() {
        ServiceContext serviceContext = super.mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_DIFUSION);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoApoyoDifusion() {
        ServiceContext serviceContext = super.mockServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_APOYO_DIFUSION);
        return serviceContext;
    }

    private void putMetamacPrincipalInServiceContext(ServiceContext serviceContext, CommonMetadataRoleEnum role) {
        MetamacPrincipal metamacPrincipal = new MetamacPrincipal();
        metamacPrincipal.setUserId(serviceContext.getUserId());
        metamacPrincipal.getAccesses().add(new MetamacPrincipalAccess(role.getName(), CommonMetadataConstants.APPLICATION_ID, null));
        serviceContext.setProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE, metamacPrincipal);
    }

    // --------------------------------------------------------------------------------------------------------------
    // DBUNIT CONFIGURATION
    // --------------------------------------------------------------------------------------------------------------

    @Override
    public String getDataSetFile() {
        return "dbunit/CommonMetadataServiceTest.xml";
    }

    @Override
    public List<String> getTableNamesOrderedByFKDependency() {
        List<String> tables = new ArrayList<String>();
        tables.add("TB_SEQUENCES");
        tables.add("TB_INTERNATIONAL_STRINGS");
        tables.add("TB_LOCALISED_STRINGS");
        tables.add("TB_EXTERNAL_ITEMS");
        tables.add("TB_CONFIGURATIONS");
        return tables;
    }

    @Override
    protected Map<String, List<String>> getTablePrimaryKeys() {
        Map<String, List<String>> primaryKeys = new HashMap<String, List<String>>();
        primaryKeys.put("TB_SEQUENCES", Arrays.asList("SEQUENCE_NAME"));
        return primaryKeys;
    }

    @Override
    protected DataBaseProvider getDatabaseProvider() {
        return DataBaseProvider.valueOf(databaseProvider);
    }

}