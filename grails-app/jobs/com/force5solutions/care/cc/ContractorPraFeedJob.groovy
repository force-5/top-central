package com.force5solutions.care.cc

import com.force5solutions.care.feed.ContractorPraFeedService
import com.force5solutions.care.feed.ContractorPraFileFeedService
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ContractorPraFeedJob extends FeedJob {
    def concurrent = false

    static triggers = {
        cron name: 'contractorPraFeedCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        log.info "Executing Contractor PRA Feed Job at ${new Date()}"
        configKeyName = "runDatabaseFeedForContractorPra"
        super.execute()
    }

    void executeFeedFromDbSource() {
        new ContractorPraFeedService().execute()
    }

    void executeFeedFromFileSource() {
        new ContractorPraFileFeedService().execute()
    }
}
