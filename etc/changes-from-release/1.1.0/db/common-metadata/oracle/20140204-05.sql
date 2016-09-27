-- --------------------------------------------------------------------------------------------------
-- METAMAC-2109 - Problemas actualización desde versión anterior instalada a nueva versión enviada
-- --------------------------------------------------------------------------------------------------

ALTER TABLE TB_EXTERNAL_ITEMS ADD CODE_NESTED VARCHAR2(255 CHAR);

/* Nuevo campo */
ALTER TABLE TB_EXTERNAL_ITEMS ADD URN_PROVIDER VARCHAR2(4000 CHAR);

