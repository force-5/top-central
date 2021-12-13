package com.force5solutions.care.cc

class CertificationExpirationNotificationTemplateController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [certificationExpirationNotificationTemplateInstanceList: CertificationExpirationNotificationTemplate.list(params), certificationExpirationNotificationTemplateInstanceTotal: CertificationExpirationNotificationTemplate.count()]
    }

    def create = {
        def certificationExpirationNotificationTemplateInstance = new CertificationExpirationNotificationTemplate()
        certificationExpirationNotificationTemplateInstance.properties = params
        return [certificationExpirationNotificationTemplateInstance: certificationExpirationNotificationTemplateInstance]
    }

    def save = {
        def certificationExpirationNotificationTemplateInstance = new CertificationExpirationNotificationTemplate(params)
        if (certificationExpirationNotificationTemplateInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'certificationExpirationNotificationTemplate.label', default: 'CertificationExpirationNotificationTemplate'), certificationExpirationNotificationTemplateInstance.id])}"
            redirect(action: "show", id: certificationExpirationNotificationTemplateInstance.id)
        }
        else {
            render(view: "create", model: [certificationExpirationNotificationTemplateInstance: certificationExpirationNotificationTemplateInstance])
        }
    }

    def show = {
        def certificationExpirationNotificationTemplateInstance = CertificationExpirationNotificationTemplate.get(params.id)
        if (!certificationExpirationNotificationTemplateInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certificationExpirationNotificationTemplate.label', default: 'CertificationExpirationNotificationTemplate'), params.id])}"
            redirect(action: "list")
        }
        else {
            [certificationExpirationNotificationTemplateInstance: certificationExpirationNotificationTemplateInstance]
        }
    }

    def edit = {
        def certificationExpirationNotificationTemplateInstance = CertificationExpirationNotificationTemplate.get(params.id)
        if (!certificationExpirationNotificationTemplateInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certificationExpirationNotificationTemplate.label', default: 'CertificationExpirationNotificationTemplate'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [certificationExpirationNotificationTemplateInstance: certificationExpirationNotificationTemplateInstance]
        }
    }

    def update = {
        def certificationExpirationNotificationTemplateInstance = CertificationExpirationNotificationTemplate.get(params.id)
        if (certificationExpirationNotificationTemplateInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (certificationExpirationNotificationTemplateInstance.version > version) {
                    
                    certificationExpirationNotificationTemplateInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'certificationExpirationNotificationTemplate.label', default: 'CertificationExpirationNotificationTemplate')] as Object[], "Another user has updated this CertificationExpirationNotificationTemplate while you were editing")
                    render(view: "edit", model: [certificationExpirationNotificationTemplateInstance: certificationExpirationNotificationTemplateInstance])
                    return
                }
            }
            certificationExpirationNotificationTemplateInstance.properties = params
            if (!certificationExpirationNotificationTemplateInstance.hasErrors() && certificationExpirationNotificationTemplateInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'certificationExpirationNotificationTemplate.label', default: 'CertificationExpirationNotificationTemplate'), certificationExpirationNotificationTemplateInstance.id])}"
                redirect(action: "show", id: certificationExpirationNotificationTemplateInstance.id)
            }
            else {
                render(view: "edit", model: [certificationExpirationNotificationTemplateInstance: certificationExpirationNotificationTemplateInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certificationExpirationNotificationTemplate.label', default: 'CertificationExpirationNotificationTemplate'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def certificationExpirationNotificationTemplateInstance = CertificationExpirationNotificationTemplate.get(params.id)
        if (certificationExpirationNotificationTemplateInstance) {
            try {
                certificationExpirationNotificationTemplateInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'certificationExpirationNotificationTemplate.label', default: 'CertificationExpirationNotificationTemplate'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'certificationExpirationNotificationTemplate.label', default: 'CertificationExpirationNotificationTemplate'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'certificationExpirationNotificationTemplate.label', default: 'CertificationExpirationNotificationTemplate'), params.id])}"
            redirect(action: "list")
        }
    }
}
