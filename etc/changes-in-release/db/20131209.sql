-- ----------------------------------------------------------------------------------
-- METAMAC-1976 - Posibilidad de setear en la WEB los datos por defecto de data
-- ----------------------------------------------------------------------------------

CREATE TABLE TB_DATA_CONFIGURATIONS (
  ID NUMBER(19) NOT NULL,
  CONF_KEY VARCHAR2(255 CHAR) NOT NULL,
  CONF_VALUE VARCHAR2(4000 CHAR),
  SYSTEM_PROPERTY NUMBER(1,0) NOT NULL,
  UPDATE_DATE_TZ VARCHAR2(50 CHAR),
  UPDATE_DATE TIMESTAMP ,
  CREATED_DATE_TZ VARCHAR2(50 CHAR),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50 CHAR),
  LAST_UPDATED_TZ VARCHAR2(50 CHAR),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50 CHAR),
  VERSION NUMBER(19) NOT NULL
);

ALTER TABLE TB_DATA_CONFIGURATIONS ADD CONSTRAINT PK_TB_DATA_CONFIGURATIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_DATA_CONFIGURATIONS ADD CONSTRAINT DATA_CONF_KEY UNIQUE(CONF_KEY) DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE TB_CONFIGURATIONS DROP COLUMN UUID; 

-- Insert DATA_CONFIGURATIONS sequence
Insert into TB_SEQUENCES(SEQUENCE_NAME, SEQUENCE_NEXT_VALUE) VALUES ('DATA_CONFIGURATIONS', 1);

-- Create GET_NEXT_SEQUENCE_VALUE function
CREATE OR REPLACE FUNCTION GET_NEXT_SEQUENCE_VALUE(sequence_name_in IN VARCHAR2)
  RETURN NUMBER
  IS sequence_next_value_out NUMBER(19,0);
  BEGIN
    SELECT SEQUENCE_NEXT_VALUE INTO sequence_next_value_out
    FROM TB_SEQUENCES 
    WHERE SEQUENCE_NAME = sequence_name_in;
    RETURN (sequence_next_value_out);
  END;
/

commit;

/*Examples in db/ directory*/
-- Have to execute 04a and 04b of the properly environment
