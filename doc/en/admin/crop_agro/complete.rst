.. module:: cippak.admin.crop_agro.complete
   :synopsis: Learn about how to manage Crops and Agromet factors.

.. _cippak.admin.crop_agro.complete:

================
Complete example
================

   .. note::  All resources for this exercise are present on the folder ``ROOT\training\data\crop_agro`` of the training. Please open the folder and use the files inside to complete the example.

We have attached data and styles for a complete proccess to add a new crop with data and styles to be used on the portal:


1. **Create the new crop**: follow the instructions in `Create a new Crop <index.html#create-a-new-crop>`_ section and create a new crop with the identifier *soybean*

2. **Create the styles**: Create a new style of each needed type (use xml files inside the training folder):

* ``province_soybean_area_style.xml``
* ``province_soybean_prod_style.xml``
* ``province_soybean_yield_style.xml``
* ``district_soybean_area_style.xml``
* ``district_soybean_prod_style.xml``
* ``district_soybean_yield_style.xml``

After this step you will have the new styles published on GeoServer:

.. figure:: img/styles_search.png

    Example's styles


3. **Import the data**: Follow the instructions on `CSV ingestion <../csv/index.html#operations>`_ section with the file ``soybean_10-12.csv``

4. **Test data**: Follow the instructions on `crop data tool <../../using/crop_data_tool/index.html>`_ section to use the new crop:

* `As map <../../using/crop_data_tool/index.html#output-type-map>`_. You must select a correct year in the range (2010-2012). The map should be something like that:

.. figure:: img/result_map.png
  
	Expected map (Soybean 2010 - Yield)

* `As chart <../../using/crop_data_tool/index.html#output-type-chart>`_: You must select the correct interval (2010-2012) and district or provinces with data.

5. **Delete the test data**: Follow the instructions on `CSV ingestion <../csv/index.html#operations>`_ section with the file ``soybean_10-12_del.csv`` and change the zoom level. You should see the same color on all province/districts.

