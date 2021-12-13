package com.force5solutions.care.cc

import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.common.Secured
import com.force5solutions.care.feed.HrInfo
import com.force5solutions.care.ldap.Permission
import com.force5solutions.care.report.AccessReportEntitlementRoleVO
import com.force5solutions.care.report.ComplianceSummaryReportVO

@Secured(value = Permission.CAN_ACCESS_REPORTS_MENU)
class ReportController {

    def index = {
        render(view: 'index', model:
        [contractors: Contractor.list(), employees: Employee.list(), supervisors: EmployeeSupervisor.list(), businessUnitRequesters: BusinessUnitRequester.list(),
                vendors: Vendor.list(), entitlementRoles: CcEntitlementRole.list()])
    }
    def cipAccessReport = {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024 * 1024)
        Writer writer = new BufferedWriter(new PrintWriter(baos));
        writer.println 'SLID,Last Name,First Name,Role Name,Status'
        Worker.list().each { Worker worker ->
            worker.entitlementRoles.each { WorkerEntitlementRole workerRole ->
                if (workerRole.status in [EntitlementRoleAccessStatus.ACTIVE, EntitlementRoleAccessStatus.PENDING_ACCESS_APPROVAL, EntitlementRoleAccessStatus.PENDING_ACCESS_REVOCATION, EntitlementRoleAccessStatus.PENDING_PROVISIONER_DEPROVISIONER_TASKS_ON_ROLE_UPDATE] && workerRole.entitlementRole.types.contains('CIP')) {
                    writer.println "\"${worker.slid}\",\"${worker.lastName}\",\"${worker.firstName}\",\"${workerRole.entitlementRole.name}\",\"${workerRole.status}\""
                }
            }
        }
        writer.flush()
        writer.close()

