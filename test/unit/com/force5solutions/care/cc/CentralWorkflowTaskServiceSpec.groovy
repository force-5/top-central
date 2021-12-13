package com.force5solutions.care.cc

import com.force5solutions.care.common.MetaClassHelper
import grails.plugin.spock.UnitSpec

import com.force5solutions.care.workflow.CentralWorkflowTaskService
import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.ldap.SecurityRole
import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.workflow.CentralWorkflowType
import com.force5solutions.care.workflow.CentralWorkflowTaskType
import com.force5solutions.care.common.CentralMessageTemplate
import com.force5solutions.care.workflow.CentralWorkflowTaskPermittedSlid
import ru.perm.kefir.asynchronousmail.AsynchronousMailService

class CentralWorkflowTaskServiceSpec extends UnitSpec {

    CentralWorkflowTaskService service;

    def setupSpec() {
        MetaClassHelper.enrichClasses();
    }

    def setup() {
        mockDomain(CentralWorkflowTask)
        mockDomain(SecurityRole)
        mockDomain(CentralWorkflowTaskTemplate)
        mockDomain(CentralMessageTemplate)
        mockDomain(WorkerEntitlementRole)
        mockDomain(WorkerCertification)
        mockDomain(CentralWorkflowTaskPermittedSlid)
        mockLogging(CentralWorkflowTaskService)
        service = new CentralWorkflowTaskService();
        def centralUtilService = Mock(CentralUtilService)
        def asynchronousMailService = Mock(AsynchronousMailService)
        asynchronousMailService.sendAsynchronousMail(_)  >> {}
        service.centralUtilService = centralUtilService
        centralUtilService.asynchronousMailService = asynchronousMailService
        mockConfig('''
            grails.serverURL = 'http://www.google.com'
            '''
        )
    }

    def "Basic Test"() {
        setup:

        CentralMessageTemplate messageTemplate = new CentralMessageTemplate();
        messageTemplate.with {
            name = "Dummy Template"
            transportType = TransportType.EMAIL
            subjectTemplate = "subject"
            bodyTemplate = "body"
            s()
        }

        SecurityRole careEditor = new SecurityRole()
        careEditor.name = CareConstants.CAREEDITOR
        careEditor.description = "description"
        careEditor.s();

        CentralWorkflowTask taskToBeEscalated = new CentralWorkflowTask();
        CentralWorkflowTaskTemplate escalationTemplate = new CentralWorkflowTaskTemplate();
        escalationTemplate.messageTemplate = messageTemplate
        escalationTemplate.with {
            id = "defaultEscalationTemplate"
            periodUnit = PeriodUnit.DAYS
            period = 2
            responseForm = "response.gsp"
            workflowTaskType = CentralWorkflowTaskType.HUMAN
            actorSlids = 'slid1, slid2'
            actions = ['CONFIRM'] as Set
            actorApplicationRoles = [CentralApplicationRole.BUSINESS_UNIT_REQUESTER, CentralApplicationRole.SUPERVISOR] as Set
            actorSecurityRoles = [SecurityRole.findByName(CareConstants.CAREEDITOR)]
            s()
        }

        taskToBeEscalated.with {
            workerCertificationId = null
            workerCertificationId = null
            escalationTemplateId = escalationTemplate.id
            droolsSessionId = 1
            workflowType = CentralWorkflowType.EMPLOYEE_ACCESS_REVOKE
            workflowGuid = "guid1"
            nodeName = "nodeName1"
            workItemId = 5
            nodeId = 35
        }
        taskToBeEscalated.s()

        when:

        service.escalateWorkflowTask(taskToBeEscalated)

        then:
        messageTemplate.id != null
        escalationTemplate.id != null
        CentralWorkflowTaskTemplate.count() == 1
        CentralMessageTemplate.count() == 1
        escalationTemplate.messageTemplate != null
        CentralWorkflowTask.count() == 2
    }
}

