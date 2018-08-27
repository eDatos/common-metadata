-- --------------------------------------------------------------------------------------------------
-- METAMAC-2810 - La propiedad del código de seguimiento de Google Analytics debe ser pública
-- --------------------------------------------------------------------------------------------------

UPDATE TB_DATA_CONFIGURATIONS SET EXTERNALLY_PUBLISHED=1 WHERE CONF_KEY='metamac.analytics.google.tracking_id';

commit;