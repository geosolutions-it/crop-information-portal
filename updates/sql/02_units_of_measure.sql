-- *************************************************************************
-- UNIT OF MEASURE TABLE
-- contains the available unit of measuers, divided by classes
-- and the releated coefficients for the available units of measure_units
-- you have to multiply the values with the coefficient to convert from the 
-- database unit to wanted units
-- *************************************************************************

DROP TABLE IF EXISTS measure_units;

create table measure_units (
 -- the name to use as key for this unit
 id character varying(20) NOT NULL,
 -- the label to use
 name character varying(255) NOT NULL,
 -- short name
 shortname character varying(20) NOT NULL,
 -- description for this unit
 description character varying(255) NOT NULL,
 -- class 
 class character varying(20) NOT NULL,
 -- the multiplyer for the base unit for the class
 coefficient double precision,
 filter varchar(255),
 CONSTRAINT measure_units_key PRIMARY KEY (id)
 );
 -- if filter doesn't exists
 -- ALTER table measure_units add column filter varchar(255);

-- *************************************************
-- INITIAL VALUES FOR UOM
-- *************************************************

-- the values on crop production are all in 000 of tons
-- except Cotton that is 000 bales


INSERT INTO measure_units VALUES ('000_acres', '000 acres', '000 a', '1000 acres ( 1 acre = 0.404678363 hectares)', 'area', 0.404678363, '');
INSERT INTO measure_units VALUES ('kg_ha', 'kg/ha', 'kg/ha', 'kg/ha', 'yield', 1000, '');
INSERT INTO measure_units VALUES ('000_ha', '000 hectares', '000 ha', '1000 hectares', 'area', 1, '');
INSERT INTO measure_units VALUES ('000_tons', '000 tons', '000 tons', '1000 tons', 'production', 1, '');
INSERT INTO measure_units VALUES ('000_maunds', '000 maunds', '000 m', '1000 maunds (1 maund = 40kg)', 'production', 0.04, 'cotton');
INSERT INTO measure_units VALUES ('000_bales', '000 bales', '000_bales', '1000 bales (1 bale = 170 kg)', 'production', 0.17, 'cotton');
INSERT INTO measure_units VALUES ('tons_ha', 't/ha', 't/ha', 'tons / ha', 'yield', 1, '');
-- INSERT INTO measure_units VALUES ('kg', 'Kilograms', 'kg', 'Kilograms', 'production', 0.001, '');

