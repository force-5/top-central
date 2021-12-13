-- Remove NULL constraint from period
alter table careTest.dbo.certification alter column period int null;
go

-- Remove NULL constraint from period_unit
alter table careTest.dbo.certification alter column period_unit varchar(255) null;
go

into careTest.dbo.alert_action_template (version, action_type, template) values (0, 'UPDATE_CERTIFICATION', '<br/>To update certification, click on link below:<br/>');
go