package com.force5solutions.care.workflow

import com.force5solutions.care.cc.*
import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.common.SessionUtils
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.drools.KnowledgeBaseFactory
import org.drools.base.MapGlobalResolver
import org.drools.builder.KnowledgeBuilderFactory
import org.drools.builder.ResourceType
import org.drools.compiler.DialectConfiguration
import org.drools.compiler.PackageBuilderConfiguration
import org.drools.io.ResourceFactory
import org.drools.persistence.gorm.GORMKnowledgeService
import org.drools.rule.builder.dialect.java.JavaDialectConfiguration
import org.drools.runtime.EnvironmentName
import org.drools.runtime.StatefulKnowledgeSession

import java.text.SimpleDateFormat

import static com.force5solutions.care.common.CareConstants.*

class CentralWorkflowUtilService extends WorkflowUtilService {

    static Log log = LogFactory.getLog(com.force5solutions.care.workflow.CentralWorkflowUtilService.class)
    static transactional = true
    static config = ConfigurationHolder.config

    static void sendNotificationForWorker(String taskTemplate, Long workerId) {
        def applicationContext = ApplicationHolder.getApplication().getMainContext()
        def centralUtilService = applicationContext.getBean('centralUtilService')
        CentralWorkflowTaskTemplate centralWorkflowTaskTemplate = CentralWorkflowTaskTemplate.findById(taskTemplate)
        if (centralWorkflowTaskTemplate) {
            Worker worker = Worker.get(workerId)
            centralUtilService.sendNotification(centralWorkflowTaskTemplate, centralWorkflowTaskTemplate.messageTemplate, worker, CentralUtilService.getParametersForMessageTemplate(worker))
        }
    }

    static void sendNotificationForWorkerEntitlementRole(String taskTemplate, Long workerEntitlementRoleId) {
        def applicationContext = ApplicationHolder.getApplication().getMainContext()
        def centralUtilService = applicationContext.getBean('centralUtilService')
        CentralWorkflowTaskTemplate centralWorkflowTaskTemplate = CentralWorkflowTaskTemplate.findById(taskTemplate)
        if (centralWorkflowTaskTemplate) {
            WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(workerEntitlementRoleId)
            centralUtilService.sendNotification(centralWorkflowTaskTemplate, centralWorkflowTaskTemplate.messageTemplate, workerEntitlementRole, CentralUtilService.getParametersForMessageTemplate(workerEntitlementRole))
        }
    }

    static void startTermination(long workerEntitlementRoleId) {
        log.debug "Starting termination"
        WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(workerEntitlementRoleId)
        Worker worker = workerEntitlementRole.worker
        CentralWorkflowType workflowType = workerEntitlementRole.worker.isContractor() ? CentralWorkflowType.CONTRACTOR_TERMINATION : CentralWorkflowType.EMPLOYEE_TERMINATION
        List entitlementRoleIds = worker.entitlementRoles*.id
        List<CentralWorkflowTask> pendingTasks = CentralWorkflowTask.findAllByWorkflowTypeNotEqualAndWorkerEntitlementRoleIdInList(workflowType, entitlementRoleIds)
        def (kbase, env) = createKnowledgeBaseForFlows(workflowType.workflowFilePath)
        pendingTasks.each { CentralWorkflowTask centralWorkflowTask ->
            log.debug "Loading session: " + centralWorkflowTask.id + ", Session Id: " + centralWorkflowTask.droolsSessionId
            StatefulKnowledgeSession knowledgeSession = GORMKnowledgeService.loadStatefulKnowledgeSession(centralWorkflowTask.droolsSessionId.toInteger(), kbase, null, env);
            //TODO: Abort Session / ProcessInstance here
            centralWorkflowTask.status = WorkflowTaskStatus.COMPLETE
            centralWorkflowTask.s()
        }
    }

    static void registerAllWorkItemHandlers(StatefulKnowledgeSession knowledgeSession) {
        knowledgeSession.getWorkItemManager().registerWorkItemHandler("Central Workflow Task", new WorkflowTaskHandler())
    }

