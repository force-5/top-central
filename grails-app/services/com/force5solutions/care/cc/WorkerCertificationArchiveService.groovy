package com.force5solutions.care.cc

class WorkerCertificationArchiveService {

    boolean transactional = true

    void matchOrCreateWorkerCertificationEntry(WorkerCertification workerCertification, def oldCertificationIds = null) {
        try {
            String activeCertificationNames = workerCertification.worker.activeCertifications.sort {it.certification.name}*.certification.name.join(',')
            List<WorkerCertificationArchive> workerCertificationArchiveList = WorkerCertificationArchive.findAllByWorkerIdAndCertificationNames(workerCertification.worker.id, activeCertificationNames)
            if (workerCertificationArchiveList.empty || oldCertificationIds) {
                createWorkerCertificationArchiveEntry(workerCertification)
            } else {
                matchWorkerCertificationArchiveEntry(workerCertificationArchiveList.sort {it.dateCreated}.last(), workerCertification)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    public void createWorkerCertificationArchiveEntry(WorkerCertification workerCertification) {
        WorkerCertificationArchive workerCertificationArchive = new WorkerCertificationArchive()
        workerCertificationArchive.certificationIds = workerCertification.worker.activeCertifications.sort {it.certification.name}*.certification.id.join(',')
        workerCertificationArchive.workerId = workerCertification.worker.id
        workerCertificationArchive.certificationNames = workerCertification.worker.activeCertifications.sort {it.certification.name}*.certification.name.join(',')
        workerCertificationArchive.workerFirstName = workerCertification.worker.firstName
        workerCertificationArchive.workerMiddleName = workerCertification.worker.middleName
        workerCertificationArchive.workerLastName = workerCertification.worker.lastName
        workerCertificationArchive.workerSlid = workerCertification.worker.slid
        workerCertificationArchive.s()
    }

    public matchWorkerCertificationArchiveEntry(WorkerCertificationArchive workerCertificationArchive, WorkerCertification workerCertification) {
        Map workerCertificationArchiveMap = createMapFromWorkerCertificationArchive(workerCertificationArchive)
        Map workerCertificationMap = createMapFromWorkerCertification(workerCertification)
        if (!workerCertificationArchiveMap.equals(workerCertificationMap)) {
            createWorkerCertificationArchiveEntry(workerCertification)
        }
    }

    Map createMapFromWorkerCertificationArchive(WorkerCertificationArchive workerCertificationArchive) {
        Map workerCertificationArchiveMap = new HashMap()
        workerCertificationArchiveMap.put("certificationIds", workerCertificationArchive.certificationIds)
        workerCertificationArchiveMap.put("workerId", workerCertificationArchive.workerId)
        workerCertificationArchiveMap.put("certificationNames", workerCertificationArchive.certificationNames)
        workerCertificationArchiveMap.put("workerFirstName", workerCertificationArchive.workerFirstName)
        workerCertificationArchiveMap.put("workerMiddleName", workerCertificationArchive.workerMiddleName)
        workerCertificationArchiveMap.put("workerLastName", workerCertificationArchive.workerLastName)
        workerCertificationArchiveMap.put("workerSlid", workerCertificationArchive.workerSlid)
        return workerCertificationArchiveMap
    }

    Map createMapFromWorkerCertification(WorkerCertification workerCertification) {
        Map workerCertificationMap = new HashMap()
        workerCertificationMap.put("certificationIds", workerCertification.worker.activeCertifications.sort {it.certification.name}*.certification.id.join(','))
        workerCertificationMap.put("workerId", workerCertification.worker.id)
        workerCertificationMap.put("certificationNames", workerCertification.worker.activeCertifications.sort {it.certification.name}*.certification.name.join(','))
        workerCertificationMap.put("workerFirstName", workerCertification.worker.firstName)
        workerCertificationMap.put("workerMiddleName", workerCertification.worker.middleName)
        workerCertificationMap.put("workerLastName", workerCertification.worker.lastName)
        workerCertificationMap.put("workerSlid", workerCertification.worker.slid)
        return workerCertificationMap
    }
}
