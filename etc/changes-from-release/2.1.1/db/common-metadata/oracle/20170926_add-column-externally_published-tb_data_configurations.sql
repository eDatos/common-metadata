-- --------------------------------------------------------------------------------------------------
-- METAMAC-2669 - Crear API para obtener propiedades de configuración
-- --------------------------------------------------------------------------------------------------

ALTER TABLE TB_DATA_CONFIGURATIONS ADD EXTERNALLY_PUBLISHED NUMBER(1,0) DEFAULT 0 NOT NULL;

commit;