import com.force5solutions.care.cc.*

class ContactsFunctionalTests extends CareFunctionalTests {

    void testCreateVendor() {
        int initialCount = Vendor.count()
        login("admin", "admin")
        get('/vendor/create')
        def vendorFormData = VendorFormData.getDefaultVendorFormData();
        createVendor(vendorFormData)
        TARGET_ARGS_WITH_VALUES = ["${vendorFormData.companyName}"]
//        assertContentContains getMessage('vendor.create.message', TARGET_ARGS_WITH_VALUES)
        int finalCount = Vendor.count();
        assertEquals(1, finalCount - initialCount)
        assertContentContains "${vendorFormData.companyName}"
        assertContentContains "${vendorFormData.addressLine1}"
        assertContentContains "${vendorFormData.addressLine2}"
        logout()
    }

    void testCreateVendor_DUPLICATE_VENDOR_NAME_NOT_ALLOWED() {
        login("admin", "admin")
        get('/vendor/create')
        int initialCount = Vendor.count()
        def vendorFormData = VendorFormData.getDefaultVendorFormData();
        createVendor(vendorFormData)
        TARGET_ARGS_WITH_VALUES = ["${vendorFormData.companyName}"]
//        assertContentContains getMessage('vendor.create.message', TARGET_ARGS_WITH_VALUES)
        int intermediateCount = Vendor.count();
        assertEquals(1, intermediateCount - initialCount)
        get('/vendor/create')
        createVendor(vendorFormData)
        int finalCount = Vendor.count();
        assertEquals(0, finalCount - intermediateCount)
        assertContentContains getMessage('vendor.invalid.values.message')
        logout()
    }

    void testCreateVendor_BLANK_COMPANYNAME() {
        login("admin", "admin")
        get('/vendor/create')
        int initialCount = Vendor.count()
        def vendorFormData = VendorFormData.getDefaultVendorFormData();
        vendorFormData.companyName = ""
        createVendor(vendorFormData)
        int finalCount = Vendor.count()
        assertEquals(0, finalCount - initialCount)
        assertContentContains getMessage('vendor.invalid.values.message')
        logout()
    }

    void testEditVendor() {
        login("admin", "admin")
        get('/vendor/create')
        int initialCount = Vendor.count()
        def vendorFormData = VendorFormData.getDefaultVendorFormData();
        createVendor(vendorFormData)
        TARGET_ARGS_WITH_VALUES = ["${vendorFormData.companyName}"]
//        assertContentContains getMessage('vendor.create.message', TARGET_ARGS_WITH_VALUES)
        int intermediateCount = Vendor.count();
        assertEquals(1, intermediateCount - initialCount)

        Vendor vendor = Vendor.findByCompanyName(vendorFormData.companyName)
        get("/vendor/edit/${vendor.id}")
        vendorFormData.companyName = "New Gfunc Company-${System.currentTimeMillis()}"
        updateVendor(vendorFormData)
        int finalCount = Vendor.count();
        assertEquals(0, finalCount - intermediateCount)
        TARGET_ARGS_WITH_VALUES = ["${vendorFormData.companyName}"]
//        assertContentContains getMessage('vendor.update.message', TARGET_ARGS_WITH_VALUES)
        logout()
    }

    void testCreateContractorSupervisor() {
        login("admin", "admin")
        get('/contractorSupervisor/create')
        int initialCount = ContractorSupervisor.count()
        def supervisorFormData = ContractorSupervisorFormData.getDefaultContractorSupervisorFormData()
        createContractorSupervisor(supervisorFormData)
        int finalCount = ContractorSupervisor.count()
        assertEquals(1, finalCount - initialCount)
        TARGET_ARGS_WITH_VALUES = ["${supervisorFormData.lastName}", "${supervisorFormData.firstName}", "${supervisorFormData.middleName}"]
//        assertContentContains getMessage('contractor.supervisor.create.message', TARGET_ARGS_WITH_VALUES)
        assertContentContains "${supervisorFormData.lastName}"
        assertContentContains "${supervisorFormData.firstName}"
        assertContentContains "${supervisorFormData.middleName}"
        logout()
    }