        response.setContentLength(baos.size())
        response.setHeader("Content-disposition", "attachment; filename=" + 'CIP_Access_Report.csv')
        response.setContentType('text/plain')
        OutputStream out = response.getOutputStream()
        baos.writeTo(out)
        out.flush()
        out.close()
    }
    def reportByVendor = {
        List<ContractorByVendorVO> contractorByVendorVOs = []
        if (params.vendorRadio?.equals("selectedVendor")) {
            Vendor vendor = Vendor.get(params.vendorSelect.toLong())
            contractorByVendorVOs << new ContractorByVendorVO(vendor)
        } else {
            def vendors = Vendor.list()
            vendors.each {Vendor vendor ->
                contractorByVendorVOs << new ContractorByVendorVO(vendor)
            }
        }

        params._name = "contractorByVendor"
        params._file = "contractorByVendor"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"

        chain(controller: 'jasper', action: 'index', model: [data: contractorByVendorVOs], params: params)
    }

    def reportByBur = {
        params._name = "contractorByBur"
        params._file = "contractorByBur"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"
        def contractorByBurVOs = []
        if (params.burRadio?.equals("selectedBur")) {
            BusinessUnitRequester businessUnitRequester = BusinessUnitRequester.get(params.burSelect.toLong())
            contractorByBurVOs << new ContractorByBurVO(businessUnitRequester)
        } else {
            BusinessUnitRequester.createCriteria().list {
                createAlias('person', 'p')
                order('p.lastName')
                order('p.firstName')
            }.each {BusinessUnitRequester businessUnitRequester ->
                contractorByBurVOs << new ContractorByBurVO(businessUnitRequester)
            }
        }

        chain(controller: 'jasper', action: 'index', model: [data: contractorByBurVOs], params: params)
    }

    def reportByContractor = {
        params._name = "contractorDetail"
        params._file = "contractorDetail"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"
        def contractorVOs = []
        if (params.contractorRadio?.equals("selectedContractor")) {
            Contractor contractor = Contractor.get(params.contractorSelect.toLong())
            contractorVOs << new ContractorVO(contractor)
        } else {
            Contractor.createCriteria().list {
                createAlias('person', 'p')
                order('p.lastName')
                order('p.firstName')
            }.each {Contractor contractor ->
                contractorVOs << new ContractorVO(contractor)
            }
        }

        chain(controller: 'jasper', action: 'index', model: [data: contractorVOs], params: params)
    }

    def reportByEmployee = {
        params._name = "employeeDetail"
        params._file = "employeeDetail"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"
        def employeeVOs = []
        if (params.employeeRadio?.equals("selectedEmployee")) {
            Employee employee = Employee.get(params.employeeSelect.toLong())
            employeeVOs << new EmployeeVO(employee)
        } else {
            Employee.createCriteria().list {
                createAlias('person', 'p')
                order('p.lastName')
                order('p.firstName')
            }.each {Employee employee ->
                employeeVOs << new EmployeeVO(employee)
            }
        }

        chain(controller: 'jasper', action: 'index', model: [data: employeeVOs], params: params)
    }

    def entitlementRoleGroupReport = {
        params._name = "entitlementRolesBoth"
        params._file = "entitlementRolesBoth"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"
        List<GroupVO> entitlementRoles = []

        CcEntitlementRole.list().each {
            entitlementRoles << new GroupVO(it)
        }

        entitlementRoles = entitlementRoles.sort {it.name.toLowerCase()}

        List<GroupGroupVO> entitlementRoleVOs = []
        entitlementRoleVOs << new GroupGroupVO(entitlementRoleVOs: entitlementRoles)
        chain(controller: 'jasper', action: 'index', model: [data: entitlementRoleVOs], params: params)
    }

    def entitlementRoleAccessReport = {
        params._name = "entitlementRoleAccess"
        params._file = "entitlementRoleAccess"
        params.SUBREPORT_NAME = "entitlementRoleAccessContractor"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"
        List<AccessReportEntitlementRoleVO> accessReportGroupVOs
        Date fromDate
        Date toDate

        if (params.dateRange.equals("dateRange")) {
            fromDate = new Date(params.fromDate_value)
            use(org.codehaus.groovy.runtime.TimeCategory) {
                toDate = (new Date(params.toDate_value) + 1) - 1.seconds
            }
        }
        if (params.accessLocations?.equals("specificGroup")) {
            CcEntitlementRole entitlementRole = CcEntitlementRole.findById(params.entitlementRoleAccessSelect)
            accessReportGroupVOs = []
        }
        else {
            accessReportGroupVOs = []
        }

        if (toDate && fromDate) {
            accessReportGroupVOs = []
        }

        chain(controller: 'jasper', action: 'index', model: [data: accessReportGroupVOs], params: params)
    }

    def complianceSummaryReport = {
        params._name = "complianceReport"
        params._file = "complianceReport"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"

        List<ComplianceSummaryReportVO> summaryReportVOs

        if (params.complianceRadio?.equals("selectedContractor")) {
            Contractor contractor = Contractor.get(params.contractorSelect.toLong())
            summaryReportVOs = []
        } else {
            summaryReportVOs = []
        }

        chain(controller: 'jasper', action: 'index', model: [data: summaryReportVOs], params: params)
    }
    def employeeByBur = {
        params._name = "employeeByBur"
        params._file = "contractorByBur"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"
        def employeeByBurVOs = []
        if (params.burRadio?.equals("selectedBur")) {
            BusinessUnitRequester businessUnitRequester = BusinessUnitRequester.get(params.burSelect.toLong())
            employeeByBurVOs << new EmployeeByBurVO(businessUnitRequester)
        } else {
            BusinessUnitRequester.list().each {BusinessUnitRequester businessUnitRequester ->
                employeeByBurVOs << new EmployeeByBurVO(businessUnitRequester)
            }
        }

        chain(controller: 'jasper', action: 'index', model: [data: employeeByBurVOs], params: params)

    }

    def employeeBySupervisor = {
        params._name = "employeeBySupervisor"
        params._file = "employeeBySupervisor"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"
        def employeeBySupervisorVOs = []
        if (params.supervisorRadio?.equals("selectedSupervisor")) {
            EmployeeSupervisor employeeSupervisor = EmployeeSupervisor.get(params.supervisorSelect.toLong())
            employeeBySupervisorVOs << new EmployeeBySupervisorVO(employeeSupervisor)
        } else {
            EmployeeSupervisor.createCriteria().list {
                createAlias('person', 'p')
                order('p.lastName')
                order('p.firstName')
            }.each {EmployeeSupervisor employeeSupervisor ->
                employeeBySupervisorVOs << new EmployeeBySupervisorVO(employeeSupervisor)
            }
        }

        chain(controller: 'jasper', action: 'index', model: [data: employeeBySupervisorVOs], params: params)

    }

    def employeeToEntitlementRoles = {
        params._name = "employeeToEntitlementRoles"
        params._file = "employeeToEntitlementRoles"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"
        def employeeToEntitlementRolesVOs = []
        Employee.createCriteria().list {
            createAlias('person', 'p')
            order('p.lastName')
            order('p.firstName')
        }.each {Employee employee ->
            employeeToEntitlementRolesVOs << new EmployeeToEntitlementRolesVO(employee)
        }

        employeeToEntitlementRolesVOs = employeeToEntitlementRolesVOs.findAll { it.entitlementRoles }

        chain(controller: 'jasper', action: 'index', model: [data: employeeToEntitlementRolesVOs], params: params)
    }

    def entitlementRoleToEmployees = {
        params._name = "entitlementRolesToEmployee"
        params._file = "entitlementRolesToEmployee"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"
        def entitlementRolesToEmployeeVOs = []

        CcEntitlementRole.list().each { CcEntitlementRole ccEntitlementRole ->
            List<Employee> employees = Employee.createCriteria().list {
                createAlias("entitlementRoles", "r")
                createAlias('person', 'p')
                eq("r.entitlementRole", ccEntitlementRole)
                eq("r.status", EntitlementRoleAccessStatus.active)
                order('p.lastName')
                order('p.firstName')
            }
            entitlementRolesToEmployeeVOs << new EntitlementRoleToEmployeesVO(ccEntitlementRole, employees)
        }

        entitlementRolesToEmployeeVOs = entitlementRolesToEmployeeVOs.findAll { it.employeeNames }

        chain(controller: 'jasper', action: 'index', model: [data: entitlementRolesToEmployeeVOs], params: params)
    }

    def currentFPLEmployeeCCAUserPopulation = {
        params._name = "currentFPLEmployeeCCAUserPopulation"
        params._file = "currentFPLEmployeeCCAUserPopulation"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"

        List<EmployeeAccessReportVO> employeeAccessReportVOs = []

        Employee.list().each { Employee employee ->
            HrInfo hrInfo = HrInfo.findBySlid(employee.person.slid)
            if (hrInfo && employee.activeEntitlementRoles.size() > 0) {
                employeeAccessReportVOs << new EmployeeAccessReportVO(hrInfo, employee.accessTypeForWorkerBasedOnConfigProperties, employee.praStatusForEmployee)
            }
        }
        chain(controller: 'jasper', action: 'index', model: [data: employeeAccessReportVOs], params: params)
    }

    def contractorToEntitlementRoles = {
        params._name = "contractorToEntitlementRoles"
        params._file = "contractorToEntitlementRoles"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"
        def contractorToEntitlementRolesVOs = []

        Contractor.createCriteria().list {
            createAlias('person', 'p')
            order('p.lastName')
            order('p.firstName')
        }.each {Contractor contractor ->
            contractorToEntitlementRolesVOs << new ContractorToEntitlementRolesVO(contractor)
        }

        contractorToEntitlementRolesVOs = contractorToEntitlementRolesVOs.findAll { it.entitlementRoles }

        chain(controller: 'jasper', action: 'index', model: [data: contractorToEntitlementRolesVOs], params: params)
    }

    def entitlementRoleToContractors = {
        params._name = "entitlementRolesToContractor"
        params._file = "entitlementRolesToContractor"
        params.FOOTER_IMAGE_FILE = "reportFooter.png"
        def entitlementRolesToContractorVOs = []

        CcEntitlementRole.list().each { CcEntitlementRole ccEntitlementRole ->
            List<Contractor> contractors = Contractor.createCriteria().list {
                createAlias("entitlementRoles", "r")
                createAlias('person', 'p')
                eq("r.entitlementRole", ccEntitlementRole)
                eq("r.status", EntitlementRoleAccessStatus.active)
                order('p.lastName')
                order('p.firstName')
            }
            entitlementRolesToContractorVOs << new EntitlementRoleToContractorsVO(ccEntitlementRole, contractors)
        }

        entitlementRolesToContractorVOs = entitlementRolesToContractorVOs.findAll { it.contractorNames }

        chain(controller: 'jasper', action: 'index', model: [data: entitlementRolesToContractorVOs], params: params)
    }

}

