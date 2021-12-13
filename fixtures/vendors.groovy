import com.force5solutions.care.cc.Vendor
import com.force5solutions.care.cc.AppUtil
pre {
    if (!Vendor.count()) {
        println "Populating Sample Vendor"
        populateSampleVendors();
    }

}

fixture {}


void populateSampleVendors() {
    List stateMapKeys = AppUtil.getStateMap().keySet() as List
    (1..5).each {
        Vendor vendor = new Vendor()
        vendor.companyName = "vendor$it"
        vendor.addressLine1 = "addressLine1_$it"
        vendor.addressLine2 = "addressLine2_$it"
        vendor.city = "city$it"
        vendor.state = stateMapKeys.get(new Random().nextInt(stateMapKeys.size()))
        vendor.zipCode = "11111$it"
        vendor.phone = "$it"
        vendor.agreementExpirationDate = new Date() + (365 + new Random().nextInt(1000))
        vendor.s()
    }
}