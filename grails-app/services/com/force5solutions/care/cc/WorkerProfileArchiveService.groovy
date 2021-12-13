package com.force5solutions.care.cc

import com.force5solutions.care.feed.HrInfo
import com.force5solutions.care.feed.ContractorHrInfo

class WorkerProfileArchiveService {

    boolean transactional = true

    void matchOrCreateWorkerProfileEntry(Worker worker) {
        try {
            List<WorkerProfileArchive> workerProfileArchiveList = WorkerProfileArchive.findAllByWorkerId(worker.id)
            if (workerProfileArchiveList.empty) {
                createWorkerProfileArchiveEntry(worker)
            } else {
                matchWorkerProfileArchiveEntry(workerProfileArchiveList.sort {it.dateCreated}.last(), worker)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    public void createWorkerProfileArchiveEntry(Worker worker) {
        WorkerProfileArchive workerProfileArchive = new WorkerProfileArchive()
        workerProfileArchive.workerId = worker.id
        workerProfileArchive.workerImageId = worker?.workerImage?.id
        workerProfileArchive.badgeNumber = worker?.badgeNumber
        workerProfileArchive.workerNumber = worker?.workerNumber
        workerProfileArchive.firstName = worker?.person?.firstName
        workerProfileArchive.middleName = worker?.person?.middleName
        workerProfileArchive.lastName = worker?.person?.lastName
        workerProfileArchive.slid = worker.person.slid
        workerProfileArchive.email = worker?.person?.email
        workerProfileArchive.notes = worker?.person?.notes
        workerProfileArchive.phone = worker?.person?.phone
        if (worker.isEmployee()) {
            HrInfo hrInfo = HrInfo.findBySlid(worker.slid.toString())
            if (hrInfo) {
                hrInfo.with {
                    workerProfileArchive.pagerNum = PAGER_NUM
                    workerProfileArchive.pernr = pernr
                    workerProfileArchive.department = PERS_AREA_DESC
                    workerProfileArchive.persAreaNum = PERS_AREA_NUM
                    workerProfileArchive.persSubAreaNum = PERS_SUBAREA_NUM
                    workerProfileArchive.persSubAreaDesc = PERS_SUBAREA_DESC
                    workerProfileArchive.personStatus = PERSON_STATUS
                    workerProfileArchive.title = POSITION_TITLE
                    workerProfileArchive.orgUnitNum = ORGUNIT_NUM
                    workerProfileArchive.orgUnitDesc = ORGUNIT_DESC
                    workerProfileArchive.officePhoneNum = OFFICE_PHONE_NUM
                    workerProfileArchive.cellPhoneNum = CELL_PHONE_NUM
                    workerProfileArchive.supervisorSlid = supervisorSlid
                    workerProfileArchive.supvFullName = SUPV_FULL_NAME
                    workerProfileArchive.supvSupvFullName = SUPVSUPV_FULL_NAME
                    workerProfileArchive.supvSupvSlid = SUPVSUPV_SLID_ID
                    workerProfileArchive.fullName = FULL_NAME
                }
            }
        } else {
            workerProfileArchive.birthDay = worker?.birthDay
            workerProfileArchive.birthMonth = worker?.birthMonth
            workerProfileArchive.primeVendorId = worker?.primeVendor?.id
            workerProfileArchive.subVendorId = worker?.subVendor?.id
            workerProfileArchive.subSupervisorId = worker?.subSupervisor?.id
            workerProfileArchive.formOfId = worker.formOfId
            workerProfileArchive.businessUnitRequesterSlid = worker.businessUnitRequester?.person?.slid
            ContractorHrInfo contractorHrInfo = ContractorHrInfo.findByContractorSlid(worker.slid.toString())
            if (contractorHrInfo) {
                contractorHrInfo.with {
                    workerProfileArchive.supervisorSlid = supervisorSlid
                    workerProfileArchive.businessUnit = businessUnit
                    workerProfileArchive.vendorName = vendorName
                    workerProfileArchive.emergencyContact = emergencyContact
                    workerProfileArchive.personalEmail = personalEmail
                }
            }
        }
        workerProfileArchive.s()
    }

    void matchWorkerProfileArchiveEntry(WorkerProfileArchive workerProfileArchive, Worker worker) {
        Map workerProfileArchiveMap = createMapFromWorkerProfileArchive(workerProfileArchive, worker)
        Map workerPropertiesMap = createMapFromWorkerProperties(worker)
        if (!workerProfileArchiveMap.equals(workerPropertiesMap)) {
            createWorkerProfileArchiveEntry(worker)
        }
    }

    Map createMapFromWorkerProfileArchive(WorkerProfileArchive workerProfileArchive, Worker worker) {
        Map workerProfileArchiveMap = new HashMap()
        workerProfileArchiveMap.put("firstName", workerProfileArchive?.firstName)
        workerProfileArchiveMap.put("lastName", workerProfileArchive?.lastName)
        workerProfileArchiveMap.put("middleName", workerProfileArchive?.middleName)
        workerProfileArchiveMap.put("supervisorSlid", workerProfileArchive?.supervisorSlid)
        workerProfileArchiveMap.put("workerNumber", workerProfileArchive?.workerNumber)
        workerProfileArchiveMap.put("workerId", workerProfileArchive?.workerId)
        workerProfileArchiveMap.put("slid", workerProfileArchive?.slid)
        workerProfileArchiveMap.put("email", workerProfileArchive?.email)
        workerProfileArchiveMap.put("workerImageId", workerProfileArchive?.workerImageId)
        workerProfileArchiveMap.put("badgeNumber", workerProfileArchive?.badgeNumber)
        workerProfileArchiveMap.put("notes", workerProfileArchive?.notes)
        if (worker.isEmployee()) {
            workerProfileArchiveMap.put("pagerNum", workerProfileArchive.pagerNum)
            workerProfileArchiveMap.put("department", workerProfileArchive.department)
            workerProfileArchiveMap.put("pernr", workerProfileArchive.pernr)
            workerProfileArchiveMap.put("persAreaNum", workerProfileArchive.persAreaNum)
            workerProfileArchiveMap.put("persSubAreaDesc", workerProfileArchive.persSubAreaDesc)
            workerProfileArchiveMap.put("persSubAreaNum", workerProfileArchive.persSubAreaNum)
            workerProfileArchiveMap.put("personStatus", workerProfileArchive.personStatus)
            workerProfileArchiveMap.put("title", workerProfileArchive.title)
            workerProfileArchiveMap.put("orgUnitNum", workerProfileArchive.orgUnitNum)
            workerProfileArchiveMap.put("orgUnitDesc", workerProfileArchive.orgUnitDesc)
            workerProfileArchiveMap.put("officePhoneNum", workerProfileArchive.officePhoneNum)
            workerProfileArchiveMap.put("cellPhoneNum", workerProfileArchive.cellPhoneNum)
            workerProfileArchiveMap.put("supvFullName", workerProfileArchive.supvFullName)
            workerProfileArchiveMap.put("supvSupvFullName", workerProfileArchive.supvSupvFullName)
            workerProfileArchiveMap.put("supvSupvSlid", workerProfileArchive.supvSupvSlid)
            workerProfileArchiveMap.put("fullName", workerProfileArchive.fullName)
        } else {
            workerProfileArchiveMap.put("birthDay", workerProfileArchive.birthDay)
            workerProfileArchiveMap.put("birthMonth", workerProfileArchive.birthMonth)
            workerProfileArchiveMap.put("primeVendorId", workerProfileArchive.primeVendorId)
            workerProfileArchiveMap.put("subVendorId", workerProfileArchive.subVendorId)
            workerProfileArchiveMap.put("subSupervisorId", workerProfileArchive.subSupervisorId)
            workerProfileArchiveMap.put("formOfId", workerProfileArchive.formOfId)
            workerProfileArchiveMap.put("businessUnitRequesterSlid", workerProfileArchive.businessUnitRequesterSlid)
            workerProfileArchiveMap.put("businessUnit", workerProfileArchive.businessUnit)
            workerProfileArchiveMap.put("vendorName", workerProfileArchive.vendorName)
            workerProfileArchiveMap.put("emergencyContact", workerProfileArchive.emergencyContact)
            workerProfileArchiveMap.put("personalEmail", workerProfileArchive.personalEmail)
        }
        return workerProfileArchiveMap
    }

    Map createMapFromWorkerProperties(Worker worker) {
        Person person = worker.person
        Map workerPropertiesMap = new HashMap()
        workerPropertiesMap.put("firstName", person?.firstName)
        workerPropertiesMap.put("lastName", person?.lastName)
        workerPropertiesMap.put("middleName", person?.middleName)
        workerPropertiesMap.put("workerNumber", worker?.workerNumber)
        workerPropertiesMap.put("workerId", worker?.id)
        workerPropertiesMap.put("slid", person?.slid)
        workerPropertiesMap.put("email", person?.email)
        workerPropertiesMap.put("workerImageId", worker?.workerImage?.id)
        workerPropertiesMap.put("badgeNumber", worker?.badgeNumber)
        workerPropertiesMap.put("notes", worker?.notes)
        if (worker.isEmployee()) {
            HrInfo hrInfo = HrInfo.findBySlid(worker.slid.toString())
            workerPropertiesMap.put("pagerNum", hrInfo?.PAGER_NUM)
            workerPropertiesMap.put("department", worker?.department)
            workerPropertiesMap.put("pernr", hrInfo?.pernr)
            workerPropertiesMap.put("persAreaNum", hrInfo?.PERS_AREA_NUM)
            workerPropertiesMap.put("persSubAreaDesc", hrInfo?.PERS_SUBAREA_DESC)
            workerPropertiesMap.put("persSubAreaNum", hrInfo?.PERS_SUBAREA_NUM)
            workerPropertiesMap.put("personStatus", hrInfo?.PERSON_STATUS)
            workerPropertiesMap.put("title", worker?.title)
            workerPropertiesMap.put("orgUnitNum", hrInfo?.ORGUNIT_NUM)
            workerPropertiesMap.put("orgUnitDesc", hrInfo?.ORGUNIT_DESC)
            workerPropertiesMap.put("officePhoneNum", hrInfo?.OFFICE_PHONE_NUM)
            workerPropertiesMap.put("cellPhoneNum", hrInfo?.CELL_PHONE_NUM)
            workerPropertiesMap.put("supervisorSlid", hrInfo?.supervisorSlid)
            workerPropertiesMap.put("supvFullName", hrInfo?.SUPV_FULL_NAME)
            workerPropertiesMap.put("supvSupvFullName", hrInfo?.SUPVSUPV_FULL_NAME)
            workerPropertiesMap.put("supvSupvSlid", hrInfo?.SUPVSUPV_SLID_ID)
            workerPropertiesMap.put("fullName", hrInfo?.FULL_NAME)
        } else {
            ContractorHrInfo contractorHrInfo = ContractorHrInfo.findByContractorSlid(worker.slid.toString())
            workerPropertiesMap.put("birthDay", worker?.birthDay)
            workerPropertiesMap.put("birthMonth", worker?.birthMonth)
            workerPropertiesMap.put("primeVendorId", worker?.primeVendor?.id)
            workerPropertiesMap.put("subVendorId", worker?.subVendor?.id)
            workerPropertiesMap.put("subSupervisorId", worker?.subSupervisor?.id)
            workerPropertiesMap.put("formOfId", worker?.formOfId)
            workerPropertiesMap.put("businessUnitRequesterSlid", worker.businessUnitRequester?.person?.slid)
            workerPropertiesMap.put("supervisorSlid", contractorHrInfo?.supervisorSlid)
            workerPropertiesMap.put("businessUnit", contractorHrInfo?.businessUnit)
            workerPropertiesMap.put("vendorName", contractorHrInfo?.vendorName)
            workerPropertiesMap.put("emergencyContact", contractorHrInfo?.emergencyContact)
            workerPropertiesMap.put("personalEmail", contractorHrInfo?.personalEmail)
        }
        return workerPropertiesMap
    }
}
