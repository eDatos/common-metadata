-- --------------------------------------------------------------------------------------------------
-- METAMAC-2669 - Crear API para obtener propiedades de configuración
-- --------------------------------------------------------------------------------------------------

ALTER TABLE TB_DATA_CONFIGURATIONS ADD EXTERNALLY_PUBLISHED BIT NOT NULL DEFAULT(0);

commit;