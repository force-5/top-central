package com.force5solutions.care.cc

import com.force5solutions.care.common.SecuredBasedOnWorkerTypeAndSendWorkerToPermission
import com.force5solutions.care.ldap.Permission

public class WorkerCertificationController {

    def workerCertificationService
    def auditService

    @SecuredBasedOnWorkerTypeAndSendWorkerToPermission(contractorPermission = Permission.READ_CONTRACTOR_CERTIFICATION, employeePermission = Permission.READ_EMPLOYEE_CERTIFICATION)
    def certification = {
        Worker worker = Worker.get(params.id)
        [worker: worker, workerCertifications: worker.currentCertifications]
    }

    @SecuredBasedOnWorkerTypeAndSendWorkerToPermission(contractorPermission = Permission.CREATE_CONTRACTOR_CERTIFICATION, employeePermission = Permission.CREATE_EMPLOYEE_CERTIFICATION)
    def newWorkerCertification = {
        Worker worker = Worker.get(params.id)
        WorkerCertification workerCertification
        if (params.certificationId) {
            workerCertification = worker.getRecentWorkerCertificationByCertificationId(params.certificationId.toLong())
        }
        if (workerCertification && !workerCertification.dateCompleted) {
            forward(action: 'editWorkerCertification', params: [ajax: true, id: worker.id, workerCertificationId: workerCertification.id])
        } else {
            workerCertification = workerCertificationService.newWorkerCertification(worker.id, (params.certificationId) ? params.certificationId?.toLong() : 0)
            List<Long> existingCertifications = worker.effectiveCertifications.findAll {it.isActive()}*.certification.id
            Set<Certification> certifications = existingCertifications ? Certification.findAllByIdNotInList(existingCertifications) : Certification.list()
            render(template: '/workerCertification/newWorkerCertification',
                    model: [certifications: certifications, workerCertification: workerCertification, worker: worker])
        }
    }

    @SecuredBasedOnWorkerTypeAndSendWorkerToPermission(contractorPermission = Permission.UPDATE_CONTRACTOR_CERTIFICATION, employeePermission = Permission.UPDATE_EMPLOYEE_CERTIFICATION)
    def editWorkerCertification = {
        Worker worker = Worker.get(params.id)
        WorkerCertification workerCertification = WorkerCertification.get(params.workerCertificationId)
        workerCertification = WorkerCertification.findByWorkerAndCertification(worker, workerCertification.certification, [sort: 'id', order: 'desc'])
        if (workerCertification.dateCompleted) {
            render(template: '/workerCertification/recentCompleteCertification',
                    model: [workerCertification: workerCertification, worker: worker])
        } else {
            render(template: '/workerCertification/editWorkerCertification',
                    model: [workerCertification: workerCertification, worker: worker])
        }
    }

    def workerCertificationHistory = {
        Worker worker = Worker.get(params.id)
        WorkerCertification workerCertification = WorkerCertification.get(params.workerCertificationId)
        List<WorkerCertification> oldWorkerCertifications = WorkerCertification.findAllByWorkerAndCertification(worker, workerCertification.certification, [sort: 'dateCreated', order: 'desc'])
        render(template: '/workerCertification/certificationHistoryPopup',
                model: [oldWorkerCertifications: oldWorkerCertifications, worker: worker, workerCertification: workerCertification])
    }

    @SecuredBasedOnWorkerTypeAndSendWorkerToPermission(contractorPermission = Permission.RENEW_CONTRACTOR_CERTIFICATION, employeePermission = Permission.RENEW_EMPLOYEE_CERTIFICATION)
    def renewWorkerCertification = {
        WorkerCertification workerCertification = workerCertificationService.newWorkerCertification(params.id.toLong(), (params.certificationId) ? params.certificationId?.toLong() : 0)
        render(template: '/workerCertification/editWorkerCertification',
                model: [workerCertification: workerCertification, worker: workerCertification.worker])
    }

    @SecuredBasedOnWorkerTypeAndSendWorkerToPermission(contractorPermission = Permission.DELETE_CONTRACTOR_CERTIFICATION, employeePermission = Permission.DELETE_EMPLOYEE_CERTIFICATION)
    def deleteWorkerCertification = {
        WorkerCertification workerCertification = WorkerCertification.get(params.workerCertificationId)
        Boolean isSuccessful = auditService.deleteWorkerCertification(workerCertification)
        flash.message = (isSuccessful) ? "Personnel Certification deleted successfully" : "Personnel Certification failed to delete"
        if (BusinessUnitRequester.exists(params.id)) {
            redirect(controller: 'businessUnitRequester', action: 'certification', id: params.id)
        } else {
            redirect(action: 'certification', id: params.id)
        }
    }

    def saveUpdateWorkerCertification = {WorkerCertificationCO workerCertificationCO ->
        workerCertificationCO.populateExistingAffidavitIds(params)
        workerCertificationCO.populateAffidavits(params)
        workerCertificationService.saveWorkerCertification(workerCertificationCO)
        redirect(action: 'certification', id: params.id)
    }
}

