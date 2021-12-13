package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class AutoArchiveCompletedWorkflowTaskJob {

    def concurrent = false
    def centralWorkflowTaskService

    static triggers = {
        cron name: 'autoArchiveCompletedWorkflowTaskCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        try {
            log.info "Auto Archive Completed WorkflowTask Job at ${new Date()}"
            centralWorkflowTaskService.autoArchiveCompletedWorkflowTask()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
