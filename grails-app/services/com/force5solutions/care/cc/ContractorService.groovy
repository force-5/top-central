package com.force5solutions.care.cc

public class ContractorService {

    def auditService

    public Contractor saveNewContractor(Contractor contractor, Person person) {
        if (!person.s()) {
            throw new Exception("Exception while saving the person")
        }
        contractor.person = person
        if (!auditService.saveWorker(contractor)) {
            throw new Exception("Exception while saving the contractor")
        }
        return contractor
    }

}