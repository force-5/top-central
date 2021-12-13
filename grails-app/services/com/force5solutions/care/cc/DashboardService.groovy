package com.force5solutions.care.cc

import com.force5solutions.care.feed.FeedRun
import com.force5solutions.care.feed.FeedRunReportMessage
import com.force5solutions.care.common.RunTimeStampHelper
import com.force5solutions.care.workflow.CentralWorkflowType
import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.workflow.WorkflowTaskStatus
import com.force5solutions.care.common.SessionUtils
import java.text.DecimalFormat
import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.feed.FeedReportMessageType
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.commons.ConfigurationHolder


class DashboardService {

    boolean transactional = false
    def centralUtilService

    List<ActionRequestVO> getActionRequests(String workerType, String viewType = null) {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entering method getActionRequests() for workerType, viewType = ${workerType},${viewType}")
        List<ActionRequestVO> actionRequestVOs = []
        List<CentralWorkflowType> excludedWorkflowsForReporting = [CentralWorkflowType.ACCESS_GRANTED_BY_FEED,
                                                                    CentralWorkflowType.ACCESS_REVOKED_BY_FEED,
                                                                    CentralWorkflowType.CERTIFICATION_EXPIRATION_NOTIFICATION,
                                                                    CentralWorkflowType.ACCESS_VERIFICATION]

        Map taskListGrouped = [:]
        List<CentralWorkflowTask> tasks = CentralWorkflowTask.findAllByWorkflowTypeNotInList(excludedWorkflowsForReporting)
        tasks = filterTasksBasedOnInheritanceForLoggedInUser(tasks, viewType, workerType)
        // TODO: Can't understand the logic here, if workerType is present, then filter tasks based on worker type, else do-not. I assume that we should always have workerType
        tasks = workerType ? filterTasksOnWorkerType(tasks, workerType) : tasks
        taskListGrouped = tasks?.groupBy {it.workflowGuid}?.groupBy {it.value.first()?.workflowType?.name}

        // For now, going for access verification tasks only if the workerType is employee. I see 2 improvements here:
        // TODO:
        // a. Based on the worker type, we should be interested in a different set of workflows
        // b. Instead of passing in a list of workflows to be excluded, we should pass a list of workflows to be included.
        if (workerType.equals(CareConstants.WORKER_TYPE_EMPLOYEE)) {
            Map accessVerificationTaskListGrouped = getAccessVerificationTaskMap(workerType, viewType)
            accessVerificationTaskListGrouped = accessVerificationTaskListGrouped.groupBy {it.value.first()?.workflowType?.name}
            taskListGrouped = taskListGrouped + accessVerificationTaskListGrouped
        }

        Integer total
        Integer completed
        Boolean isCompleted
        taskListGrouped.each {
            total = completed = 0
            total = it.value?.size()
            CentralWorkflowType workflowType = CentralWorkflowType.findKey(it.key as String)
            it.value?.each {task ->
                isCompleted = true
                task?.value?.each {entry ->
                    if (entry.status != WorkflowTaskStatus.COMPLETE) {
                        isCompleted = false
                    }
                }
                if (isCompleted) {
                    completed++
                }
            }
            actionRequestVOs.add(new ActionRequestVO((it.key as String), (total - completed).toString(), completed && (workflowType == CentralWorkflowType.ACCESS_VERIFICATION) ? completed.toString() : ((workflowType == CentralWorkflowType.ACCESS_VERIFICATION) ? 0.toString() : 'n/a')))
        }
        log.debug("Leaving method getActionRequests() ${runTimeStampHelper.stamp()} ")
        return actionRequestVOs
    }

