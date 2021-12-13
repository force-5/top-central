import com.force5solutions.care.cc.CentralWorkflowTaskTemplate
import com.force5solutions.care.common.CareConstants


CentralWorkflowTaskTemplate centralWorkflowTaskTemplate = CentralWorkflowTaskTemplate.findById(CareConstants.GATEKEEPER_REJECTION_NOTIFICATION_EMAIL_APS_SYSTEM_TASK)
if (centralWorkflowTaskTemplate) {
    centralWorkflowTaskTemplate.delete(flush: true)
}