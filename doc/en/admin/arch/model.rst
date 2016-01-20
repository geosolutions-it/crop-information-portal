.. module:: cippak.admin.arch.model
   :synopsis: Learn about how to configure Crop Information Portal Model.

.. _cippak.admin.arch.model:

********
Model
********

The NRL database contains the main model

This is the list of the tables containing data in the database:

=================== ===================================================================================================
       Name          Description
=================== ===================================================================================================
 agromet             data about agrometeorological factors
 agrometdescriptor   information about specific factors
 cropdata            data about crops (area,production,yield)
 cropdescriptor      information about specific crop
 fertilizer          data about fertilizer usage
 cropstatus          thresholds for factor values related to a specific period of the year and crop
 marketprice         data about market prices for each disctrict
 waterflow           data about river water inflow at rim stations
 withdrawal          data about irrigation water supply
 measure units       database of the units of measure
=================== ===================================================================================================

On the same database you will find also some GeoSpatial data:

=================== ===================================================================================================
        Name          Description
=================== ===================================================================================================
 g0gen_pak           world national boundaries. Used as background on the map.
 national_boundary   national boundary Pakistan. Used as Pakistan boundary on the map.
 province_view       provinces in Pakistan.Used as province boundaries on the map.
 province_crop_map   provinces in Pakistan to generate crop maps.
 district_view       districts in Pakistan. Karachi area in 5 districts and frontier regions split from Districts. Used as district layer in TOC.
 district_crop       Karachi one polygon and frontier regions merged to KP districts. Used for selection where data are distributed in this way (e.g. crop data, fertilizer... ) .
 district_crop_map   districts in Pakistan to generate crop maps.
 district_select     Karachi area in 5 districts and frontier regions merged to KP districts. Used for selection where data are distributed in this way (agromet data...).
=================== ===================================================================================================

These

List of relations
*****************

measure_units
===============
Contains the unit of measure to use in the Crop Information Portal

schema
^^^^^^

========================   ====================================
 Column                     Description
========================   ====================================
 id                          the identifier for the unit of measure
 name                        the name to use for this unit of version
 shortname                   a short name for the unit of measure
 description                 a description of the unit of measure, just mnemonic.
 cls                         identify the class (area, production, yield, waterflow, withdrawal, denominator, exchangerate)
 filter                      a comma separated list of strings to filter wich units are allowed for which data
========================   ====================================

Sample Content
^^^^^^^^^^^^^^

here some sample rows from the *measure_units* table

================== =========================== ===========  ==================================================== ===========  =============  ============
id                     name                    shortname                       description                          cls       coefficient     filter
================== =========================== ===========  ==================================================== ===========  =============  ============
000_ha              000 hectares                000 ha       1000 hectares                                        area                   1
000_tons            000 tons                    000 tons     1000 tons                                            production             1
tons_ha             t/ha                        t/ha         tons / ha                                            yield                  1
kg_ha               kg/ha                       kg/ha        kg/ha                                                yield               1000
kg                  Kilograms                   kg           Kilograms                                            production       1000000
tons                tons                        tons         tonnes                                               production          1000
ha                  ha                          ha           hectare = 10000 sqm                                  area                1000
flow_cubicfeetsec   000 Cubic Feet per Second   000 Cusec    1 [000 Cusec] = 0.0283168467 [000 cubic meter/sec]   waterflow              1    irrigation
000_acres           000 acres                   000 a        1000 acres ( 1 a = 0.404678363 ha)                   area         2.471098263
acres               acres                       acres        acres = 404.678363 (000 ha)                          area          2471.09823
================== =========================== ===========  ==================================================== ===========  =============  ============

cropdescriptor
===============
Contains informations about specific crops.

schema
^^^^^^

========================   ====================================
 Column                     Description
========================   ====================================
 id                          the identifier for the crop
 label                       the label to display for this crop
 seasons                     the season(s) of this crop
 prod_default_unit           identify the default unit of measure for production data
 area_default_unit           identify the default unit of measure for area
 yield_default_unit          identify the default unit of measure for yield
========================   ====================================

.. note::
    The default unit of measure is the expected unit of measure in the input csv, and it is also the unit of measure selected on the UI startup.

Sample Content
^^^^^^^^^^^^^^

here some sample rows from the *cropdescriptor* table

