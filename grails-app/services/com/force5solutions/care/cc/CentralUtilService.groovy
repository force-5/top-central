package com.force5solutions.care.cc

import com.force5solutions.care.workflow.CentralWorkflowTaskPermittedSlid
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.common.CentralMessageTemplate
import com.force5solutions.care.common.CareConstants
import static com.force5solutions.care.common.CareConstants.*
import com.force5solutions.care.workflow.CentralWorkflowType
import groovy.xml.MarkupBuilder
import com.force5solutions.care.workflow.WorkflowTaskStatus
import java.text.SimpleDateFormat
import com.force5solutions.care.cp.ConfigProperty
import com.force5solutions.care.common.SessionUtils
import com.force5solutions.care.workflow.CentralWorkflowUtilService
import org.springframework.web.context.request.RequestContextHolder

public class CentralUtilService {
    def asynchronousMailService
    def workerEntitlementRoleService
    def workerEntitlementRoleArchiveService

    private static final Date MAX_DATE = new Date(2101, 0, 0, 0, 0, 0)
    static transactional = true

    static Collection<String> getSlidsByApplicationRoleForWorker(Collection<CentralApplicationRole> applicationRoles, Worker worker) {
        Set<String> slids = [] as Set
        if (worker) {
            applicationRoles.each { CentralApplicationRole centralApplicationRole ->
                if (centralApplicationRole.equals(CentralApplicationRole.SUPERVISOR)) {
                    slids << worker?.supervisor?.slid
                } else if (centralApplicationRole.equals(CentralApplicationRole.BUSINESS_UNIT_REQUESTER) && worker.businessUnitRequesters) {
                    slids << worker?.businessUnitRequester?.slid
                } else if (centralApplicationRole.equals(CentralApplicationRole.WORKER)) {
                    if (worker?.isEmployee()) {
                        slids << worker?.slid
                    }
                } else if (centralApplicationRole.equals(CentralApplicationRole.SUBORDINATES)) {
                    if (worker.isEmployee()) {
                        slids.addAll(EmployeeSupervisor.getSubordinates(worker.slid as String)*.slid)
                    }
                }
            }
        }
        return (slids.findAll { it })
    }

    List<Worker> filterWorkersBasedOnInheritanceForLoggedInUser(List<Worker> workers, String viewType = CareConstants.VIEW_TYPE_GLOBAL, String workerType) {
        def session = SessionUtils.session
        List<Worker> filteredWorkers = []
        def supervisor
        if (workerType.equals(CareConstants.WORKER_TYPE_EMPLOYEE)) {
            supervisor = EmployeeSupervisor?.findBySlid(session?.loggedUser)
        } else {
            supervisor = BusinessUnitRequester.findBySlid(session?.loggedUser)
        }
        if (supervisor && viewType?.equalsIgnoreCase(CareConstants.VIEW_TYPE_SUPERVISOR)) {
            filteredWorkers = filterWorkersBasedOnInheritedSubordinates(workers, supervisor, workerType)
        } else if ((!supervisor && viewType?.equalsIgnoreCase(CareConstants.VIEW_TYPE_GLOBAL)) || viewType?.equalsIgnoreCase(CareConstants.VIEW_TYPE_GLOBAL)) {
            filteredWorkers = workers
        }
        return filteredWorkers
    }

    private List<Worker> filterWorkersBasedOnInheritedSubordinates(List<Worker> workers, def supervisor, String workerType) {
        List<Worker> filteredSubordinates = []
        List<Worker> subordinates = []
        if (workerType.equalsIgnoreCase(CareConstants.WORKER_TYPE_EMPLOYEE)) {
            subordinates = EmployeeSupervisor.getInheritedSubordinates(supervisor.slid)
        } else {
            subordinates = BusinessUnitRequester.getInheritedContractors(supervisor.slid)
        }
        workers.each {
            if (it in subordinates) {
                filteredSubordinates.add(it)
            }
        }
        return filteredSubordinates
    }

    List<Worker> filterWorkersBasedOnInheritanceForLoggedInUserCached(List<Worker> workers, String viewType = CareConstants.VIEW_TYPE_GLOBAL, String workerType) {
        def request = SessionUtils.request
        if (!request.filterWorkersBasedOnInheritanceForLoggedInUser) {
            List<Worker> filteredWorkers = filterWorkersBasedOnInheritanceForLoggedInUser(workers, viewType, workerType)
            request.filterWorkersBasedOnInheritanceForLoggedInUser = filteredWorkers
        }
        return request.filterWorkersBasedOnInheritanceForLoggedInUser as List
    }