    static void sendResponseElements(CentralWorkflowTask centralWorkflowTask, Map responseElements, List<UploadedFile> uploadedFiles = [], String actorSlid = SessionUtils.getSession()?.loggedUser, boolean groupResponse = false) {
        def (kbase, env) = createKnowledgeBaseForFlows(centralWorkflowTask.workflowType.workflowFilePath)
        StatefulKnowledgeSession knowledgeSession = GORMKnowledgeService.loadStatefulKnowledgeSession(centralWorkflowTask.droolsSessionId.toInteger(), kbase, null, env);
        Long workItemId = centralWorkflowTask.workItemId
        if (!groupResponse) {
            uploadedFiles?.each { UploadedFile uploadedFile ->
                centralWorkflowTask.addToDocuments(new CentralDataFile(uploadedFile))
            }
        }
        centralWorkflowTask.status = WorkflowTaskStatus.COMPLETE
        centralWorkflowTask.response = responseElements['userAction'] ?: 'CONFIRM'
        centralWorkflowTask.message = responseElements['accessJustification']
        if (responseElements['actionDate'] && !(responseElements['actionDate'].toString().length() == 0)) {
            SimpleDateFormat format = new SimpleDateFormat('MM/dd/yyyy hh:mm a')
            centralWorkflowTask.actionDate = format.parse(responseElements['actionDate'].toString())
            responseElements.remove('actionDate')
        }
        centralWorkflowTask.responseElements = CentralWorkflowTask.serializeResponseEmplements(responseElements)
        if (actorSlid) {
            centralWorkflowTask.actorSlid = actorSlid
            CentralWorkflowTaskPermittedSlid.markArchived(centralWorkflowTask, actorSlid);
        }
        centralWorkflowTask.s()
        registerAllWorkItemHandlers(knowledgeSession)
        knowledgeSession.getWorkItemManager().completeWorkItem(workItemId, ['responseElements': responseElements]);
    }

    static void abortWorkflow(String workflowGuid) {
        List<CentralWorkflowTask> tasks = CentralWorkflowTask.findAllByWorkflowGuidAndStatusInList(workflowGuid, [WorkflowTaskStatus.NEW, WorkflowTaskStatus.PENDING])
        tasks.each { task ->
            task.status = WorkflowTaskStatus.CANCELLED
            task.actorSlid = SessionUtils.getSession()?.loggedUser
            task.s()
            String slid = SessionUtils.getSession()?.loggedUser;
            CentralWorkflowTaskPermittedSlid.markArchived(task, slid);
        }
        new AbortCentralWorkflow(workflowGuid: workflowGuid).s()
    }

    static void triggerProvisionerDeprovisionerTaskOnRoleUpdateWorkflow(Long workerEntitlementRoleId, String guid) {
        CentralWorkflowType workflowType = CentralWorkflowType.PROVISIONER_DEPROVISIONER_TASKS_ON_ROLE_UPDATE
        Map<String, Object> parameterMap = new HashMap<String, Object>()
        parameterMap.put("workerEntitlementRoleId", workerEntitlementRoleId)
        parameterMap.put("provisionerDeprovisionerTaskOnRoleUpdateGuid", guid)
        startWorkflow(parameterMap, workflowType)
    }

    static void startCancelWorkflow(String workflowGuid, WorkerEntitlementRole workerEntitlementRole = null, String justification = '', List<CentralDataFile> documents = [], String taskTemplate = '', Date effectiveStartDate = new Date()) {
        CentralWorkflowTask initialTask = CentralWorkflowTask.findByWorkflowGuid(workflowGuid)
        switch (initialTask.workflowType) {
            case CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST:
                startCancelApprovalWorkflow(initialTask.workerEntitlementRole, justification, documents, taskTemplate, effectiveStartDate)
                break;
            case CentralWorkflowType.CONTRACTOR_ACCESS_REQUEST:
                startCancelApprovalWorkflow(initialTask.workerEntitlementRole, justification, documents, taskTemplate, effectiveStartDate)
                break;
            case CentralWorkflowType.EMPLOYEE_ACCESS_REVOKE:
                startCancelRevocationWorkflow(initialTask.workerEntitlementRole)
                break;
            case CentralWorkflowType.CONTRACTOR_ACCESS_REVOKE:
                startCancelRevocationWorkflow(initialTask.workerEntitlementRole)
                break;
            default:
                log.debug "No cancel workflow present for workflow of type ${initialTask.workflowType}"
        }
    }

