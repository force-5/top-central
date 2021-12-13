import com.force5solutions.care.cc.CertificationExpirationNotificationTemplate
import com.force5solutions.care.cc.CentralWorkflowTaskTemplate
import com.force5solutions.care.common.CareConstants

fixture {

    generalTemplateForAllCertification(CertificationExpirationNotificationTemplate) {
        certification = "*"
        notificationPeriodInDays = "30,60,90"
        taskTemplateName = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL).id
    }

    finalTemplateForAllCertification(CertificationExpirationNotificationTemplate) {
        certification = "*"
        notificationPeriodInDays = "7"
        taskTemplateName = CentralWorkflowTaskTemplate.findById(CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_FINAL).id
    }
}
