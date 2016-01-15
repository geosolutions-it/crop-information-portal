# Update from Crop Information 1 to Crop Information Portal 2


## Update GeoStore


1. Build the new version of GeoStore
```
git clone git@github.com:geosolutions-it/geostore.git
cd geostore/server
mvn install -Ppostgres,extjs
```

2. Update GeoStore web application

* Stop the running GeoStore: `sudo service geostore stop`
* Check if is stopped (`ps aux | grep geostore`)
* Backup the old `geostore_datasource.ovr.properties` (located in /opt/tomcat_geostore/webapps/geostore/WEB-INF/classes)
* Replace the generated war file `geostore/server/web/app/target/geostore.war with the current in `/opt/tomcat_geostore/webapps`
* Start and Stop GeoStore Tomcat instance (`sudo service geostore stop; sudo service geostore start`)
* Edit  `/opt/tomcat_geostore/geostore/WEB-INF/classes/geostore-ovr.properties` and uncomment the line: `geostoreInitializer.allowPasswordRecoding=true`
* Replace `/opt/tomcat_geostore/geostore/WEB-INF/classes/geostore_datasource.ovr.properties` the with the old `geostore_datasource.ovr.properties`
* Login to the database and execute the [migration script](https://github.com/geosolutions-it/geostore/blob/master/doc/sql/migration/postgresql/postgresql-migration-from-v1.1.1-to-1.2.sql).
  *note*: if you login as geoserver you have to set search path fist, in order
  to execute the script on the geostore schema
* Start GeoStore again


## Crop Data


### Update MetaData

Installing the new manager, you will have new entities available 
(unit of measure and so on).
So you will have to update the database executing the sql script `02_units_of_measure.sql`

### Update Data

Data on DB use the following units of measure: 
* Production: 000 tons for all except cotton that is saved as 000 bales. You can import `uom_dump.json` that contains the correct values.
* Area: 000 ha
* Yield: kg/ha 

The new version need a uniform units of measure:
* Production: 000 tons 
* Area: 000 ha 
* Yield: tons/ha 

Crop Data cotton  values have to be updated executing the following sql scripts: 
* ``03_align_cotton_values.sql`` : this script align cotton production values to the latest values.
* (TODO/optional): update yield. (Yield is not used in queries. Changing this values should not have any result on the global system)

## Agromet

Agromet needs to be updated executing `01_agromet.sql`
This will add additional column and remove obsolete ones (TODO remove obsolete) in order to increase search capabilities 
New columns are:
* dek_in_year : the dekad in the year. 1 to 36
* absolute_dek : the absolute value of the dekad: `year * 36 + dekad_in_year`

Formula: `dek_in_year = MOD(absolute_dek - 1 , 36) + 1`

The ingestion flow in geobatch has been updated to add also this parameter.

## GeoServer
GeoServer needs the following additional components:
### Layers
* agromet_aggregated2
* CropDataMap2
* CropData
* CropData2
* CropDataDistrict (TODO align this with the new crop data (With unit of measures))
* measure_units_for_crops
* cropdata_ranges
* cropdescriptor
* agromet_descriptor
* fertilizer_data
* fertilizers_metadata
* marketprices_data
* marketprices_data_pakistan
* marketprices_metadata

### Styles
Crop Map Data have been updated to support unit of measure and dynamic styles.
* new: **difference_style**: crop difference. 
The district and province styles have to be replaced. All the styles starting with district or province have to be replaced : 
  * district_*
  * province_*



