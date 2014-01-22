.. module:: cippak.admin.conf.mapstore.ndviingestion
   :synopsis: Learn about how to configure Crop Information Portal Components.

.. _cippak.admin.conf.mapstore.ndviingestion:

===================
NDVI ingestion flow
===================

.. sourcecode:: xml

	<?xml version="1.0" encoding="UTF-8"?>
	<FlowConfiguration>

		<id>ndviingestion</id>
		<name>NDVI file ingestion</name>
		<description>Ingest geotiff file and add them to a NDVI mosaic</description>
		
		<corePoolSize>2</corePoolSize>
		<maximumPoolSize>2</maximumPoolSize>
		<keepAliveTime>1500</keepAliveTime>
		<workQueueSize>100</workQueueSize>
		
		<autorun>true</autorun>
		
		<EventGeneratorConfiguration>
			<id>ndvi_event_gen</id>
			<serviceID>fsEventGeneratorService</serviceID>
			<wildCard>*.tif</wildCard>
			<watchDirectory>ndviingestion/in</watchDirectory>
			<osType>OS_UNDEFINED</osType>
			<eventType>FILE_ADDED</eventType>
			<interval>0 0/15 4-6 * * ?</interval>
		</EventGeneratorConfiguration>

		<EventConsumerConfiguration>
			<id>ndvi_consumer</id>
			
			<listenerId>LoggingListener</listenerId>
			<listenerId>CumulatingListener</listenerId>

			<performBackup>false</performBackup>
			<preserveInput>true</preserveInput>


			<NDVIIngestConfiguration>
				<id>NDVIIngestConfiguration</id>
				<name>NDVI file preparation</name>
				<description>Prepare time interval in TIF filenames </description>

				<destinationDir>/home/geosolutions/gbtemp/ndvi</destinationDir>
			</NDVIIngestConfiguration>

			<ImageMosaicActionConfiguration>

				<id>ImageMosaicService</id>

				<name>NDVI image mosaic</name>
				<description>Add geotiff to the NDVI mosaic</description>

				<listenerId>LoggingListener</listenerId>
				<listenerId>CumulatingListener</listenerId>
				<listenerId>StatusListener</listenerId>

				<crs>EPSG:4326</crs>
				<envelope/>

				<dataTransferMethod>EXTERNAL</dataTransferMethod>

				<geoserverUID>admin</geoserverUID>
				<geoserverPWD>************</geoserverPWD>
				<geoserverURL>http://localhost/geoserver</geoserverURL>

				<defaultNamespace>ndvi</defaultNamespace>
				<defaultStyle>raster</defaultStyle>

				<wmsPath>/</wmsPath>
				<backgroundValue>NaN</backgroundValue>
				<outputTransparentColor/>
				<inputTransparentColor/>
				<allowMultithreading>true</allowMultithreading>
				<useJaiImageRead>false</useJaiImageRead>

				<tileSizeH>256</tileSizeH>
				<tileSizeW>256</tileSizeW>
				<NativeMinBoundingBoxX>-30</NativeMinBoundingBoxX>
				<NativeMinBoundingBoxY>25</NativeMinBoundingBoxY>
				<NativeMaxBoundingBoxX>45</NativeMaxBoundingBoxX>
				<NativeMaxBoundingBoxY>70</NativeMaxBoundingBoxY>

				<latLonMinBoundingBoxX>-30</latLonMinBoundingBoxX>
				<latLonMinBoundingBoxY>25</latLonMinBoundingBoxY>
				<latLonMaxBoundingBoxX>45</latLonMaxBoundingBoxX>
				<latLonMaxBoundingBoxY>70</latLonMaxBoundingBoxY>

				<!--NONE, REPROJECT_TO_DECLARED, FORCE_DECLARED-->
				<projectionPolicy>NONE</projectionPolicy>

				<!-- ref dir is GB_CONF_DIR/FLOWID/ACTIONID -->
				<datastorePropertiesPath>../datastore.properties</datastorePropertiesPath>

				<!-- METADATA -->
				<!-- file name is file_20130201_20130210.tif -->
				<DomainAttribute>
					<dimensionName>time</dimensionName>
					<attribName>time</attribName>
					<regEx><![CDATA[(\?<=dv_)[0-9]{8}(\?=_.\*)]]></regEx>
					<endRangeAttribName>endtime</endRangeAttribName>
					<endRangeRegEx><![CDATA[(\?<=dv_[0-9]{8}_)[0-9]{8}(\?=.\*)]]></endRangeRegEx>
				</DomainAttribute>

			</ImageMosaicActionConfiguration>

		</EventConsumerConfiguration>

		<ListenerConfigurations>
			<LoggingProgressListener>
				<serviceID>loggingListenerService</serviceID>
				<id>LoggingListener</id>
				<loggerName>it.geosolutions.geobatch</loggerName>
			</LoggingProgressListener>
			<CumulatingProgressListener>
				<serviceID>cumulatingListenerService</serviceID>
				<id>CumulatingListener</id>
			</CumulatingProgressListener>
			<StatusProgressListener>
				<serviceID>statusListenerService</serviceID>
				<id>StatusListener</id>
			</StatusProgressListener>
		</ListenerConfigurations>
	</FlowConfiguration>


The action automatially do a *retiling* and generate the overviews to increase performances for web usage.
Processing the file name, the action publish the tif as a granule in geoserver for the right time period (expressed in the file name).
The granule will be available using the WMS ``time`` parameter.
for instance if we have ``dv98041_pak`` (1st dekad of January 1998) the image will be available passing from Jan 1st 1998 to Jan 9th 1998 (whole day).
  
File format
^^^^^^^^^^^

The name of the Geotiff file must have this format: ``dvYYMMD_*.tif`` with:

* **YY**: Year of the image: two last digits of the year. *e.g.1998 becomes 98; 2000 becomes 00*.
* **MM**: Month of the image: between 01 (January) and 12 (December)
* **D**: 1,2 or 3. This represents the dekad.



The image to ingest must have these characteristics:

* **DataType**: Byte,1 band
* **Bounding Box**  ``59.7723211 23.0669642 80.4330354 37.6919642``
* **Size**: 2314x1638 px

here a sample ``gdalinfo`` output of a compatible file::

    Driver: GTiff/GeoTIFF
    Files: dv98041_pak.tif
    Size is 2314, 1638
    Coordinate System is:
    GEOGCS["WGS 84",
        DATUM["WGS_1984",
            SPHEROID["WGS 84",6378137,298.257223563,
                AUTHORITY["EPSG","7030"]],
            AUTHORITY["EPSG","6326"]],
        PRIMEM["Greenwich",0],
        UNIT["degree",0.0174532925199433],
        AUTHORITY["EPSG","4326"]]
    Origin = (59.772321143118091,37.691964174177784)
    Pixel Size = (0.008928571417941,-0.008928571417941)
    Metadata:
      AREA_OR_POINT=Area
      TIFFTAG_RESOLUTIONUNIT=1 (unitless)
      TIFFTAG_SOFTWARE=IMAGINE TIFF Support
    Copyright 1991 - 1999 by ERDAS, Inc. All Rights Reserved
    @(#)$RCSfile: etif.c $ $Revision: 1.10.1.9.1.9.2.11 $ $Date: 2004/09/15 18:42:01
    EDT $
      TIFFTAG_XRESOLUTION=1
      TIFFTAG_YRESOLUTION=1
    Image Structure Metadata:
      INTERLEAVE=BAND
    Corner Coordinates:
    Upper Left  (  59.7723211,  37.6919642) ( 59d46'20.36"E, 37d41'31.07"N)
    Lower Left  (  59.7723211,  23.0669642) ( 59d46'20.36"E, 23d 4' 1.07"N)
    Upper Right (  80.4330354,  37.6919642) ( 80d25'58.93"E, 37d41'31.07"N)
    Lower Right (  80.4330354,  23.0669642) ( 80d25'58.93"E, 23d 4' 1.07"N)
    Center      (  70.1026783,  30.3794642) ( 70d 6' 9.64"E, 30d22'46.07"N)
    Band 1 Block=64x64 Type=Byte, ColorInterp=Gray