package com.force5solutions.care.cc

class CertificationExpirationNotificationVO {
    int daysRemaining
    String taskTemplateName
    WorkerCertification workerCertification
    int notificationPeriod

    String toString() {
        return " Certification: ${workerCertification.certification} for Worker: ${workerCertification.worker} is getting expired in ${daysRemaining}"
    }
}
