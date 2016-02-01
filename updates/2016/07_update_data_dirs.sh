#!/bin/bash
# CIP PAKISTAN UPDATE SCRIPT 2016/01/25
# 

function ko_print(){
    echo -e "\e[31m$1\e[0m"
}

# Make sure only root can run our script
if [[ $EUID -ne 0 ]]; then
   ko_print "Only root can restore data_dirs"
   exit 1
fi

echo "Upgrade GeoServer data dir?"
read -p "$*"

cp -R 04_data_dirs/gs_data_dir/. /opt/gs_data_dir/

echo "Upgrade GeoBatch data dir?"
read -p "$*"

cp -R 04_data_dirs/GEOBATCH_CONFIG_DIR/. /opt/GEOBATCH_CONFIG_DIR/

echo "Create symbolic link to MapStore configuration directory?"
read -p "$*"

ln -s /opt/tomcat_gui/webapps/MapStore/WEB-INF/app/static/config/ /opt/admin_dir/mapstoreConfigurations
