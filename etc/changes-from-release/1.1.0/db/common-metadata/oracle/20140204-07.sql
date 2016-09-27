-- --------------------------------------------------------------------------------------------------
-- METAMAC-2109 - Problemas actualización desde versión anterior instalada a nueva versión enviada
-- --------------------------------------------------------------------------------------------------
/* RENAME DE CAMPOS*/
UPDATE TB_EXTERNAL_ITEMS SET URN_PROVIDER = URN WHERE URN_PROVIDER is null AND TYPE LIKE 'structuralResources#%';

commit;