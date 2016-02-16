# Update districts and provinces
The following scripts can be used to update districts names and provinces names.  
The `psql` command is required to run the scripts.

## The scripts
There are three scripts, two `sql` scripts and one `bash` script.  
The update must be done on the server, so you must upload the scripts on the server machine:

- Connect with WinSCP
- Upload the scripts (for example in a new directory `/root/utils`)
  - region-update
  - update-district.sql
  - update-province.sql
- Connect with Putty
- Move into the scripts directory ( `cd /root/utils`)
- Make the `region-update` script executable ( `chmod a+x region-update` )

### Bash script
Use the `region-update` script to update a district or a province:

For example, the command:

```
region-update -d lahore Lahore -l "-h localhost -U geoserver NRL "
```

updates the **district** `lahore` to `Lahore`.
It tells `psql` to connect to database `NRL` on `localhost` as user `geoserver` and it runs the script `update-district.sql`.

To update a **province** use the `-p` option instead of `-d`:

```
region-update -p SIND SINDH -l "-h localhost -U geoserver NRL "
```

## Help reference

```
USAGE
    region-update [-h] -d|-p OLD_NAME NEW_NAME -l LOGIN_OPT

OPTIONS
    -h|--help
        display this help

    -d|--district
        to update district name

    -p|--province
        to update province name

    -l|--login
        to specify login options for 'psql'

    OLD_NAME    name of the province/district to update
    NEW_NAME    the new name for the province/district

    LOGIN_OPT    a string of login option for psql (see 'man psql')
```

### SQL scripts
This section provides more details about the update scripts. 
The update is performed by SQL scripts

- `update-district.sql`: to update districts for all table of the database
- `update-province.sql`: to update provinces for all table of the database

Each one of these script can be run separately:

```
psql -v oldName="'OLD_NAME'" -v newName="'NEW_NAME'" [login options...] < update-district.sql
psql -v oldName="'OLD_NAME'" -v newName="'NEW_NAME'" [login options...] < update-province.sql
```
replacing `OLD_NAME` and `NEW_NAME` to the respectively old and new names of the
district (or province).

`login options` are the option to allow `psql` to connect database.
