
delete from tb_data_configurations where conf_key = 'metamac.metamac-portal.rest.external';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.rest.external.base', 'http://FILL_ME_WITH_HOST_AND_PORT/FILL_ME_WITH_METAMAC_PORTAL_EXTERNAL_APP');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

update TB_DATA_CONFIGURATIONS set CONF_VALUE = '${metamac.portal.rest.external.base}/apis' where CONF_KEY = 'metamac.portal.rest.external';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.rest.external.permalinks', '${metamac.portal.rest.external}/permalinks');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

commit;