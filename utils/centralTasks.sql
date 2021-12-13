select
    central_workflow_task.date_created,
    central_workflow_task.actor_slid,
    central_workflow_task.effective_start_date,
    central_workflow_task.escalation_template_id,
    central_workflow_task.message,
    central_workflow_task.period,
    central_workflow_task.period_unit,
    central_workflow_task.response,
    central_workflow_task.status,
    central_workflow_task.type,
    central_workflow_task.workflow_type,
    central_workflow_task_permitted_slid.slid
from
     central_workflow_task,
     central_workflow_task_permitted_slid
where
     central_workflow_task.id = central_workflow_task_permitted_slid.task_id
order by
     date_created;