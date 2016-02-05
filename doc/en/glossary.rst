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

    json
    JSON
      JSON (JavaScript Object Notation) is an open standard format that uses human-readable text to transmit data objects consisting of attribute–value pairs. It is the most common data format used for asynchronous browser/server communication (AJAJ), largely replacing XML which is used by AJAX.

    CRUD
      CRUD ()**C**reate **R**ead **U**pdate **D**elete) are the four basic functions of a persist storage.

    ReST
    REST
      **Re**presentational **S**tate **T**ransfer (ReST) is the software architectural style of the World Wide Web. (`ref <https://en.wikipedia.org/wiki/Representational_state_transfer>`)

    shape file
    shapefile
      The The shapefile format is a popular geospatial vector data format for
      geographic information system (GIS) software. It is developed and
      regulated by Esri as a (mostly) open specification for data
      interoperability among Esri and other GIS software products.
      The shapefile format can spatially describe vector features like:
        * points
        * lines
        * polygons
