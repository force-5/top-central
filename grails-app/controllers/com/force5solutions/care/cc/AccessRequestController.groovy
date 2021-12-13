package com.force5solutions.care.cc

import com.force5solutions.care.feed.ContractorHrInfo
import com.force5solutions.care.feed.HrInfo

public class AccessRequestController {
    def employeeUtilService
    def contractorUtilService
    def auditService
    def workerEntitlementRoleService

    static allowedMethods = [roleRequestForms: "POST", getAccessInfo: "POST", processRequest: "POST"]

    def index = {
        render(view: '/accessRequest/accessRequest')
    }

    def accessRequest = {
        Employee employee = employeeUtilService.findOrCreateEmployee(params.employeeSlid, params.workerNumber)
        if (params.selectedEntitlementRoles) {
            List<String> selectedEntitlementRoles = params.list('selectedEntitlementRoles')
            selectedEntitlementRoles.each { String entitlementRoleId ->
                employee = Employee.get(employee.id)
                workerEntitlementRoleService.createWorkerEntitlementRole(employee, entitlementRoleId, params.workerRoleHistoryItem, session.loggedUser)
            }
        }
        flash.message = "EntitlementRole added successfully"
        redirect(controller: 'employee', action: 'show', id: employee.id)
    }

    def populatePopup = {
        Employee employee = employeeUtilService.findOrCreateEmployee(params.employeeSlid, params.workerNumber)
        if (!employee) {
            render "error"
        } else {
            auditService.saveWorker(employee)
            render(care.locationTree('worker': employee))
        }
    }

    def publicAccessRequest = {
        [hasErrors: params.hasErrors]
    }

    def getAccessInfo = {
        String slid = params.slid
        if (!slid) {
            flash.message = message(code: 'accessRequest.blank.message', args: ['SLID'])
            forward(action: 'publicAccessRequest', params: [hasErrors: true])
        } else if (!HrInfo.countBySlid(slid)) {
            if (!ContractorHrInfo.countByContractorSlid(slid)) {
                flash.message = message(code: 'accessRequest.slid.not.found.message', args: [slid])
            } else {
                flash.message = message(code: 'accessRequest.contractor.slid.message', args: [slid])
            }
            forward(action: 'publicAccessRequest', params: [hasErrors: true])
        } else {
            Worker worker = employeeUtilService.findOrCreateEmployee(slid)
            if (!worker) {
                worker = contractorUtilService.findOrCreateContractor(slid)
            }
            List<String> entitlementRoleIds = params.list('entitlementRoleIds')
            List<CcEntitlementRole> entitlementRoles = CcEntitlementRole.findAllByIdInListAndDeletedFromAps(entitlementRoleIds, false)
            Map<String, String> accessJustifications = params.findAll { it.key.startsWith('accessJustification-') }
            return [worker: worker, entitlementRoles: entitlementRoles, entitlementRoleIds: entitlementRoleIds, allEntitlementRoles: CcEntitlementRole.listUndeleted(), accessJustifications: accessJustifications]
        }
    }

    def processRequest = {
        Worker worker = Worker.get(params.long('workerId'))
        List<String> entitlementRoleIds = params.list('entitlementRoleIds')
        List<CcEntitlementRole> entitlementRoles = CcEntitlementRole.findAllByIdInListAndDeletedFromAps(entitlementRoleIds, false)
        Map<String, String> accessJustifications = params.findAll { it.key.startsWith('accessJustification-') }
        if (accessJustifications.any { !it.value.trim() }) {
            flash.message = message(code: 'accessRequest.blank.message', args: ['Justification'])
            forward(action: 'roleRequestForms', model: [worker: worker, entitlementRolesIds: entitlementRoles*.id, accessJustifications: accessJustifications])
        } else {
            String userId = session.loggedUser ?: worker.slid
            Set<WorkerEntitlementRole> workerEntitlementRoles = worker.entitlementRoles
            Set<WorkerEntitlementRole> entitlementRolesThatNeedNotToBeRequestedAccess = workerEntitlementRoles ? workerEntitlementRoles.findAll { it.status in [EntitlementRoleAccessStatus.active, EntitlementRoleAccessStatus.pendingApproval] } : []
            entitlementRoleIds.each { String entitlementRoleId ->
                worker = Worker.get(worker.id)
                CcEntitlementRole ccEntitlementRole = CcEntitlementRole.findByIdAndDeletedFromAps(entitlementRoleId, false)
                if ((!ccEntitlementRole.isCip()) && (!entitlementRolesThatNeedNotToBeRequestedAccess || !(ccEntitlementRole in entitlementRolesThatNeedNotToBeRequestedAccess*.entitlementRole))) {
                    def accessJustification = params["accessJustification-${entitlementRoleId}"]?.toString()
                    if (accessJustification && accessJustification.length() > 8000) {
                        accessJustification = accessJustification.substring(0, 8000)
                    }
                    workerEntitlementRoleService.createWorkerEntitlementRole(worker, entitlementRoleId, accessJustification, userId, [], true)
                    flash.message = message(code: 'accessRequest.employee.successfully.added', args: [worker.name])
                }
            }
            redirect(action: 'publicAccessRequest')
        }
    }

    def roleRequestForms = {
        Worker worker = Worker.get(params.long('workerId'))
        List<String> entitlementRoleIds = params.list('entitlementRoleIds')?.flatten()
        if (!entitlementRoleIds) {
            flash.message = message(code: 'accessRequest.entitlementRoles.empty.message')
            forward(action: 'getAccessInfo', params: [slid: worker.slid])
        } else {
            List<CcEntitlementRole> entitlementRoles = CcEntitlementRole.findAllByIdInListAndDeletedFromAps(entitlementRoleIds, false)
            Map<String, String> accessJustifications = params.findAll { it.key.startsWith('accessJustification-') }
            render(view: 'roleRequestForms', model: [entitlementRoles: entitlementRoles, worker: worker, accessJustifications: accessJustifications])
        }
    }

}
