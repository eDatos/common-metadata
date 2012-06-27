Cuando se cree la RELEASE, añadir estos pasos al manual de instalación:

1. Parar Tomcat

2. Cambios en Base de datos
	- Ejecutar script de updates-in-release/01-common-metadata-delete-ml_url.sql que elimina las columnas innecesarias de URL por cambio de componente en la web para los tipos de dato ML+URL.

99. Reiniciar Tomcat