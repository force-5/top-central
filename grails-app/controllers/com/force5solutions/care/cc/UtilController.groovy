package com.force5solutions.care.cc

import com.force5solutions.care.cp.ConfigProperty
import com.force5solutions.care.web.EntitlementRoleDTO
import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.workflow.CentralWorkflowUtilService
import groovy.sql.Sql
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.quartz.CronTrigger
import org.springframework.web.multipart.commons.CommonsMultipartFile
import util.Database
import util.FeedUtil
import util.MySQLDatabase
import com.force5solutions.care.feed.*
import com.force5solutions.care.common.CareConstants
import jxl.WorkbookSettings
import jxl.Workbook
import jxl.Sheet

class UtilController {

    def sessionFactory
    def workerCourseService
    def feedProcessingService;
    def auditService;
    def utilService
    def workerCertificationService
    def employeeUtilService
    def contractorService
    def feedUtilService
    def feedReportService
    def versioningService
    def careCentralService
    def workerEntitlementRoleService
    def centralWorkflowTaskService
    def fixtureLoader
    def accessVerificationService
    def escalationService
    def centralUtilService
    def jobManagerService
    def quartzScheduler

    static config = ConfigurationHolder.config

    private Map createActiveWorkerEntitlementRoles(List<String> slids, CcEntitlementRole ccEntitlementRole) {
        Map processInfo = [:]
        List<String> employeesNotFound = []
        List<String> workerEntitlementRoleAlreadyExist = []
        List<String> workerEntitlementRoleCreated = []
        slids?.each {
            Employee employee = employeeUtilService.findOrCreateEmployee(it)
            if (employee) {
                if (!WorkerEntitlementRole.countByWorkerAndEntitlementRole(employee, ccEntitlementRole)) {
                    workerEntitlementRoleService.createActiveWorkerEntitlementRoles(employee, ccEntitlementRole)
                    workerEntitlementRoleCreated.add(it)
                } else {
                    workerEntitlementRoleAlreadyExist.add(it)
                }
            } else {
                employeesNotFound.add(it)
            }
        }
        if (workerEntitlementRoleCreated) {
            processInfo['Worker Entitlement Role successfully created for following slids'] = workerEntitlementRoleCreated
        }
        if (employeesNotFound) {
            processInfo['Employees cannot be created for following slids'] = employeesNotFound
        }
        if (workerEntitlementRoleAlreadyExist) {
            processInfo['Worker Entitlement Role already exists for following slids'] = workerEntitlementRoleAlreadyExist
        }
        return processInfo
    }

    def setEntitlementRolesInConfig = {
        List<CcEntitlementRole> roles = CcEntitlementRole.listUndeleted()
        if (roles) {
            ConfigProperty physical = ConfigProperty.findByName('physicalEntitlementRoleId') ?: new ConfigProperty(name: 'physicalEntitlementRoleId')
            physical.value = roles.find { it.name.contains('Physical') && !it.name.contains('Cyber') }?.id
            physical.s()
            ConfigProperty electronic = ConfigProperty.findByName('electronicEntitlementRoleId') ?: new ConfigProperty(name: 'electronicEntitlementRoleId')
            electronic.value = roles.find { it.name.contains('Cyber') && !it.name.contains('Physical') }?.id
            electronic.s()
            ConfigProperty both = ConfigProperty.findByName('bothEntitlementRoleId') ?: new ConfigProperty(name: 'bothEntitlementRoleId')
            both.value = roles.find { it.name.contains('Both') || (it.name.contains('Physical') && it.name.contains('Cyber')) }?.id
            both.s()
            render "<br/>config.physicalEntitlementRoleId : ${config.physicalEntitlementRoleId}"
            render "<br/>config.electronicEntitlementRoleId : ${config.electronicEntitlementRoleId}"
            render "<br/>config.bothEntitlementRoleId : ${config.bothEntitlementRoleId}"
        } else {
            render "Entitlement Roles List Empty"
        }
    }

    def kickOffJobs = {

        List<JobDetailsDTO> jobList = []

        jobManagerService.getAllJobs().each { currentJob ->
            String name = currentJob.name;
            if (name.contains("force5solutions")) {
                List tokenizeName = name.tokenize(".")
                name = tokenizeName.get(tokenizeName.size() - 1)
                String cronEx = "none"
                List triggers = quartzScheduler.getTriggersOfJob(currentJob.name, currentJob.group)
                if (triggers != null && triggers.size() > 0) {
                    cronEx = triggers.get(0).cronExpression
                }
                jobList.add(new JobDetailsDTO(name: name, triggerName: currentJob.triggerName, triggerGroup: currentJob.triggerGroup, status: currentJob.status, cronExpression: cronEx));
            }
        }
        jobList.sort { it.name }
        [jobs: jobList]
    }

