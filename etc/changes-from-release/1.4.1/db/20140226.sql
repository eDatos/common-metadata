-- ----------------------------------------------------------------------------------------------------
-- METAMAC-2091 - [CORE] Añadir propiedades para la app web de recursos estadísticos y su API Interna
-- ----------------------------------------------------------------------------------------------------

update TB_DATA_CONFIGURATIONS set conf_key = 'metamac.notices.rest.internal' where conf_key = 'metamac.notifications.rest.internal';

commit;