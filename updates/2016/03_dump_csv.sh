#!/bin/bash
# CIP PAKISTAN UPDATE SCRIPT 2016/01/28
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

read -rsp $'Press enter to backup tables in csv format.\n'
eval "mkdir -p /home/201601_update/csv_exports/"

psql -U postgres -c "copy agromet to '/home/201601_update/csv_exports/agromet.csv' DELIMITER ',' CSV HEADER;" NRL
psql -U postgres -c "copy agrometdescriptor to '/home/201601_update/csv_exports/agrometdescriptor.csv' DELIMITER ',' CSV HEADER;" NRL
psql -U postgres -c "copy cropdata to '/home/201601_update/csv_exports/cropdata.csv' DELIMITER ',' CSV HEADER;" NRL
psql -U postgres -c "copy cropdescriptor to '/home/201601_update/csv_exports/cropdescriptor.csv' DELIMITER ',' CSV HEADER;" NRL
psql -U postgres -c "copy cropstatus to '/home/201601_update/csv_exports/cropstatus.csv' DELIMITER ',' CSV HEADER;" NRL
