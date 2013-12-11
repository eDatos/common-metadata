ALTER TABLE TB_CONFIGURATIONS ADD CONSTRAINT CONFIGURATIONS_CODE UNIQUE(CODE) DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE TB_DATA_CONFIGURATIONS ADD CONSTRAINT DATA_CONF_KEY UNIQUE(CONF_KEY) DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT LOCALE_INTERNATIONAL UNIQUE(LOCALE, INTERNATIONAL_STRING_FK) DEFERRABLE INITIALLY DEFERRED;