import grails.util.GrailsUtil
import com.force5solutions.care.cc.Contractor
import com.force5solutions.care.cc.BusinessUnitRequester
import com.force5solutions.care.cc.ContractorSupervisor
import org.codehaus.groovy.grails.commons.GrailsApplication
import com.force5solutions.care.cc.Vendor
import com.force5solutions.care.cc.Person
import com.force5solutions.care.cc.TrainingTestStatus
import com.force5solutions.care.cc.CertificationStatus
import com.force5solutions.care.cc.WorkerCertification
import com.force5solutions.care.cc.Certification
import com.force5solutions.care.cc.Worker
import com.force5solutions.care.cc.WorkerEntitlementRole
import org.codehaus.groovy.grails.commons.ApplicationHolder
import com.force5solutions.care.cc.CentralDataFile

pre {
    if (!Contractor.count()) {
        if (GrailsUtil.environment == "qa") {
            println "Populating Sample Contractors for QA Environement"
            (1..5).each {
                populateSampleContractors(it)
                println "Created Contractor - ${it}"
            }
        }
        else {
            println "Populating Sample Contractors for Enviroment other than QA"
            (1..10).each {
                populateSampleContractors(it);
                println "Created Contractor - ${it}"
            }
        }
    }
}

fixture {}


void populateSampleContractors(Integer it) {
    def auditService = ApplicationHolder.getApplication().getMainContext().getBean('auditService')

    Vendor vendor = Vendor.list().get(new Random().nextInt(Vendor.count()))
    Contractor contractor = new Contractor()
    Person person = new Person()
    person.firstName = "first-Contractor$it"
    person.middleName = "middle-Contractor$it"
    person.lastName = "last-Contractor$it"
    person.phone = "6234567899$it"
    person.email = createEmailId("Contractor$it")
    person.notes = "Contractor notes-$it"
    person.s()
    contractor.person = person
    contractor.formOfId = "I-Card-$it"
    contractor.badgeNumber = "12345$it"
    contractor.birthDay = new Random().nextInt(27) + 1
    contractor.birthMonth = new Random().nextInt(11) + 1
    contractor.workerImage = populateContractorImage(it)

    List<ContractorSupervisor> supervisors = ContractorSupervisor.list()
    ContractorSupervisor supervisor = supervisors.get(new Random().nextInt(supervisors.size()))
    contractor.supervisor = supervisor
    contractor.primeVendor = contractor.supervisor.vendor

    List<BusinessUnitRequester> businessUnitRequesters1 = BusinessUnitRequester.list()

    Set<BusinessUnitRequester> businessUnitRequesters = []
    (new Random().nextInt(5) + 1).times {
        businessUnitRequesters.add(businessUnitRequesters1.get(new Random().nextInt(businessUnitRequesters1.size())))
    }
    contractor.businessUnitRequesters = businessUnitRequesters
    auditService.saveWorker(contractor)
    if ((GrailsUtil.environment != GrailsApplication.ENV_DEVELOPMENT) && (GrailsUtil.environment != GrailsApplication.ENV_TEST)) {
        contractor.certifications = populateContractorCertifications(contractor)
    }
}

Set<WorkerCertification> populateContractorCertifications(Worker worker) {
    def auditService = ApplicationHolder.getApplication().getMainContext().getBean('auditService')
    List<WorkerCertification> workerCertifications = []
    Set<WorkerEntitlementRole> entitlementRoles = worker.entitlementRoles
    Set<Certification> completedCertifications = (entitlementRoles*.entitlementRole.requiredCertifications + entitlementRoles*.entitlementRole.inheritedCertifications).flatten()
    completedCertifications = completedCertifications.grep {it}
    completedCertifications.each {Certification certification ->
        (1..new Random().nextInt(4)).each {Integer counter ->
            WorkerCertification workerCertification = createWorkerCertification(worker, certification)
            workerCertification.dateCompleted = new Date() - new Random().nextInt(50)
            workerCertifications.add(workerCertification)
            sleep(1000)
        }

        if (new Random().nextBoolean()) {
            WorkerCertification workerCertification = createWorkerCertification(worker, certification)
            workerCertifications.add(workerCertification)
        }

    }

    workerCertifications.each { auditService.saveWorkerCertification(it) }
    return workerCertifications as Set
}

public createWorkerCertification(Worker worker, Certification certification) {
    WorkerCertification workerCertification = new WorkerCertification()
    workerCertification.certification = certification

    if (certification.affidavitRequired) {
        Set<CentralDataFile> workerAffidavits = populateAffidavits(new Random().nextInt(10))
        workerCertification.affidavits = workerAffidavits
    }

    if (certification.trainingRequired) {
        CertificationStatus trainingStatus = new CertificationStatus()
        trainingStatus.status = TrainingTestStatus.values(new Random().nextInt(3)).toString()
        trainingStatus.date = new Date() - new Random().nextInt(50)
        trainingStatus.provider = (certification.trainingProviders.split(',') as List).get(new Random().nextInt(certification.trainingProviders.split(',').size()))
        workerCertification.trainingStatus = trainingStatus
        trainingStatus.s()
    }
    if (certification.testRequired) {
        CertificationStatus testStatus = new CertificationStatus()
        testStatus.status = TrainingTestStatus.values(new Random().nextInt(3))
        testStatus.date = new Date() - new Random().nextInt(50)
        List<String> testProvidersList = certification.testProviders.split(',') as List
        testStatus.provider = (testProvidersList).get(new Random().nextInt(testProvidersList.size()))
        workerCertification.testStatus = testStatus
        testStatus.s()
    }
    if (certification.subTypeRequired) {
        workerCertification.subType = (certification.subTypes.split(',') as List).get(new Random().nextInt(certification.subTypes.split(',').size()))
    }
    if (certification.providerRequired) {
        workerCertification.provider = (certification.providers.split(',') as List).get(new Random().nextInt(certification.providers.split(',').size()))
    }

    workerCertification.worker = worker

    return workerCertification
}

Set<CentralDataFile> populateAffidavits(Integer i) {
    Set<CentralDataFile> affidavits = []
    (1..i).each {index ->
        CentralDataFile dataFile = new CentralDataFile()
        dataFile.fileName = "affidavit-${index}.jpg"
        dataFile.bytes = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/affidavit-" + index + ".jpg")).readBytes()
        affidavits.add(dataFile)
    }
    return affidavits.unique {it.fileName}
}

String createEmailId(String name) {
    return 'care.force5+' + name.replaceAll(' ', '') + '@gmail.com'
}

CentralDataFile populateContractorImage(Integer i) {
    CentralDataFile workerImage = new CentralDataFile()
    workerImage.bytes = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/contractor-" + (i % 16).toInteger() + ".jpg")).readBytes()
    workerImage.fileName = "contractor-" + (i % 16).toInteger() + ".jpg"
    workerImage.s()
    return workerImage
}