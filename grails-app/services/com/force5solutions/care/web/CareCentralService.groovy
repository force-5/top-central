package com.force5solutions.care.web

import com.force5solutions.care.workflow.CentralWorkflowUtilService
import com.force5solutions.care.cc.CcEntitlementRole

import com.force5solutions.care.cc.WorkerEntitlementRole

import java.beans.XMLDecoder
import com.force5solutions.care.cc.CcEntitlement
import com.force5solutions.care.workflow.CentralWorkflowTask
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.plugins.versionable.VersioningContext
import com.force5solutions.care.workflow.WorkflowTaskStatus
import com.force5solutions.care.cc.EntitlementPolicy
import java.text.SimpleDateFormat
import com.force5solutions.care.cc.Worker
import com.force5solutions.care.cc.Employee
import com.force5solutions.care.cc.Certification
import com.force5solutions.care.workflow.TriggerProvisionerDeprovisionerTaskOnRoleUpdateVO
import com.force5solutions.care.cc.EntitlementRoleAccessStatus
import com.force5solutions.care.workflow.AbortCentralWorkflow

class CareCentralService {

    def securityService
    def workerEntitlementRoleService
    def versioningService

    boolean transactional = true
    static expose = ['xfire']

    void changeWorkflowTaskStatusToPending(String username, String password, long taskId) {
        validateUser(username, password)
        CentralWorkflowTask task = CentralWorkflowTask.get(taskId)
        task.status = WorkflowTaskStatus.PENDING
        task.s()
    }

    boolean triggerProvisionerDeprovisionerTaskOnRoleUpdateWorkflowInCentral(String username, String password, TriggerProvisionerDeprovisionerTaskOnRoleUpdateVO taskOnRoleUpdateVO) {
        validateUser(username, password)
        try {
            workerEntitlementRoleService.changeEntitlementRoleStatus(taskOnRoleUpdateVO.workerEntitlementRoleId, EntitlementRoleAccessStatus.PENDING_PROVISIONER_DEPROVISIONER_TASKS_ON_ROLE_UPDATE)
            CentralWorkflowUtilService.triggerProvisionerDeprovisionerTaskOnRoleUpdateWorkflow(taskOnRoleUpdateVO.workerEntitlementRoleId, taskOnRoleUpdateVO.guid)
        } catch (Throwable t) {
            t.printStackTrace()
            return false
        }
        return true
    }

    boolean processEntitlementManagerResponse(String username, String password, long taskId, String response) {
        VersioningContext.set(UUID.randomUUID().toString())
        validateUser(username, password)
        CentralWorkflowTask task = CentralWorkflowTask.get(taskId);
        XMLDecoder xmlDecoder = new XMLDecoder(new ByteArrayInputStream(response.getBytes()));
        Map responseElements = xmlDecoder.readObject() as Map
        CentralWorkflowUtilService.sendResponseElements(task, responseElements)
        return true
    }

    boolean createEntitlementRole(String username, String password, EntitlementRoleDTO entitlementRoleDTO) {
        validateUser(username, password)
        try {
            CcEntitlementRole.upsert(entitlementRoleDTO)
        } catch (Throwable t) {
            t.printStackTrace()
            return false
        }
        return true
    }

    boolean createEntitlement(String username, String password, EntitlementDTO entitlementDTO) {
        validateUser(username, password)
        CcEntitlement entitlement = CcEntitlement.upsert(entitlementDTO)
        if (!entitlement) {
            return false
        }
        return true
    }

    String getEntitlementRoleId(String username, String password, long workerEntitlementRoleId) {
        validateUser(username, password)
        def entitlementRoleId = WorkerEntitlementRole.get(workerEntitlementRoleId).entitlementRole.id
        return entitlementRoleId
    }

    public void validateUser(String username, String password) {
        username = username?.toUpperCase()
        List<String> permittedSlids = ConfigurationHolder.config.webServiceSlids ? ConfigurationHolder.config.webServiceSlids.toUpperCase().tokenize(', ')?.findAll {it} : []
        if (!((username in permittedSlids) && securityService.isValidUser(username, password))) {
            throw new InvalidLoginException("Invalid username/password")
        }
    }

    WorkerEntitlementRoleDTO getWorkerEntitlementRole(String username, String password, long workerEntitlementRoleId) {
        validateUser(username, password)
        WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(workerEntitlementRoleId)
        if (workerEntitlementRole) {
            WorkerEntitlementRoleDTO workerEntitlementRoleDTO = new WorkerEntitlementRoleDTO(id: workerEntitlementRole.id, workerName: workerEntitlementRole.worker.name)
            return workerEntitlementRoleDTO
        }
        return null
    }

    String getRequiredCertificationIdsForEntitlementPolicyOnAGiveDate(String username, String password, Long entitlementPolicyId, String dateString, Long workerId) {
        validateUser(username, password)
        Set<Certification> requiredCertifications = []
        Worker worker = Worker.get(workerId)
        EntitlementPolicy entitlementPolicy = EntitlementPolicy.get(entitlementPolicyId)
        Date date = new SimpleDateFormat('MM/dd/yyyy hh:mm a').parse(dateString)
        entitlementPolicy = versioningService.getObjectOnDate(entitlementPolicy, date) as EntitlementPolicy
        requiredCertifications = entitlementPolicy.requiredCertifications + ((worker instanceof Employee) ? (entitlementPolicy.requiredCertificationsForEmployee) : (entitlementPolicy.requiredCertificationsForContractor))
        return requiredCertifications ? requiredCertifications*.id.join(',') : ''
    }

    public void markWorkflowAsAborted(String username, String password, String workflowGuid) {
        validateUser(username, password)
        AbortCentralWorkflow workflowToBeAborted = AbortCentralWorkflow.findByWorkflowGuidAndIsAborted(workflowGuid, false)
        workflowToBeAborted.isAborted = true
        workflowToBeAborted.s()
    }

    public boolean markCcEntitlementRoleAsDeleted(String username, String password, String ccEntitlementRoleId) {
        validateUser(username, password)
        boolean status = false
        try {
            CcEntitlementRole ccEntitlementRole = CcEntitlementRole.findById(ccEntitlementRoleId)
            ccEntitlementRole.deletedFromAps = true
            ccEntitlementRole.s()
            status = true
        } catch (Exception e) {
            e.printStackTrace()
        }
        return status
    }
}