=========== =========== ============= ==================  ==================  ==================
id          label       seasons        prod_default_unit  area_default_unit    yield_default_unit
=========== =========== ============= ==================  ==================  ==================
wheat       Wheat       RABI          000_tons            000_ha              kg_ha
rice        Rice        KHARIF        000_tons            000_ha              kg_ha
maize       Maize       KHARIF        000_tons            000_ha              kg_ha
soybean     Soybean     KHARIF        000_tons            000_ha              kg_ha
sugarcane   Sugarcane   KHARIF        000_tons            000_ha              kg_ha
cotton      Cotton      KHARIF        000_bales           000_ha              kg_ha
chickpea    Chickpea    RABI          tons                000_ha              kg_ha
fodder      Fodder      RABI,KHARIF   tons                000_ha              kg_ha
=========== =========== ============= ==================  ==================  ==================


cropdata
========

Contains the data about production, cultivated area and yield of every year. This table can be
populated ingesting the csv files.

Sample Content
^^^^^^^^^^^^^^
here some sample rows from the **cropdata** table

====== ============== =========== ====== =========== ======= ============ ======= ==========
 crop     district     province    year     years     area    production   yield  src
====== ============== =========== ====== =========== ======= ============ ======= ==========
 rice   Bahawalnagar   PUNJAB      2010   2010-11     63.13       127.35    2017  Official
 rice   Bahawalpur     PUNJAB      2010   2010-11      6.07        10.34    1703  Official
 rice   Bhakkar        PUNJAB      2010   2010-11      1.21         1.83    1512  Official
 rice   Chiniot        PUNJAB      2010   2010-11     32.37        63.55    1963  Official
====== ============== =========== ====== =========== ======= ============ ======= ==========

The crop column have to be the same of the id in **cropdescriptor** table.
*district* and *province* columns must match with district and province fields in the ***_crop** and ***_crop_map** tables.
Yield column is redoundant for compatibility reasons. the yield is calculated at runtime getting values from *area* and *production* fields.

.. note:: Unit of measure of data is now uniform for all the data.
          CSV data are converted at ingestion time in the database format.
          During the update the previous data (cotton production data) will be converted from the original format (000 bales) into the db format (000 tons)

        * **production** is expressed in thousands of tons.
        * **area** is always expressed in thousands of hectares
        * **yield** is always expressed in kg/ha.


agrometdescriptor
=================

The agrometdescriptor is a table that contains the list of the available factors.

schema
^^^^^^

==============  =================================================================================
   Column       Description
==============  =================================================================================
 factor          this is the string that identify the factor.
 label           this is the label that represents the factor in the application
 aggregation     this is the kind of aggregation method to apply. can be avg (average) or sum.
 unit            the unit of measure for the factor
==============  =================================================================================

Sample Content
^^^^^^^^^^^^^^

here some sample rows from the **agrometdescriptor** table


============ ================= ============= ======
   factor          label        aggregation   unit
============ ================= ============= ======
 Tmax_avg     Max Temperature   avg           °C
 NDVI_avg     NDVI              avg
 ppt_sum_mm   Precipitation     avg           mm
 Daylen_avg   Day length        avg           hr
 Tmin_avg     Min Temperature   avg           °C
============ ================= ============= ======

agromet
=======

Contains  agro-metereological data for each district of Pakistan for each  :term:`dekad`.

==============  =================================================================================
   Column       Description
==============  =================================================================================
 district       the district for this value
 province       the province for this value
 year           the year for this value
 month          the month for this value
 dec            the :term:`dekad` for this value
 factor         the factor for this value
 value          the factor for this value
 s_yr           the year of the start of the Rabi Season
 s_dec          the :term:`dekad` starting from the start of the Rabi Season
==============  =================================================================================

Sample Content
^^^^^^^^^^^^^^

here some sample rows from the *agromet* table

=========== ========== ====== ======= ===== ========== ========= ====== ======= ============== ==============
 district    province   year   month   dec    factor     value    s_yr   s_dec   absolute_dek   dek_in_year
=========== ========== ====== ======= ===== ========== ========= ====== ======= ============== ==============
 Malakand    KPK        2006   Oct       2   Tmin_avg   10.9563   2012      35  72238          22
 Mansehra    KPK        2006   Oct       2   Tmin_avg    10.999   2012      35  72238          22
 Nowshera    KPK        2006   Oct       2   Tmin_avg   11.3808   2012      35  72238          22
 Peshawar    KPK        2006   Oct       2   Tmin_avg    9.0622   2012      35  72238          22
 Mardan      KPK        2006   Oct       2   Tmin_avg   11.3221   2012      35  72238          22
 Shangla     KPK        2006   Oct       2   Tmin_avg   11.4006   2012      35  72238          22
 Swabi       KPK        2006   Oct       2   Tmin_avg   12.7756   2012      35  72238          22
 Swat        KPK        2006   Oct       2   Tmin_avg   10.5381   2012      35  72238          22
 Tank        KPK        2006   Oct       2   Tmin_avg   16.1516   2012      35  72238          22
