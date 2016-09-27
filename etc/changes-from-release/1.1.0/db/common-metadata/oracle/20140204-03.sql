-- --------------------------------------------------------------------------------------------------
-- METAMAC-2109 - Problemas actualización desde versión anterior instalada a nueva versión enviada
-- --------------------------------------------------------------------------------------------------

/* Longitud de los campos */

alter table TB_SEQUENCES modify (
	SEQUENCE_NAME VARCHAR2(255 CHAR)
);
