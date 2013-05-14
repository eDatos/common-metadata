Cuando se cree la RELEASE, añadir estos pasos al manual de instalación:

1. Parar Tomcat

2. Base de datos:
	- Ejecutar el script 03-updates-in-release/01-update-columns-length.sql, tanto para Oracle como MSSql
	- Ejecutar el script 03-updates-in-release/02-update-localised-string.sql, tanto para Oracle como MSSql
	
3. Aplicación externa:
	- Se ha eliminado el DATA, de forma que la configuración de la aplicación se realice en el WAR.
	  a) Explicar el fichero a configurar (metamac-common-metadata-external-web-configuration.xml).
	  b) En este fichero aparecen además dos nuevas propiedades: 'metamac.web.common.metadata.internal' y 'metamac.web.srm.internal'
	  c) Explicar cómo configurar la ubicación de logs en el logback
	  	
4. DATA
	- [DATA]/common: Refactor fichero de configuración internal-endpoints.xml por api-endpoints.xml	  	
99. Reiniciar Tomcat