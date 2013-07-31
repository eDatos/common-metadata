Cuando se cree la RELEASE, añadir estos pasos al manual de instalación:

1. Parar Tomcat

2. BBDD

	ALTER TABLE TB_EXTERNAL_ITEMS RENAME COLUMN URN_INTERNAL TO URN_PROVIDER;
	UPDATE TB_EXTERNAL_ITEMS SET URN = URN_PROVIDER WHERE URN is null AND TYPE LIKE 'structuralResources#%';
					  	
99. Reiniciar Tomcat