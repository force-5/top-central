package com.force5solutions.care.cc

import com.force5solutions.care.feed.EmployeeCourseFeedService
import com.force5solutions.care.feed.EmployeeCourseFileFeedService
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class EmployeeCourseFeedJob extends FeedJob {
    def concurrent = false

    static triggers = {
        cron name: 'courseFeedCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        log.info "Executing Employee Course Feed Job at ${new Date()}"
        configKeyName = "runDatabaseFeedForEmployeeCourse"
        super.execute()
    }

    void executeFeedFromDbSource() {
        new EmployeeCourseFeedService().execute()
    }

    void executeFeedFromFileSource() {
        new EmployeeCourseFileFeedService().execute()
    }
}
