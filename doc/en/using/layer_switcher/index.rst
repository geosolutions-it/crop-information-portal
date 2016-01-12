.. module:: cippak.using.layer_switcher
   :synopsis: Layers Switcher

.. _cippak.using.layer_switcher:

Layer Switcher
==============

Layer Switcher lists all the layers on the map and shows what the features in each layer represent.
The layers at the top of the table of contents draw on top of those below them.
In CROP Information Portal's Layer Switcher there are two types of layers: base layers and overlays.

.. figure:: img/switcher.png
    :align:   center
    
    the Layer Switcher.

************
Base Layers
************

Base Layers are mutually exclusive layers, meaning only one can be enabled at any given time.
Base Layers always display below overlay layers.

In CROP Information Portal this layers are grouped in a GROUP called:

Background
^^^^^^^^^^
    +----------------------------+------------------------------------+
    |          Name              | Description                        |
    |                            |                                    |
    +============================+====================================+
    | None                       | no background layer                |
    +----------------------------+------------------------------------+
    | Administrative             | only administrative boundaries     |
    +----------------------------+------------------------------------+
    | Google Roadmap             | only Google Roadmap                |
    +----------------------------+------------------------------------+
    | Google Hybrid              | only Google Hybrid                 |
    +----------------------------+------------------------------------+
    | Google Terrain             | only Google Terrain                |
    +----------------------------+------------------------------------+
    | Open Street Map            | only Open Street Map               |
    +----------------------------+------------------------------------+
    | MapQuest OpenStreetMap     | only MapQuest OpenStreetMap        |
    +----------------------------+------------------------------------+
    
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
    | Populated Places         | Populated Places boundaries      |
    +--------------------------+----------------------------------+
    | District Boundary        | Pakistan District Boundaries     |
    +--------------------------+----------------------------------+
    | Province Boundary        | Pakistan Province Boundaries     |
    +--------------------------+----------------------------------+

Meteo Stations group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------+---------------------------------------+
    |          Name            | Description                           |
    |                          |                                       |
    +==========================+=======================================+
    | MeteoData                | Meteorological Stations of Pakistan   |
    +--------------------------+---------------------------------------+

Transportation group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------+-------------------------------+
    |          Name            | Description                   |
    |                          |                               |
    +==========================+===============================+
    | Roads                    | Main Streets of the Pakistan  |
    +--------------------------+-------------------------------+

Hydrology group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------+-------------+
    |          Name            | Description |
    |                          |             |
    +==========================+=============+
    | Indus River              | Indus River |
    +--------------------------+-------------+
    | Rivers                   | Rivers      |
    +--------------------------+-------------+

Land Cover group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------------+---------------------------------+
    |          Name                  |     Description                 |
    |                                |                                 |
    +================================+=================================+
    | GlobalCover 2009               | Land cover of 2009              |
    +--------------------------------+---------------------------------+
    | GlobalCover 2005-06            | Land cover of 2005-06           |
    +--------------------------------+---------------------------------+
    | Land cover 2000                | Land cover of 2000              |
    +--------------------------------+---------------------------------+
    | Land cover 2010                | Land cover of 2010              |
    +--------------------------------+---------------------------------+
    | Crop Mask                      | Mask of Crop                    |
    +--------------------------------+---------------------------------+

Topography group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------+-----------------------------------------+
    |          Name            | Description                             |
    |                          |                                         |
    +==========================+=========================================+
    | Contours 1000ft          | Contours with equidistance of 1000 feet |
    +--------------------------+-----------------------------------------+

Flooding group
^^^^^^^^^^^^^^^^^^^^^^^^^^
    +--------------------------+--------------------------------+
    |          Name            | Description                    |
    |                          |                                |
    +==========================+================================+
    | Flooded Areas 2010       | Flooded areas related to 2010  |
    +--------------------------+--------------------------------+
    | Flooded Areas 2011       | Flooded areas related to 2011  |
    +--------------------------+--------------------------------+
    | Flooded Areas 2012       | Flooded areas related to 2012  |
    +--------------------------+--------------------------------+
