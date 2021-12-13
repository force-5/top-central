package com.force5solutions.care.cc

import com.force5solutions.care.common.Secured
import com.force5solutions.care.ldap.Permission

class ContractorSupervisorController {

    def centralUtilService

    def index = {
        redirect(action: list, params: params)
    }

    static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

    @Secured(value = Permission.READ_CONTRACTOR_SUP)
    def list = {
        String orderBy = params.order ?: 'asc'
        String sort = params.sort ?: 'vendor'
        Integer offset = params.offset ? params.offset.toInteger() : 0
        def maxRes = centralUtilService.updateAndGetDefaultSizeOfListViewInConfig(params)

        Integer supervisorsCount = null
        def supervisors = ContractorSupervisor.createCriteria().list {
            if (sort == 'vendor') {
                vendor {
                    order("companyName", orderBy)
                }
            } else {
                person{
                    order(sort, orderBy)
                }
            }
        }
        if (!maxRes?.toString()?.equalsIgnoreCase('Unlimited')) {
            supervisorsCount = supervisors.size()
            if (supervisorsCount) {
                Integer lastIndex = offset + maxRes - 1
                if (lastIndex >= supervisorsCount) {
                    lastIndex = supervisorsCount - 1
                }
                supervisors = supervisors.getAt(offset..lastIndex)
            }
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render template: 'contractorSupervisorsTable', model: [supervisors: supervisors, supervisorsCount: supervisorsCount, offset: offset, max: maxRes, order: orderBy, sort: sort]
        } else {
            [supervisors: supervisors, supervisorsCount: supervisorsCount, offset: offset, max: maxRes, order: orderBy, sort: sort]
        }
    }

    @Secured(value = Permission.READ_CONTRACTOR_SUP)
    def show = {
        ContractorSupervisor supervisor = ContractorSupervisor.get(params.id)
        if (!supervisor) {
            flash.message = "Supervisor not found"
            redirect(action: list)
        }
        else { return [supervisor: createSupervisorCO(supervisor)] }
    }

    @Secured(value = Permission.DELETE_CONTRACTOR_SUP)
    def delete = {
        ContractorSupervisor supervisor = ContractorSupervisor.get(params.id)
        if (supervisor) {
            try {
                String name = supervisor.toString()
                supervisor.delete(flush: true)
                flash.message = "Supervisor ${name} deleted"
                redirect(action: list)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Supervisor ${supervisor} could not be deleted"
                redirect(action: show, id: params.id)
            }
        }
        else {
            flash.message = "Supervisor not found "
            redirect(action: list)
        }
    }


    @Secured(value = Permission.UPDATE_CONTRACTOR_SUP)
    def edit = {
        ContractorSupervisor supervisor = ContractorSupervisor.get(params.id)

        if (!supervisor) {
            flash.message = "Supervisor not found."
            redirect(action: list)
        }
        else {
            return [supervisor: createSupervisorCO(supervisor), vendors: Vendor.list()]
        }
    }


    @Secured(value = Permission.UPDATE_CONTRACTOR_SUP)
    def update = {SupervisorCO supervisorCO->
        if (!supervisorCO.hasErrors()) {
            ContractorSupervisor supervisor = createSupervisorObject(supervisorCO)
            if (supervisor && supervisor.s()) {
                flash.message = "Supervisor ${supervisor} updated successfully."
                redirect(action: show, id: supervisor.id)
            } else {
                flash.message = "Supervisor can not be updated."
            }
        }
        render(view: 'edit', model: ['supervisor': supervisorCO, vendors: Vendor.list()])
    }

    @Secured(value = Permission.CREATE_CONTRACTOR_SUP)
    def create = {
        SupervisorCO supervisorCO = new SupervisorCO()
        List<Vendor> vendors=Vendor.list().sort{it.toString()}
        render(view: 'create', model: ['supervisor': supervisorCO, vendors: vendors])
    }

