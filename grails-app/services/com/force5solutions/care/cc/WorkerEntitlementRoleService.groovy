package com.force5solutions.care.cc

import com.force5solutions.care.workflow.CentralWorkflowUtilService

public class WorkerEntitlementRoleService {

    def auditService

    public WorkerEntitlementRole createActiveWorkerEntitlementRoles(Employee employee, CcEntitlementRole entitlementRole) {
        WorkerEntitlementRole workerEntitlementRole = _createWorkerEntitlementRole(employee, entitlementRole, EntitlementRoleAccessStatus.active)
        workerEntitlementRole.s()
        return workerEntitlementRole
    }

    private WorkerEntitlementRole _createWorkerEntitlementRole(Worker worker, CcEntitlementRole entitlementRole, EntitlementRoleAccessStatus status) {
        WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.findByWorkerAndEntitlementRole(worker, entitlementRole)
        if (!workerEntitlementRole) {
            workerEntitlementRole = new WorkerEntitlementRole()
        }
        workerEntitlementRole.entitlementRole = entitlementRole
        workerEntitlementRole.worker = worker
        workerEntitlementRole.lastStatusChange = new Date()
        workerEntitlementRole.status = status
        auditService.saveWorkerEntitlementRole(workerEntitlementRole)
        return workerEntitlementRole
    }

    public WorkerEntitlementRole createWorkerEntitlementRole(Worker worker, String entitlementRoleId, String accessJustification, String userId, List<UploadedFile> uploadedFiles = [], boolean isPublicAccessRequest = false) {
        List<CentralDataFile> documents = []
        CcEntitlementRole entitlementRole = CcEntitlementRole.findByIdAndDeletedFromAps(entitlementRoleId, false)
        WorkerEntitlementRole workerEntitlementRole = _createWorkerEntitlementRole(worker, entitlementRole, EntitlementRoleAccessStatus.pendingApproval)
        uploadedFiles?.each {UploadedFile uploadedFile ->
            documents.add(new CentralDataFile(uploadedFile).s())
        }
        if (isPublicAccessRequest) {
            CentralWorkflowUtilService.startPublicAccessRequestWorkflow(workerEntitlementRole, accessJustification, documents)
        } else {
            CentralWorkflowUtilService.startAccessRequestWorkflow(workerEntitlementRole, accessJustification, documents)
        }
        return workerEntitlementRole
    }

    public WorkerEntitlementRole createWorkerEntitlementRoleForFeedUtility(Worker worker, CcEntitlementRole entitlementRole) {
        WorkerEntitlementRole workerEntitlementRole = _createWorkerEntitlementRole(worker, entitlementRole, EntitlementRoleAccessStatus.pendingApproval)
        return workerEntitlementRole
    }

    public void changeEntitlementRoleStatus(Long workerEntitlementRoleId, EntitlementRoleAccessStatus newStatus, String userId = null, String currentNode = null, String customPropertyValues = null) {
        try {
            WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(workerEntitlementRoleId)

            if (newStatus) {
                workerEntitlementRole.status = newStatus
                workerEntitlementRole.currentNode = currentNode
                workerEntitlementRole.customPropertyValues = customPropertyValues
                workerEntitlementRole.lastStatusChange = new Date()
            }
            auditService.saveWorkerEntitlementRole(workerEntitlementRole)
        }

        catch (Exception e) {
            e.printStackTrace()
            throw new Exception("Exception while changing entitlementRole access")
        }
    }
}