package com.force5solutions.care.feed

import com.force5solutions.care.cc.Worker

import com.force5solutions.care.common.CareConstants

import com.force5solutions.care.cc.Certification
import com.force5solutions.care.cc.Person
import com.force5solutions.care.cc.WorkerCertification
import com.force5solutions.care.cc.WorkerStatus
import groovy.sql.GroovyRowResult
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.cc.Employee

class EmployeePraFeedService extends DatabaseFeedService {

    boolean transactional = true
    def workerCertificationService = ApplicationHolder?.application?.mainContext?.getBean("workerCertificationService")
    static config = ConfigurationHolder.config

    FeedRunReportMessage exceptionWhileSaving = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    FeedRunReportMessage recordsCreatedMessage = new FeedRunReportMessage(type: FeedReportMessageType.INFO)
    Integer workerPraCertificationInfosCreated = 0
    Integer exceptionWhileSavingCount = 0
    List<FeedRunReportMessageDetail> workerPraCertificationInfosCreatedDetails = []

    List<FeedRunReportMessage> reportMessages = []
    Certification praCertification
    List<String> personnelSlids = []

    EmployeePraFeedService() {
        feedName = CareConstants.FEED_EMPLOYEE_PRA
        driver = ConfigurationHolder.config.feed.employeePraFeed.driver
        url = ConfigurationHolder.config.feed.employeePraFeed.url
        query = ConfigurationHolder.config.feed.employeePraFeed.query

        praCertification = (config.praCertificationId) ? Certification.get(config.praCertificationId?.toLong()) : null
        personnelSlids = Person.createCriteria().list() {
            projections {
                distinct('slid')
            }
        }
    }

    void preValidate() {
        if (!praCertification) {
            throw new Exception((config.praCertificationId) ? "No PRA Certification found in database" : "No praCertificationId found in Configuration File")
        }
    }

    void preProcess() {
        WorkerPraCertificationInfo.executeUpdate('delete from WorkerPraCertificationInfo where slid is NOT NULL')
    }

    Map getQueryParameters() {
        return [:]
    }

    List getVOs(def rows) {
        List<EmployeePRAFeedVO> praFeedVOs = []
        rows?.each {GroovyRowResult rowResult ->
            praFeedVOs.add(new EmployeePRAFeedVO(rowResult.getProperty('userid') as String, rowResult.getProperty('last_bg_check') as Date))
        }
        return praFeedVOs
    }

