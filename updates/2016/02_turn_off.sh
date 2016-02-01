#!/bin/bash
# CIP PAKISTAN UPDATE SCRIPT 2016/01/25
# 02 - Turn Off every application

function ko_print(){
    echo -e "\e[31m$1\e[0m"
}

# Make sure only root can run our script
if [[ $EUID -ne 0 ]]; then
   ko_print "Only root can stop services"
   exit 1
fi

service gui stop
service geoserver stop
service geobatch stop
service geostore stop
service geonetwork stop
