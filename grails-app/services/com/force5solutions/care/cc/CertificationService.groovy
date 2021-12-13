package com.force5solutions.care.cc

import com.force5solutions.care.cc.Certification

class CertificationService {

    boolean transactional = true
    def versioningService

    def save(Certification certification) {
        if (!certification.validate() && certification.hasErrors()) {
            certification?.errors?.allErrors?.each { log.error it }
            return false
        }
        versioningService.saveVersionableObject(certification)
        return true
    }
}
