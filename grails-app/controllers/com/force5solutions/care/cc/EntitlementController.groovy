package com.force5solutions.care.cc

import com.force5solutions.care.common.CustomPropertyType
import com.force5solutions.care.common.EntitlementStatus
import com.force5solutions.care.ldap.Permission
import com.force5solutions.care.common.Secured

class EntitlementController {

    def entitlementService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(value = Permission.READ_ENTITLEMENT)
    def list = {
        Integer offset = params.offset ? params.offset.toInteger() : 0
        Integer maxRes = params.max ? params.max.toInteger() : 10
        String orderBy = params.order ?: 'asc'
        String sort = params.sort ?: 'alias'
        def EntitlementList = CcEntitlement.createCriteria().list {
            if (sort == 'origin') {
                origin {
                    order("alias", orderBy)
                }
            }
            else if (sort == 'entitlementPolicy') {
                type {
                    order("alias", orderBy)
                }
            }
            else {
                order(sort, orderBy)
            }
            order("id", orderBy)
        }

        def entitlementTotal = EntitlementList.size()
        if (entitlementTotal) {
            Integer lastIndex = offset + maxRes - 1
            if (lastIndex >= entitlementTotal) {
                lastIndex = entitlementTotal - 1
            }
            EntitlementList = EntitlementList.getAt(offset..lastIndex)
        }
        [entitlementList: EntitlementList, entitlementTotal: entitlementTotal]
    }

    @Secured(value = Permission.CREATE_ENTITLEMENT)
    def create = {
        def entitlement = new CcEntitlement()
        entitlement.properties = params
        return [entitlement: entitlement, statuses: EntitlementStatus.values()]
    }