=========== ========== ====== ======= ===== ========== ========= ====== ======= ============== ==============

.. note::
    * The *factor* column have to be the same of the id in **agrometdescriptor** table.
    * *district* and *province* columns must match with *district* and *province* columns in the **district_boundary** and **province_boundary** tables.
    * s_dec, s_yr and dek_in_year (:term:`dekad in year`) are redoundant field used to simplify queries to the database and make them faster.



crop status
===========

Contains limits and optimal values of agro-metereological values for each crop and each :term:`dekad` in year.

==============  =================================================================================
   Column       Description
==============  =================================================================================
factor          the agro-metereological variable.
crop            the crop to apply this limit
month           the month
dec             the  :term:`dekad`
max             the upper bound of the value
min             the lower bound of the value
opt             the optimal value
==============  =================================================================================

Sample Content
^^^^^^^^^^^^^^

here some sample rows from the *cropstatus* table

========   ===========   =====   ===   =====   ===   ===   ===
 factor       crop       month   dec   s_dec   max   min   opt
========   ===========   =====   ===   =====   ===   ===   ===
Tmax_avg   wheat         Jan       1       7    36     3    20
Tmax_avg   wheat         Jan       2       8    37     4    21
Tmax_avg   fake_crop_2   Apr       1      16    16    42    15
Tmax_avg   fake_crop_2   Apr       2      17    17    42    -5
Tmax_avg   fake_crop_2   Apr       3      18    18    41    17
Tmax_avg   fake_crop_2   Dec       1       4     4    42    16
Tmax_avg   fake_crop_2   Dec       2       5     5    41    12
Tmax_avg   fake_crop_2   Dec       3       6     6    40     1
Tmax_avg   fake_crop_2   Feb       1      10    10    30    21
Tmax_avg   fake_crop_2   Feb       2      11    11    30    22
========   ===========   =====   ===   =====   ===   ===   ===

fertilizers
============

Contains fertilizers data for each district of Pakistan for each month.

================   =================================================================================
   Column           Description
================   =================================================================================
province            the province
district            the district for data
year                the year
month               the month (e.g. Jun)
month_num           the month number (1-12)
nutrient            the fertilizer name
offtake_tons        the quantity (tons)
================   =================================================================================

.. note::
  the district is and province are optional. If the district is empty, the data is related to the whole province.
  If both province and district are empty, the data is for the whole Pakistan.

Sample Content
^^^^^^^^^^^^^^

here some sample rows from the *fertilizers* table

=== ================== ======= =========== ========== ============== ==================== =====
id       district       month   month_num   nutrient   offtake_tons        province        year
=== ================== ======= =========== ========== ============== ==================== =====
 3   Charsadda          Jan             1   Nitrogen         653.63   KHYBER PAKHTUNKHWA   2011
 4   Dera Ismail Khan   Jan             1   Nitrogen          47.08   KHYBER PAKHTUNKHWA   2011
 6   Lakki Marwat       Jan             1   Nitrogen         259.04   KHYBER PAKHTUNKHWA   2011
 7   Malakand           Jan             1   Nitrogen          43.41   KHYBER PAKHTUNKHWA   2011
 8   Mardan             Jan             1   Nitrogen        2829.47   KHYBER PAKHTUNKHWA   2011
 9   Nowshera           Jan             1   Nitrogen         875.56   KHYBER PAKHTUNKHWA   2011
10   Peshawar           Jan             1   Nitrogen        2438.76   KHYBER PAKHTUNKHWA   2011
11   Swabi              Jan             1   Nitrogen         294.57   KHYBER PAKHTUNKHWA   2011
12   Attock             Jan             1   Nitrogen         127.03   PUNJAB               2011
14   Bahawalpur         Jan             1   Nitrogen        5963.26   PUNJAB               2011
=== ================== ======= =========== ========== ============== ==================== =====


market_price
============

Contains market prices data for each district of Pakistan for each  :term:`dekad`.

================   =================================================================================
   Column           Description
================   =================================================================================
crop                the commodity for the price
decade              the  :term:`dekad`.
decade_absolute     :term:`absolute dekad`
decade_year         the dec in year
month               the mount of the price observation
year                the year of the price observation
province            the province where the price has been observed
district            the district where the price has been observed
market_price_kpr    the market price in rupies
market_price_usd    the market price in US dollars
================   =================================================================================

.. note::
   the market price

