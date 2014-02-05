-- --------------------------------------------------------------------------------------------------
-- METAMAC-2109 - Problemas actualización desde versión anterior instalada a nueva versión enviada
-- --------------------------------------------------------------------------------------------------

/* LOCALISED STRING no modificable*/
ALTER TABLE TB_LOCALISED_STRINGS ADD (
    IS_UNMODIFIABLE NUMBER(1,0)
);