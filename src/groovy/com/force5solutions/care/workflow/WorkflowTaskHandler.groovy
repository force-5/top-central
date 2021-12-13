package com.force5solutions.care.workflow

import com.force5solutions.care.cc.CentralDataFile
import com.force5solutions.care.cc.CentralWorkflowTaskTemplate
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.drools.persistence.gorm.GORMKnowledgeService
import org.drools.runtime.StatefulKnowledgeSession
import org.drools.runtime.process.WorkItem
import org.drools.runtime.process.WorkItemManager
import com.force5solutions.care.cc.CentralUtilService
import org.apache.commons.logging.LogFactory
import static com.force5solutions.care.common.CareConstants.*

class WorkflowTaskHandler implements org.drools.runtime.process.WorkItemHandler {

    private static log = LogFactory.getLog(this)

    def applicationContext = ApplicationHolder.getApplication().getMainContext()
    def centralUtilService = applicationContext.getBean('centralUtilService')
    def centralWorkflowTaskService = applicationContext.getBean('centralWorkflowTaskService')
    def workerEntitlementRoleService = applicationContext.getBean('workerEntitlementRoleService')

    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        log.debug "**************************Executing NEW WORKFLOW TASK HANDLER: " + workItem.parameters
        CentralWorkflowType workflowType = workItem.parameters.workflowType

        def (kbase, env) = CentralWorkflowUtilService.createKnowledgeBaseForFlows(workflowType.workflowFilePath)
        StatefulKnowledgeSession knowledgeSession = GORMKnowledgeService.loadStatefulKnowledgeSession(workItem.parameters.droolsSessionId.toInteger(), kbase, null, env);

        CentralWorkflowTask task = new CentralWorkflowTask()

        task.workItemId = workItem.id
        task.workflowType = workflowType
        task.droolsSessionId = workItem.parameters.droolsSessionId
        task.effectiveStartDate = workItem.parameters.effectiveStartDate ?: new Date()
        task.workflowGuid = workItem.parameters.workflowGuid
        task.entitlementPolicyId = workItem.parameters.entitlementPolicyId
        task.workerEntitlementRoleId = workItem.parameters.workerEntitlementRoleId
        task.workerId = workItem.parameters.workerId
        task.workerCertificationId = workItem.parameters.workerCertificationId
        task.nodeName = knowledgeSession.getProcessInstance(workItem.processInstanceId).getNodeInstances()[0].nodeName
        task.nodeId = knowledgeSession.getProcessInstance(workItem.processInstanceId).getNodeInstances()[0].nodeId
        task.message = workItem.parameters.justification
        task.actorSlid = workItem.parameters.actorSlid
        task.provisionerDeprovisionerTaskOnRoleUpdateGuid = workItem.parameters.provisionerDeprovisionerTaskOnRoleUpdateGuid

        String workflowTaskTemplateName = workItem.parameters.workflowTaskTemplate
        CentralWorkflowTaskTemplate taskTemplate
        Object object = task.workerEntitlementRole ?: (task.workerCertification ?: (task.worker ?: task.entitlementPolicy))
        if (workflowTaskTemplateName) {
            taskTemplate = CentralWorkflowTaskTemplate.findById(workflowTaskTemplateName)
            if (taskTemplate) {
                task.period = taskTemplate.period
                task.periodUnit = taskTemplate.periodUnit
                task.actions = taskTemplate.actions as List

                Collection<String> slids = CentralUtilService.getSlidsByApplicationRole(taskTemplate.actorApplicationRoles, object, taskTemplate.respectExclusionList)
                if (taskTemplate.actorSlids) {slids.addAll(taskTemplate.actorSlids.tokenize(', '))}
                slids?.each {
                    task.addToPermittedSlids(new CentralWorkflowTaskPermittedSlid(slid: it))
                }
                task.securityRoles = taskTemplate.actorSecurityRoles*.name
                task.responseForm = taskTemplate.responseForm
                task.type = taskTemplate.workflowTaskType
                task.escalationTemplateId = taskTemplate.escalationTemplate?.id
                task.revocationType = taskTemplate.id.equals(WORKFLOW_TASK_TEMPLATE_REVOKE_IN_7_DAYS) ? REVOKE_WORKFLOW_TASK_IN_7_DAYS : (taskTemplate.id.equals(WORKFLOW_TASK_TEMPLATE_REVOKE_IN_24_HOURS) ? REVOKE_WORKFLOW_TASK_IN_24_HOURS : null)
            }
        }

        String documentIdsString = workItem.parameters.documents
        if (documentIdsString) {
            List<Long> documentIds = documentIdsString.tokenize(',').collect {it.toLong()}
            task.documents = CentralDataFile.getAll(documentIds)
        }

        String taskStatus = workItem.parameters.taskStatus
        if (taskStatus) {
            task.status = WorkflowTaskStatus."${taskStatus}"
        }
        task.s()

        if (taskTemplate?.messageTemplate && object) {
            Map parameters = CentralUtilService.getParametersForMessageTemplate(object)
            if (task.worker) {
                parameters['confirmationLink'] = CentralUtilService.getConfirmationLink(task, task.worker?.slid)
            }
            centralUtilService.sendNotification(taskTemplate, taskTemplate.messageTemplate, object, parameters)
        }
        if (task.status == WorkflowTaskStatus.COMPLETE) {
            manager.completeWorkItem(workItem.id, null);
        }
    }

    void abortWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

