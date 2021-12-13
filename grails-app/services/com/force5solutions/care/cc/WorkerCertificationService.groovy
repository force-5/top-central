package com.force5solutions.care.cc

import com.force5solutions.care.workflow.CentralWorkflowUtilService
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.feed.WorkerPraCertificationInfo
import org.grails.plugins.versionable.VersioningContext
import com.force5solutions.care.common.CareConstants

class WorkerCertificationService {

    def auditService
    def workerEntitlementRoleService
    static config = ConfigurationHolder.config
    private final String REVOCATION_AGAINST_MISSING_CERTIFICATIONS_JUSTIFICATION = "Revocation initialized by Revoke-On-Missing-Certifications-Job against missing certifications"

    public void addPraCertificationFromFeedToWorker(Worker worker) {
        Certification praCertification = (config.praCertificationId) ? Certification.get(config.praCertificationId?.toLong()) : null
        if (praCertification) {
            WorkerPraCertificationInfo workerPraCertificationInfo = WorkerPraCertificationInfo.findByPernr(worker.workerNumber, [sort: 'lastBackgroundDate', order: 'desc'])
            if (workerPraCertificationInfo) {
                WorkerCertification workerPraCertification = worker.getRecentWorkerCertificationByCertificationId(praCertification.id)
                if (!workerPraCertification || (workerPraCertification.dateCompleted != workerPraCertificationInfo.lastBackgroundDate)) {
                    saveNewWorkerCertificationWithDateCompleted(worker.id, praCertification.id, workerPraCertificationInfo.lastBackgroundDate)
                }
            }
        }

    }

    public WorkerCertification newWorkerCertification(Long workerId, Long certificationId) {
        WorkerCertification wc = new WorkerCertification()
        wc.worker = Worker.get(workerId)
        if (certificationId > 0) {
            wc.certification = Certification.get(certificationId)
        }
        if (wc.certification?.testRequired) {
            wc.testStatus = new CertificationStatus()
        }
        if (wc.certification?.trainingRequired) {
            wc.trainingStatus = new CertificationStatus()
        }
        return wc
    }

    public WorkerCertification saveNewWorkerCertificationWithDateCompleted(Long workerId, Long certificationId, Date dateCompleted = null) {
        WorkerCertificationCO workerCertificationCO = new WorkerCertificationCO()
        workerCertificationCO.id = workerId
        workerCertificationCO.certificationId = certificationId
        workerCertificationCO.dateCompleted_value = dateCompleted ? dateCompleted.format('MM/dd/yyyy') : ''
        saveWorkerCertification(workerCertificationCO)
    }

    /**
     * Saves the worker certification.
     *
     * @param workerCertificationCO
     * @return
     */
    public WorkerCertification saveWorkerCertification(WorkerCertificationCO workerCertificationCO) {
        Worker worker = Worker.get(workerCertificationCO.id)
        Set<WorkerEntitlementRole> entitlementRolesBeforeAddingCertifications = []
        if (worker.entitlementRoles) {
            entitlementRolesBeforeAddingCertifications = worker.entitlementRoles?.findAll {it.isPendingCertifications()}
        }
        WorkerCertification workerCertification = new WorkerCertification()
        if (workerCertificationCO.workerCertificationId) {
            workerCertification = WorkerCertification.get(workerCertificationCO.workerCertificationId)
        } else {
            workerCertification.worker = worker
            workerCertification.certification = Certification.get(workerCertificationCO.certificationId)
        }
        if (workerCertification.certification.affidavitRequired) {
            //Check if existing affidavits have been removed, if so remove them
            if (workerCertification.id) {
                List affidavitIdsToRemove = workerCertification.affidavits*.id - workerCertificationCO.existingAffidavitIds
                if (affidavitIdsToRemove) {
                    List<CentralDataFile> affidavitsToRemove = CentralDataFile.getAll(affidavitIdsToRemove)
                    affidavitsToRemove?.each {CentralDataFile affidavit ->
                        workerCertification.removeFromAffidavits(affidavit)
                        affidavit.delete(flush: true)
                    }
                }
                workerCertification = workerCertification.refresh()
            } else {
                workerCertification.affidavits = []
            }
            workerCertificationCO.affidavits.each {UploadedFile uploadedFile ->
                CentralDataFile dataFile = new CentralDataFile(uploadedFile)
                workerCertification.addToAffidavits(dataFile)
            }
        }
        workerCertification.subType = workerCertificationCO.subType
        workerCertification.provider = workerCertificationCO.provider
        workerCertification.dateCompleted = workerCertificationCO.getDateCompleted()
        workerCertification.testStatus = workerCertificationCO.getTestStatusObject();
        workerCertification.trainingStatus = workerCertificationCO.getTrainingStatusObject();

        Boolean isSuccessful = auditService.saveWorkerCertification(workerCertification)
        if (isSuccessful) {
            Set<WorkerEntitlementRole> entitlementRolesCompletedCertifications = entitlementRolesBeforeAddingCertifications.findAll { !(it.isPendingCertifications())}
            sendEntitlementRoleAccessRequests(entitlementRolesCompletedCertifications)
            return workerCertification
        } else {
            return null;
        }
    }

