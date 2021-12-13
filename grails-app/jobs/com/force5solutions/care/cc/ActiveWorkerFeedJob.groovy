package com.force5solutions.care.cc

import com.force5solutions.care.feed.ActiveWorkerFeedService
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ActiveWorkerFeedJob {

    def concurrent = false

    static triggers = {
        cron name: 'activeWorkerCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        log.info "Executing Active Worker Feed Job at ${new Date()}"
        new ActiveWorkerFeedService().execute()
    }
}
