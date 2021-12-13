package com.force5solutions.care.cc

import grails.test.GrailsUnitTestCase
import com.force5solutions.care.cc.CcEntitlementRole
import com.force5solutions.care.cc.*

class WorkerCertificationServiceTests extends GrailsUnitTestCase {
    static loggedUser = "Integration Test User"

    Contractor contractor
    Long initialWorkerCertificationCount
    def workerCertificationService
    def workerEntitlementRoleService

    protected void setUp() {
        initialWorkerCertificationCount = WorkerCertification.count()
        contractor = getAContractor();
    }

    protected void tearDown() {
    }

    void testCreateWorkerCertification() {
        Certification certification = Certification.get(1)
        WorkerCertificationCO workerCertificationCO = new WorkerCertificationCO()
        workerCertificationCO.id = contractor.id
        workerCertificationCO.certificationId = certification.id
        workerCertificationCO.dateCompleted_value = new Date().format('MM/dd/yyyy')
        workerCertificationService.saveWorkerCertification(workerCertificationCO)
        Long currentWorkerCertificationCount = WorkerCertification.count()
        assertTrue('Worker Certification not created', currentWorkerCertificationCount == (initialWorkerCertificationCount + 1))
    }

    void testExpireWorkerCertificationWithoutAccess() {
        List<Certification> certifications = Certification.list()
        certifications.each {Certification certification ->
            WorkerCertificationCO workerCertificationCO = new WorkerCertificationCO()
            workerCertificationCO.id = contractor.id
            workerCertificationCO.certificationId = certification.id
            workerCertificationCO.dateCompleted_value = new Date().format('MM/dd/yyyy')
            workerCertificationService.saveWorkerCertification(workerCertificationCO)
        }
        Long currentWorkerCertificationCount = WorkerCertification.count()
        assertTrue('Worker Certification not created', currentWorkerCertificationCount == (initialWorkerCertificationCount + Certification.count()))
        sleep(12000)
        Certification certification = Certification.get(1)
        WorkerCertificationCO workerCertificationCO = new WorkerCertificationCO()
        workerCertificationCO.id = contractor.id
        workerCertificationCO.certificationId = certification.id
        workerCertificationCO.dateCompleted_value = (new Date() - 1000).format('MM/dd/yyyy')
        workerCertificationService.saveWorkerCertification(workerCertificationCO)
        sleep(12000)
    }

    void testExpireWorkerCertificationWithAccess_ACTIVE() {
        Contractor contractor = getAContractorWithLocation()
        List<Certification> certifications = Certification.list()
        Date now = new Date()
        certifications.each {Certification certification ->
            WorkerCertificationCO workerCertificationCO = new WorkerCertificationCO()
            workerCertificationCO.id = contractor.id
            workerCertificationCO.certificationId = certification.id
            workerCertificationCO.dateCompleted_value = (new Date() - 1000).format('MM/dd/yyyy')
            workerCertificationService.saveWorkerCertification(workerCertificationCO)
        }
        Long currentWorkerCertificationCount = WorkerCertification.count()
        assertTrue('Worker Certification not created', currentWorkerCertificationCount > initialWorkerCertificationCount)
        sleep(12000)

    }

    void testExpireWorkerCertificationWithAccess_NOT_ACTIVE() {
        Contractor contractor = getAContractorWithLocation()
        Long workerEntitlementRoleId = WorkerEntitlementRole.findByWorker(contractor)?.id
        workerEntitlementRoleService.changeEntitlementRoleStatus(workerEntitlementRoleId, EntitlementRoleAccessStatus.revoked, loggedUser)
        sleep(5000)
        List<Certification> certifications = Certification.list()
        certifications.each {Certification certification ->
            WorkerCertificationCO workerCertificationCO = new WorkerCertificationCO()
            workerCertificationCO.id = contractor.id
            workerCertificationCO.certificationId = certification.id
            workerCertificationCO.dateCompleted_value = (new Date() - 1000).format('MM/dd/yyyy')
            workerCertificationService.saveWorkerCertification(workerCertificationCO)
        }
        Long currentWorkerCertificationCount = WorkerCertification.count()
        assertTrue('Worker Certification not created', currentWorkerCertificationCount > initialWorkerCertificationCount)
        sleep(12000)
    }


    private Contractor getAContractorWithLocation() {
        CcEntitlementRole entitlementRole = CcEntitlementRole.listUndeleted().first()
        workerEntitlementRoleService.createWorkerEntitlementRole(contractor, entitlementRole.id, "Adding location for testing", loggedUser)
        List<Certification> certifications = Certification.list()
        certifications.each {Certification certification ->
            WorkerCertificationCO workerCertificationCO = new WorkerCertificationCO()
            workerCertificationCO.id = contractor.id
            workerCertificationCO.certificationId = certification.id
            workerCertificationCO.dateCompleted_value = new Date().format('MM/dd/yyyy')
            workerCertificationService.saveWorkerCertification(workerCertificationCO)
        }
        return contractor
    }



    private Contractor getAContractor() {
        Contractor contractor = new Contractor();
        contractor.primeVendor = Vendor.list().get(0)
        contractor.workerNumber = contractor.populateContractorNumber()
        contractor.formOfId = "wcI-Card"
        contractor.badgeNumber = "wcBadgeNo-12345"
        contractor.supervisor = ContractorSupervisor.list(max: 1).get(0)
        contractor.person = getAPersonInfo("wcslidperson-" + new Random().nextInt(200000))
        contractor.s()

        return contractor
    }

    private Person getAPersonInfo(String slid) {
        Person person = new Person()
        person.firstName = "wcIntegrationTestFirstName"
        person.middleName = "wcIntegrationTestMiddleName"
        person.lastName = "wcIntegrationTestLastName"
        person.phone = "623452267899"
        person.email = "care.force5@gmail.com"
        person.notes = "Contractor notes"
        person.slid = slid
        person.s()
        return person
    }

}
