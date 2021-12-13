package com.force5solutions.care.cc

import com.force5solutions.care.common.SessionUtils
import com.force5solutions.care.feed.HrInfo
import com.force5solutions.care.ldap.SecurityRole
import static com.force5solutions.care.common.CareConstants.*
import com.force5solutions.care.ldap.PermissionLevel
import com.force5solutions.care.ldap.Permission
import org.grails.plugins.versionable.VersioningContext
import com.force5solutions.care.common.CareConstants

public class EmployeeUtilService {

    def auditService
    def dashboardService

    boolean transactional = false

    public EmployeeSupervisor findOrCreateEmployeeSupervisor(String slid, Employee subordinate = null, boolean isNewSubordinate = false) {
        EmployeeSupervisor employeeSupervisor
        Employee employee
        Person person = Person.findBySlid(slid)
        HrInfo hrInfo = HrInfo.findBySlid(slid)
        if (!person && hrInfo) {
            person = populatePersonFromHrInfo(hrInfo)
            person.s()
        }
        if (person) {
            employeeSupervisor = EmployeeSupervisor.findByPerson(person)
            if (!employeeSupervisor) {
                employeeSupervisor = new EmployeeSupervisor()
                employeeSupervisor.person = person
                employeeSupervisor.s()
            }
        }
        if (subordinate) {
            subordinate.supervisor = employeeSupervisor
            if (isNewSubordinate) {
                subordinate.s()
            } else {
                saveNewEmployee(subordinate)
            }
        }
        if (!Employee.findByPerson(person)) {
            createOrUpdateEmployee(hrInfo) // For every supervisor we create, a corresponding employee should be created too
        }
        return (employeeSupervisor?.id ? employeeSupervisor : null)
    }

    public Employee createOrUpdateEmployee(HrInfo hrInfo) {
        VersioningContext.set(UUID.randomUUID().toString())
        Person person = Person.findBySlid(hrInfo.slid)
        if (!person) {
            person = new Person()
        }
        person = populatePersonFromHrInfo(hrInfo, person)
        person.s()

        Employee employee = Employee.findByPerson(person)
        employee = employee ?: new Employee()
        Boolean isNewEmployee = employee.id ? false : true
        employee.person = person
        employee.title = hrInfo.POSITION_TITLE
        employee.department = hrInfo.PERS_AREA_DESC
        employee.workerNumber = hrInfo.pernr
        saveNewEmployee(employee)

        String employeeSupervisorSlid = hrInfo.supervisorSlid ?: hrInfo.SUPVSUPV_SLID_ID
        if (employeeSupervisorSlid && !(hrInfo.slid.equalsIgnoreCase(employeeSupervisorSlid))) {
            findOrCreateEmployeeSupervisor(employeeSupervisorSlid, employee, isNewEmployee)
        } else {
            employee.supervisor = null
            saveNewEmployee(employee)
        }
        return (employee?.id ? employee : null)
    }

    public Employee findOrCreateEmployeeFromHrInfo(HrInfo hrInfo) {
        if (Employee.countBySlid(hrInfo.slid)) {
            return Employee.findBySlid(hrInfo.slid)
        }
        return createOrUpdateEmployee(hrInfo)
    }

    public Person findPerson(String employeeSlid) {
        Person person = Person.findBySlid(employeeSlid)
        if (!person) {
            HrInfo hrInfo = HrInfo.findBySlid(employeeSlid ?: "")
            if (hrInfo) {
                person = populatePersonFromHrInfo(hrInfo)
            }
        }
        return person
    }

    Person populatePersonFromHrInfo(HrInfo hrInfo, Person person = new Person()) {
        if (hrInfo.FIRST_NAME && hrInfo.LAST_NAME) {
            person.firstName = hrInfo.FIRST_NAME
            person.lastName = hrInfo.LAST_NAME
        } else {
            def splitName = hrInfo.FULL_NAME?.split(" ") as List
            if (splitName == null) {
                person.firstName = "Dummy firstName"
                person.lastName = "Dummy lastName"
            } else if (splitName?.size() == 1) {
                person.firstName = splitName.get(0)
                person.lastName = "Dummy lastName"
            } else if (splitName?.size() == 2) {
                person.firstName = splitName.get(0)
                person.lastName = splitName.get(1)
            } else {
                person.firstName = splitName.get(0)
                person.middleName = splitName.get(1)
                person.lastName = splitName.get(2)
            }
        }
        person.phone = hrInfo.CELL_PHONE_NUM
        if (!person.phone) {
            person.phone = hrInfo.OFFICE_PHONE_NUM
        }
        if (!person.phone) {
            person.phone = 'NA'
        }
        person.slid = hrInfo.slid
        person.email = AppUtil.getEmailFromSlid(person.slid)
        return person
    }

