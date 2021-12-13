package com.force5solutions.care.cc

import com.force5solutions.care.feed.HrInfoFeedService
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.feed.PraFeedService

class PraFeedJob {
    def concurrent = false

    static triggers = {
        cron name: 'praFeedCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        log.info "Executing PRA Feed Job at ${new Date()}"
        new PraFeedService().execute()
    }
}