    def triggerJob = {
        CronTrigger trigger = quartzScheduler.getTrigger(params.triggerName, params.triggerGroup)
        quartzScheduler.triggerJob(trigger.jobName, trigger.jobGroup)
        redirect(controller: 'util', action: 'kickOffJobs')
    }

    def grantAccessToRole = {
        CommonsMultipartFile file = params.slidsFile
        String fileText = file.inputStream.text
        List<String> slids = fileText.tokenize().findAll { it }
        Map processInfo = [:]
        if (params.entitlementRoleId) {
            CcEntitlementRole ccEntitlementRole = CcEntitlementRole.findByIdAndDeletedFromAps(params.entitlementRoleId, false)
            if (ccEntitlementRole) {
                new HrInfoFeedService().execute()
                processInfo = createActiveWorkerEntitlementRoles(slids, ccEntitlementRole)
                flash.message = 'Processed successfully'
            } else {
                flash.message = 'No Entitlement Role found in Central Database'
            }
        } else {
            flash.message = 'Please select Entitlement Role'
        }
        render(view: 'requestAccessToRole', model: [processInfo: processInfo, entitlementRoles: CcEntitlementRole.listUndeleted().size() ? CcEntitlementRole.listUndeleted() : []])
    }

    def requestAccessToRole = {
        return [entitlementRoles: CcEntitlementRole.listUndeleted().size() ? CcEntitlementRole.listUndeleted() : []]
    }

    def index = {}


    def triggerActiveWorkerFeed = {
        FeedRun feedRun = new ActiveWorkerFeedService().execute()
        render "Feed Executed : <a href='${g.createLink(controller: 'feedRun', action: 'show', id: feedRun.id)}'>View Details</a> "
    }

    def triggerCourseFeed = {
        EmployeeCourseFeedService courseFeedService = new EmployeeCourseFeedService()
        FeedRun feedRun = courseFeedService.execute()
        render "Feed Executed : <a href='${g.createLink(controller: 'feedRun', action: 'show', id: feedRun.id)}'>View Details</a> "
    }

    def triggerPraFeed = {
        PraFeedService praFeedService = new PraFeedService();
        FeedRun feedRun = praFeedService.execute()
        render "Feed Executed : <a href='${g.createLink(controller: 'feedRun', action: 'show', id: feedRun.id)}'>View Details</a> "
    }

    def triggerContractorCourseFeed = {
        ContractorCourseFileFeedService contractorCourseFileFeedService = new ContractorCourseFileFeedService();
        FeedRun feedRun = contractorCourseFileFeedService.execute()
        render "Feed Executed : <a href='${g.createLink(controller: 'feedRun', action: 'show', id: feedRun.id)}'>View Details</a> "
    }

    def triggerContractorPRAFeed = {
        ContractorPraFileFeedService contractorPraFileFeedService = new ContractorPraFileFeedService();
        FeedRun feedRun = contractorPraFileFeedService.execute()
        render "Feed Executed : <a href='${g.createLink(controller: 'feedRun', action: 'show', id: feedRun.id)}'>View Details</a> "
    }

    def triggerEmployeePRAFileFeed = {
        EmployeePraFileFeedService employeePraFileFeedService = new EmployeePraFileFeedService();
        FeedRun feedRun = employeePraFileFeedService.execute()
        render "Feed Executed : <a href='${g.createLink(controller: 'feedRun', action: 'show', id: feedRun.id)}'>View Details</a> "
    }

    def triggerEmployeeCourseFileFeed = {
        EmployeeCourseFileFeedService employeeCourseFileFeedService = new EmployeeCourseFileFeedService();
        FeedRun feedRun = employeeCourseFileFeedService.execute()
        render "Feed Executed : <a href='${g.createLink(controller: 'feedRun', action: 'show', id: feedRun.id)}'>View Details</a> "
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
        return entitlementRole
    }

