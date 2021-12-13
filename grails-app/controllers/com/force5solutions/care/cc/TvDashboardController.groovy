package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.common.CareConstants

class TvDashboardController {

    def dashboardService
    def rssFeedsService
    static config = ConfigurationHolder.config


    def index = {
        render(view: 'index', model: [workerType: CareConstants.WORKER_TYPE_EMPLOYEE, viewType: CareConstants.VIEW_TYPE_GLOBAL])
    }

    def contractorTvDashboard = {
        render(view: 'index', model: [workerType: CareConstants.WORKER_TYPE_CONTRACTOR, viewType: CareConstants.VIEW_TYPE_GLOBAL])
    }
}

