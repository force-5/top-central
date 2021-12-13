package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class CertificationExpirationNotificationJob {

    def concurrent = false
    def workerCertificationService

    static triggers = {
        cron name: 'certificationExpirationNotificationCronTrigger',
                group: 'topJobsGroup',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression
    }

    def execute() {
        try {
            List<CertificationExpirationNotificationVO> certificationExpirationNotificationVOs = []
            Date executionDateTime = new Date()
            log.info "Executing Certification Expiration Notification Job At ${executionDateTime}"
            certificationExpirationNotificationVOs = workerCertificationService.sendCertificationExpirationNotification(executionDateTime)
            workerCertificationService.startCertificationExpirationNotificationWorkflow(certificationExpirationNotificationVOs)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
