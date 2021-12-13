package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class CreateApsTaskForAccessVerificationTimeOutJob {

    def concurrent = false
    def accessVerificationService

    static triggers = {
        cron name: 'createApsTaskForAccessVerificationTimeOutCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        try {
            log.info "Executing Create APS Task For Access Verification at ${new Date()}"
            accessVerificationService.createApsTaskForAccessVerification(new Date())
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
