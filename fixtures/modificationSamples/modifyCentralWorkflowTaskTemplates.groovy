import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.cc.CentralWorkflowTaskTemplate
import com.force5solutions.care.workflow.CentralWorkflowTaskType
import com.force5solutions.care.common.CentralMessageTemplate
import com.force5solutions.care.ldap.SecurityRole
import com.force5solutions.care.cc.CentralApplicationRole

pre {
    CentralWorkflowTaskTemplate newTemplate = new CentralWorkflowTaskTemplate()
    newTemplate.with {
        id = CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_ESCALATION
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_ESCALATION)
        workflowTaskType = CentralWorkflowTaskType.HUMAN
        responseForm = 'userResponseAlertNotification'
        actorSecurityRoles = [SecurityRole.findByName(CareConstants.CAREADMIN)] as Set
    }

    CentralWorkflowTaskTemplate templateToBeModified = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION)
    templateToBeModified.with {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION)
        period = 5
        periodUnit = PeriodUnit.DAYS
        escalationTemplate = newTemplate
        workflowTaskType = CentralWorkflowTaskType.HUMAN
        responseForm = 'userResponseAlertNotification'
        toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.SUPERVISOR] as Set
        actorApplicationRoles = [CentralApplicationRole.SUPERVISOR] as Set
    }

    templateToBeModified = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_30_DAYS)
    templateToBeModified.with {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_30_DAYS)
        period = 5
        periodUnit = PeriodUnit.DAYS
        escalationTemplate = newTemplate
        workflowTaskType = CentralWorkflowTaskType.HUMAN
        responseForm = 'userResponseAlertNotification'
        toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.SUPERVISOR] as Set
        actorApplicationRoles = [CentralApplicationRole.SUPERVISOR] as Set
    }

    templateToBeModified = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_60_DAYS)
    templateToBeModified.with {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_60_DAYS)
        period = 5
        periodUnit = PeriodUnit.DAYS
        escalationTemplate = newTemplate
        workflowTaskType = CentralWorkflowTaskType.HUMAN
        responseForm = 'userResponseAlertNotification'
        toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.SUPERVISOR] as Set
        actorApplicationRoles = [CentralApplicationRole.SUPERVISOR] as Set
    }
}