Cuando se cree la RELEASE, añadir estos pasos al manual de instalación:

1. Parar Tomcat

2. BBDD
	ALTER TABLE TB_EXTERNAL_ITEMS ADD CODE_NESTED VARCHAR2(255 CHAR);
				  	
99. Reiniciar Tomcat