.. module:: cippak.using.agromet_tool
   :synopsis: How to use Agromet Tool

.. _cippak.using.agromet_tool:

.. raw:: latex

  \newpage % hard pagebreak at exactly this position  

Agromet Tool
===========================

This section illustrates how you can use **Agromet Tool**.

*********************************
Tab to interact with Agromet Tool
*********************************

**Agromet Tool** is located in the west of the layout and allows you to generate chart dynamically.

The agromet tool aggregates agro-meteorological data from different areas of interest and display these data in dekadal charts.

The charts contains 3 charts:

* one chart for the reference year values
* one chart for the previous year values
* one chart for the average of the values of the previous years on the selected interval.

    .. figure:: img/agromet_tool.png

                Agromet Tool.

Output Type Chart
^^^^^^^^^^^^^^^^^

To generate charts that describes the trend of factor **Max Temperature** choose **Chart** as output type and select the **Season**, the **Area of interest** and the **Range of years** in which you are interested.

1. Select **output type**: Generate time-series charts (Chart):

    .. figure:: img/output_chart.png

                Choose Output Type Chart.

2. Filter available crops by *season* (Rabi or Kharif)
                
    .. figure:: img/season_choice.png

                Choose Season.                

3. Select **area of interest**
    a. Type: Province or District
        i. Add District(s) or Province(s) from list (Add)
        ii. Add District(s) or Province(s) from map (Add from map)
        iii. Delete one or all selected items (Clear)
                
    .. figure:: img/aoi_choice.png

                Choose Area Of Interest.

    .. figure:: img/aoi_map_selection.png

                Area Of Interest selection.

4. Select **time interval** for the averaging: highest in the interval is the reference year

    .. figure:: img/range_year_choice.png

                Choose Years Range.

5. Select **agromet factor(s)**

    .. figure:: img/agromet_factor.png

                Choose Factor.

6. Select **variable** to process (only for mapping)
    a. Area; Production; Yield;

7. Define output’s **unit of measures** (currently only default)
    a. tons/bales; ha; kg/ha

8. Press on **generate chart** button.

    .. figure:: img/generate_chart_button.png

                Generate Chart Button.



    .. figure:: img/agromet_generated_chart.png

                Generated Chart.



    .. figure:: img/agromet_generated_chart_legend.png

                Generated Chart Legend.    

Notes:

* One line is plotted for each year in the range and one more with the mean in the range
* Charts resize on browser’s window resizing;
* Two icons on the top right corner of each chart:

    .. figure:: img/chart_opt_1.png

        Charts export.  

 allow to Print or Export to different graphic formats  (raster: PNG, JPEG; or vector: PDF and SVG) the selected chart; 
* The chart container shows three icons:  

    .. figure:: img/chart_opt_2.png

        Charts table options. 

 The content can be collapsed or closed. The info icon allows to get basic information about the chart or group of charts.                  