-- *************************************************************************
-- Agromet
-- The changes on the agromet values are :
-- new column absolute_dek
-- new column dek_in_year
-- *************************************************************************

ALTER TABLE agromet ADD COLUMN absolute_dek integer NOT NULL default 0;
ALTER TABLE agromet ADD COLUMN dek_in_year integer NOT NULL default 0;
update agromet
set dek_in_year = dec 
where month='Jan';

update agromet
set dek_in_year = dec +3
where month='Feb';

update agromet
set dek_in_year = dec + 6
where month='Mar';

update agromet
set dek_in_year = dec + 9
where month='Apr';

update agromet
set dek_in_year = dec +12
where month='May';

update agromet
set dek_in_year = dec +15
where month='Jun';

update agromet
set dek_in_year = dec +18
where month='Jul';

update agromet
set dek_in_year = dec +21
where month='Aug';

update agromet
set dek_in_year = dec +24
where month='Sep';

update agromet
set dek_in_year = dec +27
where month='Oct';

update agromet
set dek_in_year = dec +30
where month='Nov';

update agromet
set dek_in_year = dec +33
where month='Dec';

UPDATE agromet set absolute_dek = year *36 + dek_in_year;