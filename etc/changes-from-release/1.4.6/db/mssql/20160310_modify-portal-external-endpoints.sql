-- ----------------------------------------------------------------------------------------------------------------------------------------------
-- METAMAC-2441 - Realizar refactor de las propiedades de endpoint del portal para que se puedan realizar redireccionamientos en Cibercentro
-- ----------------------------------------------------------------------------------------------------------------------------------------------

-- Refactor de propiedad "metamac.portal.rest.external" por "metamac.portal.rest.external.export"
UPDATE TB_DATA_CONFIGURATIONS SET CONF_KEY = 'metamac.portal.rest.external.export' WHERE CONF_KEY = 'metamac.portal.rest.external';

-- Modificar contenido de la propiedad "metamac.portal.rest.external.export"
-- Ejemplo de valor a añadir: http://www.gobiernodecanarias.org/istac/api/export o http://estadisticas.arte-consultores.com/export
UPDATE TB_DATA_CONFIGURATIONS SET CONF_VALUE = 'FILL_ME_WITH_EXPORT_ENDPOINT' WHERE CONF_KEY = 'metamac.portal.rest.external.export';


-- Modificar contenido de la propiedad "metamac.portal.rest.external.permalinks"
-- Ejemplo de valor a añadir: http://www.gobiernodecanarias.org/istac/api/permalinks o http://estadisticas.arte-consultores.com/permalinks
UPDATE TB_DATA_CONFIGURATIONS SET CONF_VALUE = 'FILL_ME_WITH_PERMALINKS_ENDPOINT' WHERE CONF_KEY = 'metamac.portal.rest.external.permalinks';

commit;