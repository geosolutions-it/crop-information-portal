.. module:: cippak.maintenance.install
   :synopsis: Learn how to install the platform.

.. cippak.maintenance.install:

=======
Install
=======

In this section we're going to learn how to install the platform in a new server. The platform for this training doc is a CentOS 6.

------------
Dependencies
------------

You need to install this components on your server:

* JDK 6
* JAI Image-IO
* GDAL
* Apache Tomcat 6.0.37 (four instances)
* Apache Httpd (Web server)
* PostgreSQL - PostGIS

We assume you know how to install this components. 

++++++++++++++
Services table
++++++++++++++

When you will finished you must have something like that:

========================= ================================= ============= =======================
 Application			  Local path                        Port          Access by AJP proxy
========================= ================================= ============= =======================
Tomcat GeoServer          /opt/tomcat_geoserver               gs_port 	   /geoserver
Tomcat GeoBatch           /opt/tomcat_geobatch	              gb_port	   /geobatch
Tomcat GeoStore           /opt/tomcat_geostore	              gst_port     /geostore
Tomcat GUI                /opt/tomcat_gui                     gui_port     /MapStore, /admin
PostgreSQL/PostGIS        /usr/local/postgres                 pg_port      NO
========================= ================================= ============= =======================

So, we explain a litle bit how to install those components.

+++++++++++++++++
JDK 6
+++++++++++++++++

1. Download jdk-6u45-linux-x64-rpm.bin from the Oracle site.

2. Install::

	#INSTALL JDK6
	 sh jdk-6u45-linux-x64-rpm.bin

	 mkdir jvm
	mv *.rpm jvm/
	 cd jvm/
	mkdir java
	mv jvm dk-6u45-linux-x64-rpm.bin java

++++++++++++++++++++
JAI - Image IO
++++++++++++++++++++

1. Download JAI::

	#download installer  native JAI
	wget http://download.java.net/media/jai/builds/release/1_1_3/jai-1_1_3-lib-linux-amd64-jdk.bin

2. Copy to Java dir::

	#copy to java dir
	cp jai-1_1_3-lib-linux-amd64-jdk.bin /usr/java/jdk1.6.0_45/
	 cd /usr/java/jdk1.6.0_45/

3. Install::

	#install native jai
	sh jai-1_1_3-lib-linux-amd64-jdk.bin
	# *******  accept license, type yes  ******

4. Clean the installation::

	#remove binary
	rm jai-1_1_3-lib-linux-amd64-jdk.bin

5. Download JAI Image-IO::

	#download 
	wget http://download.java.net/media/jai-imageio/builds/release/1.1/jai_imageio-1_1-lib-linux-amd64-jdk.bin
	#needed for compatibility versions
	export _POSIX2_VERSION=199209

6. Copy to Java dir::

	#copy 
	cp jai_imageio-1_1-lib-linux-amd64-jdk.bin /usr/java/jdk1.6.0_45/
	cd /usr/java/jdk1.6.0_45/

7. Install::

	#install
	sh jai_imageio-1_1-lib-linux-amd64-jdk.bin 
	# *******  accept license, type yes  ******

8. Clean the installation::

	#remove binary
	rm jai_imageio-1_1-lib-linux-amd64-jdk.bin

++++++++++++
GDAL
++++++++++++

1. Download repository::

	curl -O http://mirror.i3d.net/pub/fedora-epel/6/x86_64/epel-release-6-8.noarch.rpm

2. Install repository::

	rpm -ivh epel-release-6-8.noarch.rpm

3. Install GDAL::

	yum install gdal

++++++++++++++++++
PostgreSQL/PostGIS
++++++++++++++++++

To install and configure the database you need to access to the server and follow the next steps:

1. **Download and install** PostgreSQL repository::

	wget http://yum.pgrpms.org/9.1/redhat/rhel-5-x86_64/pgdg-centos91-9.1-4.noarch.rpm
	yum install pgdg-centos91-9.1-4.noarch.rpm

2. **Install PostgreSQL**::

	# POSTGRES
	yum install postgresql91-contrib postgresql91-server postgresql91-contrib proj geos

2. **Install PostgreGIS**::

	#POSTGIS
	yum install postgis2_91 postgis2_91-devel

3. **Install** extra packages::

	#other useful packages
	yum install proj-devel geos-devel libxml2-devel libpgxx-devel proj-epsg

4. **Initialize** database::

	#initialize database
	service postgresql-9.1 initdb

5. **Local access**: Edit the access configuration to allow user login from localhost::

	vim /var/lib/pgsql/9.1/data/pg_hba.conf
	# "local" is for Unix domain socket connections only
	local   all             all                                     trust
	# IPv4 local connections:
	host    all             all             127.0.0.1/32            md5
	# IPv6 local connections:
	host    all             all             ::1/128                 md5


6. Start database::

	#start database
	service postgresql-9.1 start

7. Auto run and user::

	# Auto run on  Startup 
	chkconfig postgresql-9.1 on
	chkconfig --add postgresql-9.1
	#alter postgres password
	su postgres
	psql
	 ALTER USER postgres WITH ENCRYPTED PASSWORD 'postgres';
	\q
	exit

************
Dump restore
************

You need to create two different databases:

* NRL
* geostore

The default system configuration use this roles (``user@password:schema`` syntax) to connect:

* ``geoserver@1geosolutions2:NRL``
* ``geostore@geostore:geostore``

You can restore the default databases and roles state with the dump::

 	psql -f crop.dump -U postgres

The dump file is attached on the training docs on ``ROOT/training/data/maintenance``.

++++++++++++++++++
Apache Tomcat
++++++++++++++++++

