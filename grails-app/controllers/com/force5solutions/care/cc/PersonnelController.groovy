package com.force5solutions.care.cc

class PersonnelController {

    def index = { }

    def show = {
        Long id = params.long('id')
        if (Contractor.exists(id)) {
            redirect(controller: 'contractor', action: 'show', id: id)
        } else {
            redirect(controller: 'employee', action: 'show', id: id)
        }
    }
}
