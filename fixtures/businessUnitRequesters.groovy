import com.force5solutions.care.cc.BusinessUnitRequester
import com.force5solutions.care.cc.EmployeeSupervisor
import com.force5solutions.care.cc.BusinessUnit
import com.force5solutions.care.cc.Person
import com.force5solutions.care.cc.EmployeeUtilService

pre {
    if (!BusinessUnitRequester.count()) {
        println "Populating Sample Business Unit Requesters"
        BusinessUnit unit = new BusinessUnit(name: 'BU-1').s()
        EmployeeUtilService employeeUtilService = new EmployeeUtilService()
        5.times {
            Person person = employeeUtilService.findPerson('BUR-' + (it + 1))
            BusinessUnitRequester businessUnitRequester = new BusinessUnitRequester()
            businessUnitRequester.unit = unit
            businessUnitRequester.person = person
            businessUnitRequester.person.s()
            businessUnitRequester.s()
        }
    }
}

fixture {}

