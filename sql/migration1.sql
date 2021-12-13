-- Drop the tables that are not being used, these were created by the old domain classes which were refactored/removed later.
drop table careTest.dbo.alert_escalation_level;
drop table careTest.dbo.alert;
drop table careTest.dbo.escalation_level_escalation_role;
drop table careTest.dbo.escalation_level;
drop table careTest.dbo.escalation_role;

-- Add a SLID field to BUSINESS_UNIT_REQUESTER table
alter table careTest.dbo.business_unit_requester add slid varchar(255);
go
-- Set some default & unique value in the SLID field
update careTest.dbo.business_unit_requester set slid = id ;
go
-- Enable the unique contraint on the SLID field
alter table careTest.dbo.business_unit_requester add constraint unique_bur_slid unique (slid);
go

-- Delete the records from CONTACT_ROLE table which are not CcGatekeepers
-- Earlier, we were creating a record in contact_role table for contractors and business unit requesters to reuse
-- the contact object for different roles. We have removed it from the code now, & these records are "orphan"
delete from careTest.dbo.contact_role where role_id != 3

-- Add a SLID field to CONTACT_ROLE table
alter table careTest.dbo.contact_role add slid varchar(255);
go
-- Set some default & unique value in the SLID field
update careTest.dbo.contact_role set slid = id ;
go
-- Enable the unique contraint on the SLID field
alter table careTest.dbo.contact_role add constraint unique_cr_slid unique (slid);
go
