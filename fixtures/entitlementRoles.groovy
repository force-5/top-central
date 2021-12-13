import com.force5solutions.care.cc.Location
import com.force5solutions.care.common.EntitlementStatus
import com.force5solutions.care.cc.CcEntitlementRole

pre {
    bootstrapEntitlementRoles()
}

fixture {}

void bootstrapEntitlementRoles() {
    10.times {
        CcEntitlementRole entitlementRole = new CcEntitlementRole();
        entitlementRole.id = UUID.randomUUID().toString()
        entitlementRole.notes = "notes-${it}"
        entitlementRole.name = "Entitlement Role -${it}"
        entitlementRole.status = EntitlementStatus.ACTIVE
        entitlementRole.location = Location.list().find {it.isBusinessUnit()}
        entitlementRole.gatekeepers = "gatekeeper-${it}"
        entitlementRole.s();
    }
}
