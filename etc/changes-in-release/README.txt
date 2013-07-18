Cuando se cree la RELEASE, añadir estos pasos al manual de instalación:

1. Parar Tomcat

2. BBDD
	ALTER TABLE TB_EXTERNAL_ITEMS ADD CODE_NESTED VARCHAR2(255 CHAR);
				 
3. DATA
	Refactor propiedades:
		- metamac.common.metadata.user.guide.file.name por metamac.common_metadata.user_guide.file_name
		- metamac.common.metadata.db.url por metamac.common_metadata.db.url
		- metamac.common.metadata.db.username por metamac.common_metadata.db.username
		- metamac.common.metadata.db.password por metamac.common_metadata.db.password
		- metamac.common.metadata.db.dialect por metamac.common_metadata.db.dialect
		- metamac.common.metadata.db.driverName por metamac.common_metadata.db.driver_name
					  	
99. Reiniciar Tomcat