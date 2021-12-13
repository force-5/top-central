package com.force5solutions.care.cc

import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.common.Secured
import com.force5solutions.care.common.SecuredAndSendWorkerToPermission
import com.force5solutions.care.common.SessionUtils
import com.force5solutions.care.ldap.Permission
import com.force5solutions.care.ldap.PermissionLevel
import com.force5solutions.care.ldap.SecurityRole
import grails.converters.JSON
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import static com.force5solutions.care.common.CareConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL

class EmployeeController {

    def employeeUtilService
    def auditService
    def workerService
    def centralUtilService

    @SecuredAndSendWorkerToPermission(value = Permission.READ_EMPLOYEE_PROFILE)
    def show = {
        if (Employee.exists(params.id.toLong())) {
            Employee employee = Employee.get(params.id.toLong())
            EmployeeCO employeeCO = employeeUtilService.createEmployeeCO(employee)
            Map model = createModelForEmployee(employeeCO)
            model.put('employee', employee)
            render(view: 'profileReadOnly', model: model)
        } else {
            redirect(action: 'list')
        }
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def list = {
        String orderBy = params.order ?: 'asc'
        String sort = params.sort ?: 'firstName'
        Integer offset = params.offset ? params.offset.toInteger() : 0
        def maxRes = centralUtilService.updateAndGetDefaultSizeOfListViewInConfig(params)
        String loggedInUserSlid = SessionUtils.session?.loggedUser
        String viewType = (params.viewType && (!params.viewType.equals("null"))) ? params.viewType.toString() : CareConstants.VIEW_TYPE_GLOBAL

        // showAll in the params is sent from system statistics widget on the dashboard to bypass the sticky filter of the employee list.
        if (params['showAll'].toString().equalsIgnoreCase('true')) {
            session?.filterEmployeeCommand = null
        }
        def employeeCommand = session?.filterEmployeeCommand

        List<Employee> employees = []
        if (params?.workersWithExpiringCertifications) {
            employees = Employee.getAll(params.list("workersWithExpiringCertifications"))
        } else {
            if (employeeCommand) {
                employees = employeeUtilService.getEmployeesForListView(loggedInUserSlid, sort, orderBy, employeeCommand, viewType)
                log.debug "Employees: " + employees
                if (employeeCommand?.status) {
                    if (employeeCommand.status in [CareConstants.ACCESS_TYPE_PHYSICAL_ONLY, CareConstants.ACCESS_TYPE_CYBER_ONLY, CareConstants.ACCESS_TYPE_BOTH]) {
                        employees = employees.findAll {it.accessTypeForWorkerBasedOnConfigProperties == employeeCommand.status}
                    } else {
                        WorkerStatus status = WorkerStatus?.getWorkerStatus(employeeCommand?.status) ?: WorkerStatus?.(employeeCommand?.status)
                        employees = employees.findAll {it.status == status}
                    }
                }
                if (employeeCommand?.roleStatus) {
                    EntitlementRoleAccessStatus entitlementRoleAccessStatus = EntitlementRoleAccessStatus.(employeeCommand?.roleStatus)
                    employees = employees.findAll {Employee employee ->
                        employee.entitlementRoles.any {it.status == entitlementRoleAccessStatus}
                    }
                }
            } else {
                List<SecurityRole> roles = SessionUtils.session?.roles ? SecurityRole.findAllByNameInList(SessionUtils.session?.roles) : []
                List permissionLevels = PermissionLevel.findAllByPermissionAndRoleInList(Permission.READ_EMPLOYEE_PROFILE, roles)
                List levels = permissionLevels*.level
                if (levels.any {it == UNRESTRICTED_ACCESS_PERMISSION_LEVEL}) {
                    employees = Employee.list()
                } else {
                    employees = EmployeeSupervisor.getInheritedSubordinates(loggedInUserSlid)
                }
                employees = employees.sort {it."${sort}"}
                if (orderBy != 'asc') { employees = employees.reverse() }
            }
        }

        employees = (centralUtilService.filterWorkersBasedOnInheritanceForLoggedInUserCached(employees as List<Worker>, viewType, CareConstants.WORKER_TYPE_EMPLOYEE)) as List<Employee>
        def employeesTotal = null
        if (!maxRes.toString()?.equalsIgnoreCase('Unlimited')) {
            employeesTotal = employees.size()
            if (employeesTotal) {
                Integer lastIndex = offset + maxRes - 1
                if (lastIndex >= employeesTotal) {
                    lastIndex = employeesTotal - 1
                }
                employees = employees.getAt(offset..lastIndex)
            }
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render(template: 'employeesTable', model: [employees: employees, employeesTotal: employeesTotal, offset: offset, max: maxRes, order: orderBy, sort: sort])
        } else {
            render view: 'list', model: [employees: employees, employeesTotal: employeesTotal, offset: offset, max: maxRes, order: orderBy, sort: sort]
        }
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def listEmployees = {
        if (ConfigurationHolder.config.defaultOrgTreeWorkerListView.toString().equalsIgnoreCase('true')) {
            redirect(action: 'employeeOrgTreeView')
        } else {
            redirect(action: 'list')
        }
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def employeeOrgTreeView = {
        String orderBy = params.order ?: 'asc'
        String sort = params.sort ?: 'firstName'
        String loggedInUserSlid = SessionUtils.session?.loggedUser
        String workerSlid = params.workerSlid ? params.workerSlid.toString() : SessionUtils.session?.loggedUser

        List<Worker> employeeHierarchyList = employeeUtilService.getEmployeeHierarchy(loggedInUserSlid, workerSlid)
        employeeHierarchyList = employeeHierarchyList.unique().flatten().reverse()

        List<Employee> employees = employeeUtilService.getEmployeesForOrgTreeView(workerSlid, sort, orderBy)
        log.info "Employees: " + employees

        if (params?.workersWithExpiringCertifications) {
            employees = Employee.getAll(params.list("workersWithExpiringCertifications"))
        }

        render(view: 'employeeOrgTreeView', model: [employees: employees, employeeHierarchyList: employeeHierarchyList])
    }

    @SecuredAndSendWorkerToPermission(value = Permission.DELETE_EMPLOYEE_PROFILE)
    def delete = {
        def employee = Employee.get(params.id)
        String name = employee.name
        if (employee) {
            try {
                workerService.delete(employee)
                flash.message = "Employee ${name} deleted"
                redirect(action: list)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Employee ${name} could not be deleted"
                redirect(action: show, id: params.id)
            }
        }
        else {
            flash.message = "Employee ${name} not found"
            redirect(action: list)
        }
    }

    @Secured(value = Permission.CREATE_EMPLOYEE_PROFILE)
    def create = {
        EmployeeCO employeeCO = new EmployeeCO()
        render(view: 'create', model: createModelForEmployee(employeeCO))
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def addEmployee = {
        render view: 'addEmployeeFromHrInfo'
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def checkSlid = {
        Person person = employeeUtilService.findPerson(params.slid)
        if (person) {
            render person as JSON
        } else {
            render "{'fail':'true'}"
        }
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def createEmployeeFromHrInfo = {
        String slid = params.slid ?: ""
        Worker worker = employeeUtilService.findOrCreateEmployee(slid)
        if (!worker) {
            flash.message = "Worker with SLID: ${slid} could not be created/updated"
        } else {
            flash.message = "Worker with SLID: ${slid} created/updated"
        }
        redirect(action: 'addEmployee')
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def updateImage = {EmployeeCO employeeCO ->
        Employee employee = Employee.get(params.long('workerId'))
        if (params.fileContent?.getBytes()?.size()) {
            employee.workerImage = new CentralDataFile()
            employee.workerImage.bytes = params.fileContent.getBytes()
            employee.workerImage.fileName = employee.name
            employee.workerImage.s()
            if (employee.s() && auditService.saveWorker(employee)) {
                flash.message = "Employee photo updated successfully"
            }
        }
        redirect(action: 'show', id: employee.id)
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def save = {EmployeeCO employeeCO ->
        if (employeeCO.validate()) {
            Employee employee = employeeUtilService.createEmployee(employeeCO)

            if (employeeUtilService.saveNewEmployee(employee)) {
                flash.message = "Employee Saved successfully"
                redirect(action: 'show', id: employee.id)
                return
            }
        }
        Map model = createModelForEmployee(employeeCO)
        model.editFlag = true
        render(view: 'create', model: model)

    }

    private Map createModelForEmployee(EmployeeCO employeeCO) {
        List supervisors = EmployeeSupervisor.list()
        List remainingBusinessUnitRequesters = BusinessUnitRequester.list()
        List selectedBusinessUnitRequesters = employeeCO?.businessUnitRequesters?.size() > 0 ? BusinessUnitRequester.getAll(employeeCO?.businessUnitRequesters) : []
        List courses = employeeCO?.courses?.size() > 0 ? Course.getAll(employeeCO?.courses) : []
        return [
                worker: employeeCO,
                supervisors: supervisors,
                remainingBusinessUnitRequesters: remainingBusinessUnitRequesters,
                selectedBusinessUnitRequesters: selectedBusinessUnitRequesters,
                courses: courses]
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def filterList = {EmployeeCO employeeCommand ->
        if (params?.byDate) {
            Date fudgedExpiryDate
            try {
                fudgedExpiryDate = Date.parse("EEE MMM dd HH:mm:ss z yyyy", params.byDate as String)
            } catch (Exception e) {
                log.debug "Date parse Format different than usual, trying another one"
                fudgedExpiryDate = Date.parse("yyyy-MM-dd HH:mm:ss.S", params.byDate as String)
            }
            employeeCommand.certificationExpirationByDate = fudgedExpiryDate
        }

        if (params?.byPeriod) {
            String period = params?.byPeriod as String
            Long certificationId = (params.selectedCertification.toString().trim().equalsIgnoreCase('null') || params.selectedCertification.toString().trim().length() == 0) ? null : params.selectedCertification.toString().toLong()
            employeeCommand.expirationForecastPeriod = period
            employeeCommand.certificationId = certificationId
        }

        if (params?.filterByStatus) {
            employeeCommand.status = params?.filterByStatus as String
        }
        session.filterEmployeeCommand = employeeCommand
        redirect(action: 'list', params: [viewType: params.viewType.toString()])
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def filterDialog = {
        def employeeCommand = session.filterEmployeeCommand

        List<EmployeeSupervisor> supervisors = EmployeeSupervisor.list().sort {
            it.toString().toLowerCase()
        }
        List<BusinessUnitRequester> businessUnitRequesters = BusinessUnitRequester.list().sort {
            it.toString().toLowerCase()
        }
        render(template: 'employeeFilter',
                model: [supervisors: supervisors,
                        businessUnitRequesters: businessUnitRequesters,
                        employeeCommand: employeeCommand])
    }

    def showAllEmployee = {
        session.filterEmployeeCommand = null
        redirect(action: 'list')
    }
}