    // TODO: There is no point in defaulting the value of workerType & viewType to null here. This method should always get some value. We should not be defaulting any values here.
    public Map getAccessVerificationTaskMap(String workerType = null, String viewType = null) {
        Map accessVerificationTaskListGrouped = [:]
        List<CentralWorkflowTask> accessVerificationTasks = CentralWorkflowTask.findAllByWorkflowType(CentralWorkflowType.ACCESS_VERIFICATION)

        if (viewType) {
            accessVerificationTasks = filterTasksBasedOnInheritanceForLoggedInUser(accessVerificationTasks, viewType, workerType)
        }

        if (workerType) {
            accessVerificationTasks = filterTasksOnWorkerType(accessVerificationTasks, workerType)
        }

        if (accessVerificationTasks) {
            Long timeInterval = 7200000 // equals to two hour in milliseconds
            CentralWorkflowTask latestAccessVerificationTask = accessVerificationTasks.max {it.dateCreated}
            CentralWorkflowTask originalAccessVerificationTask = CentralWorkflowTask?.findAllByWorkflowGuid(latestAccessVerificationTask?.workflowGuid)?.sort {it?.id}?.first()
            Date originalAccessVerificationTaskDate = originalAccessVerificationTask.dateCreated
            accessVerificationTaskListGrouped = accessVerificationTasks.findAll {it.dateCreated > (new Date(originalAccessVerificationTaskDate.time - timeInterval))}?.groupBy {it.workflowGuid}
        }
        return accessVerificationTaskListGrouped
    }

    List<WorkerStatusVO> getWorkerStatuses(String workerType, String viewType) {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entered method getWorkerStatuses()")
        List<WorkerStatusVO> workerStatusVOs = []
        List<Worker> workers = getWorkersBasedOnWorkerType(workerType)
        workers = centralUtilService.filterWorkersBasedOnInheritanceForLoggedInUserCached(workers, viewType, workerType)
        workerStatusVOs = getActiveWorkerStatuses(workerType, viewType)
        List<WorkerStatus> nonActiveStatuses = [WorkerStatus.INACTIVE, WorkerStatus.TERMINATED, WorkerStatus.UNASSIGNED]
        nonActiveStatuses.each {WorkerStatus status ->
            workerStatusVOs << new WorkerStatusVO(status.toString(), workers.findAll {it.status == status}?.size())
        }
        log.debug("Leaving method getWorkerStatuses() ${runTimeStampHelper.stamp()}")
        return workerStatusVOs
    }

    List getActiveWorkerStatuses(String workerType, String viewType) {
        // Splitting the Active workers acc. to Entitlement Policies now
        List<WorkerStatusVO> workerStatusVOs = []
        Integer physicalOnlyCount = 0
        Integer cyberOnlyCount = 0
        Integer bothCount = 0
        String accessType
        List<Worker> workers = getWorkersBasedOnWorkerType(workerType)
        workers = centralUtilService.filterWorkersBasedOnInheritanceForLoggedInUserCached(workers, viewType, workerType)
        workers = workers.findAll { it.status == WorkerStatus.ACTIVE}
        workers.each { Worker worker ->
            accessType = worker.accessTypeForWorkerBasedOnConfigProperties
            switch (accessType) {
                case CareConstants.ACCESS_TYPE_PHYSICAL_ONLY:
                    physicalOnlyCount++
                    break
                case CareConstants.ACCESS_TYPE_CYBER_ONLY:
                    cyberOnlyCount++
                    break
                case CareConstants.ACCESS_TYPE_BOTH:
                    bothCount++
                    break
            }
        }
        workerStatusVOs.add(new WorkerStatusVO(CareConstants.ACCESS_TYPE_PHYSICAL_ONLY, physicalOnlyCount))
        workerStatusVOs.add(new WorkerStatusVO(CareConstants.ACCESS_TYPE_CYBER_ONLY, cyberOnlyCount))
        workerStatusVOs.add(new WorkerStatusVO(CareConstants.ACCESS_TYPE_BOTH, bothCount))
        return workerStatusVOs
    }

