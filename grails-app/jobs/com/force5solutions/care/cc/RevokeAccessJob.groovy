package com.force5solutions.care.cc

import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.ConfigurationHolder

import static com.force5solutions.care.common.CareConstants.*
import com.force5solutions.care.workflow.CentralWorkflowUtilService
import org.grails.plugins.versionable.VersioningContext

class RevokeAccessJob {
    static config = ConfigurationHolder.config

    def concurrent = false
    def workerEntitlementRoleService

    static triggers = {
        cron name: 'revokeAccessCronTrigger',
                group: 'topJobsGroup',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression
    }

    def execute() {
        if (!config.bootStrapMode) {
            if (GrailsUtil.environment != GrailsApplication.ENV_PRODUCTION) {
                log.info "Executing Revoke Access Job at ${new Date()}"
            }


            List<WorkerEntitlementRole> workerEntitlementRoles = (Worker.list()*.entitlementRoles)?.flatten().findAll {it.status == EntitlementRoleAccessStatus.active}

            EntitlementRoleAccessStatus status = EntitlementRoleAccessStatus.pendingRevocation

            workerEntitlementRoles?.each {WorkerEntitlementRole workerEntitlementRole ->
                if (workerEntitlementRole.isPendingCertifications()) {
                    VersioningContext.set(UUID.randomUUID().toString())
                    String revokeJustification = 'Revoke Access Requested (Missing Required Certifications)'
                    workerEntitlementRoleService.changeEntitlementRoleStatus(workerEntitlementRole.id, status, CENTRAL_SYSTEM_USER_ID, revokeJustification)
                    CentralWorkflowUtilService.startRevokeRequestWorkflow(workerEntitlementRole, revokeJustification)
                }
            }
        }

    }
}