    def save = {SupervisorCO supervisorCO->
        if (!supervisorCO.hasErrors()) {
            ContractorSupervisor supervisor = createSupervisorObject(supervisorCO)
            if (supervisor && supervisor.person.s() && supervisor.s()) {
                flash.message = "Supervisor ${supervisor} saved successfully."
                redirect(action: show, id: supervisor.id)
            }
        }
        render(view: 'create', model: ['supervisor': supervisorCO, vendors: Vendor.list()])
    }


    @Secured(value = Permission.CREATE_CONTRACTOR_SUP)
    def createSupervisor = {
        SupervisorCO supervisorCO=new SupervisorCO()
        Vendor vendor=Vendor.get(params.vendorId.toLong())
        supervisorCO.vendorId=params.vendorId
        supervisorCO.vendorName=vendor.toString()
        render(template: '/contractorSupervisor/create', model: [supervisor: supervisorCO, vendors: Vendor.list()])
    }

    def saveSupervisor = {SupervisorCO supervisorCO->
        if (!supervisorCO.hasErrors()) {
            ContractorSupervisor supervisor = createSupervisorObject(supervisorCO)
            if (supervisor && supervisor.person.s() && supervisor.s()) {
                flash.message = "Supervisor ${supervisor} saved successfully."
                List<ContractorSupervisor> supervisors = ContractorSupervisor.findAllByVendor(supervisor.vendor)
                render(template: '/contractorSupervisor/selectBox',
                        model: [supervisors: supervisors, selectedSupervisorId: supervisor.id])
            } else{
                render(template: '/contractorSupervisor/create', model: [supervisor: supervisorCO, vendors: Vendor.list()])
            }
        } else {
            render(template: '/contractorSupervisor/create', model: [supervisor: supervisorCO, vendors: Vendor.list()])
        }
    }

    ContractorSupervisor setVendor(ContractorSupervisor supervisor, String vendorId) {
        if (vendorId) {
            supervisor.vendor = Vendor.get(vendorId)
            supervisor.vendor.supervisors.add(supervisor)
        }
        else {
            supervisor.vendor = null
        }
        return supervisor
    }

    def supervisorsByVendor = {
        if(params.id?.toInteger() > 0){
            Vendor vendor = Vendor.findById(params.id)
            List<ContractorSupervisor> supervisors = ContractorSupervisor.findAllByVendor(vendor)
            render(template: 'supervisorsByVendor', model: [supervisors: supervisors ])
        }
        else{
            render(template: 'supervisorsByVendor', model: [supervisors: []])
        }
    }

    SupervisorCO createSupervisorCO(ContractorSupervisor supervisor) {
        SupervisorCO supervisorCO = new SupervisorCO()
        supervisorCO.with {
            id = supervisor?.id
            vendorId=supervisor?.vendor?.id
            vendorName=supervisor?.vendor?.toString()
            firstName = supervisor?.person?.firstName
            lastName = supervisor?.person?.lastName
            middleName = supervisor?.person?.middleName
            phone = supervisor?.person?.phone
            email = supervisor?.person?.email
            notes = supervisor?.person?.notes
        }
        return supervisorCO
    }

    ContractorSupervisor createSupervisorObject(SupervisorCO supervisorCO) {
        ContractorSupervisor supervisor = new ContractorSupervisor()
        if (supervisorCO.id) {
            supervisor = ContractorSupervisor.get(supervisorCO.id?.toLong())
        } else {
            supervisor.person = new Person()
        }

        supervisor.vendor=Vendor.get(supervisorCO.vendorId.toLong())
        supervisor.with {
            person.firstName = supervisorCO?.firstName
            person.lastName = supervisorCO?.lastName
            person.middleName = supervisorCO?.middleName
            person.phone = supervisorCO?.phone
            person.email = supervisorCO?.email
            person.notes = supervisorCO?.notes
        }
        return supervisor
    }

}


class SupervisorCO {
    String id
    String vendorId
    String vendorName
    String firstName
    String middleName
    String lastName
    String phone
    String email
    String notes

    static constraints = {
        vendorId(blank:false)
        vendorName(blank:true)
        firstName(blank: false)
        middleName(blank: true)
        lastName(blank: false)
        email(blank:false, email:true)
        phone(blank: false)
        notes(blank: true)
    }
}