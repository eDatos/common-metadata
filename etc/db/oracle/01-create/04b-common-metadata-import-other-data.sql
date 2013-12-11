-- ###########################################
-- # Insert
-- ###########################################

-- ------------
-- Security
-- ------------
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.security.cas_service_login_url','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.security.tolerance','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.security.cas_server_url_prefix','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.security.cas_service_logout_url','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- ------------
-- Common
-- ------------
-- Organisation must be "ISTAC", "SREA" or "DREM"
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.organisation','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- Must be "urn:sdmx:org.sdmx.infomodel.base.Agency=SDMX:AGENCIES(1.0).ISTAC", "urn:sdmx:org.sdmx.infomodel.base.Agency=SDMX:AGENCIES(1.0).SREA" or "urn:sdmx:org.sdmx.infomodel.base.Agency=SDMX:AGENCIES(1.0).DREM"
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.organisation.urn','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.navbar.url','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- Example: 'es, en'
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,0,'metamac.edition.languages','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- ------------
-- Default
-- ------------
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,0,'metamac.default.codelist.temporal_granularity.urn','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,0,'metamac.default.codelist.geographical_granularity.urn','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,0,'metamac.default.code.language.urn','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,0,'metamac.default.codelist.languages.urn','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';


-- ------------
-- Webs
-- ------------
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_operations.web.internal','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.common_metadata.web.internal','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.web.external','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.srm.web.internal','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';


-- ------------
-- Apis
-- ------------
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.access_control.rest.internal','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.srm.rest.internal','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.srm.rest.external','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.common_metadata.rest.external','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_operations.rest.internal','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_operations.rest.external','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.rest.external','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.sdmx.registry.rest.external','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.sdmx.srm.rest.external','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.sdmx.statistical_resources.rest.external','FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.metamac-portal.rest.external', 'FILL_ME');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

commit;

