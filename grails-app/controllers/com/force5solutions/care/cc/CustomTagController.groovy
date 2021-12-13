package com.force5solutions.care.cc

import com.force5solutions.care.common.CustomTag

class CustomTagController {

    def centralUtilService

    def index = { redirect(action: list, params: params) }
    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

    def list = {
        List<CustomTag> customTagList = []
        Integer customTagTotal = null
        Integer offset = params.offset ? params.offset.toInteger() : 0
        params.order = params.order ?: 'asc'
        params.sort = params.sort ?: 'id'
        params.max = centralUtilService.updateAndGetDefaultSizeOfListViewInConfig(params)
        if (!params?.max?.toString()?.equalsIgnoreCase('Unlimited')) {
            customTagList = CustomTag.list(params)
            customTagTotal = CustomTag.count()
        } else {
            customTagList = CustomTag.list()
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render template: 'customTagsTable', model: [customTagList: customTagList, customTagTotal: customTagTotal, offset: offset, max: params.max, order: params.order, sort: params.sort]
        } else {
            [customTagList: customTagList, customTagTotal: customTagTotal, offset: offset, max: params.max, order: params.order, sort: params.sort]
        }
    }

    def show = {
        def customTagInstance = CustomTag.get(params.id)

        if (!customTagInstance) {
            flash.message = "CustomTag not found with id ${params.id}"
            redirect(action: list)
        }
        else { return [customTagInstance: customTagInstance] }
    }

    def delete = {
        def customTagInstance = CustomTag.get(params.id)
        if (customTagInstance) {
            try {
                customTagInstance.delete(flush: true)
                flash.message = "CustomTag ${params.id} deleted"
                redirect(action: list)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "CustomTag ${params.id} could not be deleted"
                redirect(action: show, id: params.id)
            }
        }
        else {
            flash.message = "CustomTag not found with id ${params.id}"
            redirect(action: list)
        }
    }

    def edit = {
        def customTagInstance = CustomTag.get(params.id)

        if (!customTagInstance) {
            flash.message = "CustomTag not found with id ${params.id}"
            redirect(action: list)
        }
        else {
            return [customTagInstance: customTagInstance]
        }
    }

    def update = {
        def customTagInstance = CustomTag.get(params.id)
        if (customTagInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (customTagInstance.version > version) {

                    customTagInstance.errors.rejectValue("version", "customTag.optimistic.locking.failure", "Another user has updated this CustomTag while you were editing.")
                    render(view: 'edit', model: [customTagInstance: customTagInstance])
                    return
                }
            }
            customTagInstance.properties = params
            if (!customTagInstance.hasErrors() && customTagInstance.save()) {
                flash.message = "CustomTag ${params.id} updated"
                redirect(action: show, id: customTagInstance.id)
            }
            else {
                render(view: 'edit', model: [customTagInstance: customTagInstance])
            }
        }
        else {
            flash.message = "CustomTag not found with id ${params.id}"
            redirect(action: list)
        }
    }

    def create = {
        def customTagInstance = new CustomTag()
        customTagInstance.properties = params
        return ['customTagInstance': customTagInstance]
    }

    def save = {
        def customTagInstance = new CustomTag(params)
        if (!customTagInstance.hasErrors() && customTagInstance.save()) {
            flash.message = "CustomTag ${customTagInstance.id} created"
            redirect(action: show, id: customTagInstance.id)
        }
        else {
            render(view: 'create', model: [customTagInstance: customTagInstance])
        }
    }
}
