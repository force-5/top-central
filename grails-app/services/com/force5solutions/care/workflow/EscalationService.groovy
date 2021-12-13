package com.force5solutions.care.workflow

import com.force5solutions.care.cc.AppUtil

class EscalationService {

    boolean transactional = true
    def centralWorkflowTaskService

    def escalateTasks(Date currentDateTime = new Date()) {
        log.debug "Checking for tasks to be escalated at ${currentDateTime}"
        List<CentralWorkflowTask> tasksToBeEscalated = findTasksToBeEscalated(currentDateTime)
        tasksToBeEscalated.each {CentralWorkflowTask taskToBeEscalated ->
            centralWorkflowTaskService.escalateWorkflowTask(taskToBeEscalated)
        }
    }

    private List<CentralWorkflowTask> findTasksToBeEscalated(Date currentDateTime) {
        List<CentralWorkflowTask> tasksToBeEscalated = []
        List<CentralWorkflowTask> candidatesForEscalation = CentralWorkflowTask.findAllByStatusInListAndEscalationTemplateIdIsNotNull([WorkflowTaskStatus.NEW, WorkflowTaskStatus.PENDING])

        candidatesForEscalation.each {CentralWorkflowTask task ->
            if (AppUtil.getDatePlusPeriodUnit(task.effectiveStartDate, task.period, task.periodUnit) < currentDateTime) {
                tasksToBeEscalated.add(task)
            }
        }
        return tasksToBeEscalated
    }
}
