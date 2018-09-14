-- --------------------------------------------------------------------------------------------------
-- METAMAC-2837 - Limpiar de la aplicación las cuestiones relativas a CAS y e-mail
-- --------------------------------------------------------------------------------------------------

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE,EXTERNALLY_PUBLISHED) values (FILL_WITH_SCHEMA_NAME.GetSequenceNextValue('DATA_CONFIGURATIONS'),1,1,'sie.style.header.url','FILL_ME',1);
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE,EXTERNALLY_PUBLISHED) values (FILL_WITH_SCHEMA_NAME.GetSequenceNextValue('DATA_CONFIGURATIONS'),1,1,'sie.style.footer.url','FILL_ME',1);
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

commit;
