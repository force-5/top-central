package com.force5solutions.care.feed

import com.force5solutions.care.common.CareConstants
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.cc.*
import org.grails.plugins.versionable.VersioningContext

class EmployeeCourseFileFeedService extends FileFeedService {

    boolean transactional = true
    def workerCourseService = ApplicationHolder?.application?.mainContext?.getBean("workerCourseService")

    static config = ConfigurationHolder.config

    List<FeedRunReportMessage> reportMessages = []
    List<String> courseNames

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

    EmployeeCourseFileFeedService() {
        feedName = CareConstants.FEED_EMPLOYEE_COURSE
        fileName = ConfigurationHolder.config.feed.employeeCourseFileFeed.fileName
    }

    void preValidate() {
        List<Course> courseList = Course.list()
        courseNames = courseList?.collect { it.name.trim() }?.unique()
        if (!courseNames) {
            coursesNotFoundInDatabase.with {
                message = 'No Courses found in Database'
            }
            reportMessages.add(coursesNotFoundInDatabase)
        }
    }

    List<String> getRows() {
        List<String> rows = []
        List<String> finalRows = []
        if (courseNames) {
            try {
                Map queryParams = getQueryParameters()
                rows = getDataAndErrorReport(queryParams)
                rows = rows.findAll { it }
                // Get only the Employee rows
                rows = rows?.findAll { (it.split('\\|')?.toList()*.trim()?.get(2)?.toString()?.equalsIgnoreCase('Employee')) }
                // Get only those rows which have a course already saved in the Course domain in the database
                rows.each { String row ->
                    if (row.split('\\|').toList()*.trim().get(5).toString() in courseNames) {
                        finalRows.add(row)
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
                exceptionWhileImportingDataCount++
            }
        }
        return finalRows
    }

    List getVOs(def rows) {
        List<EmployeePraAndCourseFeedVO> courseFeedVOs = []
        try {
            rows.each { String rowResult ->
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
        return [:]
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
        List<String> courseNames = courses*.name

        if (workerSlids && courseNames) {
            List<WorkerCourseInfo> workerCourseInfos = WorkerCourseInfo.findAllByEmployeeSlidInListAndCourseNameInList(workerSlids, courseNames)
            workerCourseInfos.each { WorkerCourseInfo workerCourseInfo ->
                Worker worker = workers.find { it.slid.toString().equalsIgnoreCase(workerCourseInfo.employeeSlid) }
                Course course = courses.find { it.name.equalsIgnoreCase(workerCourseInfo.courseName) }
                Collection<WorkerCourse> workerCourses = worker.courses
                WorkerCourse currentCourse

                if (workerCourses) {
                    workerCourses = workerCourses.findAll { it.course == course }
                    currentCourse = workerCourses?.max { it.dateCreated }
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

    void startWorkflows() {}
}
