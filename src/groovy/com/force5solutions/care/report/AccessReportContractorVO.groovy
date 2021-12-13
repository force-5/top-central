package com.force5solutions.care.report

class AccessReportContractorVO extends AccessReportWorkerVO {
    String vendor

    String toString() {
        id + " " + name + " " + badge + " " + slid + " " + vendor + " " + businessUnitRequester + " " + status + " " + statusChange
    }
}
