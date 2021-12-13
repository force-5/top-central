import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.workflow.CentralWorkflowTaskType

List<CentralWorkflowTask> tasks = CentralWorkflowTask.findAllByNodeName('Initial Task')

tasks.each { CentralWorkflowTask task ->
    task.type = CentralWorkflowTaskType.SYSTEM_CENTRAL
    task.s()
}