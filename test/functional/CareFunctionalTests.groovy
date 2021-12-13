import com.force5solutions.care.cc.*
import com.gargoylesoftware.htmlunit.html.HtmlAnchor

import org.codehaus.groovy.grails.commons.ApplicationHolder


class CareFunctionalTests extends functionaltestplugin.FunctionalTestCase {

    def messageSource = ApplicationHolder.getApplication().getMainContext().getBean("messageSource")
    Locale locale = new Locale('en', 'US')
    Object[] TARGET_ARGS_EMPTY = [].toArray()
    Object[] TARGET_ARGS_WITH_VALUES = [].toArray()

    def workerCertificationService
    /**
     // This is useful if you want to run the tests against any other URL
     void setUp() {super.setUp()
     baseURL = System.getProperty('baseURL')}*                                  */
    void testLogin() {
        login("admin", "admin")
        assertContentContains getMessage('message.valid.login')
    }

    void waitForElementToAppear(String elementName, int numberOfSeconds = 600) {
        int counter = 0;
        while (counter < numberOfSeconds) {
            Thread.sleep(1000)
            def element = byId(elementName)
            if (!element) {
                element = byName(elementName)
            }
            if (element) {
                return;
            }
            counter = counter + 1;
        }
        fail("Element with the name ${elementName} not found on page.")
    }

    void testLogin_WITH_INVALID_PASSWORD() {
        login("admin", "invalid_password")
        assertStatus 200
        assertContentContains getMessage('message.invalid.login')
    }


    void login(String userName, String userPassword) {
        get('/contractor/list')
        form('loginForm')
                {
                    slid = userName
                    password = userPassword
                    click "loginbtn"
                }
    }

    void login(LoginFormData loginFormData) {
        login(loginFormData.username, loginFormData.password)
    }

    void logout() {
        HtmlAnchor logoutLink = page.getAnchors().find {
            it.getAttribute("href") == "/care/login/logout"
        }
        logoutLink.click()
    }

    void createContractor(ContractorFormData contractorFormData) {
        form('contractorForm')
                {
                    firstName = contractorFormData.firstName
                    middleName = contractorFormData.middleName
                    lastName = contractorFormData.lastName

                    birthMonth = byName('birthMonth').getOption(2).getValueAttribute()
                    birthDay = byName('birthDay').getOption(7).getValueAttribute()
                    badgeNumber = "456674"
                    ContractorSupervisor contractorSupervisor
                    int supervisorCount = ContractorSupervisor.count()
                    if (supervisorCount) {
                        contractorSupervisor = ContractorSupervisor.get(supervisorCount)
                    }
                    else {
                        ContractorSupervisorFormData supervisorFormData = ContractorSupervisorFormData.getDefaultContractorSupervisorFormData()
                        createContractorSupervisor(supervisorFormData)
                        contractorSupervisor = ContractorSupervisor.get(supervisorCount)
                    }
                    Vendor vendor = contractorSupervisor.vendor
                    primeVendor = byName('primeVendor').getOptionByValue(vendor.id.toString()).getValueAttribute()
                    waitForElementToAppear('supervisor')
                    supervisor = byId('supervisor').getOptionByValue(contractorSupervisor.id.toString()).getValueAttribute()
                    List<BusinessUnitRequester> businessUnitRequesters = BusinessUnitRequester.list()
                    Integer burCount = BusinessUnitRequester.count()
                    BusinessUnitRequester bur = businessUnitRequesters.get(new Random().nextInt(burCount))
                    byId('businessUnitRequesters-select').getOptionByValue(bur.id.toString()).setSelected(true)
                    phone = contractorFormData.phone
                    email = contractorFormData.email
                    formOfId = contractorFormData.formOfId
                    slid = contractorFormData.slid
                    notes = contractorFormData.notes
                    click "submit"
                }
    }