    List<ExpirationForecastVO> getExpirationForecastsByCertification(Date date, Certification certification = null, String workerType, String viewType = CareConstants.VIEW_TYPE_GLOBAL) {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entered method getExpirationForecastsByCertification(Date date, Certification certification)")
        List<WorkerCertification> workerCertifications = getEffectiveCertificationsFromRequest(workerType, viewType)
        workerCertifications = certification ? workerCertifications?.findAll {it.certification == certification} : workerCertifications
        List<ExpirationForecastVO> expirationForecastVOs = []
        if (workerCertifications) {
            expirationForecastVOs = prepareExpirationForecastVOs(workerCertifications, date, certification)
        }
        log.debug("Leaving method getExpirationForecastsByCertification(Date date, Certification certification) ${runTimeStampHelper.stamp()}")
        return expirationForecastVOs
    }

    List<ExpirationForecastVO> prepareExpirationForecastVOs(List<WorkerCertification> workerCertifications, Date date, Certification certification) {
        List<ExpirationForecastVO> expirationForecastVOs = []
        List workerIds = []
        List days = [1, 7, 30, 60]
        days.eachWithIndex {Integer day, Integer index ->
            if (index < (days.size())) {
                workerIds = workerCertifications.findAll {(!(it.isExpired()) && (it.fudgedExpiry > (date + (index ? days[index - 1] : 0))) && (it.fudgedExpiry <= (date + days[index])))}*.worker?.id
                workerIds = workerIds?.unique()
                expirationForecastVOs.add(new ExpirationForecastVO("${days[index]} Days", workerIds, certification?.id))
            }
        }
        return expirationForecastVOs
    }

    List<FeedInformationVO> getFeedInformationData(Date selectedDate, String workerType) {
        List<FeedInformationVO> feedInformationVOs = []
        List<String> feedNames = []
        if (workerType.equals(CareConstants.WORKER_TYPE_CONTRACTOR)) {
            feedNames = [CareConstants.FEED_CONTRACTOR_HR_INFO, CareConstants.FEED_CONTRACTOR_COURSE, CareConstants.FEED_CONTRACTOR_PRA]
        } else {
            feedNames = [CareConstants.FEED_EMPLOYEE_HR_INFO, CareConstants.FEED_EMPLOYEE_COURSE, CareConstants.FEED_EMPLOYEE_PRA, CareConstants.FEED_ACTIVE_WORKER]
        }

        Map selectedFeedRuns = [:]
        feedNames.each { String name ->
            List<FeedRun> feedRuns = FeedRun.findAllByFeedNameAndStartTimeBetween(name, selectedDate, selectedDate + 1)
            if (feedRuns.size()) {
                selectedFeedRuns[name] = feedRuns.max {it.startTime}
            } else {
                selectedFeedRuns[name] = null
            }
        }
        feedInformationVOs = createFeedInformationVOs(selectedFeedRuns)
        return feedInformationVOs
    }

    List<FeedInformationVO> createFeedInformationVOs(Map selectedFeedRuns) {
        List<FeedInformationVO> feedInformationVOs = []
        SimpleDateFormat dateFormat = new SimpleDateFormat("k:mm")
        selectedFeedRuns.each { key, value ->
            if (value) {
                Integer processedCount, errorCount, exceptionCount
                Date startTime, endTime
                List<FeedRunReportMessage> reportMessages = FeedRunReportMessage.findAllByFeedRun(value as FeedRun)
                Map reportMessageByType = reportMessages.groupBy {it.type}
                processedCount = ((reportMessageByType[FeedReportMessageType.INFO] ? reportMessageByType[FeedReportMessageType.INFO].sum {it.numberOfRecords} : 0) + (reportMessageByType[FeedReportMessageType.WARNING] ? reportMessageByType[FeedReportMessageType.WARNING].sum {it.numberOfRecords} : 0)) as Integer
                errorCount = (reportMessageByType[FeedReportMessageType.ERROR] ? reportMessageByType[FeedReportMessageType.ERROR].sum {it.numberOfRecords} : 0) as Integer
                exceptionCount = (reportMessageByType[FeedReportMessageType.EXCEPTION] ? reportMessageByType[FeedReportMessageType.EXCEPTION].sum {it.numberOfRecords} : 0) as Integer
                startTime = value.startTime
                endTime = value.endTime
                feedInformationVOs.add(new FeedInformationVO(feedRunId: value.id, name: key, processedCount: processedCount.toString(), errorCount: errorCount.toString(), exceptionCount: exceptionCount.toString(), startTime: dateFormat.format(startTime), endTime: dateFormat.format(endTime)))
            } else {
                feedInformationVOs.add(new FeedInformationVO(key as String))
            }
        }
        return feedInformationVOs
    }

