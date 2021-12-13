package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class AccessVerificationJob {

    def concurrent = false
    def accessVerificationService

    static triggers = {
        cron name: 'accessVerificationCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        try {
            log.info "Executing Access Verification Job at ${new Date()}"
            accessVerificationService.execute()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
