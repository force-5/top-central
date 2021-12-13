package com.force5solutions.care.feed

import com.force5solutions.care.cc.Employee
import com.force5solutions.care.cc.EntitlementRoleAccessStatus
import com.force5solutions.care.cc.WorkerEntitlementRole
import groovy.sql.GroovyRowResult
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.plugins.versionable.VersioningContext
import com.force5solutions.care.cc.CcEntitlementRole
import com.force5solutions.care.common.CareConstants

class ActiveWorkerFeedService extends DatabaseFeedService {

    boolean transactional = true

    def employeeUtilService = ApplicationHolder?.application?.mainContext?.getBean("employeeUtilService")
    def workerEntitlementRoleService = ApplicationHolder?.application?.mainContext?.getBean("workerEntitlementRoleService")
    def workerProfileArchiveService = ApplicationHolder?.application?.mainContext?.getBean("workerProfileArchiveService")
    static config = ConfigurationHolder.config

    private static final String PHYSICAL_ACCESS_TYPE = 'PHYSICAL'
    private static final String ELECTRONIC_ACCESS_TYPE = 'ELECTRONIC'
    private static final String BOTH_ACCESS_TYPE = 'BOTH'

    CcEntitlementRole physical
    CcEntitlementRole electronic
    CcEntitlementRole both
    Map<String, CcEntitlementRole> entitlementRoleAccessTypeMap = [:]

    FeedRunReportMessage newWorkerEntitlementRolesInFeed = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    List<FeedRunReportMessageDetail> newWorkerEntitlementRolesInFeedDetails = []

    FeedRunReportMessage noInformationFoundInHrInfo = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    List<FeedRunReportMessageDetail> noInformationFoundInHrInfoDetails = []

    FeedRunReportMessage noERFoundForAccessType = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    List<FeedRunReportMessageDetail> noERFoundForAccessTypeDetails = []

    FeedRunReportMessage invalidAccessStatus = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    List<FeedRunReportMessageDetail> invalidAccessStatusDetails = []

    FeedRunReportMessage activeInCareButNotInFeed = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    List<FeedRunReportMessageDetail> activeInCareButNotInFeedDetails = []

    FeedRunReportMessage misMatchInWorkerEntitlementRoles = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    List<FeedRunReportMessageDetail> misMatchInWorkerEntitlementRolesDetails = []

    FeedRunReportMessage workersWithNoSupervisor = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    List<FeedRunReportMessageDetail> workersWithNoSupervisorDetails = []

    List<FeedRunReportMessage> reportMessages = []

    List<WorkerEntitlementRole> misMatchedRoles = []

    ActiveWorkerFeedService() {
        feedName = CareConstants.FEED_ACTIVE_WORKER
        driver = ConfigurationHolder.config.feed.activeWorkerFeed.driver
        url = ConfigurationHolder.config.feed.activeWorkerFeed.url
        query = ConfigurationHolder.config.feed.activeWorkerFeed.query

        physical = (config.physicalEntitlementRoleId) ? CcEntitlementRole.get(config.physicalEntitlementRoleId) : null
        electronic = (config.electronicEntitlementRoleId) ? CcEntitlementRole.get(config.electronicEntitlementRoleId) : null
        both = (config.bothEntitlementRoleId) ? CcEntitlementRole.get(config.bothEntitlementRoleId) : null
    }

    void preValidate() {
        if (!physical) {
            throw new Exception((config.physicalEntitlementRoleId) ? "No Physical Entitlement Role found in database" : "No physicalEntitlementRoleId found in Configuration File")
        }
        if (!electronic) {
            throw new Exception((config.electronicEntitlementRoleId) ? "No Electronic Entitlement Role found in database" : "No electronicEntitlementRoleId found in Configuration File")
        }
        if (!both) {
            throw new Exception((config.bothEntitlementRoleId) ? "No Both Entitlement Role found in database" : "No bothEntitlementRoleId found in Configuration File")
        }

        entitlementRoleAccessTypeMap[PHYSICAL_ACCESS_TYPE] = physical
        entitlementRoleAccessTypeMap[ELECTRONIC_ACCESS_TYPE] = electronic
        entitlementRoleAccessTypeMap[BOTH_ACCESS_TYPE] = both
    }

    void preProcess() {
    }

    Map getQueryParameters() {
        return [:]
    }

