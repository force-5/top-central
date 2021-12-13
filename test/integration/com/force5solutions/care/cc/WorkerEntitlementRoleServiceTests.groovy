package com.force5solutions.care.cc

import grails.test.*


import com.force5solutions.care.cc.CcEntitlementRole
import com.force5solutions.care.cc.*

class WorkerEntitlementRoleServiceTests extends GrailsUnitTestCase {
    static String loggedUser = "Integration Test User"

    def contractorService
    def auditService
    def workerEntitlementRoleService
    def workerService

    Contractor contractor
    WorkerEntitlementRole workerEntitlementRole

    protected void setUp() {
        contractor = getContractorWithAllCertifications()
        CcEntitlementRole entitlementRole = CcEntitlementRole.listUndeleted().first()
        workerEntitlementRole = workerEntitlementRoleService.createWorkerEntitlementRole(contractor, entitlementRole.id, "Access Justification from integration test", loggedUser)
    }

    protected void tearDown() {
    }

    void testAccessRequest() {
        assertTrue(contractor.certifications.size() == Certification.count())
        assertNotNull("Worker CcEntitlement Role was not saved", workerEntitlementRole?.id)
        assertEquals("CcEntitlement Role Status is not equal to PENDING_CCGATEKEEPER_APPROVAL", EntitlementRoleAccessStatus.pendingApproval, workerEntitlementRole.status)
    }

    void testChangeAccessStatus() {
        workerEntitlementRoleService.changeEntitlementRoleStatus(workerEntitlementRole.id, EntitlementRoleAccessStatus.active, loggedUser)
        workerEntitlementRole = WorkerEntitlementRole.get(workerEntitlementRole.id)
        assertEquals(EntitlementRoleAccessStatus.active, workerEntitlementRole.status)
    }

    void testTerminateForCauseRequest() {
        Boolean isTerminated = workerService.requestTermination(workerEntitlementRole.worker,loggedUser, "Terminate For Cause Requested")
        workerEntitlementRole = WorkerEntitlementRole.get(workerEntitlementRole.id)
        assertEquals(EntitlementRoleAccessStatus.pendingTermination, workerEntitlementRole.status)
        Worker worker = Worker.get(workerEntitlementRole.worker.id)
        assertNotNull(worker.terminateForCause)
    }

    Contractor getContractorWithAllCertifications() {
        Person person = getAPersonInfo("wpslid11")
        Contractor contractor = getAContractor()
        contractor = contractorService.saveNewContractor(contractor, person)
        Certification.list().each {
            WorkerCertification cc = new WorkerCertification()
            cc.certification = it
            cc.worker = contractor
            contractor.certifications.add(cc)
            auditService.saveWorkerCertification(cc)
        }
        return contractor
    }


    private Contractor getAContractor() {
        Contractor contractor = new Contractor();
        contractor.primeVendor = Vendor.list().get(0)
        contractor.workerNumber = contractor.populateContractorNumber()
        contractor.formOfId = "wpI-Card"
        contractor.badgeNumber = "wpBadgeNo-12345"
        contractor.supervisor = ContractorSupervisor.list(max: 1).get(0)
        return contractor
    }

    private Person getAPersonInfo(String slid) {
        Person person = new Person()
        person.firstName = "wpIntegrationTestFirstName"
        person.middleName = "wpIntegrationTestMiddleName"
        person.lastName = "wpIntegrationTestLastName"
        person.phone = "6234567899"
        person.email = "care.force5@gmail.com"
        person.notes = "Contractor notes"
        person.slid = slid
        return person
    }
}
