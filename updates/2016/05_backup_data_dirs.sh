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
   ko_print "Only root can backup data_dirs"
   exit 1
fi

# Display directory sizes
df -h
echo "GeoServer Data Dir Size:"
du -h /opt/gs_data_dir/ -s -c
echo "GeoBatch Data Dir Size:"
du -h /opt/GEOBATCH_CONFIG_DIR/ -s -c

# Backup data dirs
read -rsp $'Press enter to backup existing data dirs...\n'
eval "mkdir -p bkp_dirs_${TODAY}/{geobatch,geoserver}"

cp -rv "/opt/gs_data_dir/data" "./bkp_dirs_${TODAY}/geoserver"
cp -rv "/opt/gs_data_dir/demo" "./bkp_dirs_${TODAY}/geoserver"
mkdir -p ./bkp_dirs_${TODAY}/geoserver/gwc && cp -v /opt/gs_data_dir/gwc/*.xml ./bkp_dirs_${TODAY}/geoserver/gwc
cp -rv "/opt/gs_data_dir/gwc-layers" "./bkp_dirs_${TODAY}/geoserver"
cp -rv "/opt/gs_data_dir/images" "./bkp_dirs_${TODAY}/geoserver"
cp -rv "/opt/gs_data_dir/logs" "./bkp_dirs_${TODAY}/geoserver"
cp -rv "/opt/gs_data_dir/printing" "./bkp_dirs_${TODAY}/geoserver"
cp -rv "/opt/gs_data_dir/security" "./bkp_dirs_${TODAY}/geoserver"
cp -rv "/opt/gs_data_dir/styles" "./bkp_dirs_${TODAY}/geoserver"
cp -rv "/opt/gs_data_dir/workspaces" "./bkp_dirs_${TODAY}/geoserver"
cp -rv "/opt/gs_data_dir/www" "./bkp_dirs_${TODAY}/geoserver"
cp -v /opt/gs_data_dir/*.xml ./bkp_dirs_${TODAY}/geoserver
cp -rv "/opt/GEOBATCH_CONFIG_DIR" "./bkp_dirs_${TODAY}/geobatch"

