.. module:: cippak.admin.crop_agro
   :synopsis: Learn about how to manage Crops and Agromet factors.

.. _cippak.admin.crop_agro:

=========================
Crops and Agromet factors 
=========================

Manage Crops
============
To manage Crops you can click on the **Crops** link in the navigation bar or click on this link:

http://localhost:8083/admin/crops

.. figure:: img/crop_list.png

    The list of available crops

-----------------
Create a new Crop
-----------------

To create a new crop, click on the **Create** button. 
A form with the information to set will be displayed. 
complete the form as follows:

.. figure:: img/create_win.png

    Sample Soybean crop
    
The new crop will be shown in the Crops list.

.. figure:: img/crop_list2.png

    The new crop is added to the list
    
Anyway the new crop will not be available in MapStore until some data about the crop is ingested.

^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
How to allow map generation for new Crops
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

To generate maps you **must** add 6 new styles to geoserver with a specific name::

    <province|district>_<crop_id_lower_case>_<area|prod|yield>_style

In the case above the styles to add will have the following names:

* ``province_soybean_area_style``
* ``province_soybean_prod_style``
* ``province_soybean_yield_style``
* ``district_soybean_area_style``
* ``district_soybean_prod_style``
* ``district_soybean_yield_style``

.. note:: This styles are needed to generate maps in the *Crop Data* module of *MapStore*.

        * Area Type: National(Province)
            * ``province_soybean_area_style.xml``:  style to generate maps for area.
            * ``province_soybean_prod_style.xml``:  style to generate maps for production.
            * ``province_soybean_yield_style.xml``: style to generate maps for yield.
        
        * Area Type: National(District) or Province(District)
            * ``district_soybean_area_style.xml``:  style to generate maps for area .
            * ``district_soybean_prod_style.xml``:  style to generate maps for production.
            * ``district_soybean_yield_style.xml``: style to generate maps for yield.

+++++++++++++++
Add a SLD style
+++++++++++++++

You can access to the style layer descriptor (SLD) definition page on GeoServer with `this link <http://localhost:8080/geoserver/web/?wicket:bookmarkablePage=:org.geoserver.wms.web.data.StyleNewPage>`_ or selecting *Styles* module on GeoServer:

.. figure:: img/styles.png

    Styles module on GeoServer

and press on *add a new style*

.. figure:: img/styles_add.png

    Add a new style button

Now, you have to fill the form with the new name, 

for this exercise you can copy a style from the existing ones. :

.. figure:: img/styles_copy.png

    Copy *province_cotton_area_style*

change the style with the new name and modify filters as you want:

copy from one of the present one:

.. figure:: img/styles_edit.png

    Edit the style

validate and submit the new style:

.. figure:: img/styles_submit.png

    Styles submit

To see a complete example of how to add a crop see :ref:`cippak.admin.crop_agro.complete`.


-----------------
Edit/Delete Crops
-----------------

You can edit/delete the crops clicking on the **Edit** and **Delete** buttons on the right of each row.

Manage Agrometeorological Factors
=================================
To manage Agrometeorological Factors you can click on the **Agromet Factors** link in the navigation bar or click on this link:

http://localhost:8083/admin/agromet/

.. figure:: img/factor_list.png

    The list of available factors

-------------------
Create a new Factor
-------------------

To create a new factor, click on the **Create** button. 
A form with the information to set will be displayed. 
complete the form as follows:

.. figure:: img/factor_create.png

    Sample Evapotranspiration factor
    
The new factor will be shown in the Crops list.

.. figure:: img/factor_list2.png

    The new factor is added to the list
    
Anyway the new factor will not be available in MapStore until some data about the factor is ingested.

-------------------
Edit/Delete Factors
-------------------

You can edit/delete the factor clicking on the **Edit** and **Delete** buttons on the right of each row.

.. note:: ``NDVI_avg`` is used in a special way from the system, so it should never be deleted. otherwise the ingestion of
          generated statistics from the NDVI statistics module will not work. 

================
Complete example
================

You can follow a complete example adding the data and styles and testing it on mapstore

.. toctree::
   :maxdepth: 1

   complete example <complete>