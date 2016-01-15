-- *************************************************************************
-- Align Unit of measure
-- The changes on the agromet values are :
-- Unit of measure for cotton are scaled from 000 bales to 000 tons
-- *************************************************************************

update cropdata 
set production = x.value
from (
select *,  production * 170 /1000 as value from cropdata  where crop = 'cotton' ) as x
where cropdata.id = x.id;