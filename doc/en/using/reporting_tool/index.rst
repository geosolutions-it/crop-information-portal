.. module:: cippak.using.reporting_tool
   :synopsis: How to use Reporting Tool

.. _cippak.using.reporting_tool:

Reporting Tool
===========================

This module is responsible of the PDF report generation that includes data charts and specific maps with the aggregated data.

To generate a report you need:

* Season
* Area of interest (AoI)
	* Type
	* Zones
* Commodity
* Range
* Meteorogical factors
* Layout configuration

To access to this module, please press on ``Crop Report`` button on the user interface:

.. figure::  resources/widget_tab.png	
   :align:   center

   Button for the Crop Report module.

Season
------

.. figure::  resources/season.png	
   :align:   center

   Season selection.

Area of interest
----------------

You can select province, district or Pakistan.

For province and district selection you need to select the selected zones with the ``Add`` button

.. figure::  resources/add.png	
   :align:   center

   Add button.

and search the zone to add: 

.. figure::  resources/add_sel.png	
   :align:   center

   Search autocomplete.
                
.. raw:: latex

  \newpage % hard pagebreak at exactly this position

or ``Add from the map``

.. figure::  resources/add_map.png	
   :align:   center

   Add from the map.

and click on the map on the zone to be added.
                
.. raw:: latex

  \newpage % hard pagebreak at exactly this position

In both options, the zone will be added into the AoI grid

.. figure::  resources/add_complete.png	
   :align:   center

   Balochistan province selected as AoI.
                
.. raw:: latex

  \newpage % hard pagebreak at exactly this position

If you select ´Pakistan´, you don't have to select any AoI:

.. figure::  resources/pakistan.png	
   :align:   center

   Pakistan as AoI.

Commodity
---------

Select the commodity for the report.

.. figure::  resources/commodity.png	
   :align:   center

   Wheat as commodity.

Range
-----

Select the range for the report. The last year of the range will be selected as ``Reference year`` for the map.

.. figure::  resources/range.png	
   :align:   center

   Range and reference year selection.

Meteorogical factors
--------------------

Select the factors for the report. You can select one or more of this factors

.. figure::  resources/factors.png	
   :align:   center

   Range and reference year selection.

Layout configuration
--------------------

In this section you can customize a litle bit the generated report.

.. figure::  resources/layout.png	
   :align:   center

   Default layout configuration.

* **Report title**: Title for the report (appear on the first page of the report)
* **Subtitle**: Subtitle for the report (appear on the first page of the report)
* **Crop pages title**: Title for the pages with maps and charts with the aggregated data
* **Meteorological pages title**: Title for pages with meteorogical variables charts (the number of charts and pages depends on your factor selection)

Keep calm
---------

Then you must press on ``Generate Report``

.. figure::  resources/generate_button.png	
   :align:   center

   Generate button.

and wait for the report generation.

.. figure::  resources/wait.png	
   :align:   center

   Wait for the report generation.

We process a lot of data and render it on a PDF document. When the report will be finished, your browser will ask you if you want to download the PDF file.

.. figure::  resources/report_cover.png	
   :align:   center
   
   The PDF cover
   
.. figure::  resources/report_pdf2.png	
   :align:   center
   
   The PDF content