    void testCreateContractorSupervisor_BLANK_NAME() {
        login("admin", "admin")
        get('/contractorSupervisor/create')
        int initialCount = ContractorSupervisor.count()
        def supervisorFormData = ContractorSupervisorFormData.getDefaultContractorSupervisorFormData()
        supervisorFormData.firstName = ""
        createContractorSupervisor(supervisorFormData)
        int finalCount = ContractorSupervisor.count()
        assertEquals(0, finalCount - initialCount)
        assertContentContains getMessage('contractor.supervisor.invalid.values.message')
        logout()
    }

    void testEditContractorSupervisor() {
        login("admin", "admin")
        get('/contractorSupervisor/create')
        int initialCount = ContractorSupervisor.count()
        def supervisorFormData = ContractorSupervisorFormData.getDefaultContractorSupervisorFormData()
        createContractorSupervisor(supervisorFormData)
        int intermediateCount = ContractorSupervisor.count()
        assertEquals(1, intermediateCount - initialCount)
        TARGET_ARGS_WITH_VALUES = ["${supervisorFormData.lastName}", "${supervisorFormData.firstName}", "${supervisorFormData.middleName}"]
//        assertContentContains getMessage('contractor.supervisor.create.message', TARGET_ARGS_WITH_VALUES)
        assertContentContains "${supervisorFormData.lastName}"
        assertContentContains "${supervisorFormData.firstName}"
        assertContentContains "${supervisorFormData.middleName}"

        List<ContractorSupervisor> contractorSupervisors = ContractorSupervisor.list().reverse()
        ContractorSupervisor contractorSupervisor = contractorSupervisors.get(0)
        get("/contractorSupervisor/edit/${contractorSupervisor.id}")
        supervisorFormData.firstName = "New Gfunc Supervisor"
        editContractorSupervisor(supervisorFormData)
        TARGET_ARGS_WITH_VALUES = ["${supervisorFormData.lastName}", "${supervisorFormData.firstName}", "${supervisorFormData.middleName}"]
//        assertContentContains getMessage('contractor.supervisor.update.message', TARGET_ARGS_WITH_VALUES)
        int finalCount = ContractorSupervisor.count()
        assertEquals(0, finalCount - intermediateCount)
        logout()
    }

    void testCreateBusinessUnitRequester() {
        login("admin", "admin")
        get('/businessUnitRequester/create')
        int initialCount = BusinessUnitRequester.count()
        def businessUnitRequesterFormData = BusinessUnitRequesterFormData.getDefaultBusinessUnitRequesterFormData()
        createBusinessUnitRequester(businessUnitRequesterFormData)
        int finalCount = BusinessUnitRequester.count()
        assertEquals(1, finalCount - initialCount)
        TARGET_ARGS_WITH_VALUES = ["${businessUnitRequesterFormData.lastName}", "${businessUnitRequesterFormData.firstName}", "${businessUnitRequesterFormData.middleName}"]
        assertContentContains getMessage('businessUnitRequester.create.message', TARGET_ARGS_WITH_VALUES)
        assertContentContains "${businessUnitRequesterFormData.unit}"
        assertContentContains "${businessUnitRequesterFormData.firstName}"
        assertContentContains "${businessUnitRequesterFormData.lastName}"
        assertContentContains "${businessUnitRequesterFormData.middleName}"
        assertContentContains "${businessUnitRequesterFormData.phone}"
        assertContentContains "${businessUnitRequesterFormData.slid}"
        assertContentContains "${businessUnitRequesterFormData.notes}"
        logout()
    }

