package com.force5solutions.care.cc

class EntitlementService {

    def historyService

    private void refreshEntitlement(CcEntitlement entitlement) {
        if (!entitlement.isAttached()) {
            try {
                entitlement.attach()
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
    }

    public Boolean saveEntitlement(CcEntitlement entitlement) {
        String type, description;
        if (entitlement.id) {
            type = "Update"
            description = "Entitlement ${entitlement} updated"
            refreshEntitlement(entitlement)
        } else {
            entitlement.id = UUID.randomUUID().toString()
            type = "Create"
            description = "New Entitlement ${entitlement} created"
        }
        boolean saveSuccess = entitlement.s()
        if (saveSuccess) {
//            saveHistory(entitlement, type, description);
        }
        return saveSuccess
    }

//    public void saveHistory(CcEntitlement entitlement, String historyType, String _description) {
    //        History history = new CcEntitlementHistory()
    //        history = historyService.createHistory (history, historyType, _description)
    //        history.entitlementId = entitlement.id
    //        history.s()
    //    }

}