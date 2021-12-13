package com.force5solutions.care.cc

import com.force5solutions.care.feed.EmployeePraFeedService
import com.force5solutions.care.feed.EmployeePraFileFeedService
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class EmployeePraFeedJob extends FeedJob {
    def concurrent = false

    static triggers = {
        cron name: 'employeePraFeedCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        log.info "Executing Employee PRA Feed Job at ${new Date()}"
        configKeyName = "runDatabaseFeedForEmployeePra"
        super.execute()
    }

    void executeFeedFromDbSource() {
        new EmployeePraFeedService().execute()
    }

    void executeFeedFromFileSource() {
        new EmployeePraFileFeedService().execute()
    }
}
