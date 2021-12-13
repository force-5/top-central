package com.force5solutions.care.cc

import com.force5solutions.care.feed.WorkerCourseInfo

class WorkerCourseService {

    def workerCertificationService

    /**
     * Test Method. Not used in production code.
     */
    public void addCourses(Worker worker, List<Course> courses, Date date = new Date()) {
        courses.each {
            addCourse(worker, it, date)
        }
    }

    public WorkerCourse addCourse(Worker worker, Course course, Date completionDate = new Date()) {
        WorkerCourse workerCourse = new WorkerCourse(worker, course, completionDate).s()
        List<Certification> certifications = Certification.list().findAll {certification -> course in certification.courses}
        certifications.each {Certification certification ->
            List<WorkerCourse> completedCourses = WorkerCourse.findAllByWorkerAndCourseInList(worker, certification.courses as List)
            List<WorkerCourse> effectiveWorkerCourses = completedCourses.groupBy {it.course}.values().collect { List<WorkerCourse> workerCourses ->
                return workerCourses.max {it.dateCompleted}
            }
            Date effectiveCompletionDate = (effectiveWorkerCourses*.dateCompleted).max {it}
            workerCertificationService.saveNewWorkerCertificationWithDateCompleted(worker.id, certification.id, effectiveCompletionDate)
        }
        return workerCourse
    }

    public void addCoursesFromFeedToWorker(Worker worker) {
        String workerSlid = worker.slid
        if (workerSlid) {
            List<Course> courses = Course.list()
            List<WorkerCourseInfo> courseInfos = WorkerCourseInfo.findAllByEmployeeSlid(workerSlid)
            Set<WorkerCourse> workerCourses = worker.courses
            courseInfos?.each {WorkerCourseInfo courseInfo ->
                WorkerCourse workerCourse
                if (workerCourses) {
                    workerCourse = workerCourses.findAll {it.course.number == courseInfo.courseNumber}.max {it.id}
                }
                if (!workerCourse || (workerCourse.dateCompleted != courseInfo.completionDate)) {
                    Course course = courses.find {it.number == courseInfo.courseNumber}
                    if (course) {
                        addCourse(worker, course, courseInfo.completionDate)
                        worker.refresh()
                    }
                }
            }
        }
    }
}
