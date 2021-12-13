package com.force5solutions.care.cc

import com.force5solutions.care.common.CustomPropertyType
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.workflow.WorkflowTaskStatus
import com.force5solutions.care.workflow.CentralWorkflowType

class EntitlementPolicyService {

    boolean transactional = true

    def versioningService
    def centralWorkflowUtilService

    @Transactional(propagation = Propagation.REQUIRED)
    boolean updateCustomProperties(EntitlementPolicy entitlementPolicy, List<String> propertyNames, List<String> propertyTypes, List<Boolean> isRequiredList, List<String> customPropertySize) {
        boolean areCustomPropertiesUpdated = false
        List<CcCustomProperty> propertiesToRemove = entitlementPolicy.customProperties as List
        entitlementPolicy.customProperties = []
        propertiesToRemove.each {CcCustomProperty customProperty ->
            try {
                customProperty.delete(flush: true)
            }
            catch (Exception e) {
                return areCustomPropertiesUpdated
            }
        }
        propertyNames?.eachWithIndex {String name, Integer index ->
            CcCustomProperty customProperty = new CcCustomProperty()
            customProperty.name = name
            customProperty.entitlementPolicy = entitlementPolicy
            customProperty.isRequired = isRequiredList[index]
            customProperty.size = customPropertySize[index]
            customProperty.propertyType = CustomPropertyType.values().find {it.name == propertyTypes[index]}
            entitlementPolicy.addToCustomProperties(customProperty)
        }
        areCustomPropertiesUpdated = true
        return areCustomPropertiesUpdated
    }

    public List<EntitlementPolicy> getUnapprovedEntitlementPolicies() {
        List<EntitlementPolicy> unapprovedEntitlementPolicies = []
        EntitlementPolicy.list([fetch: [standards: 'join', customProperties: 'join']]).each { entitlementPolicy ->
            if (!entitlementPolicy.isApproved) {
                unapprovedEntitlementPolicies << entitlementPolicy
            } else if (entitlementPolicy.isApproved && (versioningService.hasPendingChanges(entitlementPolicy))) {
                unapprovedEntitlementPolicies << entitlementPolicy
            }
        }
        unapprovedEntitlementPolicies
    }

    public Boolean save(EntitlementPolicy entitlementPolicy) {
        String type, description;
        if (entitlementPolicy.id) {
            type = "Update"
        } else {
            type = "Create"
        }
        versioningService.saveVersionableObject(entitlementPolicy, true)
        entitlementPolicy = EntitlementPolicy.findById(entitlementPolicy.id, [cache: false])
        if (type == "Create") {
            if (!entitlementPolicy) {
                entitlementPolicy?.errors?.allErrors?.each { log.error it }
                return false
            }
            centralWorkflowUtilService.startAddEntitlementPolicyWorkflow(entitlementPolicy)
        } else {
            if (versioningService.hasPendingChanges(entitlementPolicy)) {
                triggerUpdateEntitlementPolicyWorkflow(entitlementPolicy)
            }
        }
        if (entitlementPolicy.hasErrors()) {
            entitlementPolicy?.errors?.allErrors?.each { log.error it }
            return false
        }
        return true
    }

    public void triggerUpdateEntitlementPolicyWorkflow(EntitlementPolicy entitlementPolicy) {
        Integer alreadyPendingTaskCount = CentralWorkflowTask.createCriteria().count {
            eq('entitlementPolicyId', entitlementPolicy.id)
            eq('status', WorkflowTaskStatus.NEW)
            inList('workflowType', [CentralWorkflowType.ADD_ENTITLEMENT_POLICY, CentralWorkflowType.UPDATE_ENTITLEMENT_POLICY])
        }
        if (!alreadyPendingTaskCount) {
            centralWorkflowUtilService.startUpdateEntitlementPolicyWorkflow(entitlementPolicy)
        }
    }

}
