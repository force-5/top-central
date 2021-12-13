import com.force5solutions.care.ldap.Permission
import com.force5solutions.care.cc.*
import com.force5solutions.care.common.Secured
import com.force5solutions.care.common.SecuredBasedOnWorkerTypeAndSendWorkerToPermission
import com.force5solutions.care.common.SecuredAndSendWorkerToPermission

class SecurityFilters {

    def permissionService
    def grailsApplication
    def filters = {

        validatePermission(controller: '*', action: '*') {
            before = {
                if (session.loggedUser && controllerName) {
                    def controllerClass = grailsApplication.controllerClasses.find {it.logicalPropertyName == controllerName}
                    def annotation = controllerClass.clazz.getAnnotation(Secured)
                    String currentAction = actionName ?: controllerClass.defaultActionName

                    if (!annotation || currentAction in annotation.exclude()) {
                        def action = applicationContext.getBean(controllerClass.fullName).class.declaredFields.find { field -> field.name == currentAction }
                        annotation = action ? action.getAnnotation(Secured) : null
                    }

                    boolean hasPermission = annotation ? permissionService.hasPermission(annotation.value()) : true

                    if (!hasPermission) {
                        render(view: "/permissionDenied")
                        return false;
                    }
                }
            }
        }

        validatePermissionBasedOnWorkerType(controller: '*', action: '*') {
            before = {
                Worker worker
                def permission
                if (session.loggedUser && controllerName) {
                    boolean hasPermission = true

                    def controllerClass = grailsApplication.controllerClasses.find {it.logicalPropertyName == controllerName}
                    def annotation = controllerClass.clazz.getAnnotation(SecuredBasedOnWorkerTypeAndSendWorkerToPermission)
                    String currentAction = actionName ?: controllerClass.defaultActionName

                    if (!annotation || currentAction in annotation.exclude()) {
                        def action = applicationContext.getBean(controllerClass.fullName).class.declaredFields.find { field -> field.name == currentAction }
                        annotation = action ? action.getAnnotation(SecuredBasedOnWorkerTypeAndSendWorkerToPermission) : null
                    }

                    if (params.workerCertificationId) {
                        WorkerCertification workerCertification = WorkerCertification.get(params.workerCertificationId)
                        worker = workerCertification.worker
                    } else {
                        worker = Worker.get(params.long('id'))
                    }

                    if (annotation) {
                        if (worker instanceof Contractor) {
                            permission = annotation.contractorPermission()
                        } else {
                            permission = annotation.employeePermission()
                        }
                        hasPermission = permissionService.hasPermission(permission, worker)
                    }

                    if (!hasPermission) {
                        render(view: "/permissionDenied")
                        return false;
                    }
                }
            }
        }

        validatePermissionSendWorkerToPermission(controller: '*', action: '*') {
            before = {
                if (session.loggedUser && controllerName) {

                    def controllerClass = grailsApplication.controllerClasses.find {it.logicalPropertyName == controllerName}
                    def annotation = controllerClass.clazz.getAnnotation(SecuredAndSendWorkerToPermission)
                    String currentAction = actionName ?: controllerClass.defaultActionName

                    if (!annotation || currentAction in annotation.exclude()) {
                        def action = applicationContext.getBean(controllerClass.fullName).class.declaredFields.find { field -> field.name == currentAction }
                        annotation = action ? action.getAnnotation(SecuredAndSendWorkerToPermission) : null
                    }

                    Worker worker = Worker.get(params.long('id'))
                    boolean hasPermission = annotation ? permissionService.hasPermission(annotation.value(), worker) : true

                    if (!hasPermission) {
                        render(view: "/permissionDenied")
                        return false;
                    }
                }
            }
        }

        readContractors(controller: 'contractor', action: 'list') {
            before = {
                if (session.loggedUser && !permissionService.hasPermission(Permission.READ_CONTRACTOR_PROFILE, null, null, true)) {
                    render(view: "/permissionDenied")
                    return false
                }
            }
        }

        readEmployees(controller: 'employee', action: 'list') {
            before = {
                if (session.loggedUser && !permissionService.hasPermission(Permission.READ_EMPLOYEE_PROFILE, null, null, true)) {
                    render(view: "/permissionDenied")
                    return false
                }
            }
        }

        readWorkerAccess(controller: 'workerEntitlementRole', action: 'access') {
            before = {
                Worker worker = Worker.get(params.long('id'))
                def permission
                if (worker instanceof Contractor) {
                    permission = Permission.READ_CONTRACTOR_ACCESS
                } else {
                    permission = Permission.READ_EMPLOYEE_ACCESS
                }
                if (session.loggedUser && !permissionService.hasPermission(permission, worker)) {
                    render(view: "/permissionDenied")
                    return false
                }
            }
        }

        deleteWorkerAccess(controller: 'workerEntitlementRole', action: 'removeEntitlementRoleAccess') {
            before = {
                WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(params.changeWorkerEntitlementRoleId?.toLong())
                def permission
                def worker = workerEntitlementRole.worker
                if (worker instanceof Contractor) {
                    permission = Permission.DELETE_CONTRACTOR_ACCESS
                } else {
                    permission = Permission.DELETE_EMPLOYEE_ACCESS
                }
                if (session.loggedUser && !permissionService.hasPermission(permission, worker, workerEntitlementRole.entitlementRole.location)) {
                    render(view: "/permissionDenied")
                    return false
                }
            }
        }

        terminateWorker(controller: 'workerEntitlementRole', action: 'terminateForCause') {
            before = {
//                Worker worker = Worker.get(params.long('id'))
                //                def permission
                //                if (worker instanceof Contractor) {
                //                    permission = Permission.REQUEST_CONTRACTOR_TERMINATE
                //                } else {
                //                    permission = Permission.REQUEST_EMPLOYEE_TERMINATE
                //                }
                //                if (session.loggedUser && !permissionService.hasPermission(permission, worker)) {
                render(view: "/permissionDenied")
                return false
//                }
            }
        }


        createCourse(controller: 'course', action: 'create') {
            before = {
                if (session.loggedUser && !permissionService.hasPermission(Permission.CREATE_COURSE)) {
                    render(view: "/permissionDenied")
                    return false
                }
            }
        }

        readCourse(controller: 'course', action: 'show|list') {
            before = {
                if (session.loggedUser && !permissionService.hasPermission(Permission.READ_COURSE)) {
                    render(view: "/permissionDenied")
                    return false
                }
            }
        }

        updateCourse(controller: 'course', action: 'edit|update') {
            before = {
                if (session.loggedUser && !permissionService.hasPermission(Permission.UPDATE_COURSE)) {
                    render(view: "/permissionDenied")
                    return false
                }
            }
        }

        deleteCourse(controller: 'course', action: 'delete') {
            before = {
                if (session.loggedUser && !permissionService.hasPermission(Permission.DELETE_COURSE)) {
                    render(view: "/permissionDenied")
                    return false
                }
            }
        }
    }
}