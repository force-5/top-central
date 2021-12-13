package com.force5solutions.care.feed

import com.force5solutions.care.cc.Course
import com.force5solutions.care.cc.Worker
import com.force5solutions.care.cc.WorkerCourse
import groovy.sql.GroovyRowResult
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.plugins.versionable.VersioningContext
import com.force5solutions.care.common.CareConstants

class EmployeeCourseFeedService extends DatabaseFeedService {

    boolean transactional = false
    def workerCourseService = ApplicationHolder?.application?.mainContext?.getBean("workerCourseService")

    List<FeedRunReportMessage> reportMessages = []
    List<String> courseNumbers
    String courseNumbersString

    FeedRunReportMessage exceptionWhileSavingWorkerCourseInfo = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    FeedRunReportMessage exceptionWhileCreatingVO = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)

    FeedRunReportMessage workerCourseInfoRecordCreatedMessage = new FeedRunReportMessage(type: FeedReportMessageType.INFO)
    FeedRunReportMessage workerCourseRecordCreatedMessage = new FeedRunReportMessage(type: FeedReportMessageType.INFO)

    FeedRunReportMessage exceptionWhileImportingData = new FeedRunReportMessage(type: FeedReportMessageType.ERROR)
    FeedRunReportMessage coursesNotFoundInDatabase = new FeedRunReportMessage(type: FeedReportMessageType.ERROR)

    Integer workerCourseInfoCreatedCount = 0
    Integer workerCourseCreatedCount = 0

    Integer exceptionWhileSavingWorkerCourseInfoCount = 0
    Integer exceptionWhileCreatingVOCount = 0
    Integer exceptionWhileImportingDataCount = 0

    List<FeedRunReportMessageDetail> workerCourseInfoCreatedDetails = []
    List<FeedRunReportMessageDetail> workerCourseCreatedDetails = []

    EmployeeCourseFeedService() {
        feedName = CareConstants.FEED_EMPLOYEE_COURSE
        driver = ConfigurationHolder.config.feed.employeeCourseFeed.driver
        url = ConfigurationHolder.config.feed.employeeCourseFeed.url
        query = ConfigurationHolder.config.feed.employeeCourseFeed.query
    }

    void preValidate() {
        courseNumbers = Course.list()?.collect {it.number}?.unique()
        if (courseNumbers) {
            courseNumbersString = courseNumbers.collect {"'" + it + "'"}.join(',')
        } else {
            coursesNotFoundInDatabase.with {
                message = 'No Courses found in Database'
            }
            reportMessages.add(coursesNotFoundInDatabase)
        }
    }

    List<GroovyRowResult> getRows() {
        List<GroovyRowResult> rows = []
        if (courseNumbers) {
            try {
                Map queryParams = getQueryParameters()
                rows = getDataAndErrorReport(queryParams)
            } catch (Throwable t) {
                t.printStackTrace();
                exceptionWhileImportingDataCount++
            }
        }
        return rows
    }

    List getVOs(def rows) {
        List<EmployeePraAndCourseFeedVO> courseFeedVOs = []
        try {
            rows?.each {GroovyRowResult rowResult ->
                courseFeedVOs.add(new EmployeePraAndCourseFeedVO(rowResult))
            }
        } catch (Throwable t) {
            t.printStackTrace()
            exceptionWhileCreatingVOCount++
        }
        return courseFeedVOs
    }

    List<FeedRunReportMessage> getFeedRunReportMessages() {
        if (exceptionWhileSavingWorkerCourseInfoCount) {
            exceptionWhileSavingWorkerCourseInfo.with {
                message = 'Exception while saving data from Feed in WorkerCourseInfo table'
                numberOfRecords = exceptionWhileSavingWorkerCourseInfoCount
            }
            reportMessages.add(exceptionWhileSavingWorkerCourseInfo)
        }

        if (exceptionWhileCreatingVOCount) {
            exceptionWhileCreatingVO.with {
                message = 'Exception while creating VOs from Result Set'
                numberOfRecords = exceptionWhileCreatingVOCount
            }
            reportMessages.add(exceptionWhileCreatingVO)
        }


        if (exceptionWhileImportingDataCount) {
            exceptionWhileImportingData.with {
                message = "Error occured during ${feedName} data import"
                numberOfRecords = exceptionWhileImportingDataCount
            }
            reportMessages.add(exceptionWhileImportingData)
        }

        if (workerCourseInfoCreatedCount) {
            workerCourseInfoRecordCreatedMessage.operation = FeedOperation.CREATE
            workerCourseInfoRecordCreatedMessage.message = "Created WorkerCourseInfo objects"
            workerCourseInfoRecordCreatedMessage.numberOfRecords = workerCourseInfoCreatedCount
            workerCourseInfoCreatedDetails.each {
                it.feedRunReportMessage = workerCourseInfoRecordCreatedMessage
                workerCourseInfoRecordCreatedMessage.details << it
            }
            reportMessages.add(workerCourseInfoRecordCreatedMessage)
        }

        if (workerCourseCreatedCount) {
            workerCourseRecordCreatedMessage.operation = FeedOperation.CREATE
            workerCourseRecordCreatedMessage.message = "Added courses to workers"
            workerCourseRecordCreatedMessage.numberOfRecords = workerCourseCreatedCount
            workerCourseCreatedDetails.each {
                it.feedRunReportMessage = workerCourseRecordCreatedMessage
                workerCourseRecordCreatedMessage.details << it
            }
            reportMessages.add(workerCourseRecordCreatedMessage)
        }

        return reportMessages
    }

    public Map getQueryParameters() {
        return ['courseNumbers': courseNumbersString]
    }

    public process(def courseFeedVO) {
        WorkerCourseInfo workerCourseInfo
        try {
            workerCourseInfo = courseFeedVO.toWorkerCourseInfo().s()
        } catch (Exception e) {
            exceptionWhileSavingWorkerCourseInfoCount++
            e.printStackTrace()
        }

        if (workerCourseInfo?.id) {
            FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "WorkerCourseInfo", param1: workerCourseInfo.toString(), entityId: workerCourseInfo.id)
            workerCourseInfoCreatedCount++
            workerCourseInfoCreatedDetails.add(messageDetail)
        }
    }

    void postProcess() {
        addCoursesToWorkers()
    }

    void preProcess() {
        WorkerCourseInfo.executeUpdate('delete from WorkerCourseInfo')
    }

    void addCoursesToWorkers() {
        FeedRunReportMessage reportMessage
        List<Course> courses = Course.list()
        List<Worker> workers = Worker.list()
        List<String> workerSlids = workers*.slid
        List<String> courseNumbers = courses*.number

        if (workerSlids && courseNumbers) {
            List<WorkerCourseInfo> workerCourseInfos = WorkerCourseInfo.findAllByEmployeeSlidInListAndCourseNumberInList(workerSlids, courseNumbers)
            workerCourseInfos.each {WorkerCourseInfo workerCourseInfo ->
                Worker worker = workers.find {it.slid.toString().equalsIgnoreCase(workerCourseInfo.employeeSlid)}
                Course course = courses.find {it.number.equalsIgnoreCase(workerCourseInfo.courseNumber)}
                Collection<WorkerCourse> workerCourses = worker.courses
                WorkerCourse currentCourse

                if (workerCourses) {
                    workerCourses = workerCourses.findAll {it.course == course}
                    currentCourse = workerCourses?.max {it.dateCreated}
                }

                if (!currentCourse || (currentCourse?.dateCompleted?.myDateTimeFormat() != workerCourseInfo?.completionDate?.myDateTimeFormat())) {
                    WorkerCourse workerCourse = workerCourseService.addCourse(worker, course, workerCourseInfo.completionDate)
                    worker = worker.refresh()
                    FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "WorkerCourse", param1: course.toString(), param2: course.number, param3: worker.toString(), entityId: workerCourse.id)
                    workerCourseCreatedCount++
                    workerCourseCreatedDetails.add(messageDetail)
                    VersioningContext.set(UUID.randomUUID().toString())
                }
            }
        }
    }

    void startWorkflows() {

    }
}