    def testER = {
        Employee employee = createAnEmployee();
        Date date1 = new Date()
        CcEntitlementRole entitlementRole = createAnEntitlementRole()
        WorkerEntitlementRole workerEntitlementRole = workerEntitlementRoleService.createWorkerEntitlementRole(employee, entitlementRole.id, "Entitlement Role added by Central System Tests", CareConstants.CENTRAL_SYSTEM)
        render "Old Current Node:" + workerEntitlementRole.currentNode
        CentralWorkflowTask task = CentralWorkflowTask.findByWorkerEntitlementRoleId(workerEntitlementRole.id)
        Map responseElements = ['userAction': 'REJECT', 'accessJustification': 'done']
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        workerEntitlementRole = workerEntitlementRole.refresh()
        render "<br/>New Current Node:" + workerEntitlementRole.currentNode
        employee = employee.refresh()
        def historyItems = versioningService.getHistoryItems(employee)
        render "<br/>History Items: " + historyItems*.id

//        employee = employee.refresh()
//        render "Success"
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
        bur.unit = new BusinessUnit(name: System.currentTimeMillis().toString()).save()
        bur.save(flush: true, failOnError: true);
        return bur
    }



    def setUser = {
        if (params.id == 'NONE') {
            session.loggedUser = null
        } else {
            session.loggedUser = params.id
        }
        redirect(uri: '/')
    }

    def uploadExcel = {
        render(view: 'upload')
    }

    def processExcel = {
        CommonsMultipartFile file = params.careFile
        Map results = excelUploadService.createLineItems(file.getInputStream())
        render(view: 'uploadResults', model: [results: results])
    }

    def triggerHrInfoFeed = {
        HrInfoFeedService service = new HrInfoFeedService();
        FeedRun feedRun = service.execute();
        render "Feed Executed : <a href='${g.createLink(controller: 'feedRun', action: 'show', id: feedRun.id)}'>View Details</a> "
    }

    def triggerContractorHrInfoFileFeed = {
        ContractorHrInfoFileFeedService service = new ContractorHrInfoFileFeedService();
        FeedRun feedRun = service.execute();
        render "Feed Executed : <a href='${g.createLink(controller: 'feedRun', action: 'show', id: feedRun.id)}'>View Details</a> "
    }

    def feeds = {}

    def exportData = {
        exportVendors();
        exportContractorSupervisors();
        exportBusinessUnitRequesters();
        exportCertifications()
        exportLocations();
        exportContractors()
        render "success"
    }

    def exportContractors() {
        def sql = fetchSqlConnection();
        sql.eachRow("select * from contractor", {
            Contractor contractor = new Contractor()
            def row = getContact(it.contact_info_id as long)

            contractor.badgeNumber = it.badge_number
            contractor.birthDay = it.birth_day as Integer
            contractor.birthMonth = it.birth_month as Integer
            contractor.formOfId = it.form_of_id
            def contact = getContractorSupervisor(it.supervisor_id as Long)
            Person supervisorPerson = Person.findByfirstNameAndLastName(contact.first_name, contact.last_name)
            ContractorSupervisor supervisor = ContractorSupervisor.findByPerson(supervisorPerson)
            contractor.supervisor = supervisor
            contractor.primeVendor = Vendor.findByCompanyName(getVendorCompanyName(it.vendor_id as Long))
            List<BusinessUnitRequester> businessUnitRequesters = getBusinessUnitRequesters(it.id as Long)
            businessUnitRequesters.each {
                contractor.addToBusinessUnitRequesters(it)
            }
            List<WorkerCertification> workerCertifications = getWorkerCertifications(it.id as Long)
            workerCertifications.each {
                contractor.addToCertifications(it)
            }
            contractorService.saveNewContractor(contractor, getPersonFromContact(row, ((it.slid) ? it.slid : 0) as long))
            log.debug "Saved Contractor ${contractor.name}"

        })

    }

    def getPersonFromContact(def contact, long slid) {

        Person person = Person.findByfirstNameAndLastName(contact.first_name, contact.last_name)
        if (person && (person?.email == contact.email)) {
            return person
        } else {
            person = new Person();
            person.firstName = contact.first_name;
            person.lastName = contact.last_name;
            person.middleName = contact.middle_name;
            person.phone = contact.phone;
            person.notes = contact.notes;
            person.email = contact.email;
            if (slid) {
                person.slid = slid
            }
            person.s()
        }

        return person
    }

    def getWorkerCertifications(long contractorId) {
        List<WorkerCertification> certifications = []
        def sql = fetchSqlConnection();
        WorkerCertification workerCertification
        sql.eachRow("select * from contractor_certification where contractor_id = ${contractorId}", {
            workerCertification = new WorkerCertification()
            workerCertification.dateCompleted = it.date_completed
            workerCertification.certification = Certification.findByName(getCertification(it.certification_id as long))
            certifications.add(workerCertification)
        })
        return certifications
    }

