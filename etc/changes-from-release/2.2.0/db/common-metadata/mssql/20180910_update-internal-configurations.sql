-- --------------------------------------------------------------------------------------------------
-- METAMAC-2829 - Las propiedades delas apis internas deben ser públicas para consultar en la api del cmetadata
-- --------------------------------------------------------------------------------------------------

UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.statistical_resources.rest.internal';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.srm.rest.internal';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='indicators.rest.internal';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.statistical_operations.rest.internal';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.access_control.rest.internal';
UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.notices.rest.internal';

commit;