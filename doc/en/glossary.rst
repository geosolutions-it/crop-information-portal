.. module:: cippak.glossary
   :synopsis: Crop Information Portal glossary.

.. _cippak.glossary:

Glossary
--------

.. glossary::

    dek
    dekad
      The **dekad** is the base time unit of most of the data present on the Crop Information Portal. It is a 10 day period used to approximate the year to a 360 period, with each month is 30 days (3 dekads) long.
      For example we refer to the 3rd dekad of January we mean the period between Jan 21st and Jan 31st.

    dekad in year
      The :term:`dekad` from the start of the year ( from 1 to 36 ) . i.e. Feb 2nd dekad => 4th dekad in year
      Formula: `dek_in_year = MOD(absolute_dek - 1 , 36) + 1`

    absolute dekad
      the absolute value of the :term:`dekad` from the year 0.
      Formula: `absolute_dekad = year * 36 + dekad_in_year`.

    GeoCoder
    Geocoding
      Geocoding (sometimes called forward geocoding) uses a description of a location, most typically
      a postal address or place name, to find geographic coordinates. The GeoCoder of the application use
      Google and Nominatim services to find the address. 