    void sendEntitlementRoleAccessRequests(Set<WorkerEntitlementRole> entitlementRoles) {
        entitlementRoles.each {WorkerEntitlementRole workerEntitlementRole ->
            CentralWorkflowUtilService.certificationCompleteEvent(workerEntitlementRole.id)
        }
    }

    public List<WorkerCertification> getRequiredWorkerCertifications() {
        List<WorkerCertification> workerCertifications = []
        List<WorkerEntitlementRole> entitlementRoles = []
        List<Certification> requiredCertifications = []
        List<Worker> workers = Worker.list()
        workers.each {Worker worker ->
            entitlementRoles = (worker.entitlementRoles?.findAll {it.status == EntitlementRoleAccessStatus.active} as List)
            if (entitlementRoles) {
                entitlementRoles.each {WorkerEntitlementRole workerEntitlementRole ->
                    requiredCertifications = ((workerEntitlementRole.entitlementRole.requiredCertifications + workerEntitlementRole.entitlementRole.getInheritedCertifications(worker)) as List)
                    workerCertifications += worker.effectiveCertifications.findAll {it.certification.id in requiredCertifications*.id}
                }
            }
        }
        return workerCertifications
    }

    public List<WorkerCertification> getRequiredBurCertifications() {
        List<Worker> workers = Worker.list()
        List<WorkerCertification> workerCertifications = []
        List<BusinessUnitRequester> burs = BusinessUnitRequester.list()
        burs.each {BusinessUnitRequester bur ->
            List<Worker> workersByBur = workers.findAll {bur in it.businessUnitRequesters}
            Set<Certification> burRequiredCertifications = []
            workersByBur.each {Worker worker ->
                if (worker.entitlementRoles.size() > 0) {
                    worker.entitlementRoles.each {
                        if (it.status == EntitlementRoleAccessStatus.active) {
                            burRequiredCertifications = burRequiredCertifications + it.entitlementRole.location.sponsorCertifications
                            burRequiredCertifications = burRequiredCertifications + it.entitlementRole.location.inheritedSponsorCertifications
                        }
                    }
                    workerCertifications = workerCertifications + bur.effectiveCertifications?.findAll {it.certification in burRequiredCertifications}
                }
            }
        }
        return workerCertifications
    }

    public List<WorkerCertification> getRequiredEmployeeSupervisorCertifications(List<Worker> workers) {
        List<WorkerCertification> workerCertifications = []
        List<EmployeeSupervisor> employeeSupervisors = EmployeeSupervisor.list()
        employeeSupervisors.each {EmployeeSupervisor employeeSupervisor ->
            List<Worker> workersBySupervisor = Employee.findAllBySupervisor(employeeSupervisor)
            Set<Certification> supervisorRequiredCertifications = []
            workersBySupervisor.each {Worker worker ->
                if (worker.entitlementRoles.size() > 0) {
                    worker.entitlementRoles.each {
                        if (it.status == EntitlementRoleAccessStatus.active) {
                            supervisorRequiredCertifications = supervisorRequiredCertifications + it.entitlementRole.sponsorCertifications
                            supervisorRequiredCertifications = supervisorRequiredCertifications + it.entitlementRole.inheritedSponsorCertifications
                        }
                    }
                    workerCertifications = workerCertifications + employeeSupervisor.certifications?.findAll {it in supervisorRequiredCertifications}
                }
            }
        }
        return workerCertifications
    }



    public void startCertificationExpirationNotificationWorkflow(List<CertificationExpirationNotificationVO> notificationVOs) {
        notificationVOs.each { CertificationExpirationNotificationVO notificationVO ->
            log.debug "**********************************************************************************************************************************"
            log.debug "  Notification Period: " + notificationVO.notificationPeriod + " day for " + notificationVO.workerCertification
            log.debug "  Days remaining to expiration: " + notificationVO.daysRemaining + " for Worker " + notificationVO.workerCertification.worker
            CentralWorkflowUtilService.startCertificationExpirationNotificationWorkflow(notificationVO.workerCertification, notificationVO.taskTemplateName)
            createCertificationExpirationNotification(notificationVO.workerCertification.id, notificationVO.notificationPeriod)
        }
    }


