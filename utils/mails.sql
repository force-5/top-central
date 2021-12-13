select
    asynchronous_mail_message.id,
    asynchronous_mail_message.create_date,
    asynchronous_mail_message.subject,
    (select count(*) from asynchronous_mail_message_to where asynchronous_mail_message_id = asynchronous_mail_message.id) as number_of_to_recipients,
    (select count(*) from asynchronous_mail_message_cc where asynchronous_mail_message_id = asynchronous_mail_message.id) as number_of_cc_recipients
from
     asynchronous_mail_message
order by
    asynchronous_mail_message.id;
