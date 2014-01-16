.. module:: cippak.maintenance.update
   :synopsis: Learn how to update the platform.

.. cippak.maintenance.update:

======
Update
======

In this section we're going to learn how to update a production instance. Your start point must be a system with a valid deployment (see `install <install.html>`_ section).

++++++++++++
Applications
++++++++++++

.. note:: We recommend to backup each war of each deployment before update it. If you have any problem with the update process, you can restore it and see the error log with the system up.

You can update this applications from the code:

* OpenSDI-Manager
* GeoBatch
* MapStore

***************
OpenSDI-Manager
***************

1. `Compile OpenSDI Manager <compile.html#opensdi-manager>`_
2. Backup your previous configuration present on ``/opt/tomcat_gui/webapps/admin/WEB-INF/classes/opensdi-config-ovr.properties``
3. Stop gui seervice (sometimes it isn't necceary)::

	$ service gui stop

4. Remove old war and content::

	$ rm -r /opt/tomcat_gui/webapps/admin**

5. Copy war to ``/opt/tomcat_gui/webapps`` folder

6. Start system::

	$ service gui start

7. Configure it. Follow the instructions on `OpenSDI Manager configuration page <../admin/conf/admin/configuration.html>`_. You can restore your older configuration, but be carefull with new options present on the new deployed war comparing the actual ``/opt/tomcat_gui/webapps/admin/WEB-INF/classes/opensdi-config-ovr.properties`` with the backed one.

8. Restart again to reload the configuration::

	$ service gui restart

********
GeoBatch
********

1. `Compile GeoBatch <compile.html#geobatch>`_
2. Stop geobatch seervice::

	$ service geobatch stop

3. Remove old war and content::

	$ rm -r /opt/tomcat_gui/webapps/geobatch**

5. Copy war to ``/opt/tomcat_geobatch/webapps`` folder

6. Start system::

	$ service geobatch start

7. Configure it if you need. Follow the instructions on `GeoBatch configuration page <../admin/conf/geobatch/index.html>`_  (older configuration must be used, you only need to configure it if there are changes on your flow configurations) 

********
MapStore
********

1. `Compile MapStore <compile.html#mapstore>`_
2. Backup your previous configuration present on ``/opt/tomcat_gui/webapps/MapStore/WEB-INF/app/static/config/mapStoreConfig.js``
3. Stop gui seervice (sometimes it isn't necceary)::

	$ service gui stop

4. Remove old war and content::

	$ rm -r /opt/tomcat_gui/webapps/MapStore**

5. Copy war to ``/opt/tomcat_gui/webapps`` folder

6. Start system::

	$ service gui start

7. Configure it. Follow the instructions on `MapStore configuration page <../admin/conf/mapstore/configuration.html>`_. You can restore your olde configuration, but you must be carefull if there are new options on the new ``/opt/tomcat_gui/webapps/MapStore/WEB-INF/app/static/config/mapStoreConfig.js`` file and merge it.

++++++++++++
Database
++++++++++++

If you're going to execute an update, it's recommended to backup the database status before the update. Most updates will be attached as a SQL script.

******
NRL
******

1. Backup (as postgres)::

	postgres@server$ pg_dump NRL > path_to_backup.dmp

2. Execute the update::

	pgsql NRL < update.sql

***********
Geostore
***********

It isn't common, but:

1. Backup (as postgres)::

	postgres@server$ pg_dump geostore > path_to_backup.dmp

2. Execute the update::

	pgsql geostore < update.sql

++++++++++++
Custom packs
++++++++++++


.. note:: Be carefull with this method, if your update have new configuration parameters, you must merge it. See:

	* `OpenSDI-Manager`_ step 7.
	* `MapStore`_ step 7.

If your updating one server with a customized pack, you need to restore your customization after the update. The custom packs are available on the `Github repository <https://github.com/geosolutions-it/crop-information-portal/tree/master>`_.

For example, to apply the ``pakistan`` custom pack:

1. Upload the `Github custom pack content <https://github.com/geosolutions-it/crop-information-portal/tree/master/custom_pack>`_ to the server.
2. Open the custom pack::

	cd custom_pack/pakistan/

3. Copy content::

	cp -R opt/* /opt/

4. Change owner to tomcat::

	chown tomcat:tomcat /opt -R