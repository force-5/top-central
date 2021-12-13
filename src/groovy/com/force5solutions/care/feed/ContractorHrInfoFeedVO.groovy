package com.force5solutions.care.feed

import groovy.sql.GroovyRowResult

class ContractorHrInfoFeedVO {

    String contractorNumber
    String contractorSlid
    String firstName
    String lastName
    String email
    String businessUnit
    String supervisorSlid
    String vendorName
    String emergencyContact
    String personalEmail


    ContractorHrInfoFeedVO() {}

    ContractorHrInfo toContractorHrInfo() {
        ContractorHrInfo contractorHrInfo = new ContractorHrInfo()
        contractorHrInfo.contractorNumber = contractorNumber
        contractorHrInfo.contractorSlid = contractorSlid?.toUpperCase()
        contractorHrInfo.firstName = firstName
        contractorHrInfo.lastName = lastName
        contractorHrInfo.email = email
        contractorHrInfo.businessUnit = businessUnit
        contractorHrInfo.supervisorSlid = supervisorSlid?.toUpperCase()
        contractorHrInfo.vendorName = vendorName
        contractorHrInfo.emergencyContact = emergencyContact
        contractorHrInfo.personalEmail = personalEmail
        return contractorHrInfo
    }

    ContractorHrInfoFeedVO(GroovyRowResult rowResult) {
        firstName = rowResult.containsKey('first_name') ? rowResult.getProperty('first_name') as String : null
        lastName = rowResult.containsKey('last_name') ? rowResult.getProperty('last_name') as String : null
        contractorNumber = rowResult.containsKey('contractor_number') ? rowResult.getProperty('contractor_number') as String : null
        contractorSlid = rowResult.containsKey('contractor_slid') ? rowResult.getProperty('contractor_slid') as String : null
        email = rowResult.containsKey('email') ? rowResult.getProperty('email') as String : null
        businessUnit = rowResult.containsKey('business_unit') ? rowResult.getProperty('business_unit') as String : null
        vendorName = rowResult.containsKey('vendor_name') ? rowResult.getProperty('vendor_name') as String : null
        emergencyContact = rowResult.containsKey('emergency_contact') ? rowResult.getProperty('emergency_contact') as String : null
        supervisorSlid = rowResult.containsKey('supervisor_slid') ? rowResult.getProperty('supervisor_slid') as String : null
        personalEmail = rowResult.containsKey('personal_email') ? rowResult.getProperty('personal_email') as String : null
    }

    ContractorHrInfoFeedVO(String rowResult) {
        List<String> tokenizedString = rowResult.split('\\|').toList()*.trim()
        contractorSlid = (!tokenizedString[0]?.toString()?.equalsIgnoreCase('null')) ? tokenizedString[0].toString().trim() : null
        firstName = tokenizedString[1] ? tokenizedString[1].toString().trim() : null
        lastName = tokenizedString[2] ? tokenizedString[2].toString().trim() : null
        email = (!tokenizedString[3]?.toString()?.equalsIgnoreCase('null')) ? tokenizedString[3].toString().trim() : null
        emergencyContact = tokenizedString[4] ? tokenizedString[4].toString().trim() : null
        businessUnit = tokenizedString[5] ? tokenizedString[5].toString().trim() : null
        supervisorSlid = tokenizedString[6] ? tokenizedString[6].toString().trim() : null
        vendorName = tokenizedString[7] ? tokenizedString[7].toString().trim() : null
    }

    String toString() {
        return "${contractorSlid} : ${firstName} : ${lastName}"
    }
}