Sample Content
^^^^^^^^^^^^^^

here some sample rows from the *market_price* table

=============== ======   ===============   ===========   ============   =================  ================   ======  =====================  =====
     crop       decade   decade_absolute   decade_year    district      market_price_kpr   market_price_usd   month         province         year
=============== ======   ===============   ===========   ============   =================  ================   ======  =====================  =====
 wheat               1             72433             1    Islamabad                   65           0.638885    Jan     PUNJAB                2012
 rice irri-6         1             72433             1    Islamabad                  125           1.228625    Jan     PUNJAB                2012
 rice basmati        1             72433             1    Islamabad                  160            1.57264    Jan     PUNJAB                2012
 wheat               1             72469             1    Peshawar                  3480           34.20492    Jan     KHYBER PAKHTUNKHWA    2013
 wheat               1             72469             1    Bahawalpur                3175          31.207075    Jan     PUNJAB                2013
 wheat               1             72469             1    Faisalabad                3330           32.73057    Jan     PUNJAB                2013
 wheat               1             72469             1    Gujranwala                3340           32.82886    Jan     PUNJAB                2013
 wheat               1             72469             1    Lahore                    3425          33.664325    Jan     PUNJAB                2013
 wheat               1             72469             1    Multan                    3280           32.23912    Jan     PUNJAB                2013
 wheat               1             72469             1    Islamabad                 82.5          0.8108925    Jan     PUNJAB                2013
=============== ======   ===============   ===========   ============   =================  ================   ======  =====================  =====



waterflow
=========

Contains  waterflow data about river water inflow at rim stations

===============  =================================================================================
   Column        Description
===============  =================================================================================
decade           the :term:`dek` of the observation
decade_absolute  the :term:`absolute dekad` of the observation
decade_year      the :term:`dekad in year` of the observation
month            the month of the observation
river            the river
waterflow        the value
year             the year of the observation
===============  =================================================================================


Sample Content
^^^^^^^^^^^^^^

here some sample rows from the *waterflow* table

=======  =================  ============= ========= ======================== =============  ======
 decade   decade_absolute    decade_year    month             river             waterflow    year
=======  =================  ============= ========= ======================== =============  ======
     1              72469              1    Jan      Indus River at Chashma         21.22    2013
     2              72470              2    Jan      Indus River at Chashma         33.75    2013
     3              72471              3    Jan      Indus River at Chashma         46.28    2013
     1              72472              4    Feb      Indus River at Chashma         59.89    2013
     2              72473              5    Feb      Indus River at Chashma         58.81    2013
     3              72474              6    Feb      Indus River at Chashma         57.72    2013
     1              72475              7    Mar      Indus River at Chashma         52.54    2013
     2              72476              8    Mar      Indus River at Chashma         56.63    2013
     3              72477              9    Mar      Indus River at Chashma         60.72    2013
     1              72478             10    Apr      Indus River at Chashma         42.02    2013
=======  =================  ============= ========= ======================== =============  ======

withdrawal
==========

Contains data about irrigation water supply for each district

===============  =================================================================================
   Column        Description
===============  =================================================================================
decade           the :term:`dekad` of the observation
decade_absolute  the :term:`absolute dekad` of the observation
decade_year      the :term:`dekad in year` of the observation
month            the month of the observation
river            the river
waterflow        the value
year             the year of the observation
===============  =================================================================================


Sample Content
^^^^^^^^^^^^^^

here some sample rows from the *waterflow* table

=======  =================  ============= ========= ======================== ============ =============  ======
 decade   decade_absolute   decade_year      month        province             district      withdrawal   year
=======  =================  ============= ========= ======================== ============ =============  ======
    1               72508              4       Feb   KHYBER PAKHTUNKHWA                              0    2014
    1               72544              4       Feb   KHYBER PAKHTUNKHWA                              0    2015
    1               72388             28       Oct   SINDH                                      1.2777    2010
    1               72388             28       Oct   KHYBER PAKHTUNKHWA                          0.007    2010
    1               72388             28       Oct   BALOCHISTAN                                0.0313    2010
    2               72389             29       Oct   PUNJAB                                     1.6847    2010
    1               72397              1       Jan   KHYBER PAKHTUNKHWA                         0.0193    2011
    1               72397              1       Jan   BALOCHISTAN                                0.0697    2011
    2               72398              2       Jan   PUNJAB                                     0.3113    2011
=======  =================  ============= ========= ======================== ============ =============  ======

.. note::
  the district is and province are optional. If the district is empty, the data is related to the whole province.
  If both province and district are empty, the data is for the whole Pakistan.
