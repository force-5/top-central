package com.force5solutions.care.feed

import groovy.sql.GroovyRowResult

class EmployeePRAFeedVO {

    Long pernr
    Date lastBackgroundDate
    String slid

    EmployeePRAFeedVO() {}

    EmployeePRAFeedVO(Long pernr, Date lastBackgroundDate) {
        this.pernr = pernr
        this.lastBackgroundDate = lastBackgroundDate
    }

    EmployeePRAFeedVO(String slid, Date lastBackgroundDate) {
        this.slid = slid
        this.lastBackgroundDate = lastBackgroundDate
    }

    EmployeePRAFeedVO(GroovyRowResult rowResult) {
        pernr = rowResult.getProperty('pernr') ? rowResult.getProperty('pernr') as Long : null
        lastBackgroundDate = rowResult.getProperty('last_bg_check') as Date
    }

    EmployeePRAFeedVO(String rowResult) {
        List<String> tokenizedString = rowResult.split('\\|').toList()*.trim()
        pernr = tokenizedString[1].isNumber() ? tokenizedString[1].toLong() : null
        lastBackgroundDate = tokenizedString[7] ? new Date().parse('MM/dd/yy hh:mm:ss', tokenizedString[7]) : null
        slid = tokenizedString[0] ? tokenizedString[0].trim() : null
    }

    String toString() {
        return ("${pernr ?: ''} : ${lastBackgroundDate} : ${slid ?: ''}")
    }

    WorkerPraCertificationInfo toWorkerPraCertificationInfo() {
        return (new WorkerPraCertificationInfo(pernr, lastBackgroundDate))
    }

    WorkerPraCertificationInfo toEmployeePraCertificationInfo() {
        return (new WorkerPraCertificationInfo(slid, lastBackgroundDate))
    }

}