    static void startCancelApprovalWorkflow(WorkerEntitlementRole workerEntitlementRole, String justification = '', List<CentralDataFile> documents = [], String taskTemplate = '', Date effectiveStartDate = new Date()) {
        CentralWorkflowType workflowType = workerEntitlementRole.worker.isContractor() ? CentralWorkflowType.CONTRACTOR_CANCEL_ACCESS_APPROVAL : CentralWorkflowType.EMPLOYEE_CANCEL_ACCESS_APPROVAL
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("justification", justification ?: "Revocation workflow initiated on cancellation of access approval workflow")
        parameterMap.put("workflowTaskTemplate", taskTemplate ?: CareConstants.WORKFLOW_TASK_TEMPLATE_REVOKE_IN_24_HOURS)
        parameterMap.put("effectiveStartDate", effectiveStartDate)
        if (documents) {
            parameterMap.put("documents", documents*.id.join(','))
        }
        parameterMap.put("workerEntitlementRoleId", workerEntitlementRole.id)
        String revokeCompleteTemplate = null
        if (workflowType.equals(CentralWorkflowType.EMPLOYEE_CANCEL_ACCESS_APPROVAL)) {
            revokeCompleteTemplate = taskTemplate.equals(WORKFLOW_TASK_TEMPLATE_REVOKE_IN_7_DAYS) ? CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE : CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE
        } else if (workflowType.equals(CentralWorkflowType.CONTRACTOR_CANCEL_ACCESS_APPROVAL)) {
            revokeCompleteTemplate = taskTemplate.equals(WORKFLOW_TASK_TEMPLATE_REVOKE_IN_7_DAYS) ? CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR : CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR
        }
        parameterMap.put("revokeCompleteTemplate", revokeCompleteTemplate)
        startWorkflow(parameterMap, workflowType)
    }

    static void startCancelRevocationWorkflow(WorkerEntitlementRole workerEntitlementRole) {
        CentralWorkflowType workflowType = workerEntitlementRole.worker.isContractor() ? CentralWorkflowType.CONTRACTOR_CANCEL_ACCESS_REVOCATION : CentralWorkflowType.EMPLOYEE_CANCEL_ACCESS_REVOCATION
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("workerEntitlementRoleId", workerEntitlementRole.id)
        startWorkflow(parameterMap, workflowType)
    }

    static void startAccessGrantedByFeedWorkflow(WorkerEntitlementRole workerEntitlementRole) {
        CentralWorkflowType workflowType = CentralWorkflowType.ACCESS_GRANTED_BY_FEED
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("workerEntitlementRoleId", workerEntitlementRole.id)
        parameterMap.put("justification", 'ACCESS GRANTED BY FEED')
        startWorkflow(parameterMap, workflowType)
    }

    static void startAccessRevokedByFeedWorkflow(WorkerEntitlementRole workerEntitlementRole) {
        CentralWorkflowType workflowType = CentralWorkflowType.ACCESS_REVOKED_BY_FEED
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("workerEntitlementRoleId", workerEntitlementRole.id)
        parameterMap.put("justification", 'ACCESS REVOKED BY FEED')
        startWorkflow(parameterMap, workflowType)
    }

    static void certificationCompleteEvent(def workerEntitlementRoleId, boolean isTimedOut = false) {
        log.debug "Inside certification complete event"
        if (CentralWorkflowTask.countByWorkerEntitlementRoleIdAndType(workerEntitlementRoleId, CentralWorkflowTaskType.SYSTEM_CENTRAL)) {
            CentralWorkflowTask task = CentralWorkflowTask.findByWorkerEntitlementRoleIdAndType(workerEntitlementRoleId, CentralWorkflowTaskType.SYSTEM_CENTRAL)
            def (kbase, env) = createKnowledgeBaseForFlows(task.workflowType.workflowFilePath)
            List<CentralWorkflowTask> tasks = CentralWorkflowTask.findAllByWorkerEntitlementRoleIdAndType(workerEntitlementRoleId, CentralWorkflowTaskType.SYSTEM_CENTRAL)
            tasks = tasks.findAll { it.nodeName.equals('Pending Certifications') && it.status in [WorkflowTaskStatus.NEW, WorkflowTaskStatus.PENDING] }
            tasks.each { CentralWorkflowTask centralWorkflowTask ->
                StatefulKnowledgeSession knowledgeSession = GORMKnowledgeService.loadStatefulKnowledgeSession(centralWorkflowTask.droolsSessionId.toInteger(), kbase, null, env);
                registerAllWorkItemHandlers(knowledgeSession)
                centralWorkflowTask.status = isTimedOut ? WorkflowTaskStatus.CANCELLED : WorkflowTaskStatus.COMPLETE
                centralWorkflowTask.s()
                knowledgeSession.getWorkItemManager().completeWorkItem(centralWorkflowTask.workItemId, null);
            }
        }
        log.debug "Certifications Complete Task Over"
    }

