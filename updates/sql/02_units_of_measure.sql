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
 cls character varying(20) NOT NULL,
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

INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('000_ha', '000 hectares', '000 ha', '1000 hectares', 'area', 1, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('000_tons', '000 tons', '000 tons', '1000 tons', 'production', 1, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('tons_ha', 't/ha', 't/ha', 'tons / ha', 'yield', 1, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('kg_ha', 'kg/ha', 'kg/ha', 'kg/ha', 'yield', 1000, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('kg', 'Kilograms', 'kg', 'Kilograms', 'production', 1000000, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('tons', 'tons', 'tons', 'tonnes ', 'production', 1000, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('ha', 'ha', 'ha', 'hectare = 10000 sqm', 'area', 1000, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('flow_cubicfeetsec', '000 Cubic Feet per Second', '000 Cusec', '1 [000 Cusec] = 0.0283168467 [000 cubic meter/sec]', 'waterflow', 1, 'irrigation');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('000_acres', '000 acres', '000 a', '1000 acres ( 1 a = 0.404678363 ha)', 'area', 2.47109826300000002, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('acres', 'acres', 'acres', 'acres = 404.678363 (000 ha)', 'area', 2471.09823000000006, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('flow_cubicmsec', '000 Cubic Meters per Second', '000 cubic meter/sec', '1 [000 Cusec] = 0.0283168467 [000 cubic meter/sec]', 'waterflow', 0.0283168466999999986, 'irrigation');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('currency_1kg', '1 Kg', '1', '', 'denominator', 0.0100000000000000002, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('supply_maf', 'Million Acre Foot', 'million acre foot', '1 [MAF] = 1233.481855 [million cubic meter]', 'watersupply', 1, 'irrigation');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('supply_cubicm', 'Million Cubic Meter', 'million cubic meter', '1 [MAF] = 1233.481855 [million cubic meter]', 'watersupply', 1233.481855, 'irrigation');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('000_bales', '000 bales', '000_bales', '1000 bales (1 bale = 170 kg)', 'production', 5.88235294099999972, 'cotton');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('000_maunds', '000 maunds', '000 m', '1000 maunds (1 maund = 37.324kg)', 'production', 26.7924123899999991, 'cotton');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('mds_acr', 'm/a', 'm/a', 'maunds/acres', 'yield', 10.8423239500000008, 'cotton');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('ton_acre', 't/a', 't/a', 'Tons / acres', 'yield', 0.404678079000000024, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('USD', 'US Dollars', 'USD', 'dollars', 'currency', 1, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('currency_100kg', '100 Kg', '100', 'factor to compute price for a unit of 100 kg weight', 'denominator', 1, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('currency_40kg', '40 Kg', '40', 'factor to compute price for a unit of 40 kg weight', 'denominator', 0.400000000000000022, '');
INSERT INTO measure_units (id, name, shortname, description, cls, coefficient, filter) VALUES ('pkr_usd', '0.009829 [PKR/USD]', '0.009829', '1 PRK = 0.009829 USD', 'exchangerate', 0.0098289999999999992, '');

