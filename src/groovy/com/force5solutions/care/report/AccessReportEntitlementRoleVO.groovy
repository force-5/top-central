package com.force5solutions.care.report

class AccessReportEntitlementRoleVO {
    Date fromDate
    Date toDate
    String entitlementRole
    List<AccessReportWorkerVO> workers = []

    AccessReportEntitlementRoleVO() {

    }

    AccessReportEntitlementRoleVO(String entitlementRole, Date fromDate, Date toDate) {
        this.entitlementRole = entitlementRole
        this.fromDate = fromDate
        this.toDate = toDate
    }

}
