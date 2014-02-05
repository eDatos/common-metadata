-- --------------------------------------------------------------------------------------------------
-- METAMAC-2109 - Problemas actualización desde versión anterior instalada a nueva versión enviada
-- --------------------------------------------------------------------------------------------------


ALTER TABLE TB_EXTERNAL_ITEMS MODIFY (
  CODE VARCHAR2(255 CHAR),
  URI VARCHAR2(4000 CHAR),
  URN VARCHAR2(4000 CHAR),
  MANAGEMENT_APP_URL VARCHAR2(4000 CHAR),
  TYPE VARCHAR2(255 CHAR)
);



ALTER TABLE TB_CONFIGURATIONS  MODIFY (
  	CODE VARCHAR2(255 CHAR),
  	URN VARCHAR2(4000 CHAR),
	UPDATE_DATE_TZ VARCHAR2(50 CHAR),
	UUID VARCHAR2(36 CHAR),
	CREATED_DATE_TZ VARCHAR2(50 CHAR),
  	CREATED_BY VARCHAR2(50 CHAR),
  	LAST_UPDATED_TZ VARCHAR2(50 CHAR),
  	LAST_UPDATED_BY VARCHAR2(50 CHAR),
  	STATUS VARCHAR2(255 CHAR)
);


alter TABLE TB_LOCALISED_STRINGS modify (
  LABEL VARCHAR2(4000 CHAR),
  LOCALE VARCHAR2(255 CHAR)
);