package com.force5solutions.care.cc

import com.force5solutions.care.common.SessionUtils
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import static com.force5solutions.care.common.CareConstants.*

import org.grails.plugins.versionable.VersionHistory

import org.codehaus.groovy.grails.commons.ApplicationHolder

class AuditService {

    def versioningService
    def sessionFactory
    def workerProfileArchiveService
    def workerCertificationArchiveService
    static config = ConfigurationHolder.config

    public static String isContractorOrEmployee(Worker worker) {
        if (worker.isContractor()) {
            return "Contractor"
        } else {
            return "Employee"
        }
    }

    public Worker refreshWorker(Long workerId) {
        Worker worker = Worker.get(workerId)

        if (worker?.isContractor()) {
            refreshContractor(worker)
        } else {
            refreshEmployee(worker)
        }

        return worker
    }


    public void refreshEmployee(Worker worker) {
        if (!worker.isAttached()) {
            try {
                worker.attach()
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
        worker?.person
        worker?.supervisor?.person
        worker.terminateForCause
        worker.businessUnitRequesters?.each { it?.person?.firstName}
        worker.workerImage?.size

//        worker.entitlementRoles = WorkerEntitlementRole.findAllByWorker(worker)
//        worker.certifications = WorkerCertification.findAllByWorker(worker)

        worker.entitlementRoles.each {WorkerEntitlementRole workerEntitlementRole ->
            workerEntitlementRole.toDetailString()
            CcEntitlementRole entitlementRole = workerEntitlementRole.entitlementRole
            entitlementRole.toDetailString()

//            while (entitlementRole.parent) {
//                entitlementRole = entitlementRole.parent
//                entitlementRole.toDetailString()
//            }
        }

        worker.certifications.each {WorkerCertification workerCertification ->
            workerCertification.toDetailString()
            workerCertification.certification.name
            workerCertification.affidavits?.fileName
        }

    }



    public void refreshContractor(Worker worker) {
        if (!worker.isAttached()) {
            worker.attach()
        }
        worker.businessUnitRequesters?.each { it?.person?.firstName}
        worker.primeVendor
        worker.supervisor?.vendor?.companyName
        worker.supervisor?.person?.firstName
        worker.subVendor
        worker.subSupervisor?.vendor?.companyName
        worker.subSupervisor?.person?.firstName
        worker.person
//        worker.entitlementRoles = WorkerEntitlementRole.findAllByWorker(worker) as Set
//        worker.certifications = WorkerCertification.findAllByWorker(worker) as Set
        worker.workerImage?.size
        worker.terminateForCause
        worker.entitlementRoles.each {WorkerEntitlementRole workerEntitlementRole ->
            workerEntitlementRole.toDetailString()
            CcEntitlementRole entitlementRole = workerEntitlementRole.entitlementRole
            entitlementRole.toDetailString()

//            while (entitlementRole.parent) {
//                entitlementRole = entitlementRole.parent
//                entitlementRole.toDetailString()
//            }
        }
        worker.certifications.each {WorkerCertification workerCertification ->
            workerCertification.toDetailString()
            workerCertification.certification.name
            workerCertification.affidavits?.fileName
        }
    }

    public Boolean deleteWorkerEntitlementRole(WorkerEntitlementRole workerEntitlementRole) {
        long workerId = workerEntitlementRole.worker.id
        //TODO: Remove related workflows in both CareCentral and APS before deleting this object
        boolean deleteSuccess = !(workerEntitlementRole.delete(flush: true))
        if (deleteSuccess) {
            Worker worker = refreshWorker(workerId)
        }
        return deleteSuccess;
    }

    public Boolean deleteWorkerCertification(WorkerCertification workerCertification) {
//        workerCertification = workerCertification.merge();
        long workerId = workerCertification.worker.id
        boolean deleteSuccess = !(workerCertification.delete(flush: true))
        if (deleteSuccess) {
            Worker worker = refreshWorker(workerId)
        }
        return deleteSuccess;
    }

    public Boolean saveWorkerEntitlementRole(WorkerEntitlementRole workerEntitlementRole) {
        def oldEntitlementRoleIds = workerEntitlementRole.worker.getPersistentValue('entitlementRoles')*.id
        String type, description;
        if (workerEntitlementRole.id) {
            type = "Access"
            description = "EntitlementRole ${workerEntitlementRole.toString()} updated"
        } else {
            type = "Access"
            description = "EntitlementRole ${workerEntitlementRole.toString()} added"
        }
        long workerId = workerEntitlementRole.worker.id
        WorkerEntitlementRole.withTransaction {
            workerEntitlementRole.entitlementRole.requiredCertifications*.toString()
            if (!workerEntitlementRole.wasEverActive && (workerEntitlementRole.status == EntitlementRoleAccessStatus.active)) {
                workerEntitlementRole.wasEverActive = true
            }
            versioningService.saveVersionableObject(workerEntitlementRole)
            saveVersionHistoryForWorkerEntitlementRole(workerEntitlementRole, oldEntitlementRoleIds)
        }
        return true
    }

    public Boolean saveWorker(Worker worker) {
        Date date = new Date()
        String type, description;
        if (worker.id) {
            type = "Update"
        } else {
            type = "Create"
        }
        if (!worker.validate() && worker.hasErrors()) {
            worker?.errors?.allErrors?.each { log.error it }
            return false
        }
        versioningService.saveVersionableObject(worker)

        if (type == "Create" && worker.isEmployee()) {
            def workerCourseService = ApplicationHolder.getApplication().getMainContext().getBean('workerCourseService')
            workerCourseService.addCoursesFromFeedToWorker(worker)
            worker.refresh()
            def workerCertificationService = ApplicationHolder.getApplication().getMainContext().getBean('workerCertificationService')
            workerCertificationService.addPraCertificationFromFeedToWorker(worker)
        }

        workerProfileArchiveService.matchOrCreateWorkerProfileEntry(worker)
        return true
    }

    void saveVersionHistoryForWorkerEntitlementRole(WorkerEntitlementRole workerEntitlementRole, def oldEntitlementRoleIds) {
        Worker worker = workerEntitlementRole.worker
        worker = worker.refresh()
        def newEntitlementRoleIds = (worker.entitlementRoles)*.id
        if (newEntitlementRoleIds.any {!(it in oldEntitlementRoleIds)}) {
            VersionHistory versionHistory = new VersionHistory()
            versionHistory.with {
                className = worker.class.name
                propertyName = 'entitlementRoles'
                objectId = worker.id.toString()
                userId = SessionUtils.getSession()?.loggedUser ?: CENTRAL_SYSTEM_USER_ID
                oldValue = oldEntitlementRoleIds
                newValue = newEntitlementRoleIds
                effectiveDate = workerEntitlementRole.lastUpdated ?: workerEntitlementRole.dateCreated
            }
            versionHistory.save(failOnError: true)
        }
    }

    void saveVersionHistoryForCertification(WorkerCertification workerCertification, def oldCertificationIds) {
        Worker worker = workerCertification.worker
        worker = worker.refresh()
        def newCertificationIds = (worker.certifications)*.id
        if (newCertificationIds.any {!(it in oldCertificationIds)}) {
            VersionHistory versionHistory = new VersionHistory()
            versionHistory.with {
                className = worker.class.name
                propertyName = 'certifications'
                objectId = worker.id.toString()
                userId = SessionUtils.getSession()?.loggedUser ?: CENTRAL_SYSTEM_USER_ID
                oldValue = oldCertificationIds
                newValue = newCertificationIds
                effectiveDate = workerCertification.lastUpdated ?: workerCertification.dateCreated
            }
            versionHistory.save(failOnError: true)
        }
    }

    public Boolean saveWorkerCertification(WorkerCertification workerCertification) {
        String type, description;
        if (workerCertification.id) {
            type = "Certification"
            description = "Certification ${workerCertification.toString()} updated"
        } else {
            type = "Certification"
            description = "Certification ${workerCertification.toString()} added"
        }
        def oldCertificationIds = workerCertification.worker.getPersistentValue('certifications')*.id
        versioningService.saveVersionableObject(workerCertification)
        saveVersionHistoryForCertification(workerCertification, oldCertificationIds)
        workerCertificationArchiveService.matchOrCreateWorkerCertificationEntry(workerCertification, oldCertificationIds)
        return true;
    }

}