    def save = {
        if (params.status) {
            params.status = EntitlementStatus."${params.status}"
        }
        def entitlement = new CcEntitlement()
        entitlement.id = UUID.randomUUID().toString()
        if (params.customPropertyId) {
            Long propertyId
            Map customPropertyMap = params.findAll {key, value ->
                (key.startsWith("customPropertyValue")) && (!(key.contains("_")) || (key.contains("_value"))) && (value != 'struct')
            }
            def keys = customPropertyMap.keySet()?.findAll {it.contains("_value")}
            customPropertyMap = customPropertyMap.findAll {!((it.key + "_value") in keys)}

            customPropertyMap.each {String key, value ->
                String t = key - "customPropertyValue";
                def x = t?.split('_') as List
                propertyId = x.first().toLong()
                CcCustomPropertyValue propertyValue = new CcCustomPropertyValue()
                CcCustomProperty customProperty = CcCustomProperty.get(propertyId)
                propertyValue.value = value
                propertyValue.customProperty = customProperty
                entitlement.addToCustomPropertyValues(propertyValue)
            }
        }
        params.remove("customPropertyId")
        List<CcCustomPropertyValue> customPropertyValues = entitlement.customPropertyValues as List
        List<CcCustomProperty> missingValueCustomProperties = []
        customPropertyValues.each {
            if ((it.customProperty.isRequired) && !(it.value)) {
                missingValueCustomProperties.add(it.customProperty)
            }
            if ((it.customProperty.propertyType == CustomPropertyType.NUMBER) && !(it.value.isNumber()) && (it.value)) {
                missingValueCustomProperties.add(it.customProperty)
            }
            if ((it.customProperty.size) && (it.customProperty.propertyType == CustomPropertyType.NUMBER) && (it.value.isNumber()) && (it.value) && (it.customProperty.size.toLong() < it.value.toLong())) {
                missingValueCustomProperties.add(it.customProperty)
            }
        }
        entitlement.properties = params
        if (!missingValueCustomProperties) {
            if (entitlementService.saveEntitlement(entitlement)) {
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'entitlement.label', default: 'Entitlement'), entitlement.alias])}"
                redirect(action: "show", id: entitlement.id)
            }

            else {
                render(view: "create", model: [entitlement: entitlement, statuses: EntitlementStatus.values(), missingCustomProperties: missingValueCustomProperties])
            }
        }
        else {
            render(view: "create", model: [entitlement: entitlement, statuses: EntitlementStatus.values(), missingCustomProperties: missingValueCustomProperties])

        }
    }

    @Secured(value = Permission.READ_ENTITLEMENT)
    def show = {
        def entitlement = CcEntitlement.findById(params.id)
        if (!entitlement) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entitlement.label', default: 'Entitlement'), params.id])}"
            redirect(action: "list")
        }
        else {
            [entitlement: entitlement]
        }
    }

    @Secured(value = Permission.UPDATE_ENTITLEMENT)
    def edit = {
        def entitlement = CcEntitlement.findById(params.id)
        if (!entitlement) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entitlement.label', default: 'Entitlement'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [entitlement: entitlement, statuses: EntitlementStatus.values()]
        }
    }

    @Secured(value = Permission.UPDATE_ENTITLEMENT)
    def update = {
        if (params.status) {
            params.status = EntitlementStatus."${params.status}"
        }
        def entitlement = CcEntitlement.findById(params.id)
        if (entitlement) {
            if (params.version) {
                def version = params.version.toLong()
                if (entitlement.version > version) {

                    entitlement.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'entitlement.label', default: 'Entitlement')] as Object[], "Another user has updated this CcEntitlement while you were editing")
                    render(view: "edit", model: [entitlement: entitlement, statuses: EntitlementStatus.values()])
                    return
                }
            }
            List<CcCustomPropertyValue> propertiesToRemove = entitlement.customPropertyValues as List
            entitlement.customPropertyValues = []
            propertiesToRemove.each {it.delete()}

            if (params.customPropertyId) {
                Long propertyId
                Map customPropertyMap = params.findAll {key, value ->
                    (key.startsWith("customPropertyValue")) && (!(key.contains("_")) || (key.contains("_value"))) && (value != 'struct')
                }
                def keys = customPropertyMap.keySet()?.findAll {it.contains("_value")}
                customPropertyMap = customPropertyMap.findAll {!((it.key + "_value") in keys)}

                customPropertyMap.each {String key, value ->
                    String t = key - "customPropertyValue";
                    def x = t?.split('_') as List
                    propertyId = x.first().toLong()
                    CcCustomPropertyValue propertyValue = new CcCustomPropertyValue()
                    CcCustomProperty customProperty = CcCustomProperty.get(propertyId)
                    propertyValue.value = value
                    propertyValue.customProperty = customProperty
                    entitlement.addToCustomPropertyValues(propertyValue)
                }
            }
            params.remove("customPropertyId")
            List<CcCustomPropertyValue> customPropertyValues = entitlement.customPropertyValues as List
            List<CcCustomProperty> missingValueCustomProperties = []
            customPropertyValues.each {
                if ((it.customProperty.isRequired) && !(it.value)) {
                    missingValueCustomProperties.add(it.customProperty)
                }
                if ((it.customProperty.propertyType == CustomPropertyType.NUMBER) && !(it.value.isNumber()) && (it.value)) {
                    missingValueCustomProperties.add(it.customProperty)
                }
                if ((it.customProperty.size) && (it.customProperty.propertyType == CustomPropertyType.NUMBER) && (it.value.isNumber()) && (it.value) && (it.customProperty.size.toLong() < it.value.toLong())) {
                    missingValueCustomProperties.add(it.customProperty)
                }
            }

            entitlement.properties = params
            if (!missingValueCustomProperties) {

                if (!entitlement.hasErrors() && entitlementService.saveEntitlement(entitlement)) {
                    flash.message = "${message(code: 'default.updated.message', args: [message(code: 'entitlement.label', default: 'Entitlement'), entitlement.alias])}"
                    redirect(action: "show", id: entitlement.id)
                }
                else {
                    render(view: "edit", model: [entitlement: entitlement, statuses: EntitlementStatus.values(), missingValueCustomProperties: missingValueCustomProperties])
                }
            }
            else {
                render(view: "edit", model: [entitlement: entitlement, statuses: EntitlementStatus.values(), missingValueCustomProperties: missingValueCustomProperties])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entitlement.label', default: 'Entitlement'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(value = Permission.DELETE_ENTITLEMENT)
    def delete = {
        def entitlement = CcEntitlement.findById(params.id)
        String alias = entitlement.alias
        if (entitlement) {
            try {
                entitlement.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'entitlement.label', default: 'Entitlement'), alias])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'entitlement.label', default: 'Entitlement'), alias])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entitlement.label', default: 'Entitlement'), alias])}"
            redirect(action: "list")
        }
    }

}
