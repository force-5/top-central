import com.force5solutions.care.cc.TransportType
import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.common.CentralMessageTemplate
import com.force5solutions.care.common.SessionUtils

pre {
    CentralMessageTemplate messageTemplate;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE
            subjectTemplate = 'Personnel ###PersonnelFirstMiddleLastNameByGroup###' + "'s access has been revoked in 24 hours for group ###PersonnelEntitlementRoleName###"
            bodyTemplate = '''Personnel ###PersonnelFirstMiddleLastNameByGroup###'s access has been revoked in 24 hours for group ###PersonnelEntitlementRoleName###.
<br /><br />To view further information, please click the link below:<br />
<a href="###MoreInformationLink###">###MoreInformationLink###</a><br />
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE
            subjectTemplate = 'Personnel ###PersonnelFirstMiddleLastNameByGroup###' + "'s access has been revoked in 7 days for group ###PersonnelEntitlementRoleName###"
            bodyTemplate = '''Personnel ###PersonnelFirstMiddleLastNameByGroup###'s access has been revoked in 7 days for group ###PersonnelEntitlementRoleName###.
<br /><br />To view further information, please click the link below:<br />
<a href="###MoreInformationLink###">###MoreInformationLink###</a><br />
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST
            subjectTemplate = "ACTION REQUIRED - Access Request for ###PersonnelNameByGroup###"
            bodyTemplate = '''
###PersonnelNameByGroup### has submitted a request for access to a Critical Cyber Asset. The request details have been included below. Please review and click the
following link to approve or reject this request.<br /><br />
<a href="###MoreInformationLink###">###MoreInformationLink###</a><br />

###AccessRequest###

The following certifications must be current before access will be provisioned.<br /><br />

Required Certifications

###RequiredCertificationsStatus### <br /><br />

Instructions <br /><br />

PRA <br /><br />

All requests for an employee PRA should be submitted via EAMS:<a href="http://inapp.fpl.com:8006/AlertEnterprise/?x=wKgQkogc3nwyTE8kxiPfgQ" target="_blank">
http://inapp.fpl.com:8006/AlertEnterprise/?x=wKgQkogc3nwyTE8kxiPfgQ</a> <br /><br />

1.	Select Request Category <br /> <br />

a.	Go to Access Tab and select CIP Access Center<br />
b.	Scroll to the bottom and select Request Employee PRA (this is only available for AAMs and CSMs)<br /><br />


1.	Select User Category <br /><br />

a.	Select whether the PRA is for you or someone else <br /><br />


FERC SOC<br /><br />

Please click on this <a href="http://eweb/Global/policies/bunit/transsub/training.shtml" target="_blank">http://eweb/Global/policies/bunit/transsub/training.shtml</a> link and select
the FERC Standards of Conduct course to renew your annual training. <br /><br />


CIP Certification <br /><br />

Click on the link below and locate the course called CIP Annual CCA Access - SCC<br />
<a href="http://infpl/Global/policies/bunit/transsub/training.shtml" target="_blank">
http://infpl/Global/policies/bunit/transsub/training.shtml</a><br /><br />

Note: It is very important that you follow the directions in the web based
training and that you take and pass the test, otherwise your access to the
SCC will be removed. <br /><br />

-or<br /><br />

If you are not able to access the training using the steps above, try the
following <br /><br />

1. Access INFPL<br />
2. Click on Companies and Business Units, then scroll down and click on Transmission/Substation<br />
3. Locate Areas of Focus on the right hand side of the screen and click on Compliance link<br />
4. Scroll down and click on FPL Compliance Training<br />
5 Scroll down and click on CIP Annual CCA Access - SCC<br />
6. Click on the link labelled Start Course Now<br />
7. Follow the prompts <br /><br />

-or<br /><br />

If you are not able to access the training using the steps above, try the following <br /><br />

From the infpl main page search for the course CIP Annual CCA Access - SCC in the search window at the top. The result should be in the training
section. Follow the prompts to start the course<br /><br />

Please note that this is a system generated message and replies will not be accepted.<br /><br />

"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."<br /><br />
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_FOR_EMPLOYEE)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_FOR_EMPLOYEE) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_FOR_EMPLOYEE
            subjectTemplate = "Access Request Confirmation"
            bodyTemplate = '''
Thank you for your recent access request. Your request has been forwarded to ###WorkerSupervisorFirstMiddleLastName### for approval.
Additional notifications will be sent informing you of the progress of your request, along with any additional requirements needed to grant access to the following:<br />

###AccessRequest###

Please note that this is a system generated message and replies will not be accepted. <br /><br />

"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_FOR_CONTRACTOR)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_FOR_CONTRACTOR) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_FOR_CONTRACTOR
            subjectTemplate = "Access Request Confirmation"
            bodyTemplate = '''
Thank you for your recent access request. Your request has been forwarded to ###WorkerSupervisorFirstMiddleLastName### for approval.
Additional notifications will be sent informing you of the progress of your request, along with any additional requirements needed to grant access to the following:<br />

###AccessRequest###

Please note that this is a system generated message and replies will not be accepted. <br /><br />

"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_SUPERVISOR_REJECT_FOR_EMPLOYEE)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_SUPERVISOR_REJECT_FOR_EMPLOYEE) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_SUPERVISOR_REJECT_FOR_EMPLOYEE
            subjectTemplate = 'Access Request Rejected by ###WorkerSupervisorFirstMiddleLastName###'
            bodyTemplate = '''
Your recent  access request has been rejected by H Evelyn.<br />

###AccessRequest###

Please note that this is a system generated message and replies will not be accepted.<br />
<br />
"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_SUPERVISOR_REJECT_FOR_CONTRACTOR)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_SUPERVISOR_REJECT_FOR_CONTRACTOR) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_SUPERVISOR_REJECT_FOR_CONTRACTOR
            subjectTemplate = 'Access Request Rejected by ###WorkerSupervisorFirstMiddleLastName###'
            bodyTemplate = '''
Your recent  access request has been rejected by H Evelyn.<br />

###AccessRequest###

Please note that this is a system generated message and replies will not be accepted.<br />
<br />
"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_MISSING_CERTIFICATIONS_EMAIL_TO_EMPLOYEE)))
    if (overrideOrCreate){
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_MISSING_CERTIFICATIONS_EMAIL_TO_EMPLOYEE) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_MISSING_CERTIFICATIONS_EMAIL_TO_EMPLOYEE
            subjectTemplate = 'ACTION REQUIRED - Access Request Certifcation Required'
            bodyTemplate = '''
Your recent access request has been approved by ###WorkerSupervisorFirstMiddleLastName###. However, the following certifications are required before access
can be provisioned. Please review the requirements and take the necessary action to update your certification status. Once these requirements have been met your request
will be updated automatically. <br />

###AccessRequest###

The following certifications must be current before access will be provisioned.<br />

Required Certifications

###RequiredCertificationsStatus### <br /><br />

Instructions <br /><br />

PRA <br /><br />

All requests for an employee PRA should be submitted via EAMS:<a href="http://inapp.fpl.com:8006/AlertEnterprise/?x=wKgQkogc3nwyTE8kxiPfgQ" target="_blank">
http://inapp.fpl.com:8006/AlertEnterprise/?x=wKgQkogc3nwyTE8kxiPfgQ</a> <br /><br />

1.	Select Request Category <br /> <br />

a.	Go to Access Tab and select CIP Access Center<br />
b.	Scroll to the bottom and select Request Employee PRA (this is only<br />
available for AAMs and CSMs)


1.	Select User Category <br /><br />

a.	Select whether the PRA is for you or someone else <br /><br />


FERC SOC<br /><br />

Please click on this <a href="http://eweb/Global/policies/bunit/transsub/training.shtml" target="_blank">http://eweb/Global/policies/bunit/transsub/training.shtml</a> link and select
the FERC Standards of Conduct course to renew your annual training. <br /><br />


CIP Certification <br /><br />

Click on the link below and locate the course called CIP Annual CCA Access - SCC<br />
<a href="http://infpl/Global/policies/bunit/transsub/training.shtml" target="_blank">
http://infpl/Global/policies/bunit/transsub/training.shtml</a>

Note: It is very important that you follow the directions in the web based
training and that you take and pass the test, otherwise your access to the
SCC will be removed. <br /><br />

-or<br /><br />

If you are not able to access the training using the steps above, try the
following <br /><br />

1. Access INFPL<br />
2. Click on Companies and Business Units, then scroll down and click on Transmission/Substation<br />
3. Locate Areas of Focus on the right hand side of the screen and click on Compliance link<br />
4. Scroll down and click on FPL Compliance Training<br />
5 Scroll down and click on CIP Annual CCA Access - SCC<br />
6. Click on the link labelled Start Course Now<br />
7. Follow the prompts <br /><br />

-or<br /><br />

If you are not able to access the training using the steps above, try the following <br /><br />

From the infpl main page search for the course CIP Annual CCA Access - SCC in the search window at the top. The result should be in the training
section. Follow the prompts to start the course<br /><br />

Please note that this is a system generated message and replies will not be accepted.<br /><br />

"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."<br /><br />
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_MISSING_CERTIFICATIONS_EMAIL_TO_CONTRACTOR)))
    if (overrideOrCreate){
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_MISSING_CERTIFICATIONS_EMAIL_TO_CONTRACTOR) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_MISSING_CERTIFICATIONS_EMAIL_TO_CONTRACTOR
            subjectTemplate = 'ACTION REQUIRED - Access Request Certifcation Required'
            bodyTemplate = '''
Your recent access request has been approved by ###WorkerSupervisorFirstMiddleLastName###. However, the following certifications are required before access
can be provisioned. Please review the requirements and take the necessary action to update your certification status. Once these requirements have been met your request
will be updated automatically. <br />

###AccessRequest###

The following certifications must be current before access will be provisioned.<br />

Required Certifications

###RequiredCertificationsStatus### <br /><br />

Instructions <br /><br />

PRA <br /><br />

All requests for an Contractor PRA should be submitted via EAMS:<a href="http://inapp.fpl.com:8006/AlertEnterprise/?x=wKgQkogc3nwyTE8kxiPfgQ" target="_blank">
http://inapp.fpl.com:8006/AlertEnterprise/?x=wKgQkogc3nwyTE8kxiPfgQ</a> <br /><br />

1.	Select Request Category <br /> <br />

a.	Go to Access Tab and select CIP Access Center<br />
b.	Scroll to the bottom and select Request Contractor PRA (this is only<br />
available for AAMs and CSMs)


1.	Select User Category <br /><br />

a.	Select whether the PRA is for you or someone else <br /><br />


FERC SOC<br /><br />

Please click on this <a href="http://eweb/Global/policies/bunit/transsub/training.shtml" target="_blank">http://eweb/Global/policies/bunit/transsub/training.shtml</a> link and select
the FERC Standards of Conduct course to renew your annual training. <br /><br />


CIP Certification <br /><br />

Click on the link below and locate the course called CIP Annual CCA Access - SCC<br />
<a href="http://infpl/Global/policies/bunit/transsub/training.shtml" target="_blank">
http://infpl/Global/policies/bunit/transsub/training.shtml</a>

Note: It is very important that you follow the directions in the web based
training and that you take and pass the test, otherwise your access to the
SCC will be removed. <br /><br />

-or<br /><br />

If you are not able to access the training using the steps above, try the
following <br /><br />

1. Access INFPL<br />
2. Click on Companies and Business Units, then scroll down and click on Transmission/Substation<br />
3. Locate Areas of Focus on the right hand side of the screen and click on Compliance link<br />
4. Scroll down and click on FPL Compliance Training<br />
5 Scroll down and click on CIP Annual CCA Access - SCC<br />
6. Click on the link labelled Start Course Now<br />
7. Follow the prompts <br /><br />

-or<br /><br />

If you are not able to access the training using the steps above, try the following <br /><br />

From the infpl main page search for the course CIP Annual CCA Access - SCC in the search window at the top. The result should be in the training
section. Follow the prompts to start the course<br /><br />

Please note that this is a system generated message and replies will not be accepted.<br /><br />

"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."<br /><br />
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_TIME_OUT_NOTIFICATION)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_TIME_OUT_NOTIFICATION) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_TIME_OUT_NOTIFICATION
            subjectTemplate = "Access Request Expired"
            bodyTemplate = '''
