#!/bin/bash

HOME_PATH=metamac-common-metadata
TRANSFER_PATH=$HOME_PATH/tmp
DEPLOY_TARGET_PATH_EXTERNAL=/servers/edatos-external/tomcats/edatos-external01/webapps
DEPLOY_TARGET_PATH_INTERNAL=/servers/edatos-internal/tomcats/edatos-internal01/webapps
ENVIRONMENT_RELATIVE_PATH_FILE=WEB-INF/classes/metamac/environment.xml
LOGBACK_RELATIVE_PATH_FILE=WEB-INF/classes/logback.xml
RESTART=1

if [ "$1" == "--no-restart" ]; then
    RESTART=0
fi

scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" -r etc/deploy deploy@estadisticas.arte.internal:$TRANSFER_PATH
scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" metamac-common-metadata-web/target/common-metadata-internal-*.war deploy@estadisticas.arte.internal:$TRANSFER_PATH/common-metadata-internal.war
scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" metamac-common-metadata-external-web/target/common-metadata-*.war deploy@estadisticas.arte.internal:$TRANSFER_PATH/common-metadata.war
ssh -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" deploy@estadisticas.arte.internal <<EOF

    chmod a+x $TRANSFER_PATH/deploy/*.sh;
    . $TRANSFER_PATH/deploy/utilities.sh

    ###
    # COMMON-METADATA-INTERNAL
    ###
    
    if [ $RESTART -eq 1 ]; then
        sudo service edatos-internal01 stop
        checkPROC "edatos-internal"
    fi

    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal
    sudo mv $TRANSFER_PATH/common-metadata-internal.war $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal.war
    sudo unzip $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal.war -d $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal
    sudo rm -rf $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal.war

    # Restore Configuration
    sudo cp $HOME_PATH/environment_internal.xml $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal/$ENVIRONMENT_RELATIVE_PATH_FILE
    # Take care!, it's not necessary to restore the logback.xml file, it's externally configured in the applicationContext.xml file
    # sudo cp $HOME_PATH/logback_internal.xml $DEPLOY_TARGET_PATH_INTERNAL/common-metadata-internal/$LOGBACK_RELATIVE_PATH_FILE
    
    if [ $RESTART -eq 1 ]; then
        sudo chown -R edatos-internal.edatos-internal /servers/edatos-internal     
        sudo service edatos-internal01 start
    fi


    ###
    # COMMON-METADATA
    ###
    
    if [ $RESTART -eq 1 ]; then
        sudo service edatos-external01 stop
        checkPROC "edatos-external"
    fi

    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH_EXTERNAL/common-metadata
    sudo mv $TRANSFER_PATH/common-metadata.war $DEPLOY_TARGET_PATH_EXTERNAL/common-metadata.war
    sudo unzip $DEPLOY_TARGET_PATH_EXTERNAL/common-metadata.war -d $DEPLOY_TARGET_PATH_EXTERNAL/common-metadata
    sudo rm -rf $DEPLOY_TARGET_PATH_EXTERNAL/common-metadata.war

    # Restore Configuration
    sudo cp $HOME_PATH/environment_external.xml $DEPLOY_TARGET_PATH_EXTERNAL/common-metadata/$ENVIRONMENT_RELATIVE_PATH_FILE
    sudo cp $HOME_PATH/logback_external.xml $DEPLOY_TARGET_PATH_EXTERNAL/common-metadata/$LOGBACK_RELATIVE_PATH_FILE

    if [ $RESTART -eq 1 ]; then
        sudo chown -R edatos-external.edatos-external /servers/edatos-external        
        sudo service edatos-external01 start
    fi

    echo "Finished deploy"
EOF