    List<ExpirationOutlookVO> getExpirationOutlook(String workerType, String viewType) {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entered Service method getExpirationOutlook()")
        Date date = new Date()
        List<ExpirationOutlookVO> expirationOutlookVOs = []
        List<WorkerCertification> workerCertifications = getEffectiveCertificationsFromRequest(workerType, viewType)
        Date today = new Date()
        Date dateAfterOneYear = new Date()
        dateAfterOneYear.year++
        dateAfterOneYear.date = 0
        Date fudgedExpiry
        workerCertifications = workerCertifications.findAll {WorkerCertification workerCertification ->
            fudgedExpiry = workerCertification.fudgedExpiry
            return ((fudgedExpiry > today) && (fudgedExpiry < dateAfterOneYear))
        }
        Map certificationByMonth = workerCertifications.groupBy {it.fudgedExpiry.format('MMM')}
        12.times {
            expirationOutlookVOs << new ExpirationOutlookVO(date.format('MMM'), certificationByMonth[date.format('MMM')]?.unique {it.worker.id}?.size())
            date.month++
        }
        log.debug("Leaving Service method getExpirationOutlook() ${runTimeStampHelper.stamp()}")
        return expirationOutlookVOs
    }

    List<ExpirationCalendarVO> getCertificationExpirationCount(Date date, String workerType, String viewType) {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entered Service method: getCertificationExpirationCount(Date date) ")
        def care = new com.force5solutions.care.cc.CareTagLib()
        String monthYearString = date.format('MM-yy')
        List<WorkerCertification> workerCertifications = getEffectiveCertificationsFromRequest(workerType, viewType)
        String controller = (workerType && workerType.equals(CareConstants.WORKER_TYPE_CONTRACTOR)) ? 'contractor' : 'employee'
        workerCertifications = workerCertifications.findAll {it.fudgedExpiry.format('MM-yy') == monthYearString}
        Map certificationsByDate = workerCertifications.groupBy {it.fudgedExpiry.format('yyyy-MM-dd')}
        List<ExpirationCalendarVO> calendarVOs = []
        certificationsByDate.eachWithIndex {key, value, Integer index ->
            Date fudgedExpiry = value.first().fudgedExpiry
            value = value.unique {it.worker}
            calendarVOs.add(new ExpirationCalendarVO(index, fudgedExpiry, value.size().toString(), "${care.conditionalCreateLink(controller: controller, action: 'filterList', params: [byDate: value.first().fudgedExpiry, viewType: viewType])}", ""))
        }
        log.debug("Leaving Service method: getCertificationExpirationCount(Date date) ${runTimeStampHelper.stamp()} ")
        return calendarVOs
    }

    List<ExpirationOutlookVO> getExpirationOutlookByCertification(Certification certification, String workerType, String viewType) {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entered Service method getExpirationOutlookByCertification(Certification certification)")
        Date date = new Date()
        List<ExpirationOutlookVO> expirationOutlookVOs = []
        List<WorkerCertification> workerCertifications = getEffectiveCertificationsFromRequest(workerType, viewType)
        workerCertifications = workerCertifications?.findAll {it.certification == certification}
        Date today = new Date()
        Date dateAfterOneYear = new Date()
        dateAfterOneYear.year++
        dateAfterOneYear.date = 0
        Date fudgedExpiry
        workerCertifications = workerCertifications.findAll {WorkerCertification workerCertification ->
            fudgedExpiry = workerCertification.fudgedExpiry
            return ((fudgedExpiry > today) && (fudgedExpiry < dateAfterOneYear))
        }
        Map certificationByMonth = workerCertifications.groupBy {it.fudgedExpiry.format('MMM')}
        12.times {
            expirationOutlookVOs << new ExpirationOutlookVO(date.format('MMM'), certificationByMonth[date.format('MMM')]?.unique {it.worker.id}?.size())
            date.month++
        }
        log.debug("Leaving Service method getExpirationOutlookByCertification(Certification certification) ${runTimeStampHelper.stamp()}")
        return expirationOutlookVOs
    }

