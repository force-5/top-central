package com.force5solutions.care.cc

import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.converters.JSON
import com.force5solutions.care.ldap.Permission
import com.force5solutions.care.ldap.PermissionLevel
import com.force5solutions.care.ldap.SecurityRole
import com.force5solutions.care.common.CentralMessageTemplate
import com.force5solutions.care.common.CustomTag
import com.force5solutions.care.feed.HrInfo
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.common.RunTimeStampHelper
import com.force5solutions.care.common.CannedResponse

class CareTagLib {
    static namespace = "care"
    static config = ConfigurationHolder.config
    def dashboardService
    def rssFeedsService
    def permissionService
    def centralWorkflowTaskService

    def accessRequestWorkerInfo = {attrs ->
        Worker worker = attrs.worker
        HrInfo hrInfo = HrInfo.findBySlid(worker.slid)
        out << g.render(template: "/accessRequest/workerInfo", model: [worker: worker, hrInfo: hrInfo])
    }


    def selectEntitlementRoles = {attrs ->
        List<CcEntitlementRole> entitlementRoles = CcEntitlementRole.listUndeleted()
        List<CcEntitlementRole> selectedEntitlementRoles = attrs['selected'] ?: []
        Map availableRoleMap = createModelMapForView(entitlementRoles)
        out << g.render(template: '/location/selectEntitlementRoles', model: [availableRoleMap: availableRoleMap, entitlementRoles: selectedEntitlementRoles, worker: attrs['worker']])
    }

    def showRolesInTreeView = {attrs ->
        def tree = attrs.remove('tree')
        List entitlementRoleIds = attrs.entitlementRoleIds
        out << g.render(template: '/location/locationRoleTreeView', model: [tree: tree, entitlementRoleIds: entitlementRoleIds])
    }

    def showSelectedRolesInTreeView = {attrs ->
        List entitlementRoles = attrs.entitlementRoles
        entitlementRoles.each {
            out << "<li class='selectable' rel='entitlementRole_${it.id}'>" + it.location + "/" + it.name + "</li>"
        }
    }

    private Map createModelMapForView(List<CcEntitlementRole> rolesList) {
        Location rootLocation = Location.findByParentIsNull()
        Map locationsByParent = Location.list().groupBy {it.parent}
        Map rolesByLocation = rolesList.groupBy {it.location}
        Map intermediateMap = [:]
        locationsByParent.findAll {it.key && it.key != rootLocation}.each {key, value ->
            intermediateMap[key] = createListOfMaps(value, locationsByParent, rolesByLocation)
        }
        intermediateMap = intermediateMap.sort {it.key.name}
        return intermediateMap
    }

    private Map createListOfMaps(def value, Map locationsByParent, Map rolesByLocation) {
        Map subMap = [:]
        if (!rolesByLocation.isEmpty()) {
            value.each {
                if (it instanceof List) {
                    subMap[it] = createListOfMaps(locationsByParent[it], locationsByParent, rolesByLocation)
                } else if (!locationsByParent[it]) {
                    subMap[it] = getRolesGroupedByTag(it)
                }
            }
        }
        subMap = subMap.sort {it.key.name}
        return subMap
    }

    private Map getRolesGroupedByTag(Location location) {
        Map rolesByTag = [:]
        List<CcEntitlementRole> roles = CcEntitlementRole.findAllByLocationAndDeletedFromAps(location, false)
        String allTags = roles*.tags
        List<String> tagList = allTags.replace('[', '').replace(']', '').split(',')
        tagList = tagList.flatten().unique()*.trim()
        tagList.each { String s ->
            if (!s.equalsIgnoreCase('null')) {
                Set<CcEntitlementRole> roleSet = []
                roles.each { CcEntitlementRole role ->
                    if (s in role?.tags?.split(',')*.trim()) {
                        roleSet.add(role)
                    }
                }
                rolesByTag[s] = roleSet.toList().sort {it.name}
            }
        }
        rolesByTag = rolesByTag.sort {it.key}
        return rolesByTag
    }

    def inbox = {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Calling getPermittedTasks...")
        List<CentralWorkflowTask> tasks = CentralWorkflowTask.getPermittedTasks()
        log.debug("Returned from getPermittedTasks ${runTimeStampHelper.stamp()}")
        out << g.render(template: '/layouts/inbox', model: [total: (tasks) ? tasks.size() : 0])

    }