    static void certificationTimeOutCompleteEvent(def workerEntitlementRoleId) {
        log.debug "Inside certification time out complete event"
        certificationCompleteEvent(workerEntitlementRoleId, true)
        log.debug "Certifications time out Complete Task Over"
    }


    static void startAddEntitlementPolicyWorkflow(EntitlementPolicy entitlementPolicy) {
        CentralWorkflowType workflowType = CentralWorkflowType.ADD_ENTITLEMENT_POLICY
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("entitlementPolicyId", entitlementPolicy.id)
        startWorkflow(parameterMap, workflowType)
    }

    static void startUpdateEntitlementPolicyWorkflow(EntitlementPolicy entitlementPolicy) {
        CentralWorkflowType workflowType = CentralWorkflowType.UPDATE_ENTITLEMENT_POLICY
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("entitlementPolicyId", entitlementPolicy.id)
        startWorkflow(parameterMap, workflowType)
    }

    static void startPublicAccessRequestWorkflow(WorkerEntitlementRole workerEntitlementRole, String justification = '', List<CentralDataFile> documents = []) {
        CentralWorkflowType workflowType = workerEntitlementRole.worker.isContractor() ? CentralWorkflowType.CONTRACTOR_ACCESS_REQUEST : CentralWorkflowType.EMPLOYEE_PUBLIC_ACCESS_REQUEST
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("justification", justification)
        if (documents) {
            parameterMap.put("documents", documents*.id.join(','))
        }
        parameterMap.put("workerEntitlementRoleId", workerEntitlementRole.id)
        startWorkflow(parameterMap, workflowType)
    }

    static void startAccessRequestWorkflow(WorkerEntitlementRole workerEntitlementRole, String justification = '', List<CentralDataFile> documents = []) {
        CentralWorkflowType workflowType = workerEntitlementRole.worker.isContractor() ? CentralWorkflowType.CONTRACTOR_ACCESS_REQUEST : CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("justification", justification)
        if (documents) {
            parameterMap.put("documents", documents*.id.join(','))
        }
        parameterMap.put("workerEntitlementRoleId", workerEntitlementRole.id)
        startWorkflow(parameterMap, workflowType)
    }

    static void startCertificationExpirationNotificationWorkflow(WorkerCertification workerCertification, String taskTemplate) {
        CentralWorkflowType workflowType = CentralWorkflowType.CERTIFICATION_EXPIRATION_NOTIFICATION
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("workerCertificationId", workerCertification.id)
        parameterMap.put("workflowTaskTemplate", taskTemplate)
        startWorkflow(parameterMap, workflowType)
    }

    static void startRevokeRequestWorkflow(WorkerEntitlementRole workerEntitlementRole, String justification = '', List<CentralDataFile> documents = [], String taskTemplate = '', Date effectiveStartDate = new Date()) {
        CentralWorkflowType workflowType = workerEntitlementRole.worker.isContractor() ? CentralWorkflowType.CONTRACTOR_ACCESS_REVOKE : CentralWorkflowType.EMPLOYEE_ACCESS_REVOKE
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("justification", justification)
        parameterMap.put("workflowTaskTemplate", taskTemplate)
        parameterMap.put("effectiveStartDate", effectiveStartDate)
        if (documents) {
            parameterMap.put("documents", documents*.id.join(','))
        }
        parameterMap.put("workerEntitlementRoleId", workerEntitlementRole.id)
        String revokeCompleteTemplate = null
        if (workflowType.equals(CentralWorkflowType.EMPLOYEE_ACCESS_REVOKE)) {
            revokeCompleteTemplate = taskTemplate.equals(WORKFLOW_TASK_TEMPLATE_REVOKE_IN_7_DAYS) ? CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE : CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE
        } else if (workflowType.equals(CentralWorkflowType.CONTRACTOR_ACCESS_REVOKE)) {
            revokeCompleteTemplate = taskTemplate.equals(WORKFLOW_TASK_TEMPLATE_REVOKE_IN_7_DAYS) ? CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR : CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR
        }
        parameterMap.put("revokeCompleteTemplate", revokeCompleteTemplate)
        startWorkflow(parameterMap, workflowType)
    }

