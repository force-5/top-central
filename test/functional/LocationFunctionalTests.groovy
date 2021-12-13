import com.force5solutions.care.cc.Location
import com.gargoylesoftware.htmlunit.html.HtmlAnchor

class LocationFunctionalTests extends CareFunctionalTests {

    public final String TEXT_FOR_SHOW_LOCATION = "Show"
    public final String TEXT_FOR_EDIT_LOCATION = "Edit..."
    // TODO : Fix this test , it is failing on Hudson

    void createLocation() {
        login("admin", "admin")
        get('/location/newTree')
        HtmlAnchor anchor = page.getAnchors().find {
            it.getAttribute("href") == "/care/location/getLocationTree/1"
        }
        anchor.rightClick()
        assertContentContains getMessage('location.text.show.location')
        assertContentContains getMessage('location.text.edit.location')

        int initialCount = Location.count()
        LocationFormData locationFormData = LocationFormData.getDefaultLocationFormData()
        List<HtmlAnchor> newAnchors = page.getAnchors()
        def createNewLocation = newAnchors.find {
            it.getAttribute("href") == "#2"
        }
        createNewLocation.click()
        waitForElementToAppear('createLocationForm')
        String locationName = createLocation(locationFormData)
        Thread.sleep(5000)
        int afterAddingLocation = Location.count()
        assertEquals(1, afterAddingLocation - initialCount)

        Location location = Location.findByName(locationName)
        List<HtmlAnchor> allAnchors = page.getAnchors()
        def newCompanyAnchor = allAnchors.find {it.getRelAttribute() == "${location.id}"}
        newCompanyAnchor.rightClick()
        assertContentContains getMessage('location.text.show.location')
        assertContentContains getMessage('location.text.edit.location')
        assertContentContains getMessage('location.text.add.new.Physical')
        assertContentContains getMessage('location.text.add.new.cyber')
        /*------------Adding new Physical-------------------*/

        List<HtmlAnchor> anchorsForPhysical = page.getAnchors()
        def addNewPhysical = anchorsForPhysical.find {
            it.getAttribute("href") == "#3"
        }
        addNewPhysical.click()
        waitForElementToAppear('createLocationForm')
        LocationFormData locationFormDataPhysical = LocationFormData.getDefaultLocationFormData()
        createLocationPhysical(locationFormDataPhysical)
        Thread.sleep(5000)
        int afterAddingPhysical = Location.count()
        assertEquals(1, afterAddingPhysical - afterAddingLocation)
        newCompanyAnchor.rightClick()
        assertContentContains getMessage('location.text.show.location')
        assertContentContains getMessage('location.text.edit.location')
        /*------------Adding new Cyber-------------------*/
        List<HtmlAnchor> allAnchorsNew = page.getAnchors()
        def addNewCyber = allAnchorsNew.find {
            it.getAttribute("href") == "#5"
        }
        addNewCyber.click()
        waitForElementToAppear('createLocationForm')
        LocationFormData locationFormDataCyber = LocationFormData.getDefaultLocationFormData()
        createLocationCyber(locationFormDataCyber)
        Thread.sleep(5000)
        int afterAddingCyber = Location.count()
        assertEquals(1, afterAddingCyber - afterAddingPhysical)

        List<HtmlAnchor> allNewAnchors = page.getAnchors()
        def addNewPhysicalLocation = allNewAnchors.find {
            it.getRelAttribute() == "${location.id + 1}"
        }
        addNewPhysicalLocation.rightClick()
//        /*------------Adding new Critical Physical-------------------*/
//
//        def addNewCyberLocation = allNewAnchors.find {
//            it.getRelAttribute() == "${location.id + 2}"
//        }
//        addNewCyberLocation.rightClick()
//        assertContentContains getMessage('location.text.show.location')
//        assertContentContains getMessage('location.text.edit.location')
        logout()
//    }
    }
}
