-- -------------------------------------------------------------------------------------------------------------------
-- METAMAC-2198 - [CORE] Desde la aplicación interna del GPE los recursos deben previsualizarse en el portal interno
-- -------------------------------------------------------------------------------------------------------------------
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.web.internal','http://estadisticas.arte-consultores.com/opencms/opencms/istac/metamac/');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

commit;