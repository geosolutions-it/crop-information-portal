.. module:: cippak.admin.conf.mapstore.general_note
   :synopsis: Learn about how to configure Crop Information Portal Components.

.. _cippak.admin.conf.mapstore.general_note:

GeoBatch Flows at a glance
==========================

GeoBatch's basic idea is to perform a *chain of actions triggered by custom defined events* called **Flow**. 

The possible **event generators** include monitoring for new files added to a directory, or receiving files in the embedded FTP server. **Actions** range from geotransforming an input raster file, to creating overviews, or publishing data into a GeoServer instance.

The *Actions* and *Event Generators* that composes a *Flow* are selected and configured through a *Flow Configuration* tipically implemented as an XML document.

.. figure:: images/KeyConcepts.png
   :align: center
   
You can find more GeoBatch information visiting the `official GeoBatch documentation <http://demo.geo-solutions.it/share/github/geobatch/download/latest/doc/>`_