    List getVOs(def rows) {
        List<ActiveWorkerFeedVO> activeWorkerFeedVOs = []
        rows?.each {GroovyRowResult rowResult ->
            activeWorkerFeedVOs.add(new ActiveWorkerFeedVO(rowResult))
        }

        // TODO: this doeesn't really belong here.
        List<WorkerEntitlementRole> activeWorkerEntitlementRoles = WorkerEntitlementRole.findAllByStatus(EntitlementRoleAccessStatus.active)
        List<WorkerEntitlementRole> misMatchInWorkerEntitlementRolesList = []
        List<WorkerEntitlementRole> deletedActiveWorkerRecords = []
        activeWorkerEntitlementRoles.each {WorkerEntitlementRole workerEntitlementRole ->
            ActiveWorkerFeedVO activeWorkerFeedVO = activeWorkerFeedVOs.find {ActiveWorkerFeedVO activeWorkerFeedVO ->
                ((workerEntitlementRole.worker.slid == activeWorkerFeedVO.slid) && (workerEntitlementRole.entitlementRole == entitlementRoleAccessTypeMap[activeWorkerFeedVO.accessType]))
            }
            if (!activeWorkerFeedVO) {
                ActiveWorkerFeedVO deletedRecord = activeWorkerFeedVOs.find {(workerEntitlementRole.worker.slid == it.slid)}
                if (!deletedRecord) {
                    deletedActiveWorkerRecords.add(workerEntitlementRole)
                } else {
                    misMatchInWorkerEntitlementRolesList.add(workerEntitlementRole)
                }
            }
        }

        deletedActiveWorkerRecords?.each { WorkerEntitlementRole workerEntitlementRole ->
            VersioningContext.set(UUID.randomUUID().toString())
            FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "workerEntitlementRole", entityId: workerEntitlementRole.id, param1: workerEntitlementRole.toString(), param2: workerEntitlementRole.worker.slid)
            activeInCareButNotInFeedDetails.add(messageDetail)
        }

