-- This script will fix hibernate sequence value and update the agromet and cropdata tables to prevent delete issues

select setval('hibernate_sequence', max(mymax)) from
(
select max(id) as mymax from agromet
union
select max(id) as mymax from cropdata 
union
select max(rowid) as mymax from cropstatus
)as t1;

update agromet as tomodify
set id = nextval('hibernate_sequence') 
from
(select year, id, max(year) over (partition by id) as maxyear
from agromet
where id in 
(select id from
	(select id, nid from
		(select id, count(id) as nid 
		from agromet
		group by id ) as t1
	where nid > 1 ) as t2
)) as t3
where tomodify.id = t3.id and tomodify.year = t3.maxyear ;

update cropdata as tomodify
set id = nextval('hibernate_sequence') 
from
(select year, id, max(year) over (partition by id) as maxyear
from cropdata
where id in 
(select id from
	(select id, nid from
		(select id, count(id) as nid 
		from cropdata
		group by id ) as t1
	where nid > 1 ) as t2
)) as t3
where tomodify.id = t3.id and tomodify.year = t3.maxyear ;

/*
update cropstatus as tomodify
set rowid = nextval('hibernate_sequence') 
from
(select max, rowid, max(max) over (partition by rowid) as maxyear
from cropstatus
where rowid in 
(select rowid from
	(select rowid, nid from
		(select rowid, count(rowid) as nid 
		from cropstatus
		group by rowid ) as t1
	where nid > 1 ) as t2
)) as t3
where tomodify.rowid = t3.rowid and tomodify.year = t3.maxyear ;
*/