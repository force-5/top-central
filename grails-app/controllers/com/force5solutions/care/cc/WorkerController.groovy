package com.force5solutions.care.cc

import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.workflow.WorkflowTask
import com.force5solutions.care.workflow.WorkflowVO

class WorkerController {

    def workflowReport = {
        Long workerId = params['workerId']?.toString()?.toLong() ?: null
        Worker worker = Worker.get(workerId)
        String workflowGUID = params['id']
        List<WorkflowVO> workflowVOs = []

        if (workflowGUID == "latest") {
            List<WorkflowTask> tasks = CentralWorkflowTask.list()
            WorkflowTask task = tasks.max {it.lastUpdated}
            workflowGUID = task?.workflowGuid
        }

        List<CentralWorkflowTask> centralTasks = CentralWorkflowTask?.findAllByWorkflowGuid(workflowGUID)
        centralTasks.each { CentralWorkflowTask centralWorkflowTask ->
            workflowVOs << new WorkflowVO(centralWorkflowTask, "Central")
        }
        workflowVOs = workflowVOs.sort {it.taskUpdated}
        render(view: 'workflowReport', model: [worker: worker ?: Worker.get(1L), workflowGUID: workflowGUID, workflowType: workflowVOs?.first()?.workflowType, workflowVOs: workflowVOs])
    }

    def workflowTaskDocuments = {
        Long taskId = params.long('taskId')
        String system = params['system']
        WorkflowTask task = CentralWorkflowTask.get(taskId)
        List documents = task?.documents as List
        render(template: 'documentPopUp', model: [documents: documents, system: system, message: task.message], contentType: 'text/plain')
    }

    def downloadFile = {
        Class clazz = grailsApplication.getClassForName(params.className)
        Object object = clazz.get(params.id)
        byte[] fileContent = object[params.fieldName]
        String fileName = object['fileName']
        response.setContentLength(fileContent.size())
        response.setHeader("Content-disposition", "attachment; filename=" + fileName)
        response.setContentType(AppUtil.getMimeContentType(fileName.tokenize(".").last().toString()))
        OutputStream out = response.getOutputStream()
        out.write(fileContent)
        out.flush()
        out.close()
    }

    def workflowReportBySlidOrId = {
        String workerSlidOrId = params.get('id')?.toString()
        Worker worker = Employee.findBySlid(workerSlidOrId?.toUpperCase()) ?: Employee.get(workerSlidOrId) ?: Contractor.findBySlid(workerSlidOrId?.toUpperCase()) ?: Contractor.get(workerSlidOrId)
        List<CentralWorkflowTask> centralWorkflowTasks = CentralWorkflowTask.findAllByWorkerEntitlementRoleIdIsNotNull()
        List<Long> selectedWorkeEntitlementRoleIds = []

        List<Long> workeEntitlementRoleIds = centralWorkflowTasks.unique {CentralWorkflowTask task -> task.workerEntitlementRoleId}*.workerEntitlementRoleId
        workeEntitlementRoleIds.each {Long workeEntitlementRoleId ->
            WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(workeEntitlementRoleId)
            if (workerEntitlementRole.worker.slid == workerSlidOrId || workerEntitlementRole.worker.id == worker.id) {
                selectedWorkeEntitlementRoleIds << workeEntitlementRoleId
            }
        }

        List<CentralWorkflowTask> selectedCentralWorkflowTasks = CentralWorkflowTask.findAllByWorkerEntitlementRoleIdInList(selectedWorkeEntitlementRoleIds).unique { CentralWorkflowTask task -> task.workflowGuid }
        render(view: 'workflowReportBySlidOrId', model: [worker: worker, slid: workerSlidOrId, selectedCentralWorkflowTasks: selectedCentralWorkflowTasks])
    }
}
