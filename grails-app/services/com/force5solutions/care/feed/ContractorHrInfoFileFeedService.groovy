package com.force5solutions.care.feed

import com.force5solutions.care.cc.Contractor
import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.common.RunTimeStampHelper
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.plugins.versionable.VersioningContext

class ContractorHrInfoFileFeedService extends FileFeedService {

    boolean transactional = false

    def contractorUtilService = ApplicationHolder?.application?.mainContext?.getBean("contractorUtilService")
    List<FeedRunReportMessage> reportMessages = []

    FeedRunReportMessage exceptionWhileSavingContractorHrInfo = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    FeedRunReportMessage contractorHrInfoRecordsCreatedMessage = new FeedRunReportMessage(type: FeedReportMessageType.INFO)
    Integer contractorHrInfosCreated = 0
    Integer exceptionWhileSavingContractorHrInfoCount = 0
    List<FeedRunReportMessageDetail> contractorHrInfosCreatedDetails = []

    ContractorHrInfoFileFeedService() {
        feedName = CareConstants.FEED_CONTRACTOR_HR_INFO
        fileName = ConfigurationHolder.config.feed.contractorHrInfoFileFeed.fileName
    }

    private List<FeedRunReportMessage> updateContractorInformation() {
        List<FeedRunReportMessage> reportMessages = []
        Integer numberOfContractorRecordsProcessed = 0
        Integer numberOfExceptions = 0
        List<FeedRunReportMessageDetail> exceptionMessageDetails = []
        List<FeedRunReportMessageDetail> contractorsProcessedMessageDetails = []

        List<Contractor> contractors = Contractor.list()
        contractors.each { Contractor contractor ->
            ContractorHrInfo contractorHrInfo = contractor?.slid && ContractorHrInfo.findByContractorSlid(contractor?.slid?.toString()?.trim()) ? ContractorHrInfo.findByContractorSlid(contractor?.slid?.toString()?.trim()) : (ContractorHrInfo.findByFirstNameAndLastName(contractor?.firstName?.toString()?.trim(), contractor?.lastName?.toString()?.trim()) ?: null)
            if (contractorHrInfo) {
                VersioningContext.set(UUID.randomUUID().toString())
                contractor = contractorUtilService.createOrUpdateContractor(contractorHrInfo)
                FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "Contractor", param1: contractor.toString(), param2: contractor.slid, entityId: contractor.id)
                contractorsProcessedMessageDetails.add(messageDetail)
                numberOfContractorRecordsProcessed++
            } else {
                FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "Contractor", param1: contractor.toString(), param2: contractor.slid, entityId: contractor.id)
                exceptionMessageDetails.add(messageDetail)
                numberOfExceptions++
            }
        }

        FeedRunReportMessage reportMessage = new FeedRunReportMessage()
        reportMessage.type = FeedReportMessageType.INFO
        reportMessage.message = "Records created/updated in Contractor table"
        reportMessage.numberOfRecords = numberOfContractorRecordsProcessed
        contractorsProcessedMessageDetails.each { FeedRunReportMessageDetail messageDetail ->
            messageDetail.feedRunReportMessage = reportMessage
            reportMessage.addToDetails(messageDetail)
        }
        reportMessages.add(reportMessage)

        if (numberOfExceptions) {
            reportMessage = new FeedRunReportMessage()
            reportMessage.type = FeedReportMessageType.EXCEPTION
            reportMessage.message = "Record in Central but not in ContractorHrInfo Feed"
            reportMessage.numberOfRecords = numberOfExceptions
            exceptionMessageDetails.each { FeedRunReportMessageDetail messageDetail ->
                messageDetail.feedRunReportMessage = reportMessage
                reportMessage.addToDetails(messageDetail)
            }
            reportMessages.add(reportMessage)
        }

        return reportMessages
    }


    def process(def feedVO) {
        try {
            ContractorHrInfoFeedVO contractorHrInfoFeedVO = (ContractorHrInfoFeedVO) feedVO;
            ContractorHrInfo contractorHrInfo = contractorHrInfoFeedVO.toContractorHrInfo().s()
            FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "Contractor Hr Info", param1: contractorHrInfo.toString(), param2: contractorHrInfo.firstName, param3: contractorHrInfo.lastName, entityId: contractorHrInfo.lastName)
            contractorHrInfosCreatedDetails.add(messageDetail)
            contractorHrInfosCreated++
        } catch (Throwable t) {
            t.printStackTrace();
            exceptionWhileSavingContractorHrInfoCount++
        }
    }

    public Map getQueryParameters() {
        return [:]
    }

    void postProcess() {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        updateContractorInformation()?.each { FeedRunReportMessage feedRunReportMessage ->
            reportMessages.add(feedRunReportMessage)
        }
        log.debug "Time taken to update Contractors info. and adding report messages: ${runTimeStampHelper.stamp()}"
        println "Time taken to update Contractor info. and adding report messages:: ${runTimeStampHelper.stamp()}"
    }

    void preProcess() {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        ContractorHrInfo.executeUpdate('delete from ContractorHrInfo')
        log.debug "Time taken to delete ContractorHrInfo records from the local database: ${runTimeStampHelper.stamp()}"
        println "Time taken to delete ContractorHrInfo records from the local database: ${runTimeStampHelper.stamp()}"
    }

    List getVOs(def rows) {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        List<ContractorHrInfoFeedVO> contractorHrInfoFeedVOs = []
        rows?.each { String rowResult ->
            if (!rowResult.isEmpty()) {
                contractorHrInfoFeedVOs.add(new ContractorHrInfoFeedVO(rowResult))
            }
        }
        log.debug "Time taken to create VOs from the row result: ${runTimeStampHelper.stamp()}"
        println "Time taken to create VOs from the row result: ${runTimeStampHelper.stamp()}"
        return contractorHrInfoFeedVOs
    }

    List<FeedRunReportMessage> getFeedRunReportMessages() {
        if (exceptionWhileSavingContractorHrInfoCount) {
            exceptionWhileSavingContractorHrInfo.with {
                message = 'Exception while saving data from Feed in ContractorHrInfo table'
                numberOfRecords = exceptionWhileSavingContractorHrInfoCount
            }
            reportMessages.add(exceptionWhileSavingContractorHrInfo)
        }

        if (contractorHrInfosCreated) {
            contractorHrInfoRecordsCreatedMessage.message = 'Contractor HR Info records created'
            contractorHrInfoRecordsCreatedMessage.numberOfRecords = contractorHrInfosCreated
            contractorHrInfoRecordsCreatedMessage.details = contractorHrInfosCreatedDetails
            reportMessages.add(contractorHrInfoRecordsCreatedMessage)
        }

        return reportMessages
    }

    void preValidate() {
    }

    void startWorkflows() {

    }
}
