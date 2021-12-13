package com.force5solutions.care.workflow

import com.force5solutions.care.common.SessionUtils
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.cc.*
import com.force5solutions.care.common.CareConstants

class CentralWorkflowTaskService {

    private static final Date MAX_DATE = new Date(2101, 0, 0, 0, 0, 0)

    static transactional = true
    def securityService
    def centralUtilService

    static config = ConfigurationHolder.config

    // TODO: See if this is being used anywhere else, If yes, then it needs to be changed.
    Boolean hasPermission(String slid = null, CentralWorkflowTask task) {
        slid = slid ?: SessionUtils.getSession()?.loggedUser
        if (!slid) {return false}
        if (task.permittedSlids.find {it.slid == slid && !it.isArchived}) {
            return true
        }
        Collection userSecurityRoles = SessionUtils.session?.roles
        if (userSecurityRoles?.any {it in task.securityRoles} && (task.status == WorkflowTaskStatus.NEW)) {
            return true
        }
        return false
    }

    public void escalateWorkflowTask(CentralWorkflowTask taskToBeEscalated) {
        log.debug "Escalating Task : " + taskToBeEscalated.id
        taskToBeEscalated.createEscalationLogTask()
        CentralWorkflowTaskTemplate escalationTemplate = CentralWorkflowTaskTemplate.findById(taskToBeEscalated.escalationTemplateId)
        Collection<String> slids = CentralUtilService.getSlidsByApplicationRole(escalationTemplate.actorApplicationRoles, taskToBeEscalated.worker, escalationTemplate.respectExclusionList)
        if (escalationTemplate.actorSlids) {slids.addAll(escalationTemplate.actorSlids.tokenize(', '))}

        slids?.each {String slid ->
            if (!CentralWorkflowTaskPermittedSlid.countByTaskAndSlid(taskToBeEscalated, slid)) {
                taskToBeEscalated.addToPermittedSlids(new CentralWorkflowTaskPermittedSlid(slid: slid))
            }
        }
        taskToBeEscalated.securityRoles += escalationTemplate.actorSecurityRoles*.name
        if (escalationTemplate.escalationTemplate) {
            taskToBeEscalated.escalationTemplateId = escalationTemplate.escalationTemplate.id
            taskToBeEscalated.period = escalationTemplate.period
            taskToBeEscalated.periodUnit = escalationTemplate.periodUnit
        } else {
            taskToBeEscalated.escalationTemplateId = null
            taskToBeEscalated.period = null
            taskToBeEscalated.periodUnit = null
        }

        taskToBeEscalated.s();
        Object object = taskToBeEscalated.workerEntitlementRole ?: (taskToBeEscalated.workerCertification ?: (taskToBeEscalated.worker ?: taskToBeEscalated.entitlementPolicy))

        // Send a notification to the new recipients
        if (escalationTemplate && object) {
            Map parameters = CentralUtilService.getParametersForMessageTemplate(object)
            parameters['task'] = taskToBeEscalated

            if (taskToBeEscalated.escalationTemplateId && taskToBeEscalated.workflowType == CentralWorkflowType.ACCESS_VERIFICATION && taskToBeEscalated.escalationTemplateId.equalsIgnoreCase(CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_VERIFICATION_NOTICE).id)) {
                accessVerificationWorkflowChanges(taskToBeEscalated, object, escalationTemplate)
            } else {
                centralUtilService.sendNotification(escalationTemplate, escalationTemplate.messageTemplate, object, parameters)
            }
        }
    }

    private void accessVerificationWorkflowChanges(CentralWorkflowTask taskToBeEscalated, Object object, CentralWorkflowTaskTemplate escalationTemplate) {
        Map parameters = CentralUtilService.getParametersForMessageTemplate(object)
        parameters['task'] = taskToBeEscalated
        if (taskToBeEscalated.worker) {
            parameters['confirmationLink'] = centralUtilService.getConfirmationLink(taskToBeEscalated, taskToBeEscalated.worker?.supervisor?.slid)
        }
        centralUtilService.sendNotification(escalationTemplate, escalationTemplate.messageTemplate, object, parameters)

        // Send the notification to the supervisor without a confirmation link.
        CentralWorkflowTaskTemplate accessVerificationNotificationToOriginalSupervisor = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ESCALATED_ACCESS_VERIFICATION_NOTIFICATION_TO_ORIGINAL_SUPERVISOR)
        parameters['confirmationLink'] = centralUtilService.getConfirmationLink(taskToBeEscalated, taskToBeEscalated.worker?.slid)
        if (escalationTemplate.respectExclusionList) {
            List<String> exclusionSlids = ConfigurationHolder.config.exclusionSlids ? ConfigurationHolder.config.exclusionSlids.toUpperCase().tokenize(', ').findAll {it} : []
            if (!(taskToBeEscalated.worker?.supervisor?.slid in exclusionSlids)) {
                centralUtilService.sendNotification(accessVerificationNotificationToOriginalSupervisor, accessVerificationNotificationToOriginalSupervisor.messageTemplate, object, parameters)
            }
        } else {
            centralUtilService.sendNotification(accessVerificationNotificationToOriginalSupervisor, accessVerificationNotificationToOriginalSupervisor.messageTemplate, object, parameters)
        }
    }

    public void cancelCertificationsTimedOutTasks(Date date = new Date()) {
        int timeOutInDays = ConfigurationHolder.config.certificationCompletionTimeOutInDays.toString().toInteger()
        List<CentralWorkflowTask> tasksTimedOutOnCertificationCompletion = CentralWorkflowTask.findAllByNodeNameAndStatusNotInList('Pending Certifications', [WorkflowTaskStatus.COMPLETE, WorkflowTaskStatus.CANCELLED])?.findAll { (it.lastUpdated + timeOutInDays) < date }
        tasksTimedOutOnCertificationCompletion.each {
            CentralWorkflowUtilService.certificationTimeOutCompleteEvent(it.workerEntitlementRoleId)
        }
    }

    public void autoArchiveCompletedWorkflowTask() {
        String autoArchiveCompletedWorkflowTaskDaysInterval = ConfigurationHolder.config.autoArchiveCompletedWorkflowTaskDaysInterval ?: '30'
        List<CentralWorkflowTask> tasks = CentralWorkflowTask.createCriteria().list {
            and {
                le("dateCreated", (new Date() - autoArchiveCompletedWorkflowTaskDaysInterval.toInteger()))
                eq("status", WorkflowTaskStatus.COMPLETE)
                eq("type", CentralWorkflowTaskType.HUMAN)
                'permittedSlids'{
                    eq('isArchived', false)
                }
            }
        }
        tasks.each { CentralWorkflowTask task ->
            List<CentralWorkflowTaskPermittedSlid> permittedSlids = CentralWorkflowTaskPermittedSlid.findAllByTask(task)
            if (task in permittedSlids*.task) {
                permittedSlids.each { CentralWorkflowTaskPermittedSlid permittedSlid ->
                    CentralWorkflowTaskPermittedSlid.markArchived(permittedSlid.task, permittedSlid.slid)
                }
            }
        }
    }
}
