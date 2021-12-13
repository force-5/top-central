package com.force5solutions.care.cc

import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.common.LogMessage

class SponsorCertificationExpirationJob {

    static config = ConfigurationHolder.config
    def concurrent = false
    def workerCertificationService

    static triggers = {
        cron name: 'sponsorExpirationCronTrigger',
                group: 'topJobsGroup',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression
    }

    def execute() {
        try {
            LogMessage logMessage = new LogMessage();
            logMessage.message = "Job Executed"
            logMessage.module = "CertificationExpirationNotificationJob"
            logMessage.save();

            if (!config.bootStrapMode) {
                if (GrailsUtil.environment != GrailsApplication.ENV_PRODUCTION) {
                    log.info "Executing Sponsor Certification Expiration Job at ${new Date()}"
                }
//                workerCertificationService.getRequiredBurCertifications()
            }
        } catch (Exception t) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            t.printStackTrace()
            log.error(t)
            LogMessage logMessage = new LogMessage();
            logMessage.message = "Exception while execution of CertificationExpirationNotificationJob"
            logMessage.module = "CertificationExpirationNotificationJob"
            logMessage.stackTrace = sw.toString();
            logMessage.save();
        }
    }
}
