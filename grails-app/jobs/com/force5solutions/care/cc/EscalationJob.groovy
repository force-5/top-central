package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class EscalationJob {

    def concurrent = false
    def escalationService

    static triggers = {
        cron name: 'escalationCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        try {
            escalationService.escalateTasks(new Date())
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
