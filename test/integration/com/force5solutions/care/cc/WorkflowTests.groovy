package com.force5solutions.care.cc

import static com.force5solutions.care.common.CareConstants.*
import grails.test.GrailsUnitTestCase

import com.force5solutions.care.web.EntitlementRoleDTO
import com.force5solutions.care.workflow.CentralWorkflowTask

import com.force5solutions.care.workflow.CentralWorkflowUtilService
import java.beans.XMLEncoder

import com.force5solutions.care.workflow.CentralWorkflowType
import com.force5solutions.care.workflow.WorkflowUtilService

class WorkflowTests extends GrailsUnitTestCase {

    def workerService
    def employeeUtilService
    def careCentralService
    def workerCertificationService
    def workerEntitlementRoleService
    def entitlementPolicyService

    Worker employee
    Worker employeeWithCertifications
    CcEntitlementRole entitlementRole

    String getEncodedString(Map map) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLEncoder xmlEncoder = new XMLEncoder(bos);
        xmlEncoder.writeObject(map);
        xmlEncoder.flush();
        return bos.toString()
    }

    protected void setUp() {
        employee = getAnEmployee("empslid-${(new Random().nextInt(10) + 1)}")
        employeeWithCertifications = getAnEmployeeWithCertifications()
        entitlementRole = getAnEntitlementRole()
    }

    protected void tearDown() {
    }

