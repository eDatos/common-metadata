#!/bin/sh

TMP_PATH=/servers/metamac/tmp
DEPLOY_TARGET_PATH=/servers/metamac/tomcats/metamac01/webapps
ENVIRONMENT_RELATIVE_PATH_FILE=WEB-INF/classes/metamac/environment.xml
LOGBACK_RELATIVE_PATH_FILE=WEB-INF/classes/logback.xml

scp -r etc/deploy deploy@estadisticas.arte-consultores.com:
scp metamac-common-metadata-web/target/common-metadata-internal-*.war deploy@estadisticas.arte-consultores.com:common-metadata-internal.war
scp metamac-common-metadata-external-web/target/common-metadata-*.war deploy@estadisticas.arte-consultores.com:common-metadata.war
ssh deploy@estadisticas.arte-consultores.com <<EOF

    chmod a+x deploy/*.sh;
    . deploy/utilities.sh
    
    sudo service metamac01 stop
    checkPROC "metamac"
    
    ###
    # COMMON-METADATA-INTERNAL
    ###
    # Backup Configuration
    sudo mv $DEPLOY_TARGET_PATH/common-metadata-internal/$ENVIRONMENT_RELATIVE_PATH_FILE $TMP_PATH/environment.xml_common-metadata-internal_tmp
    sudo mv $DEPLOY_TARGET_PATH/common-metadata-internal/$LOGBACK_RELATIVE_PATH_FILE $TMP_PATH/logback.xml_common-metadata-internal_tmp
    
    # Update Process
    sudo mv common-metadata-internal.war $DEPLOY_TARGET_PATH/common-metadata-internal.war
    sudo unzip $DEPLOY_TARGET_PATH/common-metadata-internal.war -d $DEPLOY_TARGET_PATH
    sudo rm -rf $DEPLOY_TARGET_PATH/common-metadata-internal.war
    
    # Restore Configuration
    sudo mv $TMP_PATH/environment.xml_common-metadata-internal_tmp $DEPLOY_TARGET_PATH/common-metadata-internal/$ENVIRONMENT_RELATIVE_PATH_FILE
    sudo mv $TMP_PATH/logback.xml_common-metadata-internal_tmp $DEPLOY_TARGET_PATH/common-metadata-internal/$LOGBACK_RELATIVE_PATH_FILE
    
    
    ###
    # COMMON-METADATA
    ###
    # Backup Configuration
    sudo mv $DEPLOY_TARGET_PATH/common-metadata/$ENVIRONMENT_RELATIVE_PATH_FILE $TMP_PATH/environment.xml_common-metadata_tmp
    sudo mv $DEPLOY_TARGET_PATH/common-metadata/$LOGBACK_RELATIVE_PATH_FILE $TMP_PATH/logback.xml_common-metadata_tmp
    
    # Update Process
    sudo mv common-metadata.war $DEPLOY_TARGET_PATH/common-metadata.war
    sudo unzip $DEPLOY_TARGET_PATH/common-metadata.war -d $DEPLOY_TARGET_PATH
    sudo rm -rf $DEPLOY_TARGET_PATH/common-metadata.war
    
    # Restore Configuration
    sudo mv $TMP_PATH/environment.xml_common-metadata_tmp $DEPLOY_TARGET_PATH/common-metadata/$ENVIRONMENT_RELATIVE_PATH_FILE
    sudo mv $TMP_PATH/logback.xml_common-metadata_tmp $DEPLOY_TARGET_PATH/common-metadata/$LOGBACK_RELATIVE_PATH_FILE
    
    sudo chown -R metamac.metamac /servers/metamac
    sudo service metamac01 start
    checkURL "http://estadisticas.arte-consultores.com/common-metadata-internal" "metamac01"
    checkURL "http://estadisticas.arte-consultores.com/cmetadata/latest" "metamac01"

EOF