    List<CentralWorkflowTask> filterTasksOnWorkerType(List<CentralWorkflowTask> tasks, String workerType) {
        List<CentralWorkflowTask> filteredTasks = []
        filteredTasks = workerType.equals(CareConstants.WORKER_TYPE_EMPLOYEE) ? tasks.findAll {it.isTaskForEmployee()} : tasks.findAll {!it.isTaskForEmployee()}
        return filteredTasks
    }

    List<ActionRequestResponseTimeVO> getActionRequestResponseTimes(String workerType) {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entering method getActionRequestResponseTimes()")
        List<ActionRequestResponseTimeVO> actionRequestResponseTimeVOs = []
        String dateString = ConfigurationHolder.config.dashboardRequestResponseDateRange ?: '90'
        List<CentralWorkflowTask> tasks = CentralWorkflowTask.findAllByWorkflowTypeNotInListAndDateCreatedGreaterThanEquals([CentralWorkflowType.ACCESS_GRANTED_BY_FEED, CentralWorkflowType.ACCESS_REVOKED_BY_FEED], (new Date() - dateString.toInteger()))
        tasks = workerType ? filterTasksOnWorkerType(tasks, workerType) : tasks
        Map tasksByWorkflowType = tasks.groupBy {it.workflowType}
        tasksByWorkflowType.each { key, value ->
            Map typeTasksByWorkflowGuid = value.groupBy {it.workflowGuid}
            Map duplicateMap = new HashMap()
            duplicateMap = typeTasksByWorkflowGuid.clone() as Map
            Set duplicateMapEntrySet = duplicateMap.entrySet()
            List ignoreStatusList = [WorkflowTaskStatus.NEW, WorkflowTaskStatus.PENDING, WorkflowTaskStatus.CANCELLED]
            duplicateMapEntrySet.each {
                if (ignoreStatusList.any {def status -> status in it.value*.status}) {
                    typeTasksByWorkflowGuid.remove(it.key)
                }
            }
            // actionRequestReponseTimeVO would be created only for those type of requests which have at least one fully completed task i.e. end-to-end completion of the workflow.
            if (typeTasksByWorkflowGuid.size()) {
                ActionRequestResponseTimeVO actionRequestResponseTimeVO = createActionRequestVO(key, typeTasksByWorkflowGuid)
                actionRequestResponseTimeVOs.add(actionRequestResponseTimeVO)
            }
        }
        log.debug("Entering method getActionRequestResponseTimes() ${runTimeStampHelper.stamp()}")
        return actionRequestResponseTimeVOs
    }

    private ActionRequestResponseTimeVO createActionRequestVO(def type, Map typeTasksByWorkflowGuid) {
        ActionRequestResponseTimeVO actionRequestResponseTimeVO
        Double averageTimeInHours
        List<Double> timeTakenToCompleteTasksInHours = []
        DecimalFormat decimalFormat = new DecimalFormat("#.###")
        typeTasksByWorkflowGuid.each { key, value ->
            Date firstCreateDate = value.min {it.dateCreated}.dateCreated
            Date lastUpdateDate = value.max {it.lastUpdated}.lastUpdated
            Double timeTakenToCompleteTask = ((lastUpdateDate.time - firstCreateDate.time) / (60 * 60 * 1000))
            timeTakenToCompleteTasksInHours.add(timeTakenToCompleteTask)
        }
        averageTimeInHours = (timeTakenToCompleteTasksInHours.sum() / timeTakenToCompleteTasksInHours.size())
        actionRequestResponseTimeVO = new ActionRequestResponseTimeVO(action: type, shortestTime: decimalFormat.format(timeTakenToCompleteTasksInHours.min()).toDouble(), longestTime: decimalFormat.format(timeTakenToCompleteTasksInHours.max()).toDouble(), averageTime: decimalFormat.format(averageTimeInHours).toDouble())
        return actionRequestResponseTimeVO
    }

