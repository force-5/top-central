package com.force5solutions.care.cc

import com.force5solutions.care.common.Secured
import com.force5solutions.care.ldap.Permission

class CertificationController {

    def certificationService
    def centralUtilService

    def index = { redirect(action: list, params: params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

    @Secured(value = Permission.READ_CERTIFICATION)
    def list = {
        List<Certification> certificationList = []
        Integer certificationTotal = null
        params.order = params.order ?: 'asc'
        params.sort = params.sort ?: 'name'
        Integer offset = params.offset ? params.offset.toInteger() : 0
        params.max = centralUtilService.updateAndGetDefaultSizeOfListViewInConfig(params)
        if (!params?.max?.toString()?.equalsIgnoreCase('Unlimited')) {
            certificationList = Certification.list(params)
            certificationTotal = Certification.count()
        } else {
            certificationList = Certification.list()
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render template: 'certificationsTable', model: [certificationInstanceList: certificationList, certificationInstanceTotal: certificationTotal, offset: offset, max: params.max, order: params.order, sort: params.sort]
        } else {
            [certificationInstanceList: certificationList, certificationInstanceTotal: certificationTotal, offset: offset, max: params.max, order: params.order, sort: params.sort]
        }
    }

    @Secured(value = Permission.READ_CERTIFICATION)
    def show = {
        Certification certificationInstance = Certification.get(params.id)

        if (!certificationInstance) {
            flash.message = "Certification not found with id ${params.id}"
            redirect(action: list)
        }
        else { return [certificationInstance: certificationInstance] }
    }

    @Secured(value = Permission.DELETE_CERTIFICATION)
    def delete = {
        Certification certificationInstance = Certification.get(params.id)
        if (certificationInstance) {
            try {
                certificationInstance.delete(flush: true)
                flash.message = "Certification ${certificationInstance} deleted"
                redirect(action: list)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Certification ${certificationInstance} could not be deleted"
                redirect(action: show, id: params.id)
            }
        }
        else {
            flash.message = "Certification not found with id ${params.id}"
            redirect(action: list)
        }
    }

    @Secured(value = Permission.UPDATE_CERTIFICATION)
    def edit = {
        Certification certificationInstance = Certification.get(params.id)
        CertificationCommand certificationCommand = createCertificationCommand(certificationInstance)
        if (!certificationInstance) {
            flash.message = "Certification not found with id ${params.id}"
            redirect(action: list)
            return
        }
        return ['certificationInstance': certificationCommand]
    }

    @Secured(value = Permission.UPDATE_CERTIFICATION)
    def update = {CertificationCommand certificationCommand ->
        if (params.courses) {
            certificationCommand.courses = Course.getAll(params.list('courses')*.toLong())*.id
        }
//        if (params.notificationPeriods) {
//            certificationCommand.notificationPeriods = NotificationPeriod.getAll(params.list('notificationPeriods')*.toLong())*.id
//        }
        if (!certificationCommand.hasErrors()) {
            Certification certificationInstance = createCertificationObject(certificationCommand)
            if (!certificationInstance?.id) {
                flash.message = "Certification not found with id ${params.id}"
            } else if (certificationService.save(certificationInstance)) {
                flash.message = "Certification ${certificationInstance} updated"
                redirect(action: show, id: certificationInstance.id)
            }
        }
        render(view: 'edit', model: ['certificationInstance': certificationCommand])
    }

    @Secured(value = Permission.CREATE_CERTIFICATION)
    def create = {
        CertificationCommand certificationCommand = new CertificationCommand()
        render(view: 'create', model: ['certificationInstance': certificationCommand])
    }

    def save = {CertificationCommand certificationCommand ->
        Certification certificationInstance = createCertificationObject(certificationCommand)
        if (certificationService.save(certificationInstance)) {
            flash.message = "Certification ${certificationInstance} created"
            redirect(action: show, id: certificationInstance.id)
            return
        }
        render(view: 'create', model: ['certificationInstance': certificationCommand])
    }

//    def addNotificationPeriod = {
//        Certification certification = Certification.get(params['certificationId'].toLong())
//        NotificationPeriod notificationPeriod = new NotificationPeriod()
//        notificationPeriod.days = params.days.toInteger()
//        notificationPeriod.certification = certification
//        notificationPeriod.s()
//        certificationService.save(certification)
//        redirect(action: 'edit', params: [id: certification.id])
//    }

//    def removeNotificationPeriod = {
//        Certification certification = Certification.get(params['certificationId'].toLong())
//        NotificationPeriod notificationPeriod = NotificationPeriod.get(params['notificationPeriodId'].toLong())
//        notificationPeriod.delete(flush: true)
//        if (!NotificationPeriod.exists(notificationPeriod.id)) {
//            render "Success"
//        } else {
//            render "Fail"
//        }
//    }

    def createCertificationCommand(Certification certification) {
        CertificationCommand certificationCommand = new CertificationCommand()
        certificationCommand.with {
            id = certification?.id
            name = certification?.name
            courses = certification?.courses
//            notificationPeriods = certification?.notificationPeriods
            description = certification?.description
            quarterly = certification?.quarterly
            period = certification?.period
            periodUnit = certification?.periodUnit?.name
            expirationOffset = certification?.expirationOffset
            trainingRequired = certification?.trainingRequired
            testRequired = certification?.testRequired
            affidavitRequired = certification?.affidavitRequired
            subTypeRequired = certification?.subTypeRequired
            providerRequired = certification?.providerRequired
            subTypes = certification?.subTypes
            providers = certification?.providers
            testProviders = certification?.testProviders
            trainingProviders = certification?.trainingProviders
        }
        return certificationCommand
    }

    def createCertificationObject(CertificationCommand certificationCommand) {
        if (certificationCommand.courses) {
            certificationCommand.courses = Course.getAll(certificationCommand.courses*.toLong()) as Set
        }
//        if (certificationCommand.notificationPeriods) {
//            certificationCommand.notificationPeriods = NotificationPeriod.getAll(certificationCommand.notificationPeriods*.toLong()) as Set
//        }
        Certification certification = certificationCommand.id ? Certification.get(certificationCommand.id) : new Certification()
        certificationCommand.with {
            certification.name = name
            certification.description = description ? description : null
            certification.quarterly = quarterly
            certification.period = period
            certification.courses = courses
//            certification.notificationPeriods = notificationPeriods
            certification.periodUnit = periodUnit
            certification.expirationOffset = expirationOffset
            certification.trainingRequired = trainingRequired
            certification.testRequired = testRequired
            certification.affidavitRequired = affidavitRequired
            certification.subTypeRequired = subTypeRequired
            certification.providerRequired = providerRequired
            certification.subTypes = subTypes ? subTypes : null
            certification.providers = providers ? providers : null
            certification.testProviders = testProviders ? testProviders : null
            certification.trainingProviders = trainingProviders ? trainingProviders : null
        }
        return certification
    }
}
class CertificationCommand {
    Long id
    Set courses
//    Set notificationPeriods
    String name
    String description
    Boolean quarterly
    Integer period
    def periodUnit
    Integer expirationOffset
    boolean trainingRequired
    boolean testRequired
    boolean affidavitRequired
    boolean subTypeRequired
    boolean providerRequired
    String subTypes
    String providers
    String testProviders
    String trainingProviders

    void setPeriodUnit(def periodUnit) {
        this.periodUnit = PeriodUnit.getPeriodUnit(periodUnit)
    }

    static constraints = {
        name(blank: false, validator: {val, obj ->
            List<Certification> certifications = Certification.findAllByName(obj.name)
            if (obj.id) {
                certifications = certifications.findAll {it.id != obj.id}
            }
            if ((certifications?.size() > 0)) {
                return "default.not.unique.message"
            }
        })
        description(blank: true)
        quarterly(blank: true)
        period(min: 1, validator: {val, obj ->
            if (!val && !obj.quarterly) {
                return "CertificationCommand.period.blank.message"
            }
        })
        expirationOffset(nullable: false, min: 0)
        affidavitRequired(blank: true)

        providers(validator: {val, obj ->
            if (obj['providerRequired'] && !val) {
                return "CertificationCommand.providers.blank.message"
            }
        })
        subTypes(validator: {val, obj ->
            if (obj['subTypeRequired'] && !val) {
                return "CertificationCommand.subTypes.blank.message"
            }
        })
        testProviders(validator: {val, obj ->
            if (obj['testRequired'] && !val) {
                return "CertificationCommand.testProviders.blank.message"
            }
        })
        trainingProviders(validator: {val, obj ->
            if (obj['trainingRequired'] && !val) {
                return "CertificationCommand.trainingProviders.blank.message"
            }
        })
    }
}