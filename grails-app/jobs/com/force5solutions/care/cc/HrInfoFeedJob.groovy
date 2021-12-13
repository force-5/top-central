package com.force5solutions.care.cc

import com.force5solutions.care.feed.HrInfoFeedService
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class HrInfoFeedJob {
    def concurrent = false

    static triggers = {
        cron name: 'hrInfoFeedCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        log.info "Executing HrInfo Feed Job at ${new Date()}"
        new HrInfoFeedService().execute()
    }
}
