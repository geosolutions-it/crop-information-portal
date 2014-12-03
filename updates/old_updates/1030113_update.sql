--
-- Script to repair corrupt data
--
-- Affected tables:
--
-- *  cropdata: Remove unused columns and put correct ids. Put not null on column id.
-- *  agromet: Remove rows without value and put correct ids. Put not null on columns.
-- *  cropstatus: Put correct rowids and not null on rowid column.
--
-- @author adiaz
 
-- #########################
-- #### Crop data table ####
-- #########################
 
-- update crop data ids
select count(*) from cropdata where id is null;
-- count != 0
UPDATE cropdata 
SET id = nextval('hibernate_sequence') where id is null;
select count(*) from cropdata where id is null;
-- count = 0
 
-- add not null and unique to id
alter table cropdata ALTER COLUMN id SET NOT NULL;
alter table cropdata ADD CONSTRAINT cropdata_unique_id UNIQUE (id);
 
-- Remove unused columns
alter table cropdata DROP COLUMN rowid;
alter table cropdata drop column cropdescriptor;
 
-- #######################
-- #### Agromet table ####
-- #######################
 
-- Agromet incorrect rows: value cannot be null!
select count(*) from agromet where value is null;
-- count != 0
delete from agromet where value is null;
select count(*) from agromet where value is null;
-- count = 0
 
-- update agromet table ids
select count(*) from agromet where id is null;
-- count != 0
UPDATE agromet 
SET id = nextval('hibernate_sequence') where id is null;
select count(*) from agromet where id is null;
-- last count = 0
 
-- add not null and unique to id
alter table agromet ALTER COLUMN id SET NOT NULL;
alter table agromet ADD CONSTRAINT agromet_unique_id UNIQUE (id);
 
-- other not null columns
alter table agromet ALTER COLUMN year SET NOT NULL;
alter table agromet ALTER COLUMN dec SET NOT NULL;
alter table agromet ALTER COLUMN s_yr SET NOT NULL;
alter table agromet ALTER COLUMN s_dec SET NOT NULL;
alter table agromet ALTER COLUMN value SET NOT NULL;
 
-- Remove unused rowid
alter table agromet DROP COLUMN rowid;
 
-- ###########################
-- #### Crop status table ####
-- ###########################
 
-- update crop status ids
select count(*) from cropstatus where rowid is null;
-- count != 0
UPDATE cropstatus 
SET rowid = nextval('hibernate_sequence') where rowid is null;
select count(*) from cropstatus where rowid is null;
-- count = 0
 
-- add not null and unique to rowid
alter table cropstatus ALTER COLUMN rowid SET NOT NULL;
alter table cropstatus ADD CONSTRAINT cropstatus_unique_id UNIQUE (rowid);
