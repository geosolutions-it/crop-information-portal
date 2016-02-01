#!/bin/bash
#
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
            return 1
    fi
}

# Make sure only root can run our script
if [[ $EUID -ne 0 ]]; then
   ko_print "Only root can remove wars"
   exit 1
fi

if [ -f "/opt/tomcat_gui/webapps/admin.war"]; then
    read -p "Remove admin.war (y/n)?" answer
    case ${answer:0:1} in
        y|Y )
            echo "Removing admin webapp"
            rm -rvf /opt/tomcat_gui/webapps/admin*            
        ;;
        * )
            ko_print "admin.war is not removed"
        ;;
    esac
    
else
    ok_print "admin.war already removed"
fi




