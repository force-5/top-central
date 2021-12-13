package com.force5solutions.care.cc

import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.common.Secured
import com.force5solutions.care.cp.ConfigProperty
import com.force5solutions.care.ldap.Permission
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import java.text.SimpleDateFormat

class DashboardController {

    def dashboardService
    def rssFeedsService
    def permissionService
    static config = ConfigurationHolder.config


    def index = {
        forward(action: 'employeeDashboard')
    }

    @Secured(value = Permission.CAN_ACCESS_REPORTS_MENU)
    def employeeDashboard = {
        boolean canSeeGlobalAndSupervisorMode = whetherGlobalAndSupervisorModesAvailable()
        String viewType = getViewType(canSeeGlobalAndSupervisorMode, params.viewType)
        render(view: '/dashboard/index', model: [workerType: CareConstants.WORKER_TYPE_EMPLOYEE, canSeeGlobalAndSupervisorMode: canSeeGlobalAndSupervisorMode, viewType: viewType])
    }

    @Secured(value = Permission.CAN_ACCESS_REPORTS_MENU)
    def contractorDashboard = {
        boolean canSeeGlobalAndSupervisorMode = whetherGlobalAndSupervisorModesAvailable()
        String viewType = getViewType(canSeeGlobalAndSupervisorMode, params.viewType)
        render(view: '/dashboard/index', model: [workerType: CareConstants.WORKER_TYPE_CONTRACTOR, canSeeGlobalAndSupervisorMode: canSeeGlobalAndSupervisorMode, viewType: viewType])
    }

    public String getViewType(boolean canSeeGlobalAndSupervisorMode, String viewType = null) {
        // So many if-else needed to accommodate all the cases while showing dashboard i.e. CAREADMIN, Supervisor with CAREADMIN permissions, and plain simple Supervisor.
        if (!viewType) {
            if (!canSeeGlobalAndSupervisorMode) {
                if ((permissionService.hasPermission(Permission.DASHBOARD_WITH_LINKS)) || (!session?.loggedUser)) {
                    viewType = CareConstants.VIEW_TYPE_GLOBAL
                } else {
                    viewType = CareConstants.VIEW_TYPE_SUPERVISOR
                }
            } else {
                viewType = CareConstants.VIEW_TYPE_GLOBAL
            }
        } else {
            if ((EmployeeSupervisor.findBySlid(session?.loggedUser)) || BusinessUnitRequester.findBySlid(session?.loggedUser)) {
                viewType = permissionService.hasPermission(Permission.DASHBOARD_WITH_LINKS) ? viewType : CareConstants.VIEW_TYPE_SUPERVISOR
            } else {
                viewType = permissionService.hasPermission(Permission.DASHBOARD_WITH_LINKS) ? viewType : null
            }
        }
        return viewType
    }

    public boolean whetherGlobalAndSupervisorModesAvailable() {
        boolean canSeeGlobalAndSupervisorMode = false
        if (session?.loggedUser && (permissionService.hasPermission(Permission.DASHBOARD_WITH_LINKS)) && ((EmployeeSupervisor.findBySlid(session?.loggedUser)) || BusinessUnitRequester.findBySlid(session?.loggedUser))) {
            canSeeGlobalAndSupervisorMode = true
        }
        return canSeeGlobalAndSupervisorMode
    }

    def expirationForecast = {
        String expirationDate = params.expirationForecast_value
        String certification = params.selectedCertification
        String workerType = (params.workerType && params.workerType.toString().equals(CareConstants.WORKER_TYPE_CONTRACTOR)) ? CareConstants.WORKER_TYPE_CONTRACTOR : CareConstants.WORKER_TYPE_EMPLOYEE
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy")
        Date selectedDate = new Date()
        if (expirationDate) {
            selectedDate = dateFormat.parse(expirationDate)
        }
        render(care.expirationForecast(selectedDate: selectedDate, selectedCertification: certification, workerType: workerType))

    }

    @Secured(value = Permission.EDIT_RSS_FEEDS)
    def editRssFeedUrl = {
        String feedUrl = params.feedUrl
        ConfigProperty rssFeedUrl = ConfigProperty.findByName('rssFeedUrl')
        if (rssFeedUrl) {
            rssFeedUrl.value = feedUrl
            rssFeedUrl.s()
            render(care.rssFeed())
        } else {
            rssFeedUrl = new ConfigProperty(name: 'rssFeedUrl', value: params.feedUrl)
            rssFeedUrl.value = feedUrl
            rssFeedUrl.s()
            render(care.rssFeed())
        }
    }
    def workerExpirationOutlook = {
        String certification = params.selectedCertification
        String workerType = (params.workerType && params.workerType.toString().equals(CareConstants.WORKER_TYPE_CONTRACTOR)) ? CareConstants.WORKER_TYPE_CONTRACTOR : CareConstants.WORKER_TYPE_EMPLOYEE
        String viewType = params.viewType ? params.viewType.toString() : CareConstants.VIEW_TYPE_GLOBAL
        render(care.workerExpirationOutlook(selectedCertification: certification, workerType: workerType, viewType: viewType))
    }

