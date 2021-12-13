package com.force5solutions.care.cc

import com.force5solutions.care.feed.ContractorCourseFeedService
import com.force5solutions.care.feed.ContractorCourseFileFeedService
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ContractorCourseFeedJob extends FeedJob {
    def concurrent = false

    static triggers = {
        cron name: 'contractorCourseFeedCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        log.info "Executing Contractor Course Feed Job at ${new Date()}"
        configKeyName = "runDatabaseFeedForContractorCourse"
        super.execute()
    }

    void executeFeedFromDbSource() {
        new ContractorCourseFeedService().execute()
    }

    void executeFeedFromFileSource() {
        new ContractorCourseFileFeedService().execute()
    }
}
