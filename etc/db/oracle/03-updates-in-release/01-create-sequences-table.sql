-- Create sequences table

create table TB_SEQUENCES (
	SEQUENCE_NAME VARCHAR2(255) NOT NULL,
	SEQUENCE_NEXT_VALUE NUMBER(19)
);

ALTER TABLE TB_SEQUENCES ADD CONSTRAINT PK_TB_SEQUENCES
	PRIMARY KEY (SEQUENCE_NAME)
;
