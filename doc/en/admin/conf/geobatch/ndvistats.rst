.. module:: cippak.admin.conf.mapstore.ndvistats
   :synopsis: Learn about how to configure Crop Information Portal Components.

.. _cippak.admin.conf.mapstore.ndvistats:

NDVI stats flow
===============

| The **NDVI stats flow** is responsible for compute statistics over a raster using a vector layer to determine a Region Of Interest in which the computation will be performed.
| The output format of the statistics is CSV.
| The Statistics are internally computed using the ZonalStats operators of the `JaiTools project <http://jaitools.org/>`_ project.

The flow is composed by a single Action called **NDVIStatsAction** that perform all the operations needed. 

The flow periodically check for an XML file inside a FileSystem location: for each XML file found a new execution of the flow will be thrown.

In the following paragraphs will be shown the format of the XML input file and the most important configuration settings.

The input XML file format
-------------------------

In order to run the flow, an user must place inside the watch directory an XML file. See the following file as example:

.. sourcecode:: xml

	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<statsBean>
		<classifier>DISTRICT</classifier>
		<forestMask>DISABLED</forestMask>
		<ndviFileName>dv_19980401_19980410.tif</ndviFileName>
	</statsBean>

* **classifier** The Vector data used as classificator layer. Accepted values are:
	
	* **PROVINCE** Aggregate results by provinces
	
	* **DISTRICT** Aggregate results by districts
	
	* **CUSTOM** Use a custom classifier specifying its absolute path. The path must be write adding one more field inside the statsBean tag called **classifierFullPath**

* **forestMask** The mask used as Region Of Interest to compute the statistics. Accepted Values are:

	* **STANDARD** Use the mask specyfied in the flow configuration (see later in the *Action Configuration* paragraph)
	
	* **CUSTOM** Similar to the value CUSTOM used in classifier. The tag to add is called **forestMaskFullPath**. Place there the absolute path of the shapefile.
	
	* **DISABLED** Don't use any mask 

* **ndviFileName** the file name of the NDVI mosaic granule to use to compute the NDVI statistics

Generate the input XML using the Crop Portal administration webapp
------------------------------------------------------------------

The Crop Information Portal has its own Administration interface that allow the user to run the flow without dealing with XML files and FileSystem directly usage. 

Refer to the Administration Page documentation for more details.

Through that web interface is it possible to run the flow directly from the browser. The Crop Portal administration application will create the xml and will place it in the flow watch dir.

EventGenerator Configuration
----------------------------

.. sourcecode:: xml

		<wildCard>*.xml</wildCard>
		<watchDirectory>ndvistats/in</watchDirectory>

* **wildCard** field is possible to specify which file name/extension looking for. In this case the first (and only) action is developed to accept only XML files so the flow will be launched only if an XML file will be placed in the whatch directory.

* **watchDirectory** is the directory where GB watches for new input files. It is relative to **Geobatch Config Dir**. See the official GB documentation for more info about the Geobatch Config Directory

.. sourcecode:: xml
		
		<interval>0 0/15 4-6 * * ?</interval>
		<interval>* * * * * ?</interval>
		<interval>SEC MIN HR DOM MON DOW</interval>

* **interval** in this field is possible to specify the GeoBatch frequency to checks for new files in the flow's watch directory.
The format to use is the `Quartz syntax <http://quartz-scheduler.org/documentation/quartz-2.1.x/tutorials/crontrigger>` that basically is an evolution of the Linux Crontab syntax.

		
Action Configuration
--------------------
		
.. sourcecode:: xml

		<defaultMaskUrl>file:/opt/gs_data_dir/data/spatial/CROPMASKS/crop_mask_pak_2012.shp</defaultMaskUrl>

* **defaultMaskUrl** is the default mask used if no other mask are specified.
		
