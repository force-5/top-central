import com.force5solutions.care.cc.Contractor
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput

class ContractorProfileHistoryFunctionalTests extends CareFunctionalTests {

    void testHistoryUpdation() {
        login("admin", "admin")
//        get('/contractor/create')
//        ContractorFormData contractorFormDataNew = ContractorFormData.getDefaultContractorFormData()
//        createContractor(contractorFormDataNew)
//        assertContentContains getMessage('contractor.create.message')
//
//        List<Contractor> contractors = Contractor.list().reverse()
//        Contractor contractor = contractors.get(0)
//        int initialRowCount = byId('tablesorter').getRowCount()
//        HtmlButtonInput editButton = byId("editButton")
//        editButton.click()
//        ContractorFormData contractorFormData = ContractorFormData.getDefaultContractorFormData()
//        contractorFormData.firstName = "New-First-Name"
//        contractorFormData.middleName = "New-Middle-Name"
//        contractorFormData.lastName = "New-Last-Name"
//        updateContractor_NAME(contractorFormData)
//        int intermediateRowCount = byId('tablesorter').getRowCount()
//        assertContentContains contractorFormData.firstName
//        assertContentContains contractorFormData.middleName
//        assertContentContains contractorFormData.lastName
//        assertEquals(1, intermediateRowCount - initialRowCount)
//        assertContentContains getMessage('contractor.update.message')
//        assertContentContains "Contractor profile updated:"
//        assertContentContains "Middle Name"
//        assertContentContains "Last Name"
//        assertContentContains "First Name"

        /*-- click on edit and then save without modifying anything
          -- assert on the number of records in the table that it should not increase.*/
//        editButton.click()
//        HtmlSubmitInput updateButton = byId('updateContractor')
//        updateButton.click()
//        int intermediateRowCountWithoutEditing = byId('tablesorter').getRowCount()
//        assertEquals(0, intermediateRowCountWithoutEditing - intermediateRowCount)

        /*  now add a location
         go back to history and check that the location row is shown in the history */

//        addLocationToContractor(contractor)
//        get("/contractor/show/${contractor.id}")
//        int intermediateRowCountAfterAddingLocation = byId('tablesorter').getRowCount()
//        assertTrue(intermediateRowCountAfterAddingLocation > intermediateRowCountWithoutEditing)
//
//        /* modify 3 fields together in the profile and save
//           verify that the content of all the 3 fields is shown in the history*/
//        HtmlButtonInput editContractorButton1 = byId("editButton")
//        editContractorButton1.click()
//        ContractorFormData contractorFormData1 = ContractorFormData.getDefaultContractorFormData()
//        contractorFormData1.firstName="New-Gfunc-First-Name"
//        updateContractor_FIRST_NAME(contractorFormData1)
//        HtmlButtonInput editContractorButton2 = byId("editButton")
//        editContractorButton2.click()
//        contractorFormData1.slid="New-Gfunc-SLID-${System.currentTimeMillis()}"
//        updateContractor_SLID(contractorFormData1)
//        HtmlButtonInput editContractorButton3 = byId("editButton")
//        editContractorButton3.click()
//        contractorFormData1.birthDay=byName('birthDay').getOption(10).getValueAttribute()
//        updateContractor_BIRTHDAY(contractorFormData1)
//        int finalRowCount = byId('tablesorter').getRowCount()
//        assertEquals(3, finalRowCount - intermediateRowCountAfterAddingLocation)
        logout()
    }
}
