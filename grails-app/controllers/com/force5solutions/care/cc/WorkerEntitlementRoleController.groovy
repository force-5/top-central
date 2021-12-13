package com.force5solutions.care.cc

import com.force5solutions.care.common.SessionUtils
import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.workflow.CentralWorkflowUtilService
import com.force5solutions.care.workflow.WorkflowTask
import com.force5solutions.care.workflow.WorkflowVO

import static com.force5solutions.care.common.CareConstants.*
import com.force5solutions.care.workflow.WorkflowTaskStatus

class WorkerEntitlementRoleController {

    def auditService
    def workerService
    def workerEntitlementRoleService

    def revokeAccess = {
        WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(params.long('id'))
        String userId = SessionUtils.getSession()?.loggedUser ?: CENTRAL_SYSTEM_USER_ID
        List<UploadedFile> uploadedFiles = AppUtil.populateAttachments(params)
        List<CentralDataFile> documents = []

        uploadedFiles?.each {UploadedFile uploadedFile ->
            documents.add(new CentralDataFile(uploadedFile).s())
        }

        String revocationTaskTemplate = (params.revocationType == '24H') ? WORKFLOW_TASK_TEMPLATE_REVOKE_IN_24_HOURS : WORKFLOW_TASK_TEMPLATE_REVOKE_IN_7_DAYS
        Date effectiveStartDate = Date.parse('MM/dd/yyyy', params.effectiveDate_value)
        effectiveStartDate.hours = (params.selected_hour as Integer);
        effectiveStartDate.minutes = (params.selected_min as Integer);
        String revokeJustification = params.revokeJustification
        workerEntitlementRoleService.changeEntitlementRoleStatus(workerEntitlementRole.id, EntitlementRoleAccessStatus.pendingRevocation, userId)
        CentralWorkflowUtilService.startRevokeRequestWorkflow(workerEntitlementRole, revokeJustification, documents, revocationTaskTemplate, effectiveStartDate)
        redirect(action: 'access', id: workerEntitlementRole.worker.id)
    }

    def cancelAccessApprovalRequest = {
        WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(params.long('id'))
        CentralWorkflowTask accessApprovalWorkflowToBeAborted = CentralWorkflowTask.createCriteria().get {
            eq('nodeName', 'Pending APS Approval')
            inList('status', [WorkflowTaskStatus.NEW, WorkflowTaskStatus.PENDING])
            eq('workerEntitlementRoleId', workerEntitlementRole.id)
        }
        if (accessApprovalWorkflowToBeAborted) {
            CentralWorkflowUtilService.abortWorkflow(accessApprovalWorkflowToBeAborted.workflowGuid)
            String userId = SessionUtils.getSession()?.loggedUser ?: CENTRAL_SYSTEM_USER_ID
            List<UploadedFile> uploadedFiles = AppUtil.populateAttachments(params)
            List<CentralDataFile> documents = []
            uploadedFiles?.each {UploadedFile uploadedFile ->
                documents.add(new CentralDataFile(uploadedFile).s())
            }
            String revocationTaskTemplate = (params.revocationType == '24H') ? WORKFLOW_TASK_TEMPLATE_REVOKE_IN_24_HOURS : WORKFLOW_TASK_TEMPLATE_REVOKE_IN_7_DAYS
            Date effectiveStartDate = Date.parse('MM/dd/yyyy', params.effectiveDate_value)
            effectiveStartDate.hours = (params.selected_hour as Integer);
            effectiveStartDate.minutes = (params.selected_min as Integer);
            String revokeJustification = params.revokeJustification
            workerEntitlementRoleService.changeEntitlementRoleStatus(workerEntitlementRole.id, EntitlementRoleAccessStatus.pendingRevocation, userId)
            CentralWorkflowUtilService.startCancelWorkflow(accessApprovalWorkflowToBeAborted.workflowGuid, workerEntitlementRole, revokeJustification, documents, revocationTaskTemplate, effectiveStartDate)
        }
        redirect(action: 'access', id: workerEntitlementRole.worker.id)
    }

    def access = {
        Worker worker = Worker.get(params.long('id'))
        List<WorkerEntitlementRole> entitlementRoles = worker.entitlementRoles?.toList()

        if (params.filterByCertification) {
            entitlementRoles = entitlementRoles.findAll {
                params.filterByCertification.toLong() in it.entitlementRole?.getInheritedCertifications(worker)*.id
            }
        }

        render(view: 'access', model:
                [worker: worker,
                        certifications: Certification.list(),
                        entitlementRoles: entitlementRoles,
                        filterByCertification: params.filterByCertification
                ])
    }

    def showAllEntitlementRole = {
        params.remove('filterByCertification')
        redirect(action: 'access', id: params.long('id'))
    }

    def terminateForCause = {
        Worker worker = Worker.get(params.long('id'))
        List<UploadedFile> uploadedFiles = AppUtil.populateAttachments(params)
        if (workerService.requestTermination(worker, session.loggedUser, params.terminateForCauseWorkerRoleHistoryItem, uploadedFiles)) {
            flash.message = "${worker.name} terminated for cause"
        } else {
            flash.message = "${worker.name} cannot be terminated for cause"
        }

        redirect(action: 'access', id: params.long('id'))
    }

    def addEntitlementRoleAccess = {
        Worker worker = Worker.get(params?.workerId)
        List<UploadedFile> uploadedFiles = AppUtil.populateAttachments(params)
        WorkerEntitlementRole workerEntitlementRole = workerEntitlementRoleService.createWorkerEntitlementRole(worker, params.entitlementRoleId, params.accessJustification, session.loggedUser, uploadedFiles)
        if (workerEntitlementRole?.id) {
            render(workerEntitlementRole.entitlementRole.id)
        } else {
            render("failure")
        }
    }

    def removeEntitlementRoleAccess = {
        WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(params.changeWorkerEntitlementRoleId?.toLong())
        Boolean isSuccessful = auditService.deleteWorkerEntitlementRole(workerEntitlementRole)
        flash.message = (isSuccessful) ? "CcEntitlementRole deleted successfully." : "This entitlementRole could not be deleted"
        redirect(action: 'access', id: workerEntitlementRole.worker.id)
    }

    def getLocationTree = {
        render(care.locationTree(id: params.id, worker: Worker.get(params.workerId?.toLong())))
    }

    def workflowTaskDocuments = {
        WorkflowTask task = CentralWorkflowTask.get(params.long('taskId'))
        render(template: 'documentPopUp', model: [documents: task?.documents, message: task.message])
    }

    def workerRoleHistoryItemsPopup = {
        Long workerEntitlementRoleId = params.long('workerEntitlementRoleId')
        List<WorkflowVO> workflowVOs = []

        List<CentralWorkflowTask> centralTasks = CentralWorkflowTask.findAllByWorkerEntitlementRoleId(workerEntitlementRoleId)
        centralTasks.each { CentralWorkflowTask centralWorkflowTask ->
            workflowVOs << new WorkflowVO(centralWorkflowTask, "Central")
        }
        workflowVOs = workflowVOs.sort {it.taskCreated}

        render(template: '/workerEntitlementRole/workerRoleHistoryItemsPopup', model: [workflowVOs: workflowVOs])
    }

    def revokeJustificationPopup = {
        WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(params?.workerEntitlementRoleId?.toLong())
        boolean isAccessWorkflowCancelled = params?.isCancelled?.toString()?.toBoolean()
        render(template: '/workerEntitlementRole/revokeJustification', model: [workerEntitlementRole: workerEntitlementRole, isAccessWorkflowCancelled: isAccessWorkflowCancelled])
    }

}