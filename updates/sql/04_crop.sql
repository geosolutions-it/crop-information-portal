-- *************************************************
-- Modify the CROPDESCRIPTOR tables to declare unit class and
-- unit
-- *************************************************
ALTER TABLE cropdescriptor
--The base unit 
ADD COLUMN prod_default_unit varchar(20),
ADD COLUMN area_default_unit varchar(20),
ADD COLUMN yield_default_unit varchar(20) ;
-- TODO evaluate if a unit class for each value is needed

--associate the unit with the units
UPDATE cropdescriptor SET prod_default_unit = '000_tons' where id <> 'cotton';
UPDATE cropdescriptor SET prod_default_unit = '000_bales' where id = 'cotton';
UPDATE cropdescriptor SET area_default_unit = '000_ha';
UPDATE cropdescriptor SET yield_default_unit = 'kg_ha';
  
-- *************************
-- *  Table: cropdata      *
-- *************************

ALTER TABLE cropdata ADD COLUMN src character varying(255);
ALTER TABLE cropdata DROP CONSTRAINT cropdata_pkey;
ALTER TABLE cropdata ADD CONSTRAINT cropdata_pkey PRIMARY KEY (id);
ALTER TABLE cropdata ADD CONSTRAINT cropdata_crop_key UNIQUE (crop, district, province, year, src);
UPDATE cropdata SET src='Official';