    def createLink = {
//        log.debug request.scheme + "://" + request.serverName + ":" + request.serverPort + grailsAttributes.getApplicationUri(request)
        out << request.scheme + "://" + request.serverName + ":" + request.serverPort + ServletContextHolder.servletContext.contextPath
    }


    def workerImage = {attrs ->
        if (attrs['id']) {
            out << "<img width='110' height='127' src=" + '"' + "${g.createLink(controller: 'careUtil', action: 'downloadFile', params: [className: 'com.force5solutions.care.cc.CentralDataFile', fieldName: 'bytes', id: attrs['id']])}" + '"' + "/>"
        }
        else {
            out << "<img width='110' height='127' src=" + '"' + "${g.createLinkTo(dir: 'data', file: 'noimguploaded.jpg')}" + '"' + "/>"

        }
    }

    def certificationExpiryDate = {attrs ->
        WorkerCertification contractorCertification = attrs['certification']
        Date certificationExpiryDate = contractorCertification.expiry
        if (certificationExpiryDate) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy")
            out << sdf.format(certificationExpiryDate)
        }
    }

    def certificationStatus = {attrs ->
        WorkerCertification cc = attrs['certification']
        out << cc.currentStatus
    }

    def vendors = {
        List<ContactVO> idList = []
        List<Vendor> vendors = Vendor.list()
        vendors.each {
            idList << new ContactVO(it.id, it.companyName)
        }
        out << g.render(template = "/contractor/vendors")
    }

    def canDeleteWorker = {attrs ->
        Worker worker = Worker.findById(attrs['workerId'].toInteger())
        if (worker?.entitlementRoles?.size() > 0) {
            out << false
        } else {
            out << true
        }
    }

    def recentStatus = {attrs ->
        Worker worker = Worker.findById(attrs['workerId'].toInteger())
        String status = worker.status
        Date recentStatusChange = worker.recentStatusChange
        out << g.render(template: '/shared/currentStatus', model: [worker: worker, date: recentStatusChange, status: status])
    }