1. Prepare folders::

	mdir /opt/
	mkdir tomcat
	cd tomcat

2. Download and copy in opt::

	#download and copy in opt
	wget ftp://mirror.nohup.it/apache/tomcat/tomcat-6/v6.0.37/bin/apache-tomcat-6.0.37.tar.gz
	tar xzvf apache-tomcat-6.0.37.tar.gz -C /opt/

	#create symbolic link
	ln -s /opt/apache-tomcat-6.0.37/ /opt/tomcat
	ls /opt
	# should be: (contain apache tomcat and tomcat)
	# apache-tomcat-6.0.37  sun  tomcat

3. Create Catalina base::

	#create catalina base
	#mkdir -p /var/lib/tomcat/base/{bin,conf,logs,temp,webapps,work}
	cp /opt/tomcat/conf/* /var/lib/tomcat/base/conf/

4. Customize different instances: You need to create different instances for tomcat as you can see on `Services table`_.

5. User tomcat::

	#create tomcat user
	adduser -m -s /bin/bash tomcat
	# don’t change password for now
	# passwd tomcat 

*****************
AJP configuration
*****************

You must prepare various tomcat instances configuring different ports with the `AJP Connector <http://tomcat.apache.org/tomcat-6.0-doc/config/ajp.html>`_ on each server.xml.

For example, you need to change the server shutdown port::

	<Server port="8008" shutdown="SHUTDOWN">

and this connector::

	<Connector port="8012" protocol="AJP/1.3" redirectPort="8446" />

for each instance.

++++++++++++++++++
Apache Httpd
++++++++++++++++++

1. Install::

	yum install httpd

2. Basic configuration::

	chkconfig --level 2345 httpd on
	# enable incoming connection 
	#enable incoming tcp connection on port 80

4. Open port 80::

	iptables -I INPUT -p tcp --dport 80 -j ACCEPT
	service iptables save

5. Create configurations to the tomcat instances as you can see on `Services table`_.

***************************
Mod proxy AJP configutation
***************************

You must configure the access for each service present on the `Services table`_ with the `Apache Module mod_proxy_ajp <http://httpd.apache.org/docs/2.2/mod/mod_proxy_ajp.html>`_.

For example, you need to add this configuration for the MapStore instance::

	ProxyPass /MapStore  ajp://localhost:gst_port/MapStore
	ProxyPassReverse /MapStore  ajp://localhost:gst_port/MapStore

++++++++
Services
++++++++

All tomcat instances should be installed as default services on the system. The configuration files are available on ``ROOT/training/data/maintenance/config.tar.gz``::

	## APACHE WEB SERVER

	cp httpd/conf.d/* /etc/httpd/conf.d/

	#CONFIGURE MAPSTORE
	configure urls

	##TOMCAT STARTUP SCRIPTS
	cp config/init.d/* /etc/init.d/

	# start services on startup
	chkconfig geoserver  on
	chkconfig --add geoserver 

	chkconfig geobatch on
	chkconfig --add geobatch 

	chkconfig geostore on
	chkconfig --add geostore 

	chkconfig gui on
	chkconfig --add gui


----------------
Platform install
----------------


You need to `compile <compile.html>`_ the platform. Then you will have:

* OpenSDI-Manager war (admin.war)
* GeoBatch war (geobatch.war)
* MapStore war (mapcomposer.war)

Also, you need a version of this products:

* GeoStore
* Http-proxy
* GeoServer


++++++++
GeoStore
++++++++

1. Download code from the `GeoStore Github repository <https://github.com/geosolutions-it/geostore>`_
2. Compile and prepare the war for a PostgreSQL deployment::

	geostore/src$ mvn clean install -Dovrdir=postgres -Ppostgres

4. Copy ``geostore/src/server/app/target/geostore.war`` to ``/opt/tomcat_geostore/webapps`` folder.

++++++++++
Http-proxy
++++++++++

1. Download code from the `Http proxy Github repository <https://github.com/geosolutions-it/http-proxy>`_
2. `Configure it <https://github.com/geosolutions-it/http-proxy/wiki/Configuring%20Http-Proxy>`_
3. `Build http proxy <https://github.com/geosolutions-it/http-proxy/wiki/Building%20Istructions>`_::

	http-proxy/src$ mvn clean install -Dmaven.test.skip

4. Copy ``http-proxy/http-proxy.war`` to ``/opt/tomcat_geostore/webapps`` folder

+++++++++
GeoServer
+++++++++

1. Download the lastest war from `GeoServer downloads page <http://geoserver.org/display/GEOS/Download>`_
2. Copy to ``/opt/tomcat_geoserver/webapps`` folder

+++++++++++++++
OpenSDI-Manager
+++++++++++++++

1. `Compile OpenSDI Manager <compile.html#opensdi-manager>`_
2. Copy war to ``/opt/tomcat_gui/webapps`` folder
3. Configure it. Follow the instructions on `OpenSDI Manager configuration page <../admin/conf/admin/configuration.html>`_.

++++++++
GeoBatch
++++++++

1. `Compile GeoBatch <compile.html#geobatch>`_
2. Copy war to ``/opt/tomcat_geobatch/webapps`` folder
3. Configure it. Follow the instructions on `GeoBatch configuration page <../admin/conf/geobatch/index.html>`_.

++++++++
Mapstore
++++++++

1. `Compile MapStore <compile.html#mapstore>`_
2. Copy war to ``/opt/tomcat_gui/webapps`` folder
3. Configure it. Follow the instructions on `MapStore configuration page <../admin/conf/mapstore/configuration.html>`_.