    public List getEffectiveCertificationsFromRequest(String workerType, String viewType = CareConstants.VIEW_TYPE_GLOBAL) {
        def request = SessionUtils.request
        if (!(request.effectiveCerifications) || viewType) {
            List<Worker> workers = getWorkersBasedOnWorkerType(workerType)
            workers = centralUtilService.filterWorkersBasedOnInheritanceForLoggedInUserCached(workers, viewType, workerType)
            workers = workers.findAll {it.status == WorkerStatus.ACTIVE}
            List<WorkerCertification> workerCertifications = (workers*.effectiveCertifications)?.flatten()
            request.effectiveCerifications = workerCertifications
        }
        return request.effectiveCerifications as List
    }

    List<Worker> getWorkersBasedOnWorkerType(String workerType) {
        def request = SessionUtils.request
        if (!request.workersBasedOnWorkerType) {
            List<Worker> workers = []
            if (workerType.equals(CareConstants.WORKER_TYPE_EMPLOYEE)) {
                workers = Employee.list()
            } else if ((workerType.equals(CareConstants.WORKER_TYPE_CONTRACTOR))) {
                workers = Contractor.list()
            } else {
                workers = Worker.list()
            }
            request.workersBasedOnWorkerType = workers
        }
        return request.workersBasedOnWorkerType as List
    }

    public List<CentralWorkflowTask> filterTasksBasedOnInheritanceForLoggedInUser(List<CentralWorkflowTask> tasks, String viewType, String workerType) {
        def session = SessionUtils.session
        def supervisor
        List<CentralWorkflowTask> filteredTasks = []
        if (workerType.equals(CareConstants.WORKER_TYPE_EMPLOYEE)) {
            supervisor = EmployeeSupervisor?.findBySlid(session?.loggedUser)
        } else {
            supervisor = BusinessUnitRequester.findBySlid(session?.loggedUser)
        }
        if (supervisor && viewType?.equalsIgnoreCase(CareConstants.VIEW_TYPE_SUPERVISOR)) {
            filteredTasks = filteredTasksBasedOnInheritedSubordinates(tasks, supervisor, workerType)
        } else if ((!supervisor && viewType?.equalsIgnoreCase(CareConstants.VIEW_TYPE_GLOBAL)) || viewType?.equalsIgnoreCase(CareConstants.VIEW_TYPE_GLOBAL)) {
            filteredTasks = tasks
        }
        return filteredTasks
    }

    private List<CentralWorkflowTask> filteredTasksBasedOnInheritedSubordinates(List<CentralWorkflowTask> tasks, def supervisor, String workerType) {
        List<CentralWorkflowTask> filteredTasks = []
        List<Worker> subordinates = []
        if (workerType.equalsIgnoreCase(CareConstants.WORKER_TYPE_EMPLOYEE)) {
            subordinates = EmployeeSupervisor.getInheritedSubordinates(supervisor.slid)
        } else {
            subordinates = BusinessUnitRequester.getInheritedContractors(supervisor.slid)
        }
        Worker supervisorAsWorker = Employee.findBySlid(supervisor.slid) ?: Contractor.findBySlid(supervisor.slid)
        tasks.each { CentralWorkflowTask task ->
            if (task.worker in subordinates || (supervisorAsWorker && task.worker.equals(supervisorAsWorker))) {
                filteredTasks.add(task)
            }
        }
        return filteredTasks
    }

    String convertToJson(List<ExpirationCalendarVO> calendarVOs) {
        String result = '['
        if (calendarVOs.size()) {
            calendarVOs.each {
                result += '{' + it.toJsonString() + '},'
            }
            result = result.substring(0, result.length() - 1)
        }
        result += '];'
        return result
    }
}

class ActionResponseTime {

    String action
    Integer responseTime

}
