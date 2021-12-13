package com.force5solutions.care.cc

class EntitlementRoleService {

//    def historyService

    private void refreshEntitlementRole(CcEntitlementRole entitlementRole) {
        if (!entitlementRole.isAttached()) {
            try {
                entitlementRole.attach()
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
    }

    public Boolean save(CcEntitlementRole entitlementRole) {
        if (!entitlementRole.location) {
            entitlementRole.location = Location.list()?.find {it.isBusinessUnit()}
        }
        String type, description;
        if (entitlementRole.id) {
            type = "Update"
            description = "Entitlement Role ${entitlementRole} updated"
            refreshEntitlementRole(entitlementRole)
        } else {
            type = "Create"
            description = "New Entitlement Role ${entitlementRole} created"
        }
        boolean saveSuccess = entitlementRole.s()
        if (saveSuccess) {
//            saveHistory(entitlementRole, type, description);
        }
        return saveSuccess
    }

//    public void saveHistory(CcEntitlementRole entitlementRole, String historyType, String _description) {
    //        History history = new EntitlementRoleHistory()
    //        history = historyService.createHistory(history, historyType, _description)
    //        history.entitlementRoleId = entitlementRole.id
    //        history.s()
    //    }

}