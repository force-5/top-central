package com.force5solutions.care.cc

class CentralWorkflowTaskTemplateController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def centralUtilService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        Integer offset = params.offset ? params.offset.toInteger() : 0
        def maxRes = centralUtilService.updateAndGetDefaultSizeOfListViewInConfig(params)
        params.order = params.order ?: 'asc'
        params.sort = params.sort ?: 'id'
        List<CentralWorkflowTaskTemplate> centralWorkflowTaskTemplateList = CentralWorkflowTaskTemplate.list([order: params.order, sort: params.sort])
        List<CentralWorkflowTaskTemplate> filteredTemplateList = []
        centralWorkflowTaskTemplateList.each {
            if (!(it in (centralWorkflowTaskTemplateList*.escalationTemplate))) {
                filteredTemplateList.add(it)
            }
        }
        filteredTemplateList.remove {it.id.equalsIngoreCase("INITIAL_TASK_TEMPLATE")}
        filteredTemplateList.removeAll {((it?.actorApplicationRoles?.size() == 0) && (it?.toNotificationApplicationRoles?.size() == 0) && (it?.ccNotificationApplicationRoles?.size() == 0))}

        def filteredTemplateListCount = null
        if (!maxRes.toString()?.equalsIgnoreCase('Unlimited')) {
            filteredTemplateListCount = filteredTemplateList.size()
            if (filteredTemplateListCount) {
                Integer lastIndex = offset + maxRes - 1
                if (lastIndex >= filteredTemplateListCount) {
                    lastIndex = filteredTemplateListCount - 1
                }
                filteredTemplateList = filteredTemplateList.getAt(offset..lastIndex)
            }
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render(template: 'centralWorkflowTaskTemplatesTable', model: [filteredTemplateList: filteredTemplateList, filteredTemplateListCount: filteredTemplateListCount, offset: offset, max: maxRes, order: params.order, sort: params.sort])
        } else {
            [filteredTemplateList: filteredTemplateList, filteredTemplateListCount: filteredTemplateListCount, offset: offset, max: maxRes, order: params.order, sort: params.sort]
        }
    }

    def listAll = {
        render(view: 'list', model: [filteredTemplateList: CentralWorkflowTaskTemplate.list()])
    }

    def create = {
        def centralWorkflowTaskTemplateInstance = new CentralWorkflowTaskTemplate()
        centralWorkflowTaskTemplateInstance.properties = params
        return [centralWorkflowTaskTemplateInstance: centralWorkflowTaskTemplateInstance]
    }

    def save = {
        def centralWorkflowTaskTemplateInstance = new CentralWorkflowTaskTemplate()
        bindData(centralWorkflowTaskTemplateInstance, params, ['actorApplicationRoles', 'toNotificationApplicationRoles', 'ccNotificationApplicationRoles', 'periodUnit'])

        if (params.actorApplicationRoles) {
            List<String> roles = (params.actorApplicationRoles as String)?.replace('[', '')?.replace(']', '')?.tokenize(',')*.trim()*.toString()
            roles = roles.findAll {it.length()}
            roles.each {
                CentralApplicationRole centralApplicationRole = CentralApplicationRole.get(it)
                centralWorkflowTaskTemplateInstance.addToActorApplicationRoles(centralApplicationRole)
            }
        }

        if (params.toNotificationApplicationRoles) {
            List<String> roles = (params.toNotificationApplicationRoles as String)?.replace('[', '')?.replace(']', '')?.tokenize(',')*.trim()*.toString()
            roles = roles.findAll {it.length()}
            roles.each {
                CentralApplicationRole centralApplicationRole = CentralApplicationRole.get(it)
                centralWorkflowTaskTemplateInstance.addToToNotificationApplicationRoles(centralApplicationRole)
            }
        }

        if (params.ccNotificationApplicationRoles) {
            List<String> roles = (params.ccNotificationApplicationRoles as String)?.replace('[', '')?.replace(']', '')?.tokenize(',')*.trim()*.toString()
            roles = roles.findAll {it.length()}
            roles.each {
                CentralApplicationRole centralApplicationRole = CentralApplicationRole.get(it)
                centralWorkflowTaskTemplateInstance.addToCcNotificationApplicationRoles(centralApplicationRole)
            }
        }

        if (params.periodUnit) {
            List<String> periodUnits = (params.periodUnit as String)?.replace('[', '')?.replace(']', '')?.tokenize(',')*.trim()*.toString()
            periodUnits.each {
                PeriodUnit unit = PeriodUnit.get(it)
                centralWorkflowTaskTemplateInstance.periodUnit = unit
            }
        }

        centralWorkflowTaskTemplateInstance.id = params.templateName

        if (centralWorkflowTaskTemplateInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'centralWorkflowTaskTemplate.label', default: 'CentralWorkflowTaskTemplate'), centralWorkflowTaskTemplateInstance.id])}"
            redirect(action: "show", id: centralWorkflowTaskTemplateInstance.id)
        }
        else {
            render(view: "create", model: [centralWorkflowTaskTemplateInstance: centralWorkflowTaskTemplateInstance])
        }
    }

    def show = {
        def centralWorkflowTaskTemplateInstance = CentralWorkflowTaskTemplate.findById(params.id)
        if (!centralWorkflowTaskTemplateInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'centralWorkflowTaskTemplate.label', default: 'CentralWorkflowTaskTemplate'), params.id])}"
            redirect(action: "list")
        }
        else {
            [centralWorkflowTaskTemplateInstance: centralWorkflowTaskTemplateInstance]
        }
    }

    def edit = {
        def centralWorkflowTaskTemplateInstance = CentralWorkflowTaskTemplate.findById(params.id)
        if (!centralWorkflowTaskTemplateInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'centralWorkflowTaskTemplate.label', default: 'CentralWorkflowTaskTemplate'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [centralWorkflowTaskTemplateInstance: centralWorkflowTaskTemplateInstance]
        }
    }

    def update = {
        def centralWorkflowTaskTemplateInstance = CentralWorkflowTaskTemplate.findById(params.templateName)
        if (centralWorkflowTaskTemplateInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (centralWorkflowTaskTemplateInstance.version > version) {

                    centralWorkflowTaskTemplateInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'centralWorkflowTaskTemplate.label', default: 'CentralWorkflowTaskTemplate')] as Object[], "Another user has updated this CentralWorkflowTaskTemplate while you were editing")
                    render(view: "edit", model: [centralWorkflowTaskTemplateInstance: centralWorkflowTaskTemplateInstance])
                    return
                }
            }

            centralWorkflowTaskTemplateInstance.actorSecurityRoles.clear()
            bindData(centralWorkflowTaskTemplateInstance, params, ['actorApplicationRoles', 'toNotificationApplicationRoles', 'ccNotificationApplicationRoles', 'periodUnit'])
            centralWorkflowTaskTemplateInstance.actorApplicationRoles.clear()
            centralWorkflowTaskTemplateInstance.toNotificationApplicationRoles.clear()
            centralWorkflowTaskTemplateInstance.ccNotificationApplicationRoles.clear()

            if (params.actorApplicationRoles) {
                List<String> roles = (params.actorApplicationRoles as String)?.replace('[', '')?.replace(']', '')?.tokenize(',')*.trim()*.toString()
                roles = roles.findAll {it.length()}
                roles.each {
                    CentralApplicationRole centralApplicationRole = CentralApplicationRole.get(it)
                    centralWorkflowTaskTemplateInstance.addToActorApplicationRoles(centralApplicationRole)
                }
            }

            if (params.toNotificationApplicationRoles) {
                List<String> roles = (params.toNotificationApplicationRoles as String)?.replace('[', '')?.replace(']', '')?.tokenize(',')*.trim()*.toString()
                roles = roles.findAll {it.length()}
                roles.each {
                    CentralApplicationRole centralApplicationRole = CentralApplicationRole.get(it)
                    centralWorkflowTaskTemplateInstance.addToToNotificationApplicationRoles(centralApplicationRole)
                }
            }

            if (params.ccNotificationApplicationRoles) {
                List<String> roles = (params.ccNotificationApplicationRoles as String)?.replace('[', '')?.replace(']', '')?.tokenize(',')*.trim()*.toString()
                roles = roles.findAll {it.length()}
                roles.each {
                    CentralApplicationRole centralApplicationRole = CentralApplicationRole.get(it)
                    centralWorkflowTaskTemplateInstance.addToCcNotificationApplicationRoles(centralApplicationRole)
                }
            }

            if (params.periodUnit) {
                List<String> periodUnits = (params.periodUnit as String)?.replace('[', '')?.replace(']', '')?.tokenize(',')*.trim()*.toString()
                periodUnits.each {
                    PeriodUnit unit = PeriodUnit.get(it)
                    centralWorkflowTaskTemplateInstance.periodUnit = unit
                }
            }

            centralWorkflowTaskTemplateInstance.id = params.templateName

            if (!centralWorkflowTaskTemplateInstance.hasErrors() && centralWorkflowTaskTemplateInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'centralWorkflowTaskTemplate.label', default: 'CentralWorkflowTaskTemplate'), centralWorkflowTaskTemplateInstance.id])}"
                redirect(action: "show", id: centralWorkflowTaskTemplateInstance.id)
            }
            else {
                render(view: "edit", model: [centralWorkflowTaskTemplateInstance: centralWorkflowTaskTemplateInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'centralWorkflowTaskTemplate.label', default: 'CentralWorkflowTaskTemplate'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def centralWorkflowTaskTemplateInstance = CentralWorkflowTaskTemplate.get(params.id)
        if (centralWorkflowTaskTemplateInstance) {
            try {
                centralWorkflowTaskTemplateInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'centralWorkflowTaskTemplate.label', default: 'CentralWorkflowTaskTemplate'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'centralWorkflowTaskTemplate.label', default: 'CentralWorkflowTaskTemplate'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'centralWorkflowTaskTemplate.label', default: 'CentralWorkflowTaskTemplate'), params.id])}"
            redirect(action: "list")
        }
    }

    def cloneTemplate = {
        def centralWorkflowTaskTemplate = CentralWorkflowTaskTemplate.findById(params.id)
        CentralWorkflowTaskTemplate clonedCentralWorkflowTaskTemplate = new CentralWorkflowTaskTemplate()
        clonedCentralWorkflowTaskTemplate.properties = centralWorkflowTaskTemplate.properties
        clonedCentralWorkflowTaskTemplate.id = "CLONE_OF_" + centralWorkflowTaskTemplate.id
        render(view: 'create', model: [centralWorkflowTaskTemplateInstance: clonedCentralWorkflowTaskTemplate])
    }
}
