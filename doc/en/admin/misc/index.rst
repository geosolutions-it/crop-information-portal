.. module:: cippak.admin.misc
   :synopsis: Learn about how flow status and operation list modules.

.. _cippak.admin.misc:

Flow Status
===========

.. note::
    * If you execute an action from a file browser, it store the last execution for the file
    * From another operations (NDVI Statistics) you must save the *Consumer ID*

When you execute one operation in the administration portal, you run this operation on GeoBatch. 

Then you can see the status of the execution following the link

.. figure::  resources/execution.png  
   :align:   center

   CSV execution.


and show the log pressing on '*Get status*' button on the next page. 

.. figure::  resources/get_status.png	
   :align:   center

   Get status button.

The log of the execution will be shown in the page: 

.. figure::  resources/success.png  
   :align:   center

   Message log.

The id of the flow is **volatile**. It seems if you switch the page after the execution, you must save the id if you want to access again to the log page.

If you have saved a ``Consumer ID`` and you want to see the log, press on ``Check Flow Status`` on the navigation bar, 

.. figure::  resources/get_status_module.png	
   :align:   center

   Get status module.

fill the form 

.. figure::  resources/consumer_id.png	
   :align:   center

   Consumer ID parameter.

and press on *Get Status* button.

.. figure::  resources/get_status.png	
   :align:   center

   Get status button.

The log will appear again on your browser.   

Operation List
==============

.. figure::  resources/op_list_module.png	
   :align:   center

   Operation lis module.

This module show a resume of available operations on the application. For each availabel operation will show:

* **Name**: Name of the operation
* **REST path**: Used to access to the view of the operation and interact with this.
* **File Action**: Indicates if the file can be executed with a file.