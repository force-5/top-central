package com.force5solutions.care.cc

import com.force5solutions.care.common.Secured
import com.force5solutions.care.feed.HrInfo
import com.force5solutions.care.ldap.Permission
import grails.converters.JSON
import org.codehaus.groovy.grails.commons.ConfigurationHolder

public class EmployeeSupervisorController {

    def employeeUtilService
    def centralUtilService

    def index = {
        redirect(action: list, params: params)
    }

    static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

    @Secured(value = Permission.READ_EMPLOYEE_SUP)
    def list = {
        String orderBy = params.order ?: 'asc'
        String sort = params.sort ?: 'firstName'
        Integer offset = params.offset ? params.offset.toInteger() : 0
        def maxRes = centralUtilService.updateAndGetDefaultSizeOfListViewInConfig(params)
        Integer supervisorsCount = null
        def employeeSupervisors = EmployeeSupervisor.createCriteria().list {
            person {
                order(sort, orderBy)
            }
        }
        if (!maxRes.toString()?.equalsIgnoreCase('Unlimited')) {
            supervisorsCount = employeeSupervisors.size()
            if (supervisorsCount) {
                Integer lastIndex = offset + maxRes - 1
                if (lastIndex >= supervisorsCount) {
                    lastIndex = supervisorsCount - 1
                }
                employeeSupervisors = employeeSupervisors.getAt(offset..lastIndex)
            }
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render template: 'employeeSupervisorsTable', model: [employeeSupervisors: employeeSupervisors, supervisorsCount: supervisorsCount, offset: offset, max: maxRes, order: orderBy, sort: sort]
        } else {
            [employeeSupervisors: employeeSupervisors, supervisorsCount: supervisorsCount, offset: offset, max: maxRes, order: orderBy, sort: sort]
        }
    }

    @Secured(value = Permission.READ_EMPLOYEE_SUP)
    def show = {
        EmployeeSupervisor employeeSupervisor = EmployeeSupervisor.get(params.id)
        if (!employeeSupervisor) {
            flash.message = "Supervisor not found"
            redirect(action: list)
        }
        else {
            String viewToRender = (ConfigurationHolder.config.isEmployeeEditable == 'true') ? 'show' : 'showReadOnly'
            render(view: viewToRender, model: [employeeSupervisor: createEmployeeSupervisorCO(employeeSupervisor)])
        }
    }

    @Secured(value = Permission.DELETE_EMPLOYEE_SUP)
    def delete = {
        EmployeeSupervisor employeeSupervisor = EmployeeSupervisor.get(params.id)
        if (employeeSupervisor) {
            try {
                String name = employeeSupervisor.toString()
                employeeSupervisor.delete(flush: true)
                flash.message = "Supervisor ${name} deleted"
                redirect(action: list)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Supervisor ${employeeSupervisor} could not be deleted"
                redirect(action: show, id: params.id)
            }
        }
        else {
            flash.message = "Supervisor not found "
            redirect(action: list)
        }
    }

    @Secured(value = Permission.UPDATE_EMPLOYEE_SUP)
    def edit = {
        EmployeeSupervisor employeeSupervisor = EmployeeSupervisor.get(params.id)

        if (!employeeSupervisor) {
            flash.message = "Supervisor not found."
            redirect(action: list)
        }
        else {
            return [employeeSupervisor: createEmployeeSupervisorCO(employeeSupervisor)]
        }
    }

    @Secured(value = Permission.UPDATE_EMPLOYEE_SUP)
    def update = {EmployeeSupervisorCO employeeSupervisorCO ->
        if (!employeeSupervisorCO.hasErrors()) {
            EmployeeSupervisor employeeSupervisor = createEmployeeSupervisorObject(employeeSupervisorCO)
            if (employeeSupervisor && employeeSupervisor.person.s() && employeeSupervisor.s()) {
                flash.message = "Supervisor ${employeeSupervisor} updated successfully."
                redirect(action: show, id: employeeSupervisor.id)
            } else {
                flash.message = "Supervisor can not be updated."
            }
        }
        render(view: 'edit', model: ['employeeSupervisor': employeeSupervisorCO])
    }