//    EntitlementPolicy getAnEntitlementPolicy(){
//        EntitlementPolicy type = new EntitlementPolicy(name: 'Type-1')
//        type.s()
//        assertNotNull(type?.id)
//        return type
//    }
//
//    CcEntitlement getAnEntitlement() {
//        CcEntitlement entitlement = new CcEntitlement()
//        entitlement.with{
//            name = "Entitlement-1"
//            alias = "Entitlement-1"
//            type = getAnEntitlementPolicy()
//            origin = CcOrigin.get(1)
//        }
//        entitlement.s()
//        assertNotNull(entitlement?.id)
//        return entitlement
//    }

    CcEntitlementRole getAnEntitlementRole() {
        EntitlementRoleDTO entitlementRoleDTO = new EntitlementRoleDTO()
        entitlementRoleDTO.with {
            id = UUID.randomUUID()
            name = "EntitlementRole-1"
            status = "INACTIVE"
            gatekeepers = "gatekeeper-1"
        }
        CcEntitlementRole entitlementRole = new CcEntitlementRole(entitlementRoleDTO)
        assertNotNull(entitlementRole?.id)
        entitlementRole.requiredCertifications = [Certification.get(1), Certification.get(2)] as Set
        entitlementRole.s()
        assertEquals(2, entitlementRole.requiredCertifications.size())
        return entitlementRole
    }

    Employee getAnEmployee(String empSlid) {
        EmployeeCO employeeCO = new EmployeeCO()
        employeeCO.with {
            firstName = "Emp1_FN"
            lastName = "Emp1_LN"
            slid = empSlid
            supervisor = 1
            phone = '12345'
            businessUnitRequester = 1
        }
        Employee employee = employeeUtilService.createEmployee(employeeCO)
        employeeUtilService.saveNewEmployee(employee)
        assertNotNull(employee?.id)
        return employee
    }

    Employee getAnEmployeeWithCertifications() {
        Employee employee = getAnEmployee("empslid-${(new Random().nextInt(10) + 11)}")
        List<Certification> certifications = Certification.list()
        certifications.each {Certification certification ->
            workerCertificationService.saveNewWorkerCertificationWithDateCompleted(employee.id, certification.id, new Date())
        }
        return employee
    }

    void testAccessRequestBySomeUser_APPROVED() {
        WorkerEntitlementRole workerEntitlementRole = workerEntitlementRoleService.createWorkerEntitlementRole(employee, entitlementRole.id, "Entitlement Role added by CARE System Tests", CENTRAL_SYSTEM_USER_ID)
        assertNotNull(workerEntitlementRole.id)
        employee = employee.refresh()
        assertTrue(employee.entitlementRoles.size() > 0)
        CentralWorkflowTask task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST, task.workflowType)
        assertEquals("Pending APS Approval", task.nodeName)
        Map responseElements = ['userAction': 'APPROVE', 'accessJustification': 'Approved by Care System Tests']
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST, task.workflowType)
        assertEquals("Entitlement Role Provisioned Notification", task.nodeName)
        assertTrue(employee.missingCertifications.size() > 0)
        List<Certification> certifications = Certification.list()
        certifications.each {Certification certification ->
            workerCertificationService.saveNewWorkerCertificationWithDateCompleted(employee.id, certification.id, new Date())
        }
        employee = employee.refresh()
        assertEquals(0, employee.missingCertifications.size())
        task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST, task.workflowType)
        assertEquals("Entitlement Role Provisioned Notification", task.nodeName)
        responseElements = ['userAction': 'APPROVE', 'accesJustification': 'Approved by Care System Tests']
        careCentralService.processEntitlementManagerResponse('admin', 'admin', task.id, getEncodedString(responseElements))
        employee = employee.refresh()
        assertEquals(WorkerStatus.ACTIVE, employee.status)
    }

    void testAccessRequestBySponsor_APPROVED() {
        employee.supervisor.person.slid = CENTRAL_SYSTEM_USER_ID
        employee.supervisor.person.s()
        WorkerEntitlementRole workerEntitlementRole = workerEntitlementRoleService.createWorkerEntitlementRole(employee, entitlementRole.id, "Entitlement Role added by CARE System Tests", employee.supervisor.slid)
        assertNotNull(workerEntitlementRole.id)
        employee = employee.refresh()
        assertTrue(employee.entitlementRoles.size() > 0)
        CentralWorkflowTask task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(employee.id, task.worker.id)
        assertEquals(CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST, task.workflowType)
        assertEquals("Pending APS Approval", task.nodeName)
        CentralWorkflowUtilService.sendResponseElements(task, ['userAction': 'APPROVE'])
        task = CentralWorkflowTask.list().last()
        assertEquals("Entitlement Role Provisioned Notification", task.nodeName)
        assertTrue(employee.missingCertifications.size() > 0)
        List<Certification> certifications = Certification.list()
        certifications.each {Certification certification ->
            workerCertificationService.saveNewWorkerCertificationWithDateCompleted(employee.id, certification.id, new Date())
        }
        employee = employee.refresh()
        assertEquals(0, employee.missingCertifications.size())
        task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST, task.workflowType)
        assertEquals("Entitlement Role Provisioned Notification", task.nodeName)
        Map responseElements = ['userAction': 'APPROVE', 'accesJustification': 'Approved by Care System Tests']
        careCentralService.processEntitlementManagerResponse('admin', 'admin', task.id, getEncodedString(responseElements))
        employee = employee.refresh()
        assertEquals(WorkerStatus.ACTIVE, employee.status)
    }

    void test_TERMINATED_FOR_CAUSE() {
        employee.supervisor.person.slid = CENTRAL_SYSTEM_USER_ID
        employee.supervisor.person.s()
        WorkerEntitlementRole workerEntitlementRole = workerEntitlementRoleService.createWorkerEntitlementRole(employee, entitlementRole.id, "Entitlement Role added by CARE System Tests", employee.supervisor.slid)
        assertNotNull(workerEntitlementRole.id)
        employee = employee.refresh()
        assertTrue(employee.entitlementRoles.size() > 0)
        CentralWorkflowTask task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(employee.id, task.worker.id)
        assertEquals(CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST, task.workflowType)
        assertEquals("Pending APS Approval", task.nodeName)
        CentralWorkflowUtilService.sendResponseElements(task, ['userAction': 'APPROVE'])
        task = CentralWorkflowTask.list().last()
        assertEquals("Entitlement Role Provisioned Notification", task.nodeName)
        assertTrue(employee.missingCertifications.size() > 0)
        List<Certification> certifications = Certification.list()
        certifications.each {Certification certification ->
            workerCertificationService.saveNewWorkerCertificationWithDateCompleted(employee.id, certification.id, new Date())
        }
        employee = employee.refresh()
        assertEquals(0, employee.missingCertifications.size())
        task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST, task.workflowType)
        assertEquals("Entitlement Role Provisioned Notification", task.nodeName)
        Map responseElements = ['userAction': 'APPROVE', 'accesJustification': 'Approved by Care System Tests']
        careCentralService.processEntitlementManagerResponse('admin', 'admin', task.id, getEncodedString(responseElements))
        employee = employee.refresh()
        assertEquals(WorkerStatus.ACTIVE, employee.status)
        workerService.requestTermination(employee, CENTRAL_SYSTEM_USER_ID, 'Terminate requested by CARE System Tests')
        workerEntitlementRole = workerEntitlementRole.refresh()
        assertEquals(EntitlementRoleAccessStatus.pendingTermination, workerEntitlementRole.status)
        task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(CentralWorkflowType.EMPLOYEE_TERMINATION, task.workflowType)
        assertTrue(task.nodeName.contains("Pending Termination by APS"))
        responseElements = ['accessJustification': 'Terminated by Care System Tests']
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        employee = employee.refresh()
        assertEquals(WorkerStatus.TERMINATED, employee.status)
    }

    void test_REVOKE_ACCESS() {
        WorkerEntitlementRole workerEntitlementRole = workerEntitlementRoleService.createWorkerEntitlementRole(employee, entitlementRole.id, "Entitlement Role added by CARE System Tests", CENTRAL_SYSTEM_USER_ID)
        assertNotNull(workerEntitlementRole.id)
        employee = employee.refresh()
        assertTrue(employee.entitlementRoles.size() > 0)
        CentralWorkflowTask task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST, task.workflowType)
        assertEquals("Pending APS Approval", task.nodeName)
        Map responseElements = ['userAction': 'APPROVE', 'accessJustification': 'Approved by Care System Tests']
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST, task.workflowType)
        assertEquals("Entitlement Role Provisioned Notification", task.nodeName)
        assertTrue(employee.missingCertifications.size() > 0)
        List<Certification> certifications = Certification.list()
        certifications.each {Certification certification ->
            workerCertificationService.saveNewWorkerCertificationWithDateCompleted(employee.id, certification.id, new Date())
        }
        employee = employee.refresh()
        assertEquals(0, employee.missingCertifications.size())
        task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST, task.workflowType)
        assertEquals("Entitlement Role Provisioned Notification", task.nodeName)
        responseElements = ['userAction': 'APPROVE', 'accesJustification': 'Approved by Care System Tests']
        careCentralService.processEntitlementManagerResponse('admin', 'admin', task.id, getEncodedString(responseElements))
        employee = employee.refresh()
        assertEquals(WorkerStatus.ACTIVE, employee.status)
        workerEntitlementRoleService.changeEntitlementRoleStatus(workerEntitlementRole.id, EntitlementRoleAccessStatus.pendingRevocation, CENTRAL_SYSTEM_USER_ID)
        CentralWorkflowUtilService.startRevokeRequestWorkflow(workerEntitlementRole)
        workerEntitlementRole = workerEntitlementRole.refresh()
        assertEquals(EntitlementRoleAccessStatus.pendingRevocation, workerEntitlementRole.status)
        task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(CentralWorkflowType.EMPLOYEE_ACCESS_REVOKE, task.workflowType)
        assertTrue(task.nodeName.contains("Pending Revocation"))
        assertEquals("Pending Revocation by APS", task.nodeName)
        responseElements = ['userAction': 'CONFIRM', 'accesJustification': 'Access Revoked by Care System Tests']
        careCentralService.processEntitlementManagerResponse('admin', 'admin', task.id, getEncodedString(responseElements))
        employee = employee.refresh()
        assertEquals(WorkerStatus.INACTIVE, employee.status)
        workerEntitlementRole = workerEntitlementRole.refresh()
        assertEquals(EntitlementRoleAccessStatus.revoked, workerEntitlementRole.status)
    }

    void testAccessRequest_REJECTED_BY_SPONSOR() {
        WorkerEntitlementRole workerEntitlementRole = workerEntitlementRoleService.createWorkerEntitlementRole(employee, entitlementRole.id, "Entitlement Role added by CARE System Tests", CENTRAL_SYSTEM_USER_ID)
        assertNotNull(workerEntitlementRole.id)
        employee = employee.refresh()
        assertTrue(employee.entitlementRoles.size() > 0)
        CentralWorkflowTask task = CentralWorkflowTask.list().last()
        assertNotNull(task)
        assertEquals(workerEntitlementRole.id, task.workerEntitlementRoleId)
        assertEquals(CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST, task.workflowType)
        assertEquals("Pending APS Approval", task.nodeName)
        Map responseElements = ['userAction': 'REJECT', 'accessJustification': 'Rejected by Care System Tests']
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        workerEntitlementRole = workerEntitlementRole.refresh()
        assertEquals(EntitlementRoleAccessStatus.rejected, workerEntitlementRole.status)
    }

    public void test_CREATE_ENTITLEMENT_POLICY_AND_APPROVE_FROM_CAREADMIN() {
        def entitlementPolicy = createEntitlementPolicy()
        entitlementPolicyService.save(entitlementPolicy)
        assertFalse(entitlementPolicy.isApproved)
        def task = CentralWorkflowTask.list().last()
        assertEquals(entitlementPolicy.id, task.entitlementPolicyId)
        assertEquals("ADD Entitlement Policy", task.workflowType.name)
        assertEquals("Get approval from CAREADMIN", task.nodeName)
        assertTrue(task.instanceOf(CentralWorkflowTask.class))
        Map responseElements = ['userAction': 'APPROVE', 'accessJustification': 'Approved By APS System Tests']
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        assertEquals("ADD Entitlement Policy", task.workflowType.name)
        assertEquals(entitlementPolicy.id, task.entitlementPolicyId)
        assertTrue(entitlementPolicy.isApproved)
        assertEquals("ET-1", entitlementPolicy.name)
        assertEquals(['S-1', 'S-2'], entitlementPolicy.standards)
    }

    public void test_CREATE_ENTITLEMENT_POLICY_AND_REJECTION_FROM_CAREADMIN() {
        def entitlementPolicy = createEntitlementPolicy()
        entitlementPolicyService.save(entitlementPolicy)
        assertFalse(entitlementPolicy.isApproved)
        def task = CentralWorkflowTask.list().last()
        assertEquals(entitlementPolicy.id, task.entitlementPolicyId)
        assertEquals("ADD Entitlement Policy", task.workflowType.name)
        assertEquals("Get approval from CAREADMIN", task.nodeName)
        assertTrue(task.instanceOf(CentralWorkflowTask.class))
        Map responseElements = ['userAction': 'REJECT', 'accessJustification': 'Rejected By APS System Tests']
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        assertFalse(entitlementPolicy.isApproved)
    }

    public void test_UPDATE_ENTITLEMENT_POLICY_AND_APPROVAL_FROM_CAREADMIN() {
        def entitlementPolicy = createEntitlementPolicy()
        entitlementPolicyService.save(entitlementPolicy)
        assertFalse(entitlementPolicy.isApproved)
        def task = CentralWorkflowTask.list().last()
        assertEquals(entitlementPolicy.id, task.entitlementPolicyId)
        assertEquals("ADD Entitlement Policy", task.workflowType.name)
        assertEquals("Get approval from CAREADMIN", task.nodeName)
        assertTrue(task.instanceOf(CentralWorkflowTask.class))
        Map responseElements = ['userAction': 'APPROVE', 'accessJustification': 'Approved By APS System Tests']
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        assertTrue(entitlementPolicy.isApproved)

        entitlementPolicy = entitlementPolicy.refresh()

        def newStandards = ['S-1', 'S-3']
        entitlementPolicy.standards = newStandards
        entitlementPolicyService.save(entitlementPolicy)
        task = CentralWorkflowTask.list().last()
        assertEquals("UPDATE Entitlement Policy", task.workflowType.name)
        assertEquals("Get approval from CAREADMIN", task.nodeName)
        assertTrue(task.instanceOf(CentralWorkflowTask.class))
        responseElements = ['userAction': 'APPROVE', 'accessJustification': 'Approved By APS System Tests']
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        entitlementPolicy = entitlementPolicy.refresh()
        assertEquals("ET-1", entitlementPolicy.name)
        assertEquals(newStandards, entitlementPolicy.standards)
    }

    public void test_UPDATE_ENTITLEMENT_POLICY_AND_REJECTION_FROM_CAREADMIN() {
        def entitlementPolicy = createEntitlementPolicy()
        entitlementPolicyService.save(entitlementPolicy)
        assertFalse(entitlementPolicy.isApproved)
        def task = CentralWorkflowTask.list().last()
        assertEquals(entitlementPolicy.id, task.entitlementPolicyId)
        assertEquals("ADD Entitlement Policy", task.workflowType.name)
        assertEquals("Get approval from CAREADMIN", task.nodeName)
        assertTrue(task.instanceOf(CentralWorkflowTask.class))
        Map responseElements = ['userAction': 'APPROVE', 'accessJustification': 'Approved By APS System Tests']
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        assertTrue(entitlementPolicy.isApproved)

        entitlementPolicy = entitlementPolicy.refresh()

        def newStandards = ['S-1', 'S-3']
        entitlementPolicy.standards = newStandards
        entitlementPolicyService.save(entitlementPolicy)
        task = CentralWorkflowTask.list().last()
        assertEquals(entitlementPolicy.id, task.entitlementPolicyId)
        assertEquals("UPDATE Entitlement Policy", task.workflowType.name)
        assertEquals("Get approval from CAREADMIN", task.nodeName)
        assertTrue(task.instanceOf(CentralWorkflowTask.class))
        responseElements = ['userAction': 'REJECT', 'accessJustification': 'Rejected By APS System Tests']
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        entitlementPolicy = entitlementPolicy.refresh()
        assertEquals("ET-1", entitlementPolicy.name)
        assert newStandards != entitlementPolicy.standards
    }

    public void test_UPDATE_ENTITLEMENT_POLICY_NAME_AND_NO_WORKFLOW_INITIATED() {
        def initialTasksCount = CentralWorkflowTask.count()
        def entitlementPolicy = createEntitlementPolicy()
        entitlementPolicyService.save(entitlementPolicy)
        assertFalse(entitlementPolicy.isApproved)
        def currentTasksCount = CentralWorkflowTask.count()
        def task = CentralWorkflowTask.list().last()
        assertTrue(currentTasksCount > initialTasksCount)
        assertEquals(2, currentTasksCount)
        assertEquals(entitlementPolicy.id, task.entitlementPolicyId)
        assertEquals("ADD Entitlement Policy", task.workflowType.name)
        assertTrue(task?.instanceOf(CentralWorkflowTask.class))
        Map responseElements = ['userAction': 'APPROVE', 'accessJustification': 'Approved By APS System Tests']
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        assertTrue(entitlementPolicy.isApproved)

        entitlementPolicy = entitlementPolicy.refresh()
        entitlementPolicy.name = "ET - New"           // The 'name' field is not included in the 'requireApprovalObjects' list of EntitlementPolicy domain class
        entitlementPolicyService.save(entitlementPolicy)
        entitlementPolicy = entitlementPolicy.refresh()
        assertEquals(2, currentTasksCount)             // The current task count is still 1. No new task/workflow initiated in case of updation of 'name' field.
        assertEquals("ET - New", entitlementPolicy.name)
    }

    EntitlementPolicy createEntitlementPolicy() {
        List<String> standards = ['S-1', 'S-2']
        EntitlementPolicy entitlementPolicy = new EntitlementPolicy(name: "ET-1", standards: standards)
        return entitlementPolicy
    }
}
