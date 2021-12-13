package com.force5solutions.care.cc

public class EmpTagLib {

    static namespace = "emp"

    def employeeImage = {attrs ->
        if (attrs['id']) {
            out << "<img width='110' height='127' src=" + '"' + "${g.createLink(controller: 'careUtil', action: 'downloadFile', params: [className: 'com.force5solutions.care.cc.CentralDataFile', fieldName: 'bytes', id: attrs['id']])}" + '"' + "/>"
        }
        else {
            out << "<img width='110' height='127' src=" + '"' + "${g.createLinkTo(dir: 'data', file: 'noimguploaded.jpg')}" + '"' + "/>"

        }
    }

    def employeeLocationTree = {attrs ->
        Location location
        if (attrs['id']) {
            location = Location.get(attrs['id'])
        } else {
            LocationType pt = LocationType.findByLevel(1)
            location = Location.findByLocationType(pt)
        }
        Location tempLocation = location
        List breadcrumbs = []
        while (tempLocation) {
            breadcrumbs << tempLocation
            tempLocation = tempLocation.parent
        }
        out << g.render(template: '/employeeLocation/showTree',
                model: [employee: attrs['employee'], 'location': location, 'breadcrumbs': breadcrumbs.reverse()])
    }

    def certificationStatus = {attrs ->
        WorkerCertification cc = attrs['certification']
        out << cc.currentStatus
    }

    def missingCertifications = {attrs ->
        Employee employee = attrs['employee']
        List<Certification> missingCertifications = employee.missingCertifications.sort {it.name} //to maintain order
//        if (contractorInstance.terminateForCause) {
            out << g.render(template: "/employeeCertification/missingCertificationsDisabled",
                    model: ['employee': employee, missingCertifications: missingCertifications])
//        }
//        else {
//            out << g.render(template: "/employeeCertification/missingCertifications",
//                    model: ['employee': employee, missingCertifications: missingCertifications])
//        }
    }

}