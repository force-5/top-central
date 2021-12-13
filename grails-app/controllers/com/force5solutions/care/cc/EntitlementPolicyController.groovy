package com.force5solutions.care.cc

import com.force5solutions.care.common.CustomPropertyType
import com.force5solutions.care.common.Secured
import com.force5solutions.care.ldap.Permission

class EntitlementPolicyController {

    def entitlementPolicyService
    def centralUtilService
    def versioningService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(value = Permission.READ_ENTITLEMENT_POLICY)
    def list = {
        List<EntitlementPolicy> entitlementPolicyList = []
        Integer entitlementPolicyTotal = null
        params.order = params.order ?: 'asc'
        params.sort = params.sort ?: 'name'
        Integer offset = params.offset ? params.offset.toInteger() : 0
        def maxRes = centralUtilService.updateAndGetDefaultSizeOfListViewInConfig(params)
        if (!maxRes?.toString()?.equalsIgnoreCase('Unlimited')) {
            entitlementPolicyList = EntitlementPolicy.findAllByIsApproved(true)
            entitlementPolicyTotal = EntitlementPolicy.findAllByIsApproved(true).size()
            if (entitlementPolicyTotal) {
                Integer lastIndex = offset + maxRes - 1
                if (lastIndex >= entitlementPolicyTotal) {
                    lastIndex = entitlementPolicyTotal - 1
                }
                entitlementPolicyList = entitlementPolicyList.getAt(offset..lastIndex)
            }
        } else {
            entitlementPolicyList = EntitlementPolicy.findAllByIsApproved(true)
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render template: 'entitlementPoliciesTable', model: [entitlementPolicyList: entitlementPolicyList, entitlementPolicyTotal: entitlementPolicyTotal, offset: offset, max: maxRes, order: params.order, sort: params.sort]
        } else {
            [entitlementPolicyList: entitlementPolicyList, entitlementPolicyTotal: entitlementPolicyTotal, offset: offset, max: maxRes, order: params.order, sort: params.sort]
        }
    }

    @Secured(value = Permission.CREATE_ENTITLEMENT_POLICY)
    def create = {
        def entitlementPolicy = new EntitlementPolicy()
        return [entitlementPolicy: entitlementPolicy]
    }

