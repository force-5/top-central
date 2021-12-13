package com.force5solutions.care.workflow

import com.force5solutions.care.cc.Worker
import com.force5solutions.care.cc.Employee
import com.force5solutions.care.cc.Contractor
import com.force5solutions.care.ldap.Permission
import com.force5solutions.care.common.Secured

@Secured(value = Permission.READ_MANAGE_WORKFLOW)
class WorkflowController {

    def index = {
        redirect(action: 'manageWorkflowTasks')
    }

    def manageWorkflowTasks = {
        List filteredList = session?.filteredTaskList?.flatten()
        def isFiltered = params['isFiltered'] ?: ""

        if (filteredList || isFiltered) {
            render(view: 'manageWorkflow', model: [taskList: filteredList, isFiltered: isFiltered])
        } else {
            def tasks = CentralWorkflowTask.createCriteria().list {
                'in'('status', [WorkflowTaskStatus.NEW, WorkflowTaskStatus.PENDING])
                projections {
                    min('id')
                    groupProperty('workflowGuid')
                }
            }
            List<CentralWorkflowTask> workflowTasks = []
            if (tasks) {
                workflowTasks = CentralWorkflowTask.getAll(tasks*.first())
            }
            render(view: 'manageWorkflow', model: [taskList: workflowTasks])
        }
    }

    def filterWorkflowTasks = {
        String name = params['name']?.trim()
        String workflowTypeString = params['workflowType']
        CentralWorkflowType workflowType = workflowTypeString ? CentralWorkflowType."${workflowTypeString}" : null
        String slid = params['slid']?.trim()
        String taskNodeName = params['taskNodeName']
        List<CentralWorkflowTask> taskList = []
        List<Worker> workerList = []
        List taskIds = []

        taskIds = CentralWorkflowTask.createCriteria().list {
            and {
                if (workflowType) eq('workflowType', workflowType)
                if (taskNodeName) eq('nodeName', taskNodeName)
                'in'('status', [WorkflowTaskStatus.NEW, WorkflowTaskStatus.PENDING])
            }
            projections {
                min('id')
                groupProperty('workflowGuid')
            }
        }

        if (taskIds) {
            taskList = CentralWorkflowTask.getAll(taskIds*.first())
        }

        if (!name?.length() == 0 || !slid?.length() == 0) {
            def employeeList = Employee.createCriteria().list {
                and {
                    person {
                        or {
                            ilike('firstName', '%' + name + '%')
                            ilike('middleName', '%' + name + '%')
                            ilike('lastName', '%' + name + '%')
                        }
                        if (slid) ilike('slid', slid)
                    }
                }
            }
            def contractorList = Contractor.createCriteria().list {
                and {
                    person {
                        or {
                            ilike('firstName', '%' + name + '%')
                            ilike('middleName', '%' + name + '%')
                            ilike('lastName', '%' + name + '%')
                        }
                        if (slid) ilike('slid', slid)
                    }
                }
            }
            workerList = (employeeList + contractorList).flatten()
            taskList = taskList.findAll { CentralWorkflowTask task ->
                task.worker in workerList && (task.status == WorkflowTaskStatus.NEW || task.status == WorkflowTaskStatus.PENDING)
            }
        }

        session.filteredTaskList = taskList
        redirect(action: 'manageWorkflowTasks', params: [isFiltered: true])
    }

    def filterWorkflowDialog = {
        // TODO : ideally, I see the following method as a method call on the WorkflowType Enum
        List<CentralWorkflowType> workflowTypes =
        [CentralWorkflowType.EMPLOYEE_PUBLIC_ACCESS_REQUEST,
                CentralWorkflowType.EMPLOYEE_ACCESS_REQUEST,
                CentralWorkflowType.CONTRACTOR_ACCESS_REQUEST,
                CentralWorkflowType.EMPLOYEE_ACCESS_REVOKE,
                CentralWorkflowType.CONTRACTOR_ACCESS_REVOKE,
                CentralWorkflowType.CONTRACTOR_TERMINATION,
                CentralWorkflowType.EMPLOYEE_TERMINATION,
                CentralWorkflowType.CERTIFICATION_EXPIRATION_NOTIFICATION,
                CentralWorkflowType.ADD_ENTITLEMENT_POLICY,
                CentralWorkflowType.UPDATE_ENTITLEMENT_POLICY,
                CentralWorkflowType.EMPLOYEE_CANCEL_ACCESS_APPROVAL,
                CentralWorkflowType.CONTRACTOR_CANCEL_ACCESS_APPROVAL,
                CentralWorkflowType.EMPLOYEE_CANCEL_ACCESS_REVOCATION,
                CentralWorkflowType.EMPLOYEE_CANCEL_ACCESS_REVOCATION]
        List<String> nodeNames = CentralWorkflowTask.createCriteria().list {
            projections {
                distinct "nodeName"
            }
        }
        nodeNames.remove('Initial Task')
        render(template: 'workflowFilter', model: [workflowTypes: workflowTypes, nodeNames: nodeNames])
    }

    def showAllWorkflowTasks = {
        session.filteredTaskList = null
        redirect(action: 'manageWorkflowTasks')
    }

    def terminateWorkflowTasks = {
        List<String> selectedWorkflowGuids = params.list('selectedWorkflowGuids')
        selectedWorkflowGuids.unique()
        selectedWorkflowGuids.each{
            CentralWorkflowUtilService.abortWorkflow(it)
            CentralWorkflowUtilService.startCancelWorkflow(it)
        }
        redirect(action: 'manageWorkflowTasks')
    }
}
