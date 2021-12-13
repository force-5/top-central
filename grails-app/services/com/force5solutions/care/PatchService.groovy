package com.force5solutions.care

import com.force5solutions.care.cc.CentralWorkflowTaskTemplate
import com.force5solutions.care.cc.TransportType
import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.common.CentralMessageTemplate
import com.force5solutions.care.common.CustomTag
import com.force5solutions.care.cp.ConfigProperty
import com.force5solutions.care.workflow.CentralWorkflowTaskType
import com.force5solutions.care.cc.CentralApplicationRole
import com.force5solutions.care.workflow.CentralWorkflowType

class PatchService {

    boolean transactional = true

    def applyPatch() {
        CustomTag customTag = new CustomTag()
        customTag.with {
            name = "Days Remaining in Certification Expiration"
            displayValue = '###DaysRemainingInCertificationExpiration###'
            value = '${(workerCertification.fudgedExpiry - new Date())}'
            dummyData = "1"
        }
        customTag.s()


        CentralMessageTemplate centralMessageTemplateGeneral = new CentralMessageTemplate()
        centralMessageTemplateGeneral.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_GENERAL
            subjectTemplate = ''' Action Required - SCC CIP Certification Expiration for ###PersonnelNameByCertification### '''

            bodyTemplate = '''
****CIP Credentials for ###PersonnelNameByCertification### will expire in ###DaysRemainingInCertificationExpiration### days.
If he/she does not renew ###PersonnelCertificationName### their access to the Power Supply System Control Center (SCC) will be removed.*** <br/><br/>


Once the required credential has been completed, please provide certificate of completion to the Power Supply Control Center Tech (Mina Soto). <br/> <br/>

Please Note: <br/>
Web based training courses are located on the RSCO website.<br/><br/>

Follow the steps below to directly access the web based trainings:<br/><br/>

Click on the link below and locate the course called CIP Annual CCA Access - SCC and click on the link, then start course<br/><br/>

<a href="http://infpl/Global/policies/bunit/transsub/training.shtml" target="_blank">http://infpl/Global/policies/bunit/transsub/training.shtml</a><br/><br/>

Note: It is very important that you follow the directions in the web based training and that you take and pass the test, otherwise your access to the SCC will be removed.<br/><br/>

-or <br/><br/>

If you are not able to access the training using the steps above, try the following<br/><br/>

1. Access INFPL <br/>
2. Click on Companies and Business Units, then scroll down and click on Transmission/Substation <br/>
3. Locate Areas of Focus on the right hand side of the screen and click on Compliance link<br/>
4. Scroll down and click on FPL Compliance Training <br/>
5 Scroll down and click on CIP Annual CCA Access - SCC <br/>
6. Click on the link labelled Start Course Now <br/>
7. Follow the prompts <br/><br/>

-or <br/><br/>

If you are not able to access the training using the steps above, try the following <br/> <br/>

From the infpl main page search for the course <br/><br/>

<strong>CIP Annual CCA Access - SCC</strong> <br/><br/>

in the search window at the top.<br/>
The result should be in the training section. Follow the prompts to start the course<br/> <br/>

Please note that this is a system generated message and replies will not be accepted.<br/><br/>

"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."
'''
            transportType = TransportType.EMAIL
        }
        centralMessageTemplateGeneral.s()


        CentralMessageTemplate centralMessageTemplateFinal = new CentralMessageTemplate()
        centralMessageTemplateFinal.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_FINAL
            subjectTemplate = ''' [Final] Action Required - SCC CIP Certification Expiration for ###PersonnelNameByCertification### '''