/*
    def teminateIcon = {
        out << '<img  style="vertical-align:top;" src="' + "${g.createLinkTo(dir: 'images', file: 'TERMINATED.jpg')}" + '" alt="Terminated For Cause"/>'
    }
*/

    def statusIcon = {attrs ->
        String status = attrs["status"]
        out << '<img  style="vertical-align:top;" src="' + "${g.createLinkTo(dir: 'images', file: (status.toString().toUpperCase() + '.jpg'))}" + '" alt="' + status + '"/>'
    }

    def recentStatusWithoutDate = {attrs ->
        Contractor contractor = Contractor.findById(attrs['contractorId'].toInteger())
        out << care.statusIcon(status: contractor.status)

    }

    def missingCertifications = {attrs ->
        Worker worker = attrs['worker']
        List<Certification> missingCertifications = worker.missingCertifications.sort {it.name} //to maintain order
        def permission
        if (worker instanceof Contractor) {
            permission = Permission.CREATE_CONTRACTOR_CERTIFICATION
        } else {
            permission = Permission.CREATE_EMPLOYEE_CERTIFICATION
        }
        if (!worker.terminateForCause && hasPermission(permission: permission, worker: worker)) {
            out << g.render(template: "/workerCertification/missingCertifications",
                    model: [worker: worker, missingCertifications: missingCertifications])
        }
        else {
            out << g.render(template: "/workerCertification/missingCertificationsDisabled",
                    model: [worker: worker, missingCertifications: missingCertifications])
        }
    }

    def missingCertificationsForWorkflow = {attrs ->
        Worker worker = attrs['worker']
        List<Certification> missingCertifications = worker.missingCertifications.sort {it.name} //to maintain order
        out << g.render(template: "/workerCertification/missingCertificationForWorkflow", model: [worker: worker, missingCertifications: missingCertifications])
    }

    def missingCourses = {attrs ->
        Worker worker = attrs['worker']
        List<Certification> missingCertifications = worker.missingCertifications.sort {it.name}
        List<Course> missingCourses = (missingCertifications*.courses)?.flatten()
        if (!worker.terminateForCause && hasPermission(permission: Permission.CREATE_CONTRACTOR_CERTIFICATION, worker: worker)) {
            out << g.render(template: "/workerCertification/missingCourses",
                    model: [contractorInstance: worker, missingCertifications: missingCertifications, missingCourses: missingCourses])
        }
    }

    def showTree = {attrs ->
        Location location = Location.get(attrs['id'])
        Location tempLocation = location
        List breadcrumbs = []
        while (tempLocation) {
            breadcrumbs << tempLocation
            tempLocation = tempLocation.parent
        }
        List<LocationType> locationTypes = LocationType.list()//findAllByLevel(1)
        out << g.render(template: '/location/showTree1',
                model: ['location': location, 'breadcrumbs': breadcrumbs.reverse(), 'locationTypes': locationTypes])
    }

    def workerEntitlementRoleTree = {attrs ->
        Location location
        if (attrs['id']) {
            location = Location.get(attrs['id'])
        } else {
            LocationType pt = LocationType.findByLevel(1)
            location = Location.findByLocationType(pt)
        }
        Location tempLocation = location
        List breadcrumbs = []
        while (tempLocation) {
            breadcrumbs << tempLocation
            tempLocation = tempLocation.parent
        }
        out << g.render(template: '/workerEntitlementRole/showTree',
                model: [worker: attrs['worker'], 'location': location, 'breadcrumbs': breadcrumbs.reverse()])
    }

    def hasPermission = {attrs, body ->
        Worker worker = attrs['worker']
        Location location = attrs['location']
        Permission permission = attrs['permission']
        boolean ignorePermissionLevel = attrs['ignorePermissionLevel']
        if (permission && permissionService.hasPermission(permission, worker, location, ignorePermissionLevel ?: false)) {
            out << true
        }

    }

    def isPermissionChecked = {attrs, body ->
        SecurityRole role = attrs['role']
        String permission = attrs['permission']
        Long value = attrs['value']
        PermissionLevel permissionLevel = (role?.permissionLevels?.find {it?.permission?.name == permission})
        Boolean isChecked = (permissionLevel && ((permissionLevel?.level % value) == 0))
        if (isChecked) {
            out << true
        }
    }

    def locationTree = {attrs ->
        Location location
        if (attrs['id']) {
            location = Location.get(attrs['id'])
        } else {
            LocationType pt = LocationType.findByLevel(1)
            location = Location.findByLocationType(pt)
        }
        Location tempLocation = location
        List breadcrumbs = []
        while (tempLocation) {
            breadcrumbs << tempLocation
            tempLocation = tempLocation.parent
        }
        out << g.render(template: '/workerEntitlementRole/showTree',
                model: [worker: attrs['worker'], 'location': location, 'breadcrumbs': breadcrumbs.reverse()])
    }

    def messagePreview = {attrs ->
        def messageTemplateCO = attrs['messageTemplate']
        CentralMessageTemplate messageTemplate = CentralMessageTemplate.get(messageTemplateCO.id)
        String text = '<div style="font-size: 14px;font-weight: bold;text-decoration:underline;">SUBJECT:</div>' + CustomTag.replaceTagsWithDummyCode(messageTemplate.subjectTemplate)
        text = text + '<div style="font-size: 14px;font-weight: bold;text-decoration:underline;">BODY:</div>' + CustomTag.replaceTagsWithDummyCode(messageTemplate.bodyTemplate)
        out << text
    }

    def expirationForecast = {attrs ->
        log.debug("Entered tag Expiration forecast")
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        Date selectedDate = attrs['selectedDate']
        String selectedCertification = attrs['selectedCertification']
        String templateName = attrs['media'].toString().equals('tv') ? "/tvDashboard/expirationForecast" : "/dashboard/expirationForecast"
        String workerType = attrs['workerType'] ? attrs['workerType'].toString() : null
        String viewType = attrs['viewType'] ? attrs['viewType'].toString() : null
        List<ExpirationForecastVO> expirationForecastVOs
        Certification certification = (selectedCertification) ? Certification.get(selectedCertification.toLong()) : null
        expirationForecastVOs = dashboardService.getExpirationForecastsByCertification(selectedDate ? selectedDate : new Date(), certification, workerType, viewType)
        out << g.render(template: templateName, model: [expirationForecastVOs: expirationForecastVOs,
                total: expirationForecastVOs.sum {it.workerIds?.size()}, selectedDate: selectedDate ? selectedDate : new Date(),
                certifications: Certification.list(), selectedCertification: certification, workerType: workerType, viewType: viewType])
        log.debug("Leaving tag Expiration forecast ${runTimeStampHelper.stamp()}")
    }

    def workerStatusChart = { attrs ->
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entered tag Worker Status Chart")
        String media = attrs['media'].toString()
        String workerType = attrs['workerType'] ? attrs['workerType'].toString() : null
        String viewType = attrs['viewType'] ? attrs['viewType'].toString() : null
        List<WorkerStatusVO> workerStatusVOs = media.equals('tv') ? dashboardService.getActiveWorkerStatuses(workerType, viewType) : dashboardService.getWorkerStatuses(workerType, viewType)
        String templateName = media.equals('tv') ? "/tvDashboard/statusChart" : "/dashboard/statusChart"
        out << g.render(template: templateName, model: [workerStatusVOs: workerStatusVOs, total: workerStatusVOs.sum {it.count}])
        log.debug("Leaving tag Worker Status Chart ${runTimeStampHelper.stamp()}")
    }

    def outstandingRequestsChart = {attrs ->
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entering tag outstandingRequestchart")
        String workerType = attrs['workerType'] ? attrs['workerType'].toString() : null
        String viewType = attrs['viewType'] ? attrs['viewType'].toString() : null
        List<ActionRequestVO> actionRequestVOs = dashboardService.getActionRequests(workerType, viewType)
        String templateName = attrs['media'].toString().equals('tv') ? "/tvDashboard/outstandingRequestsChart" : "/dashboard/outstandingRequestsChart"
        out << g.render(template: templateName, model: [actionRequestVOs: actionRequestVOs, workerType: workerType, viewType: viewType])
        log.debug("Leaving tag outstandingRequestchart ${runTimeStampHelper.stamp()}")
    }

    def newsAndNotes = {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entered Tag news and Notes")
        List<News> news = News.list().reverse()
        out << g.render(template: "/news/newsAndNotes", model: [news: news])
        log.debug("Leaving Tag news and Notes ${runTimeStampHelper.stamp()}")
    }

    def getFeedInformationByDate = {attrs ->
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entered Tag getFeedExceptionsByDate")
        Date selectedDate = (attrs['selectedDate']) ? attrs['selectedDate'] : new Date()
        String workerType = attrs['workerType'] ? attrs['workerType'].toString() : null
        selectedDate.clearTime()
        List<FeedInformationVO> feedInfoVOs = dashboardService.getFeedInformationData(selectedDate, workerType)
        out << g.render(template: "/dashboard/feedInformation", model: [feedInfoVOs: feedInfoVOs, selectedDate: selectedDate])
        log.debug("Leaving Tag getFeedExceptionsByDate ${runTimeStampHelper.stamp()}")
    }

    def certificationExpirations = { attrs ->
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entered Certifications Expiration tag")
        String workerType = attrs['workerType'] ? attrs['workerType'].toString() : null
        String viewType = attrs['viewType'] ? attrs['viewType'].toString() : null
        List<ExpirationCalendarVO> calendarVOs = dashboardService.getCertificationExpirationCount(new Date(), workerType, viewType)
        String result = dashboardService.convertToJson(calendarVOs)
        out << g.render(template: "/dashboard/calendar", model: [result: result, workerType: workerType])
        log.debug("Leaving Certification Expiration tag ${runTimeStampHelper.stamp()}")
    }

    def workerExpirationOutlook = {attrs ->
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entered Tag Worker Expiration Outlook")
        String selectedCertification = attrs['selectedCertification']
        String templateName = attrs['media'].toString().equals('tv') ? "/tvDashboard/workerExpirationOutlook" : "/dashboard/workerExpirationOutlook"
        String workerType = attrs['workerType'] ? attrs['workerType'].toString() : null
        String viewType = attrs['viewType'] ? attrs['viewType'].toString() : null
        List<ExpirationOutlookVO> expirationOutlookVOs
        Certification certification = (selectedCertification) ? Certification.get(selectedCertification.toLong()) : null
        if (certification) {
            log.debug("Found Certification")
            expirationOutlookVOs = dashboardService.getExpirationOutlookByCertification(certification, workerType, viewType)
            log.debug expirationOutlookVOs
        } else {
            log.debug("Not-Found any Certification")
            expirationOutlookVOs = dashboardService.getExpirationOutlook(workerType, viewType)
        }
        out << g.render(template: templateName, model: [expirationOutlookVOs: expirationOutlookVOs,
                certifications: Certification.list(), selectedCertification: certification, workerType: workerType, viewType: viewType])
        log.debug("Leaving Tag Worker Expiration Outlook ${runTimeStampHelper.stamp()}")
    }

    def actionRequestsResponseTimes = { attrs ->
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entering tag actionRequestsResponseTimes")
        String workerType = attrs['workerType'] ? attrs['workerType'].toString() : null
        List<ActionRequestResponseTimeVO> actionRequestResponseTimeVOs = dashboardService.getActionRequestResponseTimes(workerType)
        out << g.render(template: "/dashboard/actionRequestsResponseTimes", model: [actionRequestResponseTimeVOs: actionRequestResponseTimeVOs])
        log.debug("Entering tag actionRequestsResponseTimes ${runTimeStampHelper.stamp()}")
    }

    def rssFeed = {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        log.debug("Entering tag rssFeed")
        List<RssFeedsVO> rssFeedsVOs = rssFeedsService.getRssFeeds()
        String rssFeedUrl = config.rssFeedUrl
        out << g.render(template: "/dashboard/rssFeed", model: [rssFeedsVOs: rssFeedsVOs, rssFeedUrl: rssFeedUrl])
        log.debug("leaving tag rssFeed ${runTimeStampHelper.stamp()}")
    }
    def headerNews = {
        List<News> headerNews = News.list().reverse()
        out << g.render(template: "/layouts/headerNews", model: [headerNews: headerNews])
    }

    def fullName = { attrs ->
        String slid = attrs['slid']
        if (slid) {
            Person person = Person.findBySlid(slid)
            if (person) {
                out << person.firstMiddleLastName
            } else {
                out << slid
            }
        }
    }

    def cannedResponse = {attrs ->
        String targetId = attrs['targetId']
        String taskDesc = attrs['taskDescription']
        List<CannedResponse> cannedResponses = CannedResponse?.findAllByTaskDescription(taskDesc)
        cannedResponses = cannedResponses.sort {it.priority}

        Map model = [:]
        model.put('target', targetId)
        model.put('cannedResponses', cannedResponses)
        out << g.render(template: "/cannedResponseTemplates/responseTemplate", model: model)
    }

    def systemStatistics = { attrs ->
        String viewType = attrs['viewType']
        out << g.render(template: '/dashboard/systemStatistics', model: [contractors: Contractor.count(), employees: Employee.count(), vendors: Vendor.count(), contractorsSup: ContractorSupervisor.count(), employeeSup: EmployeeSupervisor.count(), bur: BusinessUnitRequester.count(), dataFiles: CentralDataFile.count(), centralMessageTemplates: CentralMessageTemplate.count(), viewType: viewType])
    }

    def conditionalLink = { attrs, body ->
        String slid = session?.loggedUser
        def supervisor = EmployeeSupervisor?.findBySlid(slid) ?: BusinessUnitRequester.findBySlid(slid)
        if (permissionService.hasPermission(Permission.DASHBOARD_WITH_LINKS)) {
            out << g.link(attrs) {body()}
        } else if (supervisor) {
            out << g.link(attrs) {body()}
        } else {
            out << body()
        }
    }

    def conditionalCreateLink = { attrs, body ->
        String slid = session?.loggedUser
        def supervisor = EmployeeSupervisor?.findBySlid(slid) ?: BusinessUnitRequester.findBySlid(slid)
        if (permissionService.hasPermission(Permission.DASHBOARD_WITH_LINKS)) {
            out << g.createLink(attrs) {body()}
        } else if (supervisor) {
            out << g.link(attrs) {body()}
        } else {
            out << body()
        }
    }
}
