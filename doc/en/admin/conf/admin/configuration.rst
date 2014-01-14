.. module:: cippak.admin.conf.admin
   :synopsis: Learn about how to configure Crop Information Portal Components.

.. _cippak.admin.conf.admin:

=============================================
Configuration of the Administration Interface 
=============================================


The Configuration Overrider
===========================

the configuration overrider allow to override the default properties of the spring beans defined in the application contexts.
the override file is available at ::

    WEB-INF/classes/opensdi-config-ovr.properties
    
lets explain the content of this file (the commented lines are obmitted).


GeoStore REST client configuration
==================================

The first lines are related to the GeoStore installation.

    # Example of use of this file, copy and modify as convenient to ovewrite your configuration
    
    # Geostore
    geoStoreClient.geostoreRestUrl=http://localhost/geostore/rest/
    geoStoreAuthenticationProvider.geoStoreRestURL=http://localhost/geostore/rest/
    geoStoreClient.username=admin
    geoStoreClient.password=admin
    
*Configuration Options*
* ``geoStoreClient.geostoreRestUrl``: the URL of GeoStore REST interface
* ``geoStoreAuthenticationProvider.geoStoreRestURL``: the URL of the authentication provider. In this case the users are stored directly in GeoStore, so the autheitication provider is the GeoStore URL.
* ``geoStoreClient.username``: the default username of GeoStore administrator. The credentials are replaced with the current user credentials.
* ``geoStoreClient.password``: the default password to GeoStore administrator. The credentials are replaced with the current user credentials.


Database Configuration Options
==============================

Then the configurations for the JDBC connectivity::

    # DB
    opensdiDataSource.driverClassName=org.postgresql.Driver
    opensdiDataSource.url=jdbc:postgresql:NRL//localhost
    opensdiDataSource.username=someuser
    opensdiDataSource.password=somepassword
    opensdiVendorAdapter.databasePlatform=org.hibernate.dialect.PostgreSQLDialect
    
*Configuration Options*
* ``opensdiDataSource.driverClassName``: the Driver class.
* ``opensdiDataSource.url``: the URL of the database.
* ``opensdiDataSource.username``: the username to access to the database.
* ``opensdiDataSource.password``: the password to access to the database.
* ``opensdiVendorAdapter.databasePlatform``: the SQL dialect for the database.

GeoBatch Rest Client configuration
==================================
The following rows are configuration options to interact with the GeoBatch REST interface :: 

    ##################################
    ############## VIEW ##############
    ##################################

    ### Common ###

    # GeoBatch client
    geobatchClient.geobatchRestUrl=http://localhost/geobatch/rest/
    geobatchClient.geobatchUsername=admin
    geobatchClient.geobatchPassword=admin

*Configuration Options*
* ``geobatchClient.geobatchRestUrl``: the URL of GeoBatch REST interface.
* ``opensdiDataSource.geobatchUsername``: the username to interact with GeoBatch via REST.
* ``opensdiDataSource.geobatchPassword``: the password to interact with GeoBatch via REST.

Then the following configuration are specific for every Operation available in the administrator interface. 

We have three principal types of operations on crop-information-portal:

File browser operations
-----------------------

This operations are responsible of the management of the files on each folder configured. The common options for this operations are:

* **basedirString**: Base directory of the file browser
* **canManageFolders**: Flag that inidicates if the file browser can create and delete subfolders.
* **canDownloadFiles**: Flag that indicates if the files in the file browser can be downloaded.
* **showRunInformation**: Flag to show two columns with the status of the last execution for a file in a folder (``Last execution`` and ``Status``)

The next operations are file browser operations:

* ``fileBrowserOp``::

    # fileBrowserOp
    fileBrowserOp.defaultBaseDir=/home/geosolutions/admin/test_geotiff/
    fileBrowserOp.canManageFolders=true
    fileBrowserOp.canDownloadFiles=true
    fileBrowserOp.showRunInformation=true

* ``fileBrowserOpCropMask``::

    #fileBrowserOpCropMask
    fileBrowserOpCropMask.defaultBaseDir=/home/geosolutions/admin/crop_masks/
    fileBrowserOpCropMask.canManageFolders=true
    fileBrowserOpCropMask.canDownloadFiles=true
    fileBrowserOpCropMask.showRunInformation=true

* ``fileBrowserOpCSV``::

    # fileBrowserOpCSV
    fileBrowserOpCSV.defaultBaseDir=/home/geosolutions/admin/test_csv/
    fileBrowserOpCSV.canManageFolders=true
    fileBrowserOpCSV.canDownloadFiles=true
    fileBrowserOpCSV.showRunInformation=true

File operations
---------------

This operations are the operations to be launched from each file browser for a specific file. On this type of operation you need:

* **basedirString**: Base directory of the file (the same of the file browser)

We have four operations of this type:

* ``zip2pgOp``::

    # zip2pgOp
    zip2pgOp.basedirString=/home/geosolutions/admin/test_geotiff/

* ``GeoTiffOp``::

    # GeoTiffOp
    GeoTiffOp.basedirString=/home/geosolutions/admin/test_geotiff/

* ``NDVI``::

    # NDVI
    NDVI.basedirString=/home/geosolutions/admin/test_geotiff/

* ``CSV``::

    # CSV
    CSV.basedirString=/home/geosolutions/admin/test_csv/

Also, we have one special operation that combines the file browser and file operation::

    # NDVIStatistics
    NDVIStatistics.basedirString=/home/geosolutions/admin
    NDVIStatistics.gbinputdirString=/home/geosolutions/admin/gbinputdir/
    NDVIStatistics.defaultBaseDir=/home/geosolutions/admin/crop_masks/

In this operation the properties are:

* **basedirString**: From older versions. Ignore it.
* **gbinputdirString**: Input folder for the Geobatch action. Is used to copy the xml file that defines the GeoBatch action and run it. Should be the same of the input for the NDVI statistics GeoBatch action.
* **basedirString**: Base folder for the masks to be applied on the proccess

Auxiliary actions
-----------------

There are one more action to show flow status or last execution tests. You can configure this actions as you want, but you may be carefull with this parameters because are used on the JSP custom pages.

* ``flowstatus``::

    #flowstatus
    flowstatus.path=flowstatus
