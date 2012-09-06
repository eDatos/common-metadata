ALTER TABLE TB_LOCALISED_STRINGS MODIFY INTERNATIONAL_STRING_FK NUMBER(19) NOT NULL;

ALTER TABLE TB_LOCALISED_STRINGS DROP CONSTRAINT FK_TB_LOCALISED_STRINGS_TB_I97;

ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT FK_TB_LOCALISED_STRINGS_INTE13
	FOREIGN KEY (INTERNATIONAL_STRING_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) ON DELETE CASCADE
;
