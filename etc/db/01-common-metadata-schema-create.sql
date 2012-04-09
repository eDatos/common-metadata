-- ###########################################
-- # Create
-- ###########################################
-- Create pk sequence
    


-- Create normal entities
    
CREATE TABLE TB_INTERNATIONAL_STRINGS (
  ID NUMBER(19) NOT NULL,
  VERSION NUMBER(19) NOT NULL
);


CREATE TABLE TB_CONFIGURATIONS (
  ID NUMBER(19) NOT NULL,
  CODE VARCHAR2(100) NOT NULL,
  LEGAL_ACTS_URL VARCHAR2(100),
  DATA_SHARING_URL VARCHAR2(100),
  CONF_POLICY_URL VARCHAR2(100),
  CONF_DATA_TREATMENT_URL VARCHAR2(100),
  UUID VARCHAR2(36) NOT NULL,
  CREATED_DATE_TZ VARCHAR2(50)
  ,
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50),
  LAST_UPDATED_TZ VARCHAR2(50)
  ,
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50),
  VERSION NUMBER(19) NOT NULL,
  LEGAL_ACTS_FK NUMBER(19),
  DATA_SHARING_FK NUMBER(19),
  CONF_POLICY_FK NUMBER(19),
  CONF_DATA_TREATMENT_FK NUMBER(19),
  CONTACT_URI_INT VARCHAR2(255),
  CONTACT_CODE_ID VARCHAR2(255),
  CONTACT_TYPE VARCHAR2(40)
);


CREATE TABLE TB_LOCALISED_STRINGS (
  ID NUMBER(19) NOT NULL,
  LABEL VARCHAR2(4000) NOT NULL,
  LOCALE VARCHAR2(100) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  INTERNATIONAL_STRING_FK NUMBER(19)
);


CREATE TABLE TB_EXTERNAL_ITEMS (
  ID NUMBER(19) NOT NULL,
  UUID VARCHAR2(36) NOT NULL,
  EXT_URI_INT VARCHAR2(255) NOT NULL,
  EXT_CODE_ID VARCHAR2(255) NOT NULL,
  EXT_TYPE VARCHAR2(40) NOT NULL
);



-- Create many to many relations
    

-- Primary keys
    
ALTER TABLE TB_INTERNATIONAL_STRINGS ADD CONSTRAINT PK_TB_INTERNATIONAL_STRINGS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_CONFIGURATIONS ADD CONSTRAINT PK_TB_CONFIGURATIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT PK_TB_LOCALISED_STRINGS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_EXTERNAL_ITEMS ADD CONSTRAINT PK_TB_EXTERNAL_ITEMS
	PRIMARY KEY (ID)
;

    

-- Unique constraints
     
 

ALTER TABLE TB_CONFIGURATIONS
    ADD CONSTRAINT UQ_TB_CONFIGURATIONS UNIQUE (UUID)
;

 
 

ALTER TABLE TB_EXTERNAL_ITEMS
    ADD CONSTRAINT UQ_TB_EXTERNAL_ITEMS UNIQUE (UUID)
;



-- Foreign key constraints
    

  
ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT FK_TB_LOCALISED_STRINGS_TB_I97
	FOREIGN KEY (INTERNATIONAL_STRING_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;

ALTER TABLE TB_CONFIGURATIONS ADD CONSTRAINT FK_TB_CONFIGURATIONS_LEGAL_A76
	FOREIGN KEY (LEGAL_ACTS_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;
ALTER TABLE TB_CONFIGURATIONS ADD CONSTRAINT FK_TB_CONFIGURATIONS_DATA_SH72
	FOREIGN KEY (DATA_SHARING_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;
ALTER TABLE TB_CONFIGURATIONS ADD CONSTRAINT FK_TB_CONFIGURATIONS_CONF_PO92
	FOREIGN KEY (CONF_POLICY_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;
ALTER TABLE TB_CONFIGURATIONS ADD CONSTRAINT FK_TB_CONFIGURATIONS_CONF_DA43
	FOREIGN KEY (CONF_DATA_TREATMENT_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;

  
  
  
  
  

    

-- Index
  
  


    