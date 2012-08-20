Cuando se cree la RELEASE, añadir estos pasos al manual de instalación:

1. Parar Tomcat

2. Cambios en Base de datos
	- Ejecutar script de updates-in-release

3. [DATA]
	- Si no existen, añadir al fichero [DATA]/common/static/endpoints.xml (crearlo si no existe), las siguientes entradas:
    <entry key="metamac.endpoints.common.metadata.rest.external">http://localhost:8080/metamac-common-metadata-web/apis/cmetadata</entry>
    <entry key="metamac.endpoints.srm.rest.external">http://localhost:8080/metamac-srm-external-web/apis/srm</entry>

99. Reiniciar Tomcat