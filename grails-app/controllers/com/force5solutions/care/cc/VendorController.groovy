package com.force5solutions.care.cc

import com.force5solutions.care.common.Secured
import com.force5solutions.care.ldap.Permission

class VendorController {

    def centralUtilService

    def index = { redirect(action: list, params: params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

    @Secured(value = Permission.READ_VENDOR)
    def list = {
        List<Vendor> vendorInstanceList = []
        Integer vendorInstanceTotal = null
        params.order = params.order ?: 'asc'
        params.sort = params.sort ?: 'companyName'
        Integer offset = params.offset ? params.offset.toInteger() : 0
        params.max = centralUtilService.updateAndGetDefaultSizeOfListViewInConfig(params)
        if (!params?.max?.toString()?.equalsIgnoreCase('Unlimited')) {
            vendorInstanceList = Vendor.list(params)
            vendorInstanceTotal = Vendor.count()
        } else {
            vendorInstanceList = Vendor.list()
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render template: 'vendorsTable', model: [vendorInstanceList: vendorInstanceList, vendorInstanceTotal: vendorInstanceTotal, offset: offset, max: params.max, 'stateMap': AppUtil.getStateMap(), order: params.order, sort: params.sort]
        } else {
            [vendorInstanceList: vendorInstanceList, vendorInstanceTotal: vendorInstanceTotal, offset: offset, max: params.max, 'stateMap': AppUtil.getStateMap(), order: params.order, sort: params.sort]
        }
    }

    @Secured(value = Permission.READ_VENDOR)
    def show = {
        Vendor vendorInstance = Vendor.get(params.id)

        if (!vendorInstance) {
            flash.message = "Vendor not found"
            redirect(action: list)
        }
        else { return [vendorInstance: vendorInstance, 'stateMap': AppUtil.getStateMap()] }
    }

    @Secured(value = Permission.DELETE_VENDOR)
    def delete = {
        Vendor vendorInstance = Vendor.get(params.id)
        if (vendorInstance) {
            try {
                vendorInstance.delete(flush: true)
                flash.message = "Vendor ${vendorInstance} deleted"
                redirect(action: list)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Vendor ${vendorInstance} could not be deleted"
                redirect(action: show, id: params.id)
            }
        }
        else {
            flash.message = "Vendor not found "
            redirect(action: list)
        }
    }

    @Secured(value = Permission.UPDATE_VENDOR)
    def edit = {
        Vendor vendorInstance = Vendor.get(params.id)

        if (!vendorInstance) {
            flash.message = "Vendor not found "
            redirect(action: list)
        }
        else {
            return [vendorInstance: vendorInstance, 'stateMap': AppUtil.getStateMap(), vendors: Vendor.listOrderByCompanyName()]
        }
    }

    @Secured(value = Permission.UPDATE_VENDOR)
    def update = {
        Vendor vendorInstance = Vendor.get(params.id)
        if (vendorInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (vendorInstance.version > version) {

                    vendorInstance.errors.rejectValue("version", "vendor.optimistic.locking.failure", "Another user has updated this Vendor while you were editing.")
                    render(view: 'edit', model: [vendorInstance: vendorInstance, 'stateMap': AppUtil.getStateMap(), vendors: Vendor.listOrderByCompanyName()])
                    return
                }
            }
            vendorInstance.properties = params.properties
            if (vendorInstance.validate() && !vendorInstance.hasErrors() && vendorInstance.s()) {
                flash.message = "Vendor ${vendorInstance} updated"
                redirect(action: show, id: vendorInstance.id)
            }
            else {
                render(view: 'edit', model: [vendorInstance: vendorInstance, 'stateMap': AppUtil.getStateMap(), vendors: Vendor.listOrderByCompanyName()])
            }
        }
        else {
            flash.message = "Vendor not found "
            redirect(action: list)
        }
    }

    @Secured(value = Permission.CREATE_VENDOR)
    def create = {
        Vendor vendorInstance = new Vendor()
        return ['vendorInstance': vendorInstance, 'stateMap': AppUtil.getStateMap(), vendors: Vendor.listOrderByCompanyName()]
    }

    def save = {
        Vendor vendorInstance = new Vendor()
        vendorInstance.properties = params.properties
        if (vendorInstance.validate() && !vendorInstance.hasErrors() && vendorInstance.s()) {
            flash.message = "Vendor ${vendorInstance} created"
            redirect(action: show, id: vendorInstance.id)
        }
        else {
            render(view: 'create', model: [vendorInstance: vendorInstance, 'stateMap': AppUtil.getStateMap(), vendors: Vendor.listOrderByCompanyName()])
        }
    }


    def createVendor = {
        Vendor vendorInstance = new Vendor()
        render(template: 'create', model: [instance: vendorInstance, 'stateMap': AppUtil.getStateMap(), vendors: Vendor.listOrderByCompanyName()])
    }

    def saveVendor = {
        Vendor vendorInstance = new Vendor()
        vendorInstance.properties = params.properties
        if (vendorInstance.validate() && !vendorInstance.hasErrors() && vendorInstance.s()) {
            render(template: 'selectBox', model: [vendors: Vendor.list(), selectedVendorId: vendorInstance.id])
            return
        }
        render(template: 'create', model: [instance: vendorInstance, 'stateMap': AppUtil.getStateMap(), vendors: Vendor.listOrderByCompanyName()])
    }
}
