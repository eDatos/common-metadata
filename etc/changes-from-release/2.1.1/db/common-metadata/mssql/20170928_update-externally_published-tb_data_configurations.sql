-- --------------------------------------------------------------------------------------------------
-- METAMAC-2669 - Crear API para obtener propiedades de configuración
-- --------------------------------------------------------------------------------------------------

UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.statistical_resources.dot_code_mapping';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.notices.help.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='indicators.widgets.typelist.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='indicators.querytools.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.srm.soap.external';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.default.style.header.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.default.style.css.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.default.style.footer.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.agriculture.style.header.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.agriculture.style.css.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.agriculture.style.footer.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.environment.style.header.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.environment.style.css.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.environment.style.footer.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.tourism.style.header.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.tourism.style.css.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.tourism.style.footer.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='idxmanager.search.form.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='search.help.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.web.external.visualizer';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.organisation';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.organisation.urn';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.navbar.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.edition.languages';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.default.codelist.temporal_granularity.urn';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.default.codelist.geographical_granularity.urn';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.default.code.language.urn';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.default.codelist.languages.urn';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.web.external';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.srm.rest.external';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.common_metadata.rest.external';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.statistical_operations.rest.external';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.statistical_resources.rest.external';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.sdmx.registry.rest.external';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.sdmx.srm.rest.external';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.sdmx.statistical_resources.rest.external';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.srm.dsd.primary_measure.default_concept.urn';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.srm.dsd.time_dimension_or_attribute.default_concept.urn';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.srm.dsd.measure_dimension_or_attribute.default_concept.urn';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.srm.variables.variable_world.urn';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.srm.variables.variable_element_world.urn';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.rest.external.base';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.rest.external.export';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.rest.external.permalinks';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.api.style.header.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.api.style.css.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='indicators.rest.external';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='indicators.opendata.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='indicators.web.external.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='indicators.help.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.common_metadata.help.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.access_control.help.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.statistical_operations.help.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.statistical_resources.help.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.srm.help.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.publicservice.style.header.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.api.style.footer.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.publicservice.style.css.url';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.portal.publicservice.style.footer.url';

COMMIT;