    void updateContractor_NAME(ContractorFormData contractorFormData) {
        form('contractorEditForm')
                {
                    firstName = contractorFormData.firstName
                    middleName = contractorFormData.middleName
                    lastName = contractorFormData.lastName
                    click "updateContractor"
                }
    }

    void updateContractor_FIRST_NAME(ContractorFormData contractorFormData) {
        form('contractorEditForm')
                {
                    firstName = contractorFormData.firstName
                    click "updateContractor"
                }
    }

    void updateContractor_SLID(ContractorFormData contractorFormData) {
        form('contractorEditForm')
                {
                    slid = contractorFormData.slid
                    click "updateContractor"
                }
    }

    void updateContractor_BIRTHDAY(ContractorFormData contractorFormData) {
        form('contractorEditForm')
                {
                    birthDay = contractorFormData.birthDay
                    click "updateContractor"
                }
    }

    void createVendor(VendorFormData vendorFormData) {
        form('vendorForm')
                {
                    companyName = vendorFormData.companyName
                    addressLine1 = vendorFormData.addressLine1
                    addressLine2 = vendorFormData.addressLine2
                    city = vendorFormData.city
                    state = vendorFormData.state
                    zipCode = vendorFormData.zipCode
                    phone = vendorFormData.phone
                    notes = vendorFormData.notes
                    agreementExpirationDate = vendorFormData.agreementExpirationDate
                    click "submitVendor"
                }
    }

    void updateVendor(VendorFormData vendorFormData) {
        form('vendorUpdateForm')
                {
                    companyName = vendorFormData.companyName
                    addressLine1 = vendorFormData.addressLine1
                    addressLine2 = vendorFormData.addressLine2
                    city = vendorFormData.city
                    state = vendorFormData.state
                    zipCode = vendorFormData.zipCode
                    phone = vendorFormData.phone
                    notes = vendorFormData.notes
                    agreementExpirationDate = vendorFormData.agreementExpirationDate
                    click "updateVendorButton"
                }
    }

    void updateCertification(CertificationFormData certificationFormData) {
        form('editCertificationForm')
                {
                    name = certificationFormData.name
                    description = certificationFormData.description
                    period = certificationFormData.period
                    byId('periodUnit').getOptionByValue(certificationFormData.periodUnit).setSelected(true)
                    expirationOffset = certificationFormData.expirationOffset
                    click "updateCertificationButton"
                }
    }

    void createContractorSupervisor(ContractorSupervisorFormData supervisorFormData) {
        form('contractorSupervisorForm')
                {
                    vendorId = supervisorFormData.vendorId
                    firstName = supervisorFormData.firstName
                    middleName = supervisorFormData.middleName
                    lastName = supervisorFormData.lastName
                    phone = supervisorFormData.phone
                    email = supervisorFormData.email
                    notes = supervisorFormData.notes
                    click "submitContractorSupervisor"
                }
    }


    void editContractorSupervisor(ContractorSupervisorFormData supervisorFormData) {
        form('editSupervisorForm')
                {
                    vendorId = supervisorFormData.vendorId
                    firstName = supervisorFormData.firstName
                    middleName = supervisorFormData.middleName
                    lastName = supervisorFormData.lastName
                    phone = supervisorFormData.phone
                    email = supervisorFormData.email
                    notes = supervisorFormData.notes
                    click "editSupervisorButton"
                }
    }

    void createBusinessUnitRequester(BusinessUnitRequesterFormData businessUnitRequesterFormData) {
        form('businessUnitRequesterForm')
                {
                    byId('unit').getOption(0).setSelected(true)
                    firstName = businessUnitRequesterFormData.firstName
                    middleName = businessUnitRequesterFormData.middleName
                    lastName = businessUnitRequesterFormData.lastName
                    phone = businessUnitRequesterFormData.phone
                    slid = businessUnitRequesterFormData.slid
                    notes = businessUnitRequesterFormData.notes
                    click "submitBusinessUnitRequester"
                }
    }