            bodyTemplate = '''
****[Final] CIP Credentials for ###PersonnelNameByCertification### will expire in ###DaysRemainingInCertificationExpiration### days.
If he/she does not renew ###PersonnelCertificationName### their access to the Power Supply System Control Center (SCC) will be removed.*** <br/><br/>


Once the required credential has been completed, please provide certificate of completion to the Power Supply Control Center Tech (Mina Soto). <br/><br/>

Please Note: <br/>
Web based training courses are located on the RSCO website.<br/><br/>

Follow the steps below to directly access the web based trainings:<br/><br/>

Click on the link below and locate the course called CIP Annual CCA Access - SCC and click on the link, then start course <br/><br/>

<a href="http://infpl/Global/policies/bunit/transsub/training.shtml" target="_blank">http://infpl/Global/policies/bunit/transsub/training.shtml</a><br/><br/>

Note: It is very important that you follow the directions in the web based training and that you take and pass the test, otherwise your access to the SCC will be removed.<br/><br/>

-or <br/><br/>

If you are not able to access the training using the steps above, try the following<br/>

1. Access INFPL <br/>
2. Click on Companies and Business Units, then scroll down and click on Transmission/Substation <br/>
3. Locate Areas of Focus on the right hand side of the screen and click on Compliance link<br/>
4. Scroll down and click on FPL Compliance Training <br/>
5 Scroll down and click on CIP Annual CCA Access - SCC <br/>
6. Click on the link labelled Start Course Now <br/>
7. Follow the prompts <br/><br/>

-or <br/><br/>

If you are not able to access the training using the steps above, try the following <br/> <br/>

From the infpl main page search for the course <br/> <br/>

<strong>CIP Annual CCA Access - SCC</strong><br/><br/>

in the search window at the top.<br/>
The result should be in the training section. Follow the prompts to start the course<br/><br/>

Please note that this is a system generated message and replies will not be accepted.<br/><br/>

"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."
'''
            transportType = TransportType.EMAIL
        }
        centralMessageTemplateFinal.s()

        ConfigProperty certificationExpirationNotificationWorkflow = ConfigProperty.findByName('sendNotificationPeriodEmailWorkflow')
        certificationExpirationNotificationWorkflow.name = "certificationExpirationNotificationWorkflow"
        certificationExpirationNotificationWorkflow.value = "flows/certification-expiration-notification.rf"
        certificationExpirationNotificationWorkflow.s()

        ConfigProperty certificationExpirationNotificationCronTrigger = ConfigProperty.findByName('newCertificationExpirationCronTrigger')
        certificationExpirationNotificationCronTrigger.name = "certificationExpirationNotificationCronTrigger"
        certificationExpirationNotificationCronTrigger.value = "0/5 * * * * ? *"
        certificationExpirationNotificationCronTrigger.s()

        CentralWorkflowTaskTemplate certificationExpirationNotificationGeneralTaskTemplate = new CentralWorkflowTaskTemplate()
        certificationExpirationNotificationGeneralTaskTemplate.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_GENERAL)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.SUPERVISOR] as Set
        }
        certificationExpirationNotificationGeneralTaskTemplate.s()

        CentralWorkflowTaskTemplate certificationExpirationNotificationFinalTaskTemplate = new CentralWorkflowTaskTemplate()
        certificationExpirationNotificationFinalTaskTemplate.with {
            id = CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_FINAL
            messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_FINAL)
            workflowTaskType = CentralWorkflowTaskType.SYSTEM_CENTRAL
            toNotificationApplicationRoles = [CentralApplicationRole.WORKER, CentralApplicationRole.SUPERVISOR] as Set
        }
        certificationExpirationNotificationFinalTaskTemplate.s()


        ConfigProperty.findByName("alertEscalationCronTrigger").delete();

        ConfigProperty revokeOnMissingCertificationsCronTrigger = ConfigProperty.findByName('revokeOnMissingCertificationsCronTrigger')
        if (!revokeOnMissingCertificationsCronTrigger) {
            revokeOnMissingCertificationsCronTrigger = new ConfigProperty(name: 'revokeOnMissingCertificationsCronTrigger', value: '0 0 0 1 1 ? 2050').s()
        }

        ConfigProperty complianceDashboardFileGenerationCronTrigger = ConfigProperty.findByName('complianceDashboardFileGenerationCronTrigger')
        if (!complianceDashboardFileGenerationCronTrigger) {
            complianceDashboardFileGenerationCronTrigger = new ConfigProperty(name: 'complianceDashboardFileGenerationCronTrigger', value: '0 0/5 * * * ? *').s()
        }

        ConfigProperty complianceDashboardFileFolderLocation = ConfigProperty.findByName('complianceDashboardFileFolderLocation')
        if (!complianceDashboardFileFolderLocation) {
            complianceDashboardFileFolderLocation = new ConfigProperty(name: 'complianceDashboardFileFolderLocation', value: '/tmp').s()
        }

        ConfigProperty complianceDashboardFileGenerationLastRunTime = ConfigProperty.findByName('complianceDashboardFileGenerationLastRunTime')
        if (!complianceDashboardFileGenerationLastRunTime) {
            complianceDashboardFileGenerationLastRunTime = new ConfigProperty(name: 'complianceDashboardFileGenerationLastRunTime', value: '0').s()
        }

    }
}
