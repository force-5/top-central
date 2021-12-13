package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class WorkerCertificationArchiveJob {

    def concurrent = false
    def workerCertificationArchiveService

    static triggers = {
        cron name: 'workerCertificationArchiveCronTrigger',
                cronExpression: ConfigurationHolder.config.farAheadCronExpression,
                group: 'topJobsGroup'
    }

    def execute() {
        try {
            log.info "Executing Worker Certification Archive Job at ${new Date()}"
            WorkerCertification.list().each { WorkerCertification workerCertification ->
                workerCertificationArchiveService.matchOrCreateWorkerCertificationEntry(workerCertification)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
