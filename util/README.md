# Update districts and provinces
The fallowing scripts assume that the `psql` command has been installed.

## The scripts
There are three scripts, two `sql` scripts and one `bash` script.

### SQL scripts
- `update-district.sql`: to update districts for all table of the database
- `update-province.sql`: to update provinces for all table of the database

Each one of these script can be run like:

```
psql -v oldName="'OLD_NAME'" -v newName="'NEW_NAME'" [login options...] < update-district.sql

```
replacing `OLD_NAME` and `NEW_NAME` to the respectively old and new names of the
district (or province).

`login options` are the option to allow `psql` to connect database.

### Bash script
- `region-update`: to easily run the `sql` scripts

For example, the command:

```
region-update -d lahore Lahore -l "-h localhost NRL geoserver"
```

tells `psql` to connect to database `NRL` on `localhost` as user `geoserver` and it runs the script `update-district.sql` to update `lahore` to `Lahore`.

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
