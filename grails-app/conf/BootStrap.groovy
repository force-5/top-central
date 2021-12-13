import com.force5solutions.care.cc.Course
import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.common.CentralMessageTemplate
import com.force5solutions.care.common.MetaClassHelper
import com.force5solutions.care.common.SessionUtils
import com.force5solutions.care.cp.ConfigProperty
import com.force5solutions.care.ldap.TopUser
import com.force5solutions.care.workflow.CentralWorkflowTask
import com.force5solutions.care.workflow.CentralWorkflowTaskPermittedSlid
import com.force5solutions.care.workflow.CentralWorkflowType
import com.force5solutions.care.workflow.WorkflowTaskStatus
import grails.util.Environment
import grails.util.GrailsUtil
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.feed.FeedRun
import com.force5solutions.care.ldap.PermissionLevel
import com.force5solutions.care.common.CannedResponse
import com.force5solutions.care.cc.CcEntitlementRole

class BootStrap {

    def fixtureLoader
    def patchService;

    private static final log = LogFactory.getLog(this)
    def init = { servletContext ->
        MetaClassHelper.enrichClasses();

        ConfigurationHolder.config.bootStrapMode = true

        // For all environments
        bootStrapMasterData()

        log.debug "Bootstrap Environment: " + GrailsUtil.environment
        if ((Environment.currentEnvironment != Environment.PRODUCTION) && (Environment.currentEnvironment.getName() != "qa") && (Environment.currentEnvironment.getName() != "demo")) {
            bootstrapDummyData()
        }

        if (!CannedResponse.count()) {
            fixtureLoader.load "cannedResponses"
        }

        if (Environment.currentEnvironment == Environment.TEST) {
            fixtureLoader.load "entitlementRoles"
        }

        ConfigurationHolder.config.bootStrapMode = false
        SessionUtils.setSession(null)

        // One time for new build
        if (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_GENERAL)) {
            patchService.applyPatch()
        }
        patchForAccessVerificationCompletedSlids()

        fixtureLoader.load "customTags"
        fixtureLoader.load "messageTemplates"
        fixtureLoader.load "centralWorkflowTaskTemplates"

        renameFeedNames()
        deleteCanAccessDashboardPermissionLevel()
        deleteDashboardWithOutLinksPermissionLevel()
        markTheDeletedFromApsInCcEntitlementRoleAsFalse()
        setEntitlementRolesInConfig()
    }

    def setEntitlementRolesInConfig() {
        List<CcEntitlementRole> roles = CcEntitlementRole.listUndeleted()
        if (roles) {
            ConfigProperty physical = ConfigProperty.findByName('physicalEntitlementRoleId') ?: new ConfigProperty(name: 'physicalEntitlementRoleId')
            physical.value = roles.find { it.name.contains('Physical') && !it.name.contains('Cyber') }?.id
            physical.s()
            ConfigProperty electronic = ConfigProperty.findByName('electronicEntitlementRoleId') ?: new ConfigProperty(name: 'electronicEntitlementRoleId')
            electronic.value = roles.find { it.name.contains('Cyber') && !it.name.contains('Physical') }?.id
            electronic.s()
            ConfigProperty both = ConfigProperty.findByName('bothEntitlementRoleId') ?: new ConfigProperty(name: 'bothEntitlementRoleId')
            both.value = roles.find { it.name.contains('Both') || (it.name.contains('Physical') && it.name.contains('Cyber')) }?.id
            both.s()
        }
    }

    private void markTheDeletedFromApsInCcEntitlementRoleAsFalse() {
        CcEntitlementRole.list().findAll { !it.deletedFromAps }.each { CcEntitlementRole ccEntitlementRole ->
            ccEntitlementRole.deletedFromAps = false
            ccEntitlementRole.save(flush: true)
        }
    }

    private void deleteCanAccessDashboardPermissionLevel() {
        PermissionLevel.executeUpdate("delete from PermissionLevel where permission = 'CAN_ACCESS_DASHBOARD'")
    }

    private void deleteDashboardWithOutLinksPermissionLevel() {
        PermissionLevel.executeUpdate("delete from PermissionLevel where permission = 'DASHBOARD_WITHOUT_LINKS'")
    }

    private void renameFeedNames() {
        FeedRun.list().each { FeedRun feedRun ->
            switch (feedRun.feedName) {
                case 'HR Info Feed':
                    feedRun.feedName = CareConstants.FEED_EMPLOYEE_HR_INFO
                    feedRun.s()
                    break;
                case 'Course Feed':
                    feedRun.feedName = CareConstants.FEED_EMPLOYEE_COURSE
                    feedRun.s()
                    break;
                case 'PRA Feed':
                    feedRun.feedName = CareConstants.FEED_EMPLOYEE_PRA
                    feedRun.s()
                    break;
                case 'Active Worker Feed':
                    feedRun.feedName = CareConstants.FEED_ACTIVE_WORKER
                    feedRun.s()
            }
        }
    }

    private def patchForAccessVerificationCompletedSlids() {
        CentralWorkflowTaskPermittedSlid permittedSlid
        List<CentralWorkflowTask> tasks = CentralWorkflowTask.createCriteria().list {
            and {
                eq("nodeName", "Waiting for Review from Supervisor")
                eq("workflowType", CentralWorkflowType.ACCESS_VERIFICATION)
                eq("status", WorkflowTaskStatus.COMPLETE)
                eq("actorSlid", "Central System")
            }
        }
        tasks.each { CentralWorkflowTask task ->
            permittedSlid = CentralWorkflowTaskPermittedSlid.findByTask(task)
            if (permittedSlid) {
                task.actorSlid = permittedSlid.slid
                task.s()
            }
        }
    }

    void bootStrapMasterData() {
        fixtureLoader.load "configProperties"
        if (!TopUser.count()) {
            fixtureLoader.load "master"
        }
    }

    private void bootstrapDummyData() {
        Map mockSession = new HashMap()
        mockSession.isContractorImageUpdated = false
        mockSession.loggedUser = "Test User"
        SessionUtils.setSession(mockSession)

        if (!Course.count()) {
            fixtureLoader.load "dummyData"
        }
    }

    def destroy = {
    }

}

