import com.force5solutions.care.cc.Employee
import com.force5solutions.care.cc.Person
import com.force5solutions.care.cc.EmployeeSupervisor

fixture {

    employeeOne(Employee) {
        title = "title"
        department = "IT"
        supervisor = EmployeeSupervisor.findBySlid("emp-supervisor-2")
        person = Person.findBySlid("admin5")
    }

    employeeTwo(Employee) {
        title = "title"
        department = "IT"
        supervisor = EmployeeSupervisor.findBySlid("admin5")
        person = Person.findBySlid("admin1")
    }
}