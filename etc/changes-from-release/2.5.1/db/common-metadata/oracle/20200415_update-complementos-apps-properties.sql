-- --------------------------------------------------------------------------------------------------
-- EDATOS-3156 - Realizar modificaciones sobre el título y footer del catálogo de APIs
-- --------------------------------------------------------------------------------------------------

UPDATE TB_DATA_CONFIGURATIONS SET CONF_KEY = 'FILL_ME' WHERE CONF_KEY = 'metamac.api.style.footer.url';
-- Ejemplo PRO: UPDATE TB_DATA_CONFIGURATIONS SET CONF_KEY = 'https://www3.gobiernodecanarias.org/aplicaciones/appsistac/apis/footer.html' WHERE CONF_KEY = 'metamac.api.style.footer.url';
-- Ejemplo PRE: UPDATE TB_DATA_CONFIGURATIONS SET CONF_KEY = 'https://www3-pre.gobiernodecanarias.org/aplicaciones/appsistac/apis/footer.html' WHERE CONF_KEY = 'metamac.api.style.footer.url';

UPDATE TB_DATA_CONFIGURATIONS SET CONF_KEY = 'FILL_ME' WHERE CONF_KEY = 'metamac.app.style.header.url';
-- Ejemplo PRO: UPDATE TB_DATA_CONFIGURATIONS SET CONF_KEY = 'https://www3.gobiernodecanarias.org/aplicaciones/appsistac/apps/header/header.html' WHERE CONF_KEY = 'metamac.app.style.header.url';
-- Ejemplo PRE: UPDATE TB_DATA_CONFIGURATIONS SET CONF_KEY = 'https://www3-pre.gobiernodecanarias.org/aplicaciones/appsistac/apps/header/header.html' WHERE CONF_KEY = 'metamac.app.style.header.url';

-- Aseguramos que se haya eliminado la propiedad, que ya no debe estarse usando
DELETE FROM TB_DATA_CONFIGURATIONS WHERE CONF_KEY = 'sie.style.header.url';

commit;

