#!/bin/bash

HOME_PATH=metamac-common-metadata
TRANSFER_PATH=$HOME_PATH/tmp
DEPLOY_TARGET_PATH=/servers/edatos-external/tomcats/edatos-external01/webapps
DEPLOY_TARGET_PATH_INTERNAL=/servers/edatos-internal/tomcats/edatos-internal01/webapps
ENVIRONMENT_RELATIVE_PATH_FILE=WEB-INF/classes/metamac/environment.xml
LOGBACK_RELATIVE_PATH_FILE=WEB-INF/classes/logback.xml
RESTART=1

if [ "$1" == "--no-restart" ]; then
    RESTART=0
fi


scp -r etc/deploy deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH
scp metamac-common-metadata-web/target/common-metadata-internal-*.war deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH/common-metadata-internal.war
scp metamac-common-metadata-external-web/target/common-metadata-*.war deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH/common-metadata.war
ssh deploy@estadisticas.arte-consultores.com <<EOF

    chmod a+x $TRANSFER_PATH/deploy/*.sh;
    . $TRANSFER_PATH/deploy/utilities.sh

    if [ $RESTART -eq 1 ]; then
        sudo service edatos-internal01 stop
        sudo service edatos-external01 stop
        checkPROC "edatos-internal"
        checkPROC "edatos-external"
    fi

    ###
    # COMMON-METADATA-INTERNAL
    ###

    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal
    sudo mv $TRANSFER_PATH/common-metadata-internal.war $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal.war
    sudo unzip $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal.war -d $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal
    sudo rm -rf $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal.war

    # Restore Configuration
    sudo cp $HOME_PATH/environment_internal.xml $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal/$ENVIRONMENT_RELATIVE_PATH_FILE
    sudo cp $HOME_PATH/logback_internal.xml $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal/$LOGBACK_RELATIVE_PATH_FILE


    ###
    # COMMON-METADATA
    ###

    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH/common-metadata
    sudo mv $TRANSFER_PATH/common-metadata.war $DEPLOY_TARGET_PATH/common-metadata.war
    sudo unzip $DEPLOY_TARGET_PATH/common-metadata.war -d $DEPLOY_TARGET_PATH/common-metadata
    sudo rm -rf $DEPLOY_TARGET_PATH/common-metadata.war

    # Restore Configuration
    sudo cp $HOME_PATH/environment.xml $DEPLOY_TARGET_PATH/common-metadata/$ENVIRONMENT_RELATIVE_PATH_FILE
    sudo cp $HOME_PATH/logback.xml $DEPLOY_TARGET_PATH/common-metadata/$LOGBACK_RELATIVE_PATH_FILE

    if [ $RESTART -eq 1 ]; then
        sudo chown -R edatos-internal.edatos-internal /servers/edatos-internal
        sudo chown -R edatos-external.edatos-external /servers/edatos-external        
        sudo service edatos-internal01 start
        sudo service edatos-external01 start
    fi

    #checkURL "http://estadisticas.arte-consultores.com/common-metadata-internal" "metamac01"
    #checkURL "http://estadisticas.arte-consultores.com/cmetadata/latest" "metamac01"

    echo "Finished deploy"
EOF
