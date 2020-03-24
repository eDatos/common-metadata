-- --------------------------------------------------------------------------------------------------
-- EDATOS-3128 - Mejora de la gestión de los recursos de complementos-apps
-- --------------------------------------------------------------------------------------------------

UPDATE TB_DATA_CONFIGURATIONS SET CONF_KEY = 'metamac.app.style.header.url' WHERE CONF_KEY = 'sie.style.header.url';
UPDATE TB_DATA_CONFIGURATIONS SET CONF_KEY = 'metamac.app.style.footer.url' WHERE CONF_KEY = 'sie.style.footer.url';

commit;