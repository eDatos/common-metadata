package org.siemac.metamac.common.metadata.core.aop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.siemac.metamac.core.common.aop.FlushingInterceptorBase;

public class FlushingInterceptor extends FlushingInterceptorBase {
    
    @Override
    @PersistenceContext(unitName = "CommonMetadataEntityManagerFactory")
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}