    def getBusinessUnitRequesters(long contractorId) {
        List<BusinessUnitRequester> businessUnitRequesters = []
        def sql = fetchSqlConnection();
        sql.eachRow("select business_unit_requester_id from contractor_business_unit_requester where contractor_business_unit_requesters_id = ${contractorId}", {
            businessUnitRequesters.add(getBusinessUnitRequester(it.business_unit_requester_id as long))
        })
        return businessUnitRequesters

    }

    def getBusinessUnitRequester(long businessUnitRequesterId) {
        def sql = fetchSqlConnection();
        def contact = sql.firstRow("select * from contact where id in(select contact_id from business_unit_requester where id = ${businessUnitRequesterId})")
        Person burPerson = Person.findByfirstNameAndLastName(contact.first_name, contact.last_name)
        BusinessUnitRequester businessUnitRequester = BusinessUnitRequester.findByPerson(burPerson)
        return businessUnitRequester
    }

    def fetchSqlConnection() {
        def sql = Sql.newInstance("jdbc:jtds:sqlserver://192.168.1.95:1433/caredb", "sa", "igdefault", "net.sourceforge.jtds.jdbc.Driver")
        return sql;
    }

    def getContractorSupervisor(long supervisorId) {
        def sql = fetchSqlConnection();
        def row = sql.firstRow("select * from contact where id = ${supervisorId}");
        return row

    }

    def exportCertifications() {
        def sql = fetchSqlConnection();
        sql.eachRow("select * from certification", {
            Certification certification = new Certification()


            certification.name = it.name
            certification.affidavitRequired = it.affidavit_required as boolean
            certification.description = it.description
//            certification.expirationOffset = (it.expiration_offset) ? (it.expiration_offset as long) : 0
            certification.expirationOffset = 0
            certification.period = it.period as long
            certification.periodUnit = PeriodUnit."${it.period_unit}"
            certification.providerRequired = it.provider_required as boolean
            certification.providers = it.providers
//            certification.quarterly = it.quarterly as boolean
            certification.quarterly = false
            certification.subTypeRequired = it.sub_type_required as boolean
            certification.subTypes = it.sub_types
            certification.testProviders = it.test_providers
            certification.testRequired = it.test_required as boolean
            certification.trainingProviders = it.training_providers
            certification.trainingRequired = it.training_required as boolean
            certification.s()
            log.debug "Saving Certification ${certification.name}"
        })
    }

    def exportBusinessUnitRequesters() {
        def sql = fetchSqlConnection();
        sql.eachRow("select * from business_unit_requester", {
            BusinessUnitRequester businessUnitRequester = new BusinessUnitRequester()
            def row = getContact(it.contact_id as long)

            businessUnitRequester.person = getPersonFromContact(row, it.slid as long)
            businessUnitRequester.unit = it.unit
            businessUnitRequester.s()
            log.debug "Saving Business Unit Requester ${businessUnitRequester.name}"
        }
        );
    }



    def getContact(long contactId) {
        def sql = fetchSqlConnection();
        def row = sql.firstRow("select * from contact where id = ${contactId}");
        return row
    }

    def exportVendors() {
        def sql = fetchSqlConnection();
        sql.eachRow("select * from vendor", {
            Vendor vendor = new Vendor();
            vendor.addressLine1 = it.address_line1
            vendor.addressLine2 = it.address_line2
            vendor.city = it.city;
            vendor.state = it.state;
            vendor.phone = it.phone
            vendor.notes = it.notes;
            vendor.zipCode = it.zip_code;
            vendor.agreementExpirationDate = it.agreement_expiration_date;
            vendor.companyName = it.company_name;
            log.debug "Saving vendor ${vendor.companyName}"
            vendor.s();
        }
        );
    }

    def exportContractorSupervisors() {
        def sql = fetchSqlConnection();
        sql.eachRow("select * from contact where class = 'com.force5solutions.care.Supervisor'", {

            ContractorSupervisor contractorSupervisor = new ContractorSupervisor();
            contractorSupervisor.person = getPersonFromContact(it, 0)
            contractorSupervisor.vendor = Vendor.findByCompanyName(getVendorCompanyName(it.vendor_id as long))
            contractorSupervisor.s()
            log.debug "Saving Contractor Supervisor ${contractorSupervisor.name}"
        }
        );
    }

