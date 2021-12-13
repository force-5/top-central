import com.force5solutions.care.cc.Certification
import com.force5solutions.care.cc.Course
import com.force5solutions.care.cc.PeriodUnit

pre {
    if (!Certification.count()) {
        println "Populating Sample Certifications"
        populateSampleCertifications();
    }
}

fixture {}

void populateSampleCertifications() {
    (1..5).each {
        Certification certification = new Certification();
        certification.name = "Certification-${it}"
        certification.description = "Certification-${it} description"
        Boolean isQuarterly = new Random().nextBoolean()
        if (isQuarterly) {
            certification.quarterly = true
        } else {
            certification.quarterly = false
            certification.period = 5
            certification.periodUnit = PeriodUnit.MONTHS
        }
        certification.expirationOffset = new Random().nextInt(5)
        certification.providerRequired = new Random().nextBoolean()
        certification.subTypeRequired = new Random().nextBoolean()
        certification.trainingRequired = new Random().nextBoolean()
        certification.testRequired = new Random().nextBoolean()
        certification.affidavitRequired = new Random().nextBoolean()
        if (certification.trainingRequired) {
            List trainingProviders = []
            (1..(new Random().nextInt(5) + 2)).each {trainingProviders.add("TrainingProvider${it}")}
            certification.trainingProviders = trainingProviders.join(",")
        }
        if (certification.testRequired) {
            List testProviders = []
            (1..(new Random().nextInt(5) + 2)).each {testProviders.add("TestProvider${it}")}
            certification.testProviders = testProviders.join(",")
        }
        if (certification.subTypeRequired) {
            List subTypes = []
            (1..(new Random().nextInt(5) + 2)).each {subTypes.add("SubType${it}")}
            certification.subTypes = subTypes.join(",")
        }
        if (certification.providerRequired) {
            List providers = []
            (1..(new Random().nextInt(5) + 2)).each {providers.add("Provider${it}")}
            certification.providers = providers.join(",")
        }
        certification.addToCourses(Course.get(it));
        certification.s()
    }
}


