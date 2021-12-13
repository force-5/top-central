package com.force5solutions.care.cc

import grails.test.*

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.web.EntitlementRoleDTO

class ContractorServiceTests extends GrailsUnitTestCase {
    static config = ConfigurationHolder.config

    def versioningService
    def contractorService;
    def auditService;
    def careCentralService
    def workerEntitlementRoleService
    Contractor contractor
    Contractor contractorWithCertification
    Contractor contractorWithLocation
    int numberOfContractorCertifications
    int numberOfContractorLocations
    int numberOfContractors
    int numberOfPeople

    protected void setUp() {
        config.bootStrapMode = true
        contractor = getContractorInstance()
        contractorWithCertification = getContractorInstanceWithCertification()
        contractorWithLocation = getContractorInstanceWithEntitlementRole()
        numberOfContractors = Contractor.count()
        numberOfPeople = Person.count()
        numberOfContractorCertifications = WorkerCertification.count()
        numberOfContractorLocations = WorkerEntitlementRole.count()
    }

    protected void tearDown() {
        config.bootStrapMode = false
    }

    void testSaveNewContractor() {
        assertNewContractor(numberOfContractors, numberOfPeople, contractor)
    }

    Contractor getContractorInstance() {
        Person person = getAPersonInfo("slid1")
        Contractor contractor = getAContractor()
        contractor = contractorService.saveNewContractor(contractor, person)
        return contractor
    }


    Contractor getContractorInstanceWithCertification() {
        Person person = getAPersonInfo("slid11")
        person.firstName = "ChangingFirstName"
        Contractor contractor = getAContractor()
        contractor = contractorService.saveNewContractor(contractor, person)
        WorkerCertification cc = new WorkerCertification()
        cc.certification = Certification.list().first()
        cc.worker = contractor
        contractor.certifications.add(cc)
        auditService.saveWorkerCertification(cc)
        return contractor
    }

    CcEntitlementRole createAnEntitlementRole() {
    EntitlementRoleDTO entitlementRoleDTO = new EntitlementRoleDTO()
    entitlementRoleDTO.with {
        id = UUID.randomUUID().toString()
        name = "ER-${System.currentTimeMillis()}"
        status = 'INACTIVE'
        notes = 'Initial Notes'
        standards = 'Standard-1, Standard-2'
        types = 'Physical'
        gatekeepers = "gatekeeper-1"
    }
    careCentralService.createEntitlementRole('admin', 'admin', entitlementRoleDTO)
    CcEntitlementRole entitlementRole = CcEntitlementRole.findByIdAndDeletedFromAps(entitlementRoleDTO.id, false)
    assertNotNull(entitlementRole)
    return entitlementRole
}

    Contractor getContractorInstanceWithEntitlementRole() {
        Person person = getAPersonInfo("slid111")
        person.lastName = "ChangingChangingName"
        Contractor contractor = getAContractor()
        contractor = contractorService.saveNewContractor(contractor, person)
        CcEntitlementRole entitlementRole = createAnEntitlementRole()
        workerEntitlementRoleService.createWorkerEntitlementRole(contractor, entitlementRole.id, "Entitlement Role added by CARE System Tests", 'CARE SYSTEM')
        contractor = contractor.refresh()
        return contractor
    }

    void testUpdateContractorLocation() {
        contractorWithLocation = Contractor.get(contractorWithLocation.id)
        WorkerEntitlementRole cp = contractorWithLocation.entitlementRoles.toList().first()
        List statuses = [EntitlementRoleAccessStatus.active, EntitlementRoleAccessStatus.revoked, EntitlementRoleAccessStatus.pendingRevocation]
        EntitlementRoleAccessStatus status = statuses.get(new Random().nextInt(statuses.size()))
        while (status == cp.status) {
            status = statuses.get(new Random().nextInt(statuses.size()))
        }
        cp.status = status
        auditService.saveWorkerEntitlementRole(cp)
        assertUpdateContractorLocation(numberOfContractors, numberOfPeople, contractorWithLocation)
    }

    void assertUpdateContractorLocation(Integer numberOfContractors, Integer numberOfPeople, Contractor contractor) {
        assertEquals(numberOfContractors, Contractor.count());
        assertEquals(numberOfPeople, Person.count());
        assertEquals(numberOfContractorLocations, WorkerEntitlementRole.count());
        assertEquals(1, contractor.entitlementRoles.size())
        assertEquals(0, contractor.certifications.size())
    }

    void testAddContractorLocation() {
        contractor = Contractor.get(contractor.id)
        CcEntitlementRole entitlementRole = createAnEntitlementRole()
        workerEntitlementRoleService.createWorkerEntitlementRole(contractor, entitlementRole.id, "Entitlement Role added by CARE System Tests", 'CARE SYSTEM')
        contractor = contractor.refresh()
        assertAddContractorLocation(numberOfContractors, numberOfPeople, contractor)
    }

