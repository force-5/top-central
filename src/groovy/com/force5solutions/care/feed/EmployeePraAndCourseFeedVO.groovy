package com.force5solutions.care.feed

import com.force5solutions.care.cc.Course
import groovy.sql.GroovyRowResult

class EmployeePraAndCourseFeedVO {

    String employeeSlid
    String courseNumber
    String courseName
    Date completionDate

    EmployeePraAndCourseFeedVO() {}

    public String getWorkerSlid() {
        return (employeeSlid?.toUpperCase())
    }

    EmployeePraAndCourseFeedVO(GroovyRowResult rowResult) {
        courseNumber = rowResult.containsKey('ID_CRS') ? rowResult.getProperty('ID_CRS') as String : null
        completionDate = rowResult.containsKey('DT_CRS_SESN_END') ? rowResult.getProperty('DT_CRS_SESN_END') as Date : null
        employeeSlid = rowResult.containsKey('LOGIN_ID') ? rowResult.getProperty('LOGIN_ID') as String : null
    }

    EmployeePraAndCourseFeedVO(String rowResult) {
        List<String> tokenizedString = rowResult.split('\\|').toList()*.trim()
        courseName = tokenizedString[5] ? tokenizedString[5].trim() : null
        completionDate = tokenizedString[7] ? new Date().parse('MM/dd/yy hh:mm:ss', tokenizedString[7]) : null
        employeeSlid = tokenizedString[0] ? tokenizedString[0].trim() : null
    }

    WorkerCourseInfo toWorkerCourseInfo() {
        return (new WorkerCourseInfo(employeeSlid, courseNumber, courseName, completionDate))
    }
}
