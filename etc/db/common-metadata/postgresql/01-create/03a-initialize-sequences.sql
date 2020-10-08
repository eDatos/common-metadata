-- Create sequences table

create table TB_SEQUENCES (
	SEQUENCE_NAME VARCHAR(255) NOT NULL,
	SEQUENCE_NEXT_VALUE BIGINT
);

ALTER TABLE TB_SEQUENCES ADD CONSTRAINT PK_TB_SEQUENCES
	PRIMARY KEY (SEQUENCE_NAME)
;

-- Initialize sequences table

Insert into TB_SEQUENCES(SEQUENCE_NAME, SEQUENCE_NEXT_VALUE) VALUES ('DATA_CONFIGURATIONS', 1);