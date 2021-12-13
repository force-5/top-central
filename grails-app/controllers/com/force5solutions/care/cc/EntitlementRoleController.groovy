package com.force5solutions.care.cc

import com.force5solutions.care.common.EntitlementStatus
import com.force5solutions.care.ldap.Permission
import com.force5solutions.care.common.Secured

class EntitlementRoleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def entitlementRoleService
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(value = Permission.READ_ENTITLEMENT_ROLE)
    def list = {
        Integer offset = params.offset ? params.offset.toInteger() : 0
        Integer maxRes = params.max ? params.max.toInteger() : 10
        String orderBy = params.order ?: 'asc'
        String sort = params.sort ?: 'name'
        def entitlementRoleList = CcEntitlementRole.createCriteria().list {
            if (sort == 'origin') {
                origin {
                    order("name", orderBy)
                }
            }
            else {
                order(sort, orderBy)
            }
            order("id", orderBy)
        }
        entitlementRoleList = entitlementRoleList.findAll {!it.deletedFromAps}
        def entitlementRoleTotal = entitlementRoleList.size()
        if (entitlementRoleTotal) {
            Integer lastIndex = offset + maxRes - 1
            if (lastIndex >= entitlementRoleTotal) {
                lastIndex = entitlementRoleTotal - 1
            }
            entitlementRoleList = entitlementRoleList.getAt(offset..lastIndex)
        }
        [entitlementRoleList: entitlementRoleList, entitlementRoleTotal: entitlementRoleTotal]
    }

    @Secured(value = Permission.CREATE_ENTITLEMENT_ROLE)
    def create = {
        def entitlementRole = new CcEntitlementRole()
        entitlementRole.properties = params
        return [entitlementRole: entitlementRole, statuses: EntitlementStatus.values()]
    }

    def save = {
        if (params.status) {
            params.status = EntitlementStatus."${params.status}"
        }
        def entitlementRole = new CcEntitlementRole()
        entitlementRole.id = UUID.randomUUID().toString()
        if (params.entitlementRoles) {
            entitlementRole.entitlementRoles = CcEntitlementRole.findAllByIdInListAndDeletedFromAps(params.list("entitlementRoles"), false)
            params.remove("entitlementRoles")
        }
        if (params.entitlementIds) {
            entitlementRole.entitlements = CcEntitlement.findAllByIdInList(params.list("entitlementIds"))
            params.remove("entitlementIds")
        }

        entitlementRole.properties = params
        if (entitlementRoleService.save(entitlementRole)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'entitlementRole.label', default: 'Entitlement Role'), entitlementRole.name])}"
            redirect(action: "show", id: entitlementRole.id)
        } else {
            render(view: "create", model: [entitlementRole: entitlementRole, statuses: EntitlementStatus.values()])
        }
    }

    @Secured(value = Permission.READ_ENTITLEMENT_ROLE)
    def show = {
        def entitlementRole = CcEntitlementRole.findByIdAndDeletedFromAps(params.id, false)
        if (!entitlementRole) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entitlementRole.label', default: 'Entitlement Role'), params.id])}"
            redirect(action: "list")
        }
        else {
            String standardsString = entitlementRole.inheritedStandards*.toString()?.join(", ")
            String entitlementPoliciesString = entitlementRole.inheritedEntitlementPolicies*.toString()?.join(", ")
            return [entitlementRole: entitlementRole, standardsString: standardsString, entitlementPoliciesString: entitlementPoliciesString]
        }
    }

    @Secured(value = Permission.UPDATE_ENTITLEMENT_ROLE)
    def edit = {
        def entitlementRole = CcEntitlementRole.findByIdAndDeletedFromAps(params.id, false)
        if (!entitlementRole) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entitlementRole.label', default: 'Entitlement Role'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [entitlementRole: entitlementRole, statuses: EntitlementStatus.values()]
        }
    }

    @Secured(value = Permission.UPDATE_ENTITLEMENT_ROLE)
    def update = {
        if (params.status) {
            params.status = EntitlementStatus."${params.status}"
        }
        def entitlementRole = CcEntitlementRole.findByIdAndDeletedFromAps(params.id, false)
        if (entitlementRole) {
            if (params.version) {
                def version = params.version.toLong()
                if (entitlementRole.version > version) {

                    entitlementRole.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'entitlementRole.label', default: 'Entitlement Role')] as Object[], "Another user has updated this CcEntitlementRole while you were editing")
                    render(view: "edit", model: [entitlementRole: entitlementRole, statuses: EntitlementStatus.values()])
                    return
                }
            }
            if (params.entitlementRoles) {
                entitlementRole.entitlementRoles = CcEntitlementRole.findAllByIdInListAndDeletedFromAps(params.list("entitlementRoles"), false)
                params.remove("entitlementRoles")
            } else {
                entitlementRole.entitlementRoles = []
            }

            if (params.entitlementIds) {
                entitlementRole.entitlements = CcEntitlement.findAllByIdInList(params.list("entitlementIds"))
                params.remove("entitlementIds")
            }

            entitlementRole.properties = params
            if (!entitlementRole.hasErrors() && entitlementRoleService.save(entitlementRole)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'entitlementRole.label', default: 'Entitlement Role'), entitlementRole.name])}"
                redirect(action: "show", id: entitlementRole.id)
            }
            else {
                render(view: "edit", model: [entitlementRole: entitlementRole, statuses: EntitlementStatus.values()])
            }
        } else {
            render(view: "edit", model: [entitlementRole: entitlementRole, statuses: EntitlementStatus.values()])
        }
    }

    @Secured(value = Permission.DELETE_ENTITLEMENT_ROLE)
    def delete = {
        def entitlementRole = CcEntitlementRole.findByIdAndDeletedFromAps(params.id, false)
        String name = entitlementRole.name
        if (entitlementRole) {
            try {
                entitlementRole.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'entitlementRole.label', default: 'Entitlement Role'), name])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'entitlementRole.label', default: 'Entitlement Role'), name])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entitlementRole.label', default: 'Entitlement Role'), name])}"
            redirect(action: "list")
        }
    }

}
