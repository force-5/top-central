package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder


class ComplianceDashboardFileGenerationJob {
    def concurrent = false
    def centralUtilService

    static triggers = {
        cron name: 'complianceDashboardFileGenerationCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        try {
            Date jobExcecutionDate = new Date()
            log.info "Executing Compliance Dashboard File Generation Job At ${jobExcecutionDate}"
            centralUtilService.generateComplianceDashboardXMLFile(jobExcecutionDate)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}

