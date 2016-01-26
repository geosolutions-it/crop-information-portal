#!/bin/bash
# CIP PAKISTAN UPDATE SCRIPT 2016/01/25
# 

function ko_print(){
    echo -e "\e[31m$1\e[0m"
}

# Make sure only root can run our script
if [[ $EUID -ne 0 ]]; then
   ko_print "Only root can dump database"
   exit 1
fi

# TODAY
TODAY=$(date +%Y%m%d)

read -rsp $'Press enter to backup database...\n'
eval "mkdir -p 01_db_bkp/"

pg_dumpall -U postgres -c -f 01_db_bkp/${TODAY}_old.dump