    /**
     * Looks up the employee in the CARE database. If it does not find it there, looks in the external database and inserts into CARE database.
     * Returns null if does not find in CARE database and external database.
     */
    public Employee findOrCreateEmployee(String employeeSlid, String workerNumber = '') {
        Employee employee = Employee.list().find {
            ((employeeSlid && (it.person.slid == employeeSlid)) || (workerNumber && (it.workerNumber == workerNumber)))
        }
        if (!employee) {
            HrInfo hrInfo = HrInfo.findBySlidOrWorkerNumber(employeeSlid ?: '', workerNumber ?: '')
            if (hrInfo) {
                employee = createOrUpdateEmployee(hrInfo)
            }
        }
        return employee
    }

    public boolean saveNewEmployee(Employee employee) {
        try {
            if (!employee.person.save()) {
                throw new Exception("Exception while saving the person")
            }
            if (!auditService.saveWorker(employee)) {
                throw new Exception("Exception while saving the Employee")
            }
        } catch (Exception e) {
            e.printStackTrace()
            return false
        }
        return true
    }

    public List<Employee> getEmployeesForListView(String loggedInUserSlid, String sort, String orderBy, EmployeeCO employeeCommand, String viewType) {
        EmployeeSupervisor supervisor = EmployeeSupervisor.findBySlid(loggedInUserSlid)
        boolean isSelfRestriction = false
        boolean isEmployeeSupervisorRestriction = false
        boolean isCareAdmin = false

        if (loggedInUserSlid) {
            List<SecurityRole> roles = SessionUtils.session?.roles ? SecurityRole.findAllByNameInList(SessionUtils.session?.roles) : []
            List permissionLevels = PermissionLevel.findAllByPermissionAndRoleInList(Permission.READ_EMPLOYEE_PROFILE, roles)
            List levels = permissionLevels*.level
            if (!levels.any {it == UNRESTRICTED_ACCESS_PERMISSION_LEVEL}) {
                if (levels.any {(it % ACCESS_IF_SELF_PERMISSION_LEVEL) == 0}) {
                    isSelfRestriction = true
                }
                if (levels.any {(it % ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL) == 0}) {
                    isEmployeeSupervisorRestriction = true
                }
            } else {
                isCareAdmin = true
            }
        }

        def employees = Employee.createCriteria().list {
            and {
                person {
                    if (employeeCommand?.firstName) ilike("firstName", "%" + employeeCommand.firstName + "%")
                    if (employeeCommand?.middleName) ilike("middleName", "%" + employeeCommand.middleName + "%")
                    if (employeeCommand?.lastName) ilike("lastName", "%" + employeeCommand.lastName + "%")
                    if (employeeCommand?.phone) ilike("phone", "%" + employeeCommand.phone + "%")
                    if (employeeCommand?.email) ilike("email", "%" + employeeCommand.email + "%")
                    or {
                        if (employeeCommand?.slid) ilike("slid", "%" + employeeCommand.slid + "%")
                        if (isSelfRestriction) ilike("slid", "%" + loggedInUserSlid + "%")
                    }
                }
                if (employeeCommand?.badgeNumber) ilike("badgeNumber", "%" + employeeCommand.badgeNumber + "%")
                if (employeeCommand?.title) ilike("title", "%" + employeeCommand.title + "%")
                if (employeeCommand?.department) ilike("department", "%" + employeeCommand.department + "%")
                if (employeeCommand?.workerNumber) ilike("workerNumber", "%" + employeeCommand.workerNumber + "%")

                if (!employeeCommand && isEmployeeSupervisorRestriction) {
                    eq("supervisor", supervisor)
                } else {
                    if (employeeCommand?.supervisor) eq("supervisor", EmployeeSupervisor.get(employeeCommand.supervisor))
                }
            }

            if (employeeCommand?.businessUnitRequester) {
                businessUnitRequesters {
                    eq("id", employeeCommand.businessUnitRequester)
                }
            }


            if (sort == "firstName" || sort == "slid") {
                person {
                    order(sort, orderBy)
                }
            } else {
                order(sort, orderBy)
            }

        }

        if (employeeCommand.certificationExpirationByDate) {
            List<Worker> certificationExpirationByDateEmployees = []
            List<Worker> workers = Worker.list()
            List<WorkerCertification> workerCertifications = (workers*.effectiveCertifications)?.flatten()
            workerCertifications = workerCertifications.findAll {it.fudgedExpiry.format('dd-MM-yyyy') == employeeCommand.certificationExpirationByDate.format('dd-MM-yyyy')}
            workerCertifications.each {certification ->
                certificationExpirationByDateEmployees.add(certification.worker as Employee)
            }
            certificationExpirationByDateEmployees.unique()
            employees = employees.findAll {it in certificationExpirationByDateEmployees}
        }

        if (employeeCommand.expirationForecastPeriod) {
            List<Worker> expirationForecastPeriodEmployees = []
            Certification certification = employeeCommand.certificationId ? Certification.get(employeeCommand.certificationId) : null
            ExpirationForecastVO expirationForecastVO = dashboardService.getExpirationForecastsByCertification(new Date(), certification, CareConstants.WORKER_TYPE_EMPLOYEE, viewType)?.find {it.period == employeeCommand.expirationForecastPeriod}
            expirationForecastVO.workerIds?.each {workerId ->
                expirationForecastPeriodEmployees.add(Worker.read(workerId as Long) as Employee)
            }
            expirationForecastPeriodEmployees.unique()
            employees = employees.findAll {it in expirationForecastPeriodEmployees}
        }

        if (!isCareAdmin) {
            List<Employee> subordinates = EmployeeSupervisor.getInheritedSubordinates(loggedInUserSlid)
            employees = employees.findAll {it in subordinates}
        }
        return employees
    }

