package com.force5solutions.care.feed

import com.force5solutions.care.cc.Employee
import groovy.sql.GroovyRowResult
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.plugins.versionable.VersioningContext
import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.common.RunTimeStampHelper

class HrInfoFeedService extends DatabaseFeedService {

    def employeeUtilService = ApplicationHolder?.application?.mainContext?.getBean("employeeUtilService")
    List<FeedRunReportMessage> reportMessages = []

    FeedRunReportMessage exceptionWhileSavingHrInfo = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    FeedRunReportMessage recordsCreatedMessage = new FeedRunReportMessage(type: FeedReportMessageType.INFO)
    Integer hrInfosCreated = 0
    Integer exceptionWhileSavingHrInfoCount = 0
    List<FeedRunReportMessageDetail> hrInfosCreatedDetails = []

    HrInfoFeedService() {
        feedName = CareConstants.FEED_EMPLOYEE_HR_INFO
        driver = ConfigurationHolder.config.feed.hrInfoFeed.driver
        url = ConfigurationHolder.config.feed.hrInfoFeed.url
        query = ConfigurationHolder.config.feed.hrInfoFeed.query
    }

    boolean transactional = false;

    private List<FeedRunReportMessage> updateEmployeesInformation() {
        List<FeedRunReportMessage> reportMessages = []
        Integer numberOfRecordsProcessed = 0
        Integer numberOfExceptions = 0
        List<FeedRunReportMessageDetail> exceptionMessageDetails = []
        List<FeedRunReportMessageDetail> supervisorSetToNullDetails = []

        List<Employee> employees = Employee.list()
        employees.each {Employee employee ->
            HrInfo hrInfo = HrInfo.findBySlid(employee.slid)
            if (hrInfo) {
                boolean hasSupervisor = employee.supervisor ? true : false
                VersioningContext.set(UUID.randomUUID().toString())
                employee = employeeUtilService.createOrUpdateEmployee(hrInfo)
                if (hasSupervisor && !employee.supervisor && employee.hasActiveEntitlementRole()) {
                    FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "Employee", param1: employee.toString(), param2: employee.slid, entityId: employee.id)
                    supervisorSetToNullDetails.add(messageDetail)
                }
                numberOfRecordsProcessed++
            } else {
                FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "Employee", param1: employee.toString(), param2: employee.slid, entityId: employee.id)
                exceptionMessageDetails.add(messageDetail)
                numberOfExceptions++
            }
        }

        FeedRunReportMessage reportMessage = new FeedRunReportMessage()
        reportMessage.type = FeedReportMessageType.INFO
        reportMessage.operation = FeedOperation.PROCESS
        reportMessage.numberOfRecords = numberOfRecordsProcessed
        reportMessages.add(reportMessage)

        if (numberOfExceptions) {
            reportMessage = new FeedRunReportMessage()
            reportMessage.type = FeedReportMessageType.EXCEPTION
            reportMessage.message = "Record in Central but not in HR_INFO Feed"
            reportMessage.numberOfRecords = numberOfExceptions
            exceptionMessageDetails.each {FeedRunReportMessageDetail messageDetail ->
                messageDetail.feedRunReportMessage = reportMessage
                reportMessage.addToDetails(messageDetail)
            }
            reportMessages.add(reportMessage)
        }

        if (supervisorSetToNullDetails) {
            reportMessage = new FeedRunReportMessage()
            reportMessage.type = FeedReportMessageType.WARNING
            reportMessage.message = "Employee Supervisor set to NULL"
            reportMessage.numberOfRecords = supervisorSetToNullDetails?.size()
            supervisorSetToNullDetails.each {FeedRunReportMessageDetail messageDetail ->
                messageDetail.feedRunReportMessage = reportMessage
                reportMessage.addToDetails(messageDetail)
            }
            reportMessages.add(reportMessage)
        }
        return reportMessages
    }


    def process(def feedVO) {
        try {
            HrInfoFeedVO hrInfoFeedVO = (HrInfoFeedVO) feedVO;
            HrInfo hrInfo = hrInfoFeedVO.toHrInfo().s()
            hrInfosCreated++
        } catch (Throwable t) {
            t.printStackTrace();
            exceptionWhileSavingHrInfoCount++
        }
    }

    public Map getQueryParameters() {
        return [:]
    }

    void postProcess() {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        updateEmployeesInformation()?.each {FeedRunReportMessage feedRunReportMessage ->
            reportMessages.add(feedRunReportMessage)
        }
        log.debug "Time taken to update Employees info. and adding report messages: ${runTimeStampHelper.stamp()}"
        println "Time taken to update Employees info. and adding report messages:: ${runTimeStampHelper.stamp()}"
    }

    void preProcess() {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        HrInfo.executeUpdate('delete from HrInfo')
        log.debug "Time taken to delete HrInfo records from the local database: ${runTimeStampHelper.stamp()}"
        println "Time taken to delete HrInfo records from the local database: ${runTimeStampHelper.stamp()}"
    }

    List getVOs(def rows) {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        List<HrInfoFeedVO> hrInfoFeedVOs = []
        rows?.each {GroovyRowResult rowResult ->
            hrInfoFeedVOs.add(new HrInfoFeedVO(rowResult))
        }
        log.debug "Time taken to create VOs from the row result: ${runTimeStampHelper.stamp()}"
        println "Time taken to create VOs from the row result: ${runTimeStampHelper.stamp()}"
        return hrInfoFeedVOs
    }

    List<FeedRunReportMessage> getFeedRunReportMessages() {
        if (exceptionWhileSavingHrInfoCount) {
            exceptionWhileSavingHrInfo.with {
                message = 'Exception while saving data from Feed in HrInfo table'
                numberOfRecords = exceptionWhileSavingHrInfoCount
            }
            reportMessages.add(exceptionWhileSavingHrInfo)
        }

        if (hrInfosCreated) {
            recordsCreatedMessage.operation = FeedOperation.CREATE
            recordsCreatedMessage.numberOfRecords = hrInfosCreated
            reportMessages.add(recordsCreatedMessage)
        }

        return reportMessages
    }

    void preValidate() {
    }

    void startWorkflows() {

    }
}
