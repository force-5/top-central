import com.force5solutions.care.cc.EntitlementPolicy

fixture {
    physical(EntitlementPolicy) {
        name = "Physical"
        isApproved = true
    }

    cyber(EntitlementPolicy) {
        name = "Cyber"
        isApproved = true
    }
}
