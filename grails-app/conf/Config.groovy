grails.config.locations = [
        "file:${appName}-config.groovy",
        "classpath:${appName}-config.groovy"
]
encryptionProperties = ['snmpServerUrl']
farAheadCronExpression = '0 0 0 1 1 ? 2050'
rssFeedCacheTimeOut = (1000 * 60 * 60) //1 hour in milliseconds
feed {
    hrInfoFeed {
        url = 'jdbc:mysql://localhost:3306/caresap?user=root&password=igdefault'
        driver = 'com.mysql.jdbc.Driver'
        query = '''
select PERNR, SLID_ID, FULL_NAME, FIRST_NAME, LAST_NAME, PERSON_STATUS, ORGUNIT_NUM, ORGUNIT_DESC, POSITION_TITLE, POSITION_NUM, CELL_PHONE_NUM, OFFICE_PHONE_NUM, PAGER_NUM, PERS_AREA_NUM, PERS_AREA_DESC, PERS_SUBAREA_NUM, PERS_SUBAREA_DESC, SUPV_SLID_ID, SUPV_FULL_NAME, SUPVSUPV_SLID_ID, SUPVSUPV_FULL_NAME, UPDATE_DT, INSERT_DT from hr_info
'''
    }
    employeeCourseFeed {
        url = 'jdbc:mysql://localhost:3306/caresap?user=root&password=igdefault'
        driver = 'com.mysql.jdbc.Driver'
        query = "select LOGIN_ID, ID_CRS, DT_CRS_SESN_END from FERC_CLASS where LOGIN_ID is not null and ID_CRS in (###courseNumbers###)"
    }
    praFeed {
        url = 'jdbc:mysql://localhost:3306/caresap?user=root&password=igdefault'
        driver = 'com.mysql.jdbc.Driver'
        query = 'select pernr, last_bg_check from BACKGROUND_CHECK where nerc_loc_code = "Fortney" and PRA_status = "Active"'
    }
    activeWorkerFeed {
        url = 'jdbc:mysql://localhost:3306/caresap?user=root&password=igdefault'
        driver = 'com.mysql.jdbc.Driver'
        query = 'select SLID_ID, PERNR, FULL_NAME, POSITION_TITLE, SUPV_FULL_NAME, PERS_SUBAREA_DESC, EMAIL, ACCESS_TYPE, PRA_STATUS, BADGE_NUMBER from F5_SCC_CCAUSERS'
    }

    contractorHrInfoFeed {
        url = 'jdbc:mysql://localhost:3306/caresap?user=root&password=igdefault'
        driver = 'com.mysql.jdbc.Driver'
        query = 'select id, business_unit, contractor_number, contractor_slid, email, emergency_contact, first_name, last_name, personal_email, supervisor_slid, vendor_name from dwfs_contractor_hr_info'
    }
    contractorPRAAndCourseFeed {
        url = 'jdbc:mysql://localhost:3306/caresap?user=root&password=igdefault'
        driver = 'com.mysql.jdbc.Driver'
        query = "select contractor_number, contractor_slid, course_name, end_date, start_date from dwfs_course_and_pra where contractor_number is NOT NULL"
    }
    employeePraFeed {
        url = 'jdbc:mysql://localhost:3306/caresap?user=root&password=igdefault'
        driver = 'com.mysql.jdbc.Driver'
        query = "select userid, end_date as last_bg_check from ARBT_USR_ENT_DTLS where status = 'Y' and course_name = 'EMPLOYEE PRA'"
    }
    contractorHrInfoFileFeed {
        fileName = 'EAMS_CS_SCC_Feeds_CHI.csv'
    }
    contractorCourseFileFeed {
        fileName = 'EAMS_CS_SCC_Feeds_Course_PRA.csv'
    }
    employeePraFileFeed {
        fileName = 'EAMS_CS_SCC_Feeds_Course_PRA.csv'
    }
    employeeCourseFileFeed {
        fileName = 'EAMS_CS_SCC_Feeds_Course_PRA.csv'
    }
}

grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [html: ['text/html', 'application/xhtml+xml'],
        xml: ['text/xml', 'application/xml'],
        text: 'text/plain',
        js: 'text/javascript',
        rss: 'application/rss+xml',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        all: '*/*',
        json: ['application/json', 'text/json'],
        form: 'application/x-www-form-urlencoded',
        multipartForm: 'multipart/form-data'
]
// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
homepageController = 'centralWorkflowTask'
homepageAction = 'list'

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
        cc.configFilePath = "/config/configProperties-development.groovy"
    }

    demo {
        grails.serverURL = "http://localhost:8080/care"
        cc.configFilePath = "/config/configProperties-development.groovy"
    }

    test {
        grails.serverURL = "http://test.care.force5solutions.com"
        cc.configFilePath = "/config/configProperties-test.groovy"
    }

    qa {
        grails.serverURL = "http://qa.care.force5solutions.com"
        cc.configFilePath = "/config/configProperties-qa.groovy"
    }

    mssql {
        grails.serverURL = "http://localhost:8080/${appName}"
        cc.configFilePath = "/config/configProperties-mssql.groovy"
    }

    production {
        grails.serverURL = "http://localhost:8080/${appName}"
        cc.configFilePath = "/config/configProperties-production.groovy"
    }


}

log4j = {
    appenders {
        environments {
            production {
                rollingFile name: "myAppender", maxFileSize: 26214400, maxBackupIndex: 10, file: "/var/log/tomcat6/care.log"
                rollingFile name: "stacktrace", maxFileSize: 26214400, file: "/var/log/tomcat6/stacktrace_care.log"
            }
            demo {
                rollingFile name: "myAppender", maxFileSize: 26214400, maxBackupIndex: 10, file: "/var/log/tomcat6/care.log"
                rollingFile name: "stacktrace", maxFileSize: 26214400, file: "/var/log/tomcat6/stacktrace_care.log"
            }
        }
    }

    error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.springframework'

    warn 'org.mortbay.log'

    debug 'grails.app.controller',
            'grails.app.service.com.force5solutions',
            'grails.app.task.com.force5solutions',
            'org.codehaus.groovy.grails.plugins.com.force5solutions.care',
            'grails.app.filters',
            'org.codehaus.groovy.grails.web.taglib',
            'grails.app.tagLib',
            'com.force5solutions.care'
}

