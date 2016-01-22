#!/bin/bash
# CIP PAKISTAN CHECK SCRIPT 2016/01/25
# 01 - Use to check if all the target files are in place

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
TODAY = "date +%Y%m%d"



# Check startup scripts
check_exists "/etc/init.d/geobatch"
check_exists "/etc/init.d/geoserver"
check_exists "/etc/init.d/geostore"
check_exists "/etc/init.d/gui"
check_exists "/etc/init.d/httpd"

# Check httpd autostart
CHK_OUTPUT="$(chkconfig --list | grep httpd )"
echo $CHK_OUTPUT
if echo $CHK_OUTPUT | grep -q '3:on'; then
  ok_print "httpd is configured to autostart"
else
  ko_print "httpd is NOT configured to autostart"
fi

# TODO chkconfig httpd on

# Check tomcat logrotate
check_exists "/etc/logrotate.d/tomcat"

# TODO: Update logrotate

# TODO: Check httpd conf