        misMatchInWorkerEntitlementRolesList?.each { WorkerEntitlementRole workerEntitlementRole ->
            VersioningContext.set(UUID.randomUUID().toString())
            FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "workerEntitlementRole", entityId: workerEntitlementRole.id, param1: workerEntitlementRole.toString(), param2: workerEntitlementRole.worker.slid)
            misMatchInWorkerEntitlementRolesDetails.add(messageDetail)
            misMatchedRoles.add(workerEntitlementRole)
        }
        return activeWorkerFeedVOs
    }

    def process(Object feedVO) {
        VersioningContext.set(UUID.randomUUID().toString())
        ActiveWorkerFeedVO activeWorkerFeedVO = (ActiveWorkerFeedVO) feedVO
        Employee employee = Employee.findBySlid(activeWorkerFeedVO.slid)
        CcEntitlementRole entitlementRole = entitlementRoleAccessTypeMap[activeWorkerFeedVO.accessType]
        if (!employee) {
            HrInfo hrInfo = HrInfo.findBySlid(activeWorkerFeedVO.slid)
            if (hrInfo) {
                if (hrInfo.supervisorSlid || hrInfo.SUPVSUPV_SLID_ID) {
                    employee = employeeUtilService.findOrCreateEmployee(activeWorkerFeedVO.slid)
                } else {
                    log.debug "Could not find any supervisor for ${hrInfo.slid}"
                    FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "HrInfo", entityId: hrInfo.id, param1: hrInfo.slid)
                    workersWithNoSupervisorDetails.add(messageDetail)
                }
            } else {
                FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "activeWorkerFeedVO", entityId: activeWorkerFeedVO.slid, param1: activeWorkerFeedVO.slid)
                noInformationFoundInHrInfoDetails.add(messageDetail)
            }
        }
        if (employee) {
            if (entitlementRole) {
                WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.findByWorkerAndEntitlementRole(employee, entitlementRole)
                if (workerEntitlementRole) {
                    if (!(workerEntitlementRole.status in [EntitlementRoleAccessStatus.active, EntitlementRoleAccessStatus.pendingRevocation])) {
                        FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "workerEntitlementRole", entityId: workerEntitlementRole.id, param1: workerEntitlementRole.toString(), param2: workerEntitlementRole.worker.slid, param3: workerEntitlementRole.status?.toString())
                        invalidAccessStatusDetails.add(messageDetail)
                    }
                } else if (!(employee in misMatchedRoles*.worker)) {
                    FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "entitlementRole", param1: entitlementRole.name, param2: employee.slid, entityId: entitlementRole.id)
                    newWorkerEntitlementRolesInFeedDetails.add(messageDetail)
                }
            } else {
                FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "activeWorkerFeedVO", entityId: activeWorkerFeedVO.accessType)
                noERFoundForAccessTypeDetails.add(messageDetail)
            }
            employee.badgeNumber = activeWorkerFeedVO.badgeNumber
            employee.s()
            workerProfileArchiveService.matchOrCreateWorkerProfileEntry(employee)
        }
    }

    void postProcess() {
    }

    List<FeedRunReportMessage> getFeedRunReportMessages() {
        if (newWorkerEntitlementRolesInFeedDetails) {
            newWorkerEntitlementRolesInFeed.message = 'Worker in Feed but not in Central'
            newWorkerEntitlementRolesInFeed.numberOfRecords = newWorkerEntitlementRolesInFeedDetails.size()
            newWorkerEntitlementRolesInFeedDetails.each {
                it.feedRunReportMessage = newWorkerEntitlementRolesInFeed
                newWorkerEntitlementRolesInFeed.details << it
            }
            reportMessages.add(newWorkerEntitlementRolesInFeed)
        }

        if (noInformationFoundInHrInfoDetails) {
            noInformationFoundInHrInfo.message = 'No information found in HRInfo table for given SLID'
            noInformationFoundInHrInfo.numberOfRecords = noInformationFoundInHrInfoDetails.size()
            noInformationFoundInHrInfoDetails.each {
                it.feedRunReportMessage = noInformationFoundInHrInfo
                noInformationFoundInHrInfo.details << it
            }
            reportMessages.add(noInformationFoundInHrInfo)
        }

        if (noERFoundForAccessTypeDetails) {
            noERFoundForAccessType.message = 'No Entitlement Role found for given Access Type'
            noERFoundForAccessType.numberOfRecords = noERFoundForAccessTypeDetails.size()
            noERFoundForAccessTypeDetails.each {
                it.feedRunReportMessage = noERFoundForAccessType
                noERFoundForAccessType.details << it
            }
            reportMessages.add(noERFoundForAccessType)
        }

        if (invalidAccessStatusDetails) {
            invalidAccessStatus.message = 'Worker in Feed but revoked in Central'
            invalidAccessStatus.numberOfRecords = invalidAccessStatusDetails.size()
            invalidAccessStatusDetails.each {
                it.feedRunReportMessage = invalidAccessStatus
                invalidAccessStatus.details << it
            }
            reportMessages.add(invalidAccessStatus)
        }

        if (activeInCareButNotInFeedDetails) {
            activeInCareButNotInFeed.message = 'Worker not in Feed but active in Central'
            activeInCareButNotInFeed.numberOfRecords = activeInCareButNotInFeedDetails.size()
            activeInCareButNotInFeedDetails.each {
                it.feedRunReportMessage = activeInCareButNotInFeed
                activeInCareButNotInFeed.details << it
            }
            reportMessages.add(activeInCareButNotInFeed)
        }

        if (misMatchInWorkerEntitlementRolesDetails) {
            misMatchInWorkerEntitlementRoles.message = 'Active worker feed worker role is not the same as central worker role'
            misMatchInWorkerEntitlementRoles.numberOfRecords = misMatchInWorkerEntitlementRolesDetails.size()
            misMatchInWorkerEntitlementRolesDetails.each {
                it.feedRunReportMessage = misMatchInWorkerEntitlementRoles
                misMatchInWorkerEntitlementRoles.details << it
            }
            reportMessages.add(misMatchInWorkerEntitlementRoles)
        }

        if (workersWithNoSupervisorDetails) {
            workersWithNoSupervisor.message = 'Could not provision worker since no Supervisor found'
            workersWithNoSupervisor.numberOfRecords = workersWithNoSupervisorDetails.size()
            workersWithNoSupervisorDetails.each {
                it.feedRunReportMessage = workersWithNoSupervisor
                workersWithNoSupervisor.details << it
            }
            reportMessages.add(workersWithNoSupervisor)
        }
        return reportMessages
    }

    void startWorkflows() {

    }
}
