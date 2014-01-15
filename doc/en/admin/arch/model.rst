.. module:: cippak.admin.arch.model
   :synopsis: Learn about how to configure Crop Information Portal Model.

.. _cippak.admin.arch.model:

********
Model
********

The NRL database contains the main model

=================== ===================================================================================================
       Name          Description
=================== ===================================================================================================
 agromet             data about agrometeorological factors
 agrometdescriptor   information about specific factors
 cropdata            data about crops (area,production,yield)
 cropdescriptor      information about specific crop
 cropstatus          thresholds for factor values related to a specific period of the year and crop
 district_boundary   districts in Pakistan 
 district_crop       districts in Pakistan with some differences in districts that matches with provided data about crops.
 national_boundary   national boundary Pakistan. 
 province_boundary   provinces in Pakistan
 province_crop       provinces in Pakistan to generate crop maps.
=================== ===================================================================================================
  
   
List of relations
*****************
 
 
cropdescriptor
===============
Contains informations about specific crops.

schema
^^^^^^

========   ====================================
 Column    Description
========   ====================================
 id        the identifier for the crop
 label     the label to display for this crop
 seasons   the season(s) of this crop
========   ====================================
 

Sample Content 
^^^^^^^^^^^^^^

here some sample rows from the *cropdescriptor* table

=========== =========== =============
     id         label       seasons
=========== =========== =============
 rice        Rice        KHARIF
 cotton      Cotton      KHARIF
 maize       Maize       KHARIF
 fodder      Fodder      RABI,KHARIF
 wheat       Wheat       RABI
 sugarcane   Sugarcane   KHARIF
=========== =========== =============

cropdata
========

Contains the data about production, cultivated area and yield of every year. This table can be 
populated ingesting the csv files.

Sample Content 
^^^^^^^^^^^^^^
here some sample rows from the *cropdata* table

 ====== ============== =========== ====== =========== ======= ============ ======= ======== 
  crop     district     province    year     years     area    production   yield     id     
 ====== ============== =========== ====== =========== ======= ============ ======= ======== 
  rice   Bahawalnagar   PUNJAB      2010   2010-11     63.13       127.35    2017    73930  
  rice   Bahawalpur     PUNJAB      2010   2010-11      6.07        10.34    1703    73931  
  rice   Bhakkar        PUNJAB      2010   2010-11      1.21         1.83    1512    73932  
  rice   Chiniot        PUNJAB      2010   2010-11     32.37        63.55    1963    73934  
 ====== ============== =========== ====== =========== ======= ============ ======= ======== 

The crop column have to be the same of the id in *cropdescriptor* table.
*district* and *province* columns must match with district and province fields in the *district_crop* and *province_crop* tables.
Yield column is redoundant for compatibility reasons. the yield is calculated at runtime getting values from *area* and *production* fields.

.. note:: Unit of measure are not uniform to mantain the original format of the CSV files.

        * **production** is expressed in thousands of tons for all crops except cotton which is expressed in thousands of bales.
        * **area** is always expressed in thousands of hectares (ha)
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
 
here some sample rows from the *agrometdescriptor* table

 
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

Contains  agro-metereological data for each district of Pakistan for each :term:dekad.

==============  =================================================================================
   Column       Description
==============  =================================================================================  
 district       the district for this value
 province       the province for this value
 year           the year for this value
 month          the month for this value
 dec            the dekad for this value
 factor         the factor for this value
 value          the factor for this value
 s_yr           the year of the start of the Rabi Season
 s_dec          the dekad starting from the start of the Rabi Season
==============  =================================================================================  

Sample Content
^^^^^^^^^^^^^^

here some sample rows from the *agromet* table

=========== ========== ====== ======= ===== ========== ========= ====== ======= 
 district    province   year   month   dec    factor     value    s_yr   s_dec  
=========== ========== ====== ======= ===== ========== ========= ====== ======= 
 Malakand    KPK        2012   Oct       2   Tmin_avg   10.9563   2012      35  
 Mansehra    KPK        2012   Oct       2   Tmin_avg    10.999   2012      35  
 Mardan      KPK        2012   Oct       2   Tmin_avg   11.3221   2012      35  
 Nowshera    KPK        2012   Oct       2   Tmin_avg   11.3808   2012      35  
 Peshawar    KPK        2012   Oct       2   Tmin_avg    9.0622   2012      35  
 Shangla     KPK        2012   Oct       2   Tmin_avg   11.4006   2012      35  
 Swabi       KPK        2012   Oct       2   Tmin_avg   12.7756   2012      35  
 Swat        KPK        2012   Oct       2   Tmin_avg   10.5381   2012      35  
 Tank        KPK        2012   Oct       2   Tmin_avg   16.1516   2012      35  
=========== ========== ====== ======= ===== ========== ========= ====== =======

.. note::
    * The *factor* column have to be the same of the id in *agrometdescriptor* table.
    * *district* and *province* columns must match with *district* and *province* columns in the *district_boundary* and *province_boundary* tables.
    * s_dec and s_yr are redoundant field used to simplify queries to the database and make them faster.


 
 