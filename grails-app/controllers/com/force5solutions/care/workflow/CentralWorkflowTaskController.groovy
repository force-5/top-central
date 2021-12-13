package com.force5solutions.care.workflow

import com.force5solutions.care.cc.UploadedFile
import com.force5solutions.care.cc.AppUtil
import com.force5solutions.care.common.SessionUtils
import com.force5solutions.care.common.CareConstants
import com.force5solutions.care.cc.EntitlementPolicy
import com.force5solutions.care.cc.CentralDataFile
import com.force5solutions.care.ldap.SecurityRole
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class CentralWorkflowTaskController {

    def accessVerificationService
    def dashboardService

    def list = {
        List<CentralWorkflowTask> tasks = CentralWorkflowTask.getPermittedTasks()
        tasks = session.filterVO ? filterWorkflowTaskList(tasks, (session.filterVO as WorkflowTaskFilterVO)) : tasks
        [tasks: tasks]
    }

    public List<CentralWorkflowTask> filterWorkflowTaskList(List<CentralWorkflowTask> centralWorkflowTasks, WorkflowTaskFilterVO workflowTaskFilterVO) {
        if (workflowTaskFilterVO?.workflowType) {
            centralWorkflowTasks = centralWorkflowTasks.findAll { it?.workflowType == CentralWorkflowType.findKey(workflowTaskFilterVO?.workflowType) }
        }

        if (workflowTaskFilterVO?.workerId) {
            centralWorkflowTasks = centralWorkflowTasks.findAll { it?.worker?.id == workflowTaskFilterVO?.workerId }
        }

        if (workflowTaskFilterVO?.entitlementPolicyId) {
            centralWorkflowTasks = centralWorkflowTasks.findAll { it?.entitlementPolicy?.id == workflowTaskFilterVO?.entitlementPolicyId }
        }

        if (workflowTaskFilterVO?.securityRoleId) {
            centralWorkflowTasks = centralWorkflowTasks.findAll { it?.securityRoles?.contains(SecurityRole.get(workflowTaskFilterVO?.securityRoleId).name)}
        }
        if (workflowTaskFilterVO?.actorSlid) {
            centralWorkflowTasks = centralWorkflowTasks.findAll { it?.actorSlid == workflowTaskFilterVO?.actorSlid}
        }
        return centralWorkflowTasks
    }

    def listAll = {
        List<CentralWorkflowTask> tasks = CentralWorkflowTask.list()
        render(view: 'listAll', model: [tasks: tasks])
    }

    def getUserResponse = {
        CentralWorkflowTask task = CentralWorkflowTask.get(params.long('id'))
        if (task.status in [WorkflowTaskStatus.COMPLETE, WorkflowTaskStatus.CANCELLED]) {
            render(view: 'actionAlreadyTaken', model: [task: task])
        } else if (task.workflowType == CentralWorkflowType.ACCESS_VERIFICATION) {
            String slid = CentralWorkflowTaskPermittedSlid.findAllByTask(task, [max: 1, order: 'dateCreated'])?.first()?.slid
            Map rolesMap = accessVerificationService.accessVerificationReport(slid)
            String supervisorName = task.worker.firstName + ' ' + task.worker.lastName
            render(view: task.responseForm, model: [rolesMap: rolesMap, supervisorName: supervisorName, taskId: task.id])
        } else {
            //This code is written to populate the WorkflowVOs to be passed to the supervisor inbox to show the details of the tasks having the same workflow guid.
            List<WorkflowVO> workflowVOs = []
            if (task.workerEntitlementRoleId) {
                List<CentralWorkflowTask> centralTasks = CentralWorkflowTask.findAllByWorkerEntitlementRoleId(task.workerEntitlementRoleId)
                centralTasks.each { CentralWorkflowTask centralWorkflowTask ->
                    workflowVOs << new WorkflowVO(centralWorkflowTask, CareConstants.CENTRAL_SYSTEM_USER_ID)
                }
                workflowVOs = workflowVOs.sort {it.taskCreated}
            }
            render(view: task.responseForm, model: [task: task, workflowVOs: workflowVOs])
        }
    }

    def getGroupResponse = {
        List<Long> centralWorkflowTaskIds = params.list('taskIds')
        List<CentralWorkflowTask> centralWorkflowTasks = centralWorkflowTaskIds ? CentralWorkflowTask.getAll(centralWorkflowTaskIds) : []
        CentralWorkflowTask firstTask = centralWorkflowTasks.first()
        if (firstTask.workflowType != CentralWorkflowType.ACCESS_VERIFICATION) {
            render view: firstTask.responseForm, model: [tasks: centralWorkflowTasks, task: firstTask]
        } else {
            flash.message = 'Access Verification tasks can not be completed in groups'
            redirect(action: 'list')
        }
    }

    def sendGroupResponse = {
        List<CentralWorkflowTask> centralWorkflowTaskList = params.list('taskIds') ? CentralWorkflowTask.getAll(params.list('taskIds')*.toLong()) : []
        Map responseElements = params as HashMap
        responseElements.remove('action')
        responseElements.remove('controller')
        responseElements.remove('id')
        List<UploadedFile> uploadedFiles = AppUtil.populateAttachments(params)
        List<CentralDataFile> centralDataFiles = []
        responseElements = responseElements.findAll { String key, value ->
            (!key.startsWith("multiFile"))
        }
        uploadedFiles?.each {UploadedFile uploadedFile ->
            centralDataFiles.add(new CentralDataFile(uploadedFile))
        }
        centralWorkflowTaskList.each {CentralWorkflowTask centralWorkflowTask ->
            centralWorkflowTask.documents = centralDataFiles as Set
            if (centralWorkflowTask.status in [WorkflowTaskStatus.COMPLETE, WorkflowTaskStatus.CANCELLED]) {
                CentralWorkflowTaskPermittedSlid.markArchived(CentralWorkflowTask.read(centralWorkflowTask.id), SessionUtils.getSession()?.loggedUser);
            } else {
                CentralWorkflowUtilService.sendResponseElements(centralWorkflowTask, responseElements, uploadedFiles, SessionUtils.getSession()?.loggedUser, true)
            }
        }
        redirect(action: 'list')
    }

    def confirm = {
        CentralWorkflowTaskPermittedSlid taskPermittedSlid = CentralWorkflowTaskPermittedSlid.findByGuid(params.id)
        if (taskPermittedSlid) {
            if ((!taskPermittedSlid.isArchived && (taskPermittedSlid.task.status != WorkflowTaskStatus.COMPLETE))) {
                CentralWorkflowUtilService.sendResponseElements(taskPermittedSlid.task, ['userAction': 'CONFIRM', 'accessJustification': 'Confirmed by ' + taskPermittedSlid.slid], [], taskPermittedSlid.slid)
                taskPermittedSlid.isArchived = true
                taskPermittedSlid.s()
                render(view: 'confirmAccessVerification', model: [taskPermittedSlid: taskPermittedSlid])
            } else {
                render(view: 'actionAlreadyTaken', model: [task: taskPermittedSlid.task])
            }
        } else {
            response.sendError(404)
        }
    }

    def changeTaskStatus = {
        CentralWorkflowTaskPermittedSlid.markArchived(CentralWorkflowTask.read(params.long('id')), SessionUtils.getSession()?.loggedUser);
        redirect(action: 'list')
    }

    def sendUserResponse = {
        CentralWorkflowTask task = CentralWorkflowTask.get(params.long('id'))
        Map responseElements = params as HashMap
        responseElements.remove('action')
        responseElements.remove('controller')
        responseElements.remove('id')
        List<UploadedFile> uploadedFiles = AppUtil.populateAttachments(params)
        responseElements = responseElements.findAll { String key, value ->
            (!key.startsWith("multiFile"))
        }
        CentralWorkflowUtilService.sendResponseElements(task, responseElements, uploadedFiles)
        redirect(action: 'list')
    }

    def showIncompleteTasks = {
        Map tasksGrouped = [:]
        CentralWorkflowType type = CentralWorkflowType.findKey(params.type) as CentralWorkflowType
        String workerType = params.workerType.toString()
        String viewType = params.viewType.toString()
        // Shows incomplete access verification tasks only from the batch of last access verification job run if link is clicked from Dashboard.
        if (type == CentralWorkflowType.ACCESS_VERIFICATION && params.showResultsFromLastBatch.toString().equalsIgnoreCase('true')) {
            tasksGrouped = dashboardService.getAccessVerificationTaskMap(workerType, viewType)
        } else {
            List<CentralWorkflowTask> tasks = CentralWorkflowTask.findAllByWorkflowType(type)
            tasks = viewType ? dashboardService.filterTasksBasedOnInheritanceForLoggedInUser(tasks, viewType, workerType) : tasks
            tasks = workerType ? dashboardService.filterTasksOnWorkerType(tasks, workerType) : tasks
            tasksGrouped = tasks.groupBy {it.workflowGuid}
        }
        List<CentralWorkflowTask> tasks = []
        tasksGrouped.each {
            it.value?.each {task ->
                if (task.status != WorkflowTaskStatus.COMPLETE) {
                    tasks.add(task)
                }
            }
        }
        [tasks: tasks, taskType: type.toString().replace('_', ' ')]
    }

    def workflowTaskDocuments = {
        WorkflowTask task = CentralWorkflowTask.get(params.long('taskId'))
        render(template: 'documentPopUp', model: [documents: task?.documents, message: task.message])
    }

    def filterDialog = {
        List<CentralWorkflowTask> centralWorkflowTasks = CentralWorkflowTask.getPermittedTasks()
        render(template: 'workflowTaskFilter',
                model: [centralWorkflowTypeList: centralWorkflowTasks*.workflowType.unique().findAll {it}.sort {it.name},
                        workers: centralWorkflowTasks*.worker.unique().findAll {it}.sort {it.lastName},
                        entitlementPolicyList: EntitlementPolicy.list().sort {it.name},
                        securityRolesList: SessionUtils.session?.roles ? SecurityRole.findAllByNameInList(SessionUtils.session?.roles) : [],
                        filterVO: session.filterVO
                ])
    }

    def filterList = {
        List<CentralWorkflowTask> centralWorkflowTaskList = CentralWorkflowTask.getPermittedTasks()
        WorkflowTaskFilterVO workflowTaskFilterVO = new WorkflowTaskFilterVO(params?.workflowType, params?.workerId ? params?.workerId?.toLong() : null, params?.entitlementPolicyId ? params?.entitlementPolicyId?.toLong() : null, null, params?.entitlementId, params?.securityRoleId ? params?.securityRoleId?.toLong() : null)
        centralWorkflowTaskList = filterWorkflowTaskList(centralWorkflowTaskList, workflowTaskFilterVO)
        session.filterVO = workflowTaskFilterVO
        render view: 'list', model: [tasks: centralWorkflowTaskList]
    }

    def showAllTasks = {
        session.filterVO = null
        redirect action: 'list'
    }

    def filterCompletedTasks = {
        List<CentralWorkflowTask> centralWorkflowTasks = CentralWorkflowTask.getPermittedTasksCompleted()
        render(view: 'filterCompletedTasks',
                model: [centralWorkflowTypeList: centralWorkflowTasks*.workflowType.unique().findAll {it}.sort {it.name},
                        workers: centralWorkflowTasks*.worker.unique().findAll {it}.sort {it.lastName},
                        entitlementPolicyList: EntitlementPolicy.list().sort {it.name},
                        securityRolesList: SessionUtils.session?.roles ? SecurityRole.findAllByNameInList(SessionUtils.session?.roles) : [],
                        actorSlids: centralWorkflowTasks*.actorSlid.unique(),
                        filterVO: session.filterVO
                ])
    }

    def filteredCompletedTasks = {
        List<CentralWorkflowTask> centralWorkflowTaskList = CentralWorkflowTask.getPermittedTasksCompleted()
        WorkflowTaskFilterVO workflowTaskFilterVO = new WorkflowTaskFilterVO(params?.workflowType, params?.workerId ? params?.workerId?.toLong() : null, params?.entitlementPolicyId ? params?.entitlementPolicyId?.toLong() : null, null, params?.entitlementId, params?.securityRoleId ? params?.securityRoleId?.toLong() : null, params?.actorSlid)
        centralWorkflowTaskList = filterWorkflowTaskList(centralWorkflowTaskList, workflowTaskFilterVO)
        render view: 'filteredCompletedTasks', model: [tasks: centralWorkflowTaskList]
    }

    def storeAndAttachEvidence = {
        List<CentralWorkflowTask> apsWorkflowTaskList = params.list('taskIds') ? CentralWorkflowTask.getAll(params.list('taskIds')*.toLong()) : []
        List<UploadedFile> uploadedFiles = AppUtil.populateAttachments(params)
        List<CentralDataFile> apsDataFiles = []
        uploadedFiles?.each {UploadedFile uploadedFile ->
            apsDataFiles.add(new CentralDataFile(uploadedFile))
        }
        apsWorkflowTaskList.each {CentralWorkflowTask centralWorkflowTask ->
            centralWorkflowTask.documents.addAll(apsDataFiles as Set)
            centralWorkflowTask.s()
        }
        redirect(action: 'filterCompletedTasks')
    }
}

