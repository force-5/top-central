package com.force5solutions.care.cc

import grails.test.*
import org.codehaus.groovy.runtime.TimeCategory
import groovy.time.BaseDuration
import com.force5solutions.care.common.MetaClassHelper

class WorkerCertificationTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        MetaClassHelper.enrichClasses()
        mockDomain(Employee.class)
        mockDomain(Certification.class)
        mockDomain(WorkerCertification.class)
        mockDomain(Course.class)
        mockDomain(Person.class)
        mockDomain(WorkerCourse.class)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testExpiryDate_NO_COURSE() {
        use(org.codehaus.groovy.runtime.TimeCategory) {

            Person person = createPerson()
            Employee employee = createEmployee(person)
            Certification certification = createCertification()

            def date = new Date()
            WorkerCertification workerCertification = createWorkerCertification(employee, certification, date)
            assertEquals((date + 5.months).toString(), workerCertification.getExpiry().toString())
        }
    }

    void testExpiryDate_COURSE_WITH_END_DATE_BUT_COURSE_NOT_ASSIGNED_TO_WORKER() {
        use(org.codehaus.groovy.runtime.TimeCategory) {
            Person person = createPerson()
            Employee employee = createEmployee(person)
            Certification certification = createCertification()

            def date = new Date()
            Course course = createCourse(date + 5.days)

            certification.courses << course
            certification.save(failOnError: true)

            WorkerCertification workerCertification = createWorkerCertification(employee, certification, date)
            assertEquals((date + 5.months).toString(), workerCertification.getExpiry().toString())
        }
    }

    void testExpiryDate_COURSE_WITH_END_DATE_AND_COURSE_ASSIGNED_TO_WORKER() {
        use(org.codehaus.groovy.runtime.TimeCategory) {
            def date = new Date()
            Person person = createPerson()
            Employee employee = createEmployee(person)
            Certification certification = createCertification()
            Course course = createCourse(date + 1.year)

            certification.courses << course
            createWorkerCourse(employee, course, date)
            certification.save(failOnError: true)
            employee.save(failOnError: true)

            WorkerCertification workerCertification = createWorkerCertification(employee, certification, date)
            assertEquals((date + 5.months).toString(), workerCertification.getExpiry().toString())
        }
    }

    void testExpiryDate_TWO_COURSES_WITH_END_DATE_AND_COURSES_ASSIGNED_TO_WORKER() {
        use(org.codehaus.groovy.runtime.TimeCategory) {
            Person person = createPerson()
            Employee employee = createEmployee(person)
            Certification certification = createCertification()
            def date = new Date()
            Course course1 = createCourse(date + 7.days)
            Course course2 = createCourse(date + 3.months)

            certification.courses << course1
            certification.courses << course2
            employee.courses.add(createWorkerCourse(employee, course1, date))
            employee.courses.add(createWorkerCourse(employee, course2, date))

            certification.save(failOnError: true)
            employee.save(failOnError: true)

            WorkerCertification workerCertification = createWorkerCertification(employee, certification, date)
            assertEquals((date + 3.months).toString(), workerCertification.getExpiry().toString())
        }
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

    public Certification createCertification() {
        Certification certification = new Certification()
        certification.name = "Java 1.5"
        certification.period = 5
        certification.periodUnit = PeriodUnit.MONTHS
        return certification
    }

    public Course createCourse(Date endDate = null) {
        Course course = new Course()
        course.name = "Exception Handling"
        course.number = "EX101"
        course.endDate = endDate
        course.save(failOnError: true)
        return course
    }

    public WorkerCertification createWorkerCertification(Worker worker, Certification certification, Date dateCompleted) {
        WorkerCertification workerCertification = new WorkerCertification()
        workerCertification.worker = worker
        workerCertification.certification = certification
        workerCertification.dateCompleted = dateCompleted
        workerCertification.save(failOnError: true)
        return workerCertification
    }

    public WorkerCourse createWorkerCourse(Worker worker, Course course, Date dateCompleted) {
        WorkerCourse workerCourse = new WorkerCourse()
        workerCourse.worker = worker
        workerCourse.course = course
        workerCourse.dateCompleted = dateCompleted
        workerCourse.s()
        return workerCourse
    }

}
