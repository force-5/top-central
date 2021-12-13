package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class CertificationCompletionTimeOutJob {

    def concurrent = false
    def centralWorkflowTaskService

    static triggers = {
        cron name: 'certificationCompletionTimeOutCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        try {
            Date date = new Date()
            log.info "Executing Certification Completion Time Out Job at ${date}"
            centralWorkflowTaskService.cancelCertificationsTimedOutTasks(date)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
