select
  w.id,
  wp.slid as Worker_SLID, 
  sp.slid as Supervisor_SLID
from
  worker w, 
  person wp,
  person sp, 
  employee_supervisor s
where
  w.person_id = wp.id and
  s.person_id = sp.id and
  w.employee_supervisor_id = s.id and 
  wp.slid <> sp.slid
order by
  wp.slid;