    void testCreateBusinessUnitRequester_BLANK_NAME() {
        login("admin", "admin")
        get('/businessUnitRequester/create')
        int initialCount = BusinessUnitRequester.count()
        def businessUnitRequesterFormData = BusinessUnitRequesterFormData.getDefaultBusinessUnitRequesterFormData()
        businessUnitRequesterFormData.firstName = ""
        createBusinessUnitRequester(businessUnitRequesterFormData)
        int finalCount = BusinessUnitRequester.count()
        assertEquals(0, finalCount - initialCount)
        assertContentContains getMessage('businessUnitRequester.invalid.values.message')
        logout()
    }


    void testCreateBusinessUnitRequester_DUPLICATE_SLID_NOT_ALLOWED() {
        login("admin", "admin")
        get('/businessUnitRequester/create')
        int initialCount = BusinessUnitRequester.count()
        def businessUnitRequesterFormData = BusinessUnitRequesterFormData.getDefaultBusinessUnitRequesterFormData()
        businessUnitRequesterFormData.slid = "SLID-BUR"
        createBusinessUnitRequester(businessUnitRequesterFormData)
        int intermediateCount = BusinessUnitRequester.count()
        assertEquals(1, intermediateCount - initialCount)
        TARGET_ARGS_WITH_VALUES = ["${businessUnitRequesterFormData.lastName}", "${businessUnitRequesterFormData.firstName}", "${businessUnitRequesterFormData.middleName}"]
        assertContentContains getMessage('businessUnitRequester.create.message', TARGET_ARGS_WITH_VALUES)

        get('/businessUnitRequester/create')
        createBusinessUnitRequester(businessUnitRequesterFormData)
        int finalCount = BusinessUnitRequester.count()
        assertEquals(0, finalCount - intermediateCount)
        assertContentContains getMessage('businessUnitRequester.invalid.values.message')
        logout()
    }

    void testEditBusinessUnitRequester() {
        login("admin", "admin")
        get('/businessUnitRequester/create')
        int initialCount = BusinessUnitRequester.count()
        def businessUnitRequesterFormData = BusinessUnitRequesterFormData.getDefaultBusinessUnitRequesterFormData()
        createBusinessUnitRequester(businessUnitRequesterFormData)
        int intermediateCount = BusinessUnitRequester.count()
        assertEquals(1, intermediateCount - initialCount)
        TARGET_ARGS_WITH_VALUES = ["${businessUnitRequesterFormData.lastName}", "${businessUnitRequesterFormData.firstName}", "${businessUnitRequesterFormData.middleName}"]
        assertContentContains getMessage('businessUnitRequester.create.message', TARGET_ARGS_WITH_VALUES)
        assertContentContains "${businessUnitRequesterFormData.unit}"
        assertContentContains "${businessUnitRequesterFormData.firstName}"
        assertContentContains "${businessUnitRequesterFormData.lastName}"
        assertContentContains "${businessUnitRequesterFormData.middleName}"
        assertContentContains "${businessUnitRequesterFormData.phone}"
        assertContentContains "${businessUnitRequesterFormData.slid}"
        assertContentContains "${businessUnitRequesterFormData.notes}"

        BusinessUnitRequester businessUnitRequester = BusinessUnitRequester.findBySlid(businessUnitRequesterFormData.slid)
        get("/businessUnitRequester/edit/${businessUnitRequester.id}")
        businessUnitRequesterFormData.firstName = "New Gfunc First"
        editBusinessUnitRequester(businessUnitRequesterFormData)
        int finalCount = BusinessUnitRequester.count()
        assertEquals(0, finalCount - intermediateCount)
        TARGET_ARGS_WITH_VALUES = ["${businessUnitRequesterFormData.lastName}", "${businessUnitRequesterFormData.firstName}", "${businessUnitRequesterFormData.middleName}"]
        assertContentContains getMessage('businessUnitRequester.update.message', TARGET_ARGS_WITH_VALUES)
        logout()
    }

}
