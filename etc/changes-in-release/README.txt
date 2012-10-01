Cuando se cree la RELEASE, añadir estos pasos al manual de instalación:

0. Se ha creado una nueva aplicación web a instalar en Cibercentro, que publica la API externa de Common Metadata.

1. Parar Tomcat

2. Cambios en Base de datos
	- Ejecutar script de updates-in-release
	- La configuración del datasource de las aplicaciones web se realiza en el DATA.

3. [DATA]
	- Añadir al fichero [DATA]/common/static/endpoints.xml la siguiente entrada:
    	<entry key="metamac.endpoints.common.metadata.rest.external">http://localhost:8080/metamac-common-metadata-external-web/apis/cmetadata</entry>
    
		Se requiere que las siguientes propiedades estén ya definidas en dicho fichero:
	     - API externa de SRM: metamac.endpoints.srm.rest.external
    
4. Se ha publicado la API Rest: 
	http://localhost:8080/metamac-common-metadata-external-web/apis/cmetadata/v1.0

5. Indicar que la ubicación de la documentación de la API:
	- Externa: http://localhost:8080/metamac-common-metadata-external-web/docs/api/index.html

99. Reiniciar Tomcat