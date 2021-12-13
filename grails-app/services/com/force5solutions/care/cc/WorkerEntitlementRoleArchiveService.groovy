package com.force5solutions.care.cc

import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.workflow.CentralWorkflowType

class WorkerEntitlementRoleArchiveService {

    boolean transactional = true

    def entitlementService

    void createWorkerEntitlementRoleEntry(CentralWorkflowTask centralWorkflowTask, Map responseElements) {
        try {
            List<String> nodeNameList = ['Access Granted By Feed', 'Access Revoked By Feed']
            if (centralWorkflowTask.workerEntitlementRole && (centralWorkflowTask.nodeName in nodeNameList)) {
                createWorkerEntitlementRoleArchiveEntry(centralWorkflowTask, responseElements)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    public void createWorkerEntitlementRoleArchiveEntry(CentralWorkflowTask centralWorkflowTask, Map responseElements) {
        WorkerEntitlementRoleArchive workerEntitlementRoleArchive = new WorkerEntitlementRoleArchive()
        workerEntitlementRoleArchive.workerId = centralWorkflowTask.workerEntitlementRole.worker.id
        workerEntitlementRoleArchive.entitlementRoleId = centralWorkflowTask?.entitlementRole?.id
        workerEntitlementRoleArchive.workerFirstName = centralWorkflowTask.workerEntitlementRole.worker.firstName
        workerEntitlementRoleArchive.workerMiddleName = centralWorkflowTask.workerEntitlementRole.worker.middleName
        workerEntitlementRoleArchive.workerLastName = centralWorkflowTask.workerEntitlementRole.worker.lastName
        workerEntitlementRoleArchive.workerSlid = centralWorkflowTask.workerEntitlementRole.worker.slid
        workerEntitlementRoleArchive.entitlementRoleName = centralWorkflowTask.entitlementRole.name
        workerEntitlementRoleArchive.userResponse = (responseElements && responseElements.containsKey('userAction')) ? responseElements.get('userAction') : centralWorkflowTask.response
        workerEntitlementRoleArchive.entitlementRoleOrigin = centralWorkflowTask?.entitlementRole?.origin?.name
        workerEntitlementRoleArchive.entitlementRoleTypes = centralWorkflowTask?.entitlementRole?.types
        workerEntitlementRoleArchive.notes = centralWorkflowTask.message
        if (centralWorkflowTask.workflowType == CentralWorkflowType.ACCESS_GRANTED_BY_FEED) {
            workerEntitlementRoleArchive.actionType = "ACCESS GRANTED BY FEED"
        } else if (centralWorkflowTask.workflowType == CentralWorkflowType.ACCESS_REVOKED_BY_FEED) {
            workerEntitlementRoleArchive.actionType = "ACCESS REVOKED BY FEED"
        }
        workerEntitlementRoleArchive.actionDate = centralWorkflowTask.actionDate
        workerEntitlementRoleArchive.activeEntitlementRoles = centralWorkflowTask.worker?.activeEntitlementRoles*.entitlementRole.name.join(',')
        workerEntitlementRoleArchive.s()
    }
}
