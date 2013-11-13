-- ###########################################
-- # Insert
-- ###########################################

insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.common_metadata.db.driver_name','oracle.jdbc.OracleDriver');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.common_metadata.db.username','FILL_ME_WITH_USERNAME');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.common_metadata.db.url','jdbc:oracle:thin:@FILL_ME_WITH_HOST:FILL_ME_WITH_PORT:XE');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.common_metadata.db.password','FILL_ME_WITH_PASSWORD');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.common_metadata.user_guide.file_name','Gestor_metadatos_comunes-Manual_usuario.pdf');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.common_metadata.db.dialect','org.siemac.metamac.hibernate.dialect.Oracle10gDialectMetamac');