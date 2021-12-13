package com.force5solutions.care.cc

import com.force5solutions.care.common.Secured
import com.force5solutions.care.ldap.Permission

class BusinessUnitController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def centralUtilService

    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(value = Permission.READ_BUSINESS_UNIT)
    def list = {
        List<BusinessUnit> businessUnitInstanceList = []
        Integer businessUnitInstanceTotal = null
        params.order = params.order ?: 'asc'
        params.sort = params.sort ?: 'name'
        Integer offset = params.offset ? params.offset.toInteger() : 0
        params.max = centralUtilService.updateAndGetDefaultSizeOfListViewInConfig(params)
        if (!params?.max?.toString()?.equalsIgnoreCase('Unlimited')) {
            businessUnitInstanceList = BusinessUnit.list(params)
            businessUnitInstanceTotal = BusinessUnit.count()
        } else {
            businessUnitInstanceList = BusinessUnit.list()
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render template: 'businessUnitsTable', model: [businessUnitInstanceList: businessUnitInstanceList, businessUnitInstanceTotal: businessUnitInstanceTotal, offset: offset, max: params.max, order: params.order, sort: params.sort]
        } else {
            [businessUnitInstanceList: businessUnitInstanceList, businessUnitInstanceTotal: businessUnitInstanceTotal, offset: offset, max: params.max, order: params.order, sort: params.sort]
        }
    }

    @Secured(value = Permission.CREATE_BUSINESS_UNIT)
    def create = {
        def businessUnitInstance = new BusinessUnit()
        businessUnitInstance.properties = params
        return [businessUnitInstance: businessUnitInstance]
    }

    def save = {
        def businessUnitInstance = new BusinessUnit(params)
        if (businessUnitInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), businessUnitInstance.id])}"
            redirect(action: "show", id: businessUnitInstance.id)
        }
        else {
            render(view: "create", model: [businessUnitInstance: businessUnitInstance])
        }
    }

    @Secured(value = Permission.READ_BUSINESS_UNIT)
    def show = {
        def businessUnitInstance = BusinessUnit.get(params.id)
        if (!businessUnitInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), params.id])}"
            redirect(action: "list")
        }
        else {
            [businessUnitInstance: businessUnitInstance]
        }
    }

    @Secured(value = Permission.UPDATE_BUSINESS_UNIT)
    def edit = {
        def businessUnitInstance = BusinessUnit.get(params.id)
        if (!businessUnitInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [businessUnitInstance: businessUnitInstance]
        }
    }

    @Secured(value = Permission.UPDATE_BUSINESS_UNIT)
    def update = {
        def businessUnitInstance = BusinessUnit.get(params.id)
        if (businessUnitInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (businessUnitInstance.version > version) {

                    businessUnitInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'businessUnit.label', default: 'BusinessUnit')] as Object[], "Another user has updated this BusinessUnit while you were editing")
                    render(view: "edit", model: [businessUnitInstance: businessUnitInstance])
                    return
                }
            }
            businessUnitInstance.properties = params
            if (!businessUnitInstance.hasErrors() && businessUnitInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), businessUnitInstance.id])}"
                redirect(action: "show", id: businessUnitInstance.id)
            }
            else {
                render(view: "edit", model: [businessUnitInstance: businessUnitInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(value = Permission.DELETE_BUSINESS_UNIT)
    def delete = {
        def businessUnitInstance = BusinessUnit.get(params.id)
        if (businessUnitInstance) {
            try {
                businessUnitInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), params.id])}"
            redirect(action: "list")
        }
    }
}
