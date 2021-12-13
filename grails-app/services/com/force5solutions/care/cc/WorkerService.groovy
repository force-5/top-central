package com.force5solutions.care.cc

import com.force5solutions.care.workflow.CentralWorkflowUtilService

public class WorkerService {
    def auditService
    def workerEntitlementRoleService

    public Boolean requestTermination(Worker worker, String loggedUser, String terminateJustification, List<UploadedFile> uploadedFiles = []) {
        worker?.refresh()
        Boolean isTerminated = false
        List<CentralDataFile> documents = []

        try {
            EntitlementRoleAccessStatus pendingTermination = EntitlementRoleAccessStatus.pendingTermination

            uploadedFiles?.each {UploadedFile uploadedFile ->
                documents.add(new CentralDataFile(uploadedFile).s())
            }
            worker.entitlementRoles?.each {WorkerEntitlementRole workerEntitlementRole ->
                workerEntitlementRole.status = pendingTermination
                workerEntitlementRole.lastStatusChange = new Date()
                CentralWorkflowUtilService.startTerminateRequestWorkflow(workerEntitlementRole, terminateJustification, documents)
            }
            worker = Worker.get(worker.id)
            TerminateForCause terminateForCause = new TerminateForCause()
            terminateForCause.cause = terminateJustification
            terminateForCause.s()
            worker.terminateForCause = terminateForCause
            auditService.saveWorker(worker)
            isTerminated = true
        } catch (Exception e) {
            e.printStackTrace()
        }
        finally {
            return isTerminated
        }
    }

    public Boolean delete(Worker worker) {
        Boolean isDeleted
        Set<WorkerCertification> workerCertifications = worker.certifications
        worker.certifications = []
        workerCertifications?.each { WorkerCertification workerCertification ->
            workerCertification.worker = null
            workerCertification.delete(flush: true)
        }
        worker.delete(flush: true)
        isDeleted = true
        return isDeleted
    }

}