    static Collection<String> getSlidsByApplicationRole(Collection<CentralApplicationRole> applicationRoles, Object object, Boolean respectExclusionList = false) {
        Set<String> slids = [] as Set
        if (object) {
            if (WorkerEntitlementRole.isAssignableFrom(object.class)) {
                slids = getSlidsByApplicationRoleForWorker(applicationRoles, object.worker)
            } else if (WorkerCertification.isAssignableFrom(object.class)) {
                slids = getSlidsByApplicationRoleForWorker(applicationRoles, object.worker)
            } else if (Worker.isAssignableFrom(object.class)) {
                slids = getSlidsByApplicationRoleForWorker(applicationRoles, object)
            }
        }
        if (respectExclusionList) {
            List<String> exclusionSlids = ConfigurationHolder.config.exclusionSlids ? ConfigurationHolder.config.exclusionSlids.toUpperCase().tokenize(', ').findAll { it } : []
            slids = slids.findAll { it && !(it in exclusionSlids) }
        }
        return slids
    }

    static Collection<String> getRecipientsByApplicationRoles(Collection<CentralApplicationRole> applicationRoles, Object object, Boolean respectExclusionList = false) {
        Collection<String> recipients = []
        recipients.addAll(getSlidsByApplicationRole(applicationRoles, object, respectExclusionList)?.collect { AppUtil.getEmailFromSlid(it) })
        if (WorkerEntitlementRole.isAssignableFrom(object.class) && ((object as WorkerEntitlementRole).worker.isContractor()) && (applicationRoles.any { it.equals(CentralApplicationRole.WORKER) })) {
            recipients.add(addContractorEmail(object as WorkerEntitlementRole))
        }
        return recipients
    }

    static private String addContractorEmail(WorkerEntitlementRole workerEntitlementRole) {
        return workerEntitlementRole.worker.email
    }

    static Collection<String> getRecipients(String emails, String slids, Collection<CentralApplicationRole> applicationRoles, Object object, Boolean respectExclusionList = false) {
        Collection<String> recipients = []
        if (emails) {
            recipients.addAll(emails.tokenize(', ')?.findAll { it })
        }
        if (slids) {
            recipients.addAll(slids.tokenize(', ')?.findAll { it }?.collect { AppUtil.getEmailFromSlid(it?.toString()) })
        }
        recipients.addAll(getRecipientsByApplicationRoles(applicationRoles, object, respectExclusionList))
        return recipients
    }

    static String getConfirmationLink(CentralWorkflowTask task, String slid) {
        if (!task) {
            return null
        }
        if (!slid) {
            return null
        }
        CentralWorkflowTaskPermittedSlid taskPermittedSlid = task.permittedSlids.find { it.slid == slid }
        return "${ConfigurationHolder.config.grails.serverURL}/centralWorkflowTask/confirm/${taskPermittedSlid?.guid}"
    }

    static Map getParametersForMessageTemplate(Object object) {
        Map parameters = [employeeListLink: "${ConfigurationHolder.config.grails.serverURL}/employee/list",
                link: ConfigurationHolder.config.grails.serverURL]
        if (object) {
            if (WorkerEntitlementRole.isAssignableFrom(object.class)) {
                parameters['worker'] = object?.worker
                parameters['workerAsSupervisor'] = (object?.worker ? EmployeeSupervisor.findBySlid(object?.worker.slid) : null)
                parameters['entitlementRole'] = object?.entitlementRole
                parameters['workerEntitlementRole'] = object
            } else if (WorkerCertification.isAssignableFrom(object.class)) {
                parameters['worker'] = object.worker
                parameters['workerAsSupervisor'] = (object.worker ? EmployeeSupervisor.findBySlid(object.worker.slid) : null)
                parameters['certification'] = object?.certification
                parameters['workerCertification'] = object
            } else if (Worker.isAssignableFrom(object.class)) {
                parameters['worker'] = object
                parameters['workerAsSupervisor'] = (object ? EmployeeSupervisor.findBySlid(object.slid) : null)
            } else if (EntitlementPolicy.isAssignableFrom(object.class)) {
                parameters['entitlementPolicy'] = object
            }
        }
        return parameters
    }

