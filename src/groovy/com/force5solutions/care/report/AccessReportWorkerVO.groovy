package com.force5solutions.care.report

class AccessReportWorkerVO {
    Long id
    String name
    String badge
    String slid
    String businessUnitRequester
    String status
    Date statusChange

    String toString() {
        id + " " + name + " " + badge + " " + slid + " " + " " + businessUnitRequester + " " + status + " " + statusChange
    }
}