    public List<CertificationExpirationNotificationVO> sendCertificationExpirationNotification(Date executionDate = new Date()) {
        List<CertificationExpirationNotificationVO> notificationVOs = []
        Map workerCertificationByCertification = getRequiredWorkerCertifications().groupBy {it.certification}
        workerCertificationByCertification.each {Certification certification, List<WorkerCertification> workerCertifications ->
            List<Integer> notificationPeriods = certification.notificationPeriods
            notificationPeriods = notificationPeriods.sort().reverse()
            workerCertifications.each { WorkerCertification workerCertification ->
                Date expiration = workerCertification.getFudgedExpiry()
                int daysRemaining = expiration - executionDate
                if (daysRemaining >= 0) {
                    int notificationPeriod = getNotificationPeriodToSend(notificationPeriods, daysRemaining)
                    if (notificationPeriod && CertificationExpirationNotification.countByWorkerCertificationAndNotificationPeriod(workerCertification, notificationPeriod) == 0) {
                        String templateName = CertificationExpirationNotificationTemplate.findCertificationExpirationNotificationTemplate(workerCertification, notificationPeriod)
                        notificationVOs.add(new CertificationExpirationNotificationVO(workerCertification: workerCertification, daysRemaining: daysRemaining, taskTemplateName: templateName, notificationPeriod: notificationPeriod))
                    }
                }
            }
        }
        return notificationVOs
    }

    private void createCertificationExpirationNotification(Long workerCertificationId, int notificationPeriod) {
        WorkerCertification workerCertification = WorkerCertification.get(workerCertificationId)
        CertificationExpirationNotification certificationExpirationNotification = new CertificationExpirationNotification(workerCertification: workerCertification, notificationPeriod: notificationPeriod)
        certificationExpirationNotification.s()
    }

    private int getNotificationPeriodToSend(List notificationPeriods, Integer daysRemaining) {
        List<Integer> notificationPeriodCandidates = []
        int size = notificationPeriods.size()
        notificationPeriods.eachWithIndex { notificationPeriod, index ->
            if (index < size - 1) {
                if (notificationPeriod > daysRemaining && (notificationPeriods[index + 1]) <= daysRemaining) {
                    notificationPeriodCandidates << notificationPeriod
                }
            } else if (notificationPeriod > daysRemaining) {
                notificationPeriodCandidates << notificationPeriod
            }
        }
        return !notificationPeriodCandidates.isEmpty() ? notificationPeriodCandidates.sort().last() : 0
    }

    public void revokeAccessInCaseMissingAnyCertifications() {
        List<WorkerEntitlementRole> activeEntitlementRoles = WorkerEntitlementRole.findAllByStatus(EntitlementRoleAccessStatus.ACTIVE)
        activeEntitlementRoles.each {WorkerEntitlementRole workerEntitlementRole ->
            Set<Certification> missingCertifications = workerEntitlementRole.getMissingCertifications()
            Set<Certification> requiredCertifications = workerEntitlementRole.entitlementRole.requiredCertifications + workerEntitlementRole.entitlementRole.getInheritedCertifications(workerEntitlementRole.worker)
            Set<WorkerCertification> effectiveCertifications = workerEntitlementRole.worker.effectiveCertifications
            int daysPriorToKickOffWorkflow = ConfigurationHolder.config.daysPriorToKickOffWorkflowInMissingCertificationRevocation ? ConfigurationHolder.config.daysPriorToKickOffWorkflowInMissingCertificationRevocation.toString().toInteger() : 0

            if ((effectiveCertifications.any {it.certification in requiredCertifications}) && (!(effectiveCertifications.any {it.isExpired()})) && (effectiveCertifications.any {(it.fudgedExpiry - daysPriorToKickOffWorkflow) <= (new Date())})) {
                VersioningContext.set(UUID.randomUUID().toString())
                log.debug "Found a required certification for worker: ${workerEntitlementRole.worker} on role: ${workerEntitlementRole.entitlementRole} which will expire in less than ${daysPriorToKickOffWorkflow} days... Starting 7 days revocation workflow now ..."
                CentralWorkflowUtilService.startRevokeRequestWorkflow(workerEntitlementRole, REVOCATION_AGAINST_MISSING_CERTIFICATIONS_JUSTIFICATION, [], CareConstants.WORKFLOW_TASK_TEMPLATE_REVOKE_IN_7_DAYS, new Date())
            } else if (missingCertifications.size()) {
                VersioningContext.set(UUID.randomUUID().toString())
                log.debug "Found an expired certification for worker: ${workerEntitlementRole.worker} on role: ${workerEntitlementRole.entitlementRole}.... Starting 24 hours revocation workflow now ..."
                CentralWorkflowUtilService.startRevokeRequestWorkflow(workerEntitlementRole, REVOCATION_AGAINST_MISSING_CERTIFICATIONS_JUSTIFICATION, [], CareConstants.WORKFLOW_TASK_TEMPLATE_REVOKE_IN_24_HOURS, new Date())
            }
        }
    }
}
