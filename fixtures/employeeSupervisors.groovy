import com.force5solutions.care.cc.EmployeeSupervisor
import com.force5solutions.care.cc.Person

fixture {

    employeeSupervisorOne(EmployeeSupervisor) {
        person = Person.findBySlid("admin5")
    }

    employeeSupervisorTwo(EmployeeSupervisor) {
        person = Person.findBySlid("emp-supervisor-2")
    }
}