    def exportLocationTypes() {
        def sql = fetchSqlConnection();
        sql.eachRow("select * from location_type order by id", {
            LocationType locationType = new LocationType();
            locationType.type = it.type
            locationType.level = it.level;
            locationType.isEditable = it.is_editable as boolean;
            locationType.isSelectable = it.is_selectable as boolean;
            if (it.parent_id) {
                locationType.parent = LocationType.findByType(getLocationTypeName(it.parent_id as long))
            }
            locationType.s()
            log.debug "Saving Location Type ${locationType.type}"
        }
        );
    }

    def exportLocations() {
        def sql = fetchSqlConnection();
        Location.listOrderById().reverse().each {
            it.parent = null
            it.delete(flush: true)
        }
        sql.eachRow("select * from location order by id", {
            Location location = new Location();
            location.name = it.name;
            location.notes = it.notes;
            if (it.parent_id) {
                location.parent = Location.findByName(getLocationName(it.parent_id as long))
            }
            location.locationType = LocationType.findByType(getLocationTypeName(it.location_type_id as long))
            List<Certification> requiredCertifications = getLocationRequiredCertifications(it.id as long)
            requiredCertifications.each {
                location.addToRequiredCertifications(it)
            }
            location.s()
            log.debug "Saving Location ${location.name}"
        }
        );
    }

    def getLocationRequiredCertifications(long locationId) {
        List<Certification> requiredCertifications = []
        def sql = fetchSqlConnection();
        sql.eachRow("select certification_id from location_certification where location_required_certifications_id = ${locationId}", {
            requiredCertifications.add(Certification.findByName(getCertification(it.certification_id as long)))
        })
        return requiredCertifications
    }

    def getCertification(long certificationId) {
        def sql = fetchSqlConnection();
        def row = sql.firstRow("select name from certification where id = ${certificationId}");
        return row.name
    }

    def getVendorCompanyName(long vendorId) {
        def sql = fetchSqlConnection();
        def row = sql.firstRow("select company_name from vendor where id = ${vendorId}");
        return row.company_name
    }

    def getLocationTypeName(long locationTypeId) {
        def sql = fetchSqlConnection();
        def row = sql.firstRow("select type from location_type where id = ${locationTypeId}");
        return row.type;
    }

    def getLocationName(long locationId) {
        def sql = fetchSqlConnection();
        def row = sql.firstRow("select name from location where id = ${locationId}");
        return row.name;
    }


    def testDatabaseCopy = {
        Database source = new MySQLDatabase();
        source.connect("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/care?user=root&password=igdefault");

        Database target = new MySQLDatabase();
        target.connect("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/careFeed?user=root&password=igdefault");

        FeedUtil.exportDatabase(source, target)

        source.close();
        target.close();
        render "Database copied"
    }

    def testTableTruncate = {
        Database source = new MySQLDatabase();
        source.connect("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/careFeed?user=root&password=igdefault");

        FeedUtil.truncateTable(source, 'application_role_permissions')

        source.close();
        render "Table truncated"
    }

    def testTableCopy = {
        Database source = new MySQLDatabase();
        source.connect("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/care?user=root&password=igdefault");

        Database target = new MySQLDatabase();
        target.connect("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/careFeed?user=root&password=igdefault");

        FeedUtil.copyTable(source, target, "application_role_permissions", "application_role_permissions")

        source.close();
        target.close();
        render "Table copied"
    }

    def addAllCertifications = {
        List<Certification> certifications = Certification.list()
        Long workerId = params.id.toLong()
        certifications.each { Certification certification ->
            workerCertificationService.saveNewWorkerCertificationWithDateCompleted(workerId, certification.id, new Date())
        }
        render "Added all certifications"
    }

    def addCoursesToWorker = {
        List<Worker> workers = Employee.list() + Contractor.list()
        List<Course> courses = Course.list()
        render(view: 'addCoursesToWorker', model: [workers: workers, courses: courses])
    }

    def saveCoursesToWorker = {
        Worker worker = Worker.get(params.long('id'))
        List<Course> courses = Course.getAll(params.list('courses'))
        workerCourseService.addCourses(worker, courses)
        redirect(controller: 'workerCertification', action: 'certification', id: worker.id)
    }