class ContractorVO {
    String module
    String name
    String completeName

    String firstName
    String lastName
    String middleName
    String slid
    Integer birthDay
    Integer birthMonth
    String birthText
    String email
    String phone
    String vendorName
    String badgeNumber
    String workerNumber
    String formOfId
    String bur
    String supervisor
    String notes
    String accessStatus
    String lastStatusChange
    Person person
    List<CertificationVO> certificationVOs = []
    List<ContractorGroupVO> contractorGroupVOs = []
    List<BusinessUnitRequesterVO> businessUnitRequesterVOs = []

    ContractorVO() {

    }

    ContractorVO(Contractor contractor) {
        module = "Contractor"

        name = contractor.name
        completeName = contractor.toString()

        firstName = contractor.person.firstName
        middleName = contractor.person.middleName
        lastName = contractor.person.lastName
        phone = contractor.person.phone
        slid = contractor.slid
        birthDay = contractor.birthDay
        birthMonth = contractor.birthMonth
        if (birthMonth && birthDay) {
            birthText = ['', 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][birthMonth ?: 0] + ", ${birthDay}"
        }
        email = contractor.person.email
        vendorName = contractor?.primeVendor?.companyName
        supervisor = contractor?.supervisor?.toString()
        badgeNumber = contractor.badgeNumber
        workerNumber = contractor.workerNumber
        formOfId = contractor.formOfId
        notes = contractor.person.notes
        bur = contractor.businessUnitRequesters*.toString()[0]
        accessStatus = contractor.entitlementRoles.any {it.status == EntitlementRoleAccessStatus.active} ? "Active" : "Inactive"

        Date dt = contractor.entitlementRoles.max {it.lastStatusChange}?.lastStatusChange
        lastStatusChange = dt ? dt.myFormat() : ''

        person = contractor.person
        contractor.effectiveCertifications.each {
            certificationVOs << new CertificationVO(it)
        }

        contractor.entitlementRoles.each {

            contractorGroupVOs << new ContractorGroupVO(it)
        }

        contractor.businessUnitRequesters.each {

            businessUnitRequesterVOs << new BusinessUnitRequesterVO(it)
        }
    }

}

