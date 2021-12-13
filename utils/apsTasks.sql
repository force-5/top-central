select
    aps_workflow_task.date_created,
    aps_workflow_task.actor_slid,
    aps_workflow_task.effective_start_date,
    aps_workflow_task.escalation_template_id,
    aps_workflow_task.message,
    aps_workflow_task.period,
    aps_workflow_task.period_unit,
    aps_workflow_task.response,
    aps_workflow_task.status,
    aps_workflow_task.type,
    aps_workflow_task.workflow_type,
    aps_workflow_task_permitted_slid.slid
from
     aps_workflow_task,
     aps_workflow_task_permitted_slid
where
     aps_workflow_task.id = aps_workflow_task_permitted_slid.task_id
order by
     date_created;