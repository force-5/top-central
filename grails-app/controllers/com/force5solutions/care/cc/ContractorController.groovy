package com.force5solutions.care.cc

import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.common.Secured
import com.force5solutions.care.common.SecuredAndSendWorkerToPermission
import com.force5solutions.care.common.SessionUtils
import com.force5solutions.care.ldap.Permission
import com.force5solutions.care.ldap.PermissionLevel
import com.force5solutions.care.ldap.SecurityRole
import grails.converters.JSON

import static com.force5solutions.care.common.CareConstants.ACCESS_IF_BUR_OWNS_PERMISSION_LEVEL
import static com.force5solutions.care.common.CareConstants.UNRESTRICTED_ACCESS_PERMISSION_LEVEL


class ContractorController {
    def contractorService
    def contractorUtilService
    def auditService
    def workerService
    def dashboardService
    def centralUtilService

    def index = {
        redirect(action: 'list', params: params)
    }
    def cancel = {
        redirect(action: 'show', id: params.long('id'))
    }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def filterDialog = {
        def contractorCO = session.filterContractorCommand
        List<Vendor> vendors = Vendor.list().sort {
            it.toString().toLowerCase()
        }
        List<ContractorSupervisor> supervisors = ContractorSupervisor.list().sort {
            it.toString().toLowerCase()
        }
        List<BusinessUnitRequester> businessUnitRequesters = BusinessUnitRequester.list().sort {
            it.toString().toLowerCase()
        }
        render(template: 'contractorFilter',
                model: [vendors: vendors, supervisors: supervisors,
                        businessUnitRequesters: businessUnitRequesters,
                        contractorCO: contractorCO])
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def filterList = {ContractorCO contractorCO1 ->
        String workerType = (params.workerType && params.workerType.toString().equals(CareConstants.WORKER_TYPE_CONTRACTOR)) ? CareConstants.WORKER_TYPE_CONTRACTOR : CareConstants.WORKER_TYPE_EMPLOYEE
        if (params?.byDate) {
            Date fudgedExpiryDate
            try {
                fudgedExpiryDate = Date.parse("EEE MMM dd HH:mm:ss z yyyy", params.byDate as String)
            } catch (Exception e) {
                log.debug "Date parse Format different than usual, trying another one"
                fudgedExpiryDate = Date.parse("yyyy-MM-dd HH:mm:ss.S", params.byDate as String)
            }
            contractorCO1.certificationExpirationByDate = fudgedExpiryDate
        }

        if (params?.byPeriod) {
            String period = params?.byPeriod as String
            Long certificationId = (params.selectedCertification.toString().trim().equalsIgnoreCase('null') || params.selectedCertification.toString().trim().length() == 0) ? null : params.selectedCertification.toString().toLong()
            contractorCO1.expirationForecastPeriod = period
            contractorCO1.certificationId = certificationId
        }

        if (params?.filterByStatus) {
            contractorCO1.status = params?.filterByStatus as String
        }

        session.filterContractorCommand = contractorCO1
        redirect(action: 'list', params: [workerType: workerType, viewType: params.viewType.toString()])
    }

    def showAllContractor = {
        session.filterContractorCommand = null
        redirect(action: 'list')
    }

    @SecuredAndSendWorkerToPermission(value = Permission.DELETE_CONTRACTOR_PROFILE)
    def delete = {
        def contractorInstance = Contractor.get(params.long('id'))
        String name = contractorInstance.name
        if (contractorInstance) {
            try {
                workerService.delete(contractorInstance)
                flash.message = "Contractor ${name} deleted"
                redirect(action: list)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Contractor ${name} could not be deleted"
                redirect(action: show, id: params.long('id'))
            }
        }
        else {
            flash.message = "Contractor ${name} not found"
            redirect(action: list)
        }
    }

    @Secured(value = Permission.CREATE_CONTRACTOR_PROFILE)
    def create = {
        ContractorCO contractorCO = new ContractorCO()
        render(view: 'create', model: contractorCO.createModelForContractor())
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def addContractor = {
        render view: 'addContractorFromContractorHrInfo'
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def checkSlid = {
        Person person = contractorUtilService.findContractorDetails(params.slid)
        if (person) {
            render person as JSON
        } else {
            render "{'fail':'true'}"
        }
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def createContractorFromContractorHrInfo = {
        String slid = params.slid ?: ""
        Worker worker = contractorUtilService.findOrCreateContractor(slid)
        if (!worker) {
            flash.message = "Contractor with SLID: ${slid} could not be created/updated"
        } else {
            flash.message = "Contractor with SLID: ${slid} created/updated"
        }
        redirect(action: 'addContractor')
    }

    @SecuredAndSendWorkerToPermission(value = Permission.UPDATE_CONTRACTOR_PROFILE)
    def update = {ContractorCO contractorCO ->
        Contractor ci
        if (contractorCO.validate()) {
            ci = Contractor.createContractorObject(contractorCO as ContractorCO)
            if (auditService.saveWorker(ci)) {
                flash.message = "Contractor updated successfully"
                redirect(action: 'show', id: ci.id)
                return
            }
        }
        Map model = contractorCO.createModelForContractor()
        model['contractor'] = ci
        model.editFlag = true
        flash.errormessage = "Please enter valid value in required fields"
        render(view: 'contractor', model: model)
    }

    @Secured(value = Permission.CAN_ACCESS_PERSONNEL_MENU)
    def save = {ContractorCO contractorCO ->
        if (contractorCO.validate()) {
            Contractor ci = Contractor.createContractorObject(contractorCO)
            if (contractorService.saveNewContractor(ci, ci.person)) {
                flash.message = "New Contractor Created successfully"
                redirect(action: 'show', id: ci.id)
                return
            }
        }
        flash.errormessage = "Please enter valid value in required fields"
        render(view: 'create', model: contractorCO.createModelForContractor())
    }

    @SecuredAndSendWorkerToPermission(value = Permission.READ_CONTRACTOR_PROFILE)
    def show = {
        Contractor contractorInstance = Contractor.get(params.long('id'))
        if (!contractorInstance) {
            flash.message = "Contractor not found with id ${params.long('id')}"
            redirect(action: list)
            return
        }
        ContractorCO contractorCO = new ContractorCO(contractorInstance)
        Map model = contractorCO.createModelForContractor()
        model['contractor'] = contractorInstance
        render(view: 'contractor', model: model)
    }

    def list = {
        String orderBy = params.order ?: 'asc'
        String sort = params.sort ?: 'primeVendor'
        Integer offset = params.offset ? params.offset.toInteger() : 0
        def maxRes = centralUtilService.updateAndGetDefaultSizeOfListViewInConfig(params)
        ContractorCO contractorCO = session.filterContractorCommand

        String loggedInUserSlid = SessionUtils.session?.loggedUser
        BusinessUnitRequester businessUnitRequester = BusinessUnitRequester.findBySlid(loggedInUserSlid)
        boolean isBURRestriction = false

        if (loggedInUserSlid) {
            List<SecurityRole> roles = SessionUtils.session?.roles ? SecurityRole.findAllByNameInList(SessionUtils.session?.roles) : []
            List permissionLevels = PermissionLevel.findAllByPermissionAndRoleInList(Permission.READ_CONTRACTOR_PROFILE, roles)
            List levels = permissionLevels*.level
            if (!levels.any {it == UNRESTRICTED_ACCESS_PERMISSION_LEVEL}) {
                if (levels.any {(it % ACCESS_IF_BUR_OWNS_PERMISSION_LEVEL) == 0}) {
                    isBURRestriction = true
                }
            }
        }

        def contractorList = Contractor.createCriteria().listDistinct {
            and {
                person {
                    if (contractorCO?.firstName) ilike("firstName", "%" + contractorCO.firstName + "%")
                    if (contractorCO?.middleName) ilike("middleName", "%" + contractorCO.middleName + "%")
                    if (contractorCO?.lastName) ilike("lastName", "%" + contractorCO.lastName + "%")
                    if (contractorCO?.phone) ilike("phone", "%" + contractorCO.phone + "%")
                    if (contractorCO?.email) ilike("email", "%" + contractorCO.email + "%")
                    if (contractorCO?.slid) ilike("slid", "%" + contractorCO.slid + "%")
                }
                if (contractorCO?.badgeNumber) ilike("badgeNumber", "%" + contractorCO.badgeNumber + "%")
                if (contractorCO?.workerNumber) ilike("workerNumber", "%" + contractorCO.workerNumber + "%")
                if (contractorCO?.primeVendor > 0) eq("primeVendor", Vendor.get(contractorCO.primeVendor))
                if (contractorCO?.supervisor > 0) eq("supervisor", ContractorSupervisor.get(contractorCO.supervisor))
                businessUnitRequesters {
                    or {
                        if (contractorCO?.businessUnitRequester > 0) eq("id", contractorCO.businessUnitRequester)
                        if (isBURRestriction) eq("id", businessUnitRequester?.id)
                    }
                }
            }

            if (sort == 'primeVendor') {
                primeVendor {
                    order("companyName", orderBy)
                }
            } else if (sort == 'lastName' || sort == 'slid') {
                person {
                    order(sort, orderBy)
                }
            } else {
                order(sort, orderBy)
            }
            order("id", orderBy)
        }

        if (contractorCO?.status) {
            if (contractorCO.status in [CareConstants.ACCESS_TYPE_PHYSICAL_ONLY, CareConstants.ACCESS_TYPE_CYBER_ONLY, CareConstants.ACCESS_TYPE_BOTH]) {
                contractorList = contractorList.findAll {it.accessTypeForWorkerBasedOnConfigProperties == contractorCO.status}
            } else {
                WorkerStatus status = WorkerStatus?.getWorkerStatus(contractorCO?.status) ?: WorkerStatus?.(contractorCO?.status)
                contractorList = contractorList.findAll {it.status == status}
            }
        }

        if (contractorCO?.roleStatus) {
            EntitlementRoleAccessStatus entitlementRoleAccessStatus = EntitlementRoleAccessStatus.(contractorCO?.roleStatus)
            contractorList = contractorList.findAll {Contractor contractor ->
                contractor.entitlementRoles.any {it.status == entitlementRoleAccessStatus}
            }
        }

        if (params?.workersWithExpiringCertificationsByDate) {
            contractorList = Contractor.getAll(params.list("workersWithExpiringCertifications"))
        }

        if (contractorCO?.certificationExpirationByDate) {
            List<Worker> certificationExpirationByDateEmployees = []
            List<Worker> workers = Contractor.list()
            List<WorkerCertification> workerCertifications = (workers*.effectiveCertifications)?.flatten()
            workerCertifications = workerCertifications.findAll {it.fudgedExpiry.format('dd-MM-yyyy') == contractorCO.certificationExpirationByDate.format('dd-MM-yyyy')}
            workerCertifications.each {certification ->
                certificationExpirationByDateEmployees.add(certification.worker as Contractor)
            }
            certificationExpirationByDateEmployees.unique()
            contractorList = contractorList.findAll {it in certificationExpirationByDateEmployees}
        }

        if (contractorCO?.expirationForecastPeriod) {
            List<Worker> expirationForecastPeriodEmployees = []
            Certification certification = contractorCO?.certificationId ? Certification.get(contractorCO.certificationId) : null
            ExpirationForecastVO expirationForecastVO = dashboardService.getExpirationForecastsByCertification(new Date(), certification, params.workerType.toString())?.find {it.period == contractorCO.expirationForecastPeriod}
            expirationForecastVO.workerIds?.each {workerId ->
                expirationForecastPeriodEmployees.add(Worker.read(workerId as Long) as Contractor)
            }
            expirationForecastPeriodEmployees.unique()
            contractorList = contractorList.findAll {it in expirationForecastPeriodEmployees}
        }

        contractorList = (centralUtilService.filterWorkersBasedOnInheritanceForLoggedInUserCached(contractorList as List<Worker>, (params.viewType && !params.viewType.toString().equalsIgnoreCase('null')) ? params.viewType.toString() : CareConstants.VIEW_TYPE_GLOBAL, CareConstants.WORKER_TYPE_CONTRACTOR)) as List<Contractor>

        def contractorListTotal = null
        if (!maxRes.toString()?.equalsIgnoreCase('Unlimited')) {
            contractorListTotal = contractorList.size()
            if (contractorListTotal) {
                Integer lastIndex = offset + maxRes - 1
                if (lastIndex >= contractorListTotal) {
                    lastIndex = contractorListTotal - 1
                }
                contractorList = contractorList.getAt(offset..lastIndex)
            }
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render template: 'contractorsTable', contentType: 'text/plain', model: [contractorInstanceList: contractorList, contractorInstanceTotal: contractorListTotal, offset: offset, max: maxRes, order: orderBy, sort: sort]
        } else {
            render view: 'list', model: [contractorInstanceList: contractorList, contractorInstanceTotal: contractorListTotal, offset: offset, max: maxRes, order: orderBy, sort: sort]
        }
    }
}
