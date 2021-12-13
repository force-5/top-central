package com.force5solutions.care.feed

import com.force5solutions.care.cc.Contractor
import com.force5solutions.care.cc.Course
import com.force5solutions.care.cc.Worker
import com.force5solutions.care.cc.WorkerCourse
import com.force5solutions.care.common.CareConstants
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.plugins.versionable.VersioningContext

class ContractorCourseFileFeedService extends FileFeedService {

    boolean transactional = false
    def workerCourseService = ApplicationHolder?.application?.mainContext?.getBean("workerCourseService")

    List<FeedRunReportMessage> reportMessages = []
    List<String> courseNames
    String courseNamesString

    FeedRunReportMessage exceptionWhileSavingWorkerCourseInfo = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    FeedRunReportMessage exceptionWhileCreatingVO = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)
    FeedRunReportMessage exceptionMisMatchInWorkerCoursesAndFeedInfo = new FeedRunReportMessage(type: FeedReportMessageType.EXCEPTION)

    FeedRunReportMessage workerCourseInfoRecordCreatedMessage = new FeedRunReportMessage(type: FeedReportMessageType.INFO)
    FeedRunReportMessage workerCourseRecordCreatedMessage = new FeedRunReportMessage(type: FeedReportMessageType.INFO)

    FeedRunReportMessage exceptionWhileImportingData = new FeedRunReportMessage(type: FeedReportMessageType.ERROR)
    FeedRunReportMessage coursesNotFoundInDatabase = new FeedRunReportMessage(type: FeedReportMessageType.ERROR)

    Integer workerCourseInfoCreatedCount = 0
    Integer workerCourseCreatedCount = 0

    Integer exceptionWhileSavingWorkerCourseInfoCount = 0
    Integer exceptionWhileCreatingVOCount = 0
    Integer exceptionWhileImportingDataCount = 0
    Integer exceptionMisMatchInWorkerCoursesAndFeedInfoCount = 0

    List<FeedRunReportMessageDetail> workerCourseInfoCreatedDetails = []
    List<FeedRunReportMessageDetail> workerCourseCreatedDetails = []
    List<FeedRunReportMessageDetail> exceptionMisMatchInWorkerCoursesAndFeedInfoDetails = []

    ContractorCourseFileFeedService() {
        feedName = CareConstants.FEED_CONTRACTOR_COURSE
        fileName = ConfigurationHolder.config.feed.contractorCourseFileFeed.fileName
    }

    void preValidate() {
        courseNames = Course.list()?.collect { it.name }?.unique()
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
                // Get only the Contractor rows
                rows = rows?.findAll { (it.split('\\|')?.toList()*.trim()?.get(2)?.toString()?.equalsIgnoreCase('Contractor')) }
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
        List<ContractorPraAndCourseFeedVO> courseFeedVOs = []
        try {
            rows.each { String rowResult ->
                if (ContractorHrInfo.findByContractorSlid(rowResult?.split('\\|')?.toList()*.trim()?.get(0)?.toString())) {
                    courseFeedVOs.add(new ContractorPraAndCourseFeedVO(rowResult))
                }
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
                message = 'Exception while saving data from Feed in ContractorPraAndCourseInfo table'
                numberOfRecords = exceptionWhileSavingWorkerCourseInfoCount
            }
            reportMessages.add(exceptionWhileSavingWorkerCourseInfo)
        }

        if (exceptionMisMatchInWorkerCoursesAndFeedInfoCount) {
            exceptionMisMatchInWorkerCoursesAndFeedInfo.with {
                message = 'Mis-match in Active Contractors Courses and Feed Information'
                numberOfRecords = exceptionMisMatchInWorkerCoursesAndFeedInfoCount
                details = exceptionMisMatchInWorkerCoursesAndFeedInfoDetails
            }
            reportMessages.add(exceptionMisMatchInWorkerCoursesAndFeedInfo)
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
            workerCourseInfoRecordCreatedMessage.message = "Created ContractorPraAndCourseInfo objects"
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
        ContractorPraAndCourseInfo contractorPraAndCourseInfo
        try {
            contractorPraAndCourseInfo = courseFeedVO.toContractorPraAndCourseInfo().s()
        } catch (Exception e) {
            exceptionWhileSavingWorkerCourseInfoCount++
            e.printStackTrace()
        }

        if (contractorPraAndCourseInfo?.id) {
            FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "ContractorPraAndCourseInfo", param1: contractorPraAndCourseInfo.toString(), entityId: contractorPraAndCourseInfo.id)
            workerCourseInfoCreatedCount++
            workerCourseInfoCreatedDetails.add(messageDetail)
        }
    }

    void postProcess() {
        addCoursesToWorkers()
    }

    void preProcess() {
        ContractorPraAndCourseInfo.executeUpdate('delete from ContractorPraAndCourseInfo')
    }

    void addCoursesToWorkers() {
        FeedRunReportMessage reportMessage
        List<Course> courses = Course.list()
        List<Worker> contractors = Contractor.list()
        List<String> workerSlids = contractors*.slid
        List<String> courseNames = courses*.name

        if (workerSlids && courseNames) {
            List<ContractorPraAndCourseInfo> praAndCourseInfos = ContractorPraAndCourseInfo.findAllByContractorSlidInListAndCourseNameInList(workerSlids, courseNames)
            praAndCourseInfos.each { ContractorPraAndCourseInfo contractorPraAndCourseInfo ->
                Worker worker = contractors.find { it.slid == contractorPraAndCourseInfo.contractorSlid }
                Course course = courses.find { it.name.contains(contractorPraAndCourseInfo.courseName) }
                Collection<WorkerCourse> workerCourses = worker.courses
                WorkerCourse currentCourse

                workerCourses.each { WorkerCourse workerCourse ->
                    if (workerCourse.course.endDate && !(workerCourse.course.name in ContractorPraAndCourseInfo.findAllByContractorSlid(worker.slid as String)*.courseName)) {
                        FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "WorkerCourse", param1: workerCourse.toString(), param2: workerCourse.course, param3: worker.toString(), entityId: workerCourse.id)
                        exceptionMisMatchInWorkerCoursesAndFeedInfoCount++
                        exceptionMisMatchInWorkerCoursesAndFeedInfoDetails.add(messageDetail)
                    }
                }

                if (workerCourses) {
                    workerCourses = workerCourses.findAll { it.course == course }
                    currentCourse = workerCourses?.max { it.dateCreated }
                }

                if (!currentCourse || (currentCourse?.dateCompleted?.myDateTimeFormat() != contractorPraAndCourseInfo?.startDate?.myDateTimeFormat())) {
                    WorkerCourse workerCourse = workerCourseService.addCourse(worker, course, contractorPraAndCourseInfo?.startDate)
                    worker = worker.refresh()
                    FeedRunReportMessageDetail messageDetail = new FeedRunReportMessageDetail(entityType: "ContractorPraAndCourseInfo", param1: course.toString(), param2: worker.toString(), entityId: workerCourse.id)
                    workerCourseCreatedCount++
                    workerCourseCreatedDetails.add(messageDetail)
                    VersioningContext.set(UUID.randomUUID().toString())
                }
            }
        }
    }

    void startWorkflows() {}
}
