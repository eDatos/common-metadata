package org.siemac.metamac.common.metadata.core.serviceapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.metadata.core.constants.CommonMetadataConstants;
import org.siemac.metamac.common.metadata.core.enume.domain.CommonMetadataRoleEnum;
import org.siemac.metamac.common.test.MetamacBaseTests;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;

public abstract class CommonMetadataBaseTests extends MetamacBaseTests {

    // --------------------------------------------------------------------------------------------------------------
    // SERVICE CONTEXT
    // --------------------------------------------------------------------------------------------------------------
    
    @Override
    protected ServiceContext getServiceContextAdministrador() {
        ServiceContext serviceContext = super.getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.ADMINISTRADOR);
        return serviceContext;
    }
    
    protected ServiceContext getServiceContextJefeNormalizacion() {
        ServiceContext serviceContext = super.getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.JEFE_NORMALIZACION);
        return serviceContext;
    }
    
    protected ServiceContext getServiceContextTecnicoNormalizacion() {
        ServiceContext serviceContext = super.getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_NORMALIZACION);
        return serviceContext;
    }
    
    protected ServiceContext getServiceContextTecnicoApoyoNormalizacion() {
        ServiceContext serviceContext = super.getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_APOYO_NORMALIZACION);
        return serviceContext;
    }
    
    protected ServiceContext getServiceContextTecnicoPlanificacion() {
        ServiceContext serviceContext = super.getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_PLANIFICACION);
        return serviceContext;
    }
    
    protected ServiceContext getServiceContextTecnicoApoyoPlanificacion() {
        ServiceContext serviceContext = super.getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_APOYO_PLANIFICACION);
        return serviceContext;
    }
    
    protected ServiceContext getServiceContextTecnicoProduccion() {
        ServiceContext serviceContext = super.getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_PRODUCCION);
        return serviceContext;
    }
    
    protected ServiceContext getServiceContextTecnicoApoyoProduccion() {
        ServiceContext serviceContext = super.getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_APOYO_PRODUCCION);
        return serviceContext;
    }
    
    protected ServiceContext getServiceContextTecnicoDifusion() {
        ServiceContext serviceContext = super.getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_DIFUSION);
        return serviceContext;
    }
    
    protected ServiceContext getServiceContextTecnicoApoyoDifusion() {
        ServiceContext serviceContext = super.getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, CommonMetadataRoleEnum.TECNICO_APOYO_DIFUSION);
        return serviceContext;
    }
    
    
    
    private void putMetamacPrincipalInServiceContext(ServiceContext serviceContext, CommonMetadataRoleEnum role) {
        MetamacPrincipal metamacPrincipal = new MetamacPrincipal();
        metamacPrincipal.setUserId(serviceContext.getUserId());
        metamacPrincipal.getAccesses().add(new MetamacPrincipalAccess(role.getName(), CommonMetadataConstants.SECURITY_APPLICATION_ID, null));
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
    public List<String> getTablesToRemoveContent() {
        List<String> tables = new ArrayList<String>();
        tables.add("TB_CONFIGURATIONS");
        tables.add("TB_EXTERNAL_ITEMS");
        tables.add("TB_LOCALISED_STRINGS");
        tables.add("TB_INTERNATIONAL_STRINGS");
        return tables;
    }

    @Override
    public List<String> getSequencesToRestart() {
        List<String> sequences = new ArrayList<String>();
        sequences.add("SEQ_EXTERNAL_ITEMS");
        sequences.add("SEQ_L10NSTRS");
        sequences.add("SEQ_I18NSTRS");
        sequences.add("SEQ_CONFIGURATION");
        return sequences;
    }
    
    @Override
    protected Map<String, String> getTablePrimaryKeys() {
        return null;
    }
}