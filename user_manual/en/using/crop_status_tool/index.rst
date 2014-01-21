.. module:: cippak.using.crop_status_tool
   :synopsis: How to use Crop Status Tool

.. _cippak.using.crop_status_tool:

.. raw:: latex

  \newpage % hard pagebreak at exactly this position

Crop Status Tool
===========================

This section illustrates how you can use **Crop Status Tool**.

This module aims to provide users with information about the crop health status. It will compare agro-meteorological variables (current values and historical averages) with thresholds defining optimum from warning conditions impacting crop growth and ultimately crop results. Variables defining these thresholds will likely depend on area, crop stage, crop type and more. 

*************************************
Tab to interact with Crop Status Tool
*************************************

**Crop Status Tool** is located in the west of the layout and allows you to generate chart dynamically.

    .. figure:: img/cropstatus_tool.png

                Crop Status Tool.

Crop conditions in a specified year (current or historical) can be evaluated by analyzing one or more agro-meteorological factors, for which comparison between actual values and potential warning thresholds is charted. The interface to set parameters is on the right.

The system allows users to:

* Extract tabular data (Data). only for registered users. Ask account for enabling this selection.
* Generate time-series charts (Chart)

.. raw:: latex

  \newpage % hard pagebreak at exactly this position

Output Type Chart
^^^^^^^^^^^^^^^^^

1. Select **output type**:

    .. figure:: img/output_chart.png

                Choose Output Type Chart.

you need to select **Chart** type.

2. Define **season**:
                
    .. figure:: img/season_choice.png

                Choose Season.       

you can select ``Rabi`` or ``Kharif``.

3. Select **area of interest**:

    .. figure:: img/aoi_chart_single.png

                Choose Area Of Interest.

you can select ``Province`` or ``District``

.. raw:: latex

  \newpage % hard pagebreak at exactly this position

It  can be selected by using the text search box or the add from map button.

    .. figure:: img/aoi_map_selection.png

                Area Of Interest selection.

The selected zone will be highlighed on the map.

4. Select reference **year**:

    .. figure:: img/choice_year.png

                Choose Year.

The data or chart will be generated for the selected year.

5. Select a **commodity**:

    .. figure:: img/commodity_choice.png

                Choose Commodity.

.. raw:: latex

  \newpage % hard pagebreak at exactly this position

The options you can select will change when you change the season.

6. Select **factor(s)** to process

    .. figure:: img/cropstatus_factor.png

                Choose Factor.

Now you can generate the chart with the button:

    .. figure:: img/generate_chart_button.png

                Generate Chart Button.

.. raw:: latex

  \newpage % hard pagebreak at exactly this position

And the chart will have the common options:

    .. figure:: img/cropstatus_generated_chart.png

                Generated Chart.

You can minimize, close, print export...

At the buttom of the chart will appear a legend for the selected data:

    .. figure:: img/cropstatus_generated_chart_legend.png

                Generated Chart Legend.       

       