    void assertAddContractorLocation(Integer numberOfContractors, Integer numberOfPeople, Contractor contractor) {
        assertEquals(numberOfContractors, Contractor.count());
        assertEquals(numberOfPeople, Person.count());
        assertEquals(numberOfContractorLocations + 1, WorkerEntitlementRole.count());
        assertEquals(1, contractor.entitlementRoles.size())
        assertEquals(0, contractor.certifications.size())
    }

    void testUpdateContractorCertification() {
        contractorWithCertification = Contractor.get(contractorWithCertification.id)
        WorkerCertification cc = contractorWithCertification.certifications.toList().first()
        cc.dateCompleted = (new Date() - new Random().nextInt(50))
        auditService.saveWorkerCertification(cc)
        assertUpdateContractorCertification(numberOfContractors, numberOfPeople, contractorWithCertification)
    }

    void assertUpdateContractorCertification(Integer numberOfContractors, Integer numberOfPeople, Contractor contractor) {
        assertEquals(numberOfContractors, Contractor.count());
        assertEquals(numberOfPeople, Person.count());
        assertEquals(numberOfContractorCertifications, WorkerCertification.count());
        assertEquals(1, contractor.certifications.size())
        assertEquals(0, contractor.entitlementRoles.size())
    }


    void testAddContractorCertification() {
        contractor = Contractor.get(contractor.id)
        WorkerCertification cc = new WorkerCertification()
        cc.certification = Certification.list().first()
        cc.worker = contractor
        contractor.certifications.add(cc)
        auditService.saveWorkerCertification(cc)
        assertAddContractorCertification(numberOfContractors, numberOfPeople, contractor)
    }

    void assertAddContractorCertification(Integer numberOfContractors, Integer numberOfPeople, Contractor contractor) {
        assertEquals(numberOfContractors, Contractor.count());
        assertEquals(numberOfPeople, Person.count());
        assertEquals(numberOfContractorCertifications + 1, WorkerCertification.count());
        assertEquals(1, contractor.certifications.size())
        assertEquals(0, contractor.entitlementRoles.size())
    }

    void testRemoveContractorCertification() {
        contractorWithCertification = Contractor.get(contractorWithCertification.id)
        WorkerCertification cc = contractorWithCertification.certifications.toList().first()
        contractorWithCertification.certifications = contractorWithCertification.certifications - cc
        auditService.deleteWorkerCertification(cc)
        assertRemoveContractorCertification(numberOfContractors, numberOfPeople, contractorWithCertification)
    }

    void assertRemoveContractorCertification(Integer numberOfContractors, Integer numberOfPeople, Contractor contractor) {
        assertEquals(numberOfContractors, Contractor.count());
        assertEquals(numberOfPeople, Person.count());
        assertEquals(numberOfContractorCertifications - 1, WorkerCertification.count());
        assertEquals(0, contractor.certifications.size())
        assertEquals(0, contractor.entitlementRoles.size())
    }

    void testSaveNewContractor_WITH_NULL_VALUES() {
        Contractor contractor = new Contractor();
        Person person = new Person()
        shouldFail {
            contractorService.saveNewContractor(contractor, person)
        }
    }

    void testSaveNewContractor_WITH_INVALID_EMAIL_ID() {
        Person person = getAPersonInfo("slid11111")
        person.email = "something"
        Contractor contractor = getAContractor()
        shouldFail {
            contractorService.saveNewContractor(contractor, person)
        }
    }


    void testUpdateExistingContractor() {
        Contractor contractor = Contractor.list().get(0)
        contractor.person.lastName = "changed"
    }

