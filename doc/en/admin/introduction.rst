.. module:: cippak.admin.intro
   :synopsis: Introduction to the Administration Page

.. _cippak.admin.intro:

***********************
The Administration Page
***********************
    
| The portal provides a lot of data to facilitate the analysis of the state of the crops in Pakistan.
| These data must be continuously updated, and, where possible, automatically.
| To allow mantainence operations, the portal has an administration page accessible from the User drop-down menu. 

The tasks that can be performed are:

* administration of registered users for data download
* creation of new crops/agromet factors
* ingestion of new data or update existing values
* insertion of new NDVI images
* extraction of mean NDVI values per district/province using (optional) a crop mask.

Login
=====

| To access the administration area, you need to have an account of administrator.
| Click on the Login button in the toolbar ( |loginbutton| ) and insert the account parameters:

    .. figure:: img/admin_02.jpg

        Login Form.
    
.. |loginbutton| image:: img/admin_01.jpg

.. note:: Only the users with the ADMIN role can access to the administration interface.

The default admin credentials are 

    * **user**:admin
    * **password**:admin

Now you will have the option to access the reserved Administration Page.

    .. figure:: img/admin_03.jpg

        Administration Page option.

User interface and tasks
========================

| For details on the tasks performed, procedures and practical examples, refer to the Administration manual.
| Here is an overview of look and feel and functionalities.

All tasks are distributed in four main groups:

    .. figure:: img/admin_04.jpg

        Administration Tools.

Workflows
---------

| Interface to manage data.
| Database updates are conducted running tasks in this section.

    .. figure:: img/admin_05.jpg

        Workflows tool.

Any tasks performed in the workflows group are logged and can be retrieved for analysis of results.

    .. figure:: img/admin_06.jpg

        Workflows tool.

User manager
------------

| Interface to manage users.
| An user can have roles of guest (data download) and administration.

    .. figure:: img/admin_07.jpg

        User Manager.

File manager
------------

Data and service files are uploaded and managed; for example the database is updated starting from CSV files uploaded here.

    .. figure:: img/admin_08.jpg

        File manager.
        
Entities manager
----------------

Crop and agromet variables are configured; units of measures are introduced and assigned to commodities or variables.

    .. figure:: img/admin_09.jpg

        File manager.
