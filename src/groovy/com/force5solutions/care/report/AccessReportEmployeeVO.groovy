package com.force5solutions.care.report

public class AccessReportEmployeeVO extends AccessReportWorkerVO {
    String supervisor

    String toString() {
        id + " " + name + " " + badge + " " + slid + " " + supervisor + " " + businessUnitRequester + " " + status + " " + statusChange
    }
}
