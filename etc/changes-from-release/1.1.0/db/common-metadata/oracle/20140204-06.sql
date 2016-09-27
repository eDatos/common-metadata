-- --------------------------------------------------------------------------------------------------
-- METAMAC-2109 - Problemas actualización desde versión anterior instalada a nueva versión enviada
-- --------------------------------------------------------------------------------------------------


/* Nuevo campo */
ALTER TABLE TB_CONFIGURATIONS ADD EXTERNALLY_PUBLISHED NUMBER(1,0) DEFAULT 1 NOT NULL;