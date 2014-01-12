.. module:: cippak.using.toc
   :synopsis: Table Of Contents

.. _cippak.using.toc:

Table Of Contents (TOC)
=======================

The table of contents lists all the layers on the map and shows what the features in each layer represent.
The layers at the top of the table of contents draw on top of those below them.
In CROP Information Portal's TOC there are two types of layers: base layers and overlays.

************
Base Layers
************


Base Layers are mutually exclusive layers, meaning only one can be enabled at any given time.
Base Layers always display below overlay layers.

In CROP Information Portal this layers are grouped in a GROUP called:

Background
^^^^^^^^^^
    +--------------------------+------------------------------------------------------------------------------------------+
    |          Name            | Description                                                                              |
    |                          |                                                                                          |
    +==========================+==========================================================================================+
    | None                     | no background layer                                                                      |
    +--------------------------+------------------------------------------------------------------------------------------+
    | Administrative           | only administrative boundaries                                                           |
    +--------------------------+------------------------------------------------------------------------------------------+
    | Bing Aerial              | only Bing Aerial map                                                                     |
    +--------------------------+------------------------------------------------------------------------------------------+
    
**************************
Non Base Layers (Overlays)
**************************


Non base layers, sometimes called overlays, are the alternative to Base Layers.
Multiple non-base layer can be enabled at a time.

In CROP Information Portal this layers are grouped in several GROUPS

Admin group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------+----------------------------------+
    |          Name            | Description                      |
    |                          |                                  |
    +==========================+==================================+
    | Label                    | Administrative boundaries label  |
    +--------------------------+----------------------------------+
    | Populated Places         | Desc                             |
    +--------------------------+----------------------------------+
    | District Boundary        | Desc                             |
    +--------------------------+----------------------------------+
    | Province Boundary        | Desc                             |
    +--------------------------+----------------------------------+

Meteo Stations group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------+-------------+
    |          Name            | Description |
    |                          |             |
    +==========================+=============+
    | MeteoData                | Desc        |
    +--------------------------+-------------+

Transportation group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------+-------------+
    |          Name            | Description |
    |                          |             |
    +==========================+=============+
    | Roads                    | Desc        |
    +--------------------------+-------------+

Hydrology group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------+-------------+
    |          Name            | Description |
    |                          |             |
    +==========================+=============+
    | Indus River              | Desc        |
    +--------------------------+-------------+
    | Rovers                   | Desc        |
    +--------------------------+-------------+

Land Cover group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------+-------------+
    |          Name            | Description |
    |                          |             |
    +==========================+=============+
    | GlobalCover 2009         | Desc        |
    +--------------------------+-------------+
    | GlobalCover 2005-06      | Desc        |
    +--------------------------+-------------+
    | Land cover 2000          | Desc        |
    +--------------------------+-------------+
    | Land cover 2010          | Desc        |
    +--------------------------+-------------+
    | Crop Mask                | Desc        |
    +--------------------------+-------------+

Topography group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------+-------------+
    |          Name            | Description |
    |                          |             |
    +==========================+=============+
    | Contours 1000ft          | Desc        |
    +--------------------------+-------------+

Flooding group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------+-------------+
    |          Name            | Description |
    |                          |             |
    +==========================+=============+
    | Flooded Areas 2010       | Desc        |
    +--------------------------+-------------+
    | Flooded Areas 2011       | Desc        |
    +--------------------------+-------------+
    | Flooded Areas 2012       | Desc        |
    +--------------------------+-------------+