    def getEntitlementsByDate = {
        List<CcEntitlementRole> entitlementRoles = CcEntitlementRole.list()
        render(view: 'entitlementsByDate', model: [entitlementRoles: entitlementRoles])
    }

    def getWorkerInfoByDate = {
        List<Worker> workers = Employee.list() + Contractor.list()
        render(view: 'workerInfoByDate', model: [workers: workers])
    }

    def entitlementAccessReport = {
        render(view: 'entitlementAccessReport', model: [entitlements: CcEntitlement.list()])
    }

    def newIndex = {
        List<Course> courses = [Course.list().first(), Course.list().last()]
        List<Certification> certifications = Certification.list().findAll { certification -> courses.any { it in certification.courses } }
        render "Certifications: " + certifications
    }

    def employeeToEntitlementRoles = {
        params._name = "employeeToEntitlementRoles"
        params._file = "employeeToEntitlementRoles"
        def employeeToEntitlementRolesVOs = []
        Employee.list().each { Employee employee ->
            employeeToEntitlementRolesVOs << new EmployeeToEntitlementRolesVO(employee)
        }

        employeeToEntitlementRolesVOs = employeeToEntitlementRolesVOs.findAll { it.entitlementRoles }

        render(view: 'employeeToEntitlementRoles', model: [employeeToEntitlementRolesVOs: employeeToEntitlementRolesVOs])
    }

    def entitlementRolesToEmployee = {
        params._name = "entitlementRolesToEmployee"
        params._file = "entitlementRolesToEmployee"
        def entitlementRolesToEmployeeVOs = []

        CcEntitlementRole.list().each { CcEntitlementRole ccEntitlementRole ->
            List<Employee> employees = Employee.createCriteria().list {
                createAlias("entitlementRoles", "r")
                eq("r.entitlementRole", ccEntitlementRole)
                eq("r.status", EntitlementRoleAccessStatus.active)
            }
            entitlementRolesToEmployeeVOs << new EntitlementRoleToEmployeesVO(ccEntitlementRole, employees)
        }

        entitlementRolesToEmployeeVOs = entitlementRolesToEmployeeVOs.findAll { it.employeeNames }

        render(view: 'entitlementRolesToEmployee', model: [entitlementRolesToEmployeeVOs: entitlementRolesToEmployeeVOs])
    }

    def testAccessVerification = {
        accessVerificationService.execute()
        render "Access Verification Workflow has been initiated"
    }

    def selectDateTimeForEscalation = {
        render(view: 'selectDateTimeForEscalation')
    }

    def triggerUptoDateTime = {
        String dateString = params.futureDate_value + " ${params.futureDate_hours}:${params.futureDate_minutes}:0"
        Date futureDateTime = Date.parse('MM/dd/yyyy HH:mm:ss', dateString)
        Date currentDateTime = new Date()
        Integer intervalInMinutes = 50
        List<CertificationExpirationNotificationVO> certificationExpirationNotificationVOs = []

        while (currentDateTime < futureDateTime) {
            escalationService.escalateTasks(currentDateTime)
            accessVerificationService.createApsTaskForAccessVerification(currentDateTime)
            centralWorkflowTaskService.cancelCertificationsTimedOutTasks(currentDateTime)
            certificationExpirationNotificationVOs = workerCertificationService.sendCertificationExpirationNotification(currentDateTime)
            workerCertificationService.startCertificationExpirationNotificationWorkflow(certificationExpirationNotificationVOs)
            currentDateTime.minutes = currentDateTime.minutes + intervalInMinutes
        }

        redirect(action: 'selectDateTimeForEscalation')
    }

    def uploadFixture = {
        render(view: 'uploadFixture')
    }

    def executeFixture = {
        CommonsMultipartFile uploadedFile = params.fixtureFile
        File file = new File(AppUtil.getFixturesDirectoryPath() + System.getProperty("file.separator") + uploadedFile.originalFilename)
        file.bytes = uploadedFile.inputStream.bytes

        if (params.executeFixtureCheckBox) {
            fixtureLoader.load uploadedFile.originalFilename - ".groovy"
        }
        render "Fixture Uploaded and Executed"
    }