    void editBusinessUnitRequester(BusinessUnitRequesterFormData businessUnitRequesterFormData) {
        form('editBusinessUnitRequesterForm')
                {
                    byId('unit').getOption(0).setSelected(true)
                    firstName = businessUnitRequesterFormData.firstName
                    middleName = businessUnitRequesterFormData.middleName
                    lastName = businessUnitRequesterFormData.lastName
                    phone = businessUnitRequesterFormData.phone
                    slid = businessUnitRequesterFormData.slid
                    notes = businessUnitRequesterFormData.notes
                    click "editBusinessUnitRequesterButton"
                }
    }

    /**
     * Helper method for filling up the certification creationg form.
     */
    void createCertification(CertificationFormData certificationFormData) {
        form('certificationForm') {
            name = certificationFormData.name
            description = certificationFormData.description
            period = certificationFormData.period
            byId('periodUnit').getOptionByValue(certificationFormData.periodUnit).setSelected(true)
            expirationOffset = certificationFormData.expirationOffset
            click "submitCertification"
        }
    }

    /**
     * Helper method for creating a location.
     */
    String createLocation(LocationFormData locationFormData) {
        form('createLocationForm')
                {
                    name = locationFormData.name
                    List<Certification> certifications = Certification.list()
                    Integer certificationCount = Certification.count()
                    Certification certification = certifications.get(new Random().nextInt(certificationCount))
                    byId('requiredCertifications-select').getOptionByValue(certification.id.toString()).setSelected(true)
                    notes = locationFormData.notes
                    click "newCompanyCreation"
                }
        return locationFormData.name
    }

    /**
     * Helper method for creating a Physical Location.
     */
    void createLocationPhysical(LocationFormData locationFormData) {
        Thread.sleep(180000)
        form('createLocationForm')
                {
                    byId('requiredCertifications-select').getOption(new Random().nextInt(3)).setSelected(true)
                    notes = locationFormData.notes
                    click "newCompanyCreation"
                }
    }

    /**
     * Helper method for creating a Cyber Location.
     */
    void createLocationCyber(LocationFormData locationFormData) {
        Thread.sleep(180000)
        form('createLocationForm')
                {
                    byId('requiredCertifications-select').getOption(new Random().nextInt(3)).setSelected(true)
                    notes = locationFormData.notes
                    click "newCompanyCreation"
                }
    }

    // TODO : This method doesn't seem to be used anywhere. Remove it after confirmation.

    void createCriticalPhysicalLocation() {
        List<HtmlAnchor> criticalPhysicalLocations = page.getAnchors()
        def criticalPhysicalLocation = criticalPhysicalLocations.find {
            it.getAttribute("href") == "#4"
        }
        criticalPhysicalLocation.click()
        waitForElementToAppear('createLocationForm')
        form('createLocationForm')
                {
                    name = "Gfunc Critical Physical"
                    byId('requiredCertifications-select').getOption(new Random().nextInt(5)).setSelected(true)
                    notes = "Gfunc location notes"
                    click "newCompanyCreation"
                }
    }

    void addLocationToContractor(Contractor contractor) {
        List<HtmlAnchor> allAnchors = page.getAnchors()
        def accessLink = allAnchors.find {
            it.getAttribute("href") == "/care/workerEntitlementRole/access/${contractor.id}"
        }
        accessLink.click()
        def addLocation = byId("addWorkerEntitlementRole")

        List<Certification> certifications = Certification.list()
        certifications.each {Certification certification ->
            workerCertificationService.saveNewWorkerCertificationWithDateCompleted(contractor.id, certification.id, new Date())
        }
        addLocation.click()
        waitForElementToAppear('add_contractor_cert')
        Thread.sleep(10000)
        CcEntitlementRole entitlementRole = CcEntitlementRole.listUndeleted().first()
        Location location = entitlementRole.location.parent

        def locationAnchor = page.getAnchors().find {it.getAttribute('class') == "location-${location.id}"}
        locationAnchor.click()

        waitForElementToAppear('children')
        Thread.sleep(10000)
        def cyberAnchor = page.getAnchors().find {it.getAttribute('class') == "location-${entitlementRole.location.id}"}
        cyberAnchor.click()
        waitForElementToAppear('children')
        Thread.sleep(10000)

        def criticalCyberAnchor = page.getAnchors().find {it.getAttribute('class') == "location-add-${entitlementRole.id }"}
        criticalCyberAnchor.click()

        form('roleForm')
                {
                    byId("accessJustification").setText("Access Justification")
                    click "accessJustificationButton"
                }
        Thread.sleep(10000)
        waitForElementToAppear('addLocationButton')
        def addLocationButton = byId('addLocationButton')
        addLocationButton.click()
        Thread.sleep(10000)
    }

