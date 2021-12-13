package com.force5solutions.care.cc

import com.force5solutions.care.common.CareConstants
import org.grails.plugins.versionable.VersioningContext
import com.force5solutions.care.feed.ContractorHrInfo
import com.force5solutions.care.feed.HrInfo

class ContractorUtilService {

    boolean transactional = false
    def auditService
    def employeeUtilService

    public Contractor createOrUpdateContractor(ContractorHrInfo contractorHrInfo) {
        VersioningContext.set(UUID.randomUUID().toString())
        Person person = null
        if (contractorHrInfo?.contractorSlid?.length() || (contractorHrInfo?.firstName && contractorHrInfo?.lastName)) {
            person = Contractor?.findBySlid(contractorHrInfo?.contractorSlid?.toString())?.person ?: Contractor.findByFirstNameAndLastName(contractorHrInfo?.firstName?.trim(), contractorHrInfo?.lastName?.trim())?.person
        } else {
            person = Person.findByFirstNameAndLastName(contractorHrInfo.firstName, contractorHrInfo.lastName)
        }

        if (!person) {
            person = new Person()
        }
        person = populatePersonFromContractorHrInfo(contractorHrInfo, person)
        person.s()

        Contractor contractor = Contractor.findByPerson(person) ?: new Contractor()
        contractor.person = person
        contractor.workerNumber = contractorHrInfo?.contractorNumber ?: contractor?.workerNumber
        saveNewContractor(contractor)
        if (contractorHrInfo.supervisorSlid) {
            BusinessUnitRequester businessUnitRequester = findOrCreateBusinessUnitRequester(contractorHrInfo)
            if (businessUnitRequester) {
                contractor.businessUnitRequesters = [businessUnitRequester]
                saveNewContractor(contractor)
            }
        }
        return (contractor?.id ? contractor : null)
    }

    BusinessUnitRequester findOrCreateBusinessUnitRequester(ContractorHrInfo contractorHrInfo) {
        BusinessUnitRequester businessUnitRequester = BusinessUnitRequester?.findBySlid(contractorHrInfo.supervisorSlid) ?: null
        if (!businessUnitRequester) {
            Person person = Person.findBySlid(contractorHrInfo.supervisorSlid) ?: null
            if (!person) {
                HrInfo hrInfo = HrInfo.findBySlid(contractorHrInfo.supervisorSlid)
                person = hrInfo ? employeeUtilService.populatePersonFromHrInfo(hrInfo).s() : null
            }

            String unitName = contractorHrInfo.businessUnit
            BusinessUnit businessUnit = findOrCreateABusinessUnit(unitName)

            if (person && businessUnit) {
                businessUnitRequester = new BusinessUnitRequester()
                businessUnitRequester.person = person
                businessUnitRequester.unit = businessUnit
                businessUnitRequester.s()
            }
        }
        return businessUnitRequester
    }

    BusinessUnit findOrCreateABusinessUnit(String unitName) {
        return (unitName && !unitName?.isEmpty()) ? (BusinessUnit.findByName(unitName)) : findOrCreateDummyBusinessUnit()
    }

    BusinessUnit findOrCreateDummyBusinessUnit() {
        return BusinessUnit.findByName(CareConstants.DUMMY_BUSINESS_UNIT) ?: new BusinessUnit(name: CareConstants.DUMMY_BUSINESS_UNIT).s()
    }

    Person populatePersonFromContractorHrInfo(ContractorHrInfo contractorHrInfo, Person person = new Person()) {
        person.firstName = contractorHrInfo?.firstName
        person.lastName = contractorHrInfo?.lastName
        if (!person.phone) {
            person.phone = contractorHrInfo?.emergencyContact
        }
        person.slid = contractorHrInfo?.contractorSlid
        person.email = contractorHrInfo?.email ?: person.email
        return person
    }

    private boolean saveNewContractor(Contractor contractor) {
        try {
            if (!contractor.person.save()) {
                throw new Exception("Exception while saving the person")
            }
            if (!auditService.saveWorker(contractor)) {
                throw new Exception("Exception while saving the Contractor")
            }
        } catch (Exception e) {
            e.printStackTrace()
            return false
        }
        return true
    }

    public Contractor findOrCreateContractor(String slid = '') {
        Contractor contractor = Contractor.findBySlid(slid)
        if (!contractor) {
            ContractorHrInfo contractorHrInfo = ContractorHrInfo.findByContractorSlid(slid)
            if (contractorHrInfo) {
                contractor = createOrUpdateContractor(contractorHrInfo)
            }
        }
        return contractor
    }

    public Person findContractorDetails(String slid) {
        Person person = null
        Contractor contractor = Contractor.findBySlid(slid)
        if (contractor) {
            person = contractor.person
        } else {
            ContractorHrInfo contractorHrInfo = ContractorHrInfo.findByContractorSlid(slid)
            if (contractorHrInfo) {
                person = populatePersonFromContractorHrInfo(contractorHrInfo)
            }
        }
        return person
    }
}
