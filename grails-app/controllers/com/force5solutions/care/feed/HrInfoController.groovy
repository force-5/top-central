

package com.force5solutions.care.feed

class HrInfoController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ hrInfoInstanceList: HrInfo.list( params ), hrInfoInstanceTotal: HrInfo.count() ]
    }

    def show = {
        def hrInfoInstance = HrInfo.get( params.id )

        if(!hrInfoInstance) {
            flash.message = "HrInfo not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ hrInfoInstance : hrInfoInstance ] }
    }

    def delete = {
        def hrInfoInstance = HrInfo.get( params.id )
        if(hrInfoInstance) {
            try {
                hrInfoInstance.delete(flush:true)
                flash.message = "HrInfo ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "HrInfo ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "HrInfo not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def hrInfoInstance = HrInfo.get( params.id )

        if(!hrInfoInstance) {
            flash.message = "HrInfo not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ hrInfoInstance : hrInfoInstance ]
        }
    }

    def update = {
        def hrInfoInstance = HrInfo.get( params.id )
        if(hrInfoInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(hrInfoInstance.version > version) {
                    
                    hrInfoInstance.errors.rejectValue("version", "hrInfo.optimistic.locking.failure", "Another user has updated this HrInfo while you were editing.")
                    render(view:'edit',model:[hrInfoInstance:hrInfoInstance])
                    return
                }
            }
            hrInfoInstance.properties = params
            if(!hrInfoInstance.hasErrors() && hrInfoInstance.save()) {
                flash.message = "HrInfo ${params.id} updated"
                redirect(action:show,id:hrInfoInstance.id)
            }
            else {
                render(view:'edit',model:[hrInfoInstance:hrInfoInstance])
            }
        }
        else {
            flash.message = "HrInfo not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def hrInfoInstance = new HrInfo()
        hrInfoInstance.properties = params
        return ['hrInfoInstance':hrInfoInstance]
    }

    def save = {
        def hrInfoInstance = new HrInfo(params)
        if(!hrInfoInstance.hasErrors() && hrInfoInstance.save()) {
            flash.message = "HrInfo ${hrInfoInstance.id} created"
            redirect(action:show,id:hrInfoInstance.id)
        }
        else {
            render(view:'create',model:[hrInfoInstance:hrInfoInstance])
        }
    }
}
