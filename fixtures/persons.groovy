import com.force5solutions.care.cc.Person

fixture {

    supervisorPersonOne(Person) {
        firstName = "EMP_SUP_FN1"
        lastName = "EMP_SUP_LN1"
        slid = "admin5"
        phone = "4445557777"
    }

    supervisorPersonTwo(Person) {
        firstName = "EMP_SUP_FN2"
        lastName = "EMP_SUP_LN2"
        slid = "emp-supervisor-2"
        phone = "555666777"
    }

    employeePerson(Person) {
        firstName = "Emp-FN-2"
        lastName = "Emp-LN-2"
        slid = "admin1"
        phone = "5554446333"
    }

    adminAsPerson(Person) {
        firstName = "Emp-FN-1"
        lastName = "Emp-LN-1"
        slid = "admin"
        phone = "55546333"
    }
}