class CertificationVO {
    String name
    String description
    String periodUnit
    Integer period
    String dateCompleted
    String expiryDate
    String status

    CertificationVO() {

    }

    CertificationVO(WorkerCertification cc) {
        name = cc.certification.name
        description = cc.certification.description
        period = cc.certification?.period
        periodUnit = cc.certification?.periodUnit?.name()

        dateCompleted = cc.dateCompleted ? cc.dateCompleted.myFormat() : ''
        expiryDate = cc.fudgedExpiry ? cc.fudgedExpiry.myFormat() : ''

        status = cc.currentStatus
    }
}

class ContractorByVendorVO {

    String vendorName
    List<ContractorVO> contractorVOs = []

    ContractorByVendorVO() {

    }

    ContractorByVendorVO(Vendor vendor) {
        vendorName = vendor.companyName
        Contractor.findAllByPrimeVendor(vendor).each {
            contractorVOs << new ContractorVO(it)
        }
    }
}
class SupervisorVO {

    String supervisorName
    List<ContractorVO> contractorVOs = []

    SupervisorVO() {

    }

    SupervisorVO(ContractorSupervisor supervisor) {
        supervisorName = supervisor.toString()
        Contractor.findAllByPrimeVendorAndSupervisor(supervisor.vendor, supervisor).each {
            contractorVOs << new ContractorVO(it)
        }
    }
}

