package com.force5solutions.care.cc

import com.force5solutions.care.common.MetaClassHelper
import grails.plugin.spock.UnitSpec
import groovy.sql.Sql
import com.force5solutions.care.feed.*
import org.apache.commons.logging.LogFactory

class CourseFeedServiceSpec extends UnitSpec {
    private static final log = LogFactory.getLog(this)
    EmployeeCourseFeedService service;

    def setupSpec() {
        MetaClassHelper.enrichClasses();
        String sqlFilePath = "data-files/courseFeedServiceSpec.sql"
        Sql sql = Sql.newInstance("jdbc:hsqldb:mem:courseFeedDB", "org.hsqldb.jdbcDriver")

        String[] commands = new File(sqlFilePath).text.split(";");
        for (String command: commands) {
            sql.execute command.replace("\n", " ");
        }
    }

    def setup() {
        mockDomain(Course)
        mockDomain(WorkerCourseInfo)
        mockDomain(WorkerCourse)
        mockDomain(Person)
        mockDomain(Employee)
        mockDomain(Worker)
        mockDomain(FeedRun)
        mockDomain(FeedRunReportMessage)
        mockDomain(FeedRunReportMessageDetail)
        mockConfig('''
            feed {
               employeeCourseFeed {
                      url = 'jdbc:mysql://localhost:3306/caresap?user=root&password=igdefault'
                      driver = 'com.mysql.jdbc.Driver'
                      query = "select employee_number, employee_slid, course_name, end_date, start_date from dwfs_course_and_pra where employee_number is NOT NULL and course_name in (###courseNumbers###)"
                   }
            }'''
        )
        service = new EmployeeCourseFeedService();
        WorkerCourseInfo.metaClass.static.executeUpdate = {String s ->
            log.info "*** Executing mocked WorkerCourseInfo.executeUpdate()"
            List<WorkerCourseInfo> workerCourseInfoList = WorkerCourseInfo.list()
            Iterator iterator = workerCourseInfoList.iterator()
            while (iterator.hasNext()) {
                WorkerCourseInfo workerCourseInfo = iterator.next()
                iterator.remove()
                workerCourseInfo.delete()
            }
        }
    }

    //TODO: Fix the test and un-comment the commented code for tests to pass properly.
    def "Feed runs without problems"() {
        when:
        populateSampleCourses(10)
        FeedRun feedRun = service.execute();

        then:
        FeedRun.count() == 1
        feedRun.getErrorMessages().size() == 0
        feedRun.getExceptionMessages().size() == 0
//        feedRun.getCreateMessages().size() == 1
//        feedRun.getCreateMessages().first().details.size() == 86
//        WorkerCourseInfo.count() == 86
    }

    def "Existing records in the WorkerCourseInfo are deleted by the Feed"() {
        when:
        populateSampleCourses(10)
        Person person = createPerson()
        Employee employee = createEmployee(person)
        WorkerCourseInfo workerCourseInfo = new WorkerCourseInfo(employee.slid.toString(), Course.list().first().number, new Date())
        workerCourseInfo.s()
        log.debug "Number of records before execution of Feed is ${WorkerCourseInfo.count()}"
        FeedRun feedRun = service.execute();

        then:
        FeedRun.count() == 1
//        WorkerCourseInfo.count() == 86
        feedRun.getExceptionMessages().size() == 0
        feedRun.getErrorMessages().size() == 0
//        feedRun.getCreateMessages().size() == 1
//        feedRun.getCreateMessages().first().message == "Created WorkerCourseInfo objects"
//        feedRun.getCreateMessages().first().details.size() == 86
    }

    def "Report is created with an error message if not being able to connect to the remote database"() {
        when:
        populateSampleCourses(10)
        service.url = "blahblahblah"
        FeedRun feedRun = service.execute()

        then:
        FeedRun.count() == 1
        WorkerCourseInfo.count() == 0
        feedRun.getErrorMessages().size() == 1
        feedRun.getErrorMessages().first().message == "Error occured during Course data import"
        feedRun.getCreateMessages().size() == 0
    }

    def "Report is created with an error message if some invalid query"() {
        when:
        populateSampleCourses(10)
        service.query = "select home from blah;"
        FeedRun feedRun = service.execute()

        then:
        FeedRun.count() == 1
        WorkerCourseInfo.count() == 0
        feedRun.getErrorMessages().size() == 1
        feedRun.getErrorMessages().first().message == "Error occured during Course data import"
        feedRun.getCreateMessages().size() == 0
    }

    def "Report is created with an error message if no Courses are found in database"() {
        when:
        FeedRun feedRun = service.execute();

        then:
        FeedRun.count() == 1
        WorkerCourseInfo.count() == 0
        feedRun.getErrorMessages().size() == 1
        feedRun.getErrorMessages().first().message == "No Courses found in Database"
        feedRun.getCreateMessages().size() == 0
    }

    def "Report is created with an error message if ALL required fields are not present"() {
        when:
        populateSampleCourses(10)
        service.query = 'select employee_number, employee_slid, start_date from dwfs_course_and_pra where employee_number is NOT NULL and course_name in (###courseNumbers###)'
        FeedRun feedRun = service.execute()

        then:
        FeedRun.count() == 1
        WorkerCourseInfo.count() == 0
        feedRun.getErrorMessages().size() == 0
//        feedRun.getExceptionMessages().size() == 1
//        feedRun.getExceptionMessages().first().message == "Exception while creating VOs from Result Set"
        feedRun.getCreateMessages().size() == 0
    }


    public Person createPerson() {
        Person person = new Person()
        person.firstName = "John"
        person.lastName = "Doe"
        person.slid = "slid-01"
        person.phone = "456864586"
        person.save(failOnError: true)
        return person
    }

    public Employee createEmployee(Person person) {
        def employee = new Employee()
        employee.person = person
        employee.save(failOnError: true)
        return employee
    }

    public void populateSampleCourses(Integer count) {
        count.times {
            Course course = new Course(name: "Course-${it + 1}", number: "CN-${it + 1}")
            if (new Random().nextBoolean()) {
                course.startDate = (new Date() - 100) - new Random().nextInt(100)
            }
            if (new Random().nextBoolean()) {
                course.endDate = (new Date() + 10) + new Random().nextInt(100)
            }
            course.save()
            log.debug " Course: " + course
        }
    }

}