    /**
     * Helper method for filling up the form for creating an AlertConfig.
     */
    void createAlertConfig(AlertConfigFormData alertConfigFormData) {
        form('alertConfigForm')
                {
                    name = alertConfigFormData.name
                    alertType = byId('alertType').getOption(1).getValueAttribute()
                    waitForElementToAppear('alertTemplateName')
                    alertTemplateName = byId('alertTemplateName').getOption(1).getValueAttribute()
                    alertTemplatePeriod = alertConfigFormData.alertTemplatePeriod
                    alertTemplatePeriodUnit = byId('alertTemplatePeriodUnit').getOption(4).getValueAttribute()
                    click "submitAlertConfig"
                }
    }

    /**
     * Helper method to return a message from the message bundle.
     */
    String getMessage(String key, def targetArgs = TARGET_ARGS_EMPTY) {
        def keyValue = messageSource.resolveCode(key, locale)
        return keyValue?.format(targetArgs)
    }
}


public class VendorFormData {
    String companyName
    String addressLine1
    String addressLine2
    String city
    String state
    String zipCode
    String phone
    String notes
    String agreementExpirationDate

    public static VendorFormData getDefaultVendorFormData() {
        VendorFormData vendorFormData = new VendorFormData();
        vendorFormData.companyName = "vn-${System.currentTimeMillis()}"
        vendorFormData.addressLine1 = "New Address 1"
        vendorFormData.addressLine2 = "New Address 2"
        vendorFormData.city = "VendorCity"
        vendorFormData.state = "South Carolina"
        vendorFormData.zipCode = "456674"
        vendorFormData.phone = '376487345'
        vendorFormData.notes = "Vendor Notes"
        vendorFormData.agreementExpirationDate = '03/23/2010'
        return vendorFormData;
    }
}

public class ContractorSupervisorFormData {
    String vendorId
    String firstName
    String middleName
    String lastName
    String phone
    String email
    String notes

    public static ContractorSupervisorFormData getDefaultContractorSupervisorFormData() {
        ContractorSupervisorFormData supervisorFormData = new ContractorSupervisorFormData();
        supervisorFormData.vendorId = "2"
        supervisorFormData.firstName = "S-Gfunc-first"
        supervisorFormData.middleName = "S-Gfunc-middle"
        supervisorFormData.lastName = "S-Gfunc-last"
        supervisorFormData.phone = "43764783"
        supervisorFormData.email = "Gfunc_Supervisor@example.com"
        supervisorFormData.notes = "Supervisor Notes"
        return supervisorFormData;
    }
}

public class BusinessUnitRequesterFormData {
    String unit
    String firstName
    String middleName
    String lastName
    String phone
    String slid
    String notes

    public static BusinessUnitRequesterFormData getDefaultBusinessUnitRequesterFormData() {
        BusinessUnitRequesterFormData businessUnitRequesterFormData = new BusinessUnitRequesterFormData();
        businessUnitRequesterFormData.unit = "5"
        businessUnitRequesterFormData.firstName = "Gfunc-first"
        businessUnitRequesterFormData.middleName = "Gfunc-middle"
        businessUnitRequesterFormData.lastName = "Gfunc-last"
        businessUnitRequesterFormData.phone = "43764783"
        businessUnitRequesterFormData.slid = "Slid-${System.currentTimeMillis()}"
        businessUnitRequesterFormData.notes = "BusinessUnitRequester Notes"
        return businessUnitRequesterFormData;
    }
}


