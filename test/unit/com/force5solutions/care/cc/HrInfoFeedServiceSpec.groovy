package com.force5solutions.care.cc

import grails.plugin.spock.*

import com.force5solutions.care.common.MetaClassHelper
import com.force5solutions.care.feed.*
import groovy.sql.Sql
import org.apache.commons.logging.LogFactory

class HrInfoFeedServiceSpec extends UnitSpec {

    HrInfoFeedService service;
    private static final log = LogFactory.getLog(this)
    def setupSpec() {
        MetaClassHelper.enrichClasses();
        String sqlFilePath = "data-files/hrInfoFeedServiceSpec.sql"
        Sql sql = Sql.newInstance("jdbc:hsqldb:mem:devDB", "org.hsqldb.jdbcDriver")

        String[] commands = new File(sqlFilePath).text.split(";");
        for (String command: commands) {
            sql.execute command.replace("\n", " ");
        }
    }

    def setup() {
        mockDomain(EmployeeSupervisor)
        mockDomain(Employee)
        mockDomain(BusinessUnitRequester)
        mockDomain(Worker)
        mockDomain(HrInfo)
        mockDomain(FeedRun)
        mockDomain(FeedRunReportMessage)
        mockDomain(FeedRunReportMessageDetail)
        mockLogging(HrInfoFeedService)
        mockLogging(DatabaseFeedService)
        mockConfig('''
            hrInfoMandatoryFields = 'pernr'
            feed {
                hrInfoFeed {
                    url = 'jdbc:hsqldb:mem:devDB'
                    driver = 'org.hsqldb.jdbcDriver'
                    query = 'select PERNR, SLID_ID, FULL_NAME, FIRST_NAME, LAST_NAME, PERSON_STATUS, ORGUNIT_NUM, ORGUNIT_DESC, POSITION_TITLE, CELL_PHONE_NUM, OFFICE_PHONE_NUM, PAGER_NUM, PERS_AREA_NUM, PERS_AREA_DESC, PERS_SUBAREA_NUM, PERS_SUBAREA_DESC, SUPV_SLID_ID, SUPV_FULL_NAME, SUPVSUPV_SLID_ID, SUPVSUPV_FULL_NAME, UPDATE_DT, INSERT_DT from hr_info'
                }
            }'''
        )
        service = new HrInfoFeedService();
        HrInfo.metaClass.static.executeUpdate = {String s ->
            log.info "*** Executing mocked HrInfo.executeUpdate()"
            List<HrInfo> hrInfos = HrInfo.list()
            Iterator iterator = hrInfos.iterator()
            while(iterator.hasNext()){
                HrInfo hrInfo = iterator.next()
                iterator.remove()
                hrInfo.delete()
            }
        }
    }

    def "Feed runs without problems"() {
        when:
        FeedRun feedRun = service.execute();

        then:
        FeedRun.count() == 1
        feedRun.getErrorMessages().size() == 0
        feedRun.getExceptionMessages().size() == 0
        feedRun.getCreateMessages().size() == 1
        feedRun.getCreateMessages().first().numberOfRecords == 36
        HrInfo.count() == 36
    }

    def "Existing records in the HR_INFO are deleted by the Feed"() {
        when:
        HrInfo hrInfo = new HrInfo();
        hrInfo.pernr = 1l;
        hrInfo.s();
        log.debug "Number of records before execution of Feed is ${HrInfo.count()}"
        FeedRun feedRun = service.execute();

        then:
        FeedRun.count() == 1
        HrInfo.count() == 36
        feedRun.getExceptionMessages().size() == 0
        feedRun.getErrorMessages().size() == 0
        feedRun.getCreateMessages().size() == 1
        feedRun.getCreateMessages().first().numberOfRecords == 36
    }

    def "Report is created with an error message if not being able to connect to the remote database"() {
        when:
        service.url = "blahblahblah"
        FeedRun feedRun = service.execute()

        then:
        FeedRun.count() == 1
        HrInfo.count() == 0
        feedRun.getErrorMessages().size() == 1
        feedRun.getCreateMessages().size() == 0
    }

    def "Report is created with an error message if some invalid query"() {
        when:
        service.query = "select home from blah;"
        FeedRun feedRun = service.execute()

        then:
        FeedRun.count() == 1
        HrInfo.count() == 0
        feedRun.getErrorMessages().size() == 1
        feedRun.getCreateMessages().size() == 0
    }

    def "Report is created with an error message if ALL required fields are not present"() {
        when:
        service.query = 'select FULL_NAME, FIRST_NAME, LAST_NAME, OFFICE_PHONE_NUM, PAGER_NUM, SUPV_SLID_ID, SUPV_FULL_NAME, SUPVSUPV_SLID_ID, SUPVSUPV_FULL_NAME, UPDATE_DT, INSERT_DT from hr_info'
        FeedRun feedRun = service.execute()

        then:
        FeedRun.count() == 1
        HrInfo.count() == 0
        feedRun.getExceptionMessages().size() == 1
        feedRun.getCreateMessages().size() == 0
    }

}
