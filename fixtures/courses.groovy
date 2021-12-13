import com.force5solutions.care.cc.Course

pre {
    if (!Course.count()) {
        println "Populating Sample Courses"
        populateSampleCourses(5);
    }
}

fixture {}

public void populateSampleCourses(Integer count) {
    count.times {
        Course course = new Course(name: "Course-${it + 1}", number: "CN-${it + 1}")
        if (new Random().nextBoolean()) {
            course.startDate = (new Date() - 100) - new Random().nextInt(100)
        }
        if (new Random().nextBoolean()) {
            course.endDate = (new Date() + 10) + new Random().nextInt(100)
        }
        course.s()
    }
}
