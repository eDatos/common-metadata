-- --------------------------------------------------------------------------------------------------
-- METAMAC-2109 - Problemas actualización desde versión anterior instalada a nueva versión enviada
-- --------------------------------------------------------------------------------------------------

/* Nuevos dimensiones columnas External items */

ALTER TABLE TB_EXTERNAL_ITEMS MODIFY  (
    MANAGEMENT_APP_URL VARCHAR2(4000),
    TYPE VARCHAR2(255)
);


/* URN opcional */
ALTER TABLE TB_EXTERNAL_ITEMS MODIFY URN VARCHAR2(4000) NULL;