    void sendNotification(CentralWorkflowTaskTemplate taskTemplate, CentralMessageTemplate messageTemplate, Object object, Map parameters) {
        log.debug "*****************Inside send Notification: ${taskTemplate?.id} : ${messageTemplate} : ${object}"
        Collection<String> toRecipients = getRecipients(taskTemplate.toNotificationEmails, taskTemplate.toNotificationSlids, taskTemplate.toNotificationApplicationRoles, object, taskTemplate.respectExclusionList)
        Collection<String> ccRecipients = getRecipients(taskTemplate.ccNotificationEmails, taskTemplate.ccNotificationSlids, taskTemplate.ccNotificationApplicationRoles, object, taskTemplate.respectExclusionList)

        if (messageTemplate) {
            String subjectPrefix = (ConfigurationHolder.config.testMode == 'true') ? "*** TEST ***" : ""
            String bodyPrefix = (ConfigurationHolder.config.testMode == 'true') ? "<span style='color: #e67412'> *** THIS IS ONLY A TEST ***</span><br/>" : ""
            String emailBody = bodyPrefix + messageTemplate.getBody(parameters)
            String emailSubject = subjectPrefix + messageTemplate.getSubject(parameters)
            try {
                if ((toRecipients) || (ConfigurationHolder.config.bccAlertRecipient)) {
                    asynchronousMailService.sendAsynchronousMail {
                        if (toRecipients) {
                            to toRecipients as List
                            if (ConfigurationHolder.config.bccAlertRecipient) {
                                bcc ConfigurationHolder.config.bccAlertRecipient
                            }
                        } else {
                            to ConfigurationHolder.config.bccAlertRecipient
                        }
                        if (ccRecipients) {
                            cc ccRecipients as List
                        }
                        // set message headers in this case priority mail( priorities are from 1-5 in descending order i.e.high to low, with 3 as normal)
                        headers(CareConstants.CARE_PRIORITY_MAIL_HEADERS)
                        subject emailSubject
                        html emailBody
                        if (ConfigurationHolder.config.sendEmail != "true") {
                            beginDate MAX_DATE
                            endDate MAX_DATE + 1
                        }
                    }
                }
            } catch (Throwable t) {
                // TODO : What else to do?
                t.printStackTrace();
            }
        } else {
            log.warn("Message template not defined.")
        }
    }

    public void generateComplianceDashboardXMLFile(Date jobExcecutionDate) {
        Date lastRunTime
        SimpleDateFormat timeStampFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        ConfigProperty lastRunTimeProperty = ConfigProperty.findByName("complianceDashboardFileGenerationLastRunTime")
        if (!lastRunTimeProperty) {
            lastRunTimeProperty = new ConfigProperty(name: "complianceDashboardFileGenerationLastRunTime", value: "0").s()
        }

        lastRunTime = new Date(lastRunTimeProperty.value.toLong());

        log.info "ComplianceDashboardXMLFileJob Excecution Time : " + jobExcecutionDate
        log.info "ComplianceDashboardXMLFileJob Last Run Time : " + lastRunTime


        List<CentralWorkflowTask> centralWorkflowTasks = CentralWorkflowTask.createCriteria().list {
            and {
                eq("workflowType", CentralWorkflowType.EMPLOYEE_ACCESS_REVOKE)
                between("lastUpdated", lastRunTime, jobExcecutionDate)
                ne("nodeName", "Initial Task")
            }
        }

        log.info "TASKS LIST FOR WRITING INTO XML: " + centralWorkflowTasks

        if (centralWorkflowTasks) {
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(jobExcecutionDate)
            FileWriter fileWriter = new FileWriter(ConfigurationHolder.config.complianceDashboardFileFolderLocation.toString() + "/revoke${fileName}" + ".xml")
            MarkupBuilder xml = new groovy.xml.MarkupBuilder(fileWriter)
            Worker worker
            String requestorName
            String workflowStatus
            String completionDate
            String revocationType

            xml.AccessRevocation {
                ControlArea {

                    timestamp(timeStampFormatter.format(jobExcecutionDate))
                }
                DataArea {
                    centralWorkflowTasks.each { CentralWorkflowTask centralWorkflowTask ->
                        worker = centralWorkflowTask.worker
                        Person requestorPerson = Person.findBySlid(CentralWorkflowTask.getInitialTask(centralWorkflowTask.workflowGuid).actorSlid)
                        requestorName = (CentralWorkflowTask.getInitialTask(centralWorkflowTask.workflowGuid).actorSlid.equalsIgnoreCase(CareConstants.CENTRAL_SYSTEM_USER_ID)) ? "APS Central" : (requestorPerson ? requestorPerson.firstMiddleLastName : "")
                        workflowStatus = (centralWorkflowTask.status != WorkflowTaskStatus.COMPLETE) ? "Initiate" : "Completed"
                        completionDate = (centralWorkflowTask.status != WorkflowTaskStatus.COMPLETE) ? "" : timeStampFormatter.format(centralWorkflowTask.lastUpdated)
                        revocationType = (centralWorkflowTask.revocationType.equals(REVOKE_WORKFLOW_TASK_IN_7_DAYS)) ? "7D" : "24H"

                        Revocation {
                            revocid(centralWorkflowTask.workflowGuid)
                            Employee {
                                name(worker.firstMiddleLastName)
                                slid(worker.slid)
                                personnelid(worker.workerNumber)
                                type("Employee")
                                requestor(requestorName)
                                supervisor(worker.supervisor ? worker.supervisor.firstMiddleLastName : '')
                                areaaccessmanager(centralWorkflowTask.entitlementRole.gatekeepers)
                            }
                            ReqDetail {
                                revoctype(revocationType)
                                status(workflowStatus)
                                requestdate(timeStampFormatter.format(centralWorkflowTask.dateCreated))
                                effectivedate(timeStampFormatter.format(centralWorkflowTask.effectiveStartDate))
                                completiondate(completionDate)
                            }
                        }
                    }
                }
            }
            fileWriter.close()
        }

        lastRunTimeProperty.value = jobExcecutionDate.time.toString()
        lastRunTimeProperty.s()
    }