    public List<Employee> getEmployeesForOrgTreeView(String workerSlid, String sort, String orderBy) {
        EmployeeSupervisor supervisor = EmployeeSupervisor.findBySlid(workerSlid)

        def employees = Employee.createCriteria().list {
            and {
                eq("supervisor", supervisor)
            }
            if (sort == "firstName" || sort == "slid") {
                person {
                    order(sort, orderBy)
                }
            } else {
                order(sort, orderBy)
            }
        }

        return employees
    }

    public List<Worker> getEmployeeHierarchy(String loggedInUserSlid, String workerSlid, List<String> traversedSlids = []) {
        List<Worker> employeeHierarchy = []
        Worker worker = Employee.findBySlid(workerSlid)

        if (worker) {
            if (loggedInUserSlid.equalsIgnoreCase(workerSlid)) {
                employeeHierarchy.add(worker)
                return employeeHierarchy
            }

            if (!(worker in EmployeeSupervisor.getInheritedSubordinates(loggedInUserSlid))) {
                return []
            } else {
                employeeHierarchy.add(worker)
                traversedSlids.add(worker.slid)
                Worker supervisor = Employee.findBySlid(worker.supervisor.slid)
                employeeHierarchy.addAll(getEmployeeHierarchy(loggedInUserSlid, supervisor.slid, traversedSlids))
                return employeeHierarchy
            }
        } else {
            return employeeHierarchy
        }
    }

    public Employee createEmployee(EmployeeCO employeeCO) {
        Employee employee
        if (employeeCO.id) {
            employee = Employee.get(employeeCO.id?.toLong())
        } else {
            employee = new Employee()
        }

        Person person = (employeeCO.slid) ? Person.findBySlid(employeeCO.slid) : null
        employee.person = person ? person : employee.person
        if (!employee.person) {
            employee.person = new Person()
        }


        employeeCO.with {
            employee.person.firstName = firstName
            employee.person.middleName = middleName
            employee.person.lastName = lastName
            employee.person.phone = phone
            employee.person.email = email
            employee.person.slid = (slid?.length() > 0) ? slid : null
            employee.person.notes = notes

            if (fileContent?.size() > 0) {
                if (!employeeCO.workerImageId) {
                    employee.workerImage = new CentralDataFile()
                }
                employee.workerImage.bytes = fileContent
                employee.workerImage.fileName = employee.name
                employee.workerImage.s()
                SessionUtils.session.isEmployeeImageUpdated = true
            }

            employee.title = title
            employee.badgeNumber = badgeNumber
            employee.department = department
            employee.workerNumber = workerNumber
            employee.supervisor = EmployeeSupervisor.get(supervisor.toLong())
            employee.businessUnitRequesters = businessUnitRequesters?.size() > 0 ? BusinessUnitRequester.getAll(businessUnitRequesters) : []
            employee.courses = []
            if (courses) {
                courses.each {def course ->
                    employee.addToCourses(new WorkerCourse(employee, Course.get(course)))
                }
            }
            employee.terminateForCause = terminateForCause ? TerminateForCause.findById(terminateForCause.toLong()) : null
        }
        return employee
    }

    private EmployeeCO createEmployeeCO(Employee employee) {
        EmployeeCO employeeCO = new EmployeeCO()
        employeeCO.with {
            id = employee?.id
            firstName = employee?.person?.firstName
            middleName = employee?.person?.middleName
            lastName = employee?.person?.lastName
            email = employee?.person?.email
            phone = employee?.person?.phone
            notes = employee?.person?.notes
            slid = employee?.person?.slid
            title = employee?.title
            department = employee?.department
            supervisor = employee?.supervisor?.id
            workerNumber = employee?.workerNumber
            badgeNumber = employee?.badgeNumber
            terminateForCause = employee?.terminateForCause?.id
            workerImageId = employee?.workerImage?.id
            businessUnitRequesters = employee?.businessUnitRequesters*.id
            courses = employee?.courses*.course.id
        }
        return employeeCO
    }

}