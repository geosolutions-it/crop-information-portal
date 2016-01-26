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

echo "Have you run the sql scripts?"
read -p "$*"