    def simulateCertificationExpirationNotification = {
        Date currentDateTime = new Date()
        Date simulationDate
        if (params.containsKey('futureDate_value')) {
            String dateString = params.futureDate_value + " 0:0:0"
            simulationDate = Date.parse('MM/dd/yyyy HH:mm:ss', dateString)
        } else {
            simulationDate = currentDateTime
        }
        List<CertificationExpirationNotificationVO> certificationExpirationNotificationVOs = []
        log.debug "Simulating Certification Expiration Notification as on: ${simulationDate}"
        certificationExpirationNotificationVOs = workerCertificationService.sendCertificationExpirationNotification(simulationDate)
        render(view: 'simulateCertificationExpirationNotification', model: [notificationVOs: certificationExpirationNotificationVOs, simulationDate: simulationDate])

    }

    def selectFeedWorkflow = {
        List<CcEntitlementRole> entitlementRoles = CcEntitlementRole.listUndeleted()
        render(view: 'selectFeedWorkflow', model: [entitlementRoles: entitlementRoles])
    }

    def initiateFeedWorkflow = {
        String slid = params['slid'].toString().trim()
        String entitlementRoleId = params['entitlementRole'].toString().trim()
        String workflowRequest = params['workflowRequest'].toString().trim()
        Worker worker = Employee.findBySlid(slid) ?: Contractor.findByWorkerNumber(slid)
        CcEntitlementRole ccEntitlementRole = CcEntitlementRole.findByIdAndDeletedFromAps(entitlementRoleId, false)

        if (!worker) {
            flash.message = "No worker found with a SLID: ${slid}. Please provide the valid SLID."
        } else {
            flash.message = centralUtilService.initiateFeedWorkflowAndReturnStatusMessage(worker, ccEntitlementRole, workflowRequest)
        }
        redirect(action: 'selectFeedWorkflow')
    }

    def anonymizeData = {
        AnonymizeData.initializeAnonymization()
        Person.list().each { Person person ->
            person.slid = person.slid ? AnonymizeData.anonymize(person.slid, 'slid') : null
            person.firstName = person.firstName ? AnonymizeData.anonymize(person.firstName, 'firstName') : null
            person.lastName = person.lastName ? AnonymizeData.anonymize(person.lastName, 'lastName') : null
            person.phone = person.phone ? AnonymizeData.anonymize(person.phone, 'phone') : null
            person.email = person.email ? (AnonymizeData.anonymize(person.email, 'email') + "@fpl.com") : null
            person.s()
        }

        HrInfo.list().each { HrInfo hrInfo ->
            hrInfo.slid = hrInfo.slid ? AnonymizeData.anonymize(hrInfo.slid.toString(), 'slid') : null
            hrInfo.FULL_NAME = hrInfo.FULL_NAME ? AnonymizeData.anonymize(hrInfo.FULL_NAME, 'FULL_NAME') : null
            hrInfo.s()
        }

        CcEntitlement.list().each { CcEntitlement ccEntitlement ->
            ccEntitlement.alias = ccEntitlement.alias ? AnonymizeData.anonymize(ccEntitlement.alias, 'entitlement') : null
            ccEntitlement.s()
        }

        CcEntitlementRole.list().each { CcEntitlementRole ccEntitlementRole ->
            ccEntitlementRole.name = ccEntitlementRole.name ? AnonymizeData.anonymize(ccEntitlementRole.name, 'entitlementRole') : null
            ccEntitlementRole.s()
        }
    }

    def assignRolesToCurrentUsersFromExcelFile = {
        File excelFile = new File(System.getProperty('java.io.tmpdir'), 'Employee_cca_users.xls')
        WorkbookSettings workbookSettings = new WorkbookSettings();
        workbookSettings.setLocale(new Locale("en", "EN"));
        if (excelFile) {
            Workbook workbook = Workbook.getWorkbook(excelFile, workbookSettings)
            Sheet sheet = workbook?.sheets[0]
            (1..sheet.rows - 1)?.each { Integer index ->
                List<String> rowContents = sheet.getRow(index).toList()*.contents
                rowContents = rowContents*.trim()
                assignRolesToCurrentUsers(rowContents)
            }
        } else {
            throw FileNotFoundException
        }
    }

    private void assignRolesToCurrentUsers(List<String> rowContents) {
        String slid = rowContents.get(0)
        List<String> roleNumbers = rowContents.get(8).tokenize(',')*.trim()
        Employee worker = slid ? Employee.findBySlid(slid) : null
        if (worker) {
            revokeOldRoles(worker)
            if (rowContents.get(7).trim().equalsIgnoreCase('Active')) {
                grantNewRoles(worker, roleNumbers)
            }
        }
    }

