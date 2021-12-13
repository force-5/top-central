select
    asynchronous_mail_message.id,
    subject,
    text,
    (select count(*) from asynchronous_mail_message_to where asynchronous_mail_message_id = asynchronous_mail_message.id) as number_of_to_recipients,
    (select count(*) from asynchronous_mail_message_cc where asynchronous_mail_message_id = asynchronous_mail_message.id) as number_of_cc_recipients
from
    asynchronous_mail_message
where
    id = @mailId;

select
    asynchronous_mail_message_to.to_string
from
     asynchronous_mail_message_to
where
    asynchronous_mail_message_to.asynchronous_mail_message_id = @mailId;

select
    asynchronous_mail_message_cc.cc_string
from
     asynchronous_mail_message_cc
where
    asynchronous_mail_message_cc.asynchronous_mail_message_id = @mailId;




