package com.force5solutions.care.feed

import com.force5solutions.care.common.CareConstants
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.cc.*

class ContractorPraFileFeedService extends FileFeedService {

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
    List<String> contractorSlids = []

    ContractorPraFileFeedService() {
        feedName = CareConstants.FEED_CONTRACTOR_PRA
        fileName = ConfigurationHolder.config.feed.contractorPraFileFeed.fileName

        praCertification = (config.contractorPraCertificationId) ? Certification.get(config.contractorPraCertificationId?.toLong()) : null
        List<String> workerSlids = Contractor.list()*.slid
        workerSlids = workerSlids.findAll { it }
        if (workerSlids) {
            contractorSlids = ContractorHrInfo.findAllByContractorSlidInList(workerSlids)*.contractorSlid
        }
    }

    void preValidate() {
        if (!praCertification) {
            throw new Exception((config.contractorPraCertificationId) ? "No PRA Certification found in database" : "No contractorPraCertificationId found in Configuration File")
        }
    }

    void preProcess() {
        ContractorPraAndCourseInfo.executeUpdate("delete from ContractorPraAndCourseInfo")
    }

    Map getQueryParameters() {
        return [:]
    }

    List getVOs(def rows) {
        List<ContractorPraAndCourseFeedVO> praFeedVOs = []
        rows = rows.findAll { it }
        // Get only the Contractor PRA rows
        rows = rows.findAll { (it?.split('\\|')?.toList()*.trim()?.get(5)?.toString()?.equalsIgnoreCase('CONTRACTOR PRA')) }

        rows?.each { String rowResult ->
            if (ContractorHrInfo.findByContractorSlid(rowResult?.split('\\|')?.toList()*.trim()?.get(0)?.toString())) {
                praFeedVOs.add(new ContractorPraAndCourseFeedVO(rowResult))
            }
        }
        return praFeedVOs
    }

    def process(Object feedVO) {
        try {
            ContractorPraAndCourseFeedVO contractorPraAndCourseFeedVO = (ContractorPraAndCourseFeedVO) feedVO
            ContractorPraAndCourseInfo contractorPraAndCourseInfo = contractorPraAndCourseFeedVO.toContractorPraAndCourseInfo().s()
            FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "ContractorPraAndCourseInfo", param1: contractorPraAndCourseInfo.toString(), param2: contractorPraAndCourseInfo.contractorSlid, entityId: contractorPraAndCourseInfo.id)
            workerPraCertificationInfosCreated++
            workerPraCertificationInfosCreatedDetails.add(messageDetail)
        } catch (Throwable t) {
            t.printStackTrace();
            exceptionWhileSavingCount++
        }
    }

    void postProcess() {
        if (praCertification && contractorSlids) {
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
        List<ContractorPraAndCourseInfo> contractorPraAndCourseInfos = ContractorPraAndCourseInfo.list()
        Integer certificationsInCareButNotInFeedExceptionCount = 0
        Integer certificationsInCareIsNewerThanFeedExceptionCount = 0
        Integer certificationsCreatedCount = 0
        Integer certificationsUpdatedCount = 0
        List<FeedRunReportMessageDetail> certificationsInCareButNotInFeedDetails = []
        List<FeedRunReportMessageDetail> certificationsInCareIsNewerThanFeedExceptionDetails = []
        List<FeedRunReportMessageDetail> certificationsCreatedDetails = []
        List<FeedRunReportMessageDetail> certificationsUpdatedDetails = []
        Contractor.list().each { Worker worker ->
            WorkerCertification workerPraCertification = worker.currentCertifications?.find { it.certification == praCertification }
            ContractorPraAndCourseInfo contractorPraAndCourseInfo = contractorPraAndCourseInfos.find { it?.contractorSlid?.toString()?.equalsIgnoreCase(worker?.slid?.toString()) }
            if ((!contractorPraAndCourseInfo) && workerPraCertification) {
                if (worker?.status == WorkerStatus?.ACTIVE) {
                    certificationsInCareButNotInFeedExceptionCount++
                    FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "Worker Certification", param1: worker.toString(), param2: worker?.slid, entityId: workerPraCertification.id)
                    messageDetail.param1 = "${workerPraCertification}"
                    certificationsInCareButNotInFeedDetails.add(messageDetail)
                }
            } else if (contractorPraAndCourseInfo) {
                //TODO: Something unexpected. Time gets cleared from following dates only when calling clearTime() twice.
                contractorPraAndCourseInfo?.startDate?.clearTime()
                workerPraCertification?.dateCompleted?.clearTime()
                contractorPraAndCourseInfo?.startDate?.clearTime()
                workerPraCertification?.dateCompleted?.clearTime()
                if (!workerPraCertification) {
                    WorkerCertification workerCertification = workerCertificationService.saveNewWorkerCertificationWithDateCompleted(worker.id, praCertification.id, contractorPraAndCourseInfo.startDate)
                    certificationsCreatedCount++
                    FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "Worker Certification", param1: worker.toString(), param2: worker.slid, entityId: workerCertification.id)
                    messageDetail.param1 = "${workerCertification}"
                    certificationsCreatedDetails.add(messageDetail)
                } else if (contractorPraAndCourseInfo?.startDate?.time > workerPraCertification?.dateCompleted?.time) {
                    WorkerCertification workerCertification = workerCertificationService.saveNewWorkerCertificationWithDateCompleted(worker.id, praCertification.id, contractorPraAndCourseInfo.startDate)
                    certificationsUpdatedCount++
                    FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "Worker Certification", param1: worker.toString(), param2: worker.slid, entityId: workerCertification.id)
                    messageDetail.param1 = "${workerCertification}"
                    certificationsUpdatedDetails.add(messageDetail)
                } else if ((contractorPraAndCourseInfo?.startDate?.time < workerPraCertification?.dateCompleted?.time)) {
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
            reportMessage.message = 'Contractor PRA Certification Created'
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
            reportMessage.message = 'Contractor PRA Certification Updated'
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
            reportMessage.message = "Certifications in TOP but not in Contractor PRA Feed"
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
            reportMessage.message = "Certifications in TOP are newer than in Contractor PRA Feed"
            reportMessage.numberOfRecords = certificationsInCareIsNewerThanFeedExceptionCount
            certificationsInCareIsNewerThanFeedExceptionDetails.each {
                it.feedRunReportMessage = reportMessage
                reportMessage.details << it
            }
            reportMessages.add(reportMessage)
        }
        return reportMessages
    }

    void startWorkflows() {}
}