    def save = {
        def entitlementPolicy = new EntitlementPolicy(params)
        List<String> propertyNames = (params.propertyName) ? params.list("propertyName") : []
        List<String> propertyTypes = (params.customPropertyType) ? params.list("customPropertyType") : []
        List<Boolean> isRequiredList = (params.isCustomPropertyRequired) ? params.list("isCustomPropertyRequired")*.toBoolean() : []
        List<String> customPropertySize = (params.customPropertySize) ? params.list("customPropertySize") : []
        propertyNames.eachWithIndex {String name, Integer index ->
            CcCustomProperty customProperty = new CcCustomProperty()
            customProperty.name = name
            customProperty.entitlementPolicy = entitlementPolicy
            customProperty.size = customPropertySize[index]
            customProperty.isRequired = isRequiredList[index]
            customProperty.propertyType = CustomPropertyType.values().find {it.name == propertyTypes[index]}
            entitlementPolicy.addToCustomProperties(customProperty)
        }
        List<String> standards = (params.standardName) ? params.list("standardName") : []
        standards.each {
            String standard = new String(it)
            entitlementPolicy.addToStandards(standard)
        }
        if (entitlementPolicyService.save(entitlementPolicy)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'entitlementPolicy.label', default: 'Entitlement Policy'), entitlementPolicy.name])}"
            redirect(action: "show", id: entitlementPolicy.id)
        }
        else {
            render(view: "create", model: [entitlementPolicy: entitlementPolicy])
        }
    }

    @Secured(value = Permission.READ_ENTITLEMENT_POLICY)
    def show = {
        def entitlementPolicy = EntitlementPolicy.get(params.id)
        if (!entitlementPolicy) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entitlementPolicy.label', default: 'Entitlement Policy'), params.id])}"
            redirect(action: "list")
        }
        else {
            Boolean isEditDisabled = true
            List<CcEntitlement> entitlements = CcEntitlement.findAllByType(entitlementPolicy)
            if (entitlements) {
                isEditDisabled = false
            }
            [entitlementPolicy: entitlementPolicy, isEditDisabled: isEditDisabled]
        }
    }

    @Secured(value = Permission.UPDATE_ENTITLEMENT_POLICY)
    def edit = {
        def entitlementPolicy = EntitlementPolicy.findById(params.id, [fetch: [standards: 'join', customProperties: 'join']])
        entitlementPolicy = versioningService.getCurrentObject(entitlementPolicy)
        if (!entitlementPolicy) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entitlementPolicy.label', default: 'Entitlement Policy'), params.id])}"
            redirect(action: "list")
        }
        else {
            Boolean isEditDisabled = true
            List<CcEntitlement> entitlements = CcEntitlement.findAllByType(entitlementPolicy)
            if (entitlements) {
                isEditDisabled = false
            }
            return [entitlementPolicy: entitlementPolicy, isEditDisabled: isEditDisabled]
        }
    }

    public void refreshEntitlementPolicy(EntitlementPolicy entitlementPolicy) {
        if (!entitlementPolicy.isAttached()) {
            try {
                entitlementPolicy.attach()
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
        entitlementPolicy?.name
        entitlementPolicy?.requiredCertifications?.each {it?.name}
        entitlementPolicy?.requiredCertificationsForEmployee?.each {it?.name}
        entitlementPolicy?.requiredCertificationsForContractor?.each {it?.name}
        entitlementPolicy?.standards
        entitlementPolicy?.isApproved
        entitlementPolicy?.customProperties?.each {it?.name}
        entitlementPolicy?.dateCreated
        entitlementPolicy?.lastUpdated
    }


    @Secured(value = Permission.UPDATE_ENTITLEMENT_POLICY)
    def update = {
        List requiredCertifications = params.requiredCertifications ? Certification.getAll(params.list('requiredCertifications')) : []
        List requiredCertificationsForEmployee = params.requiredCertificationsForEmployee ? Certification.getAll(params.list('requiredCertificationsForEmployee')) : []
        List requiredCertificationsForContractor = params.requiredCertificationsForContractor ? Certification.getAll(params.list('requiredCertificationsForContractor')) : []
        params.remove('requiredCertifications')
        params.remove('requiredCertificationsForEmployee')
        params.remove('requiredCertificationsForContractor')
        EntitlementPolicy entitlementPolicy = EntitlementPolicy.get(params.id)
        refreshEntitlementPolicy(entitlementPolicy)
        if (entitlementPolicy) {
            if (params.version) {
                def version = params.version.toLong()
                if (entitlementPolicy.version > version) {
                    entitlementPolicy.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'entitlementPolicy.label', default: 'Entitlement Policy')] as Object[], "Another user has updated this EntitlementPolicy while you were editing")
                    render(view: "edit", model: [entitlementPolicy: entitlementPolicy])
                    return
                }
            }
            entitlementPolicy.properties = params
            entitlementPolicy.requiredCertifications = requiredCertifications
            entitlementPolicy.requiredCertificationsForEmployee = requiredCertificationsForEmployee
            entitlementPolicy.requiredCertificationsForContractor = requiredCertificationsForContractor
            List<String> propertyNames = (params.propertyName) ? params.list("propertyName") : []
            List<String> propertyTypes = (params.customPropertyType) ? params.list("customPropertyType") : []
            List<Boolean> isRequiredList = (params.isCustomPropertyRequired) ? params.list("isCustomPropertyRequired")*.toBoolean() : []
            List<String> customPropertySize = (params.customPropertySize) ? params.list("customPropertySize") : []
            boolean isCustomPropertiesUpdated = true
            entitlementPolicy.name = params.name
            if (isCustomPropertiesUpdated) {
                entitlementPolicy.standards = []
                List<String> standards = (params.standardName) ? params.list("standardName") : []
                standards.each {
                    String standard = new String(it)
                    entitlementPolicy.addToStandards(standard)
                }
                entitlementPolicyService.save(entitlementPolicy)
                if (!entitlementPolicy.hasErrors()) {
                    isCustomPropertiesUpdated = entitlementPolicyService.updateCustomProperties(entitlementPolicy, propertyNames, propertyTypes, isRequiredList, customPropertySize)
                    if (isCustomPropertiesUpdated) {
                        flash.message = "${message(code: 'default.updated.message', args: [message(code: 'entitlementPolicy.label', default: 'Entitlement Policy'), entitlementPolicy.name])}"
                        if (versioningService.hasPendingChanges(entitlementPolicy)) {
                            redirect(action: "showUnapprovedChanges", id: entitlementPolicy.id)
                        } else {
                            redirect(action: "show", id: entitlementPolicy.id)
                        }
                    } else {
                        flash.message = "${message(code: 'entitlementPolicy.customProperties.referenced.label', default: 'Entitlement Policy')}"
                        render(view: "edit", model: [entitlementPolicy: entitlementPolicy])
                    }
                }
                else {
                    render(view: "edit", model: [entitlementPolicy: entitlementPolicy])
                }
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entitlementPolicy.label', default: 'Entitlement Policy'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(value = Permission.UPDATE_ENTITLEMENT_POLICY)
    def resubmitApprovalRequest = {
        def entitlementPolicy = EntitlementPolicy.findById(params.id, [fetch: [standards: 'join', customProperties: 'join']])
        if (entitlementPolicy) {
            entitlementPolicyService.triggerUpdateEntitlementPolicyWorkflow(entitlementPolicy)
        }
        redirect(action: 'unapprovedEntitlementPolicies')
    }

    @Secured(value = Permission.DELETE_ENTITLEMENT_POLICY)
    def delete = {
        def entitlementPolicy = EntitlementPolicy.get(params.id)
        String name = entitlementPolicy.name
        if (entitlementPolicy) {
            try {
                entitlementPolicy.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'entitlementPolicy.label', default: 'Entitlement Policy'), name])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'entitlementPolicy.label', default: 'Entitlement Policy'), name])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'entitlementPolicy.label', default: 'Entitlement Policy'), name])}"
            redirect(action: "list")
        }
    }

    def getCustomProperties = {
        String entitlementId = params?.entitlementPolicyId
        if (entitlementId) {
            EntitlementPolicy entitlementPolicy = EntitlementPolicy.get(entitlementId?.toLong())
            List<CcCustomProperty> customProperties = entitlementPolicy?.customProperties
            List<CcCustomPropertyValue> customPropertyValues = []
            customProperties.each {
                customPropertyValues.add(new CcCustomPropertyValue(customProperty: it))
            }
            render(template: '/customProperty/customPropertiesByEntitlement', model: [customPropertyValues: customPropertyValues])
        }
        else {
            render ""
        }
    }
    def unapprovedEntitlementPolicies = {
        List<EntitlementPolicy> currentEntitlementPolicies = entitlementPolicyService.getUnapprovedEntitlementPolicies()
        currentEntitlementPolicies.each {
            it = versioningService.getCurrentObject(it)
        }
        render(view: 'unapprovedEntitlementPolicies', model: [entitlementPolicyList: currentEntitlementPolicies])
    }

    def showUnapprovedChanges = {
        EntitlementPolicy entitlementPolicy = EntitlementPolicy.findById(params.id, [fetch: [standards: 'join', customProperties: 'join']])
        entitlementPolicy = versioningService.getCurrentObject(entitlementPolicy)
        [entitlementPolicy: entitlementPolicy]
    }
}
