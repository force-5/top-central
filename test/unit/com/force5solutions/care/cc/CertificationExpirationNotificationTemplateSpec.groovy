package com.force5solutions.care.cc

import grails.plugin.spock.UnitSpec
import static com.force5solutions.care.common.CareConstants.CERTIFICATION_EXPIRATION_NOTIFICATION_TEMPLATE_WILDCARD
import com.force5solutions.care.common.CareConstants

class CertificationExpirationNotificationTemplateSpec extends UnitSpec {


    def setupSpec() {

    }

    def setup() {
        mockDomain(CertificationExpirationNotificationTemplate)
        CertificationExpirationNotificationTemplate.metaClass.s = {-> delegate.save(flush: true)}
        populateTestCENTRules()
    }

    def "Test CENT Template Retrieval"() {

        when:
        String template = CertificationExpirationNotificationTemplate.findCertificationExpirationNotificationTemplate(workerCertificationProvider(workerCertification), notificationPeriod as Integer)

        then:
        template == templateName

        where:
        workerCertification | notificationPeriod | templateName
        1 | '40' | CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL
        1 | '30' | 'E'
        1 | '10' | 'F'
        6 | '90' | 'C'
        7 | '60' | 'C'
        8 | '90' | 'C'
        9 | '30' | 'C'
        9 | '7' | 'E'
        10 | '30' | 'H'
        13 | '60' | 'E'
        5 | '30' | 'I'
        15 | '90' | 'K'
        15 | '7' | 'L'
    }

    def "Test findNotificationPeriodsForCertification"() {

        when:
        List<Integer> notificationPeriods = CertificationExpirationNotificationTemplate.findNotificationPeriodsForCertification(certificationProvider(certification))

        then:
        isListContentsIdentical(notificationPeriods,notificationPeriodsList)

        where:
        certification | notificationPeriodsList
        1 | [7,60,30,90,10]
        2 | [7,60,30,90,10]
        3 | [7,60,30,90,10]
        4 | [7,60,30,90,10]
        5 | [7,60,30,90,10,100]
        6 | [7,60,30,90,10,20]
        7 | [7,60,30,90,10,20]
        8 | [7,60,30,90,10,20]
        9 | [7,60,30,90,10,20]
        10 | [7,60,30,90,10,50]
        11 | [7,60,30,90,10]
        12 | [7,60,30,90,10]
        13 | [7,60,30,90,10,50]
        14 | [7,60,30,90,10]
        15 | [7,60,30,90,10]

    }

    void populateTestCENTRules() {
        new CertificationExpirationNotificationTemplate(CERTIFICATION_EXPIRATION_NOTIFICATION_TEMPLATE_WILDCARD, '7,60,30,90', 'E').s()
        new CertificationExpirationNotificationTemplate(CERTIFICATION_EXPIRATION_NOTIFICATION_TEMPLATE_WILDCARD, '10', 'F').s()
        new CertificationExpirationNotificationTemplate('6,7,8,9', '30,60,90,20', 'C').s()
        new CertificationExpirationNotificationTemplate('10,13', '30,50', 'H').s()
        new CertificationExpirationNotificationTemplate('5', '30,60,90,100', 'I').s()
        new CertificationExpirationNotificationTemplate('15', '30,60', 'J').s()
        new CertificationExpirationNotificationTemplate('15', '90', 'K').s()
        new CertificationExpirationNotificationTemplate('15', '7', 'L').s()
    }

    WorkerCertification workerCertificationProvider(Integer id) {
        WorkerCertification workerCertification = new WorkerCertification()
        workerCertification.setCertification(new Certification(id: id))
        return workerCertification
    }

    Certification certificationProvider(Long id) {
        return new Certification(id: id)
    }

    Boolean isListContentsIdentical(List<Integer> list1, List<Integer> list2) {
        Boolean isIdentical = true
        list1.each {
            if (!(it in list2)) {
                isIdentical = false
            }
        }
        if (list1.size() != list2.size()) {
            isIdentical = false
        }
        return isIdentical
    }
}
