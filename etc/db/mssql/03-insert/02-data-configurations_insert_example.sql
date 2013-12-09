-- ###########################################
-- # Insert
-- ###########################################

-- Security
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.security.cas_service_login_url','http://localhost:8080/metamac-sso-web/login');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.security.tolerance','300');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.security.cas_server_url_prefix','http://localhost:8080/metamac-sso-web');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.security.cas_service_logout_url','http://localhost:8080/metamac-sso-web/logout');

-- Common
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.organisation','ISTAC');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.organisation.urn','urn:sdmx:org.sdmx.infomodel.base.Agency=SDMX:AGENCIES(1.0).ISTAC');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.navbar.url','http://RELLENAR/metamac-navbar/');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.edition.languages','es,en');

-- Default
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.default.codelist.temporal_granularity.urn','urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_FREQ(1.0)');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.default.codelist.geographical_granularity.urn','urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISTAC:CL_GEO_GRANULARITIES(01.000)');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.default.code.language.urn','urn:sdmx:org.sdmx.infomodel.codelist.Code=ISTAC:CL_LANGUAJES(01.000).ES');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.default.codelist.languages.urn','urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISTAC:CL_LANGUAJES(01.000)');

-- Webs
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_operations.web.internal','http://localhost:8080/metamac-statistical-operations-web');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.common_metadata.web.internal','http://localhost:8080/metamac-common-metadata-web');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.portal.web.external','http://localhost:8080/metamac-portal-web');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.srm.web.internal','http://localhost:8080/metamac-srm-web');

-- Apis
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.access_control.rest.internal','http://localhost:8080/metamac-access-control-web/apis/access-control-internal');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.srm.rest.internal','http://localhost:8080/metamac-srm-web/apis/structural-resources-internal');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.srm.rest.external','http://localhost:8080/metamac-srm-external-web/apis/structural-resources');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.common_metadata.rest.external','http://localhost:8080/metamac-common-metadata-external-web/apis/cmetadata');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.access_control.rest.internal','http://localhost:8080/metamac-access-control-web/apis/access-control-internal');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_operations.rest.internal','http://localhost:8080/metamac-statistical-operations-web/apis/operations-internal');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_operations.rest.external','http://localhost:8080/metamac-statistical-operations-web/apis/operations-external');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.rest.external','http://localhost:8080/metamac-statistical-resources-web/apis/statistical-resources');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.sdmx.registry.rest.external','http://localhost:8080/metamac-registry-web/apis/registry');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.sdmx.srm.rest.external','http://localhost:8080/metamac-registry-web/structure/registry');
insert into TB_DATA_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.sdmx.statistical_resources.rest.external','http://localhost:8080/metamac-registry-web/data/registry');