Your recent  access request  has expired. This request has expired because certain prerequisites have not been met. Please complete the required
certifications and resubmit your request.<br />

###AccessRequest###

The following certifications must be current before access will be provisioned.<br /><br />

Required Certifications

###RequiredCertificationsStatus### <br /><br />

Instructions <br /><br />

PRA <br /><br />

All requests for an employee PRA should be submitted via EAMS:<a href="http://inapp.fpl.com:8006/AlertEnterprise/?x=wKgQkogc3nwyTE8kxiPfgQ" target="_blank">
http://inapp.fpl.com:8006/AlertEnterprise/?x=wKgQkogc3nwyTE8kxiPfgQ</a> <br /><br />

1.	Select Request Category <br /> <br />

a.	Go to Access Tab and select CIP Access Center<br />
b.	Scroll to the bottom and select Request Employee PRA (this is only<br />
available for AAMs and CSMs)


1.	Select User Category <br /><br />

a.	Select whether the PRA is for you or someone else <br /><br />


FERC SOC<br /><br />

Please click on this <a href="http://eweb/Global/policies/bunit/transsub/training.shtml" target="_blank">http://eweb/Global/policies/bunit/transsub/training.shtml</a> link and select
the FERC Standards of Conduct course to renew your annual training. <br /><br />


