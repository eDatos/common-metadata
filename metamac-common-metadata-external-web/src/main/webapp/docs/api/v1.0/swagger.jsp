{
   "swagger":"2.0",
   "info":{
      "description":"Existen determinados metadatos que siempre toman el mismo valor para todos los recursos que son publicados por una organización en concreto. Ejemplos de este tipo de metadatos son: los datos de contacto de la organización, la descripción de la organización o la licencia que aplica la organización a los datos que publican. Para evitar definir en cada uno de los recursos el valor que toman estos metadatos (que como hemos dicho siempre es el mismo en la misma organización), se definen las configuraciones de metadatos comunes. Cada organización podrá tener su propia configuración de metadatos y esto nos permite que el mantenimiento de este tipo de documentación sea sencilla y poco propicia a errores.",
      "version":"1.4.7-SNAPSHOT",
      "title":"API de Metadatos Comunes v1.0"
   },
   "host":"<%=org.siemac.metamac.common.metadata.web.external.WebUtils.getApiBaseURL()%>",
   "schemes":[

   ],
   "tags" : [
    {
      "name" : "Configuraciones",
      "description" : ""
    }
  ],
   "definitions":{
      "ChildLinks":{
         "type":"object",
         "title":"ChildLinks",
         "allOf":[
            {
               "properties":{
                  "total":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Número total de recursos a los que se puede acceder a partir del actual.",
                     "type":"number"
                  },
                  "childLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Enlace a recurso accesible desde el actual.",
                     "$ref":"#/definitions/ResourceLink"
                  }
               }
            }
         ],
         "description":"Recuersos a los que se puede acceder desde el actual."
      },
      "InternationalString":{
         "type":"object",
         "title":"InternationalString",
         "allOf":[
            {
               "properties":{
                  "text":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Traducciones del texto",
                     "$ref":"#/definitions/LocalisedString"
                  }
               }
            }
         ],
         "description":"Texto en múltiples idiomas"
      },
      "ListBase":{
         "type":"object",
         "title":"ListBase",
         "allOf":[
            {
               "properties":{
                  "firstLink":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Dado que se trata de un resultado paginado, este enlace nos permite desplazarnos a la primera página. Si no se muestra es porque ya estamos en ella. Tener en cuenta que cuando sólo existe una página, no existirá ni primera ni última.",
                     "type":"string"
                  },
                  "kind":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Tipo del recurso",
                     "type":"string"
                  },
                  "lastLink":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Dado que se trata de un resultado paginado, este enlace nos permite desplazarnos a la última página. Si no se muestra es porque ya estamos en ella. Tener en cuenta que cuando sólo existe una página, no existirá ni primera ni última.",
                     "type":"string"
                  },
                  "limit":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Número máximo de resultados a obtener",
                     "type":"number"
                  },
                  "nextLink":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Dado que se trata de un resultado paginado, este enlace nos permite desplazarnos a la página siguiente a la que nos encontramos. Si no se muestra es porque no existe siguiente.",
                     "type":"string"
                  },
                  "offset":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Desplazamiento. Número a partir del cual se comienzan a obtener los resultados.",
                     "type":"number"
                  },
                  "previousLink":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Dado que se trata de un resultado paginado, este enlace nos permite desplazarnos a la página anterior a la que nos encontramos. Si no se muestra es porque no existe siguiente.",
                     "type":"string"
                  },
                  "selfLink":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Enlace al propio recurso. Dado un resultado nos permite saber cómo realizar la petición a la API para volver a obtenerlo.",
                     "type":"string"
                  },
                  "total":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Número total de resultados existentes.",
                     "type":"number"
                  }
               }
            }
         ],
         "description":"Listado paginado de recursos"
      },
      "LocalisedString":{
         "type":"object",
         "title":"LocalisedString",
         "allOf":[
            {
               "properties":{
                  "lang":{
                     "xml":{
                        "attribute":true,
                        "namespace":"http://www.w3.org/XML/1998/namespace"
                     },
                     "description":"Idioma del texto.",
                     "type":"string"
                  },
                  "(value)":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Traducción del texto en el idioma indicado.",
                     "type":"string"
                  }
               }
            }
         ],
         "description":"Texto traducido a un idioma determinado."
      },
      "Resource":{
         "type":"object",
         "title":"Resource",
         "allOf":[
            {
               "properties":{
                  "kind":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Tipo del recurso.",
                     "type":"string"
                  },
                  "id":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Código del recurso.",
                     "type":"string"
                  },
                  "name":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Nombre del recurso.",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "nestedId":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Código completo del recurso. Sólo se especifica en los casos en los que un recurso pertenece a una jerarquía. Este código concatena el código de todos los recursos que le preceden en la jerarquía.",
                     "type":"string"
                  },
                  "selfLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"Enlace al propio recurso.",
                     "$ref":"#/definitions/ResourceLink"
                  },
                  "urn":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common/v1.0/domain"
                     },
                     "description":"URN del recurso.",
                     "type":"string"
                  }
               }
            }
         ],
         "description":"Datos identificativos de un recurso."
      },
      "ResourceLink":{
         "type":"object",
         "title":"ResourceLink",
         "allOf":[
            {
               "properties":{
                  "href":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Enlace al recurso.",
                     "type":"string"
                  },
                  "kind":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Tipo del recurso.",
                     "type":"string"
                  }
               }
            }
         ],
         "description":"Enlace a un recurso."
      },
      "CommonMetadataStatus":{
         "type":"string",
         "title":"CommonMetadataStatus",
         "enum":[
            "ENABLED",
            "DISABLED"
         ],
         "description":"Posibles estados de una configuración de metadatos comunes."
      },
      "Configuration":{
         "type":"object",
         "title":"Configuration",
         "allOf":[
            {
               "properties":{
                  "kind":{
                     "xml":{
                        "attribute":true,
                        "namespace":""
                     },
                     "description":"Tipo del recurso.",
                     "type":"string"
                  },
                  "childLinks":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Recursos a los que se puede acceder partiendo del actual.",
                     "$ref":"#/definitions/ChildLinks"
                  },
                  "confDataTreatment":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Confidencialidad que se aplica en la organización para el tratamiento de los datos.",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "confPolicy":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Política de confidencialidad que sigue la organización.",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "contact":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Datos de contacto de la organización.",
                     "$ref":"#/definitions/ResourceInternal"
                  },
                  "dataSharing":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Política de compartición de datos que sigue la organización.",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "id":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Código de la configuración de metadatos comunes.",
                     "type":"string"
                  },
                  "legalActs":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Legislación por la que se rige la organización.",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "license":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Licencia que se aplica a todos los recursos publicados por la organización.",
                     "$ref":"#/definitions/InternationalString"
                  },
                  "managementAppLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Enlace a la configuración de metadatos comunes en la aplicación interna de gestión de metadatos comunes.",
                     "type":"string"
                  },
                  "parentLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Enlace al recurso padre en la API.",
                     "$ref":"#/definitions/ResourceLink"
                  },
                  "selfLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Enlace al propio recurso.",
                     "$ref":"#/definitions/ResourceLink"
                  },
                  "status":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Estado de la configuración de metadatos comunes. Las configuraciones pueden estar habilitadas o deshabilitadas. Deshabilitar una configuración indica que ya no se le puede aplicar a los recursos que se publican.",
                     "$ref":"#/definitions/CommonMetadataStatus"
                  },
                  "urn":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"URN del recurso.",
                     "type":"string"
                  }
               }
            }
         ],
         "description":"Configuración de metadatos comunes."
      },
      "Configurations":{
         "type":"object",
         "title":"Configurations",
         "allOf":[
            {
               "$ref":"#/definitions/ListBase"
            },
            {
               "properties":{
                  "configuration":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Configuración de metadatos comunes.",
                     "$ref":"#/definitions/ResourceInternal"
                  }
               }
            }
         ],
         "description":"Listado de configuraciones de metadatos comunes existentes."
      },
      "ResourceInternal":{
         "type":"object",
         "title":"ResourceInternal",
         "allOf":[
            {
               "$ref":"#/definitions/Resource"
            },
            {
               "properties":{
                  "managementAppLink":{
                     "xml":{
                        "namespace":"http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
                     },
                     "description":"Enlace al recurso en la aplicación interna encargada de su gestión.",
                     "type":"string"
                  }
               }
            }
         ],
         "description":"Definición del recurso en el ámbito interno de la organización."
      }
   },
   "paths":{
      "/v1.0/configurations":{
         "get":{
            "tags" : [ "Configuraciones" ],
            "description":"Esta petición aporta la lista de configuraciones de metadatos existentes. Cada configuración de metadatos pertenece a una organización en particular.",
            "operationId":"resource_CommonMetadataV1_0_findConfigurations_GET",
            "produces":[
               "application/xml"
            ],
            "parameters":[
               {
                  "name":"orderBy",
                  "in":"query",
                  "type":"string",
                  "description":"Permite ordenar la lista de resultados según un determinado metadato. El orden se especifica mediante un metadato y el sentido del orden (operador) que se le quiere aplicar.<br/>\r\n Los posibles operadores son ASC y DESC.<br/>\r\n El metadato que se puede usar es ID. <br/>Ejemplos:<br/>\r\n- ID ASC<br/>\r\n- ID DESC"
               },
               {
                  "name":"query",
                  "in":"query",
                  "type":"string",
                  "description":"Permite realizar una búsquedasobre los resultados. <br/>\r\n Los metadtos sobre los que se puede buscar son: ID, URN, CONTACT_URN y STATUS. <br/>\r\n Los operadores lógicos que se permite usar son: AND y OR.  <br/>\r\n Los operadores de comparación que se permite usar son: EQ, IEQ, LIKE, ILIKE, NE, LT, LE, GT, GE, IS_NULL, IS_NOT_NULL e IN.  <br/>\r\n Ejemplos: <br/>\r\n- ID LIKE \"ISTAC\" <br/>\r\n- (ID LIKE \"ISTAC\" AND CONTACT_URN LIKE \"urn:contact:1\") OR (CONTACT_URN EQ \"urn:contact:2\")"
               }
            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/Configurations"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente."
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido."
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado."
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situación temporal."
               }
            }
         }
      },
      "/v1.0/configurations/{id}":{
         "get":{
            "tags" : [ "Configuraciones" ],
            "description":"A través de esta petición se pueden obterner los valores de todos los metadatos que forman parte de una configuración de metadatos comunes.",
            "operationId":"resource_CommonMetadataV1_0_retrieveConfigurationById_GET",
            "produces":[
               "application/xml"
            ],
            "parameters":[
               {
                  "name":"id",
                  "in":"path",
                  "type":"string",
                  "description":"Identificador de la configuración de metadatos"
               }
            ],
            "responses":{
               "200":{
                  "schema":{
                     "description":"",
                     "$ref":"#/definitions/Configuration"
                  },
                  "headers":{

                  },
                  "description":"Éxito. Indica que la petición ha sido resuelta correctamente."
               },
               "404":{
                  "description":"No encontrado. El recurso solicitado no existe."
               },
               "406":{
                  "description":"No aceptable. El formato solicitado no es válido."
               },
               "500":{
                  "description":"Error interno del servidor. Se ha producido un error que impide que se obtenga el recurso solicitado."
               },
               "503":{
                  "description":"Servicio no disponible. Indica que actualmente el servidor no está disponible y por tanto, la solicitud no puede procesarse. El error puede deberse a una sobrecarga temporal o a labores de mantenimiento del servidor. Se trata de una situaci�n temporal."
               }
            }
         }
      }
   }
}