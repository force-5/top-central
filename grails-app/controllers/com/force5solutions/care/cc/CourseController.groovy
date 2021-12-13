package com.force5solutions.care.cc

import com.force5solutions.care.common.Secured
import com.force5solutions.care.ldap.Permission

class CourseController {

    def scaffold = true

    def centralUtilService

    @Secured(value = Permission.READ_COURSE)
    def list = {
        List<Course> courseList = []
        Integer courseInstanceTotal = null
        params.order = params.order ?: 'asc'
        params.sort = params.sort ?: 'name'
        Integer offset = params.offset ? params.offset.toInteger() : 0
        params.max = centralUtilService.updateAndGetDefaultSizeOfListViewInConfig(params)
        if (!params?.max?.toString()?.equalsIgnoreCase('Unlimited')) {
            courseList = Course.list(params)
            courseInstanceTotal = Course.count()
        } else {
            courseList = Course.list()
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render template: 'coursesTable', model: [courseInstanceList: courseList, courseInstanceTotal: courseInstanceTotal, offset: offset, max: params.max, order: params.order, sort: params.sort]
        } else {
            [courseInstanceList: courseList, courseInstanceTotal: courseInstanceTotal, offset: offset, max: params.max, order: params.order, sort: params.sort]
        }
    }
}
