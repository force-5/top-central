import com.force5solutions.care.cc.ContractorSupervisor
import com.force5solutions.care.cc.Person
import com.force5solutions.care.cc.Vendor

pre {
    if (!ContractorSupervisor.count()) {
        println "Populating Sample Supervisors"
        populateSampleSupervisors();
    }
}

fixture {}

void populateSampleSupervisors() {
    (1..5).each {
        ContractorSupervisor supervisor = new ContractorSupervisor()
        Person person = new Person()
        person.firstName = "supervisor$it"
        person.middleName = "supervisor$it"
        person.lastName = "supervisor$it"
        person.email = createEmailId("supervisor$it")
        person.phone = "1234567890$it"
        person.s()
        Vendor vendor = Vendor.list().get(it - 1)
        supervisor.vendor = vendor
        supervisor.person = person
        supervisor.s()
    }
}

String createEmailId(String name) {
    return 'care.force5+' + name.replaceAll(' ', '') + '@gmail.com'
}