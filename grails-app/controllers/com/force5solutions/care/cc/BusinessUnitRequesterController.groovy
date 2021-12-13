package com.force5solutions.care.cc

import com.force5solutions.care.common.Secured
import com.force5solutions.care.ldap.Permission
import grails.converters.JSON
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class BusinessUnitRequesterController {

    def employeeUtilService
    def centralUtilService

    def index = { redirect(action: list, params: params) }
    def cancel = { params.id ? redirect(action: show, id: params.id) : redirect(action: list)}

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

    def certification = {
        BusinessUnitRequester bur = BusinessUnitRequester.get(params.id)
        Worker worker = Employee?.findBySlid(bur?.slid)
        [worker: worker, workerCertifications: worker?.currentCertifications]
    }

    @Secured(value = Permission.READ_BUR)
    def list = {
        String orderBy = params.order ?: 'asc'
        String sort = params.sort ?: 'unit'
        Integer offset = params.offset ? params.offset.toInteger() : 0
        def maxRes = centralUtilService.updateAndGetDefaultSizeOfListViewInConfig(params)
        Integer burInstanceTotal = null
        def burInstanceList = BusinessUnitRequester.createCriteria().list {
            if (sort == 'unit') {
                order(sort, orderBy)
            } else {
                person {
                    order(sort, orderBy)
                }
            }
        }
        if (!maxRes?.toString()?.equalsIgnoreCase('Unlimited')) {
            burInstanceTotal = burInstanceList.size()
            if (burInstanceTotal) {
                Integer lastIndex = offset + maxRes - 1
                if (lastIndex >= burInstanceTotal) {
                    lastIndex = burInstanceTotal - 1
                }
                burInstanceList = burInstanceList.getAt(offset..lastIndex)
            }
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render template: 'businessUnitRequestersTable', model: [burInstanceList: burInstanceList, burInstanceTotal: burInstanceTotal, offset: offset, max: maxRes, order: orderBy, sort: sort]
        } else {
            [burInstanceList: burInstanceList, burInstanceTotal: burInstanceTotal, offset: offset, max: maxRes, order: orderBy, sort: sort]
        }
    }

    @Secured(value = Permission.READ_BUR)
    def show = {
        BusinessUnitRequester instance = BusinessUnitRequester.get(params.id)

        if (!instance) {
            flash.message = "Business Unit Requester not found "
            redirect(action: list)
        }
        else { return [instance: createBURCommand(instance)] }
    }

    @Secured(value = Permission.DELETE_BUR)
    def delete = {
        def burInstance = BusinessUnitRequester.get(params.id)
        String burtemp = burInstance.toString()
        if (burInstance) {
            try {
                burInstance.delete(flush: true)
                flash.message = "Business Unit Requester ${burtemp} deleted"
                redirect(action: list)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Business Unit Requester ${burtemp} could not be deleted"
                redirect(action: show, id: params.id)
            }
        }
        else {
            flash.message = "Business Unit Requester not found "
            redirect(action: list)
        }
    }

    @Secured(value = Permission.UPDATE_BUR)
    def edit = {
        BusinessUnitRequester instance = BusinessUnitRequester.get(params.id)
        if (!instance) {
            flash.message = "Business Unit Requester not found "
            redirect(action: list)
            return
        }
        render(view: 'edit', model: [instance: createBURCommand(instance)])
    }

    @Secured(value = Permission.UPDATE_BUR)
    def update = {BusinessUnitRequesterCommand burCommand ->
        if (burCommand.validate()) {
            BusinessUnitRequester burInstance = createBURObject(burCommand)
            if (burInstance && burInstance.person.s() && burInstance.s()) {
                flash.message = "Business Unit Requester ${burInstance} updated"
                redirect(action: show, id: burInstance.id)
                return
            }
        }
        render(view: 'edit', model: [instance: burCommand])
    }

    @Secured(value = Permission.CREATE_BUR)
    def create = {
        BusinessUnitRequesterCommand burCommand = new BusinessUnitRequesterCommand()
        render(view: 'create', model: [instance: burCommand])
    }

    def save = {BusinessUnitRequesterCommand burCommand ->
        if (burCommand.validate()) {
            if (ConfigurationHolder.config.isEmployeeEditable == 'false') {
                Worker worker = Employee?.findBySlid(params.slid ?: "")
                if (!worker) {
                    worker = employeeUtilService.findOrCreateEmployee(params.slid)
                }
                Person person = worker.person
                BusinessUnitRequester businessUnitRequester = new BusinessUnitRequester()
                businessUnitRequester.person = person
                businessUnitRequester.unit = BusinessUnit.findByName(burCommand.unit)
                businessUnitRequester.person.s()
                businessUnitRequester.s()
                redirect(action: show, id: businessUnitRequester.id)
                return
            } else {
                BusinessUnitRequester burInstance = createBURObject(burCommand)
                if (burInstance.person.s() && burInstance.s()) {
                    flash.message = "Business Unit Requester ${burInstance} Saved"
                    redirect(action: show, id: burInstance.id)
                    return
                }
            }
        }
        render(view: 'create', model: [instance: burCommand, 'fail': 'true'])
    }

    def createBusinessUnitRequester = {
        BusinessUnitRequesterCommand burCommand = new BusinessUnitRequesterCommand()
        render(template: 'create', model: [instance: burCommand])
    }

    def saveBusinessUnitRequester = {BusinessUnitRequesterCommand burCommand ->
        if (burCommand.validate()) {
            BusinessUnitRequester burInstance = createBURObject(burCommand)
            if (burInstance.person.s() && burInstance.s()) {
                ContactVO contactVO = new ContactVO(burInstance.person)
                render("<option value='${burInstance.id}' title='${contactVO.title}' selected>${contactVO.title}</option>")
                return
            }
        }
        render(template: 'create', model: [instance: burCommand, 'fail': 'true'])
    }

    def createBURCommand(BusinessUnitRequester businessUnitRequester) {
        BusinessUnitRequesterCommand burCommand = new BusinessUnitRequesterCommand()
        burCommand.with {
            id = businessUnitRequester?.id
            unit = businessUnitRequester?.unit
            firstName = businessUnitRequester?.person?.firstName
            lastName = businessUnitRequester?.person?.lastName
            middleName = businessUnitRequester?.person?.middleName
            phone = businessUnitRequester?.person?.phone
            slid = businessUnitRequester?.person?.slid
            email = businessUnitRequester?.person?.email
            notes = businessUnitRequester?.person?.notes
        }
        return burCommand
    }

    def createBURObject(BusinessUnitRequesterCommand burCommand) {
        BusinessUnitRequester burInstance
        if (burCommand.id) {
            burInstance = BusinessUnitRequester.get(burCommand.id.toLong())
        } else {
            burInstance = new BusinessUnitRequester()
        }
        Person person = Person.findBySlid(burCommand.slid)
        burInstance.person = person ? person : burInstance.person
        if (!burInstance.person) {
            burInstance.person = new Person()
        }

        if (burCommand.unit) {
            burInstance.unit = BusinessUnit.findByName(burCommand.unit)
        }
        burInstance.person.firstName = burCommand.firstName
        burInstance.person.middleName = burCommand.middleName
        burInstance.person.lastName = burCommand.lastName
        burInstance.person.phone = burCommand.phone
        burInstance.person.email = burCommand.email
        burInstance.person.notes = burCommand.notes
        burInstance.person.slid = burCommand.slid
        return burInstance
    }

    def checkSlid = {
        Person person = employeeUtilService.findPerson(params.slid)
        if (person) {
            render person as JSON
        } else {
            render "{'fail':'true'}"
        }
    }
}