    static void startRevokeAfterApsAccessVerificationResponse(Long workerEntitlementRoleId, String justification = '') {
        WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(workerEntitlementRoleId)
        startRevokeRequestWorkflow(workerEntitlementRole, justification, [], WORKFLOW_TASK_TEMPLATE_REVOKE_IN_24_HOURS)
    }

    static void startTerminateRequestWorkflow(WorkerEntitlementRole workerEntitlementRole, String justification = '', List<CentralDataFile> documents = []) {
        CentralWorkflowType workflowType = workerEntitlementRole.worker.isContractor() ? CentralWorkflowType.CONTRACTOR_TERMINATION : CentralWorkflowType.EMPLOYEE_TERMINATION
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("justification", justification)
        if (documents) {
            parameterMap.put("documents", documents*.id.join(','))
        }
        parameterMap.put("workerEntitlementRoleId", workerEntitlementRole.id)
        startWorkflow(parameterMap, workflowType)
    }

    private static void startWorkflow(Map<String, Object> parameterMap, CentralWorkflowType workflowType) {
        log.debug "Starting workflow ${workflowType} now, with the parameterMap : ${parameterMap}"
        parameterMap.put("workflowType", workflowType)
        String filePath = workflowType.workflowFilePath
        String processId = workflowType.workflowProcessId
        def (kbase, env) = createKnowledgeBaseForFlows(filePath)
        StatefulKnowledgeSession knowledgeSession = createOrLoadKnowledgeSession(kbase, env);
        if (workflowType in [CentralWorkflowType.ACCESS_GRANTED_BY_FEED, CentralWorkflowType.ACCESS_REVOKED_BY_FEED, CentralWorkflowType.PROVISIONER_DEPROVISIONER_TASKS_ON_ROLE_UPDATE]) {
            parameterMap.put("actorSlid", CENTRAL_SYSTEM_USER_ID)
        } else {
            parameterMap.put("actorSlid", SessionUtils.getSession()?.loggedUser ?: CENTRAL_SYSTEM_USER_ID)
        }
        parameterMap.put("droolsSessionId", knowledgeSession.id.toLong())
        parameterMap.put("workflowGuid", UUID.randomUUID().toString())
        try {
            registerAllWorkItemHandlers(knowledgeSession)
            knowledgeSession.startProcess(processId, parameterMap)
        } catch (Throwable t) {
            t.printStackTrace()
        }
    }

    static def createKnowledgeBaseForFlows(String flowFileName) {
        PackageBuilderConfiguration pkgBuilderCfg = new PackageBuilderConfiguration()
        DialectConfiguration javaConf = pkgBuilderCfg.getDialectConfiguration("java")
        javaConf.setCompiler(JavaDialectConfiguration.JANINO)

        // create knowledge base
        def knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(pkgBuilderCfg)
        knowledgeBuilder.add(ResourceFactory.newClassPathResource(flowFileName), ResourceType.DRF);
        def knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase()
        if (knowledgeBuilder.hasErrors()) {
            def error_str = knowledgeBuilder.getErrors().toString()
            throw new IllegalArgumentException("Could not parse knowledge: $error_str")
        }
        knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages())

        def env = KnowledgeBaseFactory.newEnvironment()
        env.set(EnvironmentName.GLOBALS, new MapGlobalResolver())
        return [knowledgeBase, env]
    }

    public static StatefulKnowledgeSession createOrLoadKnowledgeSession(def kbase, def env) {
        StatefulKnowledgeSession knowledgeSession;
        knowledgeSession = GORMKnowledgeService.newStatefulKnowledgeSession(kbase, null, env)
        return knowledgeSession;
    }

    static void startAccessVerificationWorkflow(Long workerId) {
        CentralWorkflowType workflowType = CentralWorkflowType.ACCESS_VERIFICATION
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        Worker worker = Worker.get(workerId)
        EmployeeSupervisor employeeSupervisor = EmployeeSupervisor.findBySlid(worker.slid)
        List<WorkerEntitlementRole> workerEntitlementRoles = employeeSupervisor.activeWorkerEntitlementRolesForSubordinates
        parameterMap.put("workerId", workerId)
        parameterMap.put("workerEntitlementRoleIds", (workerEntitlementRoles*.id) as Set)
        startWorkflow(parameterMap, workflowType)
    }
}
