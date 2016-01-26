#!/bin/bash
# CIP PAKISTAN UPDATE SCRIPT 2016/01/25
# 

function ok_print(){
    echo -e "\e[32m$1\e[0m"
}

function ko_print(){
    echo -e "\e[31m$1\e[0m"
}

function check_exists(){
    if [ -f $1 ]; then
            ok_print "File $1 exists."
    else
            ko_print "File $1 does not exist."
    fi
}

# TODAY
TODAY=$(date +%Y%m%d)

# Make sure only root can run our script
if [[ $EUID -ne 0 ]]; then
   ko_print "Only root can backup wars"
   exit 1
fi

# Display directory sizes
df -h
du -h /opt/tomcat_* -s -c

# Backup wars
read -rsp $'Press enter to backup existing WARS...\n'
eval "mkdir -p bkp_wars_${TODAY}/{gui,geobatch,geoserver,geostore}"

cp -rv "/opt/tomcat_gui/webapps" "./bkp_wars_${TODAY}/gui"
cp -rv "/opt/tomcat_geobatch/webapps" "./bkp_wars_${TODAY}/geobatch"
cp -rv "/opt/tomcat_geoserver/webapps" "./bkp_wars_${TODAY}/geoserver"
cp -rv "/opt/tomcat_geostore/webapps" "./bkp_wars_${TODAY}/geostore"