.. sourcecode:: xml		

		<dbType>postgis</dbType>
		<dbHost>localhost</dbHost>
		<dbPort>5432</dbPort>
		<dbSchema>public</dbSchema>
		<dbName>NRL</dbName>
		<dbUser>geoserver</dbUser>
		<dbPasswd>**********</dbPasswd>

* The Postgres connection parameters.

.. sourcecode:: xml		

		<tiffDirectory>/opt/mosaics/ndvi</tiffDirectory>

* **tiffDirectory** The directory ehere the NDVI rasters are stored (That is the Mosaic Directory used by geoserver)
		
.. sourcecode:: xml		

		<outputDirectory>/opt/admin_dir/</outputDirectory>

* **outputDirectory** The directory where the output csv will be stored.
		
The whole Flow Configuration
----------------------------
				
.. sourcecode:: xml

    <?xml version="1.0" encoding="UTF-8"?>
    <FlowConfiguration>

        <id>ndvistats</id>
        <name>NDVI stats generation</name>
        <description>Generate a CSV file with a geotiff mask and a zone filter</description>
        
        <corePoolSize>2</corePoolSize>
        <maximumPoolSize>2</maximumPoolSize>
        <keepAliveTime>1500</keepAliveTime>
        <workQueueSize>100</workQueueSize>
        
        <autorun>true</autorun>
        
        <EventGeneratorConfiguration>
            <id>ndvistats_event_gen</id>
            <serviceID>fsEventGeneratorService</serviceID>
            <wildCard>*.xml</wildCard>
            <watchDirectory>ndvistats/in</watchDirectory>
            <osType>OS_UNDEFINED</osType>
            <eventType>FILE_ADDED</eventType>
            <interval>* * * * * ?</interval>
        </EventGeneratorConfiguration>

        <EventConsumerConfiguration>
            <id>ndvistats_consumer</id>
            
        <listenerId>Logger0</listenerId>

            <listenerId>Cumulator</listenerId>
            <performBackup>false</performBackup>
            <preserveInput>true</preserveInput>
            
            <NDVIStatsConfiguration>
        <listenerId>Logger</listenerId>
            <listenerId>Cumulator</listenerId>
            <listenerId>Status</listenerId>
                <id>NDVIIngestConfiguration</id>
                <name>NDVI CSV stats preparation</name>
                <description>Prepare time interval in TIF filenames </description>
                <defaultMaskUrl>file:/opt/gs_data_dir/data/spatial/CROPMASKS/crop_mask_pak_2012.shp</defaultMaskUrl>
                <dbType>postgis</dbType>
                <dbHost>localhost</dbHost>
                <dbPort>5432</dbPort>
                <dbSchema>public</dbSchema>
                <dbName>NRL</dbName>
                <dbUser>geoserver</dbUser>
                <dbPasswd>*********</dbPasswd>
                <tiffDirectory>/opt/mosaics/ndvi</tiffDirectory>
                <outputDirectory>/opt/admin_dir/</outputDirectory>
                <csvSeparator>,</csvSeparator>
            </NDVIStatsConfiguration>

        </EventConsumerConfiguration>

        <ListenerConfigurations>
            <LoggingProgressListener>
                <serviceID>loggingListenerService</serviceID>
                <id>Logger0</id>
                <loggerName>it.geosolutions.geobatch</loggerName>
            </LoggingProgressListener>
            <LoggingProgressListener>
                <serviceID>loggingListenerService</serviceID>
                <id>Logger</id>
                <loggerName>it.geosolutions.geobatch</loggerName>
            </LoggingProgressListener>
            <CumulatingProgressListener>
                <serviceID>cumulatingListenerService</serviceID>
            <appendToListenerForwarder>true</appendToListenerForwarder>
                <id>Cumulator</id>
            </CumulatingProgressListener>
            <StatusProgressListener>
                <serviceID>statusListenerService</serviceID>
                <id>Status</id>
            </StatusProgressListener>
        </ListenerConfigurations>
    </FlowConfiguration>