class ContractorByBurVO {

    String firstName
    String lastName
    String module
    List<ContractorVO> contractorVOs = []

    ContractorByBurVO() {

    }

    ContractorByBurVO(BusinessUnitRequester businessUnitRequester) {
        module = "Contractor"
        firstName = businessUnitRequester.person.firstName
        lastName = businessUnitRequester.person.lastName
        def contractorList = Contractor.list().findAll {
            it.businessUnitRequesters.contains(businessUnitRequester)
        }
        contractorList.each {
            contractorVOs << new ContractorVO(it)
        }


    }


}
class ContractorGroupVO {

    String accessStatus
    String entitlementRole
    String lastStatusChange

    ContractorGroupVO() {

    }

    ContractorGroupVO(WorkerEntitlementRole contractorGroup) {

        accessStatus = contractorGroup.status.toString()
        entitlementRole = contractorGroup.entitlementRole.name
        lastStatusChange = contractorGroup.lastStatusChange ? contractorGroup.lastStatusChange.myFormat() : ''
    }

}

class GroupVO {
    String name
    String gatekeepers

    GroupVO() {

    }

    GroupVO(CcEntitlementRole entitlementRole) {
        name = entitlementRole.name
        gatekeepers = entitlementRole.gatekeepers
    }
}

class GateKeepersVO {
    String name
    String gatekeepers

    GateKeepersVO() {}
}

class GroupGroupVO {
    List<GroupVO> entitlementRoleVOs = []
}

class EmployeeVO {
    String module
    String name
    String firstName
    String lastName
    String middleName
    String slid
    String email
    String phone
    String title
    String department
    String badgeNumber
    String workerNumber
    String bur
    String supervisor
    String notes
    String accessType
    String accessStatus
    String vendorName
    String lastStatusChange
    List<CertificationVO> certificationVOs = []
    List<ContractorGroupVO> contractorGroupVOs = []
    List<BusinessUnitRequesterVO> businessUnitRequesterVOs = []

    EmployeeVO() {

    }

    EmployeeVO(Employee employee) {
        module = "Employee"
        name = employee.name
        firstName = employee.person.firstName
        middleName = employee.person.middleName
        lastName = employee.person.lastName
        phone = employee.person.phone
        slid = employee.slid
        email = employee.person.email
        title = employee.title
        department = employee.department
        supervisor = employee?.supervisor?.name
        badgeNumber = employee.badgeNumber
        workerNumber = employee.workerNumber
        notes = employee.person.notes
        bur = employee.businessUnitRequesters*.toString()[0]
        accessType = employee.accessTypeForWorkerBasedOnConfigProperties
        accessStatus = employee.entitlementRoles.any {it.status == EntitlementRoleAccessStatus.active} ? "Active" : "Inactive"

        Date dt = employee.entitlementRoles.max {it.lastStatusChange}?.lastStatusChange
        lastStatusChange = dt ? dt.myFormat() : ''

        employee.effectiveCertifications.each {
            certificationVOs << new CertificationVO(it)
        }

        employee.entitlementRoles.each {
            contractorGroupVOs << new ContractorGroupVO(it)
        }

        employee.businessUnitRequesters.each {

            businessUnitRequesterVOs << new BusinessUnitRequesterVO(it)
        }
    }

}

class EmployeeByBurVO {
    String firstName
    String lastName
    String module
    List<EmployeeVO> contractorVOs = []

    EmployeeByBurVO() {

    }

    EmployeeByBurVO(BusinessUnitRequester businessUnitRequester) {
        module = "Employee"
        firstName = businessUnitRequester.person.firstName
        lastName = businessUnitRequester.person.lastName
        def employeeList = Employee.list().findAll {
            it.businessUnitRequesters.contains(businessUnitRequester)
        }
        employeeList.each {
            contractorVOs << new EmployeeVO(it)
        }

    }

}