    @Secured(value = Permission.CREATE_EMPLOYEE_SUP)
    def create = {
        EmployeeSupervisorCO employeeSupervisorCO = new EmployeeSupervisorCO()
        render(view: 'create', model: ['employeeSupervisor': employeeSupervisorCO])
    }

    def save = {EmployeeSupervisorCO employeeSupervisorCO ->
        if (ConfigurationHolder.config.isEmployeeEditable == 'false') {
            HrInfo hrInfo = HrInfo.findBySlid(params.slid ?: "")
            if (hrInfo) {
                EmployeeSupervisor employeeSupervisor = employeeUtilService.findOrCreateEmployeeSupervisor(hrInfo.slid)
                flash.message = "Supervisor ${employeeSupervisor} saved successfully."
                redirect(action: show, id: employeeSupervisor.id)
            }
        } else {
            if (!employeeSupervisorCO.hasErrors()) {
                EmployeeSupervisor employeeSupervisor = createEmployeeSupervisorObject(employeeSupervisorCO)
                if (employeeSupervisor && employeeSupervisor.person.s() && employeeSupervisor.s()) {
                    HrInfo hrInfo = HrInfo.findBySlid(employeeSupervisorCO.slid)
                    if (hrInfo) {
                        employeeUtilService.findOrCreateEmployeeFromHrInfo(hrInfo)
                    }
                    flash.message = "Supervisor ${employeeSupervisor} saved successfully."
                    redirect(action: show, id: employeeSupervisor.id)
                }
            }
        }
        render(view: 'create', model: ['employeeSupervisor': employeeSupervisorCO, 'fail': 'true'])
    }


    EmployeeSupervisorCO createEmployeeSupervisorCO(EmployeeSupervisor employeeSupervisor) {
        EmployeeSupervisorCO employeeSupervisorCO = new EmployeeSupervisorCO()
        employeeSupervisorCO.with {
            id = employeeSupervisor?.id
            firstName = employeeSupervisor?.person?.firstName
            lastName = employeeSupervisor?.person?.lastName
            middleName = employeeSupervisor?.person?.middleName
            phone = employeeSupervisor?.person?.phone
            notes = employeeSupervisor?.person?.notes
            slid = employeeSupervisor?.person?.slid
        }
        return employeeSupervisorCO
    }

    EmployeeSupervisor createEmployeeSupervisorObject(EmployeeSupervisorCO employeeSupervisorCO) {
        EmployeeSupervisor supervisor
        if (employeeSupervisorCO.id) {
            supervisor = EmployeeSupervisor.get(employeeSupervisorCO.id?.toLong())
        } else {
            supervisor = new EmployeeSupervisor()
        }

        Person person = Person.findBySlid(employeeSupervisorCO.slid)
        supervisor.person = person ? person : supervisor.person
        if (!supervisor.person) {
            supervisor.person = new Person()
        }

        supervisor.person.firstName = employeeSupervisorCO?.firstName
        supervisor.person.lastName = employeeSupervisorCO?.lastName
        supervisor.person.middleName = employeeSupervisorCO?.middleName
        supervisor.person.phone = employeeSupervisorCO?.phone
        supervisor.person.notes = employeeSupervisorCO?.notes
        supervisor.person.slid = employeeSupervisorCO?.slid
        return supervisor
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

class EmployeeSupervisorCO {
    String id
    String firstName
    String middleName
    String lastName
    String phone
    String notes
    String slid


    static constraints = {
        firstName(blank: false)
        middleName(blank: true)
        lastName(blank: false)
        phone(blank: false)
        notes(blank: true)
        slid(blank: false, validator: {val, obj ->
            if (val) {
                Person person = Person.findBySlid(obj.slid)
                EmployeeSupervisor employeeSupervisor
                if (person) {
                    employeeSupervisor = EmployeeSupervisor.findByPerson(person)
                    if (employeeSupervisor && (employeeSupervisor?.id != obj?.id?.toLong())) {
                        return "employeeSupervisor.person.unique.error"
                    }
                }
            }
        })

    }
}