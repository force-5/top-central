package com.force5solutions.care.feed

import groovy.sql.GroovyRowResult
import java.text.SimpleDateFormat

class ContractorPraAndCourseFeedVO {

    String contractorNumber
    String contractorSlid
    String courseName
    Date startDate
    Date endDate

    ContractorPraAndCourseFeedVO() {}

    ContractorPraAndCourseFeedVO(GroovyRowResult rowResult) {
        courseName = rowResult.containsKey('course_name') ? rowResult.getProperty('course_name') as String : null
        contractorNumber = rowResult.containsKey('contractor_number') ? rowResult.getProperty('contractor_number') as String : null
        startDate = rowResult.containsKey('start_date') ? rowResult.getProperty('start_date') as Date : null
        endDate = rowResult.containsKey('end_date') ? rowResult.getProperty('end_date') as Date : null
        contractorSlid = rowResult.containsKey('contractor_slid') ? rowResult.getProperty('contractor_slid') as String : null
    }

    ContractorPraAndCourseFeedVO(String rowResult) {
        List<String> tokenizedString = rowResult.split('\\|').toList()*.trim()
        courseName = tokenizedString[5] ?: null
        contractorNumber = tokenizedString[1] ?: null
        startDate = tokenizedString[7] ? new SimpleDateFormat('MM/dd/yy h:mm').parse(tokenizedString[7]) : null
        endDate = tokenizedString[8] ? new SimpleDateFormat('MM/dd/yy h:mm').parse(tokenizedString[8]) : null
        contractorSlid = (!tokenizedString[0]?.equalsIgnoreCase('null')) ? tokenizedString[0] : null
    }


    ContractorPraAndCourseInfo toContractorPraAndCourseInfo() {
        ContractorPraAndCourseInfo contractorPraAndCourseInfo = new ContractorPraAndCourseInfo()
        contractorPraAndCourseInfo.courseName = courseName
        contractorPraAndCourseInfo.contractorNumber = contractorNumber
        contractorPraAndCourseInfo.startDate = startDate
        contractorPraAndCourseInfo.endDate = endDate
        contractorPraAndCourseInfo.contractorSlid = contractorSlid?.toUpperCase()
        return contractorPraAndCourseInfo
    }
}
