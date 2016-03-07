{
  "swagger": "2.0",
  "info" : {
    "description" : "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis\n\t\tdis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec\n\t\tpede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede\n\t\tmollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat\n\t\tvitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum.\n\t\tAenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum\n\t\trhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio\n\t\tet ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed\n\t\tfringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,",
    "version" : "1.4.7-SNAPSHOT",
    "title" : "API de metadatos comunes"
  },
  "host" : "<%=org.siemac.metamac.common.metadata.web.external.WebUtils.getBaseURL(request)%>",
  "basePath" : "/apis/cmetadata",
  "schemes" : [],
  "tags" : [
    {
      "name" : "CommonMetadataV1_0",
      "description" : ""
    }
  ],
  "definitions" : {
    "xml_cdomain_ChildLinks" : {
      "type" : "object",
      "title" : "ChildLinks",
      "allOf" : [
        {
          "properties" : {
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "childLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ResourceLink"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_InternationalString" : {
      "type" : "object",
      "title" : "InternationalString",
      "allOf" : [
        {
          "properties" : {
            "text" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_LocalisedString"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_ListBase" : {
      "type" : "object",
      "title" : "ListBase",
      "allOf" : [
        {
          "properties" : {
            "firstLink" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "kind" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "lastLink" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "limit" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "nextLink" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "offset" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            },
            "previousLink" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "selfLink" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "total" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "number"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_LocalisedString" : {
      "type" : "object",
      "title" : "LocalisedString",
      "allOf" : [
        {
          "properties" : {
            "lang" : {
              "xml" : {
                "attribute" : true,
                "namespace" : "http://www.w3.org/XML/1998/namespace"
              },
"description" : "",
"type" : "string"
            },
            "(value)" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_Resource" : {
      "type" : "object",
      "title" : "Resource",
      "allOf" : [
        {
          "properties" : {
            "kind" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "id" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "name" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "nestedId" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "selfLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ResourceLink"
            },
            "urn" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cdomain_ResourceLink" : {
      "type" : "object",
      "title" : "ResourceLink",
      "allOf" : [
        {
          "properties" : {
            "href" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "kind" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : ""
    }
    ,
    "xml_cmetadata_CommonMetadataStatus" : {
      "type" : "string",
      "title" : "CommonMetadataStatus",
          "enum" : [
            "ENABLED",
            "DISABLED"
          ],
      "description" : "<p>Java class for CommonMetadataStatus.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n<p>\r\n<pre>\r\n &lt;simpleType name=\"CommonMetadataStatus\">\r\n   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}token\">\r\n     &lt;enumeration value=\"ENABLED\"/>\r\n     &lt;enumeration value=\"DISABLED\"/>\r\n   &lt;/restriction>\r\n &lt;/simpleType>\r\n <\/pre>"
    }
    ,
    "xml_cmetadata_Configuration" : {
      "type" : "object",
      "title" : "Configuration",
      "allOf" : [
        {
          "properties" : {
            "kind" : {
              "xml" : {
                "attribute" : true,
                "namespace" : ""
              },
"description" : "",
"type" : "string"
            },
            "childLinks" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ChildLinks"
            },
            "confDataTreatment" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "confPolicy" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "contact" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cmetadata_ResourceInternal"
            },
            "dataSharing" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "id" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "legalActs" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "license" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_InternationalString"
            },
            "managementAppLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"type" : "string"
            },
            "parentLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ResourceLink"
            },
            "selfLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cdomain_ResourceLink"
            },
            "status" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cmetadata_CommonMetadataStatus"
            },
            "urn" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for Configuration complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Configuration\">\r\n   &lt;complexContent>\r\n     &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}anyType\">\r\n       &lt;sequence>\r\n         &lt;element name=\"id\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"urn\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"selfLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"parentLink\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ResourceLink\"/>\r\n         &lt;element name=\"managementAppLink\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n         &lt;element name=\"dataSharing\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"legalActs\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"confPolicy\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"confDataTreatment\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\" minOccurs=\"0\"/>\r\n         &lt;element name=\"license\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}InternationalString\"/>\r\n         &lt;element name=\"contact\" type=\"{http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain}ResourceInternal\" minOccurs=\"0\"/>\r\n         &lt;element name=\"status\" type=\"{http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain}CommonMetadataStatus\"/>\r\n         &lt;element name=\"childLinks\" type=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ChildLinks\"/>\r\n       &lt;/sequence>\r\n       &lt;attribute name=\"kind\" use=\"required\" type=\"{http://www.w3.org/2001/XMLSchema}string\" />\r\n     &lt;/restriction>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_cmetadata_Configurations" : {
      "type" : "object",
      "title" : "Configurations",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_cdomain_ListBase"
        },
        {
          "properties" : {
            "configuration" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"$ref" : "#/definitions/xml_cmetadata_ResourceInternal"
            }
          }
        }
      ],
      "description" : "<p>Java class for Configurations complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"Configurations\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}ListBase\">\r\n       &lt;sequence>\r\n         &lt;element name=\"configuration\" type=\"{http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain}ResourceInternal\" maxOccurs=\"unbounded\" minOccurs=\"0\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
    ,
    "xml_cmetadata_ResourceInternal" : {
      "type" : "object",
      "title" : "ResourceInternal",
      "allOf" : [
        {
          "$ref" : "#/definitions/xml_cdomain_Resource"
        },
        {
          "properties" : {
            "managementAppLink" : {
              "xml" : {
                "namespace" : "http://www.siemac.org/metamac/rest/common-metadata/v1.0/domain"
              },
"description" : "",
"type" : "string"
            }
          }
        }
      ],
      "description" : "<p>Java class for ResourceInternal complex type.\r\n\r\n<p>The following schema fragment specifies the expected content contained within this class.\r\n\r\n<pre>\r\n &lt;complexType name=\"ResourceInternal\">\r\n   &lt;complexContent>\r\n     &lt;extension base=\"{http://www.siemac.org/metamac/rest/common/v1.0/domain}Resource\">\r\n       &lt;sequence>\r\n         &lt;element name=\"managementAppLink\" type=\"{http://www.w3.org/2001/XMLSchema}string\"/>\r\n       &lt;/sequence>\r\n     &lt;/extension>\r\n   &lt;/complexContent>\r\n &lt;/complexType>\r\n <\/pre>"
    }
  },
  "paths": {
    "/v1.0/configurations" : {
      "get" : {
        "tags" : [ "CommonMetadataV1_0" ],
        "description" : "Find configurations",
        "operationId" : "resource_CommonMetadataV1_0_findConfigurations_GET",
        "produces" : [ "application/xml" ],
        "parameters" : [
          {
            "name" : "orderBy",
            "in" : "query",
            "type" : "string",
            "description" : "Clause to order the results by metadata <br/>\r\n- Order operators: ASC, DESC<br/>\r\n- Metadata to order: ID<br/>\r\n- Example: ID ASC<br/>"
          },
          {
            "name" : "query",
            "in" : "query",
            "type" : "string",
            "description" : "Clause to filter results by metadata <br/>\r\n- Logical operators: AND, OR <br/>\r\n- Comparison operators: EQ, IEQ, LIKE, ILIKE, NE, LT, LE, GT, GE, IS_NULL, IS_NOT_NULL, IN <br/>\r\n- Metadata to filter: ID, URN, CONTACT_URN, STATUS <br/>\r\n- Example: (ID LIKE \"ISTAC\" AND CONTACT_URN EQ \"urn:contact:1\") OR (CONTACT_URN EQ \"urn:contact:2\")"
          }
        ],
        "responses" : {
          "200" : {
            "schema" : {
"description" : "",
"$ref" : "#/definitions/xml_cmetadata_Configurations"
            },
            "headers" : {
            },
            "description" : "Success"
          },
          "default" : {
            "description" : "Unexpected error."
          }
        }
      }
    }
    ,
    "/v1.0/configurations/{id}" : {
      "get" : {
        "tags" : [ "CommonMetadataV1_0" ],
        "description" : "Retrieve configuration by id",
        "operationId" : "resource_CommonMetadataV1_0_retrieveConfigurationById_GET",
        "produces" : [ "application/xml" ],
        "parameters" : [
          {
            "name" : "id",
            "in" : "path",
            "type" : "string",
            "description" : "Id"
          }
        ],
        "responses" : {
          "200" : {
            "schema" : {
"description" : "",
"$ref" : "#/definitions/xml_cmetadata_Configuration"
            },
            "headers" : {
            },
            "description" : "Success"
          },
          "default" : {
            "description" : "Unexpected error."
          }
        }
      }
    }
  }
}
