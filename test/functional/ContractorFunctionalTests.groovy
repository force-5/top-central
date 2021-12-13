import com.force5solutions.care.cc.WorkerCertification
import com.force5solutions.care.cc.WorkerStatus
import com.force5solutions.care.cc.Contractor

class ContractorFunctionalTests extends CareFunctionalTests {

    void testCreateContractor() {
        login("admin", "admin")
        int initialCount = Contractor.count()
        get('/contractor/create')
        def contractorFormData = ContractorFormData.getDefaultContractorFormData()
        createContractor(contractorFormData)
        assertContentContains (getMessage('contractor.create.message'))
        int finalCount = Contractor.count()
        assertEquals(1, finalCount - initialCount)
    }

    void testDeleteContractor() {
        login("admin", "admin")
        int initialCount = Contractor.count()
        get('/contractor/create')
        def contractorFormData = ContractorFormData.getDefaultContractorFormData()
        createContractor(contractorFormData)
        assertContentContains getMessage('contractor.create.message')

        int afterCreation = Contractor.count()
        List<Contractor> contractors = Contractor.list()
        Contractor contractor = new Contractor()
        contractors.each {
            if (it.status == WorkerStatus.UNASSIGNED) {
                contractor = it
            }
        }
        get("/contractor/show/${contractor.id}")
        def deleteLink = byId('deleteWorkerButton')
        deleteLink.click()
        Thread.sleep(10000)
        int finalCount = Contractor.count()
        assertEquals(1, afterCreation - initialCount)
        assertEquals(1, afterCreation - finalCount)
        assertEquals(initialCount, finalCount)

    }

    void testCreateContractor_BLANK_FILEDS() {
        login("admin", "admin")
        get('/contractor/create')
        def contractorFormData = ContractorFormData.getDefaultContractorFormData()
        contractorFormData.firstName = ""
        contractorFormData.lastName = ""
        createContractor(contractorFormData)
        assertContentContains getMessage('contractor.invalid.values.message')
    }

    void testUpdateContractor_NAME_UPDATED() {
        login("admin", "admin")
        get('/contractor/create')
        def newContractorFormData = ContractorFormData.getDefaultContractorFormData()
        createContractor(newContractorFormData)
        assertContentContains getMessage('contractor.create.message')

        def editButton = byId("editButton")
        editButton.click()
        def contractorFormData = ContractorFormData.getDefaultContractorFormData()
        contractorFormData.firstName = "Changed-First-Name"
        contractorFormData.middleName = "Changed-Middle-Name"
        contractorFormData.lastName = "Changed-Last-Name"
        updateContractor_NAME(contractorFormData)
        assertContentContains "Changed-First-Name"
        assertContentContains "Changed-Middle-Name"
        assertContentContains "Changed-Last-Name"
    }


    void testAddCertificationToContractor() {
        login("admin", "admin")
        get('/contractor/create')
        def contractorFormData = ContractorFormData.getDefaultContractorFormData()
        createContractor(contractorFormData)
        assertContentContains getMessage('contractor.create.message')

        List<Contractor> contractors = Contractor.list().reverse()
        Contractor contractor = contractors.get(0)

        get("/workerCertification/certification/${contractor?.id}")
        List<WorkerCertification> initialWorkerCertifications = WorkerCertification.findAllByWorker(contractor)
        int initialCount = initialWorkerCertifications.size()

        def addCertificationLink = page.getAnchors().find {
            it.getRelAttribute() == "addWorkerCertification"
        }
        addCertificationLink.click()

        waitForElementToAppear('certificationId')
        def certificationsList = byId('certificationId')
        certificationsList.getOption(1).setSelected(true)
        waitForElementToAppear('dateCompleted')
        form('addWorkerCertificationForm')
                {
                    dateCompleted_value = '03/15/2010'
                    click "addWorkerCertificationSubmitButton"
                }
        Thread.sleep(10000)
        List<WorkerCertification> finalWorkerCertifications = WorkerCertification.findAllByWorker(contractor)
        int finalCount = finalWorkerCertifications.size()
        assertEquals(1, finalCount - initialCount)
        assertContentContains "Certification-${certificationsList.getOption(1).getValueAttribute()}"
        assertContentContains "Completed"
    }
}