package com.force5solutions.care.feed

import groovy.sql.GroovyRowResult

class ActiveWorkerFeedVO {

    String slid
    String accessType
    String badgeNumber

    ActiveWorkerFeedVO() {}

    public String getSlid() {
        return (slid?.toUpperCase())
    }

    ActiveWorkerFeedVO(String slid, String accessType, String badgeNumber) {
        this.slid = slid?.toUpperCase()
        this.accessType = accessType
        this.badgeNumber = badgeNumber
    }

    ActiveWorkerFeedVO(GroovyRowResult rowResult) {
        slid = rowResult.getProperty('SLID_ID')
        accessType = rowResult.getProperty('ACCESS_TYPE')
        badgeNumber = rowResult.getProperty('BADGE_NUMBER') ? rowResult.getProperty('BADGE_NUMBER') as String : null
    }

    String toString() {
        return ("${slid} : ${accessType}")
    }

}