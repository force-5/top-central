package com.force5solutions.care.cc

import com.force5solutions.care.workflow.CentralWorkflowUtilService
import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.workflow.CentralWorkflowType
import com.force5solutions.care.workflow.WorkflowTaskStatus
import com.force5solutions.care.common.CareConstants
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.plugins.versionable.VersioningContext
import com.force5solutions.care.workflow.CentralWorkflowTaskType
import groovy.time.TimeCategory

class AccessVerificationService {

    boolean transactional = true
    private static final String REVOKE_MESSAGE = 'Revoke requested since access not verified by supervisor'

    def createApsTaskForAccessVerification(Date date) {
        Integer hours = ConfigurationHolder.config.accessVerificationWaitPeriodInHours ? ConfigurationHolder.config.accessVerificationWaitPeriodInHours?.toInteger() : 144
        List<CentralWorkflowTask> tasks = CentralWorkflowTask.findAllWhere(workflowType: CentralWorkflowType.ACCESS_VERIFICATION, status: WorkflowTaskStatus.NEW, escalationTemplateId: null)
        use(TimeCategory) {
            tasks = tasks.findAll {((((it.effectiveStartDate + hours.hours) as Date) < date) && (it.type != CentralWorkflowTaskType.SYSTEM_APS))}
        }
        tasks.each {CentralWorkflowTask task ->
            CentralWorkflowUtilService.sendResponseElements(task, ['userAction': 'CREATE APS TASK'])
        }
    }

    def execute() {
        List<String> supervisorSlids = getSupervisorSlidList()
        supervisorSlids.each { String slid ->
            Worker worker = Employee.findBySlid(slid)
            if (worker) {
                CentralWorkflowUtilService.startAccessVerificationWorkflow(worker.id)
            }
        }
    }

    private def getSupervisorSlidList() {
        List activeWorkerEntitlementRoles = WorkerEntitlementRole.findAllByStatus(EntitlementRoleAccessStatus.ACTIVE)
        List<String> employeeSupervisorSlids = []

        activeWorkerEntitlementRoles.each { WorkerEntitlementRole workerEntitlementRole ->
            employeeSupervisorSlids.add(workerEntitlementRole.worker.supervisor?.slid)
        }
        List<String> exclusionSlids = ConfigurationHolder.config.exclusionSlids ? ConfigurationHolder.config.exclusionSlids.toUpperCase().tokenize(', ').findAll {it} : []
        employeeSupervisorSlids = employeeSupervisorSlids.findAll {it && !(it in exclusionSlids)}
        employeeSupervisorSlids = employeeSupervisorSlids.unique()
        return employeeSupervisorSlids
    }

    public Map accessVerificationReport(String slid) {
        EmployeeSupervisor employeeSupervisor = EmployeeSupervisor.findBySlid(slid)
        Map<CcEntitlementRole, List<Worker>> activeWorkersGroupByEntitlementRoles = employeeSupervisor?.getActiveWorkersGroupByEntitlementRole()


        Map rolesMap = new HashMap()
        activeWorkersGroupByEntitlementRoles?.each {entitlementRole, workers ->
            List<EmpVO> empVOs = []
            workers.each { Worker worker ->
                empVOs << new EmpVO(worker.slid)
            }

            rolesMap.put(entitlementRole, empVOs)
        }
        return rolesMap
    }
}

class EmpVO {
    String name
    String slid
    String badge

    EmpVO() {}

    EmpVO(String slidValue) {
        Employee employee = Employee.findBySlid(slidValue)
        name = employee.name
        slid = employee.slid
        badge = employee.badgeNumber
    }

}