    def calendarData = {
        Date date = Date.parse('dd-MM-yyyy', params.date)
        String workerType = (params.workerType && params.workerType.toString().equals(CareConstants.WORKER_TYPE_CONTRACTOR)) ? CareConstants.WORKER_TYPE_CONTRACTOR : CareConstants.WORKER_TYPE_EMPLOYEE
        String viewType = params.viewType ? params.viewType.toString() : null
        List<ExpirationCalendarVO> calendarVOs = dashboardService.getCertificationExpirationCount(date, workerType, viewType)
        String result = dashboardService.convertToJson(calendarVOs)
        render("$result")
    }

    //TODO: Seems like records action below is unused. Remove it.
    def records = {
        dashboardService.getFeedRecordsProcessedData()
        render(view: '/dashboard/index')
    }

    def feedInformationByDate = {
        Date selectedDate = Date.parse('MM/dd/yyyy', params.feedExceptionsDate_value)
        render(care.getFeedInformationByDate(selectedDate: selectedDate))
    }

}


class ActionRequestVO implements Cloneable {
    String requestName
    String outstandingRequests
    String completedRequests

    ActionRequestVO() {

    }

    ActionRequestVO(String actionRequest, String outstandingRequests, String completedRequests) {
        this.requestName = actionRequest
        this.outstandingRequests = outstandingRequests
        this.completedRequests = completedRequests
    }

    String toString() {
        return ("${requestName} : ${outstandingRequests} : ${completedRequests}")
    }


}
class WorkerStatusVO {
    String status
    Integer count

    WorkerStatusVO() {

    }

    WorkerStatusVO(String status, Integer count) {
        this.status = status
        this.count = count
    }

    String toString() {
        return status + " - " + count
    }

}

class ExpirationForecastVO {
    String period
    List workerIds
    Long certificationId

    ExpirationForecastVO() {

    }

    ExpirationForecastVO(String period, List workerIds, Long certificationId) {
        this.period = period
        this.workerIds = workerIds
        this.certificationId = certificationId
    }

    String toString() {
        return period + " - " + workerIds
    }

}

class FeedInformationVO {
    Long feedRunId = null
    String name
    String processedCount
    String errorCount
    String exceptionCount
    String startTime
    String endTime

    FeedInformationVO() {}

    FeedInformationVO(String name) {
        this.name = name
        processedCount = "n/a"
        errorCount = "n/a"
        exceptionCount = "n/a"
        startTime = "n/a"
        endTime = "n/a"
    }

    FeedInformationVO(Long feedRunId, String name, String processedCount, String errorCount, String exceptionCount, String startTime, String endTime) {
        this.feedRunId = feedRunId
        this.name = name
        this.processedCount = processedCount
        this.errorCount = errorCount
        this.exceptionCount = exceptionCount
        this.startTime = startTime
        this.endTime = endTime
    }
}

class ExpirationOutlookVO {
    String month
    Integer expirationCount

    ExpirationOutlookVO() {

    }

    ExpirationOutlookVO(String month, Integer expirationCount) {
        this.month = month
        this.expirationCount = ((expirationCount) ? (expirationCount) : 0)
    }

    String toString() {
        return (month + " : " + expirationCount)
    }

}

class ActionRequestResponseTimeVO {
    String action
    Double averageTime
    Double shortestTime
    Double longestTime

    ActionRequestResponseTimeVO() {}

    ActionRequestResponseTimeVO(String action, Double averageTime, Double shortestTime, Double longestTime) {
        this.action = action
        this.averageTime = averageTime
        this.shortestTime = shortestTime
        this.longestTime = longestTime
    }

    String toString() {
        return (action + " : " + averageTime + " : " + shortestTime + " : " + longestTime)
    }

}

class RssFeedsVO {
    String title
    Date publishDate
    String description
    String uri

    RssFeedsVO() {
    }

    RssFeedsVO(String title, String uri, Date publishedDate, String description) {

        this.title = title
        this.uri = uri
        this.publishDate = publishedDate
        this.description = description
    }
}

class ExpirationCalendarVO {

    Integer EventID
    String StartDateTime
    String Title
    String URL
    String Description

    ExpirationCalendarVO() {}

    ExpirationCalendarVO(Integer eventId, Date startDate, String title, String url, String description) {
        this.EventID = eventId
        StartDateTime = startDate.format('yyyy-MM-dd')
        Title = title
        this.URL = url
        Description = description
    }

    String toString() {
        return "${this.EventID} : ${StartDateTime} : ${Title} : ${this.URL} : ${Description}"
    }

    String toJsonString() {
        return "'EventID' : '${this.EventID}', 'Date' : '${StartDateTime}', 'title' : '${Title}','URL' : '${this.URL}', 'Description' : '${Description}'"
    }

}