    private grantNewRoles(Worker worker, List<String> roleNumbers) {
        roleNumbers.each { String roleNumber ->
            List<CcEntitlementRole> ccEntitlementRoleList = CcEntitlementRole.findAllByNameIlikeAndDeletedFromAps("%${roleNumber}%", false)
            ccEntitlementRoleList.each { CcEntitlementRole ccEntitlementRole ->
                List<String> tokens = ccEntitlementRole.name.tokenize('-')*.trim()
                if (tokens.get(tokens.size() - 1).toString().toInteger() == roleNumber.toInteger()) {
                    String status = centralUtilService.initiateFeedWorkflowAndReturnStatusMessage(worker, ccEntitlementRole, 'Grant')
                    log.debug status
                }
            }
        }
    }

    private String revokeOldRoles(Worker worker) {
        List<CcEntitlementRole> activeCcEntitlementRoleList = worker?.activeEntitlementRoles*.entitlementRole
        activeCcEntitlementRoleList.each { CcEntitlementRole ccEntitlementRole ->
            String status = centralUtilService.initiateFeedWorkflowAndReturnStatusMessage(worker, ccEntitlementRole, 'Revoke')
            log.debug(status)
        }
    }

    def loadContractorsAndAssignRolesFromCsvFile = {
        File excelFile = new File(System.getProperty('java.io.tmpdir'), 'Contractor_CCA_Users.xls')
        WorkbookSettings workbookSettings = new WorkbookSettings();
        workbookSettings.setLocale(new Locale("en", "EN"));
        boolean createContractors = true
        if (excelFile) {
            Workbook workbook = Workbook.getWorkbook(excelFile, workbookSettings)
            Sheet sheet = workbook?.sheets[0]
            (1..sheet.rows - 1)?.each { Integer index ->
                List<String> rowContents = sheet.getRow(index).toList()*.contents
                rowContents = rowContents*.trim()
                if (createContractors) {
                    if (rowContents.get(0) && rowContents.get(6).trim().equalsIgnoreCase('Yes')) {
                        createContractorsAndAssignRoles(rowContents)
                    }
                } else if (rowContents.get(0) && rowContents.get(6).trim().equalsIgnoreCase('Yes')) {
                    loadContractorsAndAssignRoles(rowContents)
                }
            }
        }
    }

    private void loadContractorsAndAssignRoles(List<String> rowContents) {
        String roleNumber = rowContents.get(0).trim()
        String firstName, lastName
        lastName = rowContents.get(1).tokenize(',').first()
        firstName = rowContents.get(1).tokenize(',').last()
        if (firstName.tokenize(' ').size() > 1) {
            firstName = firstName.tokenize(' ').first()
        }
        Person person = (lastName && firstName) ? Person.findByFirstNameAndLastName(firstName, lastName) : null
        Contractor worker = person ? Contractor.findByPerson(person) : null
        if (worker) {
            revokeOldRoles(worker)
            grantNewRoles(worker, [roleNumber])
        }
    }

    private Contractor createContractorsAndAssignRoles(List<String> rowContents) {
        String roleNumber = rowContents.get(0).trim()
        String firstName, lastName, middleName = null
        lastName = rowContents.get(1).tokenize(',').first()
        firstName = rowContents.get(1).tokenize(',').last()
        if (firstName.tokenize(' ').size() > 1) {
            firstName = firstName.tokenize(' ').first()
            middleName = firstName.tokenize(' ').last()
        }
        Contractor contractor = Contractor.createCriteria().get {
            eq('badgeNumber', rowContents.get(2).trim())
            'person' {
                eq('firstName', firstName)
                eq('lastName', lastName)
            }
        }
        if (!contractor) {
            contractor = new Contractor()
        }
        Person person = contractor.person ?: new Person()
        person.firstName = firstName
        person.middleName = middleName ?: ""
        person.lastName = lastName
        person.email = rowContents.get(11).trim() ?: ""
        person.s()
        contractor.person = person
        contractor.badgeNumber = rowContents.get(2).trim()
        contractor.businessUnitRequesters = [BusinessUnitRequester.list().first()]
//        contractor.businessUnitRequesters = [BusinessUnitRequester.findBySlid(rowContents.get(4).trim())]
        auditService.saveWorker(contractor)
        if (contractor.id) {
            grantNewRoles(contractor, [roleNumber])
        }
        return contractor
    }

    def removeLeadingSpacesFromFirstNamesInPerson = {
        Person.list().each { Person person ->
            person.firstName = person.firstName.trim()
            person.s()
        }
        render "Removed leading spaces."
    }
}