    def process(Object feedVO) {
        try {
            EmployeePRAFeedVO praFeedVO = (EmployeePRAFeedVO) feedVO
            WorkerPraCertificationInfo workerPraCertificationInfos = praFeedVO.toEmployeePraCertificationInfo().s()
            FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "WorkerPraCertificationInfo", param1: workerPraCertificationInfos.toString(), param2: workerPraCertificationInfos.slid, entityId: workerPraCertificationInfos.id)
            workerPraCertificationInfosCreated++
            workerPraCertificationInfosCreatedDetails.add(messageDetail)
        } catch (Throwable t) {
            t.printStackTrace();
            exceptionWhileSavingCount++
        }
    }

    void postProcess() {
        if (praCertification && personnelSlids) {
            addPraCertificationToWorkers()
        }
    }

    List<FeedRunReportMessage> getFeedRunReportMessages() {
        if (exceptionWhileSavingCount) {
            exceptionWhileSaving.with {
                message = 'Exception while saving data from Feed in WorkerPraCertificationInfo table'
                numberOfRecords = exceptionWhileSavingCount
            }
            reportMessages.add(exceptionWhileSaving)
        }

        if (workerPraCertificationInfosCreated) {
            recordsCreatedMessage.operation = FeedOperation.CREATE
            recordsCreatedMessage.message = 'WorkerPraCertificationInfo created'
            recordsCreatedMessage.numberOfRecords = workerPraCertificationInfosCreated
            workerPraCertificationInfosCreatedDetails.each {
                it.feedRunReportMessage = recordsCreatedMessage
                recordsCreatedMessage.details << it
            }
            reportMessages.add(recordsCreatedMessage)
        }

        return reportMessages

    }

    private List<FeedRunReportMessage> addPraCertificationToWorkers() {
        List<WorkerPraCertificationInfo> workerPraCertificationInfos = WorkerPraCertificationInfo.list()
        Integer certificationsInCareButNotInFeedExceptionCount = 0
        Integer certificationsInCareIsNewerThanFeedExceptionCount = 0
        Integer certificationsCreatedCount = 0
        Integer certificationsUpdatedCount = 0
        List<FeedRunReportMessageDetail> certificationsInCareButNotInFeedDetails = []
        List<FeedRunReportMessageDetail> certificationsInCareIsNewerThanFeedExceptionDetails = []
        List<FeedRunReportMessageDetail> certificationsCreatedDetails = []
        List<FeedRunReportMessageDetail> certificationsUpdatedDetails = []
        Employee.list().each {Worker worker ->
            WorkerCertification workerPraCertification = worker.currentCertifications?.find {it.certification == praCertification}
            WorkerPraCertificationInfo workerPraCertificationInfo = workerPraCertificationInfos.find {it.slid.toString().equalsIgnoreCase(worker.slid.toString())}
            if ((!workerPraCertificationInfo) && workerPraCertification) {
                if (worker?.status == WorkerStatus?.ACTIVE) {
                    certificationsInCareButNotInFeedExceptionCount++
                    FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "Worker Certification", param1: worker.toString(), param2: worker.slid, entityId: workerPraCertification.id)
                    messageDetail.param1 = "${workerPraCertification}"
                    certificationsInCareButNotInFeedDetails.add(messageDetail)
                }
            } else if (workerPraCertificationInfo) {
                //TODO: Something unexpected. Time gets cleared from following dates only when calling clearTime() twice.
                workerPraCertificationInfo.lastBackgroundDate.clearTime()
                workerPraCertification?.dateCompleted?.clearTime()
                workerPraCertificationInfo.lastBackgroundDate.clearTime()
                workerPraCertification?.dateCompleted?.clearTime()
                if (!workerPraCertification || (workerPraCertificationInfo.lastBackgroundDate?.time > workerPraCertification.dateCompleted?.time)) {
                    WorkerCertification workerCertification = workerCertificationService.saveNewWorkerCertificationWithDateCompleted(worker.id, praCertification.id, workerPraCertificationInfo.lastBackgroundDate)
                    certificationsCreatedCount++
                    FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "Worker Certification", param1: worker.toString(), param2: worker.slid, entityId: workerCertification.id)
                    messageDetail.param1 = "${workerCertification}"
                    certificationsCreatedDetails.add(messageDetail)
                } else if ((workerPraCertificationInfo.lastBackgroundDate?.time < workerPraCertification.dateCompleted?.time)) {
                    if (worker?.status == WorkerStatus?.ACTIVE) {
                        certificationsInCareIsNewerThanFeedExceptionCount++
                        FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "Worker Certification", param1: worker.toString(), param2: worker.slid, entityId: workerPraCertification.id)
                        messageDetail.param1 = "${workerPraCertification}"
                        certificationsInCareIsNewerThanFeedExceptionDetails.add(messageDetail)
                    }
                }
            }
        }

        FeedRunReportMessage reportMessage
        if (certificationsCreatedCount) {
            reportMessage = new FeedRunReportMessage()
            reportMessage.type = FeedReportMessageType.INFO
            reportMessage.operation = FeedOperation.CREATE
            reportMessage.message = 'PRA Certification Created'
            reportMessage.numberOfRecords = certificationsCreatedCount
            certificationsCreatedDetails.each {
                it.feedRunReportMessage = reportMessage
                reportMessage.details << it
            }
            reportMessages.add(reportMessage)
        }

        if (certificationsUpdatedCount) {
            reportMessage = new FeedRunReportMessage()
            reportMessage.type = FeedReportMessageType.INFO
            reportMessage.operation = FeedOperation.UPDATE
            reportMessage.message = 'PRA Certification Updated'
            reportMessage.numberOfRecords = certificationsUpdatedCount
            certificationsUpdatedDetails.each {
                it.feedRunReportMessage = reportMessage
                reportMessage.details << it
            }
            reportMessages.add(reportMessage)
        }

        if (certificationsInCareButNotInFeedExceptionCount) {
            reportMessage = new FeedRunReportMessage()
            reportMessage.type = FeedReportMessageType.EXCEPTION
            reportMessage.message = "Certifications in TOP but not in PRA Feed"
            reportMessage.numberOfRecords = certificationsInCareButNotInFeedExceptionCount
            certificationsInCareButNotInFeedDetails.each {
                it.feedRunReportMessage = reportMessage
                reportMessage.details << it
            }
            reportMessages.add(reportMessage)
        }
        if (certificationsInCareIsNewerThanFeedExceptionCount) {
            reportMessage = new FeedRunReportMessage()
            reportMessage.type = FeedReportMessageType.EXCEPTION
            reportMessage.message = "Certifications in TOP are newer than in PRA Feed"
            reportMessage.numberOfRecords = certificationsInCareIsNewerThanFeedExceptionCount
            certificationsInCareIsNewerThanFeedExceptionDetails.each {
                it.feedRunReportMessage = reportMessage
                reportMessage.details << it
            }
            reportMessages.add(reportMessage)
        }
        return reportMessages
    }

    void startWorkflows(){

    }

}
