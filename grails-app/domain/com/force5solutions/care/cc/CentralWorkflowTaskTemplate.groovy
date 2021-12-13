package com.force5solutions.care.cc

import com.force5solutions.care.common.WorkflowTaskTemplate
import com.force5solutions.care.ldap.SecurityRole
import com.force5solutions.care.workflow.CentralWorkflowTaskType
import com.force5solutions.care.common.CentralMessageTemplate

class CentralWorkflowTaskTemplate extends WorkflowTaskTemplate {

    CentralMessageTemplate messageTemplate
    String id
    Set<String> actions = []
    Set<SecurityRole> actorSecurityRoles = []
    Set<CentralApplicationRole> actorApplicationRoles = []
    Set<CentralApplicationRole> toNotificationApplicationRoles = []
    Set<CentralApplicationRole> ccNotificationApplicationRoles = []
    CentralWorkflowTaskTemplate escalationTemplate
    CentralWorkflowTaskType workflowTaskType

    static hasMany = [actorApplicationRoles: CentralApplicationRole, actorSecurityRoles: SecurityRole,
            toNotificationApplicationRoles: CentralApplicationRole,
            ccNotificationApplicationRoles: CentralApplicationRole,
            actions: String]

    static constraints = {
        id()
        messageTemplate(nullable: true)
        responseForm(nullable: true)
        period(nullable: true)
        periodUnit(nullable: true)
        workflowTaskType(nullable: true)
        actions()
        actorSlids(nullable: true)
        toNotificationSlids(nullable: true)
        toNotificationEmails(nullable: true)
        ccNotificationSlids(nullable: true)
        ccNotificationEmails(nullable: true)
        escalationTemplate(nullable: true)
        respectExclusionList(nullable: true)
    }

    static mapping = {
        id generator: 'assigned'
    }

}
