Cuando se cree la RELEASE, añadir estos pasos al manual de instalación:

1. Parar Tomcat

2. BBDD

	-- *************************************************************************************
	-- Modificación del metadato URN_INTERNAL y URN por URN y URN_PROVIDER respectivamente
	-- *************************************************************************************

	ALTER TABLE TB_EXTERNAL_ITEMS RENAME COLUMN URN_INTERNAL TO URN_PROVIDER;
	
	UPDATE TB_EXTERNAL_ITEMS SET URN = URN_PROVIDER WHERE URN is null AND TYPE LIKE 'structuralResources#%';
	
	
	-- ****************************************
	-- Añadir metadato LICENSE (01/08/2013)
	-- ****************************************
	
	ALTER TABLE TB_CONFIGURATIONS ADD LICENSE_FK NUMBER(19);
	
	ALTER TABLE TB_CONFIGURATIONS ADD CONSTRAINT FK_TB_CONFIGURATIONS_LICENSE36
	FOREIGN KEY (LICENSE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID);
					  	
99. Reiniciar Tomcat