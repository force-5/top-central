select
    asynchronous_mail_message.id,
    asynchronous_mail_message.create_date,
    asynchronous_mail_message.subject ,
    SUBSTRING(asynchronous_mail_message.text, 20, 100),
    asynchronous_mail_message_to.to_string
from
     asynchronous_mail_message left join asynchronous_mail_message_to on (asynchronous_mail_message.id = asynchronous_mail_message_to.asynchronous_mail_message_id)
order by
    asynchronous_mail_message.id;
