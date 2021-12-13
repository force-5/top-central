package com.force5solutions.care.report

class ComplianceSummaryReportVO implements Cloneable{

    String vendor
    String contractor
    String supervisor
    Date cyberTrainingInitialCompletionDate
    Date cyberTrainingLastCompletionDate
    Date physicalTrainingInitialCompletionDate
    Date physicalTrainingLastCompletionDate
    Date praTrainingCompletionDate
    Date praTrainingDueDate
    String location
    Date accessGrantDate
    Date accessRevokeDate
    String accessRevokeReason

}