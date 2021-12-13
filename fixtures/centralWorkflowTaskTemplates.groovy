import com.force5solutions.care.cc.CentralApplicationRole
import com.force5solutions.care.cc.CentralWorkflowTaskTemplate
import com.force5solutions.care.cc.PeriodUnit
import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.common.CentralMessageTemplate
import com.force5solutions.care.ldap.SecurityRole
import com.force5solutions.care.workflow.CentralWorkflowTaskType
import com.force5solutions.care.common.SessionUtils

pre {

    CentralWorkflowTaskTemplate template

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_INITIAL_TASK)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_INITIAL_TASK) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_INITIAL_TASK
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_APS_SYSTEM_TASK)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_APS_SYSTEM_TASK) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_APS_SYSTEM_TASK
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_APS
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_REVOKE_IN_24_HOURS)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_REVOKE_IN_24_HOURS) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_REVOKE_IN_24_HOURS
            period = 24
            periodUnit = PeriodUnit.HOURS
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_APS
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_REVOKE_IN_7_DAYS)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_REVOKE_IN_7_DAYS) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_REVOKE_IN_7_DAYS
            period = 7
            periodUnit = PeriodUnit.DAYS
            actorSlids = 'admin1'
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_APS
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE
            toNotificationEmails = 'care.force5+toNotification-1@gmail.com, care.force5+toNotification-2@gmail.com'
            toNotificationApplicationRoles = [CentralApplicationRole.SUPERVISOR]
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_APS
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE
            toNotificationEmails = 'care.force5+toNotification-3@gmail.com,care.force5+toNotification-4@gmail.com'
            toNotificationApplicationRoles = [CentralApplicationRole.SUPERVISOR]
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_APS
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR
            toNotificationEmails = 'care.force5+toNotification-1@gmail.com, care.force5+toNotification-2@gmail.com'
            toNotificationApplicationRoles = [CentralApplicationRole.BUSINESS_UNIT_REQUESTER]
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_APS
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR
            toNotificationEmails = 'care.force5+toNotification-3@gmail.com,care.force5+toNotification-4@gmail.com'
            toNotificationApplicationRoles = [CentralApplicationRole.BUSINESS_UNIT_REQUESTER]
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_APS
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CENTRAL_SYSTEM_TASK)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CENTRAL_SYSTEM_TASK) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_CENTRAL_SYSTEM_TASK
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ENTITLEMENT_POLICY_APPROVAL)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ENTITLEMENT_POLICY_APPROVAL) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_ENTITLEMENT_POLICY_APPROVAL
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ENTITLEMENT_POLICY_CREATION)
            actions = ['APPROVE', 'REJECT']
            period = 5
            periodUnit = PeriodUnit.MINUTES
            workflowTaskType = CentralWorkflowTaskType.HUMAN
            responseForm = 'approveNewEntitlementPolicy'
            actorSecurityRoles = [SecurityRole.findByName(CareConstants.CAREADMIN)] as Set
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_APPROVAL_BY_SUPERVISOR)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_APPROVAL_BY_SUPERVISOR) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_APPROVAL_BY_SUPERVISOR
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST)
            actions = ['APPROVE', 'REJECT']
            workflowTaskType = CentralWorkflowTaskType.HUMAN
            responseForm = 'supervisorResponse'
            actorApplicationRoles = [CentralApplicationRole.SUPERVISOR] as Set
            toNotificationApplicationRoles = [CentralApplicationRole.SUPERVISOR] as Set
            actorSecurityRoles = [SecurityRole.findByName(CareConstants.CAREADMIN)] as Set
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_APPROVAL_BY_BUSINESS_UNIT_REQUESTER)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_APPROVAL_BY_BUSINESS_UNIT_REQUESTER) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_APPROVAL_BY_BUSINESS_UNIT_REQUESTER
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST)
            actions = ['APPROVE', 'REJECT']
            workflowTaskType = CentralWorkflowTaskType.HUMAN
            responseForm = 'supervisorResponse'
            actorApplicationRoles = [CentralApplicationRole.BUSINESS_UNIT_REQUESTER] as Set
            toNotificationApplicationRoles = [CentralApplicationRole.BUSINESS_UNIT_REQUESTER] as Set
            actorSecurityRoles = [SecurityRole.findByName(CareConstants.CAREADMIN)] as Set
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_GENERAL)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.SUPERVISOR] as Set
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_FINAL)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_FINAL) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_FINAL
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_FINAL)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.SUPERVISOR] as Set
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL_FOR_CONTRACTOR)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL_FOR_CONTRACTOR) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL_FOR_CONTRACTOR
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_GENERAL)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.BUSINESS_UNIT_REQUESTER] as Set
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_FINAL_FOR_CONTRACTOR)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_FINAL_FOR_CONTRACTOR) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_FINAL_FOR_CONTRACTOR
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_FINAL)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.BUSINESS_UNIT_REQUESTER] as Set
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_VERIFICATION_NOTICE)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_VERIFICATION_NOTICE) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_VERIFICATION_NOTICE
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_NOTICE)
            toNotificationEmails = 'care.force5+toAccessVerificationNotice-1@gmail.com, care.force5+toAccessVerificationNotice-2@gmail.com'
            ccNotificationEmails = 'care.force5+ccAccessVerificationNotice-1@gmail.com, care.force5+ccAccessVerificationNotice-2@gmail.com'
            toNotificationApplicationRoles = [CentralApplicationRole.SUPERVISOR, CentralApplicationRole.WORKER] as Set
            ccNotificationApplicationRoles = [CentralApplicationRole.SUBORDINATES] as Set
        }
        template.s()
    }

     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ESCALATED_ACCESS_VERIFICATION_NOTIFICATION_TO_ORIGINAL_SUPERVISOR)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ESCALATED_ACCESS_VERIFICATION_NOTIFICATION_TO_ORIGINAL_SUPERVISOR) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_ESCALATED_ACCESS_VERIFICATION_NOTIFICATION_TO_ORIGINAL_SUPERVISOR
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_ESCALATION_NOTIFICATION_TO_ORIGINAL_SUPERVISOR)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER] as Set
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ESCALATED_ACCESS_VERIFICATION)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ESCALATED_ACCESS_VERIFICATION) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_ESCALATED_ACCESS_VERIFICATION
            period = 5
            periodUnit = PeriodUnit.DAYS
            escalationTemplate = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_VERIFICATION_NOTICE)
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_ESCALATION)
            workflowTaskType = CentralWorkflowTaskType.HUMAN
            responseForm = 'accessVerificationTask'
            actorApplicationRoles = [CentralApplicationRole.SUPERVISOR] as Set
            toNotificationApplicationRoles = [CentralApplicationRole.SUPERVISOR] as Set
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_VERIFICATION)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_VERIFICATION) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_ACCESS_VERIFICATION
            period = 2
            periodUnit = PeriodUnit.DAYS
            escalationTemplate = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_ESCALATED_ACCESS_VERIFICATION)
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION)
            workflowTaskType = CentralWorkflowTaskType.HUMAN
            responseForm = 'accessVerificationTask'
            actorApplicationRoles = [CentralApplicationRole.WORKER] as Set
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER] as Set
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.SEND_ACCESS_REQUEST_CONFIRMATION_EMAIL_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.SEND_ACCESS_REQUEST_CONFIRMATION_EMAIL_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.SEND_ACCESS_REQUEST_CONFIRMATION_EMAIL_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_FOR_EMPLOYEE)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER] as Set
            ccNotificationEmails = 'care.force5+bcc@gmail.com'
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.SEND_ACCESS_REQUEST_CONFIRMATION_EMAIL_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.SEND_ACCESS_REQUEST_CONFIRMATION_EMAIL_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.SEND_ACCESS_REQUEST_CONFIRMATION_EMAIL_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_FOR_CONTRACTOR)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER] as Set
            ccNotificationEmails = 'care.force5+bcc@gmail.com'
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.SUPERVISOR_REJECT_NOTIFICATION_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.SUPERVISOR_REJECT_NOTIFICATION_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.SUPERVISOR_REJECT_NOTIFICATION_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_SUPERVISOR_REJECT_FOR_EMPLOYEE)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER] as Set
            ccNotificationEmails = 'care.force5+bcc@gmail.com'
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.SUPERVISOR_REJECT_NOTIFICATION_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.SUPERVISOR_REJECT_NOTIFICATION_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.SUPERVISOR_REJECT_NOTIFICATION_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_SUPERVISOR_REJECT_FOR_CONTRACTOR)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER] as Set
            ccNotificationEmails = 'care.force5+bcc@gmail.com'
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.MISSING_CERTIFICATION_DETAILS_EMAIL_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.MISSING_CERTIFICATION_DETAILS_EMAIL_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.MISSING_CERTIFICATION_DETAILS_EMAIL_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_MISSING_CERTIFICATIONS_EMAIL_TO_EMPLOYEE)
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER] as Set
            ccNotificationEmails = 'care.force5+bcc@gmail.com'
        }
        template.s()
    }

 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.MISSING_CERTIFICATION_DETAILS_EMAIL_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.MISSING_CERTIFICATION_DETAILS_EMAIL_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.MISSING_CERTIFICATION_DETAILS_EMAIL_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_MISSING_CERTIFICATIONS_EMAIL_TO_CONTRACTOR)
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER] as Set
            ccNotificationEmails = 'care.force5+bcc@gmail.com'
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.TIMEOUT_NOTIFICATION_CENTRAL_SYSTEM_TASK_TEMPLATE)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.TIMEOUT_NOTIFICATION_CENTRAL_SYSTEM_TASK_TEMPLATE) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.TIMEOUT_NOTIFICATION_CENTRAL_SYSTEM_TASK_TEMPLATE
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_TIME_OUT_NOTIFICATION)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.SUPERVISOR] as Set
            ccNotificationEmails = 'care.force5+bcc@gmail.com'
        }
        template.s()
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.TIMEOUT_NOTIFICATION_CENTRAL_SYSTEM_TASK_TEMPLATE_FOR_CONTRACTOR)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.TIMEOUT_NOTIFICATION_CENTRAL_SYSTEM_TASK_TEMPLATE_FOR_CONTRACTOR) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.TIMEOUT_NOTIFICATION_CENTRAL_SYSTEM_TASK_TEMPLATE_FOR_CONTRACTOR
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_TIME_OUT_NOTIFICATION_FOR_CONTRACTOR)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.BUSINESS_UNIT_REQUESTER] as Set
            ccNotificationEmails = 'care.force5+bcc@gmail.com'
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION_EMAIL_CENTRAL_SYSTEM_TASK)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION_EMAIL_CENTRAL_SYSTEM_TASK) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION_EMAIL_CENTRAL_SYSTEM_TASK
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.SUPERVISOR] as Set
            ccNotificationEmails = 'care.force5+bcc@gmail.com'
        }
        template.s()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralWorkflowTaskTemplate.findById(CareConstants.ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION_EMAIL_CENTRAL_SYSTEM_TASK_FOR_CONTRACTOR)))
    if (overrideOrCreate) {
        template = CentralWorkflowTaskTemplate.findById(CareConstants.ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION_EMAIL_CENTRAL_SYSTEM_TASK_FOR_CONTRACTOR) ?: new CentralWorkflowTaskTemplate()
        template.with {
            id = CareConstants.ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION_EMAIL_CENTRAL_SYSTEM_TASK_FOR_CONTRACTOR
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.BUSINESS_UNIT_REQUESTER] as Set
            ccNotificationEmails = 'care.force5+bcc@gmail.com'
        }
        template.s()
    }
}

fixture {
}
