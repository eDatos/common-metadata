Cuando se cree la RELEASE, añadir estos pasos al manual de instalación:

1. Parar Tomcat

2. Cambios en Base de datos
	- Ejecutar script de updates-in-release

3. [DATA]
	- Añadir al fichero [DATA]/common/static/rest.xml (crearlo si no existe), las siguientes entradas:
		<entry key="metamac.endpoints.srm.rest.internal">http://localhost:8080/metamac-srm-web</entry>
	    <entry key="metamac.endpoints.common.metadata.rest.internal">http://localhost:8080/metamac-common-metadata-web</entry>

99. Reiniciar Tomcat