class EmployeeBySupervisorVO {

    String supervisorName
    List<EmployeeVO> employeeVOs = []

    EmployeeBySupervisorVO() {

    }

    EmployeeBySupervisorVO(EmployeeSupervisor employeeSupervisor) {
        supervisorName = employeeSupervisor.name
        Employee.findAllBySupervisor(employeeSupervisor).each {
            employeeVOs << new EmployeeVO(it)
        }
    }
}

class EmployeeToEntitlementRolesVO {
    String employeeName
    List<EntitlementRoleNameVO> entitlementRoles = []

    EmployeeToEntitlementRolesVO(Employee employee) {
        employeeName = employee.name
        employee.activeEntitlementRoles.each { WorkerEntitlementRole workerEntitlementRole ->
            entitlementRoles << new EntitlementRoleNameVO(workerEntitlementRole.entitlementRole.name)
        }

    }
}

class ContractorToEntitlementRolesVO {
    String contractorName
    List<EntitlementRoleNameVO> entitlementRoles = []

    ContractorToEntitlementRolesVO(Contractor contractor) {
        contractorName = contractor.name
        contractor.activeEntitlementRoles.each { WorkerEntitlementRole workerEntitlementRole ->
            entitlementRoles << new EntitlementRoleNameVO(workerEntitlementRole.entitlementRole.name)
        }

    }
}

class EntitlementRoleToEmployeesVO {
    String entitlementRole
    List<WorkerNameVO> employeeNames = []

    EntitlementRoleToEmployeesVO(CcEntitlementRole ccEntitlementRole, List<Employee> employees) {
        entitlementRole = ccEntitlementRole.name
        employees.each { Employee employee ->
            employeeNames << new WorkerNameVO("${employee.person.lastName} ${employee.person.firstName} ${employee.person.middleName ?: ''}")
        }
    }
}

class EntitlementRoleToContractorsVO {
    String entitlementRole
    List<WorkerNameVO> contractorNames = []

    EntitlementRoleToContractorsVO(CcEntitlementRole ccEntitlementRole, List<Contractor> contractors) {
        entitlementRole = ccEntitlementRole.name
        contractors.each { Contractor contractor ->
            contractorNames << new WorkerNameVO("${contractor.person.lastName} ${contractor.person.firstName} ${contractor.person.middleName ?: ''}")
        }
    }
}

class WorkerNameVO {
    String name

    WorkerNameVO(String name) {
        this.name = name
    }

}

class EntitlementRoleNameVO {
    String name

    EntitlementRoleNameVO(String name) {
        this.name = name
    }
}

class EmployeeAccessReportVO {
    String SLID
    String PERNR
    String NAME
    String TITLE
    String SUPERVISOR
    String BUSINESSUNIT
    String SUPVEMAIL
    String ACCESSTYPE
    String PRA

    EmployeeAccessReportVO(HrInfo hrInfo, String accessType, String praStatus) {
        this.SLID = hrInfo.slid ?: CareConstants.REPORT_FIELD_EMPTY_TOKEN
        this.PERNR = hrInfo.pernr ?: CareConstants.REPORT_FIELD_EMPTY_TOKEN
        this.NAME = hrInfo.FULL_NAME ?: CareConstants.REPORT_FIELD_EMPTY_TOKEN
        this.TITLE = hrInfo.POSITION_TITLE ?: CareConstants.REPORT_FIELD_EMPTY_TOKEN
        this.SUPERVISOR = hrInfo.SUPV_FULL_NAME ?: CareConstants.REPORT_FIELD_EMPTY_TOKEN
        this.BUSINESSUNIT = hrInfo.PERS_SUBAREA_DESC ?: CareConstants.REPORT_FIELD_EMPTY_TOKEN
        this.SUPVEMAIL = hrInfo.supervisorSlid ? hrInfo.supervisorSlid + "@fpl.com" : CareConstants.REPORT_FIELD_EMPTY_TOKEN
        this.ACCESSTYPE = accessType
        this.PRA = praStatus
    }
}


