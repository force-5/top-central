package com.force5solutions.care.cc

import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.cp.ConfigProperty
import com.force5solutions.care.feed.FeedRun

class UtilService {

    boolean transactional = true

    def createRunDatabaseFeedConfigPropertiesForJobs() {
        createOrUpdateConfigProperty("runDatabaseFeedForEmployeeCourse", "true")
        createOrUpdateConfigProperty("runDatabaseFeedForEmployeePra", "true")
        createOrUpdateConfigProperty("runDatabaseFeedForContractorHrInfo", "false")
        createOrUpdateConfigProperty("runDatabaseFeedForContractorCourse", "false")
        createOrUpdateConfigProperty("runDatabaseFeedForContractorPra", "false")
    }

    void createOrUpdateConfigProperty(String newKey, String value, String oldKey = null) {
        ConfigProperty configProperty = !oldKey ? (ConfigProperty.findByName(newKey) ?: new ConfigProperty()) : ConfigProperty.findByName(oldKey)
        configProperty.name = newKey
        configProperty.value = value
        configProperty.s()
    }

    def updateHRInfoFeedName() {
        FeedRun.findAllByFeedName('HR Info').each { FeedRun feedRun ->
            feedRun.feedName = CareConstants.FEED_EMPLOYEE_HR_INFO
            feedRun.s()
        }
    }
}
