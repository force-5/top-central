package com.force5solutions.care.cc

import grails.test.GrailsUnitTestCase
import org.grails.plugins.versionable.VersionHistory
import com.force5solutions.care.web.EntitlementRoleDTO

class VersionServiceTests extends GrailsUnitTestCase {

    def versioningService;
    def auditService;
    def workerCertificationService
    def careCentralService
    def workerEntitlementRoleService

    protected void setUp() {
        super.setUp();
    }

    protected void tearDown() {
        super.tearDown();
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
        }
        careCentralService.createEntitlementRole('admin', 'admin', entitlementRoleDTO)
        CcEntitlementRole entitlementRole = CcEntitlementRole.findByIdAndDeletedFromAps(entitlementRoleDTO.id, false)
        assertNotNull(entitlementRole)
        return entitlementRole
    }

//    void testAddWorkerEntitlementRoleHistoryItem() {
//        Employee employee = createAnEmployee();
//        Date date1 = new Date()
//        Integer initialRolesCount = employee.entitlementRoles.size()
//        Integer initialVersionHistoryCount = VersionHistory.count()
//        CcEntitlementRole entitlementRole = createAnEntitlementRole()
//        WorkerEntitlementRole workerEntitlementRole = workerEntitlementRoleService.createWorkerEntitlementRole(employee, entitlementRole.id, "Entitlement Role added by CARE System Tests", 'CARE SYSTEM')
//        employee = employee.refresh()
//        Integer finalRolesCount = employee.entitlementRoles.size()
//        Integer finalVersionHistoryCount = VersionHistory.count()
//        assertEquals("Entitlement Role could not be added to Employee", initialRolesCount + 1, finalRolesCount)
//        assertEquals("Change in Worker Entitlement Role could not be added to Version History", initialVersionHistoryCount + 3, finalVersionHistoryCount)
//        employee = versioningService.getObjectOnDate(employee, date1)
//        assertEquals("Could not retrieve correct Worker Entitlement Roles on a given date", initialRolesCount, employee.entitlementRoles.size())
//    }

    void testAddWorkerCertificationHistoryItem() {
        Employee employee = createAnEmployee();
        employee.person.firstName = System.currentTimeMillis().toString()
        auditService.saveWorker(employee)
        employee = employee.refresh()
        Date date1 = new Date()
        Integer initialCertificationsCount = employee.certifications.size()
        Integer initialVersionHistoryCount = VersionHistory.count()
        Certification certification = Certification.list().last()
        workerCertificationService.saveNewWorkerCertificationWithDateCompleted(employee.id, certification.id, new Date())
        employee = employee.refresh()
        Integer finalCertificationsCount = employee.certifications.size()
        Integer finalVersionHistoryCount = VersionHistory.count()
        assertEquals("Certification could not be added to Employee", initialCertificationsCount + 1, finalCertificationsCount)
        assertEquals("Change in Worker Certifications could not be added to Version History", initialVersionHistoryCount + 1, finalVersionHistoryCount)
        employee = versioningService.getObjectOnDate(employee, date1)
        assertEquals("Could not retrieve correct Worker Certifications on a given date", initialCertificationsCount, employee.certifications.size())
    }

    void testChangeInBadgeNumberCreatesHistoryItem() {
        Employee employee = createAnEmployee();
        int initialNumberOfHistoryItems = VersionHistory.count();
        employee.badgeNumber = "badge01"
        auditService.saveWorker(employee);
        assertEquals("Change in badge did-not create a new history item", 1, VersionHistory.count() - initialNumberOfHistoryItems)
        assertEquals("Number of history items incorrect.", 1, versioningService.getHistoryItems(employee).size())
        VersionHistory versionHistoryItem = VersionHistory.list().last()
        assertHistoryItem(versionHistoryItem, employee.class.name, employee.id.toString(), "badgeNumber", null, "badge01")
    }

    void testChangeInBadgeNumberTwiceCreates2HistoryItems() {
        Employee employee = createAnEmployee();
        int initialNumberOfHistoryItems = VersionHistory.count();
        employee.badgeNumber = "badge01"
        auditService.saveWorker(employee);
        employee.badgeNumber = "badge02"
        auditService.saveWorker(employee);
        assertEquals("Change in badge did-not create a new history item", 2, VersionHistory.count() - initialNumberOfHistoryItems)
        assertEquals("Number of history items incorrect.", 2, versioningService.getHistoryItems(employee).size())
        VersionHistory versionHistoryItem = VersionHistory.list().last()
        assertHistoryItem(versionHistoryItem, employee.class.name, employee.id.toString(), "badgeNumber", "badge01", "badge02")
    }

    void testAddingBURCreatesHistoryItem() {
        Employee employee = createAnEmployee();
        int initialNumberOfHistoryItems = VersionHistory.count();
        BusinessUnitRequester bur = BusinessUnitRequester.list().first();
        employee.businessUnitRequesters = [bur]
        auditService.saveWorker(employee);
        assertEquals("Adding a BUR did-not create a new history item", 1, VersionHistory.count() - initialNumberOfHistoryItems)
        assertEquals("Number of history items incorrect.", 1, versioningService.getHistoryItems(employee).size())
        VersionHistory versionHistoryItem = VersionHistory.list().last()
        assertEquals(0, versionHistoryItem.oldValue.size())
        assertEquals(1, versionHistoryItem.newValue.size())
    }

    void testAddingBURTwiceCreates2HistoryItems() {
        Employee employee = createAnEmployee();
        int initialNumberOfHistoryItems = VersionHistory.count();
        def burs = BusinessUnitRequester.list();
        employee.businessUnitRequesters = [burs.first()]
        auditService.saveWorker(employee);
        employee.businessUnitRequesters = [burs.first(), burs.last()]
        auditService.saveWorker(employee);
        assertEquals("Adding a BUR did-not create a new history item", 2, VersionHistory.count() - initialNumberOfHistoryItems)
        assertEquals("Number of history items incorrect.", 2, versioningService.getHistoryItems(employee).size())
        VersionHistory versionHistoryItem = VersionHistory.list().last()
        assertEquals(1, versionHistoryItem.oldValue.size())
        assertEquals(2, versionHistoryItem.newValue.size())
    }

    void testNoChangeInAnyPropertyDoesNotCreateAnyHistoryItem() {
        Employee employee = createAnEmployee();
        int initialNumberOfHistoryItems = VersionHistory.count();
        employee.badgeNumber = employee.badgeNumber
        employee.person.firstName = employee.person.firstName;
        auditService.saveWorker(employee);
        assertEquals("Number of history items incorrect.", 0, versioningService.getHistoryItems(employee).size())
        assertEquals("History items were created without any change in the object.", 0, VersionHistory.count() - initialNumberOfHistoryItems)
    }

    void testChangeInFirstNameCreatesHistoryItem() {
        Employee employee = createAnEmployee("Scott");
        int initialNumberOfHistoryItems = VersionHistory.count();
        employee.person.firstName = "Jane"
        auditService.saveWorker(employee);
        assertEquals("Change in First Name did-not create a new history item", 1, VersionHistory.count() - initialNumberOfHistoryItems)
        assertEquals("Number of history items incorrect.", 1, versioningService.getHistoryItems(employee).size())
        VersionHistory versionHistoryItem = VersionHistory.list().last()
        assertHistoryItem(versionHistoryItem, Person.class.name, employee.person.id.toString(), "firstName", "Scott", "Jane")
    }

    void testChangeInFirstNameAndBadgeNumberCreates2HistoryItems() {
        Employee employee = createAnEmployee();
        int initialNumberOfHistoryItems = VersionHistory.count();
        employee.person.firstName = "Jane"
        employee.badgeNumber = System.currentTimeMillis();
        auditService.saveWorker(employee);
        assertEquals("Change in First Name & Badge Number did-not create 2 new history item", 2, VersionHistory.count() - initialNumberOfHistoryItems)
    }

    void testGetObjectOnDate() {
        Employee employee = createAnEmployee();
        Date date1 = new Date()
        String timeString1 = date1.time.toString()
        employee.badgeNumber = timeString1
        employee.person.firstName = timeString1
        auditService.saveWorker(employee);
        employee = employee.refresh()
        assertEquals("Badge Number of employee is not updated in database", timeString1, employee.badgeNumber)
        assertEquals("First Name of employee is not updated in database", timeString1, employee.person.firstName)
        sleep(2000)
        Date date2 = new Date()
        String timeString2 = date2.time.toString()
        employee.badgeNumber = timeString2
        employee.person.firstName = timeString2
        auditService.saveWorker(employee);
        employee = employee.refresh()
        assertEquals("Badge Number of employee is not updated in database", timeString2, employee.badgeNumber)
        assertEquals("First Name of employee is not updated in database", timeString2, employee.person.firstName)
        employee = versioningService.getObjectOnDate(employee, date2)
        assertEquals("Could not retrieve Badge Number for given date", timeString1, employee.badgeNumber)
        assertEquals("Could not retrieve First Name for given date", timeString1, employee.person.firstName)
    }

    private Employee createAnEmployee(String firstName = "John") {
        int initialNumberOfEmployees = Employee.count();
        Employee employee = new Employee();
        Person p = new Person();
        p.firstName = firstName
        p.lastName = "Doe"
        p.phone = "9818019870"
        p.slid = "slid01"
        p.save(flush: true, failOnError: true)

        employee.person = p;
        employee.save(flush: true, failOnError: true)
        assertEquals("Could not create an employee", 1, Employee.count() - initialNumberOfEmployees)
        return employee
    }

    private BusinessUnitRequester createABUR(String firstName = "John") {
        BusinessUnitRequester bur = new BusinessUnitRequester()
        Person p = new Person();
        p.firstName = firstName
        p.lastName = "Doe"
        p.phone = "9818019870"
        p.slid = System.currentTimeMillis().toString()
        p.save(flush: true, failOnError: true)

        bur.person = p;
        bur.unit = new BusinessUnit(name: "bu").save()
        bur.save(flush: true, failOnError: true);
        return bur
    }

    private void assertHistoryItem(VersionHistory versionHistoryItem, String className, String objectId, String propertyName, def oldValue, def newValue) {
        assertEquals("Property Name was not correct in history", propertyName, versionHistoryItem.propertyName)
        assertEquals("Object Id incorrect in history", objectId, versionHistoryItem.objectId)
        assertEquals("Class incorrect in history", className, versionHistoryItem.className)
        assertEquals("Old value incorrect in history", oldValue, versionHistoryItem.oldValue)
        assertEquals("New value incorrect in history", newValue, versionHistoryItem.newValue)
        assertNotNull("Effective Date in History item was NULL", versionHistoryItem.effectiveDate)
    }


}