class BusinessUnitRequesterCommand {
    String id
    String unit
    String firstName
    String middleName
    String lastName
    String phone
    String slid
    String email
    String notes

    static constraints = {
        unit(blank: false)
        firstName(blank: false)
        lastName(blank: false)
        middleName(nullable: true)
        email(email: true, blank: false)
        phone(blank: false, nullable: false)
        notes(nullable: true, blank: true)
        slid(blank: false, validator: {val, obj ->
            if (val) {
                Person person = Person.findBySlid(obj.slid)
                BusinessUnitRequester businessUnitRequester
                if (person) {
                    businessUnitRequester = BusinessUnitRequester.findByPerson(person)
                    if (businessUnitRequester && (businessUnitRequester?.id != obj?.id?.toLong())) {
                        return "businessUnitRequester.person.unique.error"
                    }
                }
            }
        })
    }
}

class BusinessUnitRequesterVO {
    String firstName
    String lastName
    String middleName
    String name
    String completeName
    String email
    String phone
    String unit
    String slid

    BusinessUnitRequesterVO() {

    }

    BusinessUnitRequesterVO(BusinessUnitRequester businessUnitRequester) {
        firstName = businessUnitRequester.person.firstName
        middleName = businessUnitRequester.person.middleName
        lastName = businessUnitRequester.person.lastName
        name = "$lastName, $firstName"
        completeName = "${lastName ?: ''}, ${firstName ?: ''} ${middleName ?: ''}"
        email = businessUnitRequester.person.email
        phone = businessUnitRequester.person.phone
        unit = businessUnitRequester.unit
        slid = businessUnitRequester.person.slid
    }
}