public class CertificationFormData {
    String name
    String description
    String period
    String periodUnit
    String expirationOffset

    public static CertificationFormData getDefaultCertificationFormData() {
        CertificationFormData certificationFormData = new CertificationFormData();
        certificationFormData.name = "Gfunc-test-${System.currentTimeMillis()}"
        certificationFormData.description = "Gfunc Test Certification"
        certificationFormData.period = '5'
        certificationFormData.periodUnit = "Years"
        certificationFormData.expirationOffset = '5'
        return certificationFormData;
    }
}

public class ContractorFormData {
    String firstName
    String middleName
    String lastName
    String birthMonth
    String birthDay
    String badgeNumber
    String primeVendor
    String supervisor
    String businessUnitRequesters
    String phone
    String email
    String formOfId
    String slid
    String notes

    public static ContractorFormData getDefaultContractorFormData() {
        ContractorFormData contractorFormData = new ContractorFormData();
        contractorFormData.firstName = "Gfunc-first"
        contractorFormData.middleName = "Gfunc-middle"
        contractorFormData.lastName = "Gfunc-last"
        contractorFormData.birthMonth = "August"
        contractorFormData.birthDay = '3'
        contractorFormData.badgeNumber = "54546"
        contractorFormData.primeVendor = "vendor1"
        contractorFormData.supervisor = "supervisor1, supervisor1 supervisor1"
        contractorFormData.businessUnitRequesters = "ln-BUR11, fn-BUR11 mn-BUR11"
        contractorFormData.phone = "561821918"
        contractorFormData.email = "Gfunc_contractor@email.com"
        contractorFormData.formOfId = "53526362"
        contractorFormData.slid = "SLID-${System.currentTimeMillis()}"
        contractorFormData.notes = "Contractor Notes"
        return contractorFormData;
    }
}

public class AlertConfigFormData {
    String name
    String alertType
    String alertTemplateName
    String alertTemplatePeriod
    String alertTemplatePeriodUnit

    public static AlertConfigFormData getDefaultAlertConfigFormData() {
        AlertConfigFormData alertConfigFormData = new AlertConfigFormData();
        alertConfigFormData.name = "Gfunc-Alert-Config-${System.currentTimeMillis()}"
        alertConfigFormData.alertType = "Access Granted"
        alertConfigFormData.alertTemplateName = "Access granted Template"
        alertConfigFormData.alertTemplatePeriod = "5"
        alertConfigFormData.alertTemplatePeriodUnit = "Years"
        return alertConfigFormData;
    }
}

public class AlertTemplateFormData {
    String name
    String transportType
    String alertType
    String messageTemplate

    public static AlertTemplateFormData getDefaultAlertTemplateFormData() {
        AlertTemplateFormData alertTemplateFormData = new AlertTemplateFormData();
        alertTemplateFormData.name = "Gfunc-Alert-Config-${System.currentTimeMillis()}"
        alertTemplateFormData.transportType = "Access Granted"
        alertTemplateFormData.alertType = "Access granted Template"
        alertTemplateFormData.messageTemplate = "5"
        return alertTemplateFormData;
    }
}

public class LocationFormData {
    String name
    String certification
    String notes

    public static LocationFormData getDefaultLocationFormData() {
        LocationFormData locationFormData = new LocationFormData()
        locationFormData.name = "Gfunc-Location-${System.currentTimeMillis()}"
        locationFormData.certification = "11"
        locationFormData.notes = "Location notes"
        return locationFormData
    }
}

public class LoginFormData {
    String username
    String password

    public static LoginFormData getDefaultLoginFormData() {
        LoginFormData loginFormData = new LoginFormData()
        loginFormData.username = "admin"
        loginFormData.password = "admin"
        return loginFormData
    }
}