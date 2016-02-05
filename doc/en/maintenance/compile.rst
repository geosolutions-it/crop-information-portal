.. module:: cippak.maintenance.compile
   :synopsis: Learn how to compile the platform.

.. cippak.maintenance.compile:

=======
Compile
=======


In this section we're going to learn how to compile different parts of the application. The source code is available on Github repository: 

* https://github.com/geosolutions-it/crop-information-portal

Please, follow the instructions in the link before continue.

The code of the project is divided in three different applications:

* OpenSDI-Manager2
* GeoBatch
* MapStore

OpenSDI-Manager2 and GeoBatch costumizations use Maven to compile it and MapStore uses Ant.

OpenSDI-Manager2
----------------

To compile the OpenSDI-Manager application::

	crop-information-portal/OpenSDI-Manager/src$ mvn clean install -Dmaven.test.skip
	...
	[INFO] Reactor Summary:
	[INFO] 
	[INFO] OpenSDI-Manager Portal ............................ SUCCESS [0.489s]
	[INFO] OpenSDI Manager - Test core ....................... SUCCESS [2.008s]
	[INFO] OpenSDI Manager - core ............................ SUCCESS [3.558s]
	[INFO] OpenSDI Manager - Admin Webapp .................... SUCCESS [2.577s]
	[INFO] OpenSDI Manager - Log operation ................... SUCCESS [0.041s]
	[INFO] OpenSDI Manager - Log operation - core ............ SUCCESS [0.676s]
	[INFO] OpenSDI Manager - Log operation - web extension ... SUCCESS [4.435s]
	[INFO] SHP file browser - OpenSDI-Manager extension ...... SUCCESS [4.362s]
	[INFO] OpenSDI Manager - Crop Information Portal ......... SUCCESS [0.082s]
	[INFO] OpenSDI Manager - Crop Information Portal - core .. SUCCESS [1.093s]
	[INFO] OpenSDI Manager - Crop Information Portal - MVC extension  SUCCESS [1.053s]
	[INFO] OpenSDI Manager - Crop Information Portal - webapp extension  SUCCESS [4.636s]
	[INFO] ------------------------------------------------------------------------
	[INFO] BUILD SUCCESS
	[INFO] ------------------------------------------------------------------------
	[INFO] Total time: 35.109s
	[INFO] Finished at: Fri Jan 10 16:20:45 CET 2014
	[INFO] Final Memory: 52M/147M
	crop-information-portal/OpenSDI-Manager/src$

Now you have a ``opensdi2-manager`` application to be deployed on `crop-information-portal/OpenSDI-Manager/src/web/target/opensdi2-manager.war`. 

| You can deploy it on your web container and change the configuration as you need.
| Please, read the `Configuration of the Administration Interface training <../admin/conf/admin/configuration.html>`_

GeoBatch
--------

To compile the GeoBatch application::

	crop-information-portal/OpenSDI-Manager/geobatch$ mvn clean install -Dmaven.test.skip
	...
	[INFO] Reactor Summary:
	[INFO] 
	[INFO] NRL GeoBatch Root ................................. SUCCESS [7.362s]
	[INFO] GeoBatch action: CSV ingest ....................... SUCCESS [12.779s]
	[INFO] GeoBatch action: NDVI preparation ................. SUCCESS [17.881s]
	[INFO] GeoBatch action: NDVI stats generation ............ SUCCESS [7.814s]
	[INFO] NRL GeoBatch webapp ............................... SUCCESS [21.242s]
	[INFO] ------------------------------------------------------------------------
	[INFO] BUILD SUCCESS
	[INFO] ------------------------------------------------------------------------
	[INFO] Total time: 1:08.150s
	[INFO] Finished at: Wed Jan 15 17:58:24 CET 2014
	[INFO] Final Memory: 32M/97M
	[INFO] ------------------------------------------------------------------------
	crop-information-portal/OpenSDI-Manager/geobatch$

Now you have a ``GeoBatch`` application to be deployed on `crop-information-portal/OpenSDI-Manager/geobatch/webapp/target/geobatch.war`. 

| You can deploy it on your web container and change the configuration as you need.
| Please, read the `GeoBatch configuration training <../admin/conf/geobatch/index.html>`_


Mapstore
--------

To compile the MapStore application:


1. **Init**: Download dependencies and build ringo jars::

	crop-information-portal/mapstore$ ant init
	Buildfile: crop-information-portal/mapstore/build.xml

	init:
	     [echo] building ringo jars

	init:

	compile:
	    [mkdir] Created dir: crop-information-portal/mapstore/externals/ringojs/build/classes
	    [javac] Compiling 52 source files to crop-information-portal/mapstore/externals/ringojs/build/classes

	jar:
	      [jar] Building jar: crop-information-portal/mapstore/externals/ringojs/lib/ringo.jar
	      [jar] Building jar: crop-information-portal/mapstore/externals/ringojs/run.jar

	BUILD SUCCESSFUL
	Total time: 2 seconds
	crop-information-portal/mapstore$ 

2. **Package**: Compile an package it into a war::

	crop-information-portal/mapstore$ ant war
	...
	war:
	      [war] Building war: crop-information-portal/mapstore/mapcomposer/build/mapcomposer.war
	      [war] Building war: crop-information-portal/mapstore/mapmanager/build/mapstore.war

	BUILD SUCCESSFUL
	Total time: 24 seconds
	crop-information-portal/mapstore$ 

Now you have a ``MapStore`` application to be deployed on `crop-information-portal/mapstore/mapcomposer/build/mapcomposer.war`. 

| You can deploy it on your web container and change the configuration as you need.
| Please, read the `MapStore Configuration training <../admin/conf/mapstore/configuration.html>`_