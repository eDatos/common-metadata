-- -------------------------------------------------------------------------------------------------------------------
--METAMAC-2052 - Añadir propiedad de URL de aplicación interna para los envíos de emails de avisos.
-- -------------------------------------------------------------------------------------------------------------------

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.notices.web.internal','http://FILL_ME_WITH_HOST_AND_PORT/FILL_ME_WITH_NOTICES_INTERNAL_APP');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

UPDATE TB_DATA_CONFIGURATIONS SET CONF_VALUE = '/data/metamac' WHERE CONF_KEY = 'metamac.data.path';

commit;