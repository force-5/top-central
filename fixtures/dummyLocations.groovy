import com.force5solutions.care.cc.Location
import com.force5solutions.care.cc.Certification
import com.force5solutions.care.cc.LocationType
pre {
    if (Location.count() < 2) {
        println "Populating Sample Locations"
        createLocations()
    }

}

fixture {}


void createLocations() {
    println "Creating Locations"
    Location location = Location.list().first()
    location.sponsorCertifications = [Certification.get(new Random().nextInt(Certification.count()) + 1)]
    populateChildrenLocations(location)
}

void populateChildrenLocations(Location parent) {
    if (LocationType.findByParent(parent.locationType)) {
        Integer childrenToGenerate = 2 + new Random().nextInt(2)
        println "Parent: " + parent + ", Children: " + childrenToGenerate
        List<LocationType> types = LocationType.findAllByParent(parent.locationType)
        LocationType locationType = types.get(new Random().nextInt(types.size()))
        (1..childrenToGenerate).each {Integer index ->
            locationType = types.get(new Random().nextInt(types.size()))
            Location location = new Location()
            location.parent = parent
            location.name = locationType.type
            if (!(location.name in location.parent.childLocations*.name)) {
                if (locationType.isEditable) {
                    location.name += ('-' + index)
                }

                location.notes = locationType.type + '-' + index + '\n Notes goes here.'
                location.locationType = locationType

                List<Certification> remainingCertifications = Certification.list() - parent.inheritedCertifications as List
                Set<Certification> certifications = []
                (new Random().nextInt(2) + 1).times {
                    certifications.add(remainingCertifications.get(new Random().nextInt(remainingCertifications.size())))
                }
                location.requiredCertifications = certifications
                location.sponsorCertifications = certifications

                if (location.s()) {
                    location.parent.childLocations.add(location)
                    location.parent.s()
                    populateChildrenLocations(location)
                } else {
                    println "************* failed to save location ******* " + location
                }
            }
        }
    }
}