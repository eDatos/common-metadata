Cuando se cree la RELEASE, añadir estos pasos al manual de instalación:

0. Se ha creado una nueva aplicación web a instalar en Cibercentro, que publica la API externa de Common Metadata.

1. Parar Tomcat

2. Cambios en el data común:
	- Eliminado el directorio de configuración del ISTAC [DATA_ISTAC]
	- Añadida propiedad "metamac.navbar.url" en el fichero [DATA]/common/conf/static/resources.xml
	- Añadida propiedad "metamac.organisation" en el fichero [DATA]/common/conf/static/resources.xml

3. Cambios en el data de common-metadata:
	- Añadido fichero [DATA]/common-metadata/docs/Gestor_metadatos_comunes-Manual_usuario.pdf
	- Añadido fichero [DATA]/common-metadata/conf/static/resources.xml con la propiedad "metamac.common.metadata.user.guide.file.name"

99. Reiniciar Tomcat