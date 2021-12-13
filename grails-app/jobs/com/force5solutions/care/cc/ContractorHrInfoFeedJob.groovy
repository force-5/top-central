package com.force5solutions.care.cc

import com.force5solutions.care.feed.ContractorHrInfoFeedService
import com.force5solutions.care.feed.ContractorHrInfoFileFeedService
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ContractorHrInfoFeedJob extends FeedJob {
    def concurrent = false

    static triggers = {
        cron name: 'contractorHrInfoFeedCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        log.info "Executing ContractorHrInfo Feed Job at ${new Date()}"
        configKeyName = "runDatabaseFeedForContractorHrInfo"
        super.execute()
    }

    void executeFeedFromDbSource() {
        new ContractorHrInfoFeedService().execute()
    }

    void executeFeedFromFileSource() {
        new ContractorHrInfoFileFeedService().execute()
    }
}