    public String initiateFeedWorkflowAndReturnStatusMessage(Worker worker, CcEntitlementRole ccEntitlementRole, String workflowRequest) {
        String status = null
        WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.findByWorkerAndEntitlementRole(worker, ccEntitlementRole)

        if (!workerEntitlementRole) {
            workerEntitlementRole = workerEntitlementRoleService.createWorkerEntitlementRoleForFeedUtility(worker, ccEntitlementRole)
        }
        if (workflowRequest.equalsIgnoreCase('Grant')) {
            if (!(workerEntitlementRole.status in [EntitlementRoleAccessStatus.ACTIVE])) {
                status = "Granting access by Feed for worker: ${worker} on entitlement role: ${ccEntitlementRole}"
                workerEntitlementRoleService.changeEntitlementRoleStatus(workerEntitlementRole.id, EntitlementRoleAccessStatus.ACTIVE)
                log.debug "Granting access by Feed for worker: ${worker} on entitlement role: ${ccEntitlementRole}"
                CentralWorkflowUtilService.startAccessGrantedByFeedWorkflow(workerEntitlementRole)
                List<CentralWorkflowTask> centralWorkflowTaskList = CentralWorkflowTask.findAllByWorkerEntitlementRoleIdAndWorkflowType(workerEntitlementRole.id, CentralWorkflowType.ACCESS_GRANTED_BY_FEED)
                workerEntitlementRoleArchiveService.createWorkerEntitlementRoleEntry(centralWorkflowTaskList.sort { it.lastUpdated }.last(), null)
            } else {
                status = "Worker with  SLID: ${worker.slid} is already ACTIVE on role: ${ccEntitlementRole}. Please choose another worker or role."
            }
        } else if (workflowRequest.equalsIgnoreCase('Revoke')) {
            if ((workerEntitlementRole.status in [EntitlementRoleAccessStatus.ACTIVE, EntitlementRoleAccessStatus.PENDING_ACCESS_APPROVAL])) {
                status = "Revoked access by Feed for worker: ${worker} on entitlement role: ${ccEntitlementRole}"
                workerEntitlementRoleService.changeEntitlementRoleStatus(workerEntitlementRole.id, EntitlementRoleAccessStatus.REVOKED)
                log.debug "Revoking access by Feed for worker: ${worker} on entitlement role: ${ccEntitlementRole}"
                CentralWorkflowUtilService.startAccessRevokedByFeedWorkflow(workerEntitlementRole)
                List<CentralWorkflowTask> centralWorkflowTaskList = CentralWorkflowTask.findAllByWorkerEntitlementRoleIdAndWorkflowType(workerEntitlementRole.id, CentralWorkflowType.ACCESS_REVOKED_BY_FEED)
                workerEntitlementRoleArchiveService.createWorkerEntitlementRoleEntry(centralWorkflowTaskList.sort { it.lastUpdated }.last(), null)
            } else {
                status = "Worker with  SLID: ${worker.slid} is already REVOKED on role: ${ccEntitlementRole} as per our records. You can only revoke roles which are Active or Pending Access Approval."
            }
        }
        return status
    }

    def updateAndGetDefaultSizeOfListViewInConfig(Map params) {
        def defaultSize = null
        def session = RequestContextHolder?.currentRequestAttributes()?.getSession()
        if (session && !session.rowCount) {
            session.rowCount = ConfigurationHolder?.config?.defaultSizeOfListView ?: '10'
            defaultSize = session.rowCount.toString().equalsIgnoreCase('Unlimited') ? session.rowCount.toString() : session.rowCount.toInteger()
        } else {
            if (params.rowCount && !params?.rowCount?.toString()?.equalsIgnoreCase('Unlimited')) {
                session.rowCount = params?.rowCount?.toInteger()
                defaultSize = session.rowCount
            } else if (params?.max && !params?.max?.toString()?.equalsIgnoreCase('Unlimited')) {
                session.rowCount = params.max.toInteger()
                defaultSize = session.rowCount
            } else if (params?.rowCount?.toString()?.equalsIgnoreCase('Unlimited')) {
                session.rowCount = params?.rowCount
                defaultSize = session.rowCount
            } else {
                defaultSize = session.rowCount ? (session.rowCount?.toString()?.equalsIgnoreCase('Unlimited') ? session.rowCount : session.rowCount?.toInteger()) : 10
            }
        }
        return defaultSize
    }
}
