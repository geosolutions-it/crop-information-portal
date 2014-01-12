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

Then the following configuration are specific for every Operation available in the administrator interface::

    # zip2pgOp
    zip2pgOp.basedirString=/home/geosolutions/admin/test_geotiff/

    # GeoTiffOp
    GeoTiffOp.basedirString=/home/geosolutions/admin/test_geotiff/

    #flowstatus
    flowstatus.path=flowstatus

    ### NRL ###

    # fileBrowserOp
    fileBrowserOp.defaultBaseDir=/home/geosolutions/admin/test_geotiff/

    # NDVI
    NDVI.basedirString=/home/geosolutions/admin/test_geotiff/

    # CSV
    CSV.basedirString=/home/geosolutions/admin/test_csv/

    # NDVIStatistics
    NDVIStatistics.basedirString=/home/geosolutions/admin
    NDVIStatistics.gbinputdirString=/home/geosolutions/admin/gbinputdir/
    NDVIStatistics.defaultBaseDir=/home/geosolutions/admin/crop_masks/

    # fileBrowserOp
    fileBrowserOp.canManageFolders=true
    fileBrowserOp.canDownloadFiles=true
    fileBrowserOp.showRunInformation=true

    #fileBrowserOpCropMask
    fileBrowserOpCropMask.defaultBaseDir=/home/geosolutions/admin/crop_masks/
    fileBrowserOpCropMask.canManageFolders=true
    fileBrowserOpCropMask.canDownloadFiles=true
    fileBrowserOpCropMask.showRunInformation=true

    # fileBrowserOpCSV
    fileBrowserOpCSV.defaultBaseDir=/home/geosolutions/admin/test_csv/
    fileBrowserOpCSV.canManageFolders=true
    fileBrowserOpCSV.canDownloadFiles=true
    fileBrowserOpCSV.showRunInformation=true


TODO


   