    void testUpdateContractorProfile_FIELD_FIRST_NAME() {
        contractor = Contractor.get(contractor.id)
        contractor.person.firstName = "Changed first name"
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    void testUpdateContractorProfile_FIELD_LAST_NAME() {
        contractor = Contractor.get(contractor.id)
        contractor.person.lastName = "Changed last name"
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    void testUpdateContractorProfile_FIELD_MIDDLE_NAME() {
        contractor = Contractor.get(contractor.id)
        contractor.person.middleName = "Changed Middle name"
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    void testUpdateContractorProfile_FIELD_EMAIL() {
        contractor = Contractor.get(contractor.id)
        contractor.person.email = "check@gmail.com"
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    void testUpdateContractorProfile_FIELD_PHONE() {
        contractor = Contractor.get(contractor.id)
        contractor.person.phone = "12345678909"
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    void testUpdateContractorProfile_FIELD_NOTES() {
        contractor = Contractor.get(contractor.id)
        contractor.person.notes = "Changed contractor notes"
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    void testUpdateContractorProfile_FIELD_PRIME_VENDOR() {
        contractor = Contractor.get(contractor.id)
        Vendor vendor = Vendor.list().get(new Random().nextInt(Vendor.count()))
        while (vendor == contractor.primeVendor) {
            vendor = Vendor.list().get(new Random().nextInt(Vendor.count()))
        }
        contractor.primeVendor = vendor
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    void testUpdateContractorProfile_FIELD_PRIME_SUPERVISOR() {
        contractor = Contractor.get(contractor.id)
        List<ContractorSupervisor> supervisors = ContractorSupervisor.list()
        ContractorSupervisor supervisor = supervisors.get(new Random().nextInt(supervisors.size()))
        while (supervisor == contractor.supervisor) {
            supervisor = supervisors.get(new Random().nextInt(supervisors.size()))
        }
        contractor.supervisor = supervisor
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    void testUpdateContractorProfile_FIELD_SUB_VENDOR() {
        contractor = Contractor.get(contractor.id)
        Vendor vendor = Vendor.list().get(new Random().nextInt(Vendor.count()))
        while (vendor == contractor.subVendor) {
            vendor = Vendor.list().get(new Random().nextInt(Vendor.count()))
        }
        contractor.subVendor = vendor
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    void testUpdateContractorProfile_FIELD_SUB_SUPERVISOR() {
        contractor = Contractor.get(contractor.id)
        List<ContractorSupervisor> supervisors = ContractorSupervisor.list()
        ContractorSupervisor supervisor = supervisors.get(new Random().nextInt(supervisors.size()))
        while (supervisor == contractor.subSupervisor) {
            supervisor = supervisors.get(new Random().nextInt(supervisors.size()))
        }
        contractor.subSupervisor = supervisor
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    void testUpdateContractorProfile_FIELD_SLID() {
        Contractor contractor = Contractor.get(contractor.id)
        Person person = contractor.person
        person.slid = "Contractor-Slid111"
        person.s()
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    void testUpdateContractorProfile_FIELD_FORM_OF_ID() {
        contractor = Contractor.get(contractor.id)
        contractor.formOfId = "ContractorFormId11"
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    void testUpdateContractorProfile_FIELD_BADGE_NUMBER() {
        contractor = Contractor.get(contractor.id)
        contractor.badgeNumber = "ContractorBadge11"
        auditService.saveWorker(contractor)
        assertUpdateContractorProfileWithoutBURChange(numberOfContractors, numberOfPeople, contractor)
    }

    private Contractor getAContractor() {
        Contractor contractor = new Contractor();
        contractor.primeVendor = Vendor.list().first()
        contractor.workerNumber = contractor.populateContractorNumber()
        contractor.formOfId = "I-Card"
        contractor.badgeNumber = "badgeNo-12345"
        contractor.supervisor = ContractorSupervisor.list(max:1).get(0)
        BusinessUnitRequester bur = BusinessUnitRequester.list().first()
        contractor.addToBusinessUnitRequesters(bur)
        return contractor
    }

    private Person getAPersonInfo(String slid) {
        Person person = new Person()
        person.firstName = "integrationTestFirstName"
        person.middleName = "integrationTestMiddleName"
        person.lastName = "integrationTestLastName"
        person.phone = "6234567899"
        person.email = "care.force5@gmail.com"
        person.notes = "Contractor notes"
        person.slid = slid
        return person
    }


    void assertNewContractor(Integer numberOfContractors, Integer numberOfPeople, Contractor contractor) {
        assertEquals(numberOfContractors, Contractor.count());
        assertEquals(numberOfPeople, Person.count());
        assertEquals(0, contractor.certifications.size())
        assertEquals(0, contractor.entitlementRoles.size())
        assertEquals(1, contractor.businessUnitRequesters.size())
    }

    void assertUpdateContractorProfile(Integer numberOfContractors, Integer numberOfPeople, Contractor contractor) {
        assertEquals(numberOfContractors, Contractor.count());
        assertEquals(numberOfPeople, Person.count());
        assertEquals(0, contractor.certifications.size())
        assertEquals(0, contractor.entitlementRoles.size())
    }


    void assertUpdateContractorProfileWithBURAdded(Integer numberOfContractors, Integer numberOfPeople, Contractor contractor) {
        assertUpdateContractorProfile(numberOfContractors, numberOfPeople, contractor)
        assertEquals(2, contractor.businessUnitRequesters.size())
    }

    void assertUpdateContractorProfileWithBURRemoved(Integer numberOfContractors, Integer numberOfPeople, Contractor contractor) {
        assertEquals(numberOfContractors, Contractor.count());
        assertEquals(numberOfPeople, Person.count());
        assertEquals(0, contractor.certifications.size())
        assertEquals(0, contractor.entitlementRoles.size())
        assertEquals(0, contractor.businessUnitRequesters.size())
    }

    void assertUpdateContractorProfileWithoutBURChange(Integer numberOfContractors, Integer numberOfPeople, Contractor contractor) {
        assertUpdateContractorProfile(numberOfContractors, numberOfPeople, contractor)
        assertEquals(1, contractor.businessUnitRequesters.size())
    }

}
