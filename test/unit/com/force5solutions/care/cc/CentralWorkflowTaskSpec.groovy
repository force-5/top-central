package com.force5solutions.care.cc

import com.force5solutions.care.common.MetaClassHelper
import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.workflow.CentralWorkflowType
import grails.plugin.spock.UnitSpec
import com.force5solutions.care.workflow.CentralWorkflowTaskPermittedSlid

class CentralWorkflowTaskSpec extends UnitSpec {

    def setupSpec() {
        MetaClassHelper.enrichClasses();
    }

    def setup() {
        mockDomain(CentralWorkflowTask)
        mockDomain(CentralWorkflowTaskPermittedSlid)
        mockConfig('''
            emailDomain = '@blah.com'
            '''
        )
    }

    def "A task is created in the database when a log for escalation is requested"() {
        setup:

        CentralWorkflowTask workflowTask = new CentralWorkflowTask();
        workflowTask.with {
            droolsSessionId = 1
            workflowType = CentralWorkflowType.EMPLOYEE_ACCESS_REVOKE
            workflowGuid = "abcd"
            nodeName = "blah"
            workItemId = 5
            nodeId = 35
        }
        workflowTask.save()

        when:
        workflowTask.createEscalationLogTask();
        CentralWorkflowTask escalatedTask = CentralWorkflowTask.list().last()

        then:
        escalatedTask.workItemId == workflowTask.workItemId
        escalatedTask.id != workflowTask.id
        escalatedTask.message.contains("Escalating")
        escalatedTask.droolsSessionId == workflowTask.droolsSessionId
        escalatedTask.workflowGuid == workflowTask.workflowGuid
    }

    def "Notification recipients are returned correctly if there is no worker in the " () {
        setup:

        CentralWorkflowTask workflowTask = new CentralWorkflowTask();
        workflowTask.with {
            droolsSessionId = 1
            workflowType = CentralWorkflowType.EMPLOYEE_ACCESS_REVOKE
            workflowGuid = "abcd"
            nodeName = "blah"
            workItemId = 5
            nodeId = 35
            ['admin1', 'admin2'].each {
                workflowTask.addToPermittedSlids(new CentralWorkflowTaskPermittedSlid(slid: it))
            }
        }
        workflowTask.s()

//        when:
//        Set<String> recipients = workflowTask.getNotificationRecipients()
//
//        then:
//        recipients.size() == 2
    }
}