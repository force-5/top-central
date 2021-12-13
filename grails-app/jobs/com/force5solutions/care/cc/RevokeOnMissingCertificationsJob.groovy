package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder


class RevokeOnMissingCertificationsJob {
    static triggers = {
            cron name: 'revokeOnMissingCertificationsCronTrigger',
                    group: 'topJobsGroup',
                    cronExpression: ConfigurationHolder.config.farAheadCronExpression
        }

    def workerCertificationService

    def execute() {
        workerCertificationService.revokeAccessInCaseMissingAnyCertifications()
    }
}