CIP Certification <br /><br />

Click on the link below and locate the course called CIP Annual CCA Access - SCC<br />
<a href="http://infpl/Global/policies/bunit/transsub/training.shtml" target="_blank">
http://infpl/Global/policies/bunit/transsub/training.shtml</a>

Note: It is very important that you follow the directions in the web based
training and that you take and pass the test, otherwise your access to the
SCC will be removed. <br /><br />

-or<br /><br />

If you are not able to access the training using the steps above, try the
following <br /><br />

1. Access INFPL<br />
2. Click on Companies and Business Units, then scroll down and click on Transmission/Substation<br />
3. Locate Areas of Focus on the right hand side of the screen and click on Compliance link<br />
4. Scroll down and click on FPL Compliance Training<br />
5 Scroll down and click on CIP Annual CCA Access - SCC<br />
6. Click on the link labelled Start Course Now<br />
7. Follow the prompts <br /><br />

-or<br /><br />

If you are not able to access the training using the steps above, try the following <br /><br />

From the infpl main page search for the course CIP Annual CCA Access - SCC in the search window at the top. The result should be in the training
section. Follow the prompts to start the course<br /><br />

Please note that this is a system generated message and replies will not be accepted.<br /><br />

"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."<br /><br />
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_TIME_OUT_NOTIFICATION_FOR_CONTRACTOR)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_TIME_OUT_NOTIFICATION_FOR_CONTRACTOR) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_TIME_OUT_NOTIFICATION_FOR_CONTRACTOR
            subjectTemplate = "Access Request Expired"
            bodyTemplate = '''
Your recent  access request  has expired. This request has expired because certain prerequisites have not been met. Please complete the required
certifications and resubmit your request.<br />

###AccessRequest###

The following certifications must be current before access will be provisioned.<br /><br />

Required Certifications

###RequiredCertificationsStatus### <br /><br />

Instructions <br /><br />

PRA <br /><br />

All requests for an Contractor PRA should be submitted via EAMS:<a href="http://inapp.fpl.com:8006/AlertEnterprise/?x=wKgQkogc3nwyTE8kxiPfgQ" target="_blank">
http://inapp.fpl.com:8006/AlertEnterprise/?x=wKgQkogc3nwyTE8kxiPfgQ</a> <br /><br />

1.	Select Request Category <br /> <br />

a.	Go to Access Tab and select CIP Access Center<br />
b.	Scroll to the bottom and select Request Contractor PRA (this is only<br />
available for AAMs and CSMs)


1.	Select User Category <br /><br />

a.	Select whether the PRA is for you or someone else <br /><br />


FERC SOC<br /><br />

Please click on this <a href="http://eweb/Global/policies/bunit/transsub/training.shtml" target="_blank">http://eweb/Global/policies/bunit/transsub/training.shtml</a> link and select
the FERC Standards of Conduct course to renew your annual training. <br /><br />


CIP Certification <br /><br />

Click on the link below and locate the course called CIP Annual CCA Access - SCC<br />
<a href="http://infpl/Global/policies/bunit/transsub/training.shtml" target="_blank">
http://infpl/Global/policies/bunit/transsub/training.shtml</a>

Note: It is very important that you follow the directions in the web based
training and that you take and pass the test, otherwise your access to the
SCC will be removed. <br /><br />

-or<br /><br />

If you are not able to access the training using the steps above, try the
following <br /><br />

1. Access INFPL<br />
2. Click on Companies and Business Units, then scroll down and click on Transmission/Substation<br />
3. Locate Areas of Focus on the right hand side of the screen and click on Compliance link<br />
4. Scroll down and click on FPL Compliance Training<br />
5 Scroll down and click on CIP Annual CCA Access - SCC<br />
6. Click on the link labelled Start Course Now<br />
7. Follow the prompts <br /><br />

-or<br /><br />

If you are not able to access the training using the steps above, try the following <br /><br />

From the infpl main page search for the course CIP Annual CCA Access - SCC in the search window at the top. The result should be in the training
section. Follow the prompts to start the course<br /><br />

Please note that this is a system generated message and replies will not be accepted.<br /><br />

"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."<br /><br />
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_GENERAL)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_GENERAL) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_GENERAL
            subjectTemplate = ''' Action Required - SCC CIP Certification Expiration for ###PersonnelNameByCertification### '''

            bodyTemplate = '''
****CIP Credentials for ###PersonnelNameByCertification### will expire in ###DaysRemainingInCertificationExpiration### days.
If he/she does not renew ###PersonnelCertificationName### their access to the Power Supply System Control Center (SCC) will be removed.*** <br /><br />


Once the required credential has been completed, please provide certificate of completion to the Power Supply Control Center Tech (Mina Soto). <br /> <br />

Please Note: <br />
Web based training courses are located on the RSCO website.<br /><br />

Follow the steps below to directly access the web based trainings:<br /><br />

Click on the link below and locate the course called CIP Annual CCA Access - SCC and click on the link, then start course<br /><br />

<a href="http://infpl/Global/policies/bunit/transsub/training.shtml" target="_blank">http://infpl/Global/policies/bunit/transsub/training.shtml</a><br /><br />

Note: It is very important that you follow the directions in the web based training and that you take and pass the test, otherwise your access to the SCC will be removed.<br /><br />

-or <br /><br />

If you are not able to access the training using the steps above, try the following<br /><br />

1. Access INFPL <br />
2. Click on Companies and Business Units, then scroll down and click on Transmission/Substation <br />
3. Locate Areas of Focus on the right hand side of the screen and click on Compliance link<br />
4. Scroll down and click on FPL Compliance Training <br />
5 Scroll down and click on CIP Annual CCA Access - SCC <br />
6. Click on the link labelled Start Course Now <br />
7. Follow the prompts <br /><br />

-or <br /><br />

If you are not able to access the training using the steps above, try the following <br /> <br />

From the infpl main page search for the course <br /><br />

<strong>CIP Annual CCA Access - SCC</strong> <br /><br />

in the search window at the top.<br />
The result should be in the training section. Follow the prompts to start the course<br /> <br />

Please note that this is a system generated message and replies will not be accepted.<br /><br />

"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_FINAL)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_FINAL) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_FINAL
            subjectTemplate = ''' [Final] Action Required - SCC CIP Certification Expiration for ###PersonnelNameByCertification### '''

            bodyTemplate = '''
****[Final] CIP Credentials for ###PersonnelNameByCertification### will expire in ###DaysRemainingInCertificationExpiration### days.
If he/she does not renew ###PersonnelCertificationName### their access to the Power Supply System Control Center (SCC) will be removed.*** <br /><br />


Once the required credential has been completed, please provide certificate of completion to the Power Supply Control Center Tech (Mina Soto). <br /><br />

Please Note: <br />
Web based training courses are located on the RSCO website.<br /><br />

Follow the steps below to directly access the web based trainings:<br /><br />

Click on the link below and locate the course called CIP Annual CCA Access - SCC and click on the link, then start course <br /><br />

<a href="http://infpl/Global/policies/bunit/transsub/training.shtml" target="_blank">http://infpl/Global/policies/bunit/transsub/training.shtml</a><br /><br />

Note: It is very important that you follow the directions in the web based training and that you take and pass the test, otherwise your access to the SCC will be removed.<br /><br />

-or <br /><br />

If you are not able to access the training using the steps above, try the following<br />

1. Access INFPL <br />
2. Click on Companies and Business Units, then scroll down and click on Transmission/Substation <br />
3. Locate Areas of Focus on the right hand side of the screen and click on Compliance link<br />
4. Scroll down and click on FPL Compliance Training <br />
5 Scroll down and click on CIP Annual CCA Access - SCC <br />
6. Click on the link labelled Start Course Now <br />
7. Follow the prompts <br /><br />

-or <br /><br />

If you are not able to access the training using the steps above, try the following <br /> <br />

From the infpl main page search for the course <br /> <br />

<strong>CIP Annual CCA Access - SCC</strong><br /><br />

in the search window at the top.<br />
The result should be in the training section. Follow the prompts to start the course<br /><br />

Please note that this is a system generated message and replies will not be accepted.<br /><br />

"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."
'''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ENTITLEMENT_POLICY_CREATION)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ENTITLEMENT_POLICY_CREATION) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ENTITLEMENT_POLICY_CREATION
            subjectTemplate = 'Request to approve new Entitlment Type - ' + "###EntitlementPolicyName###"
            bodyTemplate = '''
        This alert notifies you that a new Entitlement Policy - <strong>''' + "###EntitlementPolicyName###" + ''' </strong> has been created.
        <br /><br />Your approval is required for its usage.
        <br /><br />Please login to CARE to take appropriate action:
        <br /><a href="###MoreInformationLink###">###MoreInformationLink###</a><br />
        '''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION
            subjectTemplate = 'ACTION REQUIRED - Critical Cyber Asset continual access verification'
            bodyTemplate = '''
        Below is a list of personnel reporting to you that have authorized cyber and/or authorized unescorted physical access to the System Control Center's Critical Cyber Assets (CCAs).
        Please verify these individuals' continued need for access by clicking the <b>Confirm</b> link below. Should any of these employees listed no longer require access, please click the <b>Make Changes</b> link to revoke employees no longer needing access.
        Failure to verify continued need for access will result in escalation to your supervisor, and could result in the revocation of access to these CCAs for the individuals listed below.<br /><br />
        If you have any questions please contact Mina Soto at (305) 442-5376 or Maria Elena Martinez at (305) 442-5767.<br /><br />
        Thank you for your prompt attention to this matter.<br /><br />
        SCC Security Administrators<br /><br />
        This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct.<br /><br />
        This message may contain confidential and/or privileged information of Florida Power & Light Company.  If you are not the intended recipient please 1) do not disclose, copy, distribute or use this information, 2) advise the sender by return email, and 3) delete all copies from your computer.  Your cooperation is greatly appreciated.<br /><br />''' + "###activeWorkersGroupByEntitlementRole###" + '''
           <br /><br />
            Please click on the link below to confirm the Access Verification report:
            <br /><a href="###ConfirmationLink###">###ConfirmationLink###</a><br />
            <br />
            Please click on the link below to make changes:
            <br /><a href="###EmployeeListLink###">###EmployeeListLink###</a><br />

           '''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_ESCALATION)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_ESCALATION) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_ESCALATION
            subjectTemplate = 'ACTION REQUIRED - Critical Cyber Asset continual access verification***ESCALATION****'
            bodyTemplate = '''
        <span style="color: #c41108;"> ***This email has been forwarded to you because ###SupervisorFirstMiddleLastName### has not completed access verification for his/her direct reports.  If you wish to perform this verification on his/her behalf, please click on the link below.****</span><br /><br />
        Below is a list of personnel reporting to you that have authorized cyber and/or authorized unescorted physical access to the System Control Center's Critical Cyber Assets (CCAs).
        Please verify these individuals' continued need for access by clicking the <b>Confirm</b> link below. Should any of these employees listed no longer require access, please click the <b>Make Changes</b> link to revoke employees no longer needing access.
        Failure to verify continued need for access will result in escalation to your supervisor, and could result in the revocation of access to these CCAs for the individuals listed below.<br /><br />
        If you have any questions please contact Mina Soto at (305) 442-5376 or Maria Elena Martinez at (305) 442-5767.<br /><br />
        Thank you for your prompt attention to this matter.<br /><br />
        SCC Security Administrators<br /><br />
        This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct.<br /><br />
        This message may contain confidential and/or privileged information of Florida Power & Light Company.  If you are not the intended recipient please 1) do not disclose, copy, distribute or use this information, 2) advise the sender by return email, and 3) delete all copies from your computer.  Your cooperation is greatly appreciated.<br /><br />''' + "###activeWorkersGroupByEntitlementRole###" + '''
           <br /><br />
            Please click on the link below to confirm the Access Verification report:
            <br /><a href="###ConfirmationLink###">###ConfirmationLink###</a><br />
            <br />
            Please click on the link below to make changes:
            <br /><a href="###EmployeeListLink###">###EmployeeListLink###</a><br />
           '''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_ESCALATION_NOTIFICATION_TO_ORIGINAL_SUPERVISOR)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_ESCALATION_NOTIFICATION_TO_ORIGINAL_SUPERVISOR) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_ESCALATION_NOTIFICATION_TO_ORIGINAL_SUPERVISOR
            subjectTemplate = 'ACTION REQUIRED - Critical Cyber Asset continual access verification***ESCALATION****'
            bodyTemplate = '''
        <br />**********************************************************************<br />
        EMAIL COPY                                              EMAIL COPY      <br />
                                                                                <br />
             THIS EMAIL WAS ESCALATED TO JOHN DOE                               <br />
                                                                                <br />
        EMAIL COPY                                              EMAIL COPY      <br />
        **********************************************************************  <br />
        <span style="color: #c41108;"> ***This email has been forwarded to you because ###SupervisorFirstMiddleLastName### has not completed access verification for his/her direct reports.  If you wish to perform this verification on his/her behalf, please click on the link below.****</span><br /><br />
        Below is a list of personnel reporting to you that have authorized cyber and/or authorized unescorted physical access to the System Control Center's Critical Cyber Assets (CCAs).
        Please verify these individuals' continued need for access by clicking the <b>Confirm</b> link below. Should any of these employees listed no longer require access, please click the <b>Make Changes</b> link to revoke employees no longer needing access.
        Failure to verify continued need for access will result in escalation to your supervisor, and could result in the revocation of access to these CCAs for the individuals listed below.<br /><br />
        If you have any questions please contact Mina Soto at (305) 442-5376 or Maria Elena Martinez at (305) 442-5767.<br /><br />
        Thank you for your prompt attention to this matter.<br /><br />
        SCC Security Administrators<br /><br />
        This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct.<br /><br />
        This message may contain confidential and/or privileged information of Florida Power & Light Company.  If you are not the intended recipient please 1) do not disclose, copy, distribute or use this information, 2) advise the sender by return email, and 3) delete all copies from your computer.  Your cooperation is greatly appreciated.<br /><br />''' + "###activeWorkersGroupByEntitlementRole###" + '''
           <br /><br />
           <br />
            Please click on the link below to make changes:
            <br /><a href="###EmployeeListLink###">###EmployeeListLink###</a><br />
           '''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_NOTICE)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_NOTICE) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_NOTICE
            subjectTemplate = 'ACTION REQUIRED - Critical Cyber Asset continual access verification***FINAL NOTICE****'
            bodyTemplate = '''
        <span style="color: #c41108;"> ***FINAL NOTICE: Failure to respond will result in the revocation of the personnel listed below.  This email has been forwarded to you because ###SupervisorFirstMiddleLastName### has not completed access verification for his/her direct reports.  If you wish to perform this verification on his/her behalf, please click on the link below.****</span><br /><br />
        Below is a list of personnel reporting to you that have authorized cyber and/or authorized unescorted physical access to the System Control Center's Critical Cyber Assets (CCAs).
        Please verify these individuals' continued need for access by clicking the <b>Confirm</b> link below. Should any of these employees listed no longer require access, please click the <b>Make Changes</b> link to revoke employees no longer needing access.
        Failure to verify continued need for access will result in escalation to your supervisor, and could result in the revocation of access to these CCAs for the individuals listed below.<br /><br />
        If you have any questions please contact Mina Soto at (305) 442-5376 or Maria Elena Martinez at (305) 442-5767.<br /><br />
        Thank you for your prompt attention to this matter.<br /><br />
        SCC Security Administrators<br /><br />
        This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct.<br /><br />
        This message may contain confidential and/or privileged information of Florida Power & Light Company.  If you are not the intended recipient please 1) do not disclose, copy, distribute or use this information, 2) advise the sender by return email, and 3) delete all copies from your computer.  Your cooperation is greatly appreciated.<br /><br />''' + "###activeWorkersGroupByEntitlementRole###" + '''
           <br /><br />
            Please click on the link below to make changes:
            <br /><a href="###EmployeeListLink###">###EmployeeListLink###</a><br />
           '''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    overrideOrCreate = (SessionUtils.request || (!CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION)))
    if (overrideOrCreate) {
        messageTemplate = CentralMessageTemplate.findByName(CareConstants.CENTRAL_MESSAGE_TEMPLATE_ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION) ?: new CentralMessageTemplate();
        messageTemplate.with {
            name = CareConstants.CENTRAL_MESSAGE_TEMPLATE_ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION
            subjectTemplate = 'Access Confirmation for ###PersonnelNameByGroup###'
            bodyTemplate = '''
Thank you for your recent access request submission. This notification is to confirm that access has been provisioned for the following role(s)<br /><br />

###PersonnelNameByGroup###<br />

###AccessRequest###

Please note that this is a system generated message and replies will not be accepted.<br /><br />

"This document contains non-public transmission information and must be treated in accordance with the FERC Standards of Conduct."
  '''
            transportType = TransportType.EMAIL
        }
        messageTemplate.